package com.settowally.settowally.common

import com.settowally.settowally.BuildConfig.SETTOWALLY_API_KEY

class Constant {

    companion object {
        /**     REMOTE      */
        const val API_KEY = SETTOWALLY_API_KEY
        const val BASE_URL = "https://api.pexels.com/v1/"
        const val PER_PAGE_PHOTO_COUNTER = 20
        const val PREFERENCES_NAME="settowally_preferences"
        const val PREFERENCE_DARK_MODE_SETTING="OS"
        const val PREFERENCE_QUALITY_SETTING="Medium"

    }
}