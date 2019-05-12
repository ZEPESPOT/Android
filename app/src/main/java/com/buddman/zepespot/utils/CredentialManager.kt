package com.buddman.zepespot.utils

/**
 * Created by Junseok Oh on 2017-07-18.
 */

import android.content.Context
import android.content.SharedPreferences
import com.buddman.zepespot.AppController


class CredentialManager private constructor() {
    private val preferences: SharedPreferences
    private val editor: SharedPreferences.Editor
    private val context: Context = AppController.context

    /* Credential Data */

    var zepetoId: String
        get() = preferences.getString("zepetoid", "")
        set(value) = editor.putString("zepetoid", value).apply()

    init {
        preferences = context.getSharedPreferences("zepeto", Context.MODE_PRIVATE)
        editor = preferences.edit()
    }

    fun save(key: String, data: String) {
        editor.putString(key, data)
        editor.apply()
    }

    fun removeAllData() {
        editor.clear()
        editor.apply()
    }

    fun getString(key: String): String {
        return preferences.getString(key, "")
    }

    fun getInt(key: String): Int {
        return preferences.getInt(key, 0)
    }

    fun getBoolean(key: String): Boolean {
        return preferences.getBoolean(key, false)
    }

    fun getLong(key: String): Long {
        return preferences.getLong(key, 0)
    }

    companion object {

        /* Data Keys */
        private var manager: CredentialManager? = null

        val instance: CredentialManager
            get() {
                if (manager == null) manager = CredentialManager()
                return manager as CredentialManager
            }
    }

}
