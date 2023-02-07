package com.cc.oem.modules.agent.service;

import com.cc.oem.common.utils.R;
import com.cc.oem.modules.agent.model.param.FaqAuditParam;
import com.cc.oem.modules.agent.model.param.ProductFaqParam;
import com.cc.oem.modules.agent.model.param.ProductFaqSaveParam;
import com.cc.oem.modules.agent.model.param.UpdateFaqOrderParam;
import org.springframework.stereotype.Service;

/**
 * author liuh
 */
@Service
public interface ProductFaqService {

	/**
	 * 查询常见问题审核列表
	 */
	R productFaqAllList(ProductFaqParam param);

	/**
	 * 审核常见问题
	 */
	R productFaqAllAudit(FaqAuditParam param, Long sysUserId);

	/**
	 * 查看常见问题详情
	 */
	R productFaqAllDetail(String productFaqId);

	/**
	 * 查询我的常见问题列表
	 */
	R productFaqMyList(ProductFaqParam param, Long agentId);

	/**
	 * 发布我的常见问题
	 */
	R productFaqMySave(ProductFaqSaveParam param,Long agentId);

	/**
	 * 修改我的常见问题
	 */
	R productFaqMyUpdate(ProductFaqSaveParam param, Long sysUserId, Long agentId);

	/**
	 * 查看我的常见问题详情
	 */
	R productFaqMyDetail(String productFaqId, Long agentId);

	/**
	 * 删除我的常见问题
	 */
	R productFaqMyDelete(String productFaqId,Long agentId);

	/**
	 *根据产品名称获取产品相关信息
	 */
	R getProductInfo(String productName, Long agentId);

	/**
	 * 修改常见问题排序
	 */
	R updateFaqOrder(UpdateFaqOrderParam param, Long sysUserId, Long agentId);

	/**
	 * 常见问题重新排序
	 */
	R againOrder(Long agentId);

	R updateStatus(Integer state,Long faqId);
}
