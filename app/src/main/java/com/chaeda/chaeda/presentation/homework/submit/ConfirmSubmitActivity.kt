package com.chaeda.chaeda.presentation.homework.submit

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.chaeda.base.BindingActivity
import com.chaeda.base.util.extension.setOnSingleClickListener
import com.chaeda.chaeda.R
import com.chaeda.chaeda.databinding.ActivityConfirmSubmitBinding
import com.chaeda.chaeda.presentation.homework.FileState
import com.chaeda.chaeda.presentation.homework.HomeworkViewModel
import com.chaeda.domain.entity.FileWithName
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream


@AndroidEntryPoint
class ConfirmSubmitActivity
    : BindingActivity<ActivityConfirmSubmitBinding>(R.layout.activity_confirm_submit) {

    private val introduceAdapter = ConfirmSubmitAdapter()
    private val viewpagerList = ArrayList<File>()
    private val viewModel by viewModels<HomeworkViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initViewPager()
        initListener()
        sendFileObserver()
    }

    private fun initViewPager() {
        introduceAdapter.submitList(viewpagerList)
        with(binding) {
            vp.adapter = introduceAdapter
            vp.getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER
            vp.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    tvCount.text = "${position + 1}/${viewpagerList.size}장"
                }
            })
        }
    }

    private fun initListener() {
        with(binding) {
            tvSubmit.setOnSingleClickListener {
                viewModel.getPresignedUrl(viewpagerList.size, 4)
            }
            tvAgain.setOnSingleClickListener {
                // 갤러리에서 사진 가져와서 addFileToViewPager하면 돼
                // viewPagerList.add(file)하고, introduceAdapter.notifyDataSetChanged() 하기
                selectGallery()
            }
            llBack.setOnSingleClickListener { finish() }
        }
    }

    /**
     * 갤러리에서 사진 여러 장 선택하는 함수
     */
    private fun selectGallery() {
        val readPermission = ContextCompat.checkSelfPermission(this,
            android.Manifest.permission.READ_EXTERNAL_STORAGE)

        if(readPermission == PackageManager.PERMISSION_DENIED) { //권한이 없을 시 권한 요청
            ActivityCompat.requestPermissions(
                this,
                arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE), REQ_GALLERY
            )
        }else{
            //권한이 있을 경우 갤러리 실행
            // 갤러리에서 이미지 선택을 위한 Intent
            val intent = Intent(Intent.ACTION_PICK)
            intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                "image/*")
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true) // 다중 선택 가능하도록 설정
            selectImagesActivityResult.launch(intent)
        }
    }

    /**
     * 선택한 이미지 Uri의 -> uri와 파일명 등을 반환하는 메소드 (권한 있어야 함)
     */
    private fun getGalleryImage(uri: Uri) {
        val cursor: Cursor?
        val projection = arrayOf(MediaStore.Images.Media._ID, MediaStore.Images.Media.DISPLAY_NAME)
        val sortOrder = MediaStore.Images.Media._ID + " DESC" // 최신순으로 가져오기
        cursor = contentResolver.query(
            uri,
            projection,
            null,
            null,
            sortOrder
        )
        val idColumn = cursor!!.getColumnIndexOrThrow(MediaStore.Images.Media._ID)
        val displayNameColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME)
        cursor.use {cursor ->
            if (cursor.moveToFirst()) {
                val id = cursor.getLong(idColumn)
                val displayName = cursor.getString(displayNameColumn)
                val contentUri =
                    Uri.withAppendedPath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, id.toString())
    //            galleryList.add(GalleryModel(contentUri.toString(), displayName))
                Timber.tag("chaeda-gallery").d("contentUri: $contentUri")
                createCopyAndReturnRealPath(contentUri, displayName)?.let { File(it) }
                    ?.let { viewpagerList.add(it) }
            }
        }
    }

    private val selectImagesActivityResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data: Intent? = result.data
                //If multiple image selected
                if (data?.clipData != null) {
                    clearTempFileList()
                    val count = data.clipData?.itemCount ?: 0
                    for (i in 0 until count) {
                        val imageUri = data.clipData!!.getItemAt(i).uri
                        getGalleryImage(imageUri)
                    }
                }
                //If single image selected
                else if (data?.data != null) {
                    clearTempFileList()
                    val imageUri: Uri? = data.data
                    if (imageUri !=null) {
                        getGalleryImage(imageUri)
                    }
                }
                introduceAdapter.notifyDataSetChanged()
                binding.tvCount.text = "1/${viewpagerList.size}장"
            }
        }

    /**
     * tempFileList에 있는 모든 임시 이미지 파일 삭제
     */
    private fun clearTempFileList() {
        viewpagerList.clear()
        for (tempFile in tempFileList) {
            val file = File(tempFile)
            if (file.exists()) file.delete()
        }
    }

    private val tempFileList = ArrayList<String>()

    /**
     * 이미지 파일을 복사한 후, 그 파일의 절대 경로 반환하는 메소드
     */
    private fun createCopyAndReturnRealPath(uri: Uri?, fileName: String): String? {
        val contentResolver = contentResolver ?: return null
        // 내부 저장소 안에 위치하도록 파일 생성
        val filePath =
            applicationInfo.dataDir + File.separator + System.currentTimeMillis() + "." + fileName.substring(
                fileName.lastIndexOf(".") + 1
            )
        val file = File(filePath)
        // 서버에 이미지 업로드 후 삭제하기 위해 경로를 저장해둠
        tempFileList.add(filePath)

        /**
         * if(tempFile.exists()) { tempFile.delete() }
         * 후에 서버에 전송 후 위와 같이 임시 파일 삭제 과정 필수
         */

        try {
            // 매개변수로 받은 uri 를 통해  이미지에 필요한 데이터를 가져온다.
            val inputStream = contentResolver.openInputStream(uri!!) ?: return null
            // 가져온 이미지 데이터를 아까 생성한 파일에 저장한다.
            val outputStream: OutputStream = FileOutputStream(file)
            val buf = ByteArray(1024)
            var len: Int
            while (inputStream.read(buf).also { len = it } > 0) outputStream.write(buf, 0, len)
            outputStream.close()
            inputStream.close()
        } catch (ignore: IOException) {
            return null
        }
        return file.absolutePath // 생성한 파일의 절대경로 반환
    }

    private fun testPostHomeworkImage(url: String, file: File) {
        viewModel.putFileToUrl(url, "image/png", file)
    }

    private fun sendFileObserver() {
        lifecycleScope.launchWhenStarted {
            viewModel.urlState.collect { state ->
                when (state) {
                    is FileState.UrlSuccess -> {
                        val urlList = state.url
                        /**
                         * s3에 업로드할 때
                         */
//                        for (i in urlList.indices) {
//                            testPostHomeworkImage(urlList[i].presigendUrl, viewpagerList[i])
//                        }
                        /**
                         * uploadFile로 할 때
                         */
                        val fileWithNameList = mutableListOf<FileWithName>()
                        for (i in urlList.indices) {
                            fileWithNameList.add(FileWithName(viewpagerList[i], urlList[i].imageKey))
                            Timber.tag("chaeda-file").d("fileWithName: ${fileWithNameList[i]}")
                        }
                        viewModel.uploadImageFiles(fileWithNameList)
                    }
                    is FileState.Failure -> {
                    }
                    is FileState.FileSuccess -> {
                        Timber.tag("chaeda-pre").d("FileState is FileSuccess\n${state.url}")
                    }
                    is FileState.UploadImagesSuccess -> {
                        Timber.tag("chaeda-pre").d("FileState is UploadImagesSuccess\n${state.url}")
                    }
                    else -> {}
                }
            }
        }
    }

    companion object {
        fun getIntent(context: Context) = Intent(context, ConfirmSubmitActivity::class.java)

        private const val REQ_GALLERY = 1
    }
}