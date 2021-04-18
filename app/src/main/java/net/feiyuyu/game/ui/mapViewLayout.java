package net.feiyuyu.game.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.CursorAdapter;
import android.widget.GridLayout;

import net.feiyuyu.game.R;

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
                c.setColor(0xFF6A5ACD);
            addView(c,cubeWidth,cubeHeight);
            cubeMap[y][x] = c;
        }
        c.setImage(R.drawable.apple);
       //cubeMap[0].removeV();
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
    }

    public boolean move(String direction,int num) throws InterruptedException {

        switch (direction){
            case "left":
                for(int i = 0;i<num;) {
                    cubeMap[0][i].removeV();
                    Thread.currentThread().sleep(1000);
                    cubeMap[0][++i].setImage(R.drawable.bird);
                    Thread.currentThread().sleep(3000);
                }
                break;
            default: break;

        }
        return true;
    }
}
