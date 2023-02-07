package com.cc.oem.modules.sys.constant;

/**
 * 系统参数枚举
 * @author liuh
 * @date 2021年3月27日
 */
public enum SysParamEnum {
	SEND_FREQUENCY_LIMIT("sendFrequencyLimit","发送频率限制"),
    GET_DATA_LOW_BALANCE("getDataLowBalance","取数最低余额限制")
    ;

	private String name;
	
    private String desc;

    SysParamEnum(String name, String desc) {
    	this.name = name;
        this.desc = desc;
    }

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
