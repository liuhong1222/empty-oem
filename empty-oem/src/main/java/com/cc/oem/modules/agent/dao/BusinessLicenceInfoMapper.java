package com.cc.oem.modules.agent.dao;


import com.cc.oem.modules.agent.entity.BusinessLicenceInfo;
import com.cc.oem.modules.agent.model.param.AuthInfoParam;
import com.cc.oem.modules.agent.BaseOemMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
@Mapper
public interface BusinessLicenceInfoMapper extends BaseOemMapper<BusinessLicenceInfo, Long> {

    /**
     * 根据用户id获取公司信息
     */
    BusinessLicenceInfo selectByUserId(Long userId);

    int updateByIdAndCreUserIdSelective(BusinessLicenceInfo info);

    int updateByPrimaryKeyWithBLOBs(BusinessLicenceInfo info);

    BusinessLicenceInfo queryOneByCreUserId(Long creUserId);


}
