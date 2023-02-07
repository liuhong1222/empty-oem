package com.cc.oem.modules.agent.model.param;

import java.math.BigDecimal;

/**
 * copyright (C), 2018-2018, 创蓝253
 * fileName T
 * author   zhangx
 * date     2018/8/15 19:31
 * description
 */
public class PackageParam {
    private Long id;
    private BigDecimal number;
    private BigDecimal money;
    private String packageName;

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getNumber() {
        return number;
    }

    public void setNumber(BigDecimal number) {
        this.number = number;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }
}