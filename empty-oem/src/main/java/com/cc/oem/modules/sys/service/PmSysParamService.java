package com.cc.oem.modules.sys.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cc.oem.common.redis.RedisClient;
import com.cc.oem.common.utils.R;
import com.cc.oem.modules.agent.service.RefreshCacheService;
import com.cc.oem.modules.sys.constant.SysParamEnum;
import com.cc.oem.modules.sys.dao.PmSysParamMapper;
import com.cc.oem.modules.sys.vo.data.PmSysParam;
import com.cc.oem.modules.sys.vo.data.PmSysParamQueryParam;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 参数管理实现类
 * @author liuh, xybb
 * @date 2021年3月27日
 */
@Service
public class PmSysParamService {
    private final static Logger logger = LoggerFactory.getLogger(PmSysParamService.class);

    @Autowired
    private PmSysParamMapper pmSysParamMapper;
    
    @Autowired
    private RedisClient redisClient;
    @Autowired
	private RefreshCacheService refreshCacheService;

	/**
	 * 参数管理缓存前缀
	 */
	private final String SYS_PARAM_PREFIX = "OEM:" + "param:";

   public PmSysParam findOne(String paramName) {
	   return pmSysParamMapper.findOne(paramName);
   }
   
   public PmSysParam findOneByCache(String paramName) {
	   PmSysParam pmSysParam = new PmSysParam();
	   // 查询redis是否存在数据
	   String redisStr = redisClient.get(SYS_PARAM_PREFIX + paramName);
	   if(StringUtils.isBlank(redisStr)) {
		   // 查询数据库中的数据
		   pmSysParam = findOne(paramName);
		   // 数据库中的数据存入redis缓存
		   redisClient.set(SYS_PARAM_PREFIX + paramName, JSON.toJSONString(pmSysParam));
		   return pmSysParam;
	   }
	   
	   return JSONObject.parseObject(redisStr,PmSysParam.class);
   }
   
   public R update(String paramName) {
	   
	   if(SysParamEnum.SEND_FREQUENCY_LIMIT.getName().equals(paramName)) {
		   logger.info("-------手动清理本地发送号码缓存数据开始执行-------");
//		   sendFrequencyService.localCacheClean();
		   logger.info("-------手动清理本地发送号码缓存数据执行完成-------");
	   }
	   return null;
   }

    /**
     * 分页条件查询参数列表
     * @date 2021/3/28
     * @param param
     * @return com.chuanglan.pm.base.response.R
     */
	public R ParamPageByCondition(PmSysParamQueryParam param) {
		PageHelper.startPage(param.getCurrentPage(), param.getPageSize());
	    List<PmSysParam> list = pmSysParamMapper.ParamPageByCondition(param.getParamKey());
		PageInfo<PmSysParam> info = new PageInfo<>(list);
		return R.ok(info);
	}

	/**
	 * 修改单个参数
	 * @date 2021/3/28
	 * @param pmSysParam
	 * @return com.chuanglan.pm.base.response.R
	 */
	public R updateOne(PmSysParam pmSysParam) {
		int i = pmSysParamMapper.updateOne(pmSysParam);
		if (i!=1) {
			logger.error("系统设置-参数管理-修改参数失败。params:{}", pmSysParam);
			return R.error("修改参数失败");
		} else {
			// 更新redis缓存
			refreshCacheService.sysConfigInfoRefresh(pmSysParam.getParamKey());
			logger.info("系统设置-参数管理-修改参数成功。params:{}",pmSysParam);
			return R.ok();
		}
	}

	/**
	 * 删除单个参数
	 * @date 2021/3/28
	 * @param id
	 * @return com.chuanglan.pm.base.response.R
	 */
	public R deleteOne(Integer id) {
		PmSysParam pmSysParam = this.getPmSysParamById(id);
		int i = pmSysParamMapper.deleteOne(id);
		if (i!=1) {
			logger.error("系统设置-参数管理-删除参数失败。params:{}",id);
			return R.error("删除参数失败");
		} else {
			// 更新redis缓存
			refreshCacheService.sysConfigInfoRefresh(pmSysParam.getParamKey());
			logger.info("系统设置-参数管理-删除参数成功。params:{}", id);
			return R.ok();
		}
	}

	/**
	 * 新增参数
	 * @date 2021/4/23
	 * @param param
	 * @return com.chuanglan.pm.base.response.R
	 */
	public R save(PmSysParam param) {
		int i = pmSysParamMapper.save(param);
		if (i<=0) {
		    logger.error("系统设置-参数管理-新增参数失败。param:{}", param);
		    return R.error("新增参数失败");
		} else {
			refreshCacheService.sysConfigInfoRefresh(param.getParamKey());
		    logger.info("系统设置-参数管理-新增参数成功。param:{}", param);
		    return R.ok();
		}
	}

	/**
	 * 通过id查询参数详情
	 * @date 2021/4/23
	 * @param id
	 * @return com.chuanglan.pm.base.response.R
	 */
	public R queryById(Integer id) {
		PmSysParam param = getPmSysParamById(id);
		return R.ok(param);
	}

	/**
	 * 通过id查询参数详情-pure
	 * @date 2021/8/19
	 * @param id
	 * @return com.chuanglan.pm.core.param.sys.PmSysParam
	 */
	private PmSysParam getPmSysParamById(Integer id) {
		return pmSysParamMapper.queryById(id);
	}
}
