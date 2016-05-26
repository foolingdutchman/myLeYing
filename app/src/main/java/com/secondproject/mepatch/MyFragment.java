package com.secondproject.mepatch;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.display.BitmapDisplayer;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.secondproject.Config;
import com.secondproject.LocationPatch.utils.OkHttpUtil;
import com.secondproject.leying.MainActivity;
import com.secondproject.leying.OderActivity;
import com.secondproject.leying.ProfileActivity;
import com.secondproject.leying.R;
import com.secondproject.mepatch.Callback.Myfragmentcallback;
import com.secondproject.mepatch.adapters.MeSettingAdapter;
import com.secondproject.mepatch.adapters.PaylistAdapter;
import com.secondproject.mepatch.adapters.RegistpageAdapter;
import com.secondproject.mepatch.adapters.ShowPageAdapter;
import com.secondproject.mepatch.domain.Record;
import com.secondproject.mepatch.domain.User;
import com.secondproject.mepatch.fragments.MeShowFragment;
import com.secondproject.mepatch.fragments.RegistFragment0;
import com.secondproject.mepatch.fragments.RegistFragment1;
import com.secondproject.mepatch.utils.ImageUtils;
import com.secondproject.mepatch.utils.TimeUtils;

import net.tsz.afinal.FinalDb;
import net.tsz.afinal.db.sqlite.DbModel;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.ShareSDK;
import demo.login.LoginApi;
import demo.login.OnLoginListener;
import demo.login.UserInfo;

/**
 * Created by Administrator on 2016/5/16 0016.
 */
public class MyFragment extends Fragment {
    private static int ME_BG = 100;
    private static int REGIST_BG = 200;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private ViewPager registpage;
    private ImageView registbg;
    private FinalDb db;
    private User currentuser;
    private View view;
    private ViewPager showHeader;
    private ImageView skecth;
    private Button loggin;
    private MeSettingAdapter meSettingAdapter;
    private ShowPageAdapter showPageAdapter;
    private List<android.support.v4.app.Fragment> meShowFragmentList;
    private LinearLayout setting1;
    private LinearLayout setting2;
    private LinearLayout setting3;
    private LinearLayout setting4;
    private PopupWindow loginWindow = null;
    private PopupWindow settingWindow = null;
    private View contentView = null;
    private ImageView appseting;
    private TextView nametext;
    private int popHeight;
    private Intent intent;
    private Myfragmentcallback myfragmentcallback;

    private String[] settings = {
            "全部订单", "待支付订单", "优惠券", "影院会员卡"
    };
    private int[] icons = {
            R.drawable.wdly_icon_1,
            R.drawable.wdly_icon_daizhifu,
            R.drawable.wdly_icon_2,
            R.drawable.wdly_icon_3,
    };
    private int[] bg_show = {
            R.drawable.me_anim_bg1,
            R.drawable.me_anim_bg2,
            R.drawable.me_anim_bg3,
            R.drawable.me_anim_bg4,
    };
    private int[] regist_bg = {
            R.drawable.icon_login1,
            R.drawable.icon_login2,
            R.drawable.icon_login3,
            R.drawable.icon_login4
    };
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            Log.i("popwindow", "消息已接受");
            if (msg.what == ME_BG) {

                showHeader.setCurrentItem(msg.arg1, false);

            } else if (msg.what == REGIST_BG) {
                Log.i("popwindow", "REGIST_BG消息已接受");
                registbg.setImageResource(msg.arg1);
            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ImageUtils.initImageLoader(getActivity());
        initAssumeData();
        Rect outRect = new Rect();
        getActivity().getWindow().findViewById(Window.ID_ANDROID_CONTENT).getDrawingRect(outRect);
        popHeight = getActivity().getWindowManager().getDefaultDisplay().getHeight() - 40;
        Log.i("popupwindow", "height------" + popHeight);
        ImageUtils.initImageLoader(getActivity());
        sharedPreferences = MainActivity.sharedPreferences;
        editor = sharedPreferences.edit();
        db = FinalDb.create(getActivity(), Config.LEYING_DATABASE);
        initView();
        initControler();
        showHeader.setAdapter(showPageAdapter);
        new Thread(new Runnable() {
            @Override
            public void run() {
                int i = 0;
                while (true) {
                    if (i > 3) {
                        i = 0;
                    }
                    Message message = Message.obtain();
                    message.what = ME_BG;
                    message.arg1 = i;
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    handler.sendMessage(message);
                    i++;
                }
            }
        }).start();

        return view;
    }

    private void initAssumeData() {
        db = FinalDb.create(getActivity(), Config.LEYING_DATABASE);
        if (db.findDbModelBySQL("select * from " + Config.USER_TABLE +
                " where name='" + "张三'") == null) {
            User user = new User();
            user.setName("张三");
            user.setMoblenumber("15910871220");
            user.setPassword("888888");
            db.save(user);
        }


    }

    private void initControler() {
        meSettingAdapter = new MeSettingAdapter(icons, settings, getActivity());
        meShowFragmentList = new ArrayList<>();
        showPageAdapter = new ShowPageAdapter(MainActivity.fragmentManager);
        for (int i = 0; i < bg_show.length; i++) {
            MeShowFragment meShowFragment = new MeShowFragment();
            meShowFragment.setBg_show(bg_show[i]);
            meShowFragmentList.add(meShowFragment);
        }
        showPageAdapter.setMeShowFragmentList(meShowFragmentList);
    }


    private void initView() {
        view = LinearLayout.inflate(getActivity(), R.layout.myfragment, null);
        showHeader = (ViewPager) view.findViewById(R.id.vp_me_show);
        showHeader.setFocusable(false);
        skecth = (ImageView) view.findViewById(R.id.iv_me_skecth);
        loggin = (Button) view.findViewById(R.id.bt_me_login);
        setting1 = (LinearLayout) view.findViewById(R.id.ll_me_setting1);
        setting2 = (LinearLayout) view.findViewById(R.id.ll_me_setting2);
        setting3 = (LinearLayout) view.findViewById(R.id.ll_me_setting3);
        setting4 = (LinearLayout) view.findViewById(R.id.ll_me_setting4);
        appseting = (ImageView) view.findViewById(R.id.iv_me_setting);
        nametext = (TextView) view.findViewById(R.id.tv_me_username);
        if (Config.CURRENT_USER != null) {
            loggin.setVisibility(View.INVISIBLE);
            nametext.setVisibility(View.VISIBLE);
            nametext.setText(Config.CURRENT_USER);
            currentuser = new User();
            currentuser.setName(Config.CURRENT_USER);
            DbModel model = db.findDbModelBySQL("select * from " + Config.USER_TABLE +
                    " where name='" + Config.CURRENT_USER + "'");
            if (model != null) {

                String sketchUri = model.getString("sketch");
                String birth = model.getString("birth");
                String number = model.getString("moblenumber");
                String gender = model.getString("gender");
                float money = model.getFloat("money");

                currentuser.setBirth(birth);
                currentuser.setMoblenumber(number);
                currentuser.setGender(gender);
                currentuser.setMoney(money);
                currentuser.setSketch(sketchUri);
                Log.i("profileIntent", "user-----" + currentuser.getName() + "---" +
                        currentuser.getMoblenumber());
                Bitmap bitmap = ImageLoader.getInstance().loadImageSync(currentuser.getSketch());
                if (bitmap != null) {
                    skecth.setImageBitmap(ImageUtils.getRoundedCornerBitmap(bitmap));

//                    ImageLoaderConfiguration configuration = ImageLoaderConfiguration
//                            .createDefault(getActivity());
//                    DisplayImageOptions options = new DisplayImageOptions.Builder()
//                            .cacheInMemory(true)
//                            .cacheOnDisc(true)
//                            .displayer(new RoundedBitmapDisplayer(40))
//                            .bitmapConfig(Bitmap.Config.RGB_565)
//                            .build();
//                    ImageLoader.getInstance().displayImage(sketchUri, skecth,options);
                }
            }
        }


        setting1.setOnClickListener(onClickListener);
        setting2.setOnClickListener(onClickListener);
        setting3.setOnClickListener(onClickListener);
        setting4.setOnClickListener(onClickListener);
        skecth.setOnClickListener(onClickListener);
        loggin.setOnClickListener(onClickListener);
        appseting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initsettingWindow();
                settingWindow.setAnimationStyle(R.style.settingwindowAnimation);
                settingWindow.showAtLocation(contentView, Gravity.BOTTOM, 0, 0);
            }
        });
    }

    public void setUser(User currentuser) {
        this.currentuser = currentuser;

    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (Config.CURRENT_USER == null) {
                //尚未登录用户
                initloginWindow(R.id.iv_me_skecth);
                loginWindow.setAnimationStyle(R.style.popupwindowAnimation);
                Log.i("popupwindow", "动画已设置");

                loginWindow.showAtLocation(contentView, Gravity.BOTTOM, 0, 0);
            } else {

                switch (v.getId()) {
                    case R.id.ll_me_setting1:
                        intent = new Intent(getActivity(), OderActivity.class);
                        intent.putExtra("type", Config.TICKET_PAID);
                        startActivity(intent);
                        Toast.makeText(getActivity(), "1已点击", Toast.LENGTH_SHORT).show();

                        return;
                    case R.id.ll_me_setting2:
                        Toast.makeText(getActivity(), "2已点击", Toast.LENGTH_SHORT).show();
                        intent = new Intent(getActivity(), OderActivity.class);
                        intent.putExtra("type", Config.TICKET_UNPAID);
                        startActivity(intent);
                        return;
                    case R.id.ll_me_setting3:
                        Toast.makeText(getActivity(), "3已点击", Toast.LENGTH_SHORT).show();
                        initDiscountWindow();
                        break;
                    case R.id.ll_me_setting4:
                        initclubmemberwindow();
                        break;
                    case R.id.iv_me_skecth:
                        Toast.makeText(getActivity(), "4已点击", Toast.LENGTH_SHORT).show();
                        intent = new Intent(getActivity(), ProfileActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("currentuser", currentuser);
                        Log.i("profileIntent", "Intentuser-----" +
                                currentuser.getName() + "---" + currentuser.getMoblenumber());
                        intent.putExtras(bundle);
                        getActivity().startActivityForResult(intent, Config.PROFILE_LOG);
                        return;

                }
                settingWindow = new PopupWindow(contentView, WindowManager.LayoutParams.MATCH_PARENT, popHeight);
                settingWindow.setFocusable(true);
                ColorDrawable dw = new ColorDrawable(0x00000000);
                settingWindow.setBackgroundDrawable(dw);
                settingWindow.setAnimationStyle(R.style.settingwindowAnimation);
                settingWindow.showAtLocation(contentView, Gravity.BOTTOM, 0, 0);
            }
        }


    };

    private void initsettingWindow() {
        contentView = LayoutInflater.from(getActivity()).inflate(R.layout.setting, null);
        settingWindow = new PopupWindow(contentView, WindowManager.LayoutParams.MATCH_PARENT, popHeight);
        settingWindow.setFocusable(true);
        ColorDrawable dw = new ColorDrawable(0x00000000);
        settingWindow.setBackgroundDrawable(dw);
        LinearLayout clearcache = (LinearLayout) contentView.findViewById(R.id.ll_setting1);
        final LinearLayout message = (LinearLayout) contentView.findViewById(R.id.ll_setting2);
        LinearLayout feedback = (LinearLayout) contentView.findViewById(R.id.ll_setting3);
        LinearLayout version = (LinearLayout) contentView.findViewById(R.id.ll_setting4);
        LinearLayout aboutus = (LinearLayout) contentView.findViewById(R.id.ll_setting5);
        final ImageView messagebutton = (ImageView) contentView.findViewById(R.id.iv_setting_message);
        ImageView settingback = (ImageView) contentView.findViewById(R.id.iv_setting_back);
        settingback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                settingWindow.dismiss();
            }
        });
        View.OnClickListener settinglistener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.ll_setting1:
                        new AlertDialog.Builder(getActivity()).setMessage("清除缓存会影响离线浏览，您确认清理缓存么？")
                                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        return;
                                    }
                                }).setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        }).show();
                        break;
                    case R.id.ll_setting2:
                        if (messagebutton.getTag() == "message") {
                            messagebutton.setImageResource(R.drawable.shezhi_kaiguan_off);
                            message.setTag("nomessage");
                            Toast.makeText(getActivity(), "您将不能收到最新活动或新片上映信息啦", Toast.LENGTH_SHORT).show();
                        } else {
                            messagebutton.setImageResource(R.drawable.shezhi_kaiguan_on);
                            message.setTag("message");
                            Toast.makeText(getActivity(), "新片上映或活动发布时，您将在第一时间收到我的信息", Toast.LENGTH_SHORT).show();
                        }
                        break;
                    case R.id.ll_setting3:

                        break;
                    case R.id.ll_setting4:
                        Toast.makeText(getActivity(), "不要急呀，下个版本还没出炉呢", Toast.LENGTH_SHORT).show();

                        break;
                    case R.id.ll_setting5:
                        break;

                }
            }
        };
        clearcache.setOnClickListener(settinglistener);
        message.setOnClickListener(settinglistener);
        feedback.setOnClickListener(settinglistener);
        version.setOnClickListener(settinglistener);
        aboutus.setOnClickListener(settinglistener);

    }


    private void initclubmemberwindow() {
        contentView = LayoutInflater.from(getActivity()).inflate(R.layout.clubmembers, null);
        ImageView back = (ImageView) contentView.findViewById(R.id.iv_paylist_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                settingWindow.dismiss();
            }
        });
    }


    private void initDiscountWindow() {
        contentView = LayoutInflater.from(getActivity()).inflate(R.layout.discountbonds, null);
        ImageView back = (ImageView) contentView.findViewById(R.id.iv_discount_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                settingWindow.dismiss();
            }
        });
    }


    private void initloginWindow(int viewId) {
        switch (viewId) {

        }
        contentView = LayoutInflater.from(getActivity()).inflate(R.layout.registframent, null);
        loginWindow = new PopupWindow(contentView, WindowManager.LayoutParams.MATCH_PARENT, popHeight);
        loginWindow.setFocusable(true);
        ColorDrawable dw = new ColorDrawable(0x00000000);
        loginWindow.setBackgroundDrawable(dw);
        registpage = (ViewPager) contentView.findViewById(R.id.vp_regist_show);
        registbg = (ImageView) contentView.findViewById(R.id.iv_bg_regist);
        new Thread(new Runnable() {
            @Override
            public void run() {
                int i = 0;
                while (true) {
                    if (i > 3) {
                        i = 0;
                    }
                    Message message = Message.obtain();
                    message.what = REGIST_BG;
                    message.arg1 = regist_bg[i];
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    handler.sendMessage(message);
                    Log.i("popwindow", "消息已发出");
                    i++;
                }
            }
        }).start();
        List<View> registpagelist = new ArrayList<>();
        View regestpage0 = View.inflate(getActivity(), R.layout.registpage0, null);
        View regestpage1 = View.inflate(getActivity(), R.layout.registpage1, null);
        registpagelist.add(regestpage0);
        registpagelist.add(regestpage1);
        RegistpageAdapter regisAdapter = new RegistpageAdapter(registpagelist);
        registpage.setAdapter(regisAdapter);

        ImageView closeRegist = (ImageView) contentView.findViewById(R.id.iv_regist_back);
        closeRegist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginWindow.dismiss();
            }
        });
        Button fastregist = (Button) regestpage1.findViewById(R.id.bt_regist_fastlogin);
        fastregist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registpage.setCurrentItem(0);
            }
        });
        //登陆功能
        final EditText loadnumber = (EditText) regestpage1.findViewById(R.id.et_regist_number);

        final EditText loadPassword = (EditText) regestpage1.findViewById(R.id.et_regist_password);

        Button loadin = (Button) regestpage1.findViewById(R.id.bt_regist_login);
        loadin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String opwd = null;
                String name = null;
                String number = loadnumber.getText().toString();
                String password = loadPassword.getText().toString();
                if (number.equals("")) {
                    Toast.makeText(getActivity(), "您账号忘记输入:)", Toast.LENGTH_SHORT).show();
                } else if (password.equals("")) {
                    Toast.makeText(getActivity(), "哎，您密码忘了:)", Toast.LENGTH_SHORT).show();
                } else {
                    Log.i("login", number + "---------" + password);
                    db = FinalDb.create(getActivity(), Config.LEYING_DATABASE);
                    DbModel model1 = new DbModel();
                    if ((model1 = db.findDbModelBySQL("select * from " + Config.USER_TABLE +
                            " where moblenumber='" + number + "'")) != null) {
                        opwd = model1.getString("password");
                        name = model1.getString("name");
                        Log.i("login", "password-------" + password);
                        Log.i("login", "rpassword-------" + opwd + "-------" + name);
                    }
                    if (opwd != null && opwd.equals(password)) {
                        Toast.makeText(getActivity(), "欢迎你:)", Toast.LENGTH_SHORT).show();
                        Config.CURRENT_USER = name;
                        currentuser = new User();
                        currentuser.setMoblenumber(model1.getString("moblenumber"));
                        currentuser.setPassword(model1.getString("password"));
                        currentuser.setName(model1.getString("name"));
                        currentuser.setBirth(model1.getString("birth"));
                        currentuser.setGender(model1.getString("gender"));
                        currentuser.setSketch(model1.getString("sketch"));
                        currentuser.setMoney(model1.getFloat("money"));
                        nametext.setVisibility(View.VISIBLE);
                        nametext.setText(Config.CURRENT_USER);
                        loggin.setVisibility(View.INVISIBLE);
                        Bitmap bitmap = ImageLoader.getInstance().loadImageSync(currentuser.getSketch());
                        if (bitmap!= null) {
                            skecth.setImageBitmap(ImageUtils.getRoundedCornerBitmap(bitmap));

                        }

                        editor.putString("name", name);
                        editor.commit();
                        loginWindow.dismiss();
                    }
                }
            }
        });

        //注册功能
        final EditText regestnumber = (EditText) regestpage0.findViewById(R.id.et_regist_rnumber);

        final EditText regestPassword = (EditText) regestpage0.findViewById(R.id.et_regist_rpassword);

        final CheckBox checknotify = (CheckBox) regestpage0.findViewById(R.id.cb_regist_readnotify);
        Button regist = (Button) regestpage0.findViewById(R.id.bt_regist_regist);
        regist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String rnumber = regestnumber.getText().toString();
                String rpassword = regestPassword.getText().toString();
                if (!checknotify.isChecked()) {
                    Toast.makeText(getActivity(), "请阅读并同意服务协议", Toast.LENGTH_SHORT).show();
                    return;
                } else if (rnumber == null || rnumber.equals("")) {
                    Toast.makeText(getActivity(), "请输入手机号或邮箱", Toast.LENGTH_SHORT).show();
                    return;
                } else if (rpassword == null || rpassword.equals("")) {
                    Toast.makeText(getActivity(), "请输入密码", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    db = FinalDb.create(getActivity(), Config.LEYING_DATABASE);
                    User user = new User();
                    user.setName("defalutname");

                    user.setMoney(0);
                    user.setMoblenumber(rnumber);
                    user.setPassword(rpassword);
                    Log.i("login", user.getMoblenumber() + "--------" + user.getPassword());
                    db.save(user);
                    Toast.makeText(getActivity(), "注册成功，请重新登陆", Toast.LENGTH_SHORT).show();
                    registpage.setCurrentItem(1);
                }
            }
        });
        Button toloadin = (Button) regestpage0.findViewById(R.id.bt_regist_tologin);
        toloadin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registpage.setCurrentItem(1);
            }
        });

        //第三方登录
        ImageView qqLogin = (ImageView) regestpage1.findViewById(R.id.iv_log_qq);
        ImageView weixinLogin = (ImageView) regestpage1.findViewById(R.id.iv_log_weixin);
        ImageView aliLogin = (ImageView) regestpage1.findViewById(R.id.iv_log_zhifu);
        ImageView sinaLogin = (ImageView) regestpage1.findViewById(R.id.iv_log_weibo);


        ShareSDK.initSDK(getActivity());
        final Platform[] Platformlist = ShareSDK.getPlatformList();
        for (int i = 0; i < Platformlist.length; i++) {
            Log.i("sharedSDK", "------" + Platformlist[i].getName());
        }
        View.OnClickListener quickLoginListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Platform platform = null;
                switch (v.getId()) {
                    case R.id.iv_log_qq:
                        Log.i("shareSdk", "----微信点击");

                        platform = Platformlist[3];
                        break;
                    case R.id.iv_log_weibo:
                        Log.i("shareSdk", "----微信点击");
                        platform = Platformlist[0];
                        break;
                    case R.id.iv_log_weixin:
                        Log.i("shareSdk", "----微信点击");
                        platform = Platformlist[1];
                        break;
                    case R.id.iv_log_zhifu:
                        Log.i("shareSdk", "----微信点击");
                        platform = Platformlist[5];
                        break;

                }
                String name = platform.getName();
//                System.out.println("名字"+name+" "+getString(R.string.login_to_format, name));

                //登陆逻辑的调用
                login(name);
            }
        };
        qqLogin.setOnClickListener(quickLoginListener);
        weixinLogin.setOnClickListener(quickLoginListener);
        sinaLogin.setOnClickListener(quickLoginListener);
        aliLogin.setOnClickListener(quickLoginListener);

    }

    private void login(String platformName) {
        LoginApi api = new LoginApi();
        //设置登陆的平台后执行登陆的方法
        api.setPlatform(platformName);
        api.setOnLoginListener(new OnLoginListener() {
            public boolean onLogin(String platform, HashMap<String, Object> res) {
                // 在这个方法填写尝试的代码，返回true表示还不能登录，需要注册
                // 此处全部给回需要注册
                Log.i("loggingpage", "platform------" + platform);
                Set<String> keyset = res.keySet();
                Iterator<String> iterator = keyset.iterator();
                while (iterator.hasNext()) {
                    String key = iterator.next();
                    Log.i("loggingpage", "key--------" + res.get(key).toString());
                }


                return true;
            }

            public boolean onRegister(UserInfo info) {
                // 填写处理注册信息的代码，返回true表示数据合法，注册页面可以关闭
                return true;
            }
        });
        api.login(getActivity());
    }


}
