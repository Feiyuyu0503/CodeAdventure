package net.feiyuyu.game;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import org.apache.http.util.EncodingUtils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;

public class settingActivity extends Activity implements OnClickListener {

    MediaPlayer music;
    SoundPool soundPool;
    Switch musicOn;
    HashMap<Integer,Integer> soundPoolMap;

    EditText editText;
    SharedPreferences sp;
    public final String userName = "userName";
   // Button saveBtn;

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        initSounds();
        setContentView(R.layout.setting);
        musicOn = (Switch) this.findViewById((R.id.musicBtn));
        musicOn.setOnClickListener(this);

        writeFile("a.txt","你好");
        //TextView tv = findViewById(R.id.userName);
        //tv.setText(readFile("a.txt"));

        editText = findViewById(R.id.userName);
        sp = this.getPreferences(MODE_PRIVATE);
        String res = sp.getString(userName,null);
        if(res != null){
            editText.setText(res);
        }

        Button saveBtn = findViewById(R.id.saveBtn);
        saveBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = sp.edit();
                editor.putString(userName,String.valueOf(editText.getText()));
                editor.commit();
                System.out.println("commit-test");
            }
        });
    }

    protected void onDestory(){
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(userName,String.valueOf(editText.getText()));
        editor.commit();
        System.out.println("commit-test");
        super.onDestroy();
    }


    public void initSounds(){
        music = MediaPlayer.create(this,R.raw.aigei_com);
        soundPool = new SoundPool(1, AudioManager.STREAM_MUSIC,100);
        soundPoolMap = new HashMap<Integer, Integer>();
        soundPoolMap.put(1,soundPool.load(this,R.raw.aigei_com,1));
    }

    public void playSound(int sound,int loop){
        AudioManager mgr = (AudioManager) this.getSystemService(Context.AUDIO_SERVICE);
        float streamVolumeCurrent = mgr.getStreamVolume(AudioManager.STREAM_MUSIC);
        float streamVolumeMax = mgr.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        float volume = streamVolumeCurrent/streamVolumeMax;
        soundPool.play(1,volume,volume,1,loop,1f);
    }

    public void onClick(View v){
        if(v == musicOn)
            music.start();
    }

    //test i/o stream
    public void writeFile(String fileName,String msg){
        try{
            FileOutputStream fout = openFileOutput(fileName,MODE_PRIVATE);
            byte [] bytes = msg.getBytes();
            fout.write(bytes);
            fout.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
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

