package com.koleychik.pagingtest2.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.koleychik.pagingtest2.data.Db
import com.koleychik.pagingtest2.model.Model
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class MainViewModel(application: Application) : AndroidViewModel(application) {

    val dao = Db.get(application).ModelDao()

    val allName = Pager(PagingConfig(pageSize = 50, enablePlaceholders = true, maxSize = 200)){
        dao.getAll()
    }.flow

    suspend fun insert(model : Model){
        dao.insert(model)
    }

    fun delete(model: Model) = CoroutineScope(Dispatchers.IO).launch{
        dao.delete(model)
    }

}