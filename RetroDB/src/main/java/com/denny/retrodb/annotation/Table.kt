package com.denny.retrodb.annotation

/**
 * Created by hasee on 2016/11/24.
 */
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class Table(val name:String="")