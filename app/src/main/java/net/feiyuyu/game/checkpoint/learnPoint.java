package net.feiyuyu.game.checkpoint;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.bin.david.form.core.SmartTable;     //这个包名才正确
import com.bin.david.form.core.TableConfig;
import com.bin.david.form.data.style.FontStyle;

import net.feiyuyu.game.R;
import net.feiyuyu.game.pointData;

import java.util.ArrayList;
import java.util.List;

public class learnPoint extends Activity {

    SmartTable table;

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        //去除标题栏
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //去除状态栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.choose_game_view);


        setContentView(R.layout.point_view);

        table = (SmartTable)findViewById(R.id.table);

        initGame();
    }

    public void initGame(){
        List<pointData> list = new ArrayList<>();
        list.add(new pointData("cat",3));
        list.add(new pointData("dog",4));
        list.add(new pointData("apple",6));
        list.add(new pointData("watermelon",5));
        list.add(new pointData("banana",1));
        list.add(new pointData("peach",2));
        //list.add(new pointData("bird",7));

        //移除行列号
        TableConfig tableConfig = table.getConfig();
        tableConfig.setShowXSequence(false);
        tableConfig.setShowYSequence(false);

        table.setData(list);
        table.getConfig().setContentStyle(new FontStyle(80, Color.BLUE));


/*
        ImageView v = new ImageView(getApplicationContext());
        v.setImageResource(R.drawable.apple);
        Button btn = new Button(getApplicationContext());
        btn.setText("放置");
        List<pointDes> list1 = new ArrayList<>();
        list1.add(new pointDes(v,btn));
        list1.add(new pointDes(v,btn));
        list1.add(new pointDes(v,btn));
        list1.add(new pointDes(v,btn));
        list1.add(new pointDes(v,btn));
        list1.add(new pointDes(v,btn));
        list1.add(new pointDes(v,btn));
        //移除行列号
        TableConfig tableConfig1 = table1.getConfig();
        tableConfig1.setShowXSequence(false);
        tableConfig1.setShowYSequence(false);
        table1.setData(list1);
        table1.getConfig().setContentStyle(new FontStyle(80, Color.BLUE));
 */

    }

}
