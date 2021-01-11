package com.thecodeside.cvshowcaseapp.common

import com.thecodeside.cvshowcaseapp.common.utils.DefaultDispatcherProvider
import com.thecodeside.cvshowcaseapp.common.utils.DispatcherProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UtilsModule {

    @Provides
    @Singleton
    fun provideDispatcherProvider(): DispatcherProvider = DefaultDispatcherProvider()
}
