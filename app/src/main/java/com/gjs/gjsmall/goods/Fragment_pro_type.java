package com.gjs.gjsmall.goods;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.gjs.gjsmall.R;
import com.gjs.gjsmall.home.baby.GoodsActivity;

import java.util.ArrayList;


public class Fragment_pro_type extends Fragment {
	private ArrayList<Type>                          list;
	private ImageView                                hint_img;
	private GridView                                 listView;
	private Pro_type_adapter adapter;
	private Type                                     type;
	private ProgressBar                              progressBar;
	private String                                   typename;
	private Bundle bundle;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_pro_type, null);
		progressBar=(ProgressBar) view.findViewById(R.id.progressBar);
		hint_img=(ImageView) view.findViewById(R.id.hint_img);
		listView = (GridView) view.findViewById(R.id.listView);
		typename=getArguments().getString("typename");
		((TextView)view.findViewById(R.id.toptype)).setText(typename);
		GetTypeList();
		adapter=new Pro_type_adapter(getActivity(), list);


		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,long arg3) {
//				Toast.makeText(getActivity(),"111111"+ typename, Toast.LENGTH_LONG).show();
				Intent intent=new Intent(getActivity(), GoodsActivity.class);
				//用Bundle携带数据
				bundle = new Bundle();
				//传递name参数为tinyphp
				bundle.putString("name", typename);
				intent.putExtras(bundle);
				startActivity(intent);

			}
		});
		
		return view;
	}


	private void GetTypeList() {
		list=new ArrayList<Type>();
		for(int i=1;i<35;i++){
			type=new Type(i, typename+i, "");
			list.add(type);
		}	
		progressBar.setVisibility(View.GONE);
	}
	
	
	/*private class LoadTask extends AsyncTask<Void, Void, String>{

		@Override
		protected String doInBackground(Void... params) {
			String name[]=new String[]{"shopid","type"};
			String value[]=new String[]{"0","store"};
			return NetworkHandle.requestBypost("app=u_favorite&act=index",name,value);
		}
		
		@Override
		protected void onPostExecute(String result) {	
			progressBar.setVisibility(View.GONE);
			list=new ArrayList<Shop>();
			try {
				if(Constant.isDebug)System.out.println("result:"+result);
				JSONObject ob=new JSONObject(result);
				if(ob.getString("state").equals("1")){
					arrayToList(ob.getJSONArray("list"));
					adapter=new Love_shop_adapter(getActivity(), list,listView);
					listView.setAdapter(adapter);
					listView.onRefreshComplete();
					if(list.size()<20)
						listView.onPullUpRefreshFail();
					if(list.size()==0)hint_img.setVisibility(View.VISIBLE);
					else hint_img.setVisibility(View.GONE);
				}else{
					//if(tradestate.equals("0"))
						//ResultUtils.handle((Activity_order)getActivity(), ob);
				}
			} catch (Exception e) {
			//	if(tradestate.equals("0"))
					//ResultUtils.handle((Activity_order)getActivity(), "");
				e.printStackTrace();
			}	
		}
	}
	
	private void arrayToList(JSONArray array) throws JSONException{
		JSONObject ob;
		for (int i = 0; i < array.length(); i++) {
			ob=array.getJSONObject(i);
			shop=new Shop(ob.getString("shopid"),ob.getString("shopname"), ob.getString("shoplogo"), ob.getString("weixin"), ob.getString("shopurl"));
			list.add(shop);	
		   }
		}
	*/
	
	/*private class LoadTaskMore extends AsyncTask<Void, Void, String>{
		@Override
		protected String doInBackground(Void... params) {
			String name[]=new String[]{"shopid","type"};
			String value[]=new String[]{list.get(list.size()-1).getShopid(),"store"};
			return NetworkHandle.requestBypost("app=u_favorite&act=index",name,value);
		}
		@Override
		protected void onPostExecute(String result) {
			if(Constant.isDebug)System.out.println("result:"+result);
			try {
				JSONObject ob=new JSONObject(result);
				if(ob.getString("state").equals("1")){
					JSONArray array=ob.getJSONArray("list");
					arrayToList(array);
					if(array.length()>0)
						adapter.notifyDataSetChanged();
					if(array.length()<20)
						listView.onPullUpRefreshFail();
					else 
						listView.onPullUpRefreshComplete();
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		
	}*/
	
	
	
}
