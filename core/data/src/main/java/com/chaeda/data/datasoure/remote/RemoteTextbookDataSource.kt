package com.chaeda.data.datasoure.remote

import com.chaeda.data.service.TextbookService
import javax.inject.Inject

class RemoteTextbookDataSource @Inject constructor(
    private val textbookService: TextbookService
) {

}