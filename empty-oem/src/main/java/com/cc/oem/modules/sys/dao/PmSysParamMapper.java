package com.cc.oem.modules.sys.dao;

import com.cc.oem.modules.sys.vo.data.PmSysParam;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author liuh
 * @date 2021年3月27日
 */
@Mapper
public interface PmSysParamMapper {

	PmSysParam findOne(@Param("paramName") String paramName);

	/**
	 * 分页条件查询参数管理列表
	 * @date 2021/3/28
	 * @param paramKey
	 * @return java.util.List<com.chuanglan.pm.core.param.sys.PmSysParam>
	 */
    List<PmSysParam> ParamPageByCondition(@Param("paramKey") String paramKey);

    /**
     * 修改单个参数
     * @date 2021/3/28
     * @param pmSysParam
     * @return int
     */
	int updateOne(PmSysParam pmSysParam);

	/**
	 * 删除单个参数
	 * @date 2021/3/28
	 * @param id
	 * @return int
	 */
	int deleteOne(Integer id);

	/**
	 * 新增参数
	 * @date 2021/4/23
	 * @param param
	 * @return com.chuanglan.pm.base.response.Result
	 */
    int save(PmSysParam param);

    /**
     * 通过id查询参数
     * @date 2021/4/23
     * @param id
     * @return com.chuanglan.pm.core.param.sys.PmSysParam
     */
	PmSysParam queryById(Integer id);
}
