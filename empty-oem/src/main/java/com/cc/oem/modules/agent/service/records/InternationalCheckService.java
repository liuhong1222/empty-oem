package com.cc.oem.modules.agent.service.records;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cc.oem.common.utils.R;
import com.cc.oem.modules.agent.dao.CustInfoMapper;
import com.cc.oem.modules.agent.dao.records.InternationalCheckMapper;
import com.cc.oem.modules.agent.model.data.PageInfoWithTotalInfo;
import com.cc.oem.modules.agent.model.data.records.InternationalCheckQueryVo;
import com.cc.oem.modules.agent.model.param.records.InternationalQueryParam;
import com.github.pagehelper.PageHelper;

import lombok.extern.slf4j.Slf4j;

/**
 * 国际检测记录实现类
 * @author liuh
 * @date 2022年6月13日
 */
@Slf4j
@Service
public class InternationalCheckService {
	
	@Autowired
	private InternationalCheckMapper internationalCheckMapper;
	
	@Autowired
	private CustInfoMapper custInfoMapper;

	public R getInternationalPageList(InternationalQueryParam param) throws Exception {
		param.initTimeParam();
        if(StringUtils.isNotEmpty(param.getCustomerName())){
            List<Long> custList = custInfoMapper.selectCustIdListByCustName(param.getAgentId(),param.getCustomerName());
            if(custList.size()<1){
                return R.ok(new PageInfoWithTotalInfo<>(new ArrayList<>()));
            }
            param.setCustList(custList);
        }
        
        PageHelper.startPage(param.getCurrentPage(), param.getPageSize());
        //获取代理商列表

        //获取代理商列表
        List<InternationalCheckQueryVo> list = internationalCheckMapper.getInternationalCheckPageList(param);
        PageInfoWithTotalInfo<InternationalCheckQueryVo> pageInfo = new PageInfoWithTotalInfo<>(list);
        if(list.size()<1){
            return R.ok(pageInfo);
        }
        InternationalCheckQueryVo realtimeTotal = internationalCheckMapper.getInternationalTotal(param);
        Map map = new HashMap();
        map.put("totalSize",realtimeTotal.getTotalNumber());

        pageInfo.setTotalInfo(map);
        return R.ok(pageInfo);
    }
}
