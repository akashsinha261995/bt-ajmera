package com.example.bluetoothajmera.dependencies

import android.content.Context
import android.content.Intent
import com.example.bluetoothajmera.control.BtController
import com.example.bluetoothajmera.model.BtControllerInter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class SingletonBt {

    @Provides
    @Singleton

    fun provideBluetoothController(@ApplicationContext context: Context, intent: Intent): BtControllerInter{
        return BtController(context, intent)
    }
}