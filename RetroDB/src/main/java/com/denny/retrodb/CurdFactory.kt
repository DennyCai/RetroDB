package com.denny.retrodb

import com.denny.retrodb.annotation.Delete
import com.denny.retrodb.annotation.Insert
import com.denny.retrodb.annotation.Select
import com.denny.retrodb.annotation.Update
import com.denny.retrodb.invoker.Invoker
import com.denny.retrodb.invoker.SelectInvoker
import java.lang.reflect.Method
import java.util.concurrent.ConcurrentHashMap

/**
 * Created by hasee on 2016/12/19.
 */
class CurdFactory {
    companion object{
        var METHOD_CACHE:ConcurrentHashMap<Method, Invoker> = ConcurrentHashMap()
        fun load(method: Method): Invoker? = if(METHOD_CACHE.get(method)!=null){
            METHOD_CACHE.get(method)
        }else{
            for (ann in method.declaredAnnotations){
                var invoker = when(ann){
                    is Select -> SelectInvoker()
                    is Update -> UpdateInvoker()
                    is Delete -> DeleteInvoker()
                    is Insert -> InsertInvoker()
                    else -> null
                }
                if(invoker!=null) {
                    METHOD_CACHE.put(method, invoker)
                }
                invoker
            }
            null
        }


    }
}