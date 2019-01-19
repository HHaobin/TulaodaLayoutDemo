package com.gjs.gjsmall.cart;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gjs.gjsmall.R;

/**
 * 购物车界面中的降价宝贝界面
 * @author
 *
 */
public class LowBaby_F extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = LayoutInflater.from(getActivity()).inflate(R.layout.cart_low_f, null);
		initView(view);
		return view;
	}
	
	private void initView(View view){
		
		
		
	}

}
