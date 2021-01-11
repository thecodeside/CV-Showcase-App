package com.thecodeside.cvshowcaseapp.repository.remote

import com.thecodeside.cvshowcaseapp.repository.CvService
import javax.inject.Inject


class RemoteCvDataSource @Inject constructor(
    private val cvService: CvService
) {

    suspend fun fetchCvData() = cvService.getCv()
}
