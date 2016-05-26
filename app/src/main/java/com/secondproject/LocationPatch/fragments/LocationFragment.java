package com.secondproject.LocationPatch.fragments;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.Poi;
import com.baidu.location.service.LocationApplication;
import com.baidu.location.service.LocationService;
import com.secondproject.Config;
import com.secondproject.LocationPatch.Callback.LocationCallback;
import com.secondproject.LocationPatch.Callback.OnQuickSideBarTouchListener;
import com.secondproject.LocationPatch.adpters.MenuListAdapter;
import com.secondproject.LocationPatch.domain.City;
import com.secondproject.LocationPatch.stickyheader.StickyListHeadersListView;
import com.secondproject.LocationPatch.utils.JSONUtils;
import com.secondproject.LocationPatch.view.QuickSideBarView;
import com.secondproject.leying.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * Created by liushuai on 16/5/16.
 */
public class LocationFragment extends Fragment implements
        AdapterView.OnItemClickListener, StickyListHeadersListView.OnHeaderClickListener,
        StickyListHeadersListView.OnStickyHeaderOffsetChangedListener,
        StickyListHeadersListView.OnStickyHeaderChangedListener, OnQuickSideBarTouchListener {
    @Nullable
    private EditText searchCity;
    private ImageView searchback;
    private StickyListHeadersListView searchList;
    private TextWatcher watcher;
    private StickyListHeadersListView menu_city_list;
    private MenuListAdapter listAdapter;
    private MenuListAdapter  searchlistAdapter;
    private View view;
    private QuickSideBarView quickSideBarView;
    private ImageView backImage;
    private HashMap<String, Integer> letters = new HashMap<>();
    private List<String> customLetters;
    public LocationCallback locationCallback;
    private LocationService locationService;
    private LocationApplication locationapplaction;
    private TextView titleView;
    private List<City> hotCities;
    private List<City> allCities;
    private HashMap<String, List<City>> citygroup;
    private City locatedCity;
    private HashMap<String, List<City>> searchlistdata;
    private List<String> pyStr ;
    private List<String> searchpy;
    public void init() {

        customLetters = new ArrayList<>();
        customLetters.addAll(Arrays.asList(getActivity().getResources().getStringArray(R.array.quickSideBarLetters)));


        menu_city_list.setOnItemClickListener(this);
        menu_city_list.setOnHeaderClickListener(this);
        menu_city_list.setOnStickyHeaderChangedListener(this);
        menu_city_list.setOnStickyHeaderOffsetChangedListener(this);
        menu_city_list.setAreHeadersSticky(true);
        listAdapter = new MenuListAdapter(citygroup, pyStr, getActivity());
        menu_city_list.setAdapter(listAdapter);
        quickSideBarView.setOnQuickSideBarTouchListener(this);

        quickSideBarView.setLetters(customLetters);
    }


    public void setLocationapplaction(LocationApplication locationapplaction) {
        this.locationapplaction = locationapplaction;
    }

    public void initData(List<City> hotCities, List<City> allCities) {
        this.hotCities = hotCities;
        this.allCities = allCities;
        citygroup = new HashMap<>();

//        for (int j = 0; j < allCities.size(); j++) {
//            Log.i("locationfragment",allCities.get(j).getName());}
        for (char i = 'a'; i <= 'z'; i++) {
//            cities.clear();
            ArrayList<City> cities = new ArrayList<>();
//            Log.i("locationfragment", "size----"+allCities.size()+"i-----"+i);
            for (int j = 0; j < allCities.size(); j++) {
//                Log.i("locationfragment", "j-----"+(char)(allCities.get(j).getPingyin().charAt(0)));
                if (allCities.get(j).getPingyin().charAt(0) == i) {
                    Log.i("locationfragment1", "i---" + i + "----" + "j-----" + allCities.get(j).getPingyin());
                    cities.add(allCities.get(j));
                }
            }

            citygroup.put((char) (i - 32) + "", cities);
        }


        citygroup.put("热门城市", hotCities);
        pyStr=new ArrayList<>();
        pyStr.add("定位城市");
        pyStr.add("热门城市");
        for (char i = 'A'; i <='Z' ; i++) {
            pyStr.add(i+"");
        }

        for (int i = 0; i < pyStr.size(); i++) {
            letters.put(pyStr.get(i), i);
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        initView();
        init();
        return view;
    }


    private void initView() {
        view = View.inflate(getContext(),R.layout.locationfragment,null);
        menu_city_list = (StickyListHeadersListView) view.findViewById(R.id.menu_city_list);
        quickSideBarView = (QuickSideBarView) view.findViewById(R.id.quickSideBar_location);
        quickSideBarView.performClick();
        backImage = (ImageView) view.findViewById(R.id.iv_loacation_back);
        backImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (locationCallback != null) {
                    Log.i("Locationfragment", "接口已调用----------------");
                    locationCallback.stop();
                }
            }
        });
        searchback= (ImageView) view.findViewById(R.id.iv_searchback);
        searchback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchpy.clear();
                searchlistdata.clear();
                searchCity.setText("");
                searchList.setVisibility(View.INVISIBLE);
                searchback.setVisibility(View.GONE);
            }
        });
        titleView = (TextView) view.findViewById(R.id.tv_loacaiton_inditify);
        titleView.setText("城市选择");
        searchCity= (EditText) view.findViewById(R.id.menu_search_edit);
        searchList= (StickyListHeadersListView) view.findViewById(R.id.menu_city_search_list);
        searchList.setOnHeaderClickListener(this);
        searchList.setOnStickyHeaderChangedListener(this);
        searchList.setOnStickyHeaderOffsetChangedListener(this);
        searchList.setAreHeadersSticky(false);
        searchlistdata=new HashMap<String, List<City>> ();
        searchpy=new ArrayList<>();
        searchlistAdapter = new MenuListAdapter(searchlistdata, searchpy, getActivity());
        searchList.setAdapter(searchlistAdapter);
        watcher=new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            searchback.setVisibility(View.VISIBLE);
            searchList.setVisibility(View.VISIBLE);
                updataSearchList(s.toString());
            }
        };
        searchCity.addTextChangedListener(watcher);
    }

    private void updataSearchList(String s) {
        for (int i = 0; i <allCities.size() ; i++) {
            char ch=(char)(allCities.get(i).getPingyin().charAt(0)-32);
            if (allCities.get(i).getName().contains(s)) {
                if (!searchpy.contains(ch+"")){
                    searchpy.add(ch+"");
                }
            }
        }
        if (searchpy.size()!=0) {
            for (int j = 0; j <searchpy.size() ; j++) {
                ArrayList seachItem= new ArrayList();
                for (int i = 0; i < allCities.size(); i++) {
                    if ( searchpy.get(j).charAt(0)==(char)(allCities.get(i).getPingyin().charAt(0)-32)
                            &&allCities.get(i).getName().contains(s)){
                        seachItem.add(allCities.get(i));
                    }

                }
                searchlistdata.put(searchpy.get(j),seachItem);
            }
            searchlistAdapter.notifyDataSetChanged();
        }
    }


    @Override
    public void onHeaderClick(StickyListHeadersListView l, View header, int itemPosition, long headerId, boolean currentlySticky) {

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onStickyHeaderChanged(StickyListHeadersListView l, View header, int itemPosition, long headerId) {
        header.setAlpha(1);
    }

    @Override
    public void onStickyHeaderOffsetChanged(StickyListHeadersListView l, View header, int offset) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            header.setAlpha(1 - (offset / (float) header.getMeasuredHeight()));
        }
    }

    @Override
    public void onLetterChanged(String letter, int position, int itemHeight) {
        if (letters.containsKey(letter)) {
            menu_city_list.smoothScrollToPosition(letters.get(letter));
        }
    }

    @Override
    public void onLetterTouching(boolean touching) {

    }

    @Override
    public void onStop() {
        // TODO Auto-generated method stub
        locationService.unregisterListener(mListener); //注销掉监听
        locationService.stop(); //停止定位服务
        super.onStop();
    }

    @Override
    public void onStart() {
        // TODO Auto-generated method stub
        super.onStart();
        // -----------location config ------------
        locationService = locationapplaction.locationService;
        //获取locationservice实例，建议应用中只初始化1个location实例，然后使用，可以参考其他示例的activity，都是通过此种方式获取locationservice实例的
        locationService.registerListener(mListener);
        //注册监听
        int type = getActivity().getIntent().getIntExtra("from", 0);
        if (type == 0) {
            locationService.setLocationOption(locationService.getDefaultLocationClientOption());
        } else if (type == 1) {
            locationService.setLocationOption(locationService.getOption());
        }

        if (titleView.getText().toString().equals("城市选择")) {
            locationService.start();// 定位SDK
            // start之后会默认发起一次定位请求，开发者无须判断isstart并主动调用request
            titleView.setText("正在定位中");
            Log.i("locationservice", "service调用");
        }
    }

    /*****
     * @ copy funtion to you project
     * 定位结果回调，重写onReceiveLocation方法，可以直接拷贝如下代码到自己工程中修改
     */
    private BDLocationListener mListener = new BDLocationListener() {

        @Override
        public void onReceiveLocation(BDLocation location) {
            Log.i("locationservice", "listener 已启动");
            // TODO Auto-generated method stub
            if (null != location && location.getLocType() != BDLocation.TypeServerError) {
                StringBuffer sb = new StringBuffer(256);
                sb.append("time : ");
                /**
                 * 时间也可以使用systemClock.elapsedRealtime()方法 获取的是自从开机以来，每次回调的时间；
                 * location.getTime() 是指服务端出本次结果的时间，如果位置不发生变化，则时间不变
                 */
                sb.append(location.getTime());
                sb.append("\nerror code : ");
                sb.append(location.getLocType());
                sb.append("\nlatitude : ");
                sb.append(location.getLatitude());
                sb.append("\nlontitude : ");
                sb.append(location.getLongitude());
                sb.append("\nradius : ");
                sb.append(location.getRadius());
                sb.append("\nCountryCode : ");
                sb.append(location.getCountryCode());
                sb.append("\nCountry : ");
                sb.append(location.getCountry());
                sb.append("\ncitycode : ");
                sb.append(location.getCityCode());
                sb.append("\ncity : ");
                sb.append(location.getCity());
                sb.append("\nDistrict : ");
                sb.append(location.getDistrict());
                sb.append("\nStreet : ");
                sb.append(location.getStreet());
                sb.append("\naddr : ");
                sb.append(location.getAddrStr());
                sb.append("\nDescribe: ");
                sb.append(location.getLocationDescribe());
                sb.append("\nDirection(not all devices have value): ");
                sb.append(location.getDirection());
                sb.append("\nPoi: ");
                if (location.getPoiList() != null && !location.getPoiList().isEmpty()) {
                    for (int i = 0; i < location.getPoiList().size(); i++) {
                        Poi poi = (Poi) location.getPoiList().get(i);
                        sb.append(poi.getName() + ";");
                    }
                }
                if (location.getLocType() == BDLocation.TypeGpsLocation) {// GPS定位结果
                    sb.append("\nspeed : ");
                    sb.append(location.getSpeed());// 单位：km/h
                    sb.append("\nsatellite : ");
                    sb.append(location.getSatelliteNumber());
                    sb.append("\nheight : ");
                    sb.append(location.getAltitude());// 单位：米
                    sb.append("\ndescribe : ");
                    sb.append("gps定位成功");
                } else if (location.getLocType() == BDLocation.TypeNetWorkLocation) {// 网络定位结果
                    // 运营商信息
                    sb.append("\noperationers : ");
                    sb.append(location.getOperators());
                    sb.append("\ndescribe : ");
                    sb.append("网络定位成功");
                } else if (location.getLocType() == BDLocation.TypeOffLineLocation) {// 离线定位结果
                    sb.append("\ndescribe : ");
                    sb.append("离线定位成功，离线定位结果也是有效的");
                } else if (location.getLocType() == BDLocation.TypeServerError) {
                    sb.append("\ndescribe : ");
                    sb.append("服务端网络定位失败，可以反馈IMEI号和大体定位时间到loc-bugs@baidu.com，会有人追查原因");
                } else if (location.getLocType() == BDLocation.TypeNetWorkException) {
                    sb.append("\ndescribe : ");
                    sb.append("网络不同导致定位失败，请检查网络是否通畅");
                } else if (location.getLocType() == BDLocation.TypeCriteriaException) {
                    sb.append("\ndescribe : ");
                    sb.append("无法获取有效定位依据导致定位失败，一般是由于手机的原因，处于飞行模式下一般会造成这种结果，可以试着重启手机");
                }
                Log.i("locateservice","------------"+sb.toString());
                String city = handleMsg(sb.toString()).get("city");
                String address=handleMsg(sb.toString()).get("District")+handleMsg(sb.toString()).get("Street")+handleMsg(sb.toString()).get("addr");
                Config.LOCATION=city;
                Config.ADDRESS=address;
                locatedCity = new City();
                locatedCity.setName(city);
                ArrayList<City> localcities = new ArrayList<>();
                localcities.add(locatedCity);
                citygroup.put("定位城市", localcities);
                listAdapter.notifyDataSetChanged();
                logMsg(city);
            }
        }

    };

    private HashMap<String, String> handleMsg(String s) {

        return JSONUtils.getLocationInfo(s);
    }

    public void logMsg(String str) {
        try {
            if (titleView != null)
                titleView.setText("当前定位-"+str);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
