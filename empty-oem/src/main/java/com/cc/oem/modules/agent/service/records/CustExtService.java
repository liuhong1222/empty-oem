package com.cc.oem.modules.agent.service.records;


import com.cc.oem.common.utils.R;
import com.cc.oem.modules.agent.entity.BusinessLicenceInfo;
import com.cc.oem.modules.agent.model.param.AuthInfoParam;
import com.cc.oem.modules.agent.model.param.CustInfoParam;
import com.cc.oem.modules.agent.model.param.userManage.CustomerVerifyQueryParam;
import org.springframework.stereotype.Service;

/**
 *
 * @author wade
 */
@Service
public interface CustExtService {

    R getCustomerExtPageList(CustomerVerifyQueryParam param) throws Exception;

    R queryAuditingCustExtList(CustomerVerifyQueryParam param) throws Exception;
}
