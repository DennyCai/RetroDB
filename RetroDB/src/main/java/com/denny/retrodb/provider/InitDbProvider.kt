package com.denny.retrodb.provider

import android.content.ContentProvider
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.util.Log

/**
 * Created by hasee on 2016/11/23.
 */
class InitDbProvider : ContentProvider() {

    companion object {

        private var CONTEXT: Context? = null

        fun getContext(): Context {
            return CONTEXT!!
        }
    }

    override fun insert(uri: Uri?, values: ContentValues?): Uri {
        throw UnsupportedOperationException("not implemented")
    }

    override fun query(uri: Uri?, projection: Array<out String>?, selection: String?, selectionArgs: Array<out String>?, sortOrder: String?): Cursor {
        throw UnsupportedOperationException("not implemented")
    }

    override fun onCreate(): Boolean {
        Log.e("Provider","init")
        CONTEXT = context.applicationContext
        return true
    }

    override fun update(uri: Uri?, values: ContentValues?, selection: String?, selectionArgs: Array<out String>?): Int {
        throw UnsupportedOperationException("not implemented")
    }

    override fun delete(uri: Uri?, selection: String?, selectionArgs: Array<out String>?): Int {
        throw UnsupportedOperationException("not implemented")
    }

    override fun getType(uri: Uri?): String {
        throw UnsupportedOperationException("not implemented")
    }
}