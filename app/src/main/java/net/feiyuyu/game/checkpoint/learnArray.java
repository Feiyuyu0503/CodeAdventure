package net.feiyuyu.game.checkpoint;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;



import net.feiyuyu.game.R;
import net.feiyuyu.game.gameSelectActivity;
import net.feiyuyu.game.ui.gamePre;
import net.feiyuyu.game.ui.mapViewLayout;

import org.apache.http.util.EncodingUtils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class learnArray extends Activity {

    int screenHeight;         //屏幕宽度
    int screenWidth;          //屏幕高度

    Button actionBtn;
    CardView cv;
    CardView cv1;     //游戏结束提示
    Button retryOrNextBtn;
    TextView gameStateTv;
    TextView scoreTv;
    ImageButton addLeftBtn; TextView tvLeft;
    ImageButton addUpBtn; TextView tvUp;
    ImageButton addRightBtn; TextView tvRight;
    ImageButton addDownBtn; TextView tvDown;
    Button saveBtn;

    net.feiyuyu.game.ui.mapViewLayout mapViewLayout1;
    net.feiyuyu.game.ui.mapViewLayout mapViewLayout2;

    int leftNum,upNum,rightNum,downNum; //储存移动举例

    boolean isSuccess = false; //判断游戏是否成功
    int count = 0;


    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        //去除标题栏
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //去除状态栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        WindowManager wm = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
        screenWidth = wm.getDefaultDisplay().getWidth();
        screenHeight = wm.getDefaultDisplay().getHeight();                     //获取屏幕宽和高

        setContentView(R.layout.map_view);

        scoreTv = (TextView)findViewById(R.id.scoreTv);
        scoreTv.setText(readFile("score.txt"));

        gameStart();
    }

    public void gameStart(){
        cv = (CardView)findViewById(R.id.cv);
        cv1 = (CardView)findViewById(R.id.cv1);
        tvLeft = (TextView)findViewById(R.id.numOfLeft);
        tvUp = (TextView)findViewById(R.id.numOfUp);
        tvRight = (TextView)findViewById(R.id.numOfRight);
        tvDown = (TextView)findViewById(R.id.numOfDown);

        actionBtn = (Button)findViewById(R.id.actionBtn);
        actionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cv.setVisibility(View.VISIBLE);
            }
        });

        addLeftBtn = (ImageButton)findViewById(R.id.addLeftBtn);
        addLeftBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String res = String.valueOf(tvLeft.getText());
                int i = Integer.valueOf(res)+1;
                if(i>4){
                    tvLeft.setText("0");
                    Toast.makeText(learnArray.this, "太大啦", Toast.LENGTH_SHORT).show();
                }else
                    tvLeft.setText(i+"");
            }
        });

        addUpBtn = (ImageButton)findViewById(R.id.addUpBtn);
        addUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String res = String.valueOf(tvUp.getText());
                int i = Integer.valueOf(res)+1;
                if(i>4){
                    tvUp.setText("0");
                    Toast.makeText(learnArray.this, "太大啦", Toast.LENGTH_SHORT).show();
                }else
                    tvUp.setText(i+"");
            }
        });

        addRightBtn = (ImageButton)findViewById(R.id.addRightBtn);
        addRightBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String res = String.valueOf(tvRight.getText());
                int i = Integer.valueOf(res)+1;
                if(i>4){
                    tvRight.setText("0");
                    Toast.makeText(learnArray.this, "太大啦", Toast.LENGTH_SHORT).show();
                }else
                    tvRight.setText(i+"");
            }
        });

        addDownBtn = (ImageButton)findViewById(R.id.addDownBtn);
        addDownBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String res = String.valueOf(tvDown.getText());
                int i = Integer.valueOf(res)+1;
                if(i>4){
                    tvDown.setText("0");
                    Toast.makeText(learnArray.this, "太大啦", Toast.LENGTH_SHORT).show();
                }else
                    tvDown.setText(i+"");
            }
        });



        mapViewLayout1 = (mapViewLayout) findViewById(R.id.grid1);
        mapViewLayout1.addCubeMap(screenHeight/6+20,screenHeight/6+20);


        mapViewLayout2 = (mapViewLayout)findViewById(R.id.grid2);
        mapViewLayout2.addCubeArray(screenHeight/6,screenHeight/6);
        //mapViewLayout2.showArr(0,3);

        saveBtn = (Button)findViewById(R.id.saveBtn);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                leftNum =Integer.valueOf(String.valueOf(tvLeft.getText()));
                upNum = Integer.valueOf(String.valueOf(tvUp.getText()));
                rightNum = Integer.valueOf(String.valueOf(tvRight.getText()));
                downNum = Integer.valueOf(String.valueOf(tvDown.getText()));

                if(leftNum>0) {
                    count++;
                    mapViewLayout2.showArr(0, leftNum);
                    mapViewLayout2.cubeArr[0][leftNum].btn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mapViewLayout2.cubeArr[0][leftNum].btn.setEnabled(false);   //设置不可点击
                            int x=0,y=0;
                            int temp = mapViewLayout1.returnPosition(x,y);   //找到鸟的位置
                            x = temp/10;
                            y = temp%10;
                            try {
                                isSuccess = mapViewLayout1.move(x, y, "left", leftNum);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            count--;
                            if(count<=0) isSuccess();
                        }
                    });
                }
                if(upNum>0) {
                    count++;
                    mapViewLayout2.showArr(1, upNum);
                    mapViewLayout2.cubeArr[1][upNum].btn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mapViewLayout2.cubeArr[1][upNum].btn.setEnabled(false);   //设置不可点击
                            int x=0,y=0;
                            int temp = mapViewLayout1.returnPosition(x,y);   //找到鸟的位置
                            x = temp/10;
                            y = temp%10;
                            try {
                                isSuccess = mapViewLayout1.move(x, y, "up", upNum);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            count--;
                            if(count<=0) isSuccess();
                        }
                    });
                }
                if(rightNum>0) {
                    count++;
                    mapViewLayout2.showArr(2, rightNum);
                    mapViewLayout2.cubeArr[2][rightNum].btn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mapViewLayout2.cubeArr[2][rightNum].btn.setEnabled(false);   //设置不可点击
                            int x=0,y=0;
                            int temp = mapViewLayout1.returnPosition(x,y);   //找到鸟的位置
                            x = temp/10;
                            y = temp%10;
                            try {
                                isSuccess = mapViewLayout1.move(x, y, "right", rightNum);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            count--;
                            if(count<=0) isSuccess();

                        }
                    });
                }
                if(downNum>0) {
                    count++;
                    mapViewLayout2.showArr(3, downNum);
                    mapViewLayout2.cubeArr[3][downNum].btn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mapViewLayout2.cubeArr[3][downNum].btn.setEnabled(false);   //设置不可点击
                            int x=0,y=0;
                            int temp = mapViewLayout1.returnPosition(x,y);   //找到鸟的位置
                            x = temp/10;
                            y = temp%10;
                            try {
                                isSuccess = mapViewLayout1.move(x, y, "down", downNum);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            count--;
                            if(count<=0) isSuccess();
                        }
                    });
                }
                cv.setVisibility(View.GONE);
                actionBtn.setEnabled(false);   //设置不可点击
            }
        });

        gameStateTv = (TextView)findViewById(R.id.gameState);

        /*
        execBtn = (Button)findViewById(R.id.execBtn);
        execBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    int x=0,y=0;
                    int temp = mapViewLayout1.returnPosition(x,y);   //找到鸟的位置
                    x = temp/10;
                    y = temp%10;
                    if(leftNum>0) {
                        isSuccess = mapViewLayout1.move(x, y, "left", leftNum);
                        //Thread.currentThread().sleep(5500);
                        temp = mapViewLayout1.returnPosition(x,y);   //找到鸟的位置
                        x = temp/10;
                        y = temp%10;

                    }
                    if(upNum>0) {
                        isSuccess = mapViewLayout1.move(x, y, "up", upNum);
                        temp = mapViewLayout1.returnPosition(x,y);   //找到鸟的位置
                        x = temp/10;
                        y = temp%10;
                    }
                    if(rightNum>0) {
                        isSuccess = mapViewLayout1.move(x, y, "right", rightNum);
                        //Thread.currentThread().sleep(5500);
                        //mapViewLayout1.postInvalidate();
                        System.out.println("hhhhhhhhere");
                        //Thread.sleep(5000);
                        temp = mapViewLayout1.returnPosition(x,y);   //找到鸟的位置
                        x = temp/10;
                        y = temp%10;
                    }

                    //mapViewLayout1.invalidate();
                    //mapViewLayout1.postInvalidate();
                    //Looper.loop();
                    //Thread.sleep(10000);
                    if(downNum>0) {
                        mapViewLayout1.postInvalidate();
                        isSuccess = mapViewLayout1.move(x, y, "down", downNum);
                        System.out.println("kkkkkkkkere");
                        //Thread.currentThread().sleep(5500);
                        temp = mapViewLayout1.returnPosition(x,y);   //找到鸟的位置
                        x = temp/10;
                        y = temp%10;
                    }

                    if(isSuccess) {
                        //Toast.makeText(learnArray.this, "游戏成功！", Toast.LENGTH_SHORT).show();
                        cv1.setVisibility(View.VISIBLE);
                        int i = Integer.valueOf(String.valueOf(scoreTv.getText()))+50;
                        writeFile("score.txt",i+"");
                        scoreTv.setText(i+"");
                    }
                    else {
                        //Toast.makeText(learnArray.this, "游戏失败！", Toast.LENGTH_SHORT).show();
                        Thread.sleep(2000);
                        gameStateTv.setText("游戏失败！");
                        retryOrNextBtn.setText("重玩");
                        cv1.setVisibility(View.VISIBLE);

                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

         */

        retryOrNextBtn = (Button)findViewById(R.id.retryOrNextBtn);
        retryOrNextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(String.valueOf(retryOrNextBtn.getText())=="重玩"){
                    finish();
                    startActivity(getIntent());    //重启当前activity
                }else{
                    Intent intent = new Intent(learnArray.this, gamePre.class);
                    intent.putExtra("key",2);
                    startActivity(intent);
                    finish();
                }
            }
        });

    }

    public void isSuccess(){
        if(isSuccess) {
            //Toast.makeText(learnArray.this, "游戏成功！", Toast.LENGTH_SHORT).show();
            cv1.setVisibility(View.VISIBLE);
            int i = Integer.valueOf(String.valueOf(scoreTv.getText()))+50;
            writeFile("score.txt",i+"");
            scoreTv.setText(i+"");
        }
        else {
            //Toast.makeText(learnArray.this, "游戏失败！", Toast.LENGTH_SHORT).show();
            //Thread.sleep(2000);
            gameStateTv.setText("游戏失败！");
            retryOrNextBtn.setText("重玩");
            cv1.setVisibility(View.VISIBLE);
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
