package com.cc.oem.modules.agent.service.impl;


import com.cc.oem.common.annotation.SysLog;
import com.cc.oem.common.exception.RRException;
import com.cc.oem.common.utils.R;
import com.cc.oem.modules.agent.constants.AgentConstant;
import com.cc.oem.modules.agent.dao.CreUserMapper;
import com.cc.oem.modules.agent.dao.ProductFaqMapper;
import com.cc.oem.modules.agent.entity.ProductFaq;
import com.cc.oem.modules.agent.model.data.FaqInfoData;
import com.cc.oem.modules.agent.model.data.FaqInfoWithUpdateData;
import com.cc.oem.modules.agent.model.data.ProductInfoWithOrders;
import com.cc.oem.modules.agent.model.param.*;
import com.cc.oem.modules.agent.service.ProductFaqService;
import com.cc.oem.modules.agent.util.SpringBeanUtil;
import com.cc.oem.modules.config.utils.Snowflake;
import com.cc.oem.modules.sys.service.SysUserService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author liuh
 * @since 2019/03/25
 */

@Service
public class ProductFaqServiceImpl implements ProductFaqService {

    final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    CreUserMapper creUserMapper;
    @Autowired
    ProductFaqMapper productFaqMapper;
    @Autowired
    SysUserService sysUserService;
    @Autowired
    private Snowflake snowflake;

    private final static String NON_AGENT_ROLE ="该用户不是代理商";

    private final static String NON_PRODUCT_FAQ_ID ="常见问题Id不能为空";

    /**
     * 代理商常见问题列表
     */
    @SuppressWarnings("unused")
    @Override
    public R productFaqAllList(ProductFaqParam param) {
        param.appendTimeString();
        PageHelper.startPage(param.getCurrentPage(), param.getPageSize());
        List<FaqInfoData> list = productFaqMapper.productFaqAllList(param);
        PageInfo<FaqInfoData> pageInfo = new PageInfo<>(list);
        return R.ok(pageInfo);
    }

    /**
     * 审核代理商常见问题
     */
    @Override
    @SysLog
    @Transactional(rollbackFor = {Throwable.class})
    public R productFaqAllAudit(FaqAuditParam param, Long sysUserId) {
        //判断审核状态
        if (param.getAuditState() != AgentConstant.AGENT_FAQ_STATUS_SUCCESS &&
                param.getAuditState() != AgentConstant.AGENT_FAQ_STATUS_ADD_REFUSED ) {
            return R.error("非法操作，该条记录已处理");
        }

        ProductFaq record = new ProductFaq();
        record.setId(param.getProductFaqId());
        record.setApplyState(param.getAuditState());
        record.setRemark(param.getAuditRemark());
        //更新审核状态
        int update = productFaqMapper.updateByPrimaryKeySelective(record);
        if (update != 1) {
            throw new RRException("审核失败，记录不存在或已删除");
        }
        return R.ok();
    }

    /**
     * 查看代理商常见问题详情
     */
    @Override
    public R productFaqAllDetail(String productFaqId) {
        return productFaqAllDetail(productFaqId, null);
    }

    /**
     * 查看代理商常见问题详情
     */
    private R productFaqAllDetail(String productFaqId, Long agentId) {
        FaqDetailParam param = new FaqDetailParam(productFaqId,null);
        //查询问题详情
        FaqInfoData faqInfoData = productFaqMapper.getProductFaqInfoById(param);
        if (faqInfoData == null) {
            return R.error("查看失败，该记录不存在或已被删除");
        }
        return R.ok(faqInfoData);
    }

    /**
     * 查询我的常见问题列表
     */
    @Override
    public R productFaqMyList(ProductFaqParam param, Long agentId) {
        Assert.notNull(agentId, NON_AGENT_ROLE);
        param.setAgentId(agentId);
        param.appendTimeString();
        PageHelper.startPage(param.getCurrentPage(), param.getPageSize());
        List<FaqInfoData> list = productFaqMapper.productFaqAllList(param);
        PageInfo<FaqInfoData> pageInfo = new PageInfo<>(list);
        return R.ok(pageInfo);
    }

    /**
     * 发布我的常见问题
     */
    @Override
    @SysLog
    @Transactional(rollbackFor = {Throwable.class})
    public R productFaqMySave(ProductFaqSaveParam param,Long agentId) {
        Assert.notNull(agentId, NON_AGENT_ROLE);
        if(productFaqMapper.IsExistFaqInfoByFaq(new IsExistFaqParam(param.getQuestion(),
                param.getAnswer(), agentId, null)) > 0) {
            return R.error("问题标题或回答内容重复了，请重新输入");
        }
        ProductFaq productFaq = new ProductFaq();
        productFaq.setAgentId(agentId);
        productFaq.setProductId(param.getProductId());
        productFaq.setTitle(param.getQuestion());
        productFaq.setContent(param.getAnswer());
        productFaq.setState(param.getStatus());
        productFaq.setSort(param.getOrder());
        productFaq.setApplyState(AgentConstant.AGENT_FAQ_STATUS_ADD_TODO);
        productFaq.setId(snowflake.nextId());
        int count = productFaqMapper.insertSelective(productFaq);
        return count==1?R.ok():R.error("新增常见问题失败，数据库异常");
    }

    /**
     * 修改我的常见问题
     */
    @Override
    @SysLog
    @Transactional(rollbackFor = {Throwable.class})
    public R productFaqMyUpdate(ProductFaqSaveParam param, Long sysUserId, Long agentId) {
        Assert.notNull(agentId, NON_AGENT_ROLE);
        Assert.notNull(param.getId(), NON_PRODUCT_FAQ_ID);
        if(productFaqMapper.IsExistFaqInfoByFaq(new IsExistFaqParam(param.getQuestion(),
                param.getAnswer(), agentId, param.getId().toString())) > 0) {
            return R.error("问题标题或回答内容重复了，请重新输入");
        }
        ProductFaq productFaq = new ProductFaq();
        productFaq.setId(param.getId());
        productFaq.setProductId(param.getProductId());
        productFaq.setTitle(param.getQuestion());
        productFaq.setContent(param.getAnswer());
        productFaq.setState(param.getStatus());
        productFaq.setSort(param.getOrder());
        productFaq.setApplyState(AgentConstant.AGENT_FAQ_STATUS_UPDATE_TODO);
        productFaq.setRemark("");
        ProductFaq productFaq1 = productFaqMapper.selectByPrimaryKey(param.getId());
        Integer countDiff = SpringBeanUtil.helpUpdateColumn(ProductFaq.class, productFaq1, productFaq);
        productFaq.setId(param.getId());
        if(countDiff>0){
            int count = productFaqMapper.updateByPrimaryKeySelective(productFaq);
            return count==1?R.ok():R.error("修改常见问题失败，数据库异常");
        }
        return R.ok();
    }

    /**
     * 查看我的常见问题详情
     */
    @Override
    public R productFaqMyDetail(String productFaqId, Long agentId) {
        Assert.notNull(agentId, NON_AGENT_ROLE);
        Assert.notNull(productFaqId, NON_PRODUCT_FAQ_ID);
        FaqDetailParam param = new FaqDetailParam(productFaqId,agentId);
        FaqInfoWithUpdateData result = productFaqMapper.getFaqInfoWithUpdate(param);
        return result == null?R.error("问题不存在或已被删除"):R.ok(result);
    }

    /**
     * 删除我的常见问题
     */
    @Override
    @SysLog
    @Transactional(rollbackFor = {Throwable.class})
    public R productFaqMyDelete(String productFaqId, Long agentId) {
        Assert.notNull(productFaqId, NON_PRODUCT_FAQ_ID);
        int count = productFaqMapper.deleteById(productFaqId);
        return count==1?R.ok():R.error("删除常见问题失败，数据库异常");
    }

    /**
     * 根据产品名称获取相关产品信息
     */
    @Override
    public R getProductInfo(String productName, Long agentId) {
        //查询符合条件的产品
        java.util.Map<String, String> param = new HashMap<>();
        param.put("agentId", agentId.toString());
        param.put("productName", productName);
        List<ProductInfoWithOrders> list = productFaqMapper.getProductInfo(param);
        if(CollectionUtils.isEmpty(list)) {
            return R.error("无匹配的产品");
        }
        return R.ok(list);
    }

    @SysLog
    @Override
    @Transactional(rollbackFor = {Throwable.class})
    public R updateFaqOrder(UpdateFaqOrderParam param, Long sysUserId, Long agentId) {
        Assert.notNull(agentId, NON_AGENT_ROLE);
        if(param.getOrderNum() ==null) {
            return R.error("参数为空");
        }
        ProductFaq productFaq = new ProductFaq();
        productFaq.setId(param.getProductFaqId());
        productFaq.setSort(param.getOrderNum());
//        productFaq.setShelfStatus(param.getShelfStatus());
        productFaq.setUpdateTime(new Date());
        //修改productFaq表数据
        int count = productFaqMapper.updateByPrimaryKeySelective(productFaq);
        if(count != 1) {
            throw new RRException("修改常见问题排序失败，数据库异常");
        }
        return R.ok();
    }

    @SysLog
    @Override
    @Transactional(rollbackFor = {Throwable.class})
    public R againOrder(Long agentId) {
        Assert.notNull(agentId, NON_AGENT_ROLE);
        //对每个产品的常见问题进行排序

        //查询该产品下全部有效的问题记录
        List<ProductFaq> list = productFaqMapper.getProductFaqByAgentId(agentId);
        Map<Long, List<ProductFaq>> collect = list.stream().collect(Collectors.groupingBy(k -> k.getProductId()));

        for(List<ProductFaq> eachList:collect.values()){
            int order = 1;
            if(CollectionUtils.isNotEmpty(eachList)) {
                //根据查询的顺序重新排序
                eachList.sort(new Comparator<ProductFaq>() {
                    @Override
                    public int compare(ProductFaq o1, ProductFaq o2) {
                        return o1.getSort()-o2.getSort();
                    }
                });
                for(ProductFaq productFaq : eachList) {
                    ProductFaq temProductFaq = new ProductFaq();
                    temProductFaq.setId(productFaq.getId());
                    temProductFaq.setSort(order);
                    //修改productFaq表数据
                    int count = productFaqMapper.updateByPrimaryKeySelective(temProductFaq);
                    if(count != 1) {
                        throw new RRException("修改常见问题失败，数据库异常");
                    }
                    order++;
                }
            }
        }
        return R.ok();
    }

    public R updateStatus(Integer state,Long faqId){
        ProductFaq faq = new ProductFaq();
        faq.setState(state);
        faq.setId(faqId);
        int i = productFaqMapper.updateByPrimaryKeySelective(faq);
        if(i!=1){
            return R.error("更新上下架失败");
        }
        return R.ok();
    }

}
