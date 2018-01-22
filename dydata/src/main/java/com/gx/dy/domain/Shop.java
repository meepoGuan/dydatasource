package com.gx.dy.domain;

import java.io.Serializable;

import org.apache.ibatis.type.Alias;

@Alias("shop")
public class Shop implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7438140186601630436L;

	private String id;
	
	private String shopname;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getShopname() {
		return shopname;
	}

	public void setShopname(String shopname) {
		this.shopname = shopname;
	}
	
	
}
