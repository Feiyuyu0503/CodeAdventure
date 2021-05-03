package net.feiyuyu.game.checkpoint;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import net.feiyuyu.game.Configuration;
import net.feiyuyu.game.R;
import net.feiyuyu.game.gameSelectActivity;

import org.apache.http.util.EncodingUtils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class chooseConst extends Activity {

    changeBtn cb;
    ImageButton constBtn;
    ImageButton constBtn1;
    ImageButton varBtn;
    ImageButton varBtn1;
    float x = 0,y = 0;
    float x1 = 0,y1 = 0;
    float vx = 0,vy = 0;
    float vx1 = 0,vy1 = 0;
    int a=0,a1=0,va=0,va1=0; //加速度

    boolean isSuccess = false; //游戏积分是否达标

    TextView scoreTv;         //积分

    int screenHeight;         //屏幕宽度
    int screenWidth;          //屏幕高度

    CardView tip;    //游戏成功提示
    Button retry;
    Button nextCp;

    Timer timer;

    Button startBtn;
    Button createBtn;
    CardView card;
    Button okBtn;
    TextView cpName;

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        //去除标题栏
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //去除状态栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.choose_game_view);

        WindowManager wm = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
        screenWidth = wm.getDefaultDisplay().getWidth();
        screenHeight = wm.getDefaultDisplay().getHeight();                     //获取屏幕宽和高

        //游戏成功提示
        tip = (CardView) findViewById(R.id.tipCard);
        //"重玩"按钮
        retry = (Button) findViewById(R.id.retry);
        retry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //temp dev test
                delScore(100);
                startActivity(getIntent());
                finish();
            }
        });
        //"下一关"按钮
        nextCp = (Button)findViewById(R.id.nextCp);
        nextCp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(chooseConst.this, chooseVar.class);
                startActivity(intent);
                finish();
            }
        });

        //获取当前积分
        scoreTv = (TextView)findViewById(R.id.score);
        String res = readFile("score.txt");
        scoreTv.setText(res);

        x = (int)(Math.random()*1800)+50;avoidOverLap();
        x1 = (int)(Math.random()*1800)+50;avoidOverLap();
        vx = (int)(Math.random()*1800)+50;avoidOverLap();
        vx1 = (int)(Math.random()*1800)+50;avoidOverLap();

        constBtn = (ImageButton) findViewById(R.id.constBtn);
        constBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                y = 0;
                a = 0;
                x = (int)(Math.random()*1800)+50;
                constBtn.setX(x);
                constBtn.setY(y);
                addScore(5);
                String res = readFile("score.txt");
                scoreTv.setText(res);
                if(isSuccess())
                    tip.setVisibility(View.VISIBLE);
            }
        });

        constBtn1 = (ImageButton)findViewById(R.id.constBtn1);
        constBtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                y1 = 0;
                a1 = 0;
                x1 = (int)(Math.random()*1800)+50;
                constBtn1.setX(x1);
                constBtn1.setY(y1);
                addScore(5);
                String res = readFile("score.txt");
                scoreTv.setText(res);
                if(isSuccess())
                    tip.setVisibility(View.VISIBLE);
            }
        });

        varBtn = (ImageButton) findViewById(R.id.varBtn);
        varBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vy = 0;
                va = 0;
                vx = (int)(Math.random()*1800)+50;
                varBtn.setX(vx);
                varBtn.setY(vy);
                Toast.makeText(chooseConst.this, "错啦！", Toast.LENGTH_SHORT).show();
                delScore(10);
                String res = readFile("score.txt");
                scoreTv.setText(res);
                if(isSuccess())
                    tip.setVisibility(View.VISIBLE);
            }
        });

        varBtn1 = (ImageButton)findViewById(R.id.varBtn1);
        varBtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vy1 = 0;
                va1 = 0;
                vx1 = (int)(Math.random()*1800)+50;
                varBtn1.setX(vx1);
                varBtn1.setY(vy1);
                Toast.makeText(chooseConst.this, "错啦！", Toast.LENGTH_SHORT).show();
                delScore(10);
                String res = readFile("score.txt");
                scoreTv.setText(res);
                if(isSuccess())
                    tip.setVisibility(View.VISIBLE);
            }
        });

        constBtn.setX(x);
        constBtn1.setX(x1);
        varBtn.setX(vx);
        varBtn1.setX(vx1);

        //cb = new changeBtn(chooseConst.this);
        //LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-1,-1);
        //addContentView(cb,layoutParams);


        card = (CardView)findViewById(R.id.card);
        okBtn = (Button)findViewById(R.id.okBtn);
        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                card.setVisibility(View.GONE);
            }
        });

        createBtn = (Button)findViewById(R.id.createBtn);
        createBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                card.setVisibility(View.VISIBLE);
            }
        });

        cpName = (TextView)findViewById(R.id.cpName);
        timer = new Timer();   //防止异常退出
        startBtn = (Button)findViewById(R.id.startButton);
        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startBtn.setVisibility(View.GONE);
                createBtn.setVisibility(View.GONE);
                cpName.setVisibility(View.GONE);
                gameStart();
            }
        });

    }

    public void gameStart(){

        constBtn.setVisibility(View.VISIBLE);
        constBtn1.setVisibility(View.VISIBLE);
        varBtn.setVisibility(View.VISIBLE);
        varBtn1.setVisibility(View.VISIBLE);

        //timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if(isSuccess) {
                    //tip.setVisibility(View.VISIBLE);
                    //System.out.println("here");
                    cancel();
                }
                else {
                    a++;
                    y = (y++ + a) * 0.85f;
                    a1++;
                    y1 = (y1++ + a1) * 0.85f;
                    va++;
                    vy = (vy++ + va) * 0.8f;
                    va1++;
                    vy1 = (vy1++ + va1) * 0.75f;

                    constBtn.setY(y);
                    constBtn1.setY(y1);
                    varBtn.setY(vy);
                    varBtn1.setY(vy1);
                    //handler.sendEmptyMessage(0x123);
                    if (y >= screenHeight - 150) {
                        y = 0;
                        a = 0;
                        x = (int) (Math.random() * 1800) + 50;
                        constBtn.setX(x);
                        constBtn.setY(y);
                        delScore(5);
                        //String res = readFile("score.txt");
                        //tv.setText(res);    //报错:Only the original thread that created a view hierarchy can touch its views.
                    }
                    if (y1 >= screenHeight - 150) {
                        y1 = 0;
                        a1 = 0;
                        x1 = (int) (Math.random() * 1800) + 50;
                        constBtn1.setX(x1);
                        constBtn1.setY(y1);
                        delScore(5);
                        //String res = readFile("score.txt");
                        //scoreTv.setText(res);
                    }
                    if (vy >= screenHeight - 150) {
                        vy = 0;
                        va = 0;
                        vx = (int) (Math.random() * 1800) + 50;
                        varBtn.setX(vx);
                        varBtn.setY(vy);
                    }
                    if (vy1 >= screenHeight - 150) {
                        vy1 = 0;
                        va1 = 0;
                        vx1 = (int) (Math.random() * 1800) + 50;
                        varBtn1.setX(vx1);
                        varBtn1.setY(vy1);
                    }
                }
            }
        },0,20);
    }

    public void addScore(int updateSc) {
        String sc = readFile("score.txt");
        updateSc = Integer.valueOf(sc)+updateSc;
        writeFile("score.txt",String.valueOf(updateSc));
    }

    public void delScore(int updateSc){
        String sc = readFile("score.txt");
        updateSc = Integer.valueOf(sc)-updateSc;
        writeFile("score.txt",String.valueOf(updateSc));
    }

    public boolean isSuccess(){
        String res = readFile("score.txt");
        if(Integer.valueOf(res)>=100) {
            //Configuration.cp1 = 1;
            isSuccess = true;
            return true;
        }else
            return false;
    }
    public void avoidOverLap()
    {
        //int i;
        if(Math.abs(x1-x)<50){
            //i = 0;
            if(Math.abs(y1-y)<50)
                x1 = (int)(Math.random()*1800)+50;
        }
        if(Math.abs(x1-vx)<50){
            if(Math.abs(y1-vy)<50)
                x1 = (int)(Math.random()*1800)+50;
        }
        if(Math.abs(x1-vx1)<50){
            if(Math.abs(y1-vy1)<50)
                x1 = (int)(Math.random()*1800)+50;
        }
        if(Math.abs(x-vx)<50){
            if(Math.abs(y-vy)<50)
                x = (int)(Math.random()*1800)+50;
        }
        if(Math.abs(x-vx1)<50){
            if(Math.abs(y-vy1)<50)
                x = (int)(Math.random()*1800)+50;
        }
        if(Math.abs(vx-vx1)<50){
            if(Math.abs(vy-vy1)<50)
                vx = (int)(Math.random()*1800)+50;
        }
    }

    /**
    Handler handler = new Handler(){
        @SuppressLint("HandlerLeak")
        @Override
        public void handleMessage(Message msg){
            super.handleMessage(msg);
            if(msg.what == 0x123){
                cb.invalidate();
            }
        }
    };
     **/

    class changeBtn extends View{
        public changeBtn(Context context) {
            super(context);
        }

        public changeBtn(Context context, @Nullable AttributeSet attrs) {
            super(context, attrs);
        }

        public changeBtn(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
            super(context, attrs, defStyleAttr);
        }

        public void onDraw(Canvas canvas){
            super.onDraw(canvas);

            //constBtn.setX(x);
            //constBtn1.setX(x1);
            //varBtn.setX(vx);
            //varBtn1.setX(vx1);
            //constBtn.setY(y++);

        }
    }

    //再按一次返回键退出
    private long lastBackPressTime = -1L;
    public void onBackPressed() {
        long currentTIme = System.currentTimeMillis();
        if(lastBackPressTime == -1L || currentTIme - lastBackPressTime >= 2000){
            // 显示提示信息
            showBackPressTip();
            // 记录时间
            lastBackPressTime = currentTIme;
        }else{
            //退出应用
            System.out.println("exit current activity");
            timer.cancel();
            finish();
        }
    }
    private void showBackPressTip(){
        Toast.makeText(this,"再按一次退出",Toast.LENGTH_SHORT).show();
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
}
