package com.denny.retrodb

import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.denny.retrodb.provider.InitDbProvider
import java.lang.reflect.Proxy

/**
 * Created by hasee on 2016/11/22.
 */

class RetroDB private constructor(val dbName:String,val version:Int){
    private var sqlitDb: SQLiteOpenHelper
    init{
        sqlitDb = object : SQLiteOpenHelper(InitDbProvider.getContext(),dbName,null,version) {
            override fun onCreate(db: SQLiteDatabase?) {
                throw UnsupportedOperationException("not implemented")
            }

            override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
                throw UnsupportedOperationException("not implemented")
            }

        }
    }



    class Builder(){
        private var db:String = null!!
            set
        private var version:Int = 0
            set

        fun setName(dbname:String):Builder{
            db = dbname
            return this
        }

        fun setVersion(version:Int):Builder{
            this.version = version
            return this
        }

        fun build():RetroDB{
            return RetroDB(db,version)
        }

    }

    fun <T> create(claz:Class<T>): T?{
        if(claz.isInterface){
            try {
                return Proxy.newProxyInstance(claz.classLoader,
                        arrayOf(claz) ,
                        CurdHandler(sqlitDb)) as T;
            }catch (e : Exception){
                e.printStackTrace()
                return null
            }
        }
        return null
    }
}
