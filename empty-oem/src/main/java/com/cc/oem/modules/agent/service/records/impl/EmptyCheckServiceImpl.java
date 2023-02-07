package com.cc.oem.modules.agent.service.records.impl;

import com.cc.oem.common.utils.R;
import com.cc.oem.modules.agent.dao.CustInfoMapper;
import com.cc.oem.modules.agent.dao.records.EmptyCheckMapper;
import com.cc.oem.modules.agent.model.data.PageInfoWithTotalInfo;
import com.cc.oem.modules.agent.model.data.records.EmptyCheckQueryVo;
import com.cc.oem.modules.agent.model.param.records.EmptyCheckQueryParam;
import com.cc.oem.modules.agent.service.records.EmptyCheckService;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <pre>
 * 空号检测记录 服务实现类
 * </pre>
 *
 * @author rivers
 * @since 2020-03-03
 */
@Slf4j
@Service
public class EmptyCheckServiceImpl implements EmptyCheckService {

//    @Value("${interface.mode}")
    private String interfaceMode ="chuanglan";

    /**
     * 一次性检测号码最小数量
     */
    private static final int MIN_CHECK_NUM = 3000;

    /**
     * 一次性检测号码最大数量
     */
    private static final int MAX_CHECK_NUM = 200_0000;


    @Autowired
    private EmptyCheckMapper emptyCheckMapper;
    @Autowired
    private CustInfoMapper custInfoMapper;

    @Override
    public EmptyCheckQueryVo getEmptyCheckById(Serializable id) throws Exception {
        return emptyCheckMapper.getEmptyCheckById(id);
    }

    /**
     * 在线检测的列表显示最终成功或者定期删除完成的记录，即状态为9，11
     * 其次，其与API检测的区别在于API检测的file_url字段为空
     * @param param
     * @return
     * @throws Exception
     */
    @Override
    public R getEmptyCheckPageList(EmptyCheckQueryParam param) throws Exception {
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
        List<EmptyCheckQueryVo> emptyCheckPageList = emptyCheckMapper.getEmptyCheckPageList(param);
        PageInfoWithTotalInfo<EmptyCheckQueryVo> pageInfo = new PageInfoWithTotalInfo<>(emptyCheckPageList);
        if(emptyCheckPageList.size()<1){
            return R.ok(pageInfo);
        }
        pageInfo.setTotalInfo(getTotalInfo(param));
        return R.ok(pageInfo);
    }

    @Override
    public R getEmptyApiList(EmptyCheckQueryParam param) {
        param.initTimeParam();
        if(StringUtils.isNotEmpty(param.getCustomerName())){
            List<Long> custList = custInfoMapper.selectCustIdListByCustName(param.getAgentId(),param.getCustomerName());
            if(custList.size()<1){
                return R.ok(new PageInfoWithTotalInfo<>(new ArrayList<>()));
            }
            param.setCustList(custList);
        }
        PageHelper.startPage(param.getCurrentPage(), param.getPageSize());
        //getEmptyCheckApiList_COUNT
//        Long total = emptyCheckMapper.getEmptyCheckApiList_COUNT(param);
//        if(total<1){
//            return R.ok(new PageInfo<>());
//        }
        //获取代理商列表
        List<EmptyCheckQueryVo> emptyCheckPageList = emptyCheckMapper.getEmptyCheckApiList(param);
        PageInfoWithTotalInfo<EmptyCheckQueryVo> pageInfo = new PageInfoWithTotalInfo<>(emptyCheckPageList);
        if(emptyCheckPageList.size()<1){
            return R.ok(pageInfo);
        }
        EmptyCheckQueryVo emptyTotal = emptyCheckMapper.getEmptyApiTotal(param);
        Map map = new HashMap();
        map.put("lineTotal",emptyTotal.getLine());
        map.put("totalSize",emptyTotal.getTotalNumber());

        pageInfo.setTotalInfo(map);
        return R.ok(pageInfo);
    }

    /**
     * 获取合计信息
     * @return
     */
    private Map getTotalInfo(EmptyCheckQueryParam param){
        EmptyCheckQueryVo emptyTotal = emptyCheckMapper.getEmptyTotal(param);
        Map map = new HashMap();
        map.put("lineTotal",0);
        map.put("totalSize",emptyTotal.getTotalNumber());
        return map;
    }

    @Override
    public EmptyCheckQueryVo getEmptyCheckApiDetailById(Long id) {
        return emptyCheckMapper.getEmptyCheckById(id);
    }


}
