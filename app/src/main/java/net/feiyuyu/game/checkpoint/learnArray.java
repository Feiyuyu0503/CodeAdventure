package net.feiyuyu.game.checkpoint;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import net.feiyuyu.game.R;
import net.feiyuyu.game.ui.mapViewLayout;

public class learnArray extends Activity {

    int screenHeight;         //屏幕宽度
    int screenWidth;          //屏幕高度

    Button actionBtn;
    CardView cv;
    ImageButton addLeftBtn; TextView tvLeft;
    ImageButton addUpBtn; TextView tvUp;
    ImageButton addRightBtn; TextView tvRight;
    ImageButton addDownBtn; TextView tvDown;
    Button saveBtn;
    Button execBtn;

    net.feiyuyu.game.ui.mapViewLayout mapViewLayout1;
    net.feiyuyu.game.ui.mapViewLayout mapViewLayout2;

    int leftNum,upNum,rightNum,downNum; //储存移动举例


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

        setContentView(R.layout.map_view);

        cv = (CardView)findViewById(R.id.cv);
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
                tvLeft.setText(i+"");
            }
        });

        addUpBtn = (ImageButton)findViewById(R.id.addUpBtn);
        addUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String res = String.valueOf(tvUp.getText());
                int i = Integer.valueOf(res)+1;
                tvUp.setText(i+"");
            }
        });

        addRightBtn = (ImageButton)findViewById(R.id.addRightBtn);
        addRightBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String res = String.valueOf(tvRight.getText());
                int i = Integer.valueOf(res)+1;
                tvRight.setText(i+"");
            }
        });

        addDownBtn = (ImageButton)findViewById(R.id.addDownBtn);
        addDownBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String res = String.valueOf(tvDown.getText());
                int i = Integer.valueOf(res)+1;
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
                if(leftNum>0)
                    mapViewLayout2.showArr(0,leftNum);
                if(upNum>0)
                    mapViewLayout2.showArr(1,upNum);
                if(rightNum>0)
                    mapViewLayout2.showArr(2,rightNum);
                if(downNum>0)
                    mapViewLayout2.showArr(3,downNum);
                cv.setVisibility(View.GONE);
            }
        });

        execBtn = (Button)findViewById(R.id.execBtn);
        execBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    mapViewLayout1.move("left",leftNum);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

    }
}
