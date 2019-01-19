package com.javis.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.gjs.gjsmall.R;

public class Adapter_GridView extends BaseAdapter {
private Context context;
private int[]   data;
private String[] data1;
	public Adapter_GridView(Context context, int[] data,String[] data1){
		
		this.context=context;
		this.data=data;
		this.data1=data1;
	}
	
	@Override
	public int getCount() {
		return data.length;
	}

	@Override
	public Object getItem(int arg0) {
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		return 0;
	}

	@Override
	public View getView(int position, View currentView, ViewGroup arg2) {
		HolderView holderView=null;
		if (currentView==null) {
			holderView=new HolderView();
			currentView= LayoutInflater.from(context).inflate(R.layout.adapter_grid_home, null);
			holderView.iv_pic=(ImageView) currentView.findViewById(R.id.iv_adapter_grid_pic);
			holderView.iv_text=(TextView) currentView.findViewById(R.id.iv_adapter_grid_text);
			currentView.setTag(holderView);
		}else {
			holderView=(HolderView) currentView.getTag();
		}
		
		
		holderView.iv_pic.setImageResource(data[position]);
		holderView.iv_text.setText(data1[position]);
		//动态变化的轮播图
		int           Slider_height = 0;
		int           Slider_width = 0;
//		//轮播图片宽为屏幕宽度，通知设置宽高比
//		WindowManager wm     = (WindowManager) getActivity().getSystemService(Context.WINDOW_SERVICE);
//		int           width  = wm.getDefaultDisplay().getWidth();
//		int           height  = wm.getDefaultDisplay().getHeight();
//		//此处设置宽高比为7:3
//		Slider_height = (width * 3/4);
//		Slider_width=width;
//		currentView.setLayoutParams(new RelativeLayout.LayoutParams(Slider_width, Slider_height));

		return currentView;
	}
	
	
	public class HolderView {
		
		private ImageView iv_pic;
		private TextView  iv_text;
		
	}

}
