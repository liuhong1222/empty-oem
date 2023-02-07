package com.cc.oem.modules.agent.model.data;

/**
 * @author liuh
 * @since 2019/03/26
 */
public class ProductInfoWithOrders {
    /**
     * 产品ID
     */
    private Integer productId;

    /**
     * 产品名称
     */
    private String productName;
    
    /**
     * 产品的问题个数
     */
    private Integer orders;

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public Integer getOrders() {
		return orders;
	}

	public void setOrders(Integer orders) {
		this.orders = orders;
	}
}
