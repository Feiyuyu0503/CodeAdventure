package net.feiyuyu.game.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import net.feiyuyu.game.R;
import net.feiyuyu.game.checkpoint.learnArray;
import net.feiyuyu.game.checkpoint.learnArrayPro;
import net.feiyuyu.game.checkpoint.learnPoint;
import net.feiyuyu.game.mainActivity;
import net.feiyuyu.game.settingActivity;

import org.apache.http.util.EncodingUtils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class gamePre extends Activity {

    int i;
    Button startBtn;
    Button createBtn;
    Button okBtn;
    CardView card;

    TextView scoreTv;
    TextView taskTv;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.gamepre);

        //接收参数
        Intent intent = getIntent();
        this.i = intent.getIntExtra("key",0);

        //显示积分
        scoreTv = (TextView)findViewById(R.id.score);
        scoreTv.setText(String.valueOf(readFile("score.txt")));

        //更新任务要求
        taskTv = (TextView)findViewById(R.id.taskTv);
        if(i == 3){
            taskTv.setText("    指针是用来存放内存地址的变量，它存放了所指向变量的地址，请将水果放到正确的内存空间吧！");
        }
        card = (CardView)findViewById(R.id.card);
        createBtn = (Button)findViewById(R.id.createBtn);
        createBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                card.setVisibility(View.VISIBLE);
            }
        });


        okBtn = (Button)findViewById(R.id.okBtn);
        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                card.setVisibility(View.GONE);
            }
        });

        startBtn = (Button)findViewById(R.id.startButton);
        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                switch (i){
                    case 1:
                        intent=new Intent(gamePre.this, learnArray.class);
                        startActivity(intent);
                        finish();
                        break;
                    case 2:
                        intent=new Intent(gamePre.this, learnArrayPro.class);
                        startActivity(intent);
                        finish();
                        break;
                    case 3:
                        intent=new Intent(gamePre.this, learnPoint.class);
                        startActivity(intent);
                        finish();
                        break;
                    default:
                        break;
                }
            }
        });

    }

    public String readFile(String fileName){
        String res = "";
        try{
            FileInputStream fin = openFileInput(fileName);
            int length = fin.available();
            byte [] buffer = new byte[length];
            fin.read(buffer);
            res = EncodingUtils.getString(buffer,"UTF-8");
            fin.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("File not found");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return res;
    }
}
