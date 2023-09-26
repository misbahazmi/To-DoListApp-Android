package com.nytimes.utils

import android.util.Log
import com.misbah.todo.BuildConfig
import com.misbah.todo.ui.utils.Constants

/**
 * @author: MOHAMMAD MISBAH
 * @since: 16-Jul-2022
 * @sample: Technology Assessment for Android Role
 * Email Id: mohammadmisbahazmi@gmail.com
 * GitHub: https://github.com/misbahazmi
 * Expertise: Android||Java||Flutter
 */
object AppLog {

    private val APP_DEBUG = BuildConfig.DEBUG


    fun debugE(message: String) {
        if (APP_DEBUG) {
            Log.e(Constants.LOG_TAG, message)
        }
    }

    fun debugE(className: String, message: String) {
        if (APP_DEBUG) {
            Log.e(Constants.LOG_TAG, "$className  :  $message")
        }
    }


    fun debugV(message: String) {
        if (APP_DEBUG) {
            Log.v(Constants.LOG_TAG, message)
        }
    }

    fun debugV(className: String, message: String) {
        if (APP_DEBUG) {
            Log.v(Constants.LOG_TAG, "$className : $message")
        }
    }


    fun debugD(message: String) {
        if (APP_DEBUG) {
            Log.d(Constants.LOG_TAG, message)
        }
    }

    fun debugD(className: String, message: String) {
        if (APP_DEBUG) {
            Log.d(Constants.LOG_TAG, "$className : $message")
        }
    }

    fun debugI(message: String) {
        if (APP_DEBUG) {
            Log.i(Constants.LOG_TAG, message)
        }
    }

    fun debugI(className: String, message: String) {
        if (APP_DEBUG) {
            Log.i(Constants.LOG_TAG, "$className  :  $message")
        }
    }


    fun println(message: String) {
        if (APP_DEBUG) {
            println(Constants.LOG_TAG + message)
        }
    }

    fun loadStackTrace(e: Exception) {
        if (APP_DEBUG) {
            e.printStackTrace()
        }
    }


}
