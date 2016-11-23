package com.denny.retrodb.annotation

/**
 * Created by hasee on 2016/11/22.
 */
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class Select(val clounm: Array<String> = arrayOf("*"),val where: String)
