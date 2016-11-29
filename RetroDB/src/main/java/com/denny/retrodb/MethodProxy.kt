package com.denny.retrodb

import android.database.Cursor
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.denny.retrodb.annotation.Field
import com.denny.retrodb.annotation.Select
import java.lang.reflect.Method
import java.util.*
import java.util.regex.Pattern
import kotlin.reflect.KClass

/**
 * Created by hasee on 2016/11/23.
 */
class MethodProxy(val helper: SQLiteOpenHelper, val table: String) {
    private val SELECT_ANNS:Set<KClass<*>> by lazy { hashSetOf(Field::class) }

    companion object {

        val REGEX_PARAMS = Pattern.compile("\\{([a-zA-Z][a-zA-Z0-9_-]*)\\}")
        fun create(helper: SQLiteOpenHelper, table: String): MethodProxy = MethodProxy(helper, table)
    }


    fun invokeSelect(method: Method, args: Array<out Any>?): Cursor {
        val select = method.getAnnotation(Select::class.java)
        val parAns = method.parameterAnnotations
        checkSelectAnns(method, parAns)

        val argsSet = paramsArgs(method.parameterAnnotations, args)
        val where = resloveSelection(select.where, argsSet)

        Log.i(MethodProxy::class.java.simpleName, "SELECT ${table} WHERE $where")

        return helper.readableDatabase.query(table, select.clounm, where, null, null, null, null)
    }

    private fun resloveSelection(where: String, argsSet: Set<Pair<String, String>>): String? {
        if (argsSet == null)
            return where
        var temp = where
        for (arg in argsSet) {
            temp = temp.format(arg)
        }
        return temp
    }

    private fun paramsArgs(parameterAnnotations: Array<out Array<Annotation>>?, args: Array<out Any>?): Set<Pair<String, String>> {
        if (parameterAnnotations == null) {
            return emptySet()
        }
        val set = HashSet<Pair<String, String>>()
        for (i in parameterAnnotations.indices) {
            val field = parameterAnnotations[i][0] as Field
            set.add(field.value to "\'${args!![i].toString().toSafeString()}\'")
        }
        return set
    }


    private fun checkSelectAnns(method: Method, parAns: Array<out Array<Annotation>>?) {
        if (parAns == null)
            return
        for (an in parAns) {
            for (a in an) {
                if (SELECT_ANNS.contains(a.annotationClass).not()) {
                    throw IllegalArgumentException("""
                    interface ${method.declaringClass.canonicalName}
                       method ${method.name} should not declaring '${a.javaClass}' annotation
                    """)
                }
            }
        }
    }
}

