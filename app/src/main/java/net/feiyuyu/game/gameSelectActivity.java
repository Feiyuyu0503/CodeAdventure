package net.feiyuyu.game;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import net.feiyuyu.game.checkpoint.chooseConst;
import net.feiyuyu.game.checkpoint.learnConst;
import net.feiyuyu.game.checkpoint.learnVar;

public class gameSelectActivity extends Activity {

    Button btn1;
    Button btn2;
    Button btn3;
    Button btn4;

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.game_select);

        btn1 = (Button) findViewById(R.id.game0);
        btn2 = (Button) findViewById(R.id.game1);

        //System.out.println("here is selecting: "+Configuration.cp1);
        //test
        //btn1.setText(Configuration.cp1+"");
        //btn2.setText(Configuration.cp2+"");

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(gameSelectActivity.this, chooseConst.class);
                startActivity(intent);
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(gameSelectActivity.this, learnConst.class);
                startActivity(intent);
            }
        });
    }
}
