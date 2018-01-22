package com.gx.dy.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.gx.dy.domain.Shop;
import com.gx.dy.mapper.ShopMapper;

/**
 * @author MeepoGuan
 *
 * <p>Description: </p>
 *
 * 2018年1月22日
 *
 */
@Service
public class ShopService {

	@Resource
	private ShopMapper mapper;
	
	public List<Shop> getAllS() {
		return mapper.getAllShop();
	}
	
	public List<Shop> getByTx1() {
		return mapper.getAllShopByTx1();
	}
	
	public List<Shop> getByTx2() {
		return mapper.getAllShopByTx2();
	}
	
	public List<Shop> getByTx3() {
		return mapper.getAllShopByTx3();
	}
}
