package com.cc.oem.modules.agent.dao;


import com.cc.oem.modules.agent.entity.CustDetailInfo;
import com.cc.oem.modules.agent.BaseOemMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface IdCardInfoMapper extends BaseOemMapper<CustDetailInfo, Integer> {

    CustDetailInfo queryOneByCreUserId(Long creUserId);

}
