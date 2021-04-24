package net.feiyuyu.game.checkpoint;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;


import net.feiyuyu.game.R;
import net.feiyuyu.game.gameSelectActivity;
import net.feiyuyu.game.settingActivity;
import net.feiyuyu.game.ui.itemCreate;
import net.feiyuyu.game.ui.myGameView;

import org.apache.http.util.EncodingUtils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Time;
import java.util.Timer;
import java.util.TimerTask;

public class learnConst extends Activity {
    private myObj obj;
    private int xobj = 60;      //对象x坐标
    private int yobj = 500;      //对象y坐标

    int screenHeight;         //屏幕宽度
    int screenWidth;          //屏幕高度

    boolean isEnd = false;    //判断游戏是否结束
    boolean isSuccess = false; //判断是否通过
    boolean isConflict = false; //是否撞
    boolean isCorrect = false;
    View cardView;
    View cardView1;
    TextView tv;

    RadioButton constVar;    //常量按钮
    RadioButton var;         //变量按钮

    //SharedPreferences score;
    TextView tvScore;      //积分显示
    String gameScore;

    //learnConst类使用game_view.xml作为View界面，而View界面中使用了myGameView这个自定义View类

    public void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);

        //去除标题栏
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //去除状态栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.choose_game_view);

        setContentView(R.layout.game_view);

        WindowManager wm = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
        screenWidth = wm.getDefaultDisplay().getWidth();
        screenHeight = wm.getDefaultDisplay().getHeight();                     //获取屏幕宽和高
        System.out.println("screen"+screenWidth+" "+screenHeight);

        //myGameView game = new myGameView(this);
        //setContentView(game);
        cardView = (View)findViewById(R.id.card);
        cardView1 = (View)findViewById(R.id.card1);
        tv  = (TextView)findViewById(R.id.gameState);
        tvScore = (TextView)findViewById(R.id.score);
        constVar = (RadioButton)findViewById(R.id.constVar);
        var = (RadioButton)findViewById(R.id.var);

        initGame();

    }

    public void initGame(){
        //初始化隐藏cardView
        cardView.setVisibility(View.GONE);
        cardView1.setVisibility(View.GONE);
        //setInVisibility(cardView);

        //score = this.getPreferences(MODE_PRIVATE);
        //String res = score.getString(gameScore,null);
        String res = String.valueOf(readFile("score.txt"));
        if(res != null){
            tvScore.setText(res);
        }

        //"创建"按钮
        Button createBtn = (Button)findViewById(R.id.createBtn);
        createBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cardView.setVisibility(View.VISIBLE);
            }
        });

        //"确认"按钮
        Button okBtn = (Button)findViewById(R.id.okBtn);
        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isCorrect) {
                    cardView.setVisibility(View.GONE);
                    gameStart();
                }
                else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(learnConst.this);
                    builder.setTitle("提示");
                    builder.setMessage("含const关键词的才是常量");
                    builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(learnConst.this, "请重新选择", Toast.LENGTH_SHORT).show();
                        }
                    });
                    //builder.setNeutralButton("取消", null);
                    builder.show();
                }
                //LinearLayout.LayoutParams layoutParams = null;
                //myObj obj = new myObj(learnConst.this);
                //addContentView(obj,layoutParams);
            }
        });

        //"重来"按钮
        Button retry = (Button)findViewById(R.id.retry);
        retry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(getIntent());    //重启当前activity
            }
        });

        //常量按钮
        constVar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isCorrect = true;
            }
        });

        //变量按钮,保持isCorrect == false;
        var.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isCorrect = false;
            }
        });

    }


    public void gameStart(){
        obj = new myObj(learnConst.this);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-1,-1);
        addContentView(obj,layoutParams);

        //handler.sendEmptyMessage(0x123);
        myGameView v = (myGameView)findViewById(R.id.myGameView);
        v.setOnTouchListener(touch);
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if(yobj>=screenHeight-180||yobj<=0) {
                    isEnd = true;
                    isConflict = true;      //撞而失败
                    handler.sendEmptyMessage(0x123);
                    //System.out.println("game_end");
                }
                else if(xobj>screenWidth-480&&yobj<screenHeight/2){
                    isEnd = true;
                    isSuccess = true;     //成功！
                    handler.sendEmptyMessage(0x123);
                }
                else if((xobj>screenWidth-480&&yobj>=screenHeight/2)){
                    isEnd = true;          //选择错误而失败
                    handler.sendEmptyMessage(0x123);
                }
                else{
                    xobj += 3;
                    yobj += 3;
                    handler.sendEmptyMessage(0x123); //通知重绘小球
                }
            }
        },0,15);
    }

    View.OnTouchListener touch = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getAction()){
                case MotionEvent.ACTION_DOWN:
                    if(yobj>=screenHeight-180||yobj<=0)
                    {
                        isEnd = true;
                        isConflict = true;
                        handler.sendEmptyMessage(0x123);
                        break;
                    }else if(xobj>=screenWidth-480&&yobj<screenHeight/2){
                        isEnd = true;
                        isSuccess = true;
                        handler.sendEmptyMessage(0x123);
                        break;
                    }
                    else if(xobj>screenWidth-480&&yobj>=screenHeight/2){
                        isEnd = true;
                        handler.sendEmptyMessage(0x123);
                    }
                    else{
                        yobj -= 50;
                        handler.sendEmptyMessage(0x123);
                        break;
                    }
            }
            return true;
        }
    };

     Handler handler = new Handler(){
        @SuppressLint("HandlerLeak")
        @Override
        public void handleMessage(Message msg){
                super.handleMessage(msg);
                if(msg.what == 0x123){
                    obj.invalidate();
                }
        }
    };

    //内部类中设置小球
    class myObj extends View{

        public myObj(Context context) {
            super(context);
        }

        public myObj(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
            super(context, attrs, defStyleAttr);
        }

        Paint paint = new Paint();
        public void onDraw(Canvas canvas) {
            //paint画笔的属性必须在onDraw中设定？
            paint.setStyle(Paint.Style.FILL);
            paint.setAntiAlias(true);
            paint.setColor(Color.RED);
            paint.setTextSize(200);

            Bitmap birdPic = BitmapFactory.decodeResource(this.getResources(), R.drawable.bird);
            int width = birdPic.getWidth();
            int height = birdPic.getHeight();
            Matrix matrix=new Matrix();
            matrix.postScale(0.35f, 0.35f);//获取缩放比例
            Bitmap birdbmp = Bitmap.createBitmap(birdPic,0,0,
                    width,height,matrix,true);//根据缩放比例获取新的位图

            Bitmap varPic = BitmapFactory.decodeResource(this.getResources(), R.drawable.var);
            int vwidth = varPic.getWidth();
            int vheight = varPic.getHeight();
            matrix.postScale(0.4f, 0.4f);//获取缩放比例
            Bitmap varbmp = Bitmap.createBitmap(varPic,0,0,
                    vwidth,vheight,matrix,true);//根据缩放比例获取新的位图


            if(!isEnd) {
                //canvas.drawCircle(xobj+185, yobj+90, 35, paint);
                canvas.drawBitmap(varbmp, xobj+160, yobj+65, paint);
                canvas.drawBitmap(birdbmp, xobj, yobj, paint);
            }
            else if(isEnd&&isConflict&&!isSuccess){
                //canvas.drawText("游戏结束", screenWidth / 2, screenHeight / 2, paint);
                tv.setText("游戏失败！");
                cardView1.setVisibility(View.VISIBLE);
                cardView1.setVisibility(View.VISIBLE);
            }
            else if(isEnd&&!isConflict&&!isSuccess){
                cardView1.setVisibility(View.VISIBLE);
            }
            else{
                tv.setText("游戏成功" +
                           "\n输出:6");
                cardView1.setVisibility(View.VISIBLE);
                //int i = Integer.valueOf(String.valueOf(tvScore.getText())).intValue();
                int j = Integer.valueOf(String.valueOf(readFile("score.txt")));
                if(j<10) {
                    /**              //废弃功能
                    SharedPreferences.Editor editor = score.edit();
                    int temp = Integer.valueOf(String.valueOf(tvScore.getText())).intValue() + 10;
                    editor.putString(gameScore, temp + "");
                    editor.commit();
                     **/
                    //测试积分文件存取
                    //settingActivity changeScore = new settingActivity();
                    //changeScore.writeFile("score.txt","10");
                    writeFile("score.txt","10");
                }
            }
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