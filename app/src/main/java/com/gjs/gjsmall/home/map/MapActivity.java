package com.gjs.gjsmall.home.map;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.overlayutil.OverlayManager;
import com.baidu.mapapi.overlayutil.PoiOverlay;
import com.baidu.mapapi.search.core.CityInfo;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiDetailSearchOption;
import com.baidu.mapapi.search.poi.PoiNearbySearchOption;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.gjs.gjsmall.R;

import java.util.ArrayList;
import java.util.List;

/*
 *  @项目名：  GJSTXDemo0427 
 *  @包名：    com.gjs.gjsmall.home.map
 *  @文件名:   MapActivity
 *  @创建者:   Administrator
 *  @创建时间:  2016/4/27 15:38
 *  @描述：    TODO
 */
public class MapActivity extends Activity implements OnGetPoiSearchResultListener,OnClickListener{
    private static final String TAG = MapActivity.class.getSimpleName();
    /**用来发起定位，添加取消监听*/
    public LocationClient mLocClient;
    public MyLocationListenner myListener = new MyLocationListenner();
    /**Poi搜索对象*/
    private PoiSearch mPoiSearch = null;
    /**经纬度对象*/
    private LatLng mLatlng;
    /**维度*/
    private Double latitude;
    /**经度*/
    private Double longitude;

    private final static String KFC = "美食";
    private final static String PARKING = "超市";
    private final static String UNIQLO = "服装";


    private String mark = "";

    MapView mMapView;
    BaiduMap mBaiduMap;

    // UI相关
    boolean isFirstLoc = true;// 是否首次定位
    /**停车场，加油站，维修点的顶部按钮*/
    private Button kfc,parking,uniqlo;
    private ImageView map_setting_back;
    private TextView map_loction;
    private SharedPreferences sp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        mPoiSearch = PoiSearch.newInstance();
        mPoiSearch.setOnGetPoiSearchResultListener(this);
        /** Context需要时全进程有效的context,推荐用getApplicationConext获取全进程有效的context  */
        mLocClient = new LocationClient(getApplicationContext());
        mLocClient.registerLocationListener(myListener);
        // 地图初始化
        mMapView = (MapView) findViewById(R.id.bmapView);
        map_loction = (TextView) findViewById(R.id.map_loction);
        map_setting_back= (ImageView) findViewById(R.id.map_setting_back);
        map_setting_back.setOnClickListener(this);
        mBaiduMap = mMapView.getMap();
        // 开启定位图层
        mBaiduMap.setMyLocationEnabled(true);
        /**
         *  定位初始化，用来设置定位方式，包括是否启用缓存，使用GPS,时间间隔等，，，
         *  setScanSpan：时间间隔，小于1秒则一次定位;大于等于1秒则定时定位
         */
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true);// 打开gps
        option.setCoorType("bd09ll"); // 设置坐标类型
        option.setScanSpan(5000);
        option.setPoiExtraInfo(true);
        option.setAddrType("all");

        //option.setIsNeedAddress(true);
        mLocClient.setLocOption(option);
        mLocClient.start();
        Log.d(TAG, "locClient is INIT");
        if (mLocClient != null && mLocClient.isStarted()){
            mLocClient.requestLocation();
        }
        else {
            Log.d(TAG, "locClient is null or not started");
            Toast.makeText(MapActivity.this, "抱歉，未找到结果", Toast.LENGTH_SHORT);
        }
        initView();
        setListener();

    }


    /**
     * 继承BDLocationListener定位接口类，获取定位结果
     */
    public class MyLocationListenner implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            // map view 销毁后不在处理新接收的位置
            if (location == null || mMapView == null)
                return;
            MyLocationData locData = new MyLocationData.Builder()
                    .accuracy(location.getRadius())
                    // 此处设置开发者获取到的方向信息，顺时针0-360
                    .direction(100).latitude(location.getLatitude())
                    .longitude(location.getLongitude()).build();
            longitude = location.getLongitude();
            latitude = location.getLatitude();
            mLatlng = new LatLng(latitude, longitude);

            mBaiduMap.setMyLocationData(locData);

            if (isFirstLoc) {
                isFirstLoc = false;
                LatLng ll = new LatLng(location.getLatitude(),
                                       location.getLongitude());
                MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(ll);
                mBaiduMap.animateMapStatus(u);

            }
            map_loction.setText(location.getAddrStr());
            sp = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor =sp.edit();
            editor.putString("ADDRESS", location.getAddrStr());
            editor.commit();

        }
        @Override
        public void onReceivePoi(BDLocation poiLocation) {
        }
    }

    @Override
    protected void onPause() {
        mMapView.onPause();
        super.onPause();
    }

    @Override
    protected void onResume() {
        mMapView.onResume();
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        // 退出时销毁定位
        mLocClient.stop();
        // 关闭定位图层
        mBaiduMap.setMyLocationEnabled(false);
        mMapView.onDestroy();
        mMapView = null;
        mPoiSearch.destroy();
        super.onDestroy();
    }

    public void initView(){
        kfc = (Button) this.findViewById(R.id.kfc);
        parking = (Button) this.findViewById(R.id.parking);
        uniqlo = (Button) this.findViewById(R.id.uniqlo);

    }

    public void setListener(){
        kfc.setOnClickListener(setOnclicklistener);
        parking.setOnClickListener(setOnclicklistener);
        uniqlo.setOnClickListener(setOnclicklistener);
    }

    private OnClickListener setOnclicklistener = new OnClickListener() {

        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub
            switch (v.getId()) {
                case R.id.kfc:
                    mark = KFC;
                    mPoiSearch.searchNearby(new PoiNearbySearchOption()
                                                    .keyword(KFC).pageCapacity(20).radius(500).location(mLatlng));


                    break;
                case R.id.parking:
                    mark = PARKING;
                    mPoiSearch.searchNearby(new PoiNearbySearchOption()
                                                    .keyword(PARKING).pageCapacity(20).radius(500).location(mLatlng));

                    break;


                case R.id.uniqlo:
                    mark = UNIQLO;
                    mPoiSearch.searchNearby(new PoiNearbySearchOption()
                                                    .keyword(UNIQLO).pageCapacity(20).radius(500).location(mLatlng));

                    break;
                default:
                    break;
            }

        }

    };

    @Override
    public void onGetPoiDetailResult(PoiDetailResult result) {
        // TODO Auto-generated method stub

        if (result.error != SearchResult.ERRORNO.NO_ERROR) {
            Toast.makeText(MapActivity.this, "抱歉，未找到结果", Toast.LENGTH_SHORT)
                 .show();
        } else {
            Toast.makeText(MapActivity.this, result.getName() + ": " + result.getAddress(), Toast.LENGTH_SHORT)
                 .show();
        }

    }

    @Override
    public void onGetPoiResult(PoiResult result) {

        if (result == null
                || result.error == SearchResult.ERRORNO.RESULT_NOT_FOUND) {
            return;
        }
        if (result.error == SearchResult.ERRORNO.NO_ERROR) {
            mBaiduMap.clear();

            MyOm onverlay = new MyOm(mBaiduMap);
            mBaiduMap.setOnMarkerClickListener(onverlay);
            onverlay.setResult(result);
            onverlay.addToMap();
            onverlay.zoomToSpan();

            PoiOverlay overlay = new MyPoiOverlay(mBaiduMap);
            mBaiduMap.setOnMarkerClickListener(overlay);
            overlay.setData(result);
            overlay.addToMap();
            overlay.zoomToSpan();
            return;
        }
        if (result.error == SearchResult.ERRORNO.AMBIGUOUS_KEYWORD) {

            // 当输入关键字在本市没有找到，但在其他城市找到时，返回包含该关键字信息的城市列表
            String strInfo = "在";
            for (CityInfo cityInfo : result.getSuggestCityList()) {
                strInfo += cityInfo.city;
                strInfo += ",";
            }
            strInfo += "找到结果";
            Toast.makeText(MapActivity.this, strInfo, Toast.LENGTH_LONG)
                 .show();
        }
    }
    /**
     * 要显示poi上面的详细信息，要继承PoiOverlay类，重写onPoiClick方法，具体实现在jar包中已经封装好，
     * 实现点击查看详细内容，只要调用mPoiSearch.searchPoiDetail。
     * (如果要显示某一点详细信息，要跳转到详细内容Activity时，就需要重写onTap()方法，
     * 只要调用 mSearch.poiDetailSearch(info.uid)，就可以实现跳转 。
     * )
     */
    private class MyPoiOverlay extends PoiOverlay {

        public MyPoiOverlay(BaiduMap mBaiduMap) {
            super(mBaiduMap);
        }


        @Override
        public boolean onPoiClick(int index) {
            super.onPoiClick(index);
            PoiInfo poi = getPoiResult().getAllPoi().get(index);
            // if (poi.hasCaterDetails) {
            mPoiSearch.searchPoiDetail((new PoiDetailSearchOption())
                                               .poiUid(poi.uid));
            // }
            return true;
        }

    }

    private class MyOm extends OverlayManager{
        private PoiResult result;

        public void setResult(PoiResult result) {
            this.result = result;
        }

        //        public void setData(PoiResult result2) {
        //            // TODO Auto-generated method stub
        //
        //        }

        public MyOm(BaiduMap mBaiduMap) {
            super(mBaiduMap);
        }

        @Override
        public boolean onMarkerClick(Marker marker) {
            onClick(marker.getZIndex());
            return true;
        }

        public boolean onClick(int index) {
            PoiInfo poi = result.getAllPoi().get(index);
            if(poi.hasCaterDetails){
                mPoiSearch.searchPoiDetail(
                        (new PoiDetailSearchOption())
                                .poiUid(poi.uid));
            }
            return true;
        }

        @Override
        public List<OverlayOptions> getOverlayOptions() {
            List<OverlayOptions> ops = new ArrayList<OverlayOptions>();
            List<PoiInfo> pois = result.getAllPoi();
            if(mark == KFC){

                BitmapDescriptor bitmap = BitmapDescriptorFactory.fromResource(R.drawable.touming);
                for(int i = 0;i < pois.size();i++){
                    OverlayOptions op = new MarkerOptions().position(pois.get(i).location).icon(bitmap).title(result.getAllPoi().toString());
                    ops.add(op);
                    mBaiduMap.addOverlay(op);
                }
            }
            if(mark == PARKING){
                BitmapDescriptor bitmap = BitmapDescriptorFactory.fromResource(R.drawable.touming);
                for(int i = 0;i < pois.size();i++){
                    OverlayOptions op = new MarkerOptions().position(pois.get(i).location).icon(bitmap).title(result.getAllPoi().toString());
                    ops.add(op);
                    mBaiduMap.addOverlay(op);
                }
            }
            if(mark == UNIQLO){

                BitmapDescriptor bitmap = BitmapDescriptorFactory.fromResource(R.drawable.touming);
                for(int i = 0;i < pois.size();i++){
                    OverlayOptions op = new MarkerOptions().position(pois.get(i).location).icon(bitmap).title(result.getAllPoi().toString());
                    ops.add(op);
                    mBaiduMap.addOverlay(op);
                }
            }
            return ops;
        }

    }
////        在POI检索回调接口中添加自定义的PoiOverlay
//        public void onGetPoiResult(PoiResult result) {
//            if (result == null || result.error == SearchResult.ERRORNO.RESULT_NOT_FOUND) {
//                return;
//            }
//            if (result.error == SearchResult.ERRORNO.NO_ERROR) {
//                mBaiduMap.clear();
//                //创建PoiOverlay
//                PoiOverlay overlay = new MyPoiOverlay(mBaiduMap);
//                //设置overlay可以处理标注点击事件
//                mBaiduMap.setOnMarkerClickListener(overlay);
//                //设置PoiOverlay数据
//                overlay.setData(result);
//                //添加PoiOverlay到地图中
//                overlay.addToMap();
//                overlay.zoomToSpan();
//                return;
//            }
//        }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.map_setting_back:
                finish();
                break;
            default:
                break;
        }
    }



}
