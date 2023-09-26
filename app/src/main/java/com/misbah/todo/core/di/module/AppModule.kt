package com.misbah.todo.core.di.module

import android.app.Application
import android.content.Context
import com.misbah.todo.core.data.remote.APIService
import com.misbah.todo.core.data.remote.RemoteDataSource
import com.misbah.todo.core.di.NetworkConnectionInterceptor
import com.misbah.todo.ui.utils.Utils
import com.google.gson.Gson
import com.misbah.todo.ui.ToDoApplication
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Named
import javax.inject.Singleton

/**
 * @author: Mohammad Misbah
 * @since: 26-Sep-2023
 * @sample: Technology Assessment for Sr. Android Role
 * Email Id: mohammadmisbahazmi@gmail.com
 * GitHub: https://github.com/misbahazmi
 * Expertise: Android||Java/Kotlin||Flutter
 */
@Module
class AppModule {

    @Singleton
    @Provides
    fun provideContext(application: ToDoApplication): Context = application

    @Provides
    @Singleton
    fun provideApplication(app: ToDoApplication): Application = app

    @Singleton
    @Provides
    fun provideGson() = Gson()

    @Singleton
    @Provides
    fun provideUtils(context: Context) = Utils(context = context)

    @Provides
    @Singleton
    @Named("DEFAULT")
    fun provideDefaultDispatchers(): CoroutineDispatcher = Dispatchers.Default

    @Provides
    @Singleton
    @Named("IO")
    fun provideBackgroundDispatchers(): CoroutineDispatcher = Dispatchers.IO

    @Provides
    @Singleton
    @Named("MAIN")
    fun provideMainDispatchers(): CoroutineDispatcher = Dispatchers.Main

    @Provides
    @Singleton
    fun provideNetworkConnectionInterceptor(utils: Utils) = NetworkConnectionInterceptor(utils)

    @Provides
    @Singleton
    fun provideRemoteDataSource(apiService: APIService) = RemoteDataSource(apiService)
}