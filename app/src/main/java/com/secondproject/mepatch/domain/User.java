package com.secondproject.mepatch.domain;


import com.secondproject.Config;

import net.tsz.afinal.annotation.sqlite.Table;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * Created by liushuai on 16/4/30.
 */

@Table(name = Config.USER_TABLE)
public class User implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id;


    private String name;
    private String password;
    private String sketch;
    private String birth;
    private String gender;
    private float money;
    private String moblenumber;


    public String getSketch() {
        return sketch;
    }

    public void setSketch(String sketch) {
        this.sketch = sketch;
    }


    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public float getMoney() {
        return money;
    }

    public void setMoney(float money) {
        this.money = money;
    }

    public String getMoblenumber() {
        return moblenumber;
    }

    public void setMoblenumber(String moblenumber) {
        this.moblenumber = moblenumber;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public static void main(String[] args)
    {//以下代码实现序列化
        try
        {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("my.out"));//输出流保存的文件名为 my.out ；ObjectOutputStream能把Object输出成Byte流
            User user=new User();
            oos.writeObject(user);
            oos.flush();  //缓冲流
            oos.close(); //关闭流
        } catch (FileNotFoundException e)
        {
            e.printStackTrace();
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        fan();//调用下面的  反序列化  代码
    }
    public static void fan()//反序列的过程
    {
        ObjectInputStream oin = null;//局部变量必须要初始化
        try
        {
            oin = new ObjectInputStream(new FileInputStream("my.out"));
        } catch (FileNotFoundException e1)
        {
            e1.printStackTrace();
        } catch (IOException e1)
        {
            e1.printStackTrace();
        }
        User user = null;
        try {
            user = (User ) oin.readObject();//由Object对象向下转型为MyTest对象
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("name="+user.name);
        System.out.println("id="+user.id);
    }
}
