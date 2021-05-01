package net.feiyuyu.game;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
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
import net.feiyuyu.game.checkpoint.learnReference.learnReferenceActivity;
import net.feiyuyu.game.checkpoint.learnVar;
import net.feiyuyu.game.ui.gamePre;

import org.apache.http.util.EncodingUtils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class gameSelectActivity extends Activity {

    Button btn1;
    Button btn2;
    Button btn3;
    Button btn4;
    Button btn5;
    Button btn6;
    Button btn7;

    MediaPlayer music;


    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.game_select);

        music = mainActivity.mp;
        if(readFile("music.txt").equals("true")) {
            music.start();
        }else{
            music.pause();
        }

        btn1 = (Button) findViewById(R.id.game0);
        btn2 = (Button) findViewById(R.id.game1);
        btn3 = (Button) findViewById(R.id.game2);
        btn4 = (Button) findViewById(R.id.game3);
        btn5 = (Button) findViewById(R.id.game4);
        btn6 = (Button) findViewById(R.id.game5);
        btn7 = (Button) findViewById(R.id.game6);

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
                Intent intent = new Intent(gameSelectActivity.this, gamePre.class);
                intent.putExtra("key",1);
                startActivity(intent);
            }
        });

        btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(gameSelectActivity.this, gamePre.class);
                intent.putExtra("key",2);
                startActivity(intent);
            }
        });

        btn6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(gameSelectActivity.this, gamePre.class);
                intent.putExtra("key",3);
                startActivity(intent);
            }
        });

        btn7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(gameSelectActivity.this, learnReferenceActivity.class);
                startActivity(intent);
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
