package com.thecodeside.cvshowcaseapp.features.about

import app.cash.turbine.test
import com.thecodeside.cvshowcaseapp.utils.TestDispatcherExtension
import com.thecodeside.cvshowcaseapp.utils.mockCvData
import com.thecodeside.timberjunit5.TimberTestExtension
import io.kotest.matchers.types.shouldBeInstanceOf
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runBlockingTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.api.extension.RegisterExtension
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import java.net.UnknownHostException
import kotlin.time.ExperimentalTime

@ExperimentalTime
@ExtendWith(TimberTestExtension::class)
internal class AboutViewModelTest {

    @JvmField
    @RegisterExtension
    val testDispatcherExtension = TestDispatcherExtension()

    private val fetchCvDataUseCase: FetchCvDataUseCase = mockk()

    @BeforeEach
    fun beforeEach() {
        clearAllMocks()
        coEvery { fetchCvDataUseCase() } returns mockCvData
    }

    private lateinit var viewModel: AboutViewModel

    @Nested
    inner class `GIVEN view state test` {

        @Test
        fun `WHEN fetch CV data return proper data THEN state is Content`() =
            testDispatcherExtension.runBlockingTest {
                viewModel = provideViewModel()
                viewModel.viewState.test {
                    expectItem().shouldBeInstanceOf<AboutViewState.Content>()
                    expectNoEvents()
                }
            }

        @Test
        fun `WHEN fetch CV data throws Exception THEN state is Error`() =
            testDispatcherExtension.runBlockingTest {
                coEvery { fetchCvDataUseCase() } throws UnknownHostException()
                viewModel = provideViewModel()
                viewModel.viewState.test {
                    expectItem().shouldBeInstanceOf<AboutViewState.Error>()
                    cancelAndIgnoreRemainingEvents()
                }
            }
    }

    @Nested
    inner class `GIVEN easter egg test` {

        @ParameterizedTest(name = "Click {0} times")
        @ValueSource(ints = [2, 5])
        fun `WHEN profile picture click count is N THEN viewEffect is ProfilePictureKeepClicking`(
            clicks: Int
        ) =
            testDispatcherExtension.runBlockingTest {
                viewModel = provideViewModel()

                viewModel.viewEffect.test {
                    viewModel.profilePictureClick(clicks)
                    expectItem().shouldBeInstanceOf<AboutViewEffect.ProfilePictureKeepClicking>()
                    cancelAndIgnoreRemainingEvents()
                }
            }

        @ParameterizedTest(name = "Click {0} times")
        @ValueSource(ints = [0, 1, 3, 4, 6, 8])
        fun `WHEN profile picture click count is NOT 2, 5, 7 THEN there is no profile picture viewEffect`(
            clicks: Int
        ) =
            testDispatcherExtension.runBlockingTest {
                viewModel = provideViewModel()

                viewModel.viewEffect.test {
                    viewModel.profilePictureClick(clicks)
                    expectNoEvents()
                }
            }

        @Test
        fun `WHEN user clicks on profile picture 7 times THEN viewEffects are KeepClicking, KeepClicking, EasterEgg`() =
            testDispatcherExtension.runBlockingTest {
                viewModel = provideViewModel()

                viewModel.viewEffect.test {
                    for (clicks in 1..7) {
                        viewModel.profilePictureClick(clicks)
                    }
                    expectItem().shouldBeInstanceOf<AboutViewEffect.ProfilePictureKeepClicking>()
                    expectItem().shouldBeInstanceOf<AboutViewEffect.ProfilePictureKeepClicking>()
                    expectItem().shouldBeInstanceOf<AboutViewEffect.ProfilePictureEasterEgg>()
                    cancelAndIgnoreRemainingEvents()
                }
            }
    }

    private fun provideViewModel() = AboutViewModel(
        fetchCvDataUseCase,
        testDispatcherExtension.testDispatchers
    )
}