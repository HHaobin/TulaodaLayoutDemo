package com.gjs.gjsmall.like;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gjs.gjsmall.R;
import com.gjs.gjsmall.goods.assortment.Assortment_F_1;
import com.gjs.gjsmall.home.cnc.CncActivity;

/**
 * 喜欢 主界面
 * @author
 *
 */
public class Like_F extends Fragment implements View.OnClickListener {
//	private GridView my_gridView_life;
//	private GridView my_gridView_app;
//	private Adapter_GridView adapter_GridView_life;
//	private Adapter_GridView adapter_GridView_app;
//	//资源文件
//	private int[] pic_path_life={R.drawable.find_g_1,R.drawable.find_g_2,R.drawable.find_g_3,R.drawable.find_g_4};
//	private int[] pic_path_app={R.drawable.find_g_5,R.drawable.find_g_6,R.drawable.find_g_7,R.drawable.find_g_8};
//
//	private LinearLayout ll_ShaoYiShao;
//	private LinearLayout ll_game;
//
//	@Override
//	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
//
//		View view=LayoutInflater.from(getActivity()).inflate(R.layout.like_f, null);
//		initView(view);
//		return view;
//	}
//
//
//
//
//	private void initView(View view){
//		ll_ShaoYiShao=(LinearLayout) view.findViewById(R.id.ll_dicover_shao);
//		ll_ShaoYiShao.setOnClickListener(new OnClickListener() {
//			public void onClick(View arg0) {
//				Intent intent = new Intent(getActivity(), CaptureActivity.class);
//				startActivity(intent);
//			}
//		});
//		ll_game=(LinearLayout) view.findViewById(R.id.ll_dicover_game);
//		ll_game.setOnClickListener(new OnClickListener() {
//			public void onClick(View arg0) {
//				Toast.makeText(getActivity(), "此功能暂未开放", Toast.LENGTH_SHORT).show();
//			}
//		});
//		my_gridView_life=(GridView)view.findViewById(R.id.gridView_find_life);
//		my_gridView_life.setSelector(new ColorDrawable(Color.TRANSPARENT));
//		adapter_GridView_life=new Adapter_GridView(getActivity(), pic_path_life);
//		my_gridView_life.setAdapter(adapter_GridView_life);
//		my_gridView_life.setOnItemClickListener(new OnItemClickListener() {
//			@Override
//			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
//									long arg3) {
//				Intent intent = new Intent(getActivity(), WareActivity.class);
//				startActivity(intent);
//			}
//		});
//
//		my_gridView_app=(GridView)view.findViewById(R.id.gridView_find_app);
//		my_gridView_app.setSelector(new ColorDrawable(Color.TRANSPARENT));
//		adapter_GridView_app=new Adapter_GridView(getActivity(), pic_path_app);
//		my_gridView_app.setAdapter(adapter_GridView_app);
//		my_gridView_app.setOnItemClickListener(new OnItemClickListener() {
//			@Override
//			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
//									long arg3) {
//				Intent intent = new Intent(getActivity(), WareActivity.class);
//				startActivity(intent);
//			}
//		});
//	}
//	ImageView fx;
private ViewPager like_pager;
private int currentItem=0;
private LikeAdapter mLikeAdapter;
private String toolsList[];
private TextView like_title_slip_1,like_title_slip_2,like_title_like_tx,like_title_recommend_tx;
private View like_title_product_1,like_title_product_2,like_title_product_3;
public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

	View view = LayoutInflater.from(getActivity()).inflate(R.layout.like_f, null);
	like_title_slip_1= (TextView) view.findViewById(R.id.like_title_slip_1);
	like_title_slip_2= (TextView) view.findViewById(R.id.like_title_slip_2);
	like_title_like_tx= (TextView) view.findViewById(R.id.like_title_like_tx);
	like_title_product_1 =view.findViewById(R.id.like_title_product);
	like_title_product_2 =view.findViewById(R.id.like_title_product_2);
	like_title_product_3 =view.findViewById(R.id.like_title_product3);

	like_title_recommend_tx= (TextView) view.findViewById(R.id.like_title_recommend_tx);
	like_title_like_tx.setOnClickListener(this);
	like_title_recommend_tx.setOnClickListener(this);
	like_title_product_1.setOnClickListener(this);
	like_title_product_2.setOnClickListener(this);
	like_title_product_3.setOnClickListener(this);

	toolsList=new String[]{"喜欢","推荐"};
	//initPager(view);
	return view;
}

	@Override
	public void onClick(View view) {
		Bundle bundle;
		switch (view.getId()){
			case R.id.like_title_like_tx:
				//like_pager.setCurrentItem(view.getId());
				like_title_slip_1.setVisibility(View.VISIBLE);
				like_title_slip_2.setVisibility(View.GONE);
				break;

			case R.id.like_title_recommend_tx:
				//like_pager.setCurrentItem(view.getId());
				like_title_slip_2.setVisibility(View.VISIBLE);
				like_title_slip_1.setVisibility(View.GONE);
				break;
			case R.id.like_title_product:
				Intent intent1=new Intent(getActivity(), CncActivity.class);
				//用Bundle携带数据
				bundle=new Bundle();
				//传递name参数为tinyphp
				bundle.putString("url", "http://gjs.wsh68.com/wap/tmpl/go_store.html?store_id="+1);
				bundle.putString("name", "店铺");
				intent1.putExtras(bundle);
				startActivity(intent1);
				break;
			case R.id.like_title_product_2:
				Intent intent2=new Intent(getActivity(), CncActivity.class);
				//用Bundle携带数据
				bundle=new Bundle();
				//传递name参数为tinyphp
				bundle.putString("url", "http://gjs.wsh68.com/wap/tmpl/go_store.html?store_id="+3);
				bundle.putString("name", "店铺");
				intent2.putExtras(bundle);
				startActivity(intent2);
				break;
			case R.id.like_title_product3:
				Intent intent3=new Intent(getActivity(), CncActivity.class);
				//用Bundle携带数据
				bundle=new Bundle();
				//传递name参数为tinyphp
				bundle.putString("url", "http://gjs.wsh68.com/wap/tmpl/go_store.html?store_id="+4);
				bundle.putString("name", "店铺");
				intent3.putExtras(bundle);
				startActivity(intent3);
				break;
			default:
				break;
		}
	}

	/**
	 * initPager<br/>
	 * 初始化ViewPager控件相关内容
	 */
	private void initPager(View v) {
		mLikeAdapter=new LikeAdapter(getActivity().getSupportFragmentManager());
		like_pager=(ViewPager)v.findViewById(R.id.goods_pager);
		like_pager.setAdapter(mLikeAdapter);
		like_pager.setOnPageChangeListener(onPageChangeListener);
	}

	/**
	 * OnPageChangeListener<br/>
	 * 监听ViewPager选项卡变化事的事件
	 */

	private ViewPager.OnPageChangeListener onPageChangeListener = new ViewPager.OnPageChangeListener() {
		@Override
		public void onPageSelected(int arg0) {
			if(like_pager.getCurrentItem()!=arg0) {
				like_pager.setCurrentItem(arg0);
			}
			if(currentItem!=arg0){

			}
			currentItem=arg0;
		}
		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
		}
		@Override
		public void onPageScrollStateChanged(int arg0) {
		}
	};
	/**
	 * ViewPager 加载选项卡
	 * @author Administrator
	 *
	 */
	private class LikeAdapter extends FragmentPagerAdapter {
		public LikeAdapter(FragmentManager fm) {
			super(fm);
		}
		@Override
		public Fragment getItem(int arg0) {
			//			Fragment fragment =new Fragment_pro_type();
			//			Bundle bundle=new Bundle();
			//			String str=toolsList[arg0];
			//			bundle.putString("typename",str);
			//			fragment.setArguments(bundle);
			Fragment fragment;
//			Bundle bundle=new Bundle();
//			String str = toolsList[arg0];
//			bundle.putString("typename",str);

			switch (arg0){
				case 0:
					fragment=new Assortment_F_1();
					//fragment.setArguments(bundle);
					return fragment;

				case 1:
					fragment=new Assortment_F_1();
					//fragment.setArguments(bundle);
					return fragment;

			}
			fragment=new Assortment_F_1();
			//fragment.setArguments(bundle);
			return fragment;
		}

		@Override
		public int getCount() {
			return toolsList.length;
		}
	}


}