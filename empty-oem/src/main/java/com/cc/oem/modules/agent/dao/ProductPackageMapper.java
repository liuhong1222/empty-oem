package com.cc.oem.modules.agent.dao;

import com.cc.oem.modules.agent.entity.ProductPackage;
import com.cc.oem.modules.agent.BaseOemMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface ProductPackageMapper extends BaseOemMapper<ProductPackage, Long> {

    List<ProductPackage> selectProductPackageListByProductId(@Param("productId") Long productId);

}
