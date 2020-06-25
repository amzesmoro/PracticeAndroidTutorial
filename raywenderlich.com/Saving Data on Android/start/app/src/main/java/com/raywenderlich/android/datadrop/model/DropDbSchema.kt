package com.raywenderlich.android.datadrop.model

object DropDbSchema {
    const val VERSION = 2
    const val DB_NAME = "drops.db"

    object DropTable {
        const val NAME = "drops"

        object Columns {
            const val ID = "id"
            const val LATITUDE = "latitude"
            const val LONGITUDE = "longitude"
            const val DROP_MESSAGE = "drop_message"
            const val MARKER_COLOR = "markerColor"
        }
    }
}