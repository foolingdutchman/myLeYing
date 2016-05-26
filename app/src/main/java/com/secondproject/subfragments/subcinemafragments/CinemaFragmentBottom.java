package com.secondproject.subfragments.subcinemafragments;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

//import com.baidu.mapapi.BMapManager;
import com.baidu.location.service.LocationApplication;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.overlayutil.PoiOverlay;
import com.baidu.mapapi.search.core.PoiInfo;
//import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
//import com.baidu.mapapi.search.poi.PoiCitySearchOption;
//import com.baidu.mapapi.search.poi.PoiDetailResult;
//import com.baidu.mapapi.search.poi.PoiDetailSearchOption;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiCitySearchOption;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiDetailSearchOption;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.db.sqlite.WhereBuilder;
import com.lidroid.xutils.exception.DbException;
import com.secondproject.adapters.MyListViewAdapter;
import com.secondproject.adapters.MyPagerAdapter;
import com.secondproject.adapters.MypopupWindowAdapter;
import com.secondproject.asynctasks.MyAsyncTask;
import com.secondproject.com.secondproject.utils.CallBack;
import com.secondproject.configs.Config;
import com.secondproject.leying.EmptyActivity;
import com.secondproject.leying.R;
import com.secondproject.leying.WebViewActivity;
import com.secondproject.models.CinemaItem;
import com.secondproject.models.Item;
import com.secondproject.models.ViewPagerItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2016/5/17 0017.
 */
public class CinemaFragmentBottom extends Fragment implements CallBack, AdapterView.OnItemClickListener{

    private View view = null;
    private Context context = null;
    private ListView listView = null;
    private ListView popupWindowListView1 = null;
    private ListView popupWindowListView2 = null;
    private ListView popupWindowListView3 = null;
    private View headView = null;
    private View popupWindowView1 = null;
    private View popupWindowView2 = null;
    private View popupWindowView3 = null;
    private ViewPager viewPager = null;
    private String path = "http://mobile2.leying365.com/advert/list?pver=1.0&city_id=499&promotion_type=8&session_id=ENpa0OcfqFuPQzMEYd0zig==&ver=3.1.28&group=0&source=105001&.sig=2095e5efd1d88a9a6bc4796202081ed8";
    private String path1 = "http://mobile2.leying365.com/cinema/list?city_id=499&session_id=ENpa0OcfqFuPQzMEYd0zig==&movie_id=&source=105001&pver=1.0&ver=3.1.28&is_add_card=0&group=0&.sig=a9669c9e19661beda353a875308224b5";
    private MyPagerAdapter myPagerAdapter = null;
    private MyListViewAdapter listViewAdapter = null;
    private MypopupWindowAdapter myPopupWindowAdapter1 = null;
    private MypopupWindowAdapter myPopupWindowAdapter2 = null;
    private MypopupWindowAdapter myPopupWindowAdapter3 = null;
    private List<ImageView> data = null;
    private LinearLayout linearLayout = null;
    private RadioGroup radioGroup = null;
    private PopupWindow popupWindow1 = null;
    private PopupWindow popupWindow2 = null;
    private PopupWindow popupWindow3 = null;
    private int count = 0;
    MapView mMapView = null;
    private PoiSearch mPoiSearch = null;

    private EditText edit_city = null;
    private EditText edit_poi = null;
    private BaiduMap mBaiduMap = null;
    private String url = null;
    private List<Item> viewPagerList = null;
    private List<Item> listViewList = null;
    private List<Item> listViewList2 = null;

    private String[] urls = null;
    private DbUtils dbutls = null;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 110:
                    viewPager.setCurrentItem(count ++, false);
                    count = count % 5;
                    break;
            }
        }
    };
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        context = getActivity();
        url = context.getResources().getString(R.string.viewPagerUrl);
        urls = context.getResources().getStringArray(R.array.cinemaUrl);
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        int index = getArguments().getInt("index");
        switch (index){

            case 0:
                view = LayoutInflater.from(context).inflate(R.layout.cinemafragmentbottom, null);
                popupWindowView1 = LayoutInflater.from(context).inflate(R.layout.popupwindow, null);
                popupWindowView2 = LayoutInflater.from(context).inflate(R.layout.popupwindow, null);
                popupWindowView3 = LayoutInflater.from(context).inflate(R.layout.popupwindow, null);
                headView = LayoutInflater.from(context).inflate(R.layout.headview, null);
                initView();
                initData();
                initCtrl();
                initResolve();

                break;
            case 1:
                view = LayoutInflater.from(context).inflate(R.layout.cinemafragmentbottom_map, null);
                //在使用SDK各组件之前初始化context信息，传入ApplicationContext
                //注意该方法要再setContentView方法之前实现

//                initView1();
                //获取地图控件引用
                mMapView = (MapView) view.findViewById(R.id.bmapView);
                Animation right_top = AnimationUtils.loadAnimation(context, R.anim.right_top);
                mMapView.startAnimation(right_top);

                mBaiduMap = mMapView.getMap();
                mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
                mPoiSearch = PoiSearch.newInstance();

                OnGetPoiSearchResultListener poiListener = new OnGetPoiSearchResultListener(){
                    public void onGetPoiResult(PoiResult result){
                        //获取POI检索结果
                        List<PoiInfo> list =  result.getAllPoi();
                        for (int i = 0; i < list.size(); i++) {
                            Log.i("poiinfo", "第" + i + "个:" + list.get(i).name + ":" + list.get(i).address + ":" + list.get(i).phoneNum);
                        }
                        mBaiduMap.clear();
                        // 说明myoverlay创建出来属于哪个baidumap
                        MyOverlay myOverlay = new MyOverlay(mBaiduMap);


                        //为baidumap上的覆盖物添加点击事件
                        mBaiduMap.setOnMarkerClickListener(myOverlay);

                        // 这个overlay显示的是什么数据
                        myOverlay.setData(result);

                        // 把覆盖物添加到map上
                        myOverlay.addToMap();

                        // 把所有的overlay显示在map上，也就是说适当的放缩地图
                        myOverlay.zoomToSpan();

                    }
                    public void onGetPoiDetailResult(PoiDetailResult result){
                        //获取Place详情页检索结果
                        String address = result.getAddress();
                        String name = result.getName();
                        String phone = result.getTelephone();

                        Toast.makeText(context, "" + name + ":" + address + ":" + phone, Toast.LENGTH_LONG).show();
                    }
                };
                mPoiSearch.setOnGetPoiSearchResultListener(poiListener);
                mPoiSearch.searchInCity((new PoiCitySearchOption())
                        .city("北京")
                        .keyword("影院")
                        .pageNum(0));



                break;

        }

        return view;
    }


    private void initView() {
        listView = (ListView) view.findViewById(R.id.listView_cinemaFragmentBottom);
        popupWindowListView1 = (ListView) popupWindowView1.findViewById(R.id.listView_popupWindow);
        popupWindowListView2 = (ListView) popupWindowView2.findViewById(R.id.listView_popupWindow);
        popupWindowListView3 = (ListView) popupWindowView3.findViewById(R.id.listView_popupWindow);
        viewPager = (ViewPager) headView.findViewById(R.id.viewpager_headview);
        linearLayout = (LinearLayout) headView.findViewById(R.id.linear_cinemaFragmentBottom);
        radioGroup = (RadioGroup) view.findViewById(R.id.redioGroup_cinemafragmentbottom);
        data = new ArrayList<ImageView>();
        dbutls = DbUtils.create(context);
    }

    private void initData() {
        new MyAsyncTask(context, this).execute(path);
        new MyAsyncTask(context, this).execute(path1);
    }

    private void initCtrl() {
        myPagerAdapter = new MyPagerAdapter(context);
        listViewAdapter = new MyListViewAdapter(context);
        myPopupWindowAdapter1 = new MypopupWindowAdapter(context);
        myPopupWindowAdapter2 = new MypopupWindowAdapter(context);
        myPopupWindowAdapter3 = new MypopupWindowAdapter(context);

    }

    private void initResolve() {
        viewPager.setAdapter(myPagerAdapter);
        listView.addHeaderView(headView);
        listView.setAdapter(listViewAdapter);
        popupWindowListView1.setAdapter(myPopupWindowAdapter1);
        popupWindowListView2.setAdapter(myPopupWindowAdapter2);
        popupWindowListView3.setAdapter(myPopupWindowAdapter3);

        popupWindow1 = new PopupWindow(popupWindowView1, WindowManager.LayoutParams.MATCH_PARENT, 400, true);
        popupWindow1.setBackgroundDrawable(new BitmapDrawable());
        popupWindow2 = new PopupWindow(popupWindowView2, WindowManager.LayoutParams.MATCH_PARENT, 400, true);
        popupWindow2.setBackgroundDrawable(new BitmapDrawable());
        popupWindow3 = new PopupWindow(popupWindowView3, WindowManager.LayoutParams.MATCH_PARENT, 400, true);
        popupWindow3.setBackgroundDrawable(new BitmapDrawable());

        popupWindowListView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                for (int i = 0; i < parent.getChildCount(); i++) {
                    ((LinearLayout) parent.getChildAt(i)).getChildAt(0).setBackgroundColor(Color.WHITE);
                    ((LinearLayout) parent.getChildAt(i)).getChildAt(0).setTag(null);
                }
                (((LinearLayout) view).getChildAt(0)).setBackgroundResource(R.mipmap.yingyuan_xiala_p);
            }
        });
        popupWindowListView1.setOnItemClickListener(this);
        popupWindowListView2.setOnItemClickListener(this);
        popupWindowListView3.setOnItemClickListener(this);
    }

    @Override
    public void callBack(HashMap<String, List<Item>> map) {
        viewPagerList = map.get("viewPager");
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) viewPager.getLayoutParams();
        layoutParams.height = 200;
        viewPager.setLayoutParams(layoutParams);
        List<ImageView> data = new ArrayList<ImageView>();
        for(int i = 0; i < viewPagerList.size(); i ++){
            ImageView imageView = new ImageView(context);
            final int finalI = i;
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, WebViewActivity.class);
                    Bundle bundle = new Bundle();
                    if (finalI == 4) {
                        bundle.putString("path", ((ViewPagerItem) (viewPagerList.get(finalI))).getPromotion_url());
                    } else {
                        bundle.putString("path", url + ((ViewPagerItem) (viewPagerList.get(finalI))).getMovie_id());
                    }
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            });
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            LocationApplication.bitmapCacheHelper.getBitmap(((ViewPagerItem) (map.get("viewPager").get(i))).getPromotion_img_url(), imageView);
            data.add(imageView);
        }
        myPagerAdapter.setAllList(data);
        viewPagerClick();
    }

    private void viewPagerClick() {

        for(int i = 0; i < linearLayout.getChildCount(); i ++){
            linearLayout.getChildAt(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int index = linearLayout.indexOfChild(v);
                    for (int i = 0; i < linearLayout.getChildCount(); i++) {
                        ((ImageView) linearLayout.getChildAt(i)).setImageResource(R.mipmap.icon_dot_default);
                    }
                    ((ImageView) linearLayout.getChildAt(index)).setImageResource(R.mipmap.icon_dot_selected);
                    viewPager.setCurrentItem(index);
                }
            });

        }

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                for (int i = 0; i < linearLayout.getChildCount(); i++) {
                    ((ImageView) linearLayout.getChildAt(i)).setImageResource(R.mipmap.icon_dot_default);
                }
                ((ImageView) linearLayout.getChildAt(position)).setImageResource(R.mipmap.icon_dot_selected);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){

                    try {
                        Thread.sleep(2000);
                        handler.sendEmptyMessage(110);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    @Override
    public void callBackListView(final HashMap<String, List<Item>> map) {
        listViewList = map.get("cinema_data");
        if(map != null){
            listViewAdapter.setAllList(listViewList);
            for(int i = 0; i < 3; i ++){
                final int finalI = i;
                radioGroup.getChildAt(i).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(finalI == 0){
                            myPopupWindowAdapter1.setList(map.get("district_data"));
                            popupWindow1.showAsDropDown(view);

                        }else if(finalI == 1){
                            myPopupWindowAdapter2.setList(map.get("brand_data"));
                            popupWindow2.showAsDropDown(view);

                        }else{
                            myPopupWindowAdapter3.setList(map.get("special_data"));
                            popupWindow3.showAsDropDown(view);

                        }

                    }
                });
            }
            listViewClick();

        }
    }

    private void listViewClick() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView textView = (TextView) view.findViewById(R.id.text1_listView);
                String tag = (String) textView.getTag();
                String index = "0";
                for(int i = 0; i < listViewList.size(); i ++){
                    if(tag.equals(((CinemaItem)(listViewList.get(i))).getName1())){
                        index = ((CinemaItem)(listViewList.get(i))).getId();
                    }
                }
                if(!((index.equals("0")))){
                    Log.d("url", "===============" + (position - 1) + "===" + index);
                    Intent intent = new Intent(context, WebViewActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("path", Config.urlFirst + index);
                    bundle.putString("text", tag);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }else{
                    Intent intent = new Intent(context, EmptyActivity.class);
                    startActivity(intent);
                }
            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        for(int i = 0; i < parent.getChildCount(); i ++){
            ((LinearLayout)parent.getChildAt(i)).getChildAt(0).setBackgroundColor(Color.WHITE);
            ((LinearLayout)parent.getChildAt(i)).getChildAt(0).setTag(null);

        }
        (((LinearLayout)view).getChildAt(0)).setBackgroundResource(R.mipmap.yingyuan_xiala_p);
        (((LinearLayout)view).getChildAt(0)).setTag(position);
        if(popupWindowListView1 == (ListView)parent){
            TextView textView1 = (TextView)((LinearLayout) view).getChildAt(0);
            ((RadioButton) (radioGroup.getChildAt(0))).setText(textView1.getText());
            popupWindow1.dismiss();
            ((RadioButton) (radioGroup.getChildAt(0))).setTextColor(Color.parseColor("#66000000"));
        }else if(popupWindowListView2 == (ListView) parent){
            TextView textView2 = (TextView)((LinearLayout) view).getChildAt(0);
            ((RadioButton) (radioGroup.getChildAt(1))).setText(textView2.getText());
            popupWindow2.dismiss();
            ((RadioButton) (radioGroup.getChildAt(1))).setTextColor(Color.parseColor("#66000000"));
        }else{
            TextView textView3 = (TextView)((LinearLayout) view).getChildAt(0);
            ((RadioButton) (radioGroup.getChildAt(2))).setText(textView3.getText());
            popupWindow3.dismiss();
            ((RadioButton) (radioGroup.getChildAt(2))).setTextColor(Color.parseColor("#66000000"));
        }
        if(!("全部城区".equals(((RadioButton)(radioGroup.getChildAt(0))).getText().toString()))){
            if("全部品牌".equals(((RadioButton)(radioGroup.getChildAt(1))).getText().toString())){

                WhereBuilder whereBuilder = WhereBuilder.b("address", "like", "%" + ((RadioButton)(radioGroup.getChildAt(0))).getText().toString() + "%");
                try {
                    listViewList2 = dbutls.findAll(Selector.from(CinemaItem.class).where(whereBuilder));
                    listViewAdapter.setList(listViewList2);
                } catch (DbException e) {
                    e.printStackTrace();
                }
            }else{
                WhereBuilder whereBuilder = WhereBuilder.b("address", "like", "%" + ((RadioButton)(radioGroup.getChildAt(0))).getText().toString() + "%").and("name1", "like", "%" + ((RadioButton)(radioGroup.getChildAt(1))).getText().toString() + "%");
                try {
                    listViewList2 = dbutls.findAll(Selector.from(CinemaItem.class).where(whereBuilder));
                    listViewAdapter.setList(listViewList2);
                } catch (DbException e) {
                    e.printStackTrace();
                }
            }
        }else{
            if("全部品牌".equals(((RadioButton)(radioGroup.getChildAt(1))).getText().toString())){
                listViewAdapter.setList(listViewList);
            }else{
                WhereBuilder whereBuilder = WhereBuilder.b("name1", "like", "%" + ((RadioButton)(radioGroup.getChildAt(1))).getText().toString() + "%");
                try {
                    listViewList2 = dbutls.findAll(Selector.from(CinemaItem.class).where(whereBuilder));
                    listViewAdapter.setList(listViewList2);
                } catch (DbException e) {
                    e.printStackTrace();
                }
            }
        }
    }

//    private int pageNum = 1;
//    public void onClick(View view){
//        switch (view.getId()){
//
//            case R.id.btn_search:
//                mPoiSearch.searchInCity((new PoiCitySearchOption())
//                        .city(edit_city.getText().toString().trim())
//                        .keyword(edit_poi.getText().toString().trim())
//                        .pageNum(pageNum));
//                break;
//            case R.id.btn_next:
//                mPoiSearch.searchInCity((new PoiCitySearchOption())
//                        .city(edit_city.getText().toString())
//                        .keyword(edit_poi.getText().toString())
//                        .pageNum( ++ pageNum));
//                break;
//        }
//    }

    class MyOverlay extends PoiOverlay {

        /**
         * 构造函数
         *
         * @param baiduMap 该 PoiOverlay 引用的 BaiduMap 对象
         */
        public MyOverlay(BaiduMap baiduMap) {
            super(baiduMap);
        }

        @Override
        public boolean onPoiClick(int i) {

            PoiInfo poiInfo = getPoiResult().getAllPoi().get(i);
            mPoiSearch.searchPoiDetail(new PoiDetailSearchOption()
                    .poiUid(poiInfo.uid));
            return super.onPoiClick(i);
        }
    }

}

