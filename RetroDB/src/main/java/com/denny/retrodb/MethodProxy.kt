package com.denny.retrodb

import android.database.Cursor
import android.database.sqlite.SQLiteOpenHelper
import com.denny.retrodb.annotation.Field
import com.denny.retrodb.annotation.Select
import java.lang.reflect.Method
import java.util.*

/**
 * Created by hasee on 2016/11/23.
 */
class MethodProxy private constructor(val helper:SQLiteOpenHelper,val table:String){

    companion object {

        internal val SELECT_ANNS : Set<Class<*>>
        init {
            SELECT_ANNS = setOf(Field::class.java)
        }

        fun create(helper:SQLiteOpenHelper,table: String): MethodProxy {
            return MethodProxy(helper,table)
        }
    }


    fun invokeSelect(method: Method,args : Array<out Any>?): Cursor {
        val select = method.getAnnotation(Select::class.java)
        val columns = select.clounm.joinToString(",")
        val parAns = method.parameterAnnotations
        checkSelectAnns(method,parAns)

        var sql = "SELECT ${columns} FROM ${table} WHERE "
    }

    private fun checkSelectAnns(method: Method,parAns: Array<out Array<Annotation>>?) {
        if(parAns==null)
            return
        for(an in parAns){
            for (a in an){
                if(SELECT_ANNS.contains(a.javaClass).not()){
                    throw IllegalArgumentException("""
                    interface ${method.declaringClass.canonicalName}
                       method ${method.name} should not declaring '${a.javaClass}' annotation
                    """)
                }
            }
        }
    }
}