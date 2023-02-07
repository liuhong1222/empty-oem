package com.cc.oem.modules.agent.constant;

public class RedisKeys {

	private static RedisKeys rediskeys;

	public static RedisKeys getInstance() {
		if (rediskeys == null) {
			synchronized (RedisKeys.class) {
				if (rediskeys == null) {
					rediskeys = new RedisKeys();
				}
			}
		}
		return rediskeys;  
	}

	/**
	 * 获取空号检测 需要检测的总条数key （根据文件获取的总条数）
	 * 
	 * @return
	 */
	public String getKhTestCountKey(String userId, String uid) {
		return "kh:tc:" + userId + ":" + uid;
	}

	/**
	 * 获取空号检测 已经成功检测的总条数（运行中，不考虑不计费的条数）
	 * 
	 * @param userId
	 * @return
	 */
	public String getkhSucceedTestCountkey(String userId, String uid) {
		return "kh:stc:" + userId + ":" + uid;
	}

	/**
	 * 获取空号检测 已经成功检测的总条数（运行结束需要记账的总条数）
	 * 
	 * @param userId
	 * @return
	 */
	public String getkhSucceedClearingCountkey(String userId, String uid) {
		return "kh:scc:" + userId + ":" + uid;
	}

	/**
	 * 实号包列表key1
	 * 
	 * @param userId
	 * @return
	 */
	public String getkhRealOneListtkey(String userId) {
		return "kh:sl:r:one" + userId;
	}

	/**
	 * 实号包列表key2
	 * 
	 * @param userId
	 * @return
	 */
	public String getkhRealTwoListtkey(String userId) {
		return "kh:sl:s:two" + userId;
	}

	/**
	 * 实号包列表key3
	 * 
	 * @param userId
	 * @return
	 */
	public String getkhRealThreeListtkey(String userId) {
		return "kh:sl:s:three" + userId;
	}

	/**
	 * 空号包列表key1
	 * 
	 * @param userId
	 * @return
	 */
	public String getkhKongOneListtkey(String userId) {
		return "kh:sl:k:one:" + userId;
	}
	

	/**
	 * 空号包列表key2
	 * 
	 * @param userId
	 * @return
	 */
	public String getkhKongTwoListtkey(String userId) {
		return "kh:sl:k:two:" + userId;
	}

	/**
	 * 空号包列表key3
	 * 
	 * @param userId
	 * @return
	 */
	public String getkhKongThreeListtkey(String userId) {
		return "kh:sl:k:three:" + userId;
	}

	/**
	 * 空号包列表key4
	 * 
	 * @param userId
	 * @return
	 */
	public String getkhKongFourListtkey(String userId) {
		return "kh:sl:k:four:" + userId;
	}

	/**
	 * 空号包列表key4
	 * 
	 * @param userId
	 * @return
	 */
	public String getkhKongFiveListtkey(String userId) {
		return "kh:sl:k:five:" + userId;
	}

	/**
	 * 沉默包列表key
	 * 
	 * @param userId
	 * @return
	 */
	public String getkhSilenceListtkey(String userId) {
		return "kh:sl:sm:" + userId;
	}

	/**
	 * 空号检测线程key （多线程执行是 全部执行完毕生成文件使用）
	 * 
	 * @param userId
	 * @return
	 */
	public String getkhGenerateResultskey(String userId, String uid) {
		return "kh:gs:ts:" + userId + ":" + uid;
	}

	/**
	 * 空号检测线程key （多线程执行是 全部执行完毕生成文件使用）
	 * 
	 * @param userId
	 * @return
	 */
	public String getkhExceptionkey(String userId, String uid) {
		return "kh:gs:ex:" + userId + ":" + uid;
	}

	/**
	 * 空号检测线程key （多线程执行是 全部执行完毕生成文件使用）
	 * 
	 * @param userId
	 * @return
	 */
	public String getkhTheRunkey(String userId, String uid) {
		return "kh:th:rs:" + userId + ":" + uid;
	}

	/**
	 * 获取空号检测 检测方法加锁名称
	 * 
	 * @param userId
	 * @return
	 */
	public String getkhTheTestFunKey(String mobile, String uid) {
		return "kh:ttf:" + mobile + ":" + uid;
	}

	/**
	 * 获取账户二次清洗 检测方法加锁名称
	 * 
	 * @param userId
	 * @return
	 */
	public String getzhlcfunKey(String mobile) {
		return "zh:lcf:" + mobile;
	}

	/**
	 * 获取空号API 检测方法加锁名称
	 * 
	 * @param userId
	 * @return
	 */
	public String getkhApifunKey(String mobile) {
		return "kh:api:" + mobile;
	}
	
	/**
	 * 获取号码实时在线检测方法加锁名称
	 * 
	 * @param userId
	 * @return
	 */
	public String getMobileStatusApifunKey(String userid) {
		return "mobileStatusQu:api:" + userid;
	}
	
	/**
	 * 获取人证比对方法加锁名称
	 * 
	 * @param userId
	 * @return
	 */
	public String getFaceIdentyCompareApifunKey(String userid) {
		return "faceIdentyCo:api:" + userid;
	}
	
	/**
	 * 获取营业执照方法加锁名称
	 * 
	 * @param userId
	 * @return
	 */
	public String getBusinessLicenseOcrApifunKey(String userid) {
		return "businessLicenseOcr:api:" + userid;
	}
	
	/**
	 * 获取身份证OCR方法加锁名称
	 * 
	 * @param userId
	 * @return
	 */
	public String getIdCardOcrApifunKey(String userid) {
		return "idCardOcr:api:" + userid;
	}
	
	/**
	 * 获取银行卡OCR方法加锁名称
	 * 
	 * @param userId
	 * @return
	 */
	public String getBankOcrApifunKey(String userid) {
		return "bankOcr:api:" + userid;
	}
	
	/**
	 * 获取驾驶证OCR方法加锁名称
	 * 
	 * @param userId
	 * @return
	 */
	public String getDriverOcrApifunKey(String userid) {
		return "driverOcr:api:" + userid;
	}
	
	/**
	 * 获取一比一人脸比对方法加锁名称
	 * 
	 * @param userId
	 * @return
	 */
	public String getFaceToFaceCompareApifunKey(String userid) {
		return "faceToFaceCo:api:" + userid;
	}
	
	/**
	 * 获取活体检测方法加锁名称
	 * 
	 * @param userId
	 * @return
	 */
	public String getCheckLivenessApifunKey(String userid) {
		return "checkLiveness:api:" + userid;
	}
	
	/**
	 * 获取身份认证检测方法加锁名称
	 * 
	 * @param userId
	 * @return
	 */
	public String getIdCardApifunKey(String userid) {
		return "idCardAuth:api:" + userid;
	}
	
	/**
	 * 获取银行卡鉴权检测方法加锁名称
	 * 
	 * @param userId
	 * @return
	 */
	public String getBaApifunKey(String userid) {
		return "bankAuth:api:" + userid;
	}
	
	/**
	 * 获取运营商三要素方法加锁名称
	 * 
	 * @param userId
	 * @return
	 */
	public String getMtaApifunKey(String userid) {
		return "mobileThreeAuth:api:" + userid;
	}

	/**
	 * 获取空号API 检测方法加锁名称 (feign)
	 * 
	 * @param userId
	 * @return
	 */
	public String getkhApifunFeignKey(String mobile) {
		return "kh:feign:api:" + mobile;
	}

	/**
	 * 获取空号检测 redis锁的唯一标识
	 * 
	 * @param userId
	 * @return
	 */
	public String getkhRedisLockIdentifier(String userId, String uid) {
		return "kh:rli:" + userId + ":" + uid;
	}

	/**
	 * 获取用户的对象信息key
	 * 
	 * @param mobile
	 * @return
	 */
	public String getSessUserInfo(String mobile) {
		return "re:sui:" + mobile;
	}

	/**
	 * 获取用户的对象信息key
	 *
	 * @param userId
	 * @return
	 */
	public String getSessUserInfoByUserId(Integer userId) {
		return "re:sui:bu:" + userId;
	}

	/**
	 * 获取用户的对象信息key
	 * 
	 * @param userId
	 * @return
	 */
	public String getUserInfokey(String mobile) {
		return "tds:sui:" + mobile;
	}

	/**
	 * 空号API账户条数
	 * 
	 * @param userId
	 * @return
	 */
	public String getKHAPIcountKey(String userId) {
		return "kh:account:" + userId;
	}

	/**
	 * 空号API账户keys
	 * 
	 * @param userId
	 * @return
	 */
	public String getKHAPIcountKeys() {
		return "kh:account:keys";
	}

	/**
	 * 账户二次清洗API账户条数
	 * 
	 * @param
	 * @return
	 */
	public String getRQAPIcountKey(String userId) {
		return "rq:account:" + userId;
	}

	/**
	 * 账户二次清洗API账户keys
	 * 
	 * @param
	 * @return
	 */
	public String getRQAPIcountKeys() {
		return "rq:account:keys";
	}

	/**
	 * 号码状态实时查询API账户条数
	 * 
	 * @param userId
	 * @return
	 */
	public String getMsAPIcountKey(String userId) {
		return "ms:account:" + userId;
	}

	/**
	 * 身份认证API账户keys
	 * 
	 * @param userId
	 * @return
	 */
	public String getIdCardAuthcountKeys() {
		return "idCardAuth:account:keys";
	}
	
	/**
	 * 身份认证API账户条数
	 * 
	 * @param userId
	 * @return
	 */
	public String getIdCardAuthAPIcountKey(String userId) {
		return "idCardAuth:account:" + userId;
	}
	
	/**
	 * 银行卡鉴权API账户keys
	 * 
	 * @param userId
	 * @return
	 */
	public String getBankAuthcountKeys() {
		return "bankAuth:account:keys";
	}
	
	/**
	 * 银行卡鉴权API账户条数
	 * 
	 * @param userId
	 * @return
	 */
	public String getBankAuthAPIcountKey(String userId) {
		return "bankAuth:account:" + userId;
	}
	
	/**
	 * 运营商三要素API账户keys
	 * 
	 * @param userId
	 * @return
	 */
	public String getMobileThreeAuthcountKeys() {
		return "mobileThree:account:keys";
	}
	
	/**
	 *运营商三要素API账户条数
	 * 
	 * @param userId
	 * @return
	 */
	public String getMobileThreeAuthAPIcountKey(String userId) {
		return "mobileThree:account:" + userId;
	}

	/**
	 * 号码状态实时查询API账户keys
	 * 
	 * @param userId
	 * @return
	 */
	public String getMsAPIcountKeys() {
		return "ms:account:keys";
	}

	/**
	 * 号码状态实时查询API账户keys
	 * 
	 * @param userId
	 * @return
	 */
	public String getUserEncryptKey(String appid) {
		return "cldc:encrypt:key_json" + appid;
	}
	
	/**
	 * 获取上传文件空号检测账户key
	 * @param userId
	 * @return
	 */
	public String getAcountKey(String userId) {
		return "kh:waccount:" + userId;
	}
	
	/**
	 * 推送失败队列
	 * @return
	 */
	public String getPushErrorQueue() {
		return "khpsqueue";
	}
	
	/**
	 * 获取代理商短信签名
	 * @param source
	 * @return
	 */
	public String getMessageSignByDls(String source) {
		return "kh:dlsms:" + source;
	}
	
	/**
	 * 获取代理商网址
	 * @param source
	 * @return
	 */
	public String getUrlByDls(String source) {
		return "kh:dlsurl:" + source;
	}
	
	/**
	 * 人证比对API账户keys
	 * 
	 * @param userId
	 * @return
	 */
	public String getFiApicountKeys() {
		return "cldc:fiaccount:keys";
	}
	
	/**
	 *人证比对API账户条数
	 * 
	 * @param userId
	 * @return
	 */
	public String getFiApicountKey(String userId) {
		return "cldc:fiaccount:" + userId;
	}
	
	/**
	 * 一比一人脸比对API账户keys
	 * 
	 * @param userId
	 * @return
	 */
	public String getFfApicountKeys() {
		return "cldc:ffaccount:keys";
	}
	
	/**
	 *一比一人脸比对API账户条数
	 * 
	 * @param userId
	 * @return
	 */
	public String getFfApicountKey(String userId) {
		return "cldc:ffaccount:" + userId;
	}
	
	/**
	 * 活体检测API账户keys
	 * 
	 * @param userId
	 * @return
	 */
	public String getClApicountKeys() {
		return "cldc:claccount:keys";
	}
	
	/**
	 *活体检测API账户条数
	 * 
	 * @param userId
	 * @return
	 */
	public String getClApicountKey(String userId) {
		return "cldc:claccount:" + userId;
	}
	
	/**
	 * 身份证OCRAPI账户keys
	 * 
	 * @param userId
	 * @return
	 */
	public String getIdocrApicountKeys() {
		return "cldc:idocraccount:keys";
	}
	
	/**
	 *身份证OCRAPI账户条数
	 * 
	 * @param userId
	 * @return
	 */
	public String getIdocrApicountKey(String userId) {
		return "cldc:idocraccount:" + userId;
	}
	
	/**
	 * 营业执照OCRAPI账户keys
	 * 
	 * @param userId
	 * @return
	 */
	public String getBlocrApicountKeys() {
		return "cldc:blocraccount:keys";
	}
	
	/**
	 *营业执照OCRAPI账户条数
	 * 
	 * @param userId
	 * @return
	 */
	public String getBlocrApicountKey(String userId) {
		return "cldc:blocraccount:" + userId;
	}
	
	/**
	 * 银行卡OCRAPI账户keys
	 * 
	 * @param userId
	 * @return
	 */
	public String getBocrApicountKeys() {
		return "cldc:bocraccount:keys";
	}
	
	/**
	 *银行卡OCRAPI账户条数
	 * 
	 * @param userId
	 * @return
	 */
	public String getBocrApicountKey(String userId) {
		return "cldc:bocraccount:" + userId;
	}
	
	/**
	 * 驾驶证OCRAPI账户keys
	 * 
	 * @param userId
	 * @return
	 */
	public String getDocrApicountKeys() {
		return "cldc:docraccount:keys";
	}
	
	/**
	 *驾驶证OCRAPI账户条数
	 * 
	 * @param userId
	 * @return
	 */
	public String getDocrApicountKey(String userId) {
		return "cldc:docraccount:" + userId;
	}
	
}
