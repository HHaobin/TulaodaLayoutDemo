package com.gjs.gjsmall.goods.assortment;

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
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.gjs.gjsmall.R;
import com.gjs.gjsmall.goods.Pro_type_adapter;
import com.gjs.gjsmall.goods.Type;
import com.gjs.gjsmall.home.baby.GoodsActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 分类页面 子页面
 *
 */
public class Assortment_F_1 extends Fragment
{
    private ArrayList<Type>  list;
    private ImageView        hint_img;
    private Pro_type_adapter adapter;
    private Type             type;
    private ProgressBar      progressBar;
    private String           typename;
    private String[] name_list={"笔记本","手机","空调","坚果","机油","衬衫","美体塑身","辅导教材"};
    private GridView gview;
    private List<Map<String, Object>> data_list;
    private SimpleAdapter sim_adapter;
    // 图片封装为一个数组
    private int[] icon = { R.drawable.assortment_list_1, R.drawable.assortment_list_2,
                           R.drawable.assortment_list_3, R.drawable.assortment_list_4, R.drawable.assortment_list_5,
                           R.drawable.assortment_list_6, R.drawable.assortment_list_7, R.drawable.assortment_list_8};
    private Bundle bundle;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.assortment_f, null);
        progressBar=(ProgressBar) view.findViewById(R.id.progressBar);
        hint_img=(ImageView) view.findViewById(R.id.hint_img);
        gview = (GridView) view.findViewById(R.id.listView);
        typename=getArguments().getString("typename");
        ((TextView)view.findViewById(R.id.toptype)).setText(typename);
        adapter=new Pro_type_adapter(getActivity(), list);
        secletinit();//选择改变菜单
        gview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
//                Toast.makeText(getActivity(), "111111"+ typename, Toast.LENGTH_LONG).show();
                Intent intent=new Intent(getActivity(), GoodsActivity.class);
                //用Bundle携带数据
                bundle = new Bundle();
                //传递name参数为tinyphp
                bundle.putString("name", name_list[arg2]);
                intent.putExtras(bundle);
                startActivity(intent);

            }
        });
        //新建List
        data_list = new ArrayList<Map<String, Object>>();
        //获取数据
        getData();
        //新建适配器
        String [] from ={"image","text"};
        int [] to = {R.id.image,R.id.text};
        sim_adapter = new SimpleAdapter(getActivity(), data_list, R.layout.item, from, to);
        //配置适配器
        gview.setAdapter(sim_adapter);

        GetTypeList();
        return view;
    }

    private void secletinit() {
        if(typename.equals("推荐分类")){

        }else if(typename.equals("靓丽女装")){
            name_list= new String[]{"针织衫", "雪纺衫", "蕾丝衫", "衬衫", "T恤", "毛衣", "风衣", "裙"};
            icon = new int[]{R.drawable.assortment_list_6, R.drawable.assortment_list_6,
                             R.drawable.assortment_list_6, R.drawable.assortment_list_6,
                             R.drawable.assortment_list_6, R.drawable.assortment_list_6,
                             R.drawable.assortment_list_6, R.drawable.assortment_list_6};
        }
    }

    public List<Map<String, Object>> getData(){
        //cion和iconName的长度是相同的，这里任选其一都可以
        for(int i=0;i<name_list.length;i++){
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("image", icon[i]);
            map.put("text", name_list[i]);
            data_list.add(map);
        }

        return data_list;
    }

    private void GetTypeList() {
//        list=new ArrayList<Type>();
//        for(int i=1;i<name_list.length;i++){
//            type=new Type(i, name_list[i].toString(), "");
//            list.add(type);
//        }
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
