package com.secondproject.leying;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.secondproject.Config;
import com.secondproject.com.secondproject.utils.ScrollInterface;
import com.secondproject.mepatch.domain.Record;
import com.secondproject.models.filmmodels.ComingMovies;
import com.secondproject.models.filmmodels.HotMovie;
import com.secondproject.myview.MyWebView;

import net.tsz.afinal.FinalDb;

import java.util.ArrayList;

public class MovieDetailActivity extends AppCompatActivity implements View.OnClickListener {

    private MyWebView webView_movie_detail;
    private String url = "http://m.leying.com/movie/movieInfo.action?movieId=";
    private int movieID;
    private String movieName;
    private TextView text_movieDetail;
    private LinearLayout linear_top_movieDetail;
    private LinearLayout linear_movieDetail;
    private ImageView imageView_share_movieDetail;
    private ImageView imageView_back_movieDetail;
    private Button paybutton;
    private FinalDb db;
    private ComingMovies movie;
    private HotMovie hotMovie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        getSupportActionBar().hide();
        initView();
        db=FinalDb.create(this, Config.LEYING_DATABASE);
        Intent intent = getIntent();
        if (intent != null) {

            Bundle bundle = intent.getExtras();
            if (bundle.getInt("type")==Config.MOVIE_TYPE1) {
                Log.i("MovieDetail","-------------获得bundle");
             movie = (ComingMovies) bundle.getSerializable("movie");
            movieID = movie.getMovie_id();
            movieName = movie.getMovie_name();
                Log.i("MovieDetail","获得bundle数据----"+movieName);
            }else if (bundle.getInt("type")==Config.MOVIE_TYPE2){
                hotMovie= (HotMovie) bundle.getSerializable("movie");
                movieID = hotMovie.getMovie_id();
                movieName = hotMovie.getMovie_name();
            }
//            int index=bundle.getInt("index");
//            ArrayList<Integer> movieIDList=bundle.getIntegerArrayList("id");
//            ArrayList<String> movieNameList=bundle.getStringArrayList("name");
//            movieID=movieIDList.get(index);
//            movieName=movieNameList.get(index);

            text_movieDetail.setText(movieName);
            webView_movie_detail.getSettings().setJavaScriptEnabled(true);
            webView_movie_detail.setWebChromeClient(new WebChromeClient());
            webView_movie_detail.setWebViewClient(new WebViewClient());
            webView_movie_detail.loadUrl(url + movieID);
        }

        webView_movie_detail.setOnCustomScroolChangeListener(new ScrollInterface() {
            @Override
            public void onSChanged(int l, int t, int oldl, int oldt) {
                // TODO Auto-generated method stub
                float webcontent = webView_movie_detail.getContentHeight() * webView_movie_detail.getScale();//webview的高度
                float webnow = webView_movie_detail.getHeight() + webView_movie_detail.getScrollY();//当前webview的高度
                if (webView_movie_detail.getContentHeight() * webView_movie_detail.getScale() - (webView_movie_detail.getHeight() + webView_movie_detail.getScrollY()) <= 250) {

                    //已经处于底端
                    linear_movieDetail.setVisibility(View.VISIBLE);

                } else {
                    linear_movieDetail.setVisibility(View.GONE);
                }
//已经处于顶端
                Log.d("height", "=================>" + webView_movie_detail.getScrollY());
                if (webView_movie_detail.getScrollY() <= 100) {
                    linear_top_movieDetail.setVisibility(View.VISIBLE);
                } else {
                    linear_top_movieDetail.setVisibility(View.GONE);
                }
            }
        });
    }

    public void initView() {
        webView_movie_detail = (MyWebView) findViewById(R.id.webView_movie_detail);
        text_movieDetail = (TextView) findViewById(R.id.text_movieDetail);
        linear_top_movieDetail = (LinearLayout) findViewById(R.id.linear_top_movieDetail);
        linear_movieDetail = (LinearLayout) findViewById(R.id.linear_movieDetail);

        imageView_share_movieDetail = (ImageView) findViewById(R.id.imageView_share_movieDetail);
        imageView_back_movieDetail = (ImageView) findViewById(R.id.imageView_back_movieDetail);
        paybutton= (Button) findViewById(R.id.bt_movie_pay);
        imageView_share_movieDetail.setOnClickListener(this);
        imageView_back_movieDetail.setOnClickListener(this);
        paybutton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imageView_back_movieDetail:
                finish();
                break;
            case R.id.imageView_share_movieDetail:

                break;
            case R.id.bt_movie_pay:
                if (Config.CURRENT_USER!=null&&movie!=null) {
                    Record record=new Record();
                    record.setName(Config.CURRENT_USER);
                    record.setTitle(movie.getMovie_name());
                    record.setIcon(movie.getMovie_img_url());
                    record.setSummary(movie.getMovie_cast());
                    record.setPaid(Config.TICKET_UNPAID);
                    record.setPrice(38.0f);
                    db.save(record);
                    Toast.makeText(this,"已添加到待付订单",Toast.LENGTH_SHORT).show();
                }else if (Config.CURRENT_USER!=null&&hotMovie!=null) {
                    Record record=new Record();
                    record.setName(Config.CURRENT_USER);
                    record.setTitle(hotMovie.getMovie_name());
                    record.setIcon(hotMovie.getMovie_img_url());
                    record.setSummary(hotMovie.getMovie_cast());
                    record.setPaid(Config.TICKET_UNPAID);
                    record.setPrice(58.0f);
                    db.save(record);
                    Toast.makeText(this,"已添加到待付订单",Toast.LENGTH_SHORT).show();
                }else Toast.makeText(this,"请登录之后再购票",Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
