package com.thecodeside.cvshowcaseapp.repository.local

import com.thecodeside.cvshowcaseapp.repository.model.CvData
import javax.inject.Inject

// TODO: 1/10/21 implement local data source
class LocalCvDataSource @Inject constructor() {

    suspend fun fetchCvData(): CvData = TODO()
}
