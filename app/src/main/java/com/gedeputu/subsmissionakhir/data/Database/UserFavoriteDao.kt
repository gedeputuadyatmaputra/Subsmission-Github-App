package com.gedeputu.subsmissionakhir.data.Database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface UserFavoriteDao {

    @Insert
    suspend fun insertFavoriteUser(favoriteUser: UserFavorite)

    @Query("SELECT * FROM favorite_user")
    fun getFavoriteUser(): LiveData<List<UserFavorite>>

    @Query("SELECT count(*) FROM favorite_user WHERE favorite_user.id = :id")
    suspend fun isFavorite(id: Int): Int

    @Query("DELETE FROM favorite_user WHERE favorite_user.id = :id")
    suspend fun deleteFavoriteUser(id: Int)
}