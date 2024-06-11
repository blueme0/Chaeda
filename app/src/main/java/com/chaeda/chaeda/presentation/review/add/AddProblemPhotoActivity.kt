package com.chaeda.chaeda.presentation.review.add

import android.Manifest
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import coil.load
import com.canhub.cropper.CropImage
import com.canhub.cropper.CropImageContract
import com.canhub.cropper.CropImageContractOptions
import com.canhub.cropper.CropImageOptions
import com.chaeda.base.BindingActivity
import com.chaeda.base.util.extension.setOnSingleClickListener
import com.chaeda.base.util.extension.stringExtra
import com.chaeda.chaeda.R
import com.chaeda.chaeda.databinding.ActivityAddProblemPhotoBinding
import com.chaeda.chaeda.presentation.assignment.AssignmentViewModel
import com.chaeda.chaeda.presentation.assignment.FileState
import com.chaeda.domain.entity.PresignedDetailInfo
import com.chaeda.domain.enumSet.Chapter
import com.chaeda.domain.enumSet.Subject
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import timber.log.Timber
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@AndroidEntryPoint
class AddProblemPhotoActivity
    : BindingActivity<ActivityAddProblemPhotoBinding>(R.layout.activity_add_problem_photo) {

    private val viewModel by viewModels<AddProblemViewModel>()
    private val imageViewModel by viewModels<AssignmentViewModel>()

    private val index by stringExtra("")
    private val name by stringExtra("")
    private val tsubject by stringExtra("")

    private var subjects: Array<Subject>? = null
    private var subject: Subject? = null
    private var chapters: List<Chapter>? = null
    private var chapter: Chapter? = null

    private var reviewFile: File? = null
    private var tempFile: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        subjects = Subject.values()
        subject = subjects!![0]
        if (tsubject != null) initForResultReview()
        chapters = subject!!.chapters
        chapter = chapters!![0]

        initListener()
        initAddBtn()

        setTextChangedListener()
        sendFileObserver()
        reviewObserver()
    }

    private fun initForResultReview() {
        for (sub in subjects!!) {
            if (sub.koreanName == tsubject) {
                subject = sub
                break
            }
        }

        with(binding) {
            val now = LocalDateTime.now()
            etDate.setText(now.format(DateTimeFormatter.ofPattern("yyyy.MM.dd")))
            etTextbook.setText(name)
            etIndex.setText(index)
        }
    }

    private fun initAddBtn() {
        with(binding) {
            tvPhoto.setOnSingleClickListener {
                if (ContextCompat.checkSelfPermission(this@AddProblemPhotoActivity, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(this@AddProblemPhotoActivity, arrayOf(Manifest.permission.CAMERA), REQUEST_IMAGE_CODE)
                } else {
//                    Log.d("chaeda-photo", "called")
//                    val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
//                    Log.d("chaeda-photo", "${intent.resolveActivity(packageManager)}")
//                    if (intent.resolveActivity(packageManager) != null) {
//                        startActivityForResult(intent, REQUEST_IMAGE_CODE)
//                    }
                    startCameraWithoutUri()
                }
            }
        }
    }

    //이미지 저장 버튼 클릭 메서드
    private fun imgSaveOnClick(bitmap: Bitmap) {

        Timber.tag("chaeda-photo").d("imgSaveOnClick called")

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            //Q 버전 이상일 경우. (안드로이드 10, API 29 이상일 경우)
            Timber.tag("chaeda-photo").d("api 29 이상")
            saveImageOnAboveAndroidQ(bitmap)
            Toast.makeText(baseContext, "이미지 저장이 완료되었습니다.", Toast.LENGTH_SHORT).show()
        } else {
            // Q 버전 이하일 경우. 저장소 권한을 얻어온다.
            Timber.tag("chaeda-photo").d("api 29 미만")
            val writePermission = ActivityCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)

            if(writePermission == PackageManager.PERMISSION_GRANTED) {
                saveImageOnUnderAndroidQ(bitmap)
                Toast.makeText(baseContext, "이미지 저장이 완료되었습니다.", Toast.LENGTH_SHORT).show()
            } else {
                val requestExternalStorageCode = 1

                val permissionStorage = arrayOf(
                    android.Manifest.permission.READ_EXTERNAL_STORAGE,
                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE
                )

                ActivityCompat.requestPermissions(this, permissionStorage, requestExternalStorageCode)
            }
        }
    }

    //Android Q (Android 10, API 29 이상에서는 이 메서드를 통해서 이미지를 저장한다.)
    @RequiresApi(Build.VERSION_CODES.Q)
    private fun saveImageOnAboveAndroidQ(bitmap: Bitmap) {
        val fileName = System.currentTimeMillis().toString() + ".png" // 파일이름 현재시간.png

        Timber.tag("chaeda-photo").d("fileName : ${fileName}")
        /*
        * ContentValues() 객체 생성.
        * ContentValues는 ContentResolver가 처리할 수 있는 값을 저장해둘 목적으로 사용된다.
        * */
        val contentValues = ContentValues()
        contentValues.apply {
            put(MediaStore.Images.Media.RELATIVE_PATH, "DCIM/ImageSave") // 경로 설정
            put(MediaStore.Images.Media.DISPLAY_NAME, fileName) // 파일이름을 put해준다.
            put(MediaStore.Images.Media.MIME_TYPE, "image/png")
            put(MediaStore.Images.Media.IS_PENDING, 1) // 현재 is_pending 상태임을 만들어준다.
            // 다른 곳에서 이 데이터를 요구하면 무시하라는 의미로, 해당 저장소를 독점할 수 있다.
        }

        Timber.tag("chaeda-photo").d("contentValue : ${contentValues.toString()}")

        // 이미지를 저장할 uri를 미리 설정해놓는다.
        val uri = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)

        Timber.tag("chaeda-photo").d("uri : ${uri.toString()}")

        try {
            if(uri != null) {
                val image = contentResolver.openFileDescriptor(uri, "w", null)
                // write 모드로 file을 open한다.

                Timber.tag("chaeda-photo").d("uri is not null")
                if(image != null) {
                    Timber.tag("chaeda-photo").d("image is not null")
                    val fos = FileOutputStream(image.fileDescriptor)
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos)
                    //비트맵을 FileOutputStream를 통해 compress한다.
                    fos.close()

                    contentValues.clear()
                    contentValues.put(MediaStore.Images.Media.IS_PENDING, 0) // 저장소 독점을 해제한다.
                    contentResolver.update(uri, contentValues, null, null)

                    createCopyAndReturnRealPath(uri, fileName)?.let { File(it) }
                        ?.let { reviewFile = it }
                }
            }
        } catch(e: FileNotFoundException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun saveImageOnUnderAndroidQ(bitmap: Bitmap) {
        val fileName = System.currentTimeMillis().toString() + ".png"
        val externalStorage = Environment.getExternalStorageDirectory().absolutePath
        val path = "$externalStorage/DCIM/imageSave"
        val dir = File(path)

        if(dir.exists().not()) {
            dir.mkdirs() // 폴더 없을경우 폴더 생성
        }

        try {
            val fileItem = File("$dir/$fileName")
            fileItem.createNewFile()
            //0KB 파일 생성.

            val fos = FileOutputStream(fileItem) // 파일 아웃풋 스트림

            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos)
            //파일 아웃풋 스트림 객체를 통해서 Bitmap 압축.

            fos.close() // 파일 아웃풋 스트림 객체 close

            sendBroadcast(Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(fileItem)))
            reviewFile = fileItem
            // 브로드캐스트 수신자에게 파일 미디어 스캔 액션 요청. 그리고 데이터로 추가된 파일에 Uri를 넘겨준다.
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

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
        tempFile = filePath

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

    private val customCropImage = registerForActivityResult(CropImageContract()) {
        if (it !is CropImage.CancelledResult) {
//            it.uriContent?.let { it1 -> viewModel.setImageUri(it1) }
            binding.ivPhoto.load(it.uriContent)
            it.getBitmap(this)?.let { it1 -> imgSaveOnClick(it1) }
            it.bitmap?.let { it1 -> imgSaveOnClick(it1) }
//            it.uriContent?.let { it1 -> getImageFromUri(it1) }
            Log.e("chaeda-uri", "cropImage = ${it.uriContent}")
        }
    }

    private fun startCameraWithoutUri() {
        customCropImage.launch(
            CropImageContractOptions(
                uri = null,
                cropImageOptions = CropImageOptions(
                    imageSourceIncludeCamera = true,
                    imageSourceIncludeGallery = false,
                ),
            ),
        )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGE_CODE && resultCode == RESULT_OK) {
            val extras = data?.extras
            val bitmap = extras?.get("data") as Bitmap
            binding.ivPhoto.load(bitmap)
        }
    }

    private fun initListener() {
        with(binding) {
            llBack.setOnSingleClickListener {
                finish()
            }

            fab.setOnSingleClickListener {
                imageViewModel.getPresignedUrl(1, "REVIEW_NOTE_PROBLEM", "PNG")
            }

            resetSpinnerChapter(null)

            spinnerSubject.adapter = object : ArrayAdapter<Subject>(this@AddProblemPhotoActivity, R.layout.item_spinner, Subject.values()) {
                override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
                    val view = super.getView(position, convertView, parent)
                    val textView = view.findViewById<AppCompatTextView>(R.id.spinner_tv)
                    textView.text = Subject.values()[position].koreanName
                    return view
                }

                override fun getDropDownView(
                    position: Int,
                    convertView: View?,
                    parent: ViewGroup
                ): View {
                    val view = super.getDropDownView(position, convertView, parent)
                    val textView = view.findViewById<AppCompatTextView>(R.id.spinner_tv)
                    textView.text = Subject.values()[position].koreanName
                    return view
                }
            }

            spinnerSubject.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    if (parent != null) {
                        subject = parent.getItemAtPosition(position) as Subject
                        chapters = subject!!.chapters
                        resetSpinnerChapter(subject)
                        spinnerChapter.setSelection(0)
                    }
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                }
            }

            spinnerChapter.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                    if (p0 != null) {
                        chapter = p0.getItemAtPosition(p2) as Chapter
                        viewModel.updateChapter(chapter!!)
                    }
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {
                }
            }
        }
    }

    private fun resetSpinnerChapter(subject: Subject?) {
        binding.spinnerChapter.isEnabled = subject != null
        if (subject == null) return
        viewModel.updateChapter(subject.chapters[0])
        binding.spinnerChapter.adapter = object : ArrayAdapter<Chapter>(this, R.layout.item_spinner, subject.chapters) {
            override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
                val view = super.getView(position, convertView, parent)
                val textView = view.findViewById<AppCompatTextView>(R.id.spinner_tv)
                textView.text = subject.chapters[position].koreanName
                return view
            }

            override fun getDropDownView(
                position: Int,
                convertView: View?,
                parent: ViewGroup
            ): View {
                val view = super.getDropDownView(position, convertView, parent)
                val textView = view.findViewById<AppCompatTextView>(R.id.spinner_tv)
                textView.text = subject.chapters[position].koreanName
                return view
            }
        }

    }

    private fun postProblemImage(pdInfo: PresignedDetailInfo, file: File) {
        imageViewModel.putFileToUrl(pdInfo, "image/png", file)
    }

    private fun setTextChangedListener() {
        with(binding) {
            etDate.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun afterTextChanged(p0: Editable?) {
                    viewModel.updateDateString(p0.toString())
                }
            })

            etAnswer.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun afterTextChanged(p0: Editable?) {
                    viewModel.updateAnswer(p0.toString())
                }
            })

            etTextbook.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun afterTextChanged(p0: Editable?) {
                    viewModel.updateTextbookName(p0.toString())
                }
            })

            etIndex.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun afterTextChanged(p0: Editable?) {
                    viewModel.updateProblemNum(p0.toString())
                }
            })
        }
    }

    private fun sendFileObserver() {
        lifecycleScope.launchWhenStarted {
            imageViewModel.urlState.collect { state ->
                when (state) {
                    is FileState.UrlSuccess -> {
                        Timber.tag("chaeda-photo").d("reviewFile : ${reviewFile.toString()}")
                        if (reviewFile != null) {
                            val urlList = state.url
                            viewModel.updateImageKey(urlList[0].presignedInfo.imageKey)
                            postProblemImage(urlList[0], reviewFile!!)
                        }
                    }
                    is FileState.Failure -> {

                    }
                    is FileState.FileSuccess -> {
                        // s3에 업로드 시 여기로 들어와야 함
                        Timber.tag("chaeda-pre").d("FileState is FileSuccess\n${state.url}")
                        if (viewModel.isValid.value) {
                            viewModel.postProblemToBox()
                        }
                    }
                    is FileState.UploadImagesSuccess -> {
                        Timber.tag("chaeda-pre").d("FileState is UploadImagesSuccess\n${state.url}")
                    }
                    else -> { }
                }
            }
        }
    }

    private fun reviewObserver() {
        lifecycleScope.launch {
            viewModel.reviewState.collect { state ->
                when (state) {
                    is ReviewState.Failure -> {

                    }
                    is ReviewState.PostProblemSuccess -> {
                        finish()
                    }
                    else -> { }
                }
            }
        }
    }

    companion object {
        fun getIntent(context: Context) = Intent(context, AddProblemPhotoActivity::class.java)
        fun getIntent(context: Context, index: String, name: String, tsubject: String) = Intent(context, AddProblemPhotoActivity::class.java).apply {
            putExtra("index", index)
            putExtra("name", name)
            putExtra("tsubject", tsubject)
        }
        private const val REQUEST_IMAGE_CODE = 101
    }
}