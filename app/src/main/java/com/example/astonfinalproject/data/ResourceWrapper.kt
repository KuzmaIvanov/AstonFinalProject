package com.example.astonfinalproject.data

import android.content.Context
import javax.inject.Inject
import javax.inject.Singleton

private const val PAGES_SHARED_PREFERENCES = "pages_shared_preferences"

@Singleton
class ResourceWrapper @Inject constructor(
    private val context: Context
) {

    fun savePageIntoSharedPreferences(
        pageKey: String,
        pageNumber: Int
    ) {
        val sharedPref = context.getSharedPreferences(PAGES_SHARED_PREFERENCES, Context.MODE_PRIVATE)
        with(sharedPref.edit()) {
            putInt(pageKey, pageNumber)
            apply()
        }
    }

    fun getPageFromSharedPreferences(
        pageKey: String
    ): Int {
        val sharedPref = context.getSharedPreferences(PAGES_SHARED_PREFERENCES, Context.MODE_PRIVATE)
        return sharedPref.getInt(pageKey, 1)
    }
}