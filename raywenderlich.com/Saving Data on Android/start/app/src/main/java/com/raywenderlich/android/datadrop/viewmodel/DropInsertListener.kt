package com.raywenderlich.android.datadrop.viewmodel

import com.raywenderlich.android.datadrop.model.Drop

interface DropInsertListener {
    fun dropInserted(drop: Drop)
}