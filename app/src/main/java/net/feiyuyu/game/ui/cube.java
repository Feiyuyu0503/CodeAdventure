package net.feiyuyu.game.ui;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;


import net.feiyuyu.game.R;

public class cube extends FrameLayout {

    TextView tv;
    ImageView imgV;
    int bgColor;
    boolean isSuccess = false;
    boolean flag = false; //标记鸟所在位置
    boolean isCube = false; //标记不能走的方块，true代表不能走
    public Button btn; //执行按钮

    public cube(@NonNull Context context) {
        super(context);

        tv = new TextView(getContext());
        tv.setTextSize(16);
        //tv.setBackgroundColor(Color.parseColor("974CAF50"));
        //tv.setBackgroundColor(0x33fA52ff);
        tv.setBackgroundColor(0xFF6A5ACD);
        tv.setGravity(Gravity.CENTER);
        LayoutParams lp = new LayoutParams(-1,-1);
        lp.setMargins(10,10,0,0);
        addView(tv,lp);

        imgV = new ImageView(getContext());
        //imgV.setBackgroundDrawable(R.drawable.apple);
        //imgV.setImageResource(R.drawable.apple);
        //imgV.setVisibility(GONE);
        addView(imgV,lp);
    }

    public cube(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public cube(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
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
        this.isCube = true;
        tv.setBackgroundColor(bgColor);
    }

    public String getTv(){
        return String.valueOf(tv.getText());
    }

    public int getColor(){
        return bgColor;
    }

    public void removeV(){
        imgV.setImageResource(0);
        flag = false;
    }
    public void setV(){
        setImage(R.drawable.bird);
        flag = true;
    }

    public void setBtn(){
        btn = new Button(getContext());
        btn.setText("执行");
        LayoutParams lp = new LayoutParams(-1,-1);
        lp.setMargins(0,0,0,0);
        addView(btn,lp);
    }
}
