package com.gjs.gjsmall.Data;

import java.util.ArrayList;
import java.util.HashMap;


/**
 * 静态数据类
 * @author
 *
 */
public class Data  {
	/**保存添加到购物车的数据*/
	public static int                                arrayList_cart_id =0;
	/**保存添加到购物车的数据*/
	public static ArrayList<HashMap<String, Object>> arrayList_cart    =new ArrayList<HashMap<String,Object>>();
	/**保存购物车中选择的商品的总价数据*/
	public static float                              Allprice_cart     =0;

	public static String                              baby_goods_id     ="";
	public static String                              baby_goods_price     ="";
	public static String                              baby_goods_ku     ="";
	public static String                              baby_goods_pic     ="";
}

