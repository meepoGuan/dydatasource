package com.gx.dy.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.gx.dy.anno.DataSourceTypeAnno;
import com.gx.dy.config.DataSourceEnum;
import com.gx.dy.domain.Shop;

/**
 * @author MeepoGuan
 *
 * <p>Description: </p>
 *
 * 2018年1月22日
 *
 */
@Mapper
public interface ShopMapper {

	
	public List<Shop> getAllShop();
	
	@DataSourceTypeAnno(value=DataSourceEnum.tx1)
	public List<Shop> getAllShopByTx1();
	@DataSourceTypeAnno(value=DataSourceEnum.tx2)
	public List<Shop> getAllShopByTx2();
	@DataSourceTypeAnno(value=DataSourceEnum.tx3)
	public List<Shop> getAllShopByTx3();
}
