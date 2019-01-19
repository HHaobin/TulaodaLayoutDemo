package com.gjs.gjsmall.home.baby;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.gjs.gjsmall.R;
import com.gjs.http.GetHttp;
import com.javis.Adapter.Adapter_ListView_ware;
import com.lesogo.cu.custom.xListview.XListView;
import com.lesogo.cu.custom.xListview.XListView.IXListViewListener;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;

/**
 * 多个商品展示界面
 * @author
 */
@SuppressLint("SimpleDateFormat")
public class GoodsActivity
        extends Activity implements OnTouchListener, IXListViewListener , OnClickListener
{
    private Bundle bundle;
    //显示所有商品的列表
    private XListView    listView;
    //整个顶部搜索控件，用于隐藏和显示底部整个控件
    private LinearLayout ll_search;
    //返回按钮
    private ImageView    iv_back;
    @SuppressWarnings("unused")
    private EditText     ed_search;
    private String       mSearch;
    //int     goods_to_id=0;
    private AnimationSet animationSet;
    /**第一次按下屏幕时的Y坐标*/
    float fist_down_Y = 0;
    /**请求数据的页数*/
    private int pageIndex = 0;
    /**存储网络返回的数据*/
    String url;
    String aaa;
    int length;
    private TextView       RelativeLayout_ware_text,textview_ware_title;
    private RelativeLayout RelativeLayout_ware;
    String name;
    /**存储网络返回的数据中的data字段*/
    private ArrayList<HashMap<String, Object>> arrayList = new ArrayList<HashMap<String, Object>>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.goods_a);
        initView();
        //请求网络数据
        //new WareTask().execute();

    }

    private void initView() {

        ll_search = (LinearLayout) findViewById(R.id.ll_choice);
        ed_search = (EditText) findViewById(R.id.ed_Searchware);
        iv_back = (ImageView) findViewById(R.id.iv_back);


        RelativeLayout_ware         = (RelativeLayout) findViewById(R.id.RelativeLayout_ware);
        RelativeLayout_ware_text    = (TextView) findViewById(R.id.RelativeLayout_ware_text);
        textview_ware_title = (TextView) findViewById(R.id.textview_ware_title);

        iv_back.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                finish();
            }
        });

        listView = (XListView) findViewById(R.id.listView_ware);
        listView.setOnTouchListener(this);
        listView.setXListViewListener(this);
        // 设置可以进行下拉加载的功能
        listView.setPullLoadEnable(true);
        listView.setPullRefreshEnable(true);
        listView.setVisibility(View.GONE);
//        //先收索一次
//        mSearch = ed_search.getText().toString().trim();
//        //                mSearch=toUtf8(mSearch);
//        //请求网络数据
//        new WareTask().execute();
//       // onRefresh();
//        ed_search.setText("");
        onLoad();

        ed_search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                mSearch = ed_search.getText().toString().trim();
//                mSearch=toUtf8(mSearch);
                //请求网络数据
                new WareTask().execute();
                onRefresh();
                ed_search.setText("");

                return false;
            }
        });

        Bundle bundle = this.getIntent().getExtras();
        //接收num和name值
        name  = bundle.getString("name");
        ed_search.setText(name);
        textview_ware_title.setText(name);
        ed_search.setVisibility(View.GONE);
        RelativeLayout_ware.setVisibility(View.GONE);
        new WareTask().execute();
    }

    @SuppressLint("NewApi")
    @Override
    public boolean onTouch(View arg0, MotionEvent event) {
        float y = event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                //第一次按下时的坐标
                fist_down_Y = y;
                break;
            case MotionEvent.ACTION_MOVE:
                // 向上滑动，隐藏搜索栏
                if (fist_down_Y - y > 250 && ll_search.isShown()) {
                    if (animationSet != null) {
                        animationSet = null;
                    }
                    animationSet = (AnimationSet) AnimationUtils.loadAnimation(this, R.anim.up_out);
                    ll_search.startAnimation(animationSet);
                    ll_search.setY(-100);
                    ll_search.setVisibility(View.GONE);
                }
                // 向下滑动，显示搜索栏
                if (y - fist_down_Y > 250 && !ll_search.isShown()) {
                    if (animationSet != null) {
                        animationSet = null;
                    }
                    animationSet = (AnimationSet) AnimationUtils.loadAnimation(this, R.anim.down_in);
                    ll_search.startAnimation(animationSet);
                    ll_search.setY(0);
                    ll_search.setVisibility(View.VISIBLE);
                }
                break;

        }
        return false;

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
//            case R.id.RelativeLayout_ware_text2:
//                mSearch ="女装";
//                //请求网络数据
//                new WareTask().execute();
//                //onRefresh();
//                ed_search.setText("女装");
//                break;
            default:
                break;
        }
    }

    private class WareTask
            extends AsyncTask<Void, Void, HashMap<String, Object>>
    {

        ProgressDialog dialog = null;

        @Override
        protected void onPreExecute() {
            if (dialog == null) {
                dialog = ProgressDialog.show(GoodsActivity.this, "", "正在加载...");
                dialog.show();
            }


        }

        @Override
        protected HashMap<String, Object> doInBackground(Void... arg0) {

            String t=null;
            try {
                t= URLEncoder.encode(name+"", "UTF-8");//字符编码和文档编码一致
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            url ="http://gjs.wsh68.com/mobile/index.php?act=goods&op=goods_list&key=4&page=10&curpage=1&keyword="+t;

//            if (pageIndex == 0) {
//                url = "http://gjs.wsh68.com/mobile/index.php?act=goods&op=goods_list&key=4&page=10&curpage=1&keyword=" + mSearch;
//            } else {
//                url = "http://192.168.0.111:3000/taoBaoQuery?pageIndex=" + pageIndex;
//            }

            //请求数据，返回json
            String json = GetHttp.RequstGetHttp(url);
            //aaa=url;//测试用
//            ArrayList<HashMap<String, Object>> mArray = new ArrayList<HashMap<String, Object>>();
//            int                                i      = 0;
//            while (i < 10) {
//                HashMap<String, Object> hashMap1 = new HashMap<String, Object>();
//                hashMap1.put("goods_id", "46");
//                hashMap1.put("name", "春装 披肩式 超短款 针织 衫开衫 女装 青鸟 绿色");
//                hashMap1.put("price", "129.00");
//                hashMap1.put("address", "358.00");
//                hashMap1.put("pic",
//                             "http://gjs.wsh68.com/data/upload/shop/common/default_goods_image_360.gif");
//                mArray.add(hashMap1);
//                i++;
//            }
//
//            hashMap = new HashMap<String, Object>();
//            hashMap.put("data", mArray);
            HashMap<String, Object> hashMap1;
            ArrayList<HashMap<String, Object>> mArray1 = new ArrayList<HashMap<String, Object>>();
            HashMap<String, Object> hashMap2 = new HashMap<String, Object>();
            try {

            JSONTokener jsonParser = new JSONTokener(json);
            // 此时还未读取任何json文本，直接读取就是一个JSONObject对象。
            // 如果此时的读取位置在"name" : 了，那么nextValue就是"yuanzhifei89"（String）
            JSONObject person = (JSONObject) jsonParser.nextValue();

            // 接下来的就是JSON对象的操作了
            //person.getJSONArray("phone");
            //person.getString("code");
            //person.getInt("age");
           //String code=person.getString("code");
            JSONObject datas=person.getJSONObject("datas");
            //person.getBoolean("married");

            JSONArray goods_list = datas.getJSONArray("goods_list");
            //String ic_available_rc_balance = member_info.getString("available_rc_balance");
                //aaa=goods_list.length()+"";
                length=goods_list.length();
            if (goods_list.length()!=0) {

                for (int i = 0; i < goods_list.length(); i++) {
                    JSONObject index0        =   goods_list.getJSONObject(i);
                    String     goods_name =index0.getString("goods_name");//名字
                    String     goods_price    = index0.getString("goods_price");//价钱
                    String     goods_marketprice     = index0.getString("goods_marketprice");//原价
                    String     goods_image_url = index0.getString("goods_image_url");//图片
                    String     evaluation_good_star = index0.getString("evaluation_good_star");//星级
                    String     goods_salenum = index0.getString("goods_salenum");//销量
                    String      goods_id = index0.getString("goods_id");//id



                    hashMap1= new HashMap<String, Object>();
                    hashMap1.put("goods_id", goods_id);
                    hashMap1.put("goods_name", goods_name);
                    hashMap1.put("goods_price", goods_price);
                    hashMap1.put("goods_marketprice", goods_marketprice);
                    hashMap1.put("goods_image_url", goods_image_url);
                    hashMap1.put("evaluation_good_star", evaluation_good_star);
                    hashMap1.put("goods_salenum", goods_salenum);
                    mArray1.add(hashMap1);

                }

            }
            }catch (Exception e) {
                // 异常处理代码
            }
            hashMap2.put("data", mArray1);
            //goods_to_id=Integer.getInteger(goods_id);
           // aaa=hashMap2+ "ddddds";
//            hashMap = new HashMap<String, Object>();
//            hashMap.put("data", mArray);
            //aaa="aaaa"+hashMap+ "ddddd";
            return hashMap2;
        }

        @SuppressWarnings("unchecked")
        @Override
        protected void onPostExecute(HashMap<String, Object> result) {

            if (dialog != null && dialog.isShowing()) {
                dialog.dismiss();
                dialog = null;
            }
//            Toast.makeText(WareActivity.this, "size"+aaa, Toast.LENGTH_LONG).show();//测试用
            //            ed_search.setText("size"+aaa);//测试用
            //如果网络数据请求失败，那么显示默认的数据
            if (result != null && result.get("data") != null) {
                //得到data字段的数据
                arrayList.addAll((Collection<? extends HashMap<String, Object>>) result.get("data"));
                if (arrayList.size()!=0){
                    listView.setVisibility(View.VISIBLE);
                    RelativeLayout_ware.setVisibility(View.GONE);
                }else {
                    listView.setVisibility(View.GONE);
                    RelativeLayout_ware.setVisibility(View.VISIBLE);
                    RelativeLayout_ware_text.setText("商品飞到外太空去了...");
                }
//                Toast.makeText(WareActivity.this, "size"+result, Toast.LENGTH_LONG).show();

                listView.setAdapter(new Adapter_ListView_ware(GoodsActivity.this, arrayList));
                listView.setOnItemClickListener(new OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
//                        String   name   = null;
//                        String  price = null;
//                        String   pic   = null;
//                        String  marketprice = null;
//                        String evaluation_good_star= null;
//                        String goods_id= null;
//
//                        goods_id = arrayList.get(position).get("goods_id").toString().trim();
//                        name = arrayList.get(position).get("goods_name").toString().trim();
//                        price = arrayList.get(position).get("goods_price").toString().trim();
//                        pic = arrayList.get(position).get("goods_image_url").toString().trim();
//                        marketprice = arrayList.get(position).get("goods_marketprice").toString().trim();
//                        evaluation_good_star=arrayList.get(position).get("evaluation_good_star").toString().trim();
                        //
                        Intent intent1=new Intent(GoodsActivity.this, BabyActivity.class);
                        //用Bundle携带数据
                        bundle=new Bundle();
                        //传递name参数为tinyphp
                        bundle.putInt("num", Integer.parseInt(arrayList.get(position-1).get("goods_id").toString().trim()));//ed_search.setText("size"+Integer.parseInt(goods_id));
                        intent1.putExtras(bundle);
                        startActivity(intent1);


                    }
                });

            } else {

//                listView.setAdapter(new Adapter_ListView_ware(GoodsActivity.this));
//                listView.setOnItemClickListener(new OnItemClickListener() {
//                    @Override
//                    public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3)
//                    {
//                        Intent intent2 = new Intent(GoodsActivity.this, BabyActivity.class);
//                        //用Bundle携带数据
//                        bundle=new Bundle();
//                        //传递name参数为tinyphp
//                        bundle.putInt("num", 0);
//                        intent2.putExtras(bundle);
//                        startActivity(intent2);
//                    }
//                });
            }

            // 停止刷新和加载
            onLoad();

        }

    }

    /** 刷新 */
    @Override
    public void onRefresh() {
        // 刷新数据
//        pageIndex = 0;
       arrayList.clear();
//        //new WareTask().execute();
//        // 停止刷新和加载

        new WareTask().execute();
        onLoad();
    }

    /** 加载更多 */
    @Override
    public void onLoadMore() {
        pageIndex += 1;
        if (pageIndex >= (length/8) ){
            Toast.makeText(this, "已经最后一页了", Toast.LENGTH_SHORT)
                 .show();
            onLoad();
            return;
        }
        new WareTask().execute();

    }

    /** 停止加载和刷新 */
    private void onLoad() {
        listView.stopRefresh();
        // 停止加载更多
        listView.stopLoadMore();

        // 设置最后一次刷新时间
        listView.setRefreshTime(getCurrentTime(System.currentTimeMillis()));
    }

    /** 简单的时间格式 */
    public static SimpleDateFormat mDateFormat = new SimpleDateFormat("MM-dd HH:mm");

    public static String getCurrentTime(long time) {
        if (0 == time) {
            return "";
        }

        return mDateFormat.format(new Date(time));

    }

}
