package com.chaeda.chaeda.presentation.homework

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chaeda.domain.entity.FileWithName
import com.chaeda.domain.entity.PresignedInfo
import com.chaeda.domain.repository.SampleRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import java.io.File
import java.util.Calendar
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class HomeworkViewModel @Inject constructor(
    val repository: SampleRepository
) : ViewModel() {

    private var _urlState = MutableStateFlow<FileState>(FileState.Init)
    val urlState: StateFlow<FileState> = _urlState.asStateFlow()

    private var urlList = mutableListOf<PresignedInfo>()

    fun getPresignedUrl(
        count: Int,
        memberId: Int,
        imageType: String = "ANNOUNCEMENT_THUMBNAIL",
        imageFileExtension: String = "PNG") {
        repeat(count) {
            viewModelScope.launch {
                repository.getPresignedUrl(memberId, imageType, imageFileExtension)
                    .onSuccess {
                        Timber.tag("chaeda-pre").d("onSuccess: $it")
//                    _urlState.value = FileState.UrlSuccess(it)
                        urlList.add(it)
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
        url: String,
        contentType: String = "image/png",
        file: File
    ) {
        viewModelScope.launch {
            repository.putFileToUrl(url, contentType, file)
                .onSuccess {
                    Timber.tag("chaeda-put").d("putFileToUrl success: $it")
                    _urlState.value = FileState.FileSuccess(it)
                }
                .onFailure {
                    Timber.tag("chaeda-put").d("putFileToUrl failure: $it")
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
            repository.uploadImages(list)
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
}

sealed interface FileState {
    object Init: FileState
    object Success: FileState
    data class UrlSuccess(val url: List<PresignedInfo>): FileState
    data class FileSuccess(val url: String): FileState
    data class UploadImagesSuccess(val url: String): FileState
    data class Failure(val msg: String): FileState
}