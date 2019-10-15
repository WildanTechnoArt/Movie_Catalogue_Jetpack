package com.wildan.moviecatalogue.di

import android.app.Application
import com.wildan.moviecatalogue.data.source.CatalogueRepository
import com.wildan.moviecatalogue.data.source.local.LocalRepository
import com.wildan.moviecatalogue.data.source.local.room.AppDatabase
import com.wildan.moviecatalogue.data.source.local.room.FavoriteDatabase
import com.wildan.moviecatalogue.data.source.remote.RemoteRepository
import com.wildan.moviecatalogue.utils.AppExecutors

object Injection {
    fun provideRepository(application: Application): CatalogueRepository? {
        val localRepository = LocalRepository(AppDatabase.getInstance(application).catalogueDao(),
            FavoriteDatabase.getInstance(application).favoriteDao())
        val remoteRepository = RemoteRepository.getInstance()
        val executors = AppExecutors()
        return CatalogueRepository.getInstance(localRepository, remoteRepository, executors)
    }
}