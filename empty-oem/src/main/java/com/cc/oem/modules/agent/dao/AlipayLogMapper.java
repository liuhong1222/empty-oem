package com.cc.oem.modules.agent.dao;

import com.cc.oem.modules.agent.entity.AlipayLog;
import com.cc.oem.modules.agent.BaseOemMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface AlipayLogMapper extends BaseOemMapper<AlipayLog, Long> {

}