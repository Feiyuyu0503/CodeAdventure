package net.feiyuyu.game.ui;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import net.feiyuyu.game.R;

public class testCube extends FrameLayout {

    TextView tv;
    int num;
    Button btn;

    ImageView imgV;
    int bgColor;

    public testCube(@NonNull Context context) {
        super(context);

        tv = new TextView(getContext());
        tv.setTextSize(32);
        //tv.setBackgroundColor(Color.parseColor("974CAF50"));
        tv.setText("hello");
        tv.setBackgroundColor(0x33fA52ff);
        tv.setGravity(Gravity.CENTER);

        LayoutParams lp = new LayoutParams(-1,-1);
        lp.setMargins(10,10,0,0);
        addView(tv,lp);

        imgV = new ImageView(getContext());
        //imgV.setBackgroundDrawable(R.drawable.apple);
        //imgV.setImageResource(R.drawable.apple);
        //imgV.setVisibility(GONE);
        addView(imgV,lp);

        //setNum(2);
    }

    public testCube(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public testCube(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setNum(int num){
        this.num = num;
        //setBackgroundColor(Color.parseColor("974CAF50"));
        //tv.setText(num+"");
    }

    public void setImage(int i){
        //imgV.setVisibility(VISIBLE);
        imgV.setImageResource(i);
    }
    public void setTv(String str){
        tv.setText(str);
    }

    public void setColor(int i){
        this.bgColor = i;
        tv.setBackgroundColor(bgColor);
    }
    public int getColor(){
        return bgColor;
    }
}
