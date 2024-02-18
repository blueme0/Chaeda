package com.chaeda.chaeda.presentation.homework

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Environment
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chaeda.chaeda.R
import com.chaeda.domain.repository.SampleRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class HomeworkViewModel @Inject constructor(
    val repository: SampleRepository
) : ViewModel() {

    private var _urlState = MutableStateFlow<FileState>(FileState.Init)
    val urlState: StateFlow<FileState> = _urlState.asStateFlow()

    fun getPresignedUrl(
        memberId: Int,
        imageType: String = "ANNOUNCEMENT_THUMBNAIL",
        imageFileExtension: String = "PNG") {
        viewModelScope.launch {
            repository.getPresignedUrl(memberId, imageType, imageFileExtension)
                .onSuccess {
                    Timber.tag("chaeda-pre").d("onSuccess: $it")
                    _urlState.value = FileState.UrlSuccess(it)
                }
                .onFailure {
                    Timber.tag("chaeda-pre").d("onFailure: ${it.message}")
                    _urlState.value = FileState.Failure(it.message!!)
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
}

sealed interface FileState {
    object Init: FileState
    object Success: FileState
    data class UrlSuccess(val url: String): FileState
    data class FileSuccess(val url: String): FileState
    data class Failure(val msg: String): FileState
}