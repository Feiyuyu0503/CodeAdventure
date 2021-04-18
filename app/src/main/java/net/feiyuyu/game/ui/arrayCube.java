package net.feiyuyu.game.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class arrayCube extends FrameLayout {

    TextView tv;
    public arrayCube(Context context) {
        super(context);

        tv = new TextView(getContext());
        tv.setTextSize(16);
        tv.setText("向左");
        //tv.setBackgroundColor(Color.parseColor("974CAF50"));
        //tv.setBackgroundColor(0x33fA52ff);
        tv.setGravity(Gravity.CENTER);
        LayoutParams lp = new LayoutParams(-1,-1);
        lp.setMargins(10,10,0,0);
        addView(tv,lp);
    }

    public arrayCube(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public arrayCube(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setTextForArrayCube(String str){
        tv.setText(str);
    }
}
