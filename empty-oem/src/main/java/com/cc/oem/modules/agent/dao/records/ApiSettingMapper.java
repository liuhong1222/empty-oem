package com.cc.oem.modules.agent.dao.records;

import com.cc.oem.modules.agent.BaseOemMapper;
import com.cc.oem.modules.agent.entity.AgentRecharge;
import com.cc.oem.modules.agent.entity.CustInfo;
import com.cc.oem.modules.agent.entity.records.AgentRefund;
import com.cc.oem.modules.agent.entity.records.ApiSettings;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface ApiSettingMapper {

    @Select("select app_id from api_settings where customer_id =#{custId} ")
    String queryAppIdByCustId(Long custId);

    @Insert("insert into api_settings (customer_id, app_id, app_key, remark, state, version, create_time, update_time) values " +
            "(#{customerId},#{appId}, #{appKey}, #{remark}, #{state}, #{version}, now(), now())")
    int saveApiSetting(ApiSettings apiSettings);

    @Select("select * from api_settings where app_id = #{appId} ")
    ApiSettings findByAppId(String appId);

    @Select("select * from api_settings where app_key = #{appKey} ")
    ApiSettings findByAppKey(String appKey);
}
