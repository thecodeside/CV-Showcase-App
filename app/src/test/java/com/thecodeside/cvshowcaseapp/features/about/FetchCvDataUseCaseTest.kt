package com.thecodeside.cvshowcaseapp.features.about

import com.thecodeside.cvshowcaseapp.repository.CvRepository
import com.thecodeside.cvshowcaseapp.utils.TestDispatcherExtension
import com.thecodeside.cvshowcaseapp.utils.mockCvData
import com.thecodeside.timberjunit5.TimberTestExtension
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runBlockingTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.api.extension.RegisterExtension

// TODO: 1/11/21 currently we test only mocking because lack of logic in use case 
@ExtendWith(TimberTestExtension::class)
internal class FetchCvDataUseCaseTest {

    @JvmField
    @RegisterExtension
    val testDispatcherExtension = TestDispatcherExtension()

    private val cvRepository: CvRepository = mockk {
        coEvery { fetchCvData() } returns mockCvData
    }

    private val fetchCvData = FetchCvDataUseCase(cvRepository)

    @Test
    fun `WHEN cv data is fetched THEN CV data is returned`() =
        testDispatcherExtension.runBlockingTest {
            val receivedData = fetchCvData()
            receivedData shouldBe mockCvData
        }
}