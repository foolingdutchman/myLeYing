package com.secondproject.leying;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.secondproject.Config;
import com.secondproject.LocationPatch.utils.OkHttpUtil;
import com.secondproject.mepatch.domain.User;
import com.secondproject.mepatch.utils.ImageUtils;
import com.secondproject.mepatch.utils.TimeUtils;

import net.tsz.afinal.FinalDb;
import net.tsz.afinal.db.sqlite.DbModel;

import java.io.FileNotFoundException;

public class ProfileActivity extends AppCompatActivity {
    private ImageView profileback;
    private LinearLayout profile1;
    private LinearLayout profile2;
    private LinearLayout profile3;
    private LinearLayout profile4;
    private LinearLayout profile5;
    private LinearLayout profile6;
    private LinearLayout profile7;
    private ImageView header;
    private TextView nickname;
    private TextView pickgender;
    private TextView pickbirth;
    private TextView credit;
    private User currentuser;
    private FinalDb db;
    private DbModel userModel;
    private SharedPreferences sharedPreferences;
    private PopupWindow popwindow;
    private View contentView;
    private int popHeight;
    private Button quitButton;
    private Intent intent;
    private Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        intent = getIntent();
        bundle = intent.getExtras();
        currentuser = (User) bundle.getSerializable("currentuser");


        initData();
        initView();
        popHeight = this.getWindowManager().getDefaultDisplay().getHeight() - 40;

        Log.i("profileIntent", "user-----" + currentuser.getName()
                +"---uri: "+currentuser.getSketch()+ "---" + currentuser.getMoblenumber());

        View.OnClickListener profilelistener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {

                    case R.id.ll_profile1:
                        Intent intent = new Intent();
                /* 开启Pictures画面Type设定为image */
                        intent.setType("image/*");
                /* 使用Intent.ACTION_GET_CONTENT这个Action */
                        intent.setAction(Intent.ACTION_GET_CONTENT);
                /* 取得相片后返回本画面 */
                        startActivityForResult(intent, Config.GALLERY_RESP);
                        break;
                    case R.id.ll_profile2:
                        final EditText nameet = new EditText(ProfileActivity.this);

                        new AlertDialog.Builder(ProfileActivity.this).setTitle("请输入昵称").setIcon(
                                android.R.drawable.ic_dialog_info).setView(
                                nameet).setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (nameet.getText().toString() == null) {
                                    Toast.makeText(ProfileActivity.this, "昵称不能为空", Toast.LENGTH_SHORT).show();
                                } else {
                                    String nicknamrstr = nameet.getText().toString();
                                    Config.CURRENT_USER = nicknamrstr;
                                    Log.i("profile", "nickname------" + nicknamrstr);
                                    db = FinalDb.create(ProfileActivity.this, Config.LEYING_DATABASE);
                                    currentuser.setName(nicknamrstr);
                                    db.update(currentuser);
                                }

                            }
                        }).setNegativeButton("取消", null).show();

                        nickname.setText(Config.CURRENT_USER);
                        break;
                    case R.id.ll_profile3:

                        new AlertDialog.Builder(ProfileActivity.this).setTitle("选择性别").setMultiChoiceItems(
                                new String[]{"男", "女"}, null, null)
                                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        String gender = null;
                                        if (which == 0) {
                                            gender = "男";
                                        } else gender = "女";
                                        currentuser.setGender(gender);
                                        db = FinalDb.create(ProfileActivity.this, Config.LEYING_DATABASE);
                                        db.update(currentuser);
                                    }
                                })
                                .setNegativeButton("取消", null).show();

                        pickgender.setText(currentuser.getGender());
                        break;
                    case R.id.ll_profile4:
                        new DatePickerDialog(ProfileActivity.this, new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                                  int dayOfMonth) {
                                String birth = year + "." + (monthOfYear + 1) + "." + dayOfMonth;
                                currentuser.setBirth(birth);
                                db.update(currentuser);
                            }
                        }, TimeUtils.getCurrentDateArray()[0], TimeUtils.getCurrentDateArray()[1],
                                TimeUtils.getCurrentDateArray()[2]).show();
                        pickbirth.setText(currentuser.getBirth());
                        break;
                    case R.id.ll_profile5:

                        break;
                    case R.id.ll_profile6:
                        initModifyPwdWindow();
                        popwindow = new PopupWindow(contentView, WindowManager.LayoutParams.MATCH_PARENT, popHeight);
                        popwindow.setFocusable(true);
                        ColorDrawable dw = new ColorDrawable(0x00000000);
                        popwindow.setBackgroundDrawable(dw);
                        popwindow.setAnimationStyle(R.style.settingwindowAnimation);
                        popwindow.showAtLocation(contentView, Gravity.BOTTOM, 0, 0);
                        break;
                    case R.id.ll_profile7:
                        initMobnumber();
                        popwindow = new PopupWindow(contentView, WindowManager.LayoutParams.MATCH_PARENT, popHeight);
                        popwindow.setFocusable(true);
                        ColorDrawable dw1 = new ColorDrawable(0x00000000);
                        popwindow.setBackgroundDrawable(dw1);
                        popwindow.setAnimationStyle(R.style.settingwindowAnimation);
                        popwindow.showAtLocation(contentView, Gravity.BOTTOM, 0, 0);
                        break;
                    case R.id.bt_profile_exit:

                        Config.CURRENT_USER = null;
                        currentuser = null;
                        sharedPreferences = getSharedPreferences(Config.USER_PREFERENCE, MODE_PRIVATE);
                        sharedPreferences.edit().putString("name", null).commit();
                        currentuser=new User();
                        bundle.putSerializable("currentuser", currentuser);
                        intent=ProfileActivity.this.getIntent();
                        intent.putExtras(bundle);
                        ProfileActivity.this.setResult(Config.PROFILE_RESP, intent);
                        finish();
                        break;

                }
            }
        };

        profile1.setOnClickListener(profilelistener);
        profile2.setOnClickListener(profilelistener);
        profile3.setOnClickListener(profilelistener);
        profile4.setOnClickListener(profilelistener);
        profile5.setOnClickListener(profilelistener);
        profile6.setOnClickListener(profilelistener);
        profile7.setOnClickListener(profilelistener);
        quitButton.setOnClickListener(profilelistener);

        profileback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bundle.putSerializable("currentuser", currentuser);
                intent.putExtras(bundle);
                ProfileActivity.this.setResult(Config.PROFILE_RESP, intent);
                finish();
            }
        });
    }

    private void initData() {
        db = FinalDb.create(this, Config.LEYING_DATABASE);
        if (Config.CURRENT_USER != null) {
            userModel = db.findDbModelBySQL("select * from " + Config.USER_TABLE +
                    " where name='" + Config.CURRENT_USER + "'");
        }
    }

    private void initMobnumber() {
        contentView = LayoutInflater.from(ProfileActivity.this).inflate(R.layout.modifynumber, null);
        final EditText newNumber = (EditText) contentView.findViewById(R.id.et_bond_verify);
        final TextView numbertext = (TextView) contentView.findViewById(R.id.tv_bond_mobile);

        String number = userModel.getString("moblenumber");
        numbertext.setText(number);
        Button verify = (Button) contentView.findViewById(R.id.bt_bond_verify);
        Button modifyPassord = (Button) contentView.findViewById(R.id.bt_modifynumber_confirm);
        modifyPassord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newnumber = newNumber.getText().toString();
                User user = new User();
                user.setId(userModel.getInt("id"));
                user.setName(Config.CURRENT_USER);
                user.setMoblenumber(newnumber);
                db.update(user);
            }
        });
    }

    private void initModifyPwdWindow() {
        contentView = LayoutInflater.from(ProfileActivity.this).inflate(R.layout.modifypassword, null);
        final EditText oldPassword = (EditText) contentView.findViewById(R.id.et_password_old);
        final EditText newPassword = (EditText) contentView.findViewById(R.id.et_password_new);
        final EditText repeatPassword = (EditText) contentView.findViewById(R.id.et_password_confirm);
        Button modifyPassord = (Button) contentView.findViewById(R.id.bt_modifypassword_confirm);
        modifyPassord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String opwd = oldPassword.getText().toString();
                String npwd = newPassword.getText().toString();
                String rpwd = repeatPassword.getText().toString();
                if ("".equals(opwd) && opwd == "") {
                    Toast.makeText(ProfileActivity.this, "请输入原密码", Toast.LENGTH_SHORT).show();
                    return;
                } else if ("".equals(npwd) && opwd == "") {
                    Toast.makeText(ProfileActivity.this, "请输入新密码", Toast.LENGTH_SHORT).show();
                    return;
                } else if ("".equals(rpwd) && rpwd == "") {
                    Toast.makeText(ProfileActivity.this, "请再输入新密码", Toast.LENGTH_SHORT).show();
                    return;
                } else if (!npwd.equals(rpwd)) {
                    Toast.makeText(ProfileActivity.this, "密码输入不一致", Toast.LENGTH_SHORT).show();
                    return;
                } else {

                    String pwd = userModel.getString("password");
                    if (!pwd.equals(opwd)) {
                        Toast.makeText(ProfileActivity.this, "密码输入不正确", Toast.LENGTH_SHORT).show();
                        return;
                    } else {
                        User user = new User();
                        user.setId(userModel.getInt("id"));
                        user.setPassword(npwd);
                        db.update(user);
                    }
                }
            }
        });

    }

    private void initView() {
        profileback = (ImageView) findViewById(R.id.iv_setting_back);
        profile1 = (LinearLayout) findViewById(R.id.ll_profile1);
        profile2 = (LinearLayout) findViewById(R.id.ll_profile2);
        profile3 = (LinearLayout) findViewById(R.id.ll_profile3);
        profile4 = (LinearLayout) findViewById(R.id.ll_profile4);
        profile5 = (LinearLayout) findViewById(R.id.ll_profile5);
        profile6 = (LinearLayout) findViewById(R.id.ll_profile6);
        profile7 = (LinearLayout) findViewById(R.id.ll_profile7);

        quitButton = (Button) findViewById(R.id.bt_profile_exit);
        header = (ImageView) findViewById(R.id.iv_myfile_head);
        nickname = (TextView) findViewById(R.id.tv_myfile_nickname);
        pickgender = (TextView) findViewById(R.id.tv_myfile_gender);
        pickbirth = (TextView) findViewById(R.id.tv_myfile_birth);
        credit = (TextView) findViewById(R.id.tv_profile_credit);
        if (currentuser != null) {
            nickname.setText(Config.CURRENT_USER);
            pickbirth.setText(currentuser.getBirth());
            pickgender.setText(currentuser.getGender());
            credit.setText("乐影钱包" + currentuser.getMoney() + "元");
            if ((currentuser.getSketch())!=null&&(currentuser.getSketch())!=""
                    &&!(currentuser.getSketch()).equals("null")) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                       final Bitmap bitmap = ImageLoader.getInstance().loadImageSync(currentuser.getSketch());

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                header.setImageBitmap(ImageUtils.getRoundedCornerBitmap(bitmap));
                            }
                        });
                    }
                }).start();

            }


        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            Uri uri = data.getData();
            User user = new User();
            user.setId(userModel.getInt("id"));
            user.setSketch(uri.toString());
            db.update(user);
            Log.e("uri", uri.toString());
            ContentResolver cr = this.getContentResolver();
            try {
                Bitmap bitmap = BitmapFactory.decodeStream(cr.openInputStream(uri));
                header.setImageBitmap(ImageUtils.getRoundedCornerBitmap(bitmap));
//                header.setImageBitmap(ImageUtils.getRoundedCornerBitmap(bitmap));


                /* 将Bitmap设定到ImageView */
            } catch (FileNotFoundException e) {
                Log.e("Exception", e.getMessage(), e);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

}
