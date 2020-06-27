package com.raywenderlich.android.datadrop.model

import android.arch.lifecycle.LiveData
import android.os.AsyncTask
import com.raywenderlich.android.datadrop.app.DataDropApplication

class RoomRepository : DropRepository {

    private val dropDao: DropDao = DataDropApplication.database.dropDao()
    private val allDrops: LiveData<List<Drop>>

    init {
        allDrops = dropDao.getAllDrops()
    }

    override fun addDrop(drop: Drop) {
        InsertAsynctask(dropDao).execute(drop)
    }

    override fun getDrops() = allDrops

    override fun clearDrop(drop: Drop) {
        DeleteAsyncTask(dropDao).execute(drop)
    }

    override fun clearAllDrops() {
        val dropArray = allDrops.value?.toTypedArray()
        if (dropArray != null) {
            DeleteAsyncTask(dropDao).execute(*dropArray)
        }
    }

    private class InsertAsynctask internal constructor(private val dao: DropDao) : AsyncTask<Drop, Void, Void>() {
        override fun doInBackground(vararg params: Drop): Void? {
            dao.insert(params[0])
            return null
        }
    }

    private class DeleteAsyncTask internal constructor(private val dao: DropDao) : AsyncTask<Drop, Void, Void>() {
        override fun doInBackground(vararg params: Drop): Void? {
            dao.delete(*params)
            return null
        }
    }
}