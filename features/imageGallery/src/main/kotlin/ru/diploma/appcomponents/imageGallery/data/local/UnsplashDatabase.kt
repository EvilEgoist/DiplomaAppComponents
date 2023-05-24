package ru.diploma.appcomponents.imageGallery.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.diploma.appcomponents.imageGallery.data.local.dao.SearchHistoryDao
import ru.diploma.appcomponents.imageGallery.data.local.dao.UnsplashImageDao
import ru.diploma.appcomponents.imageGallery.data.local.dao.UnsplashRemoteKeysDao
import ru.diploma.appcomponents.imageGallery.data.models.UnsplashImageResponse
import ru.diploma.appcomponents.imageGallery.data.models.localonly.SearchHistoryDbModel
import ru.diploma.appcomponents.imageGallery.data.models.localonly.UnsplashRemoteKeysDb

@Database(
    entities = [
        UnsplashImageResponse::class,
        UnsplashRemoteKeysDb::class,
        SearchHistoryDbModel::class
    ],
    version = 1
)
abstract class UnsplashDatabase : RoomDatabase() {

    abstract fun unsplashImageDao(): UnsplashImageDao
    abstract fun unsplashRemoteKeysDao(): UnsplashRemoteKeysDao

    abstract fun searchHistoryDao(): SearchHistoryDao
}