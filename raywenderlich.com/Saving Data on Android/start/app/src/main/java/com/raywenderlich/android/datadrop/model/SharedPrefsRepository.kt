package com.raywenderlich.android.datadrop.model

import android.content.Context
import com.google.gson.Gson
import com.raywenderlich.android.datadrop.app.DataDropApplication

object SharedPrefsRepository : DropRepository {

    private const val SHARED_PREFS_REPOSITORY = "SHARED_PREFS_REPOSITORY"

    private val gson = Gson()

    private fun sharePrefs() = DataDropApplication.getAppContext().getSharedPreferences(
            SHARED_PREFS_REPOSITORY, Context.MODE_PRIVATE
    )

    override fun addDrop(drop: Drop) {
        sharePrefs().edit().putString(drop.id, gson.toJson(drop)).apply()
    }

    override fun getDrops(): List<Drop> {
        return sharePrefs().all.keys
                .map { sharePrefs().getString(it, "") }
                .filterNot { it.isNullOrBlank() }
                .map { gson.fromJson(it, Drop::class.java) }
    }

    override fun clearDrop(drop: Drop) {
        sharePrefs().edit().remove(drop.id).apply()
    }

    override fun clearAllDrops() {
        sharePrefs().edit().clear().apply()
    }
}