package com.gjs.gjsmall.cart;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.gjs.gjsmall.R;

/**
 * 招商主界面
 *
 * @author
 */
//implements OnClickListener
public class Cart_F extends Fragment

{

	//顶部标题栏
	private TextView  tv_top_title;
	//定位
	private ImageView iv_add;
	//菜单
	private View bus_menu_0, bus_menu_1, bus_menu_2, bus_menu_3,
			      bus_menu_4, bus_menu_5, bus_menu_6, bus_menu_7,
					iv_shao;

	private View show_cart_all, show_cart_low, show_cart_stock;
	private AllBaby_F   allBaby_F;
	private LowBaby_F   lowBaby_F;
	private StockBaby_F stockBaby_F;
	private boolean isDel=true;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = LayoutInflater.from(getActivity()).inflate(R.layout.cart_f, null);
		//initView(view);
		return view;
	}

//	private void initView(View view) {
//
//
//		//定位，扫瞄
//		iv_shao= view.findViewById(R.id.iv_shao);
//		iv_shao.setVisibility(view.GONE);
//		iv_shao.setOnClickListener(new OnClickListener() {
//			@Override
//			public void onClick(View arg0) {
//				//跳转到二维码扫描
//				Intent intent =new Intent(getActivity(), CaptureActivity.class);
//				startActivity(intent);
//			}
//		});
//
//		//搜索
//		tv_top_title=(TextView) view.findViewById(R.id.tv_main_top_title);
//		tv_top_title.setText("招商");
//		tv_top_title.setOnClickListener(new OnClickListener() {
//			@Override
//			public void onClick(View arg0) {
//				//挑战到宝贝搜索界面
//				Intent intent =new Intent(getActivity(), WareActivity.class);
//				startActivity(intent);
//			}
//		});
//
//
//		//定位，扫瞄
//		iv_add=(ImageView) view.findViewById(R.id.iv_add);
//		iv_add.setVisibility(view.VISIBLE);
//		iv_add.setOnClickListener(new OnClickListener() {
//			@Override
//			public void onClick(View arg0) {
//				//跳转到二维码扫描
//				Intent intent =new Intent(getActivity(), CaptureActivity.class);
//				startActivity(intent);
//			}
//		});
//
//		bus_menu_0 =  view.findViewById(R.id.bus_menu_0);
//		bus_menu_1 =  view.findViewById(R.id.bus_menu_1);
//		bus_menu_2 =  view.findViewById(R.id.bus_menu_2);
//		bus_menu_3 =  view.findViewById(R.id.bus_menu_3);
//		bus_menu_4 =  view.findViewById(R.id.bus_menu_4);
//		bus_menu_5 =  view.findViewById(R.id.bus_menu_5);
//		bus_menu_6 =  view.findViewById(R.id.bus_menu_6);
//		bus_menu_7 =  view.findViewById(R.id.bus_menu_7);
//
////		show_cart_all = view.findViewById(R.id.show_cart_all);
////		show_cart_low = view.findViewById(R.id.show_cart_low);
////		show_cart_stock = view.findViewById(R.id.show_cart_stock);
//
//		bus_menu_0.setOnClickListener(this);
//		bus_menu_1.setOnClickListener(this);
//		bus_menu_2.setOnClickListener(this);
//		bus_menu_3.setOnClickListener(this);
//		bus_menu_4.setOnClickListener(this);
//		bus_menu_5.setOnClickListener(this);
//		bus_menu_6.setOnClickListener(this);
//		bus_menu_7.setOnClickListener(this);
//
//
//
//	}
//
//	@Override
//	public void onClick(View v) {
//		switch (v.getId()) {
//			case R.id.bus_menu_0:
//				//
//				//Intent intent0=new Intent(getActivity(), WareActivity.class);
//				//
//				Intent intent0=new Intent(getActivity(), ExplainActivity.class);
//				//用Bundle携带数据
//				Bundle bundle0=new Bundle();
//				//传递name参数为tinyphp
//				bundle0.putString("num", "0");
//				intent0.putExtras(bundle0);
//				startActivity(intent0);
//
//				break;
//
//			case R.id.bus_menu_1:
//				//
//				//Intent intent1=new Intent(getActivity(), WareActivity.class);
//				Intent intent1=new Intent(getActivity(), ExplainActivity.class);
//				//用Bundle携带数据
//				Bundle bundle1=new Bundle();
//				//传递name参数为tinyphp
//				bundle1.putString("num", "1");
//				intent1.putExtras(bundle1);
//				startActivity(intent1);
//				break;
//
//			case R.id.bus_menu_2:
//				//
//				//Intent intent2=new Intent(getActivity(), WareActivity.class);
//				Intent intent2=new Intent(getActivity(), ExplainActivity.class);
//				//用Bundle携带数据
//				Bundle bundle2=new Bundle();
//				//传递name参数为tinyphp
//				bundle2.putString("num", "2");
//				intent2.putExtras(bundle2);
//				startActivity(intent2);
//				break;
//
//			case R.id.bus_menu_3:
//				//
//				//Intent intent3=new Intent(getActivity(), WareActivity.class);
//				Intent intent3=new Intent(getActivity(), ExplainActivity.class);
//				//用Bundle携带数据
//				Bundle bundle3=new Bundle();
//				//传递name参数为tinyphp
//				bundle3.putString("num", "3");
//				intent3.putExtras(bundle3);
//				startActivity(intent3);
//				break;
//
//			case R.id.bus_menu_4:
//				//
//				//Intent intent4=new Intent(getActivity(), WareActivity.class);
//				Intent intent4=new Intent(getActivity(), ExplainActivity.class);
//				//用Bundle携带数据
//				Bundle bundle4=new Bundle();
//				//传递name参数为tinyphp
//				bundle4.putString("num", "4");
//				intent4.putExtras(bundle4);
//				startActivity(intent4);
//				break;
//
//			case R.id.bus_menu_5:
//				//
//				//Intent intent5=new Intent(getActivity(), WareActivity.class);
//				Intent intent5=new Intent(getActivity(), ExplainActivity.class);
//				//用Bundle携带数据
//				Bundle bundle5=new Bundle();
//				//传递name参数为tinyphp
//				bundle5.putString("num", "5");
//				intent5.putExtras(bundle5);
//				startActivity(intent5);
//				break;
//
//			case R.id.bus_menu_6:
//				//
//				//Intent intent6=new Intent(getActivity(), WareActivity.class);
//				Intent intent6=new Intent(getActivity(), ExplainActivity.class);
//				//用Bundle携带数据
//				Bundle bundle6=new Bundle();
//				//传递name参数为tinyphp
//				bundle6.putString("num", "6");
//				intent6.putExtras(bundle6);
//				startActivity(intent6);
//				break;
//
//			case R.id.bus_menu_7:
//				//
//				//Intent intent7=new Intent(getActivity(), WareActivity.class);
//				Intent intent7=new Intent(getActivity(), ExplainActivity.class);
//				//用Bundle携带数据
//				Bundle bundle7=new Bundle();
//				//传递name参数为tinyphp
//				bundle7.putString("num", "7");
//				intent7.putExtras(bundle7);
//				startActivity(intent7);
//				break;
//
//			default:
//				break;
//		}
//	}
//

}
