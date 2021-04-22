package net.feiyuyu.game.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageButton;

public class myImageButton extends ImageButton {

    int seq;
    String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSeq() {
        return seq;
    }

    public void setSeq(int seq) {
        this.seq = seq;
    }

    public myImageButton(Context context) {
        super(context);
    }

    public myImageButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public myImageButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

}
