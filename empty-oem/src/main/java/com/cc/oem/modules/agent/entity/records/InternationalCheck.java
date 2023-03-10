package com.cc.oem.modules.agent.entity.records;

import com.baomidou.mybatisplus.annotations.TableLogic;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.util.Date;

/**
 * 国际检测记录
 * @author liuh
 * @date 2022年6月13日
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "InternationalCheck对象", description = "国际检测记录")
public class InternationalCheck{

    @ApiModelProperty(value = "主键")
    private Long id;

    @ApiModelProperty(value = "所属代理商编号")
    @NotNull(message = "所属代理商编号不能为空")
    private Long agentId;

    @ApiModelProperty(value = "代理商名称")
    private String agentName;

    @ApiModelProperty(value = "客户编号")
    @NotNull(message = "客户编号不能为空")
    private Long customerId;
    
    @ApiModelProperty(value = "国家编码")
    @NotNull(message = "国家编码不能为空")
    private String countryCode;
    
    @ApiModelProperty(value = "外部文件id")
    @NotNull(message = "外部文件id不能为空")
    private String externFileId;

    @ApiModelProperty(value = "文件名称")
    @NotBlank(message = "文件名称不能为空")
    private String fileName;

    @ApiModelProperty(value = "文件大小")
    @NotBlank(message = "文件大小不能为空")
    private String fileSize;

    @ApiModelProperty(value = "客户上传文件地址")
    @NotBlank(message = "客户上传文件地址不能为空")
    private String fileUrl;
    
    @ApiModelProperty(value = "总条数（不含无效号码）；null表示未检测条数")
    private Long totalNumber;
    
    @ApiModelProperty(value = "无效号码数")
    private Long illegalNumber;
    
    @ApiModelProperty(value = "已激活条数")
    private Long activeCount;
    
    @ApiModelProperty(value = "未激活条数")
    private Long noRegisterCount;
    
    @ApiModelProperty(value = "未注册条数")
    private Long unknownCount;

    @ApiModelProperty(value = "状态；-1：号码数量为0；-2：客户余额不足，-3：代理商余额不足，0：待扣款，1：扣款成功，9：最终成功，10：用户已取消此任务")
    private Integer status;

    @ApiModelProperty(value = "检测类型 1-文件 2-接口")
    private Integer checkType;

    @ApiModelProperty(value = "逻辑删除，0：未删除，1：已删除")
    @Null(message = "逻辑删除不用传")
    @TableLogic
    private Integer deleted;

    @ApiModelProperty(value = "文件md5校验值")
    private String md5;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "版本")
    private Integer version;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "修改时间")
    private Date updateTime;

    public enum InternationalCheckStatus {
        /**
         * 待分类号码数量太少，任务结束
         */
        AMOUNT_LESS(-1),
        /**
         * 客户余额不足，任务结束
         */
        CUSTOMER_NOT_ENOUGH(-2),
        /**
         * 代理商余额不足，任务结束
         */
        AGENT_NOT_ENOUGH(-3),
        /**
         * 待分类号码数量超过最大值，任务结束
         */
        AMOUNT_MORE(-4),
        /**
         * 待扣款
         */
        INIT(0),
        /**
         * 扣款成功
         */
        DEDUCTION_SUCCESS(1),
        /**
         * 调用接口检测成功
         */
        CHECK_SUCCESS(3),
        /**
         * 最终分类成功，任务结束
         */
        WORK_FINISH(9),
        /**
         * 检测失败
         */
        CHECK_FAIL(10);

        private final int status;

        InternationalCheckStatus(int status) {
            this.status = status;
        }

        public int getStatus() {
            return status;
        }
    }
}
