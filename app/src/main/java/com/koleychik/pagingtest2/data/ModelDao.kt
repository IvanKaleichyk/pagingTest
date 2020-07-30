package com.koleychik.pagingtest2.data

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.koleychik.pagingtest2.model.Model

@Dao
interface ModelDao {

    @Query("SELECT * FROM Model")
    fun getAll() : PagingSource<Int, Model>

    @Insert
    fun insert(model: Model)

    @Delete
    fun delete(model: Model)
}