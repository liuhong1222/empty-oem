package com.cc.oem.modules.agent.service;


import com.cc.oem.common.utils.R;
import com.cc.oem.modules.agent.entity.BusinessLicenceInfo;
import com.cc.oem.modules.agent.model.param.AuthInfoParam;
import com.cc.oem.modules.agent.model.param.CustInfoParam;
import com.cc.oem.modules.agent.model.param.userManage.CreateCustParam;
import com.cc.oem.modules.agent.model.param.userManage.CustomerVerifyQueryParam;
import com.cc.oem.modules.job.entity.task.StatisticsDate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * copyright (C), 2018-2018, 创蓝253
 *
 * @author zhangx
 * @fileName ManagerService
 * @date 2018/5/18 19:27
 * @description
 */
@Service
public interface CustInfoService {

    R custList(CustInfoParam param,boolean isExport);

    R findPersonCustById(Long customerId);

    void generateDailyCustStatistic(StatisticsDate statisticsDate);

    void generateDailyCusEmptyConsume(StatisticsDate statisticsDate);

    R getCustInfo(Long custId);

    R presentNum(Long custId,Long agentId);

    R setApiState(Long custId,Integer state);

    R setAuthenLevel(Long agentId, Long custId, Integer authenLevel);


    R addCustOfAgent(CreateCustParam param);

}
