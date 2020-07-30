package com.koleychik.pagingtest2.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.koleychik.pagingtest2.model.Model

@Database(entities = [Model::class], version = 1)
abstract class Db : RoomDatabase() {

    abstract fun ModelDao(): ModelDao

    companion object {
        private var instance: Db? = null
        @Synchronized
        fun get(context: Context): Db {
            if (instance == null) {
                instance = Room.databaseBuilder(context.applicationContext,
                    Db::class.java, "CheeseDatabase")
//                    .addCallback(object : RoomDatabase.Callback() {
//                        override fun onCreate(db: SupportSQLiteDatabase) {
//                            fillInDb(context.applicationContext)
//                        }})
                    .build()
            }
            return instance!!
        }

}

}