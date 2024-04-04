package com.gedeputu.subsmissionakhir.Detail.Favorite

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.gedeputu.subsmissionakhir.data.Database.UserDatabase
import com.gedeputu.subsmissionakhir.data.Database.UserFavorite
import com.gedeputu.subsmissionakhir.data.Database.UserFavoriteDao


class FavoriteViewModel (application: Application): AndroidViewModel(application) {

    private var userDao: UserFavoriteDao?
    private var userDatabase: UserDatabase?

    init {
        userDatabase = UserDatabase.getDatabase(application)
        userDao = userDatabase?.userFavoriteDao()
    }

    fun getFavoriteUser(): LiveData<List<UserFavorite>>? {
        return userDao?.getFavoriteUser()
    }

}