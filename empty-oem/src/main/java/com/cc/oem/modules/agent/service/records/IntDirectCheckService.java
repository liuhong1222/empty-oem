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
import com.cc.oem.modules.agent.dao.records.IntDirectCheckMapper;
import com.cc.oem.modules.agent.model.data.PageInfoWithTotalInfo;
import com.cc.oem.modules.agent.model.data.records.IntDirectCheckQueryVo;
import com.cc.oem.modules.agent.model.param.records.IntDirectQueryParam;
import com.github.pagehelper.PageHelper;

import lombok.extern.slf4j.Slf4j;

/**
 * 国际定向检测实现类
 * @author liuh
 * @date 2022年10月19日
 */
@Slf4j
@Service
public class IntDirectCheckService {
	
	@Autowired
	private IntDirectCheckMapper intDirectCheckMapper;
	
	@Autowired
	private CustInfoMapper custInfoMapper;

	public R getIntDirectPageList(IntDirectQueryParam param) throws Exception {
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
        List<IntDirectCheckQueryVo> list = intDirectCheckMapper.getIntDirectCheckPageList(param);
        PageInfoWithTotalInfo<IntDirectCheckQueryVo> pageInfo = new PageInfoWithTotalInfo<>(list);
        if(list.size()<1){
            return R.ok(pageInfo);
        }
        IntDirectCheckQueryVo realtimeTotal = intDirectCheckMapper.getIntDirectCheckTotal(param);
        Map map = new HashMap();
        map.put("totalSize",realtimeTotal.getTotalNumber());

        pageInfo.setTotalInfo(map);
        return R.ok(pageInfo);
    }
}
