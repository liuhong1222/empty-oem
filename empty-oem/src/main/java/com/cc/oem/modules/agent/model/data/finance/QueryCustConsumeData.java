package com.cc.oem.modules.agent.model.data.finance;

import com.cc.oem.common.annotation.ExcelField;
import com.cc.oem.modules.agent.entity.finance.CustomerRefund;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
public class QueryCustConsumeData {

    /**
     * 公司名称
     */
    private String companyName;


    @ApiModelProperty(value = "主键")
    private Long id;

    @ExcelField(value = "代理商编号", order = 0)
    @ApiModelProperty(value = "代理商编号")
    private Long agentId;

    @ExcelField(value = "客户编号", order = 1)
    @ApiModelProperty(value = "客户编号")
    private Long customerId;

    @ExcelField(value = "客户名称", order = 2)
    @ApiModelProperty(value = "客户名称")
    private String name;

    @ExcelField(value = "手机号码", order = 3)
    @ApiModelProperty(value = "手机号码")
    private String phone;

    @ExcelField(value = "消耗条数", order = 4)
    @ApiModelProperty(value = "消耗条数")
    private Long consumeNumber;

    @ExcelField(value = "消费方式，0：冻结，1：扣款成功，2：解冻", order = 5)
    @ApiModelProperty(value = "消费方式，0：冻结，1：扣款成功，2：解冻")
    private Integer consumeType;

    @ExcelField(value = "空号检测主键", order = 6)
    @ApiModelProperty(value = "空号检测主键")
    private Long emptyId;

    @ExcelField(value = "产品类别，0：空号检测产品，1：实时检测产品", order = 7)
    @ApiModelProperty(value = "产品类别，0：空号检测产品，1：实时检测产品")
    private Integer category;

    @ExcelField(value = "期初余条", order = 8)
    @ApiModelProperty(value = "期初余条")
    private Long openingBalance;

    @ExcelField(value = "期末余条", order = 9)
    @ApiModelProperty(value = "期末余条")
    private Long closingBalance;

    @ExcelField(value = "备注", order = 10)
    @ApiModelProperty(value = "备注")
    private String remark;

    @ExcelField(value = "版本", order = 11)
    @ApiModelProperty(value = "版本")
    private Integer version;

    @ExcelField(value = "创建时间", order = 12)
    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ExcelField(value = "修改时间", order = 13)
    @ApiModelProperty(value = "修改时间")
    private Date updateTime;

    public enum ConsumeType {
        /**
         * 冻结
         */
        FREEZE(0),
        /**
         * 扣款成功
         */
        DEDUCTION_SUCCESS(1),
        /**
         * 解冻
         */
        UNFREEZE(2);

        private int value;

        ConsumeType(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }


}
