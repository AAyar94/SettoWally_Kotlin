package com.settowally.settowally.common

import com.settowally.settowally.BuildConfig.SETTOWALLY_API_KEY
import com.settowally.settowally.data.model.PhotoQuality

class Constant {

    companion object {
        /**     REMOTE      */
        const val API_KEY = SETTOWALLY_API_KEY
        const val BASE_URL = "https://api.pexels.com/v1/"
        const val PER_PAGE_PHOTO_COUNTER = 20
        const val PREFERENCES_NAME = "settowally_preferences"
        const val PREFERENCE_DARK_MODE_SETTING = "OS"
        const val PREFERENCE_QUALITY_SETTING = "medium"
        const val FAVORITE_PHOTOS_TABLE_NAME = "favorite_photos"
        const val PHOTOS_SRC_TABLE_NAME = "photos_src"
        const val DATABASE_NAME = "Saved_photos"
        const val BACK_ONLINE= "back_online"

    }
}