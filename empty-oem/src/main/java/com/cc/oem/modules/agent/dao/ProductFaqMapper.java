package com.cc.oem.modules.agent.dao;


import java.util.List;

import com.cc.oem.modules.agent.entity.ProductFaq;
import com.cc.oem.modules.agent.entity.TempEntity;
import com.cc.oem.modules.agent.model.data.FaqInfoData;
import com.cc.oem.modules.agent.model.data.FaqInfoWithUpdateData;
import com.cc.oem.modules.agent.model.data.ProductInfoWithOrders;
import com.cc.oem.modules.agent.model.param.FaqDetailParam;
import com.cc.oem.modules.agent.model.param.IsExistFaqParam;
import com.cc.oem.modules.agent.model.param.ProductFaqParam;
import com.cc.oem.modules.agent.BaseOemMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface ProductFaqMapper  extends BaseOemMapper<ProductFaq, Long> {

	List<FaqInfoData> productFaqAllList(ProductFaqParam param);

	FaqInfoData getProductFaqInfoById(FaqDetailParam param);

	List<ProductInfoWithOrders> getProductInfo(java.util.Map<String, String> param);

	FaqInfoWithUpdateData getFaqInfoWithUpdate(FaqDetailParam param);

	int deleteById(String id);

	int insertProductFaqWebsiteByFaqId(String productFaqId);

	int IsExistFaqInfoByFaq(IsExistFaqParam param);
	
	Long insertWebByDefault(TempEntity tempEntity);
    
    Long insertByDefault(TempEntity tempEntity);

	List<ProductFaq> getProductFaqByAgentId(@Param("agentId") Long agentId);
}