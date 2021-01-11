package com.thecodeside.cvshowcaseapp.features.about

import com.thecodeside.cvshowcaseapp.repository.CvRepository
import javax.inject.Inject

// TODO: 1/10/21 generally one use case should one business action, this is dead simply, ready for future improvements 
class FetchCvDataUseCase @Inject constructor(
    private val cvRepository: CvRepository,
) {

    suspend operator fun invoke() = cvRepository.fetchCvData()
}
