package net.feiyuyu.game.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import net.feiyuyu.game.R;

public class tableLayout extends FrameLayout {

    ImageView v;
    public tableLayout(Context context) {
        super(context);
        initView();
    }

    public tableLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public void initView(){

        Button btn = new Button(getContext());
        btn.setText("选择");
        LayoutParams lp = new LayoutParams(-1,-1);
        lp.setMargins(0,0,0,0);
        addView(btn,lp);
        //v.setImageResource(R.drawable.bird);
        //addView(v,lp);
    }

    public void addImage(int i ){
        ImageView v = new ImageView(getContext());
        v.setImageResource(i);
        LayoutParams lp = new LayoutParams(-1,-1);
        lp.setMargins(0,0,0,0);
        addView(v,lp);
    }
}
