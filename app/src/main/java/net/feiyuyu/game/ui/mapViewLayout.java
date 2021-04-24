package net.feiyuyu.game.ui;

import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;
import android.widget.CursorAdapter;
import android.widget.GridLayout;

import net.feiyuyu.game.R;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class mapViewLayout extends GridLayout {

    public cube[][] cubeMap = new cube[5][5];
    public cube[][] cubeArr = new cube[4][5];


    public mapViewLayout(Context context) {
        super(context);
        initView();
    }

    public mapViewLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public mapViewLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    public void initView(){
        setColumnCount(5);      //指明GridLayout为5列
        //addCube(100,100);
    }

    public void onSizeChanged(int w,int h, int oldw,int oldh){
        super.onSizeChanged(w,h,oldw,oldh);
        int cubeH = (Math.min(w,h)-10)/5;
        //addCubeMap(cubeH,cubeH);           //迷惑
    }


    public void addCubeMap(int cubeWidth,int cubeHeight){
        cube c = new cube(getContext());
        for(int y = 0;y<5;y++)
        for(int x = 0;x<5;x++){
            c = new cube(getContext());
            if(x == 0&&y == 0)
                c.setV();
            if(y>0&&x!=4)
                //c.setColor(0xFF6A5ACD);
                c.setColor(Color.GRAY);
            addView(c,cubeWidth,cubeHeight);
            cubeMap[y][x] = c;
        }
        c.isSuccess = true;
        c.setImage(R.drawable.apple);
       //cubeMap[0].removeV();
    }

    public void testaddCubeMapPro(int cubeWidth,int cubeHeight){
        cube c;
        for(int x=0;x<5;x++)
            for(int y=0;y<5;y++){
                c = new cube(getContext());
                if(x==0||(x<3&&y<2))
                    c.setColor(0x33fA52ff);
                if((x>1&&y==3)||(x==4&&y<4))
                    c.setColor(0x33fA52ff);
                if(x==3&&y==0)
                    c.setV();
                if(x==4&&y==4) {
                    c.setImage(R.drawable.apple);
                    c.isSuccess = true;
                }
                //cubeMap[x][y] = c;
                addView(c,cubeWidth,cubeHeight);
                cubeMap[x][y] = c;
            }
        //test
        cubeMap[0][1].setColor(0xFF6A5ACD);
        cubeMap[1][1].setColor(0xFF6A5ACD);
        cubeMap[2][1].setColor(0xFF6A5ACD);
        cubeMap[0][2].setColor(0xFF6A5ACD);
    }

    public void addCubeMapPro(int cubeWidth,int cubeHeight) {
        cube c;
        for(int x=0;x<5;x++)
            for(int y=0;y<5;y++){
                c = new cube(getContext());
                if(x==1&&(y==1||y==3))
                    c.setColor(Color.GRAY);
                if(x==3&&y==2)
                    c.setColor(Color.GRAY);
                if(x==4&&y==3)
                    c.setColor(Color.GRAY);
                if(x==4&&y==2)
                    c.setV();
                if(x==4&&y==4) {
                    c.setImage(R.drawable.apple);
                    c.isSuccess = true;
                }

                addView(c,cubeWidth,cubeHeight);
                cubeMap[x][y] = c;
            }

    }


    public void addCubeArray(int cubeWidth,int cubeHeight){
        cube c;
        for(int x = 0;x<5;x++){
            c = new cube(getContext());
            c.setTv("向左");
            c.setVisibility(GONE);
            cubeArr[0][x] = c;
            addView(c,cubeWidth,cubeHeight);
        }
        for(int x = 0;x<5;x++){
            c = new cube(getContext());
            c.setTv("向上");
            c.setVisibility(GONE);
            cubeArr[1][x] = c;
            addView(c,cubeWidth,cubeHeight);
        }
        for(int x = 0;x<5;x++){
            c = new cube(getContext());
            c.setTv("向右");
            c.setVisibility(GONE);
            cubeArr[2][x] = c;
            addView(c,cubeWidth,cubeHeight);
        }
        for(int x = 0;x<5;x++){
            c = new cube(getContext());
            c.setTv("向下");
            c.setVisibility(GONE);
            cubeArr[3][x] = c;
            addView(c,cubeWidth,cubeHeight);
        }
    }

    public void showArr(int x,int num){
        for(int y = 0;y<num;y++)
            cubeArr[x][y].setVisibility(VISIBLE);
        cubeArr[x][num].setBtn();
        cubeArr[x][num].setVisibility(VISIBLE);
    }

    //返回鸟所在坐标
    public int returnPosition(int x,int y){
        for(int i=0;i<5;i++)
            for(int j=0;j<5;j++){
                if(cubeMap[i][j].flag == true){
                    x = i;
                    y = j;
                    return x*10+y;
                }
            }
        return 1000;
    }

    public boolean move(int x,int y,String direction,int num) throws InterruptedException {

        boolean isOver = false;    //游戏是否成功

        //暂无动画效果，直接显示结果
        switch (direction){
            case "right":
                for(int i = 0;i<num;i++) {

                    if(y+1<5&&!cubeMap[x][y+1].isCube) {
                        cubeMap[x][y].removeV();
                        //Thread.currentThread().sleep(1000);
                        //Thread.sleep(500);
                        cubeMap[x][++y].setV();
                        //Thread.currentThread().sleep(1000);
                        //Thread.sleep(500);
                    }else
                        break;
                    //Handler mHandler = new Handler();
                    //Looper.loop();
                    //invalidate();

                   // Timer timer = new Timer();
                   // timer.schedule(new TimerTask() {
                   //     @Override
                   //     public void run() {
                   //             mHandler.sendEmptyMessage(123);
                   //     }
                   // },0,1500);

                    //cubeMap[x][y].removeV();
                    //cubeMap[x][y].invalidate();
                    //cubeMap[x][++y].setV();
                    //cubeMap[x][y].invalidate();
                  // long time1=System.currentTimeMillis();
                  // System.out.println(time1);
                  // while (true){
                  //     long time2 = System.currentTimeMillis();
                  //     System.out.println(time2-time1);
                  //     if(time2-time1>2500)
                  //         break;
                  // }
                    //mHandler.getLooper().quit();

                }
                //isOver = cubeMap[x][y].isSuccess;
                break;

            case "up":
                for(int i = 0;i<num;i++){
                    if(x-1>=0&&!cubeMap[x-1][y].isCube) {
                        cubeMap[x][y].removeV();
                        cubeMap[--x][y].setV();
                    }else
                        break;
                }
                break;
            case "left":
                for(int i = 0;i<num;i++){
                    if(y-1>=0&&!cubeMap[x][y-1].isCube) {
                        cubeMap[x][y].removeV();
                        cubeMap[x][--y].setV();
                    }else
                        break;
                }
                break;
            case "down":
                for(int i = 0;i<num;i++){
                    if(x+1<5&&!cubeMap[x+1][y].isCube) {
                        cubeMap[x][y].removeV();
                        cubeMap[++x][y].setV();
                    }else
                        break;
                }

                break;
            default: break;

        }
        isOver = cubeMap[x][y].isSuccess;
        return isOver;
    }

}
