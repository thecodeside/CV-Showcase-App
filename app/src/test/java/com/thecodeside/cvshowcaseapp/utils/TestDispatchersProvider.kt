package com.thecodeside.cvshowcaseapp.utils

import com.thecodeside.cvshowcaseapp.common.utils.DispatcherProvider
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineDispatcher


class TestDispatchersProvider(
    val testDispatcher: TestCoroutineDispatcher = TestCoroutineDispatcher()
) : DispatcherProvider {

    override fun default(): CoroutineDispatcher = testDispatcher
    override fun io(): CoroutineDispatcher = testDispatcher
    override fun main(): CoroutineDispatcher = testDispatcher
    override fun unconfined(): CoroutineDispatcher = testDispatcher
}