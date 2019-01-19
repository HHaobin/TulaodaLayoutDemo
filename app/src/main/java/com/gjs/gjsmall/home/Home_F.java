package com.gjs.gjsmall.home;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import com.gjs.gjsmall.R;
import com.gjs.gjsmall.home.baby.GoodsActivity;
import com.gjs.gjsmall.home.baby.WareActivity;
import com.gjs.gjsmall.home.cnc.CncActivity;
import com.gjs.gjsmall.home.detail.ActivityHomeList1;
import com.gjs.gjsmall.home.gjs.GjsShopActivity;
import com.gjs.gjsmall.home.map.MapActivity;
import com.gjs.gjsmall.home.points.Point_FA;
import com.gjs.gjsmall.user.User_life;
import com.javis.ab.view.AbOnItemClickListener;
import com.javis.ab.view.AbSlidingPlayView;

import java.util.ArrayList;

/**
 * 首页
 *
 */
public class Home_F extends Fragment
        implements OnClickListener
{
    /**第一次按下屏幕时的Y坐标*/
    float fist_down_Y = 0;
    private ScrollView scrollview_main;
    private View       main_hot;
    //顶部标题栏
    private View       tv_top_title, tv_main_top_title, iv_refresh, iv_shao, top_title;
    //九宫格图片
    private Button menu_guide_pic_1, menu_guide_pic_2, menu_guide_pic_3, menu_guide_pic_4, menu_guide_pic_5, menu_guide_pic_6, menu_guide_pic_7, menu_guide_pic_8, menu_guide_pic_9, menu_guide_pic_10;
    //    //九宫格文本
    //    private TextView menu_guide_text_1,menu_guide_text_2,menu_guide_text_3,menu_guide_text_4,menu_guide_text_5,
    //                    menu_guide_text_6,menu_guide_text_7,menu_guide_text_8,menu_guide_text_9,menu_guide_text_10;
    //    //九宫格view
    //    private View menu_guide_1,menu_guide_2,menu_guide_3,menu_guide_4,menu_guide_5,
    //                menu_guide_6,menu_guide_7,menu_guide_8,menu_guide_9,menu_guide_10;


    //首页轮播
    private AbSlidingPlayView viewPager;
    // 分类九宫格的资源文件
    //private int[] pic_path_classify = { R.drawable.menu_guide_1, R.drawable.menu_guide_2, R.drawable.menu_guide_3, R.drawable.menu_guide_4, R.drawable.menu_guide_5, R.drawable.menu_guide_6, R.drawable.menu_guide_7, R.drawable.menu_guide_8,R.drawable.menu_guide_9, R.drawable.menu_guide_10 };
    //private String[] text_path_classify = {"逛集市商城","积分换购","活动回馈","美食","签到","附近商家","1元众筹","CNC","旅行","充值"};
    /**存储首页轮播的界面*/
    private ArrayList<View>   allListView;
    /**首页轮播的界面的资源*/
    private int[] resId = {R.drawable.show_m1,
                           R.drawable.show_m2,
                           R.drawable.show_m3,
                           R.drawable.show_m4,
                           R.drawable.show_m5,
                           R.drawable.show_m6};
    private ImageView idea_bus_1, idea_bus_2, idea_bus_3, idea_bus_4, main_hot_1, main_hot_2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {

        View view = LayoutInflater.from(getActivity())
                                  .inflate(R.layout.home_f, null);
        initView(view);

        return view;
    }

    @TargetApi(Build.VERSION_CODES.M)
    private void initView(View view) {


        //定位，扫瞄
        iv_shao = view.findViewById(R.id.iv_shao);
        iv_shao.setVisibility(view.VISIBLE);
        iv_shao.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                //                //跳转到二维码扫描
                //                Intent intent =new Intent(getActivity(), CaptureActivity.class);
                //                startActivity(intent);

                //跳转到定位
                Intent intent = new Intent(getActivity(), MapActivity.class);
                startActivity(intent);

            }
        });
        top_title = view.findViewById(R.id.top_title);
        top_title.getBackground()
                 .setAlpha(0);
//        top_title.getBackground().setAlpha(1);
        tv_main_top_title = view.findViewById(R.id.tv_main_top_title_1);
        tv_main_top_title.setVisibility(view.GONE);
        //搜索
        tv_top_title = view.findViewById(R.id.tv_top_title_main);
        tv_top_title.setVisibility(view.VISIBLE);
        tv_top_title.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                //挑战到宝贝搜索界面
                Intent intent = new Intent(getActivity(), WareActivity.class);
                startActivity(intent);
            }
        });


        //扫瞄，消息
        iv_refresh = view.findViewById(R.id.iv_refresh);
        iv_refresh.setVisibility(view.VISIBLE);
        iv_refresh.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                //                //跳转到二维码扫描
                //                Intent intent =new Intent(getActivity(), CaptureActivity.class);
                //                startActivity(intent);
                // CCPAppManager.callVoIPAction(getActivity(), ECVoIPCallManager.CallType.VOICE , "13510534212", "13510534212", true);
                //CCPAppManager.showCallMenu(getActivity(), "13510534212", "13510534212");
                Bundle bundle;
                Intent intent3 = new Intent(getActivity(), ExplainActivity.class);
                //用Bundle携带数据
                bundle = new Bundle();
                //传递name参数为tinyphp
                bundle.putString("num", "1");
                bundle.putString("name", "我的消息");
                intent3.putExtras(bundle);
                startActivity(intent3);
            }
        });


        viewPager = (AbSlidingPlayView) view.findViewById(R.id.viewPager_menu1);
        //设置播放方式为顺序播放
        viewPager.setPlayType(1);
        //设置播放间隔时间
        viewPager.setSleepTime(3000);
        initViewPager();
        initButton(view);

//        int[] position0 = new int[2];
//        viewPager.getLocationInWindow(position0);
//        Toast.makeText(getActivity().getApplicationContext(),""+position0[0]+"ppp"+position0[1], Toast.LENGTH_LONG)
//                          .show();
//        Log.d("aaaaaaaaaaaaaaaaaaaaa", ""+position0[0]+"ppp"+position0[1], null);

//        scrollview_main= (ScrollView) view.findViewById(R.id.scrollview_main);
//        scrollview_main.setOnTouchListener(new View.OnTouchListener(){
//
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                int[] position = new int[2];
//                viewPager.getLocationOnScreen(position);
//                int b = 0;
//                int a = (int)position[1];
//
//                float y = event.getY();
//
////                Toast.makeText(getActivity().getApplicationContext(),""+position[0]+"ppp"+position[1], Toast.LENGTH_LONG)
////                     .show();
////                Log.d("..sssss...", ""+position[0]+"ppp"+position[1], null);
//
//                switch (event.getAction()) {
//                    case MotionEvent.ACTION_DOWN:
//                        //第一次按下时的坐标
//                        //fist_down_Y = y;
//                        fist_down_Y = y;
//                        break;
//                    case MotionEvent.ACTION_MOVE:
////                        if(position[1]>50){
////                            top_title.getBackground()
////                                     .setAlpha(100);
////                        }
//                      //  a=a+(int)position[1]*10;//(fist_down_Y - y)/200)*100
//                        // 向上滑动，隐藏搜索栏
//                        if (a-75<=0&&b<=255) {
//                        //fist_down_Y - y > 250&&
//                            b=b-(a*5);
//                            top_title.getBackground().setAlpha(b);
//
//                        }
////                        // 向下滑动，显示搜索栏
////                        if (a-75>0&&b>=0) {
////                            //y - fist_down_Y > 250 &&
////                            //                    if (animationSet != null) {
////                            //                        animationSet = null;
////                            //                    }
////                            //                    animationSet = (AnimationSet) AnimationUtils.loadAnimation(this, R.anim.down_in);
////                            //                    ll_search.startAnimation(animationSet);
////                            //                    ll_search.setY(0);
////                            //                    ll_search.setVisibility(View.VISIBLE);
////                            b=b+a*5;
////                            top_title.getBackground().setAlpha(b+a);
////                        }
//                        break;
//
//                }
//                return false;
//            }
//        });
    }

    private void initButton(View v) {

        //初始化
        menu_guide_pic_1 = (Button) v.findViewById(R.id.menu_guide_pic_1);
        menu_guide_pic_2 = (Button) v.findViewById(R.id.menu_guide_pic_2);
        menu_guide_pic_3 = (Button) v.findViewById(R.id.menu_guide_pic_3);
        menu_guide_pic_4 = (Button) v.findViewById(R.id.menu_guide_pic_4);
        menu_guide_pic_5 = (Button) v.findViewById(R.id.menu_guide_pic_5);
        menu_guide_pic_6 = (Button) v.findViewById(R.id.menu_guide_pic_6);
        menu_guide_pic_7 = (Button) v.findViewById(R.id.menu_guide_pic_7);
        menu_guide_pic_8 = (Button) v.findViewById(R.id.menu_guide_pic_8);
        menu_guide_pic_9 = (Button) v.findViewById(R.id.menu_guide_pic_9);
        menu_guide_pic_10 = (Button) v.findViewById(R.id.menu_guide_pic_10);
        main_hot_1 = (ImageView) v.findViewById(R.id.main_hot_1);
        main_hot_2 = (ImageView) v.findViewById(R.id.main_hot_2);
        idea_bus_1 = (ImageView) v.findViewById(R.id.idea_bus_1);
        idea_bus_2 = (ImageView) v.findViewById(R.id.idea_bus_2);
        idea_bus_3 = (ImageView) v.findViewById(R.id.idea_bus_3);
        idea_bus_4 = (ImageView) v.findViewById(R.id.idea_bus_4);


        main_hot = v.findViewById(R.id.main_hot);

        menu_guide_pic_1.setOnClickListener(this);
        //      menu_guide_pic_1.getParent().requestDisallowInterceptTouchEvent(false);
        menu_guide_pic_2.setOnClickListener(this);
        menu_guide_pic_3.setOnClickListener(this);
        menu_guide_pic_4.setOnClickListener(this);
        menu_guide_pic_5.setOnClickListener(this);
        menu_guide_pic_6.setOnClickListener(this);
        menu_guide_pic_7.setOnClickListener(this);
        menu_guide_pic_8.setOnClickListener(this);
        menu_guide_pic_9.setOnClickListener(this);
        menu_guide_pic_10.setOnClickListener(this);
        main_hot_1.setOnClickListener(this);
        main_hot_2.setOnClickListener(this);
        idea_bus_1.setOnClickListener(this);
        idea_bus_2.setOnClickListener(this);
        idea_bus_3.setOnClickListener(this);
        idea_bus_4.setOnClickListener(this);


        //        menu_guide_pic_1.setOnTouchListener(new View.OnTouchListener() {
        //            @Override
        //            public boolean onTouch(View v, MotionEvent event) {
        //                Log.d("TAG", "onTouch execute, action " + event.getAction());
        //                return true;
        //            }
        //        });

        //动态变化的轮播图
        int Slider_height = 0;
        int Slider_width  = 0;
        //轮播图片宽为屏幕宽度，通知设置宽高比
        WindowManager wm     = (WindowManager) getActivity().getSystemService(Context.WINDOW_SERVICE);
        int           width  = wm.getDefaultDisplay()
                                 .getWidth();
        int           height = wm.getDefaultDisplay()
                                 .getHeight();
        //此处设置宽高比为7:3
        Slider_height = (width * 1 / 8);
        Slider_width = Slider_height;
        //设置一个布局属性
        RelativeLayout.LayoutParams lp1 = new RelativeLayout.LayoutParams(Slider_width,
                                                                          Slider_height);
        lp1.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        lp1.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
        main_hot.setLayoutParams(new LinearLayout.LayoutParams(width, (int) (width * 1 / (3.7))));

        menu_guide_pic_1.setLayoutParams(lp1);
        menu_guide_pic_2.setLayoutParams(lp1);
        menu_guide_pic_3.setLayoutParams(lp1);
        menu_guide_pic_4.setLayoutParams(lp1);
        menu_guide_pic_5.setLayoutParams(lp1);
        menu_guide_pic_6.setLayoutParams(lp1);
        menu_guide_pic_7.setLayoutParams(lp1);
        menu_guide_pic_8.setLayoutParams(lp1);
        menu_guide_pic_9.setLayoutParams(lp1);
        menu_guide_pic_10.setLayoutParams(lp1);
        //设置九宫格文本大小
        //menu_guide_text_1.setLayoutParams(new RelativeLayout.LayoutParams((int) (Slider_width*(1.2)), Slider_width/2));
        //        menu_guide_text_1.setTextSize(TypedValue.COMPLEX_UNIT_SP, (float) (Slider_width/(12.9)));
        //        menu_guide_text_2.setTextSize(TypedValue.COMPLEX_UNIT_SP, Slider_width/12);
        //        menu_guide_text_3.setTextSize(TypedValue.COMPLEX_UNIT_SP, Slider_width/12);
        //        menu_guide_text_4.setTextSize(TypedValue.COMPLEX_UNIT_SP, Slider_width/12);
        //        menu_guide_text_5.setTextSize(TypedValue.COMPLEX_UNIT_SP, Slider_width/12);
        //        menu_guide_text_6.setTextSize(TypedValue.COMPLEX_UNIT_SP, Slider_width/12);
        //        menu_guide_text_7.setTextSize(TypedValue.COMPLEX_UNIT_SP, Slider_width/12);
        //        menu_guide_text_8.setTextSize(TypedValue.COMPLEX_UNIT_SP, Slider_width/12);
        //        menu_guide_text_9.setTextSize(TypedValue.COMPLEX_UNIT_SP, Slider_width/12);
        //        menu_guide_text_10.setTextSize(TypedValue.COMPLEX_UNIT_SP, Slider_width/12);
        //menu_guide_text_1.setGravity(View.TEXT_ALIGNMENT_CENTER);
    }

    private void initViewPager() {

        if (allListView != null) {
            allListView.clear();
            allListView = null;
        }
        allListView = new ArrayList<View>();

        for (int i = 0; i < resId.length; i++) {
            //导入ViewPager的布局
            View      view      = LayoutInflater.from(getActivity())
                                                .inflate(R.layout.pic_item, null);
            ImageView imageView = (ImageView) view.findViewById(R.id.pic_item);
            imageView.setImageResource(resId[i]);
            allListView.add(view);
        }


        viewPager.addViews(allListView);
        //开始轮播
        viewPager.startPlay();
        viewPager.setOnItemClickListener(new AbOnItemClickListener() {
            @Override
            public void onClick(int position) {
                String[] url = {};
                Bundle   bundle;
                //
                Intent intent8 = new Intent(getActivity(), CncActivity.class);
                //用Bundle携带数据
                bundle = new Bundle();
                //传递name参数为tinyphp
                bundle.putString("url", "http://gjs.wsh68.com/wap/");
                bundle.putString("name", "逛集市App");
                intent8.putExtras(bundle);
                startActivity(intent8);
            }
        });

        //判断是否有网络
        if (isNetworkAvailable(getActivity())) {
            Toast.makeText(getActivity().getApplicationContext(), "当前有可用网络！", Toast.LENGTH_LONG)
                 .show();

        } else {
            Toast.makeText(getActivity().getApplicationContext(), "当前没有可用网络！", Toast.LENGTH_LONG)
                 .show();

        }


        //动态变化的轮播图
        int Slider_height = 0;
        int Slider_width  = 0;
        //轮播图片宽为屏幕宽度，通知设置宽高比
        WindowManager wm     = (WindowManager) getActivity().getSystemService(Context.WINDOW_SERVICE);
        int           width  = wm.getDefaultDisplay()
                                 .getWidth();
        int           height = wm.getDefaultDisplay()
                                 .getHeight();
        //此处设置宽高比为7:3
        Slider_height = (width * 525 / 1076);
        Slider_width = width;
        viewPager.setLayoutParams(new RelativeLayout.LayoutParams(Slider_width, Slider_height));
    }

    @Override
    public void onClick(View v) {
        Bundle bundle;
        switch (v.getId()) {
            case R.id.menu_guide_pic_1:
                //
                Intent intent1 = new Intent(getActivity(), GjsShopActivity.class);
                //用Bundle携带数据
                bundle = new Bundle();
                //传递name参数为tinyphp
                bundle.putString("url",
                                 "http://gjs.wsh68.com/shop/index.php?act=show_store&op=index&store_id=1");
                bundle.putString("name", "逛集市商城");
                intent1.putExtras(bundle);
                startActivity(intent1);
                break;
            case R.id.menu_guide_pic_2:
                //
                Intent intent2 = new Intent(getActivity(), Point_FA.class);
                startActivity(intent2);
                break;
            case R.id.menu_guide_pic_3:
                //
                Intent intent3 = new Intent(getActivity(), ExplainActivity.class);
                //用Bundle携带数据
                bundle = new Bundle();
                //传递name参数为tinyphp
                bundle.putString("num", "3");
                bundle.putString("name", "爱心基金");
                intent3.putExtras(bundle);
                startActivity(intent3);
                break;
            case R.id.menu_guide_pic_4:
                //
                Intent intent4 = new Intent(getActivity(), ExplainActivity.class);
                //用Bundle携带数据
                bundle = new Bundle();
                //传递name参数为tinyphp
                bundle.putString("num", "4");
                bundle.putString("name", "美食坊");
                intent4.putExtras(bundle);
                startActivity(intent4);
                break;
            case R.id.menu_guide_pic_5:
                //
                Intent intent5 = new Intent(getActivity(), ExplainActivity.class);
                //用Bundle携带数据
                bundle = new Bundle();
                //传递name参数为tinyphp
                bundle.putString("num", "5");
                bundle.putString("name", "领积分");
                intent5.putExtras(bundle);
                startActivity(intent5);
                break;
            case R.id.menu_guide_pic_6:
                //                //
                //                Intent intent6=new Intent(getActivity(), ExplainActivity.class);
                //                //用Bundle携带数据
                //                bundle=new Bundle();
                //                //传递name参数为tinyphp
                //                bundle.putString("num", "6");
                //                bundle.putString("name", "逛集市商城");
                //                intent6.putExtras(bundle);
                //                startActivity(intent6);
                Intent intent6 = new Intent(getActivity(), ActivityHomeList1.class);
                startActivity(intent6);
                break;
            case R.id.menu_guide_pic_7:
                //
                Intent intent7 = new Intent(getActivity(), ExplainActivity.class);
                //用Bundle携带数据
                bundle = new Bundle();
                //传递name参数为tinyphp
                bundle.putString("num", "7");
                bundle.putString("name", "一元众筹");
                intent7.putExtras(bundle);
                startActivity(intent7);
                break;
            case R.id.menu_guide_pic_8:
                //
                Intent intent8 = new Intent(getActivity(), CncActivity.class);
                //用Bundle携带数据
                bundle = new Bundle();
                //传递name参数为tinyphp
                bundle.putString("url", "http://www.cnconrich.com");
                bundle.putString("name", "CnC消费平台");
                intent8.putExtras(bundle);
                startActivity(intent8);
                break;
            case R.id.menu_guide_pic_9:
                //
                Intent intent9 = new Intent(getActivity(), User_life.class);
                //用Bundle携带数据
                //                Bundle bundle9=new Bundle();
                //                //传递name参数为tinyphp
                //                bundle9.putString("num", "9");
                //                intent9.putExtras(bundle9);
                //                bundle9.putString("name", "抽奖");
                startActivity(intent9);
                ;
                break;
            case R.id.menu_guide_pic_10:
                //
                Intent intent10 = new Intent(getActivity(), ExplainActivity.class);
                //用Bundle携带数据
                bundle = new Bundle();
                //传递name参数为tinyphp
                bundle.putString("num", "10");
                bundle.putString("name", "充值");
                intent10.putExtras(bundle);
                startActivity(intent10);
                //                Intent intent10=new Intent(getActivity(), LoginActivity.class);
                //                startActivity(intent10);
                break;
            case R.id.main_hot_1:
                //
                Intent intent11 = new Intent(getActivity(), CncActivity.class);
                //用Bundle携带数据
                bundle = new Bundle();
                //传递name参数为tinyphp
                bundle.putString("url", "http://gjs.wsh68.com/wap/");
                bundle.putString("name", "逛集市App");
                intent11.putExtras(bundle);
                startActivity(intent11);
                break;
            case R.id.main_hot_2:
                //
                Intent intent12 = new Intent(getActivity(), CncActivity.class);
                //用Bundle携带数据
                bundle = new Bundle();
                //传递name参数为tinyphp
                bundle.putString("url", "http://gjs.wsh68.com/wap/");
                bundle.putString("name", "逛集市App");
                intent12.putExtras(bundle);
                startActivity(intent12);
                break;
            case R.id.idea_bus_1:
                //
                Intent intent13 = new Intent(getActivity(), GoodsActivity.class);
                //用Bundle携带数据
                bundle = new Bundle();
                //传递name参数为tinyphp
                bundle.putString("name", "眼镜");
                intent13.putExtras(bundle);
                startActivity(intent13);
                break;
            case R.id.idea_bus_2:
                //
                Intent intent14 = new Intent(getActivity(), GoodsActivity.class);
                //用Bundle携带数据
                bundle = new Bundle();
                //传递name参数为tinyphp
                bundle.putString("name", "电壶");
                intent14.putExtras(bundle);
                startActivity(intent14);
                break;
            case R.id.idea_bus_3:
                //
                Intent intent15 = new Intent(getActivity(), GoodsActivity.class);
                //用Bundle携带数据
                bundle = new Bundle();
                //传递name参数为tinyphp
                bundle.putString("name", "自行车");
                intent15.putExtras(bundle);
                startActivity(intent15);
                break;
            case R.id.idea_bus_4:
                //
                Intent intent16 = new Intent(getActivity(), GoodsActivity.class);
                //用Bundle携带数据
                bundle = new Bundle();
                //传递name参数为tinyphp
                bundle.putString("name", "电话");
                intent16.putExtras(bundle);
                startActivity(intent16);
                break;

            default:
                break;
        }
    }
    //跳转到其他的app
    //    public void intenter() {
    //        // 通过包名获取要跳转的app，创建intent对象
    //        Intent intent = getActivity().getPackageManager().getLaunchIntentForPackage("com.weihua.superphone");
    //// 这里如果intent为空，就说名没有安装要跳转的应用嘛
    //        if (intent != null) {
    //            // 这里跟Activity传递参数一样的嘛，不要担心怎么传递参数，还有接收参数也是跟Activity和Activity传参数一样
    ////			intent.putExtra("name", "Liu xiang");
    ////			intent.putExtra("birthday", "1983-7-13");
    //            startActivity(intent);
    //        } else {
    //            // 没有安装要跳转的app应用，提醒一下
    //            Toast.makeText(getActivity().getApplicationContext(), "哟，赶紧下载安装这个APP吧", Toast.LENGTH_LONG).show();
    //        }
    //    }


    /**
     * 检查当前网络是否可用
     *
     * @param
     * @return
     */

    public boolean isNetworkAvailable(Activity activity)
    {
        Context context = activity.getApplicationContext();
        // 获取手机所有连接管理对象（包括对wi-fi,net等连接的管理）
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(
                Context.CONNECTIVITY_SERVICE);

        if (connectivityManager == null) {
            return false;
        } else {
            // 获取NetworkInfo对象
            NetworkInfo[] networkInfo = connectivityManager.getAllNetworkInfo();

            if (networkInfo != null && networkInfo.length > 0) {
                for (int i = 0; i < networkInfo.length; i++) {
                    System.out.println(i + "===状态===" + networkInfo[i].getState());
                    System.out.println(i + "===类型===" + networkInfo[i].getTypeName());
                    // 判断当前网络状态是否为连接状态
                    if (networkInfo[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        }
        return false;
    }


//    @SuppressLint("NewApi")
//    @Override
//    public boolean onTouch(View arg0, MotionEvent event) {
//        float y = event.getY();
//        switch (event.getAction()) {
//            case MotionEvent.ACTION_DOWN:
//                //第一次按下时的坐标
//                fist_down_Y = y;
//                break;
//            case MotionEvent.ACTION_MOVE:
//                float a = new Float(0);
//                float b = new Float(1);
//
//                // 向上滑动，隐藏搜索栏
//                if (fist_down_Y - y > 250 && top_title.getBackground().getAlpha() != b) {
//                    //                    if (animationSet != null) {
//                    //                        animationSet = null;
//                    //                    }
//                    //                    animationSet = (AnimationSet) AnimationUtils.loadAnimation(this, R.anim.up_out);
//                    //                    ll_search.startAnimation(animationSet);
//                    //                    ll_search.setY(-100);
//                    //                    ll_search.setVisibility(View.GONE);
//                    top_title.getBackground().setAlpha(1);
//                }
//                // 向下滑动，显示搜索栏
//                if (y - fist_down_Y > 250 && top_title.getBackground().getAlpha() != a) {
//                    //                    if (animationSet != null) {
//                    //                        animationSet = null;
//                    //                    }
//                    //                    animationSet = (AnimationSet) AnimationUtils.loadAnimation(this, R.anim.down_in);
//                    //                    ll_search.startAnimation(animationSet);
//                    //                    ll_search.setY(0);
//                    //                    ll_search.setVisibility(View.VISIBLE);
//                    top_title.getBackground().setAlpha(0);
//                }
//                    break;
//
//                }
//                return false;
//    }
}
