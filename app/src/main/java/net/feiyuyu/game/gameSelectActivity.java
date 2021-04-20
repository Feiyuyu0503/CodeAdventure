package net.feiyuyu.game;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import net.feiyuyu.game.checkpoint.chooseConst;
import net.feiyuyu.game.checkpoint.chooseVar;
import net.feiyuyu.game.checkpoint.learnArray;
import net.feiyuyu.game.checkpoint.learnArrayPro;
import net.feiyuyu.game.checkpoint.learnConst;
import net.feiyuyu.game.checkpoint.learnPoint;
import net.feiyuyu.game.checkpoint.learnVar;

public class gameSelectActivity extends Activity {

    Button btn1;
    Button btn2;
    Button btn3;
    Button btn4;
    Button btn5;
    Button btn6;

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.game_select);

        btn1 = (Button) findViewById(R.id.game0);
        btn2 = (Button) findViewById(R.id.game1);
        btn3 = (Button) findViewById(R.id.game2);
        btn4 = (Button) findViewById(R.id.game3);
        btn5 = (Button) findViewById(R.id.game4);
        btn6 = (Button) findViewById(R.id.game5);

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
                Intent intent = new Intent(gameSelectActivity.this, chooseVar.class);
                startActivity(intent);
            }
        });

        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(gameSelectActivity.this, learnVar.class);
                startActivity(intent);
            }
        });

        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(gameSelectActivity.this, learnArray.class);
                startActivity(intent);
            }
        });

        btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(gameSelectActivity.this, learnArrayPro.class);
                startActivity(intent);
            }
        });

        btn6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(gameSelectActivity.this, learnPoint.class);
                startActivity(intent);
            }
        });
    }
}
