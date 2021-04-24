package net.feiyuyu.game.checkpoint;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
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
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;


import net.feiyuyu.game.R;
import net.feiyuyu.game.ui.myGameView;

import org.apache.http.util.EncodingUtils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class  learnVar extends Activity {

    View cardView;
    View cardView1;

    TextView tvScore; //积分显示

    Button taskBtn;
    TextView taskContent; //任务要求内容"声明一个变量"

    Boolean isCorrect = false; //任务答案选择
    RadioButton choice1,choice2; //任务选项
    Button okBtn; //确认选项按钮

    boolean isEnd = false;    //判断游戏是否结束
    boolean isSuccess = false; //判断是否通过
    boolean isConflict = false; //是否撞

    int screenHeight;         //屏幕宽度
    int screenWidth;          //屏幕高度

    myObj obj;                //声明对象
    private int xobj = 60;      //对象x坐标
    private int yobj = 500;      //对象y坐标
    int height1, height2;      //柱子高度

    TextView tv;//游戏结果提示

    boolean randomVar = false;    //随机变量或常量,默认false代表常量关卡

    Timer timer;

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        //去除标题栏
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //去除状态栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.game_view);

        WindowManager wm = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
        screenWidth = wm.getDefaultDisplay().getWidth();
        screenHeight = wm.getDefaultDisplay().getHeight();                     //获取屏幕宽和高

        //游戏结果提示
        tv  = (TextView)findViewById(R.id.gameState);
        if((Math.random()*2)>1) {
            randomVar = true; //变量关卡
            //飞去常量代码块
            tv.setText("飞错啦！");
        }
        //System.out.println("hello: "+randomVar);
        initNewGame( randomVar );
    }

    //废弃
    /**
    public void initGame(boolean randomVar){
        //隐藏任务要求显示框，并重新设置任务内容
        cardView = (View)findViewById(R.id.card);
        cardView1 = (View)findViewById(R.id.card1);
        cardView.setVisibility(View.GONE);
        cardView1.setVisibility(View.GONE);
        taskContent = (TextView)findViewById(R.id.taskContent);
        taskContent.setText("声明一个变量");

        //设置积分
        tvScore = (TextView)findViewById(R.id.score);
        String res = String.valueOf(readFile("score.txt"));
        tvScore.setText(res);

        //游戏结果提示
        tv  = (TextView)findViewById(R.id.gameState);

        //设置任务要求按钮
        taskBtn = (Button)findViewById(R.id.createBtn);
        taskBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cardView.setVisibility(View.VISIBLE);
            }
        });

        //任务选项按钮事件
        choice1 = (RadioButton) findViewById(R.id.constVar);
        choice2 = (RadioButton) findViewById(R.id.var);
        choice1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isCorrect = false;
            }
        });
        choice2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isCorrect = true;
            }
        });

        //确认选项按钮
        okBtn = (Button)findViewById(R.id.okBtn);
        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isCorrect){
                    cardView.setVisibility(View.GONE);
                    gameStart(randomVar);
                }else{
                    AlertDialog.Builder builder = new AlertDialog.Builder(learnVar.this);
                    builder.setTitle("提示");
                    builder.setMessage("再好好想一想吧~");
                    builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(learnVar.this, "请重新选择", Toast.LENGTH_SHORT).show();
                        }
                    });
                    //builder.setNeutralButton("取消", null);
                    builder.show();
                }
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


    }
     **/

    public void initNewGame(boolean randomVar){
        //隐藏任务要求显示框，并重新设置任务内容
        cardView = (View)findViewById(R.id.card);
        cardView1 = (View)findViewById(R.id.card1);
        cardView.setVisibility(View.GONE);
        cardView1.setVisibility(View.GONE);
        taskContent = (TextView)findViewById(R.id.taskContent);
        taskContent.setText("声明一个变量");

        //设置积分
        tvScore = (TextView)findViewById(R.id.score);
        String res = String.valueOf(readFile("score.txt"));
        tvScore.setText(res);

        //游戏结果提示
        //tv  = (TextView)findViewById(R.id.gameState);

        //设置任务要求按钮
        taskBtn = (Button)findViewById(R.id.createBtn);


        //"重来"按钮
        Button retry = (Button)findViewById(R.id.retry);
        retry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(getIntent());    //重启当前activity
            }
        });

        gameStart(randomVar);
    }

    //选项正确，开始游戏
    public void gameStart(boolean randomVar){
        obj = new myObj(learnVar.this,randomVar);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-1,-1);
        addContentView(obj,layoutParams);     //在游戏view中添加移动对象

        myGameView v = (myGameView)findViewById(R.id.myGameView);
        v.setOnTouchListener(touch);
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if(!randomVar){
                if (yobj >= screenHeight - 180 || yobj <= 0 || (xobj >= 500 && xobj <= 780 && yobj <= height1 + 50) || (xobj >= 1100 && xobj <= 1230 && yobj >= height2 + 550 - 20)) {
                    isEnd = true;
                    isConflict = true;      //撞而失败
                    handler.sendEmptyMessage(0x123);
                    //System.out.println("game_end");
                } else if (xobj > screenWidth - 480 && yobj < screenHeight / 2) {
                    isEnd = true;
                    isSuccess = true;     //成功！
                    handler.sendEmptyMessage(0x123);
                } else if ((xobj > screenWidth - 480 && yobj >= screenHeight / 2)) {
                    isEnd = true;          //选择错误而失败
                    handler.sendEmptyMessage(0x123);
                } else {
                    xobj += 3;
                    yobj += 3;
                    handler.sendEmptyMessage(0x123); //通知重绘小球
                }
            }
                else{
                    if (yobj >= screenHeight - 180 || yobj <= 0 || (xobj >= 500 && xobj <= 780 && yobj <= height1 + 50) || (xobj >= 1100 && xobj <= 1230 && yobj >= height2 + 550 - 20)) {
                        isEnd = true;
                        isConflict = true;      //撞而失败
                        handler.sendEmptyMessage(0x123);
                        //System.out.println("game_end");
                    } else if (xobj > screenWidth - 480 && yobj < screenHeight / 2) {
                        isEnd = true;           //选择错误而失败
                        //isSuccess = true;     //成功！
                        handler.sendEmptyMessage(0x123);
                    } else if ((xobj > screenWidth - 480 && yobj >= screenHeight / 2)) {
                        isEnd = true;
                        isSuccess = true;     //成功！
                        handler.sendEmptyMessage(0x123);
                    } else {
                        xobj += 3;
                        yobj += 3;
                        handler.sendEmptyMessage(0x123); //通知重绘小球
                    }
                }
            }
        },0,15);

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
              height1 = getRandomH();
              height2 = getRandomH();
            }
        },0,220);
    }

    public int getRandomH() {
        final double d = Math.random(); //返回的是0(包含)到1(不包含)之间的double值
        final int i = (int) (d * 350);
        return i;
    }

    //设置触屏响应
    View.OnTouchListener touch = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getAction()){
                case MotionEvent.ACTION_DOWN:
                    if(yobj>=screenHeight-180||yobj<=0||(xobj>=500&&xobj<=780&&yobj<=height1+50)||(xobj>=1100&&xobj<=1230&&yobj>=height2+550-20))
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

    //通知重绘
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



    //内部类中设置移动对象
    class myObj extends View {

        private boolean random;

        public myObj(Context context,boolean random) {
            super(context);
            this.random = random;
        }

        public myObj(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
            super(context, attrs, defStyleAttr);
        }

        Paint paint = new Paint();

        public void onDraw(Canvas canvas) {
            //paint画笔的属性必须在onDraw中设定？
            paint.setStyle(Paint.Style.FILL);
            paint.setAntiAlias(true);
            paint.setColor(Color.GRAY);
            paint.setTextSize(200);

            //paint.setColor(Color.parseColor("#974CAF50"));

            //height1 = getRandomH();
            //height2 = getRandomH();
            //System.out.println("hello" + height1 + " " + height2);
            canvas.drawRect(700, 0, 760, height1 + 150, paint);
            canvas.drawRect(1200, height2 + 550, 1260, 1080, paint);

            Bitmap birdPic = BitmapFactory.decodeResource(this.getResources(), R.drawable.bird);
            int width = birdPic.getWidth();
            int height = birdPic.getHeight();
            Matrix matrix = new Matrix();
            matrix.postScale(0.35f, 0.35f);//获取缩放比例
            Bitmap birdbmp = Bitmap.createBitmap(birdPic, 0, 0,
                    width, height, matrix, true);//根据缩放比例获取新的位图

            //代表变量
            Bitmap varPic = BitmapFactory.decodeResource(this.getResources(), R.drawable.cube);
            int vwidth = varPic.getWidth();
            int vheight = varPic.getHeight();
            matrix.postScale(0.4f, 0.4f);//获取缩放比例
            Bitmap varbmp = Bitmap.createBitmap(varPic, 0, 0,
                    vwidth, vheight, matrix, true);//根据缩放比例获取新的位图

            //代表常量
            Bitmap ConstvarPic = BitmapFactory.decodeResource(this.getResources(), R.drawable.apple);
            vwidth = ConstvarPic.getWidth();
            vheight = ConstvarPic.getHeight();
            matrix.postScale(1f, 1f);//获取缩放比例
            Bitmap Constvarbmp = Bitmap.createBitmap(ConstvarPic, 0, 0,
                    vwidth, vheight, matrix, true);//根据缩放比例获取新的位图


            if (!isEnd) {
                //canvas.drawCircle(xobj+185, yobj+90, 35, paint);
                if(random)
                canvas.drawBitmap(varbmp, xobj + 160, yobj + 65, paint);
                else canvas.drawBitmap(Constvarbmp, xobj + 160, yobj + 55, paint);
                canvas.drawBitmap(birdbmp, xobj, yobj, paint);
            } else if (isEnd && isConflict && !isSuccess) {
                //canvas.drawText("游戏结束", screenWidth / 2, screenHeight / 2, paint);
                tv.setText("游戏失败！");             //撞而失败
                cardView1.setVisibility(View.VISIBLE);
            } else if (isEnd && !isConflict && !isSuccess) {
                cardView1.setVisibility(View.VISIBLE);     //选择错误而失败
            } else {
                tv.setText("游戏成功" +
                    "\n输出:6");
                cardView1.setVisibility(View.VISIBLE);
                //int i = Integer.valueOf(String.valueOf(tvScore.getText())).intValue();
                int j = Integer.valueOf(String.valueOf(readFile("score.txt")));
                if (j < 0) {
                    /**              //废弃功能
                     SharedPreferences.Editor editor = score.edit();
                     int temp = Integer.valueOf(String.valueOf(tvScore.getText())).intValue() + 10;
                     editor.putString(gameScore, temp + "");
                     editor.commit();
                     **/
                    //测试积分文件存取
                    //settingActivity changeScore = new settingActivity();
                    //changeScore.writeFile("score.txt","10");
                    writeFile("score.txt", "30");
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
