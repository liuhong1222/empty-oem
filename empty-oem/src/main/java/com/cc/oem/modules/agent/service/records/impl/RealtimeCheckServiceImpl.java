package com.cc.oem.modules.agent.service.records.impl;

import com.cc.oem.common.utils.R;
import com.cc.oem.modules.agent.dao.CustInfoMapper;
import com.cc.oem.modules.agent.dao.records.RealtimeCheckMapper;
import com.cc.oem.modules.agent.model.data.PageInfoWithTotalInfo;
import com.cc.oem.modules.agent.model.data.records.EmptyCheckQueryVo;
import com.cc.oem.modules.agent.model.data.records.RealtimeCheckQueryVo;
import com.cc.oem.modules.agent.model.param.records.RealtimeCheckQueryParam;
import com.cc.oem.modules.agent.service.records.RealtimeCheckService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;


/**
 * <pre>
 * 实时检测记录 服务实现类
 * </pre>
 *
 */
@Slf4j
@Service
public class RealtimeCheckServiceImpl implements RealtimeCheckService {
    @Autowired
    private RealtimeCheckMapper realtimeCheckMapper;
    @Autowired
    private CustInfoMapper custInfoMapper;



    @Override
    public RealtimeCheckQueryVo getRealtimeCheckById(Serializable id) throws Exception {
        return realtimeCheckMapper.getRealtimeCheckById(id);
    }


    @Override
    public R getRealtimeCheckPageList(RealtimeCheckQueryParam realtimeCheckQueryParam) throws Exception {
        realtimeCheckQueryParam.initTimeParam();
        if(StringUtils.isNotEmpty(realtimeCheckQueryParam.getCustomerName())){
            List<Long> custList = custInfoMapper.selectCustIdListByCustName(realtimeCheckQueryParam.getAgentId(),realtimeCheckQueryParam.getCustomerName());
            if(custList.size()<1){
                return R.ok(new PageInfoWithTotalInfo<>(new ArrayList<>()));
            }
            realtimeCheckQueryParam.setCustList(custList);
        }
        PageHelper.startPage(realtimeCheckQueryParam.getCurrentPage(), realtimeCheckQueryParam.getPageSize());
        //获取代理商列表

        //获取代理商列表
        List<RealtimeCheckQueryVo> list = realtimeCheckMapper.getRealtimeCheckPageList(realtimeCheckQueryParam);
        PageInfoWithTotalInfo<RealtimeCheckQueryVo> pageInfo = new PageInfoWithTotalInfo<>(list);
        if(list.size()<1){
            return R.ok(pageInfo);
        }
        RealtimeCheckQueryVo realtimeTotal = realtimeCheckMapper.getRealtimeTotal(realtimeCheckQueryParam);
        Map map = new HashMap();
        map.put("totalSize",realtimeTotal.getTotalNumber());

        pageInfo.setTotalInfo(map);
        return R.ok(pageInfo);
    }

    @Override
    public RealtimeCheckQueryVo getRealtimeApiCheck(Long id) {
        return realtimeCheckMapper.getRealtimeCheckById(id);
    }

    @Override
    public R getRealtimeCheckApiList(RealtimeCheckQueryParam realtimeCheckQueryParam) {
        realtimeCheckQueryParam.initTimeParam();
        if(StringUtils.isNotEmpty(realtimeCheckQueryParam.getCustomerName())){
            List<Long> custList = custInfoMapper.selectCustIdListByCustName(realtimeCheckQueryParam.getAgentId(),realtimeCheckQueryParam.getCustomerName());
            if(custList.size()<1){
                return R.ok(new PageInfoWithTotalInfo<>(new ArrayList<>()));
            }
            realtimeCheckQueryParam.setCustList(custList);
        }
        PageHelper.startPage(realtimeCheckQueryParam.getCurrentPage(), realtimeCheckQueryParam.getPageSize());
        //获取代理商列表
        List<RealtimeCheckQueryVo> list = realtimeCheckMapper.getRealtimeCheckApiList(realtimeCheckQueryParam);
        PageInfoWithTotalInfo<RealtimeCheckQueryVo> pageInfo = new PageInfoWithTotalInfo<>(list);
        if(list.size()<1){
            return R.ok(pageInfo);
        }
        RealtimeCheckQueryVo realtimeTotal = realtimeCheckMapper.getRealtimeApiTotal(realtimeCheckQueryParam);
        Map map = new HashMap();
        map.put("totalSize",realtimeTotal.getTotalNumber());
        pageInfo.setTotalInfo(map);
        return R.ok(pageInfo);
    }


}
