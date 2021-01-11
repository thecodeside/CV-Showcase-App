package com.thecodeside.cvshowcaseapp.repository

import com.thecodeside.cvshowcaseapp.repository.local.LocalCvDataSource
import com.thecodeside.cvshowcaseapp.repository.model.CvData
import com.thecodeside.cvshowcaseapp.repository.remote.RemoteCvDataSource
import javax.inject.Inject

// TODO: 1/10/21 split repository between features
class CvRepository @Inject constructor(
    private val localCvDataSource: LocalCvDataSource,
    private val remoteCvDataSource: RemoteCvDataSource
) {

    suspend fun fetchCvData(): CvData = remoteCvDataSource.fetchCvData()
}
