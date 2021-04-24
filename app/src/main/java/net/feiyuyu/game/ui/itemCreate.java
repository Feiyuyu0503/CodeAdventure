package net.feiyuyu.game.ui;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;


import net.feiyuyu.game.R;

public class itemCreate extends LinearLayout{
    public itemCreate(Context context) {
        super(context);
        initView();
    }

    public itemCreate(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public itemCreate(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    //Button button = new Button(getContext());
    Button button = (Button)findViewById(R.id.createBtn);

    public void initView(){
        myGameView myGameView = new myGameView(getContext());
        addView(myGameView);
        //this.addView();
    }
}
