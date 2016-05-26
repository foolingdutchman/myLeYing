package com.secondproject.leying;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.baidu.location.service.LocationApplication;
import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.exception.DbException;
import com.secondproject.adapters.MySearchListViewAdapter;
import com.secondproject.configs.Config;
import com.secondproject.models.CinemaItem;
import com.secondproject.models.Item;

import java.util.HashMap;
import java.util.List;

public class SearchActivity extends AppCompatActivity {
    private ActionBar actionBar = null;
    private ListView listView = null;
    private MySearchListViewAdapter adapter = null;
    public static HashMap<String, List<Item>> map = null;
    private DbUtils dbUtils = null;
    private List<Item> list = null;
    private EditText editText = null;
    private  List<Item> list1 = null;
    private ImageView imageView = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        initView();
        initData();
        initCtrl();
        initResolve();
        listView.setAdapter(adapter);

    }

    private void initView() {
        actionBar = getSupportActionBar();
        actionBar.hide();

        listView = (ListView) findViewById(R.id.listView_searchActivity);
        dbUtils = DbUtils.create(getApplicationContext());
        editText = (EditText) findViewById(R.id.editText_search);
        imageView = (ImageView) findViewById(R.id.imageView_searchActivity);
    }

    private void initData() {

    }

    private void initCtrl() {
        adapter = new MySearchListViewAdapter(this);
    }

    private void initResolve() {
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText.setText("");
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                TextView textView = (TextView) view.findViewById(R.id.text_searchListView);
                String tag = (String) textView.getTag();
                String index = "0";
                for(int i = 0; i < list.size(); i ++){
                    if(tag.equals(((CinemaItem)(list.get(i))).getName1())){
                        index = ((CinemaItem)(list.get(i))).getId();
                    }
                }
                if("".equals(editText.getText().toString())){
                    if(!(index.equals("0"))){
                        Intent intent = new Intent(SearchActivity.this, WebViewActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("path", Config.urlFirst + index);
                        bundle.putString("text", tag);
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }else{
                        Intent intent = new Intent(SearchActivity.this, EmptyActivity.class);
                        startActivity(intent);
                    }
                }else{

                    if(!(index.equals("0"))){
                        Intent intent = new Intent(SearchActivity.this, WebViewActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("path", Config.urlFirst + index);
                        intent.putExtras(bundle);
                        startActivity(intent);
                        editText.setText("");
                    }else{
                        Intent intent = new Intent(SearchActivity.this, EmptyActivity.class);
                        startActivity(intent);
                    }
                }

            }
        });
    }



    public void viewClick(View view) {
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        list = map.get("cinema_data");
        adapter.setList(list);
        if(LocationApplication.count == 0){

            for(int i = 0; i < list.size(); i ++){
                CinemaItem cinemaItem = (CinemaItem) list.get(i);
                try {
                    dbUtils.save(cinemaItem);
                } catch (DbException e) {
                    e.printStackTrace();
                }
            }
            LocationApplication.count += 1;
        }

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                try {
                    list1 = dbUtils.findAll(Selector.from(CinemaItem.class).where("name1", "like", "%" + editText.getText().toString().trim() + "%"));

                } catch (DbException e) {
                    e.printStackTrace();
                }
                if (!(editText.getText().toString().trim().equals(""))) {

                    adapter.setList(list1);
                } else {
                    adapter.setList(list);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
}
