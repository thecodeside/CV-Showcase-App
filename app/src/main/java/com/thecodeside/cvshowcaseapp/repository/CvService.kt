package com.thecodeside.cvshowcaseapp.repository

import com.thecodeside.cvshowcaseapp.repository.model.CvData
import retrofit2.http.GET

interface CvService {

    @GET("c2f01ec8031cb5acbd12bb5e793cc817/raw/70a72f1d06db4a57681a7096d9eed33e8a2bdc3b/CvData.json")
    suspend fun getCv(): CvData
}
