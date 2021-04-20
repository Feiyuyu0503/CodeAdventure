package net.feiyuyu.game;

import android.widget.Button;
import android.widget.ImageView;

import com.bin.david.form.annotation.SmartColumn;
import com.bin.david.form.annotation.SmartTable;

@SmartTable
public class pointDes {
    public pointDes(ImageView v, Button btn){
        this.v = v;
        btn.setText("放置");
        this.btn = btn;
        //this.btn.setText("放置");
    }


@SmartColumn(id=0)
private ImageView v;
@SmartColumn(id=1)
private Button btn;
}