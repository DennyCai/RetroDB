package com.denny.retrodb.annotation

/**
 * Created by hasee on 2016/11/24.
 */
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FIELD)
annotation class Field(val key:String)