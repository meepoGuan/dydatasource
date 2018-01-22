package com.gx.dy.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gx.dy.service.ShopService;

@RestController
@RequestMapping("api")
public class ShopController {

	@Resource
	private ShopService shopService;
	
	@GetMapping("all")
	public String geta() {
		return shopService.getAllS().get(0).getShopname();
	}
	
	@GetMapping("tx1")
	public String getTx1() {
		return shopService.getByTx1().get(0).getShopname();
	}
	
	@GetMapping("tx2")
	public String getTx2() {
		return shopService.getByTx2().get(0).getShopname();
	}
	
	@GetMapping("tx3")
	public String getTx3() {
		return shopService.getByTx3().get(0).getShopname();
	}
}
