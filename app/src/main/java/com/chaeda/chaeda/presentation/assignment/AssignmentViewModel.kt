package com.chaeda.chaeda.presentation.assignment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chaeda.domain.entity.AssignmentDTO
import com.chaeda.domain.entity.FileWithName
import com.chaeda.domain.entity.ImageInfo
import com.chaeda.domain.entity.PresignedDetailInfo
import com.chaeda.domain.repository.AssignmentRepository
import com.chaeda.domain.repository.ImageRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import java.io.File
import java.time.LocalDate
import java.util.Calendar
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class HomeworkViewModel @Inject constructor(
    private val imageRepository: ImageRepository,
    private val assignmentRepository: AssignmentRepository
) : ViewModel() {

    private var _urlState = MutableStateFlow<FileState>(FileState.Init)
    val urlState: StateFlow<FileState> = _urlState.asStateFlow()

    private var urlList = mutableListOf<PresignedDetailInfo>()

    fun getPresignedUrl(
        count: Int,
        imageType: String = "ANNOUNCEMENT_THUMBNAIL",
        imageFileExtension: String = "PNG") {
        repeat(count) {
            viewModelScope.launch {
                imageRepository.getPresignedUrl(imageType, imageFileExtension)
                    .onSuccess {
                        Timber.tag("chaeda-pre").d("onSuccess: $it")
//                    _urlState.value = FileState.UrlSuccess(it)
                        urlList.add(PresignedDetailInfo(it, imageType, imageFileExtension))
                        if (urlList.size == count) _urlState.value = FileState.UrlSuccess(urlList)
                    }
                    .onFailure {
                        Timber.tag("chaeda-pre").d("onFailure: ${it.message}")
                        _urlState.value = FileState.Failure(it.message!!)
                    }
            }
        }
    }

    private var _fileState = MutableStateFlow<FileState>(FileState.Init)
    val fileState: StateFlow<FileState> = _fileState.asStateFlow()

    fun putFileToUrl(
        pdInfo: PresignedDetailInfo,
        contentType: String = "image/png",
        file: File,
    ) {
        viewModelScope.launch {
            imageRepository.putFileToUrl(pdInfo.presignedInfo.presignedUrl, contentType, file)
                .onSuccess {
                    Timber.tag("chaeda-put").d("putFileToUrl success: $it")
                    noticePresignedUrl(pdInfo)
                }
                .onFailure {
                    Timber.tag("chaeda-put").d("putFileToUrl failure: $it")
                    _urlState.value = FileState.Failure(it.message!!)
                }
        }
    }

    private var sentSuccessCount = 0

    val sentImageList = mutableListOf<ImageInfo>()

    private fun noticePresignedUrl(
        pdInfo: PresignedDetailInfo
    ) {
        viewModelScope.launch {
            imageRepository.noticePresignedUrl(pdInfo.imageType, pdInfo.imageFileExtension, pdInfo.presignedInfo.imageKey)
                .onSuccess {
                    Timber.tag("chaeda-put").d("noticePresignedUrl success: $it")
                    sentSuccessCount++
                    sentImageList.add(ImageInfo(pdInfo.imageType, pdInfo.imageFileExtension, pdInfo.presignedInfo.imageKey))
                    if (sentSuccessCount == urlList.size) {
                        _urlState.value = FileState.FileSuccess(it.toString())
                        sentSuccessCount = 0
                    }
                }
                .onFailure {
                    Timber.tag("chaeda-put").d("noticePresignedUrl failure: $it")
                    _urlState.value = FileState.Failure(it.message!!)
                }
        }

    }

    fun getImagesUrl(
        images: List<ImageInfo>
    ) {
        viewModelScope.launch {
            imageRepository.getImagesUrl(images)
                .onSuccess {
                    Timber.tag("chaeda-put").d("getImagesUrl success: $it")
                    _urlState.value = FileState.GetImagesUrlSuccess(it)
                }
                .onFailure {
                    Timber.tag("chaeda-put").d("getImagesUrl failure: $it")
                    _urlState.value = FileState.Failure(it.message!!)
                }
        }
    }

    private var _selectedDate = MutableStateFlow<Date>(Calendar.getInstance().time)
    val selectedDate: StateFlow<Date> = _selectedDate

    fun setSelectedDate(date: Date) {
        Timber.tag("chaeda-cal").d("viewModel setSelectedDate called: $date")
        _selectedDate.value = date
    }

    fun uploadImageFiles(list: List<FileWithName>) {
        viewModelScope.launch {
            imageRepository.uploadImages(list)
                .onSuccess {
                    Timber.tag("chaeda-put").d("uploadImageFiles success: $it")
                    _fileState.value = FileState.UploadImagesSuccess(it)
                }
                .onFailure {
                    Timber.tag("chaeda-put").d("uploadImageFiles failure: $it")
                    _fileState.value = FileState.Failure(it.message!!)
                }
        }
    }

    private var _assignmentState = MutableStateFlow<AssignmentState>(AssignmentState.Init)
    val assignmentState: StateFlow<AssignmentState> = _assignmentState.asStateFlow()

    fun getAssignmentsByDate(dateString: String, date: LocalDate) {
        viewModelScope.launch {
            assignmentRepository.getAssignmentsByDate(dateString, date)
                .onSuccess {
                    _assignmentState.value = AssignmentState.GetListSuccess(it)
                }
                .onFailure {
                    _assignmentState.value = AssignmentState.Failure(it.message!!)
                }
        }
    }
}

sealed interface AssignmentState {
    object Init: AssignmentState
    data class GetListSuccess(val list: List<AssignmentDTO>): AssignmentState
    data class GetByIdSuccess(val assignment: AssignmentDTO): AssignmentState
    data class PutByIdSuccess(val assignment: AssignmentDTO): AssignmentState
    object DeleteByIdSuccess: AssignmentState
    data class UploadSuccess(val assignment: AssignmentDTO): AssignmentState
    object GetRangeSuccess: AssignmentState
    object SubmitSuccess: AssignmentState
    data class Failure(val msg: String): AssignmentState
}

sealed interface FileState {
    object Init: FileState
    object Success: FileState
    data class UrlSuccess(val url: List<PresignedDetailInfo>): FileState
    data class FileSuccess(val url: String): FileState
    data class UploadImagesSuccess(val url: String): FileState
    data class GetImagesUrlSuccess(val urls: List<String>): FileState
    data class Failure(val msg: String): FileState
}