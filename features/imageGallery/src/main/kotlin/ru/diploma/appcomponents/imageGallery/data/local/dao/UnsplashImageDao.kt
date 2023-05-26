package ru.diploma.appcomponents.imageGallery.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ru.diploma.appcomponents.imageGallery.data.models.UnsplashImageResponse

@Dao
interface UnsplashImageDao {

    @Query("SELECT * FROM unsplash_image_table")
    fun getAllImages(): PagingSource<Int, UnsplashImageResponse>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addImages(images: List<UnsplashImageResponse>)

    @Query("DELETE FROM unsplash_image_table")
    suspend fun deleteAllImages()

    @Query("SELECT * FROM unsplash_image_table WHERE id = :id")
    suspend fun getImageDetails(id: String): UnsplashImageResponse
}