package com.my_framework.www.db.orm;


/**
 * todo
 * @author _qiu
 */
public interface BaseMapper<E> {

    default void insert(E entity){
    }

}
