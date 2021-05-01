package net.feiyuyu.game;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;


import org.apache.http.util.EncodingUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class mainActivity extends Activity {

    TextView tv;
    public static MediaPlayer mp;
    public boolean sound = true;
    private SharedPreferences sp;

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.main);


        File file=new File("/data/data/net.feiyuyu.game/files/music.txt");
        if(!file.exists()) {
            //System.out.println("执行了");
            writeFile("music.txt", "true");
        }
        mp = MediaPlayer.create(this, R.raw.bg);
        mp.setLooping(true);
        //sp = this.getSharedPreferences("config",MODE_PRIVATE);
        if(String.valueOf(readFile("music.txt")).equals("true")) {
            mp.start();
        }else {
            mp.start();
            mp.pause();
        }
        //if(!sp.getBoolean("sound",true)) {
        //    sound = false;
        //}


        file=new File("/data/data/net.feiyuyu.game/files/score.txt");
        if(!file.exists()) {
            //System.out.println("执行了");
            writeFile("score.txt", "0");
        }

        String str = readFile("score.txt");
        tv = (TextView)findViewById(R.id.mainScore);
        tv.setText(str);

        ImageButton settingBtn = (ImageButton) findViewById(R.id.settingBtn);
        settingBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(mainActivity.this,settingActivity.class);
                startActivity(intent);
                //finish();
            }
        });

        Button gameStartBtn = (Button) findViewById(R.id.gameStartBtn);
        gameStartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mainActivity.this,gameSelectActivity.class);
                startActivity(intent);
                //finish();
            }
        });

    }

    //返回activity时更新积分
    public void onRestart(){
        super.onRestart();
        tv.setText(readFile("score.txt"));
        if(String.valueOf(readFile("music.txt")).equals("true")) {
            mp.start();
        }else {
            mp.start();
            mp.pause();
        }
    }

    //后台停止音乐
    public void onPause(){
        super.onPause();
        mp.pause();
    }

    //test i/o stream
    public void writeFile(String fileName,String msg){
        try{
            FileOutputStream fout = openFileOutput(fileName,MODE_PRIVATE);
            byte [] bytes = msg.getBytes();
            fout.write(bytes);
            fout.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String readFile(String fileName){
        String res = "";
        try{
            FileInputStream fin = openFileInput(fileName);
            int length = fin.available();
            byte [] buffer = new byte[length];
            fin.read(buffer);
            res = EncodingUtils.getString(buffer,"UTF-8");
            fin.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("File not found");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return res;
    }

    public void creatfile(){
        String dir = "/data/data/net.feiyuyu.net/files/"; // 需要创建的目录，sdcard目录一定存在，所以只用判断一级目录
        File file = new File(dir);
        if (!file.exists())// 判断当前目录是否存在，存在返回true,否则返回false
        file.mkdir();// 如果不存在则创建目录
        return;
    }
}
