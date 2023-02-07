package com.cc.oem.modules.agent;


/**
 * @author zhangx
 */
public interface BaseOemMapper<T,U> {
    int deleteByPrimaryKey(U id);

    int insert(T record);

    int insertSelective(T record);

    T selectByPrimaryKey(U id);

    int updateByPrimaryKeySelective(T record);

    int updateByPrimaryKey(T record);


}
