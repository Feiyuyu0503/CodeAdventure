package net.feiyuyu.game.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridLayout;

import androidx.cardview.widget.CardView;

import net.feiyuyu.game.R;

public class arrayViewLayout extends GridLayout {

    arrayCube[][] arrCube = new arrayCube[5][5];

    public arrayViewLayout(Context context) {
        super(context);
        initView();
    }

    public arrayViewLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public arrayViewLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    public void initView(){
        setColumnCount(5);      //指明GridLayout为5列
    }

    public void onSizeChanged(int w,int h, int oldw,int oldh){
        super.onSizeChanged(w,h,oldw,oldh);
        int cubeH = (Math.min(w,h)-10)/5;

        System.out.println("here: "+cubeH);
        addCube(cubeH,cubeH);
    }

    public void addCube(int cubeWidth,int cubeHeight){
        /*
        testCube c = new testCube(getContext());

        for(int x = 0;x<25;x++){
            c = new testCube((getContext()));
            //c.setImage(R.drawable.apple);
            addView(c,cubeWidth,cubeHeight);
        }
        c.setImage(R.drawable.apple);
        */
        arrayCube c = new arrayCube(getContext());
        for(int x = 0;x<5;x++)
            for(int y=0;y<5;y++)
            {
                c = new arrayCube(getContext());
                c.setTextForArrayCube("向左");
                addView(c,cubeWidth,cubeHeight);
                arrCube[x][y] = c;
            }
    }
    }