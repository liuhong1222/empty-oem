package com.cc.oem.modules.agent.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cc.oem.common.utils.Base64Img;
import com.cc.oem.common.utils.HttpUtils;
import com.cc.oem.modules.agent.service.OcrService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author wade
 */
@Service
public class OcrServiceImpl implements OcrService {

    private static final Logger logger = LoggerFactory.getLogger(OcrServiceImpl.class);
    @Value("${oem_ocr.url}")
    private String licenseOcrUrl;

    @Value("${oem_ocr.apiName}")
    private String apiName;

    @Value("${oem_ocr.password}")
    private String password;

    @Value("${fileUploadPath.common}")
    private String commonFilePrefix;

//    public static final String APP_ID = "pa1Ji2dT";
//    public static final String APP_KEY = "U82xVQXX";
//    public static final String ID_URL = "https://api.253.com/open/i/ocr/id-ocr-cl";
//    public static final String BUS_URL = "https://api.253.com/open/qtsb/bus_license";

    /**
     * 身份证ocr文字识别
     * @param imageBytes
     * @param idCardSide
     * @return
     * {"chargeStatus":0,"message":"成功","data":{"tradeNo":"21060714185869024","address":"上海市金山区南门村8号","birth":"19191008","name":"姓朴槿惠","cardNum":"","sex":"女","nation":"汉","issuingDate":"","issuingAuthority":"","expiryDate":"","imageStatus":"blurred","direction":""},"code":"200000"}
     */
//    public static IdCardIdentifyResultVo idCardOcrByImageBytes(byte[] imageBytes, String idCardSide) {
//        CloseableHttpClient client = HttpClients.createDefault();
//        // 创建一个Post对象
//        HttpPost post = new HttpPost(ID_URL);
//        // 创建一个entity模拟一个表单
//        String fileBase64 = Base64Utils.encodeToString(imageBytes); // 身份证图片转码或者url
//        String ocrType = "front".equalsIgnoreCase(idCardSide) ? "0" : "1"; // 标识身份证正反面 0表示正面，1表示反面
//        List list = new ArrayList<>();
//        // 视频文件的base64编码
//        String fileBase64 = Base64Utils.encodeToString(imageBytes); // 身份证图片转码或者url
//        String ocrType = "front".equalsIgnoreCase(idCardSide) ? "0" : "1"; // 标识身份证正反面 0表示正面，1表示反面
//        List list = new ArrayList<>();
//        // 视频文件的base64编码
//        list.add(new BasicNameValuePair("imageType","BASE64"));
//        list.add(new BasicNameValuePair("appKey", APP_KEY));
//        list.add(new BasicNameValuePair("appId", APP_ID));
//        list.add(new BasicNameValuePair("image", fileBase64));
//        list.add(new BasicNameValuePair("ocrType", ocrType));
//        CloseableHttpResponse response = null;
//
//        try {
//            // 包装成一个entity对象
//            StringEntity entity = new UrlEncodedFormEntity(list,"utf-8");
//            // 设置请求内容
//            post.setEntity(entity);
//            // 执行请求内容
//            response = client.execute(post);
//            int code = response.getStatusLine().getStatusCode();
//            if(code != 200) {
//                logger.info("http连接错误: {}", response);
//                return null;
//            }
//            HttpEntity contentEntity = response.getEntity();
//            String content = EntityUtils.toString(contentEntity);
//            logger.info("身份证ocr接口返回：{}", content);
//            if(StringUtils.isBlank(content)) {
//                return null;
//            }
//            JSONObject resp = JSON.parseObject(content);
//            String resultCode = resp.getString("code");
//            if (StringUtils.equals("200000", resultCode)) {
//                JSONObject jsonObject = resp.getJSONObject("data");
//                IdCardIdentifyResultVo idCardIdentifyResultVo = new IdCardIdentifyResultVo();
//                if ("front".equalsIgnoreCase(idCardSide)) {
//                    String address = jsonObject.getString("address");
//                    String name = jsonObject.getString("name");
//                    String cardNum = jsonObject.getString("cardNum");
//                    idCardIdentifyResultVo.setIdCardAddress(address)
//                            .setIdCardName(name)
//                            .setIdCardNumber(cardNum);
//                } else {
//                    String idCardExpireStartTime = jsonObject.getString("issuingDate");
//                    if (StringUtils.isNotBlank(idCardExpireStartTime) && idCardExpireStartTime.length() >= 8) {
//                        idCardExpireStartTime = idCardExpireStartTime.replaceAll("(?i)(\\d{4})(\\d{2})(\\d{2})", "$1-$2-$3");
//                    }
//                    String idCardExpireEndTime = jsonObject.getString("expiryDate");
//                    if (StringUtils.isNotBlank(idCardExpireEndTime) && idCardExpireEndTime.length() >= 8) {
//                        idCardExpireEndTime = idCardExpireEndTime.replaceAll("(?i)(\\d{4})(\\d{2})(\\d{2})", "$1-$2-$3");
//                    }
//
//                    idCardIdentifyResultVo.setIdCardExpireStartTime(idCardExpireStartTime)
//                            .setIdCardExpireEndTime(idCardExpireEndTime);
//                }
//                logger.info("身份证文字识别结果：{}", idCardIdentifyResultVo);
//                return idCardIdentifyResultVo;
//            } else {
//                logger.warn("身份证ocr识别返回响应码不正确: {}", content);
//            }
//        } catch (IOException e) {
//            logger.error("身份证ocr识别发生异常", e);
//        } finally {
//            IOUtils.close(response, client);
//        }
//        return null;
//    }

    /**
     * 营业执照ocr识别
     * @param filePath
     * @return
     * "{\"tradeNo\":\"851485504565284864\",\"chargeStatus\":1,\"message\":\"成功\",\"data\":{\"msg\":\"SUCCESS\",\"data\":{\"logger_id\":\"8e697f8419784516a71f8512f4ed4789\",\"words_result\":{\"社会信用代码\":{\"words\":\"91330200750364874C\"},\"组成形式\":{\"words\":\"\"},\"经营范围\":{\"words\":\"商用宁询，商用鞋和交调及具事配的、电子元具件、智能控制系统的研发、新造，称测、销售、安装、维修及相关信息咨询服务；日营和代理各类流品和技术营进出口业务，和济家和龙经营围禁业进出口的息物的技术服外、依法及件批用的项目，经相关部门批准后方可开展经营活动)\"},\"成立日期\":{\"words\":\"2003年06月24日\"},\"法人\":{\"words\":\"崔华波\"},\"注册资本\":{\"words\":\"110000000元整\"},\"证件编号\":{\"words\":\"\"},\"地址\":{\"words\":\"宁波市鄞州区姜山镇明光北路1166号\"},\"单位名称\":{\"words\":\"宁波奥克斯电气股份有限公司\"},\"有效期\":{\"words\":\"长期\"},\"类型\":{\"words\":\"股份有限公司(非上市)\"}},\"risk_type\":\"normal\",\"words_result_num\":11,\"image_status\":\"normal\"},\"state\":1},\"code\":\"200000\"}"
     *
     * {
     * 	"tradeNo": "851486134641885184",
     * 	"chargeStatus": 1,
     * 	"message": "成功",
     * 	"data": {
     * 		"msg": "SUCCESS",
     * 		"data": {
     * 			"logger_id": "40050626d871424c93e153a8653e4226",
     * 			"words_result": {
     * 				"社会信用代码": {
     * 					"words": "91330200750364874C"
     *                                },
     * 				"组成形式": {
     * 					"words": ""
     *                },
     * 				"经营范围": {
     * 					"words": "商用宁询，商用鞋和交调及具事配的、电子元具件、智能控制系统的研发、新造，称测、销售、安装、维修及相关信息咨询服务；日营和代理各类流品和技术营进出口业务，和济家和龙经营围禁业进出口的息物的技术服外、依法及件批用的项目，经相关部门批准后方可开展经营活动)"
     *                },
     * 				"成立日期": {
     * 					"words": "2003年06月24日"
     *                },
     * 				"法人": {
     * 					"words": "崔华波"
     *                },
     * 				"注册资本": {
     * 					"words": "110000000元整"
     *                },
     * 				"证件编号": {
     * 					"words": ""
     *                },
     * 				"地址": {
     * 					"words": "宁波市鄞州区姜山镇明光北路1166号"
     *                },
     * 				"单位名称": {
     * 					"words": "宁波奥克斯电气股份有限公司"
     *                },
     * 				"有效期": {
     * 					"words": "长期"
     *                },
     * 				"类型": {
     * 					"words": "股份有限公司(非上市)"
     *                }
     * 			},
     * 			"risk_type": "normal",
     * 			"words_result_num": 11,
     * 			"image_status": "normal"
     *        },
     * 		"state": 1
     * 	},
     * 	"code": "200000"
     * }
     */
    public Map<String, String> agentBizLicenseByFilePath(String filePath, String orderNo) {
        Map<String,String> ocrResultMap = new HashMap<>();
        // 创建一个entity模拟一个表单
        Map param = new HashMap();
        String imgBase64Str = Base64Img.GetImageStrFromPath(commonFilePrefix+filePath);
        param.put("image", imgBase64Str);
        param.put("appId", apiName);
        param.put("appKey", password);
        param.put("fixMode", "1");

        String result = "";
        try {
            result = HttpUtils.post(licenseOcrUrl, param);
            JSONObject resp = JSON.parseObject(result);
            String resultCode = resp.getString("code");
            if (StringUtils.equals("200000", resultCode)) {
                JSONObject jsonObject = resp.getJSONObject("data").getJSONObject("data");
                JSONObject wordsResult = jsonObject.getJSONObject("words_result");
                String socialCreditCode = wordsResult.getJSONObject("社会信用代码").getString("words");
                String compositionForm = wordsResult.getJSONObject("组成形式").getString("words");
                String businessScope = wordsResult.getJSONObject("经营范围").getString("words");
                String legalPerson = wordsResult.getJSONObject("法人").getString("words");
                String establishmentDate = wordsResult.getJSONObject("成立日期").getString("words");
                String registeredCapital = wordsResult.getJSONObject("注册资本").getString("words");
                String idNo = wordsResult.getJSONObject("证件编号").getString("words");
                String address = wordsResult.getJSONObject("地址").getString("words");
                String companyName = wordsResult.getJSONObject("单位名称").getString("words");
                String type = wordsResult.getJSONObject("类型").getString("words");
                String validityTerm = wordsResult.getJSONObject("有效期").getString("words");
                //公司名称
                ocrResultMap.put("name",companyName);
                ocrResultMap.put("address", address);
                ocrResultMap.put("legalPerson", legalPerson);
                ocrResultMap.put("regNum",socialCreditCode);
                ocrResultMap.put("establishDate",establishmentDate);
                ocrResultMap.put("validPeriod",validityTerm);

                logger.info("营业执照文字识别结果：{}", ocrResultMap);
            } else {
                logger.warn("营业执照文字识别返回响应码不正确: {}", resultCode);
            }
        } catch (IOException e) {
            logger.error("营业执照文字识别发生异常", e);
        } catch (Exception e) {
            logger.error("发生异常错误：",e);
            e.printStackTrace();
        }
        return ocrResultMap;
    }

}
