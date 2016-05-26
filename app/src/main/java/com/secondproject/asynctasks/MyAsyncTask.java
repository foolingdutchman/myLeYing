package com.secondproject.asynctasks;

import android.content.Context;
import android.os.AsyncTask;

import com.secondproject.com.secondproject.utils.CallBack;
import com.secondproject.com.secondproject.utils.JsonUtil;
import com.secondproject.com.secondproject.utils.OkHttpUtils;
import com.secondproject.leying.SearchActivity;
import com.secondproject.models.Item;
import com.secondproject.models.ViewPagerItem;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/5/17 0017.
 */
public class MyAsyncTask extends AsyncTask<String, Void, HashMap<String, List<Item>>> {
    private String currentPath = null;
    private Context context = null;
    private CallBack callBack = null;
    private String path = "http://mobile2.leying365.com/advert/list?pver=1.0&city_id=499&promotion_type=8&session_id=ENpa0OcfqFuPQzMEYd0zig==&ver=3.1.28&group=0&source=105001&.sig=2095e5efd1d88a9a6bc4796202081ed8";
    private String path1 = "http://mobile2.leying365.com/cinema/list?city_id=499&session_id=ENpa0OcfqFuPQzMEYd0zig==&movie_id=&source=105001&pver=1.0&ver=3.1.28&is_add_card=0&group=0&.sig=a9669c9e19661beda353a875308224b5";
    private HashMap<String, List<Item>> map = null;
    public MyAsyncTask(Context context, CallBack callBack) {
        this.context = context;
        this.callBack = callBack;
    }

    @Override
    protected HashMap<String, List<Item>> doInBackground(String... params) {
        currentPath = params[0];
        if(params[0] != null){
            String jsonStr = OkHttpUtils.getStringFromUrl(params[0]);
            if(jsonStr != null){
                map = new HashMap<String, List<Item>>();
                if(params[0] == path){
                    return JsonUtil.parseJson(jsonStr);
                }else if(params[0] == path1){
                    return JsonUtil.parseJson1(jsonStr);
                }else{

                }
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(HashMap<String, List<Item>> map) {
        super.onPostExecute(map);
        if(currentPath == path){

            callBack.callBack(map);
        }else{
            callBack.callBackListView(map);
            SearchActivity.map = map;
        }
    }
}
