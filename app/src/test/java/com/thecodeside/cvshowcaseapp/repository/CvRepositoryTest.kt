package com.thecodeside.cvshowcaseapp.repository

import com.thecodeside.cvshowcaseapp.repository.local.LocalCvDataSource
import com.thecodeside.cvshowcaseapp.repository.remote.RemoteCvDataSource
import com.thecodeside.cvshowcaseapp.utils.TestDispatcherExtension
import com.thecodeside.cvshowcaseapp.utils.mockCvData
import com.thecodeside.timberjunit5.TimberTestExtension
import io.kotest.matchers.shouldBe
import io.mockk.Called
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.test.runBlockingTest
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.api.extension.RegisterExtension

@ExtendWith(TimberTestExtension::class)
internal class CvRepositoryTest {

    @JvmField
    @RegisterExtension
    val testDispatcherExtension = TestDispatcherExtension()

    private val localCvDataSource: LocalCvDataSource = mockk()
    private val remoteCvDataSource: RemoteCvDataSource = mockk {
        coEvery { fetchCvData() } returns mockCvData
    }

    private val cvRepository = CvRepository(localCvDataSource, remoteCvDataSource)

    @Test
    fun `WHEN cv data is fetched THEN CV data is returned direct from remote server`() =
        testDispatcherExtension.runBlockingTest {
            val receivedData = cvRepository.fetchCvData()

            assertAll(
                { receivedData shouldBe mockCvData },
                { verify { localCvDataSource wasNot Called } }
            )
        }
}