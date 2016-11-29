package com.denny.retrodb

import android.database.sqlite.SQLiteOpenHelper
import com.denny.retrodb.annotation.Delete
import com.denny.retrodb.annotation.Insert
import com.denny.retrodb.annotation.Select
import com.denny.retrodb.annotation.Update
import java.lang.reflect.InvocationHandler
import java.lang.reflect.Method

/**
 * Created by hasee on 2016/11/23.
 */
class CurdHandler(val sqldb: SQLiteOpenHelper, val tableName: String) : InvocationHandler{
    val methodService:MethodProxy by lazy {MethodProxy(sqldb,tableName)}

    override fun invoke(proxy: Any?, method: Method, args: Array<out Any>?): Any {
        if(method.isAnnotationPresent(Select::class.java)!!){
            return methodService.invokeSelect(method, args)
        }/*else if(method.isAnnotationPresent(Delete::class.java)!!){
            return null
        }else if(method.isAnnotationPresent(Update::class.java)!!){
            return null
        }else if(method.isAnnotationPresent(Insert::class.java)!!){

        }*/
        return Object()
    }
}