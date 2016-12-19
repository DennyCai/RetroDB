package com.denny.retrodb

/**
 * Created by hasee on 2016/12/20.
 */
interface DatabseSource<Q,I,U,D> {
    fun query(table:String,where:String):Q
    fun insert(table: String,values:String):I
    fun delete(table:String,where:String):D
    fun update(table:String,where: String):U
}