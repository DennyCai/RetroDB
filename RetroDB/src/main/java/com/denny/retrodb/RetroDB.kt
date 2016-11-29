package com.denny.retrodb

import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.denny.retrodb.annotation.Table
import com.denny.retrodb.provider.InitDbProvider
import java.lang.reflect.Proxy

/**
 * Created by hasee on 2016/11/22.
 */

class RetroDB private constructor(val sqlHelper:SQLiteOpenHelper){

    class Builder(){
        private var sqlHelper:SQLiteOpenHelper? = null
        fun setSQLiteHelper(helper:SQLiteOpenHelper):Builder{
            sqlHelper = helper!!
            return this
        }

        fun build():RetroDB{
            return RetroDB(sqlHelper!!)
        }

    }

    fun <T> create(claz:Class<T>): T?{
        if(claz.isInterface){
            try {
                val tableName = getTableName(claz);
                return Proxy.newProxyInstance(claz.classLoader,
                        arrayOf(claz) ,
                        CurdHandler(sqlHelper,tableName)) as T;
            }catch (e : Exception){
                e.printStackTrace()
                return null
            }
        }
        return null
    }

    private fun getTableName(claz: Class<*>): String {
        var name : String = ""
        if(claz.isAnnotationPresent(Table::class.java)){
            val table = claz.getAnnotation(Table::class.java)
            if(table.name==null||table.name.isEmpty()){
                throw IllegalArgumentException("${claz.name} declared Annotation ${Table::class.java.name} name should be empty or not declared")
            }
            name = table.name;
            checkTableName(claz,table,name)
        }else{
            name = claz.simpleName
            checkTableName(claz,null,name)
        }
        return name
    }

    private fun checkTableName(claz: Class<*>, table: Table?, name: String) {
        if("[a-z,A-Z,_]*".toRegex().matches(name).not()){
            if(table!=null)
                throw IllegalArgumentException("${claz.name} declared Annotation ${Table::class.java} name property should be include regex [a-z,A-Z,_]")
            else
                throw IllegalArgumentException("${claz.name} name should be include regex [a-z,A-Z,_]")
        }
    }
}
