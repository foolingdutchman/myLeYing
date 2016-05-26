package com.secondproject.leying;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.secondproject.Config;
import com.secondproject.mepatch.adapters.PaylistAdapter;
import com.secondproject.mepatch.domain.Record;

import net.tsz.afinal.FinalDb;

import java.util.ArrayList;
import java.util.List;

public class OderActivity extends AppCompatActivity {
   private FinalDb db;
    private List<Record>list;
    private Bundle bundle;
    PaylistAdapter paylistadapter;
    TextView title;
    ListView listView;
    ImageView back;
    TextView bgtext;
    ImageView bgimage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oder);
        Intent intent=getIntent();
        int id=intent.getIntExtra("type",0);
        db=FinalDb.create(this,Config.LEYING_DATABASE);

        initView();

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        if (id == Config.TICKET_PAID) {
            title.setText("已支付订单");
             list = new ArrayList<>();
            list = db.findAllByWhere(Record.class, "name='" + Config.CURRENT_USER + "' and paid=" + Config.TICKET_PAID + "");
            if (list!=null&&!(list.isEmpty())){
                bgimage.setVisibility(View.INVISIBLE);
                bgtext.setVisibility(View.INVISIBLE);
                for (int i = 0; i < list.size(); i++) {

                    Log.i("orderlist",list.get(i).getTitle()+"-----------"+list.get(i).getSummary());
                }
            }
            paylistadapter = new PaylistAdapter(list, this, Config.TICKET_PAID);
            listView.setAdapter(paylistadapter);
        } else if (id == Config.TICKET_UNPAID) {
            Log.i("orderlist", "---启动配置器");
            list= new ArrayList<>();
            list = db.findAllByWhere(Record.class, "name='" + Config.CURRENT_USER + "' and paid= " + Config.TICKET_UNPAID + "");
            if (list!=null&&!(list.isEmpty())){
                bgimage.setVisibility(View.INVISIBLE);
                bgtext.setVisibility(View.INVISIBLE);
                for (int i = 0; i < list.size(); i++) {

                    Log.i("orderlist",list.get(i).getTitle()+"-----------"+list.get(i).getSummary());
                }
            }
            paylistadapter = new PaylistAdapter(list, this, Config.TICKET_UNPAID);
            listView.setAdapter(paylistadapter);
        }
    }

    private void initView() {
        title= (TextView)findViewById(R.id.tv_order_title);
        listView = (ListView) findViewById(R.id.lv_paylist);
        back = (ImageView) findViewById(R.id.iv_paylist_back);
        bgimage= (ImageView) findViewById(R.id.iv_meiyoujilu);
        bgtext= (TextView) findViewById(R.id.tv_meiyoujilu);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode==Config.PAY_REQ&&resultCode==Config.PAY_SUCCESS) {
           bundle= data.getExtras();
            Record record=(Record)bundle.getSerializable("payInfo");
            list.remove(record);
            paylistadapter.notifyDataSetChanged();
            record.setPaid(Config.TICKET_PAID);
            db.update(record);

        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
