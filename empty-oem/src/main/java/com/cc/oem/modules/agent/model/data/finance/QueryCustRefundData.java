package com.cc.oem.modules.agent.model.data.finance;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.Version;
import com.baomidou.mybatisplus.enums.IdType;
import com.cc.oem.common.annotation.ExcelField;
import com.cc.oem.modules.agent.entity.finance.CustomerRefund;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@Accessors(chain = true)
public class QueryCustRefundData{

    /**
     * 公司名称
     */
    @ExcelField(value = "代理商名称", order =3)
    private String companyName;

    private Integer customerType;

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.ID_WORKER)
    private Long id;

    @ApiModelProperty(value = "代理商编号")
    @NotNull(message = "代理商编号不能为空")
    private Long agentId;

    @ApiModelProperty(value = "客户编号")
    @NotNull(message = "客户编号不能为空")
    private Long customerId;

    @ApiModelProperty(value = "订单编号")
    @ExcelField(value = "订单编号", order =3)
    private String orderNo;

    @ApiModelProperty(value = "客户名称")
    @ExcelField(value = "客户名称", order = 1)
    private String name;

    @ApiModelProperty(value = "手机号码")
    @NotBlank(message = "手机号码不能为空")
    @ExcelField(value = "手机号", order = 2)
    private String phone;

    @ApiModelProperty(value = "单价（元/条）")
    @NotNull(message = "单价不能为空")
    @ExcelField(value = "单价（元/条）", order = 6)
    private String price;

    @ApiModelProperty(value = "退款条数")
    @NotNull(message = "退款条数不能为空")
    @ExcelField(value = "退款条数", order = 7)
    private Integer refundNumber;

    @ApiModelProperty(value = "退款金额")
    @NotNull(message = "退款金额不能为空")
    @ExcelField(value = "退款金额(元)", order = 8)
    private String refundAmount;

    @ApiModelProperty(value = "退款方式，0：对公转账，1：支付宝退款，2：其他")
    @NotNull(message = "退款方式不能为空")
    @Range(min = 0, max = 2, message = "退款方式不识别")
    private Integer refundType;

    @ExcelField(value = "退款方式", order = 12)
    private String refundTypeName;

    @ApiModelProperty(value = "产品类别，0：空号检测产品，1：实时检测产品")
//    @NotNull(message = "产品类别不能为空")
    @Range(min = 0, max = 1, message = "产品类别输入有误")
    private Integer category;

    @ExcelField(value = "产品名称", order = 4)
    private String categoryName;

    @ApiModelProperty(value = "期初余条")
    @ExcelField(value = "期初余条", order =9)
    private Long openingBalance;

    @ApiModelProperty(value = "期末余条")
    @ExcelField(value = "期末余条", order = 10)
    private Long closingBalance;

    @ApiModelProperty(value = "备注")
    @ExcelField(value = "备注", order =11)
    private String remark;

    @Version
    @ApiModelProperty(value = "版本")
    private Integer version;

    @ApiModelProperty(value = "创建时间")
    @ExcelField(value = "退款时间", order = 5)
    private Date createTime;

    @ApiModelProperty(value = "修改时间")
    private Date updateTime;

    @ApiModelProperty(value = "操作者名称")
    private String creatorName;



}
