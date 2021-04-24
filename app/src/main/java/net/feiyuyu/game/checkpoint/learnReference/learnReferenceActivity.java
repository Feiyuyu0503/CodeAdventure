package net.feiyuyu.game.checkpoint.learnReference;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import net.feiyuyu.game.R;

import org.apache.http.util.EncodingUtils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

  /**
   * 参考https://github.com/chenxi-ysc/lianliankan
   */
public class learnReferenceActivity extends AppCompatActivity {
    private ViewPager mVp;
    public boolean sound = true;
    public static TextView scoreTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //去除标题栏
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //去除状态栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.reference_view);
        getSupportActionBar().hide();    //去掉标题栏（ActionBar）
        initView();
        }


    private void initView() {
        mVp = findViewById(R.id.vp);
        mVp.setOffscreenPageLimit(1);

        scoreTv = (TextView) findViewById(R.id.scoreTv);
        scoreTv.setText(readFile("score.txt"));

        //数据填充
        setupViewPager(mVp);

    }

    private void setupViewPager(ViewPager viewPager) {
        BottomAdapter adapter = new BottomAdapter(getSupportFragmentManager());
        adapter.addFragment(new Tab1Fragment());
        viewPager.setAdapter(adapter);
    }

    //再按一次返回键退出
    private long lastBackPressTime = -1L;
    public void onBackPressed() {
        long currentTIme = System.currentTimeMillis();
        if(lastBackPressTime == -1L || currentTIme - lastBackPressTime >= 2000){
            // 显示提示信息
            showBackPressTip();
            // 记录时间
            lastBackPressTime = currentTIme;
        }else{
            //退出应用
            System.out.println("exit current activity");
            finish();
        }
    }
    private void showBackPressTip(){
        Toast.makeText(this,"再按一次退出",Toast.LENGTH_SHORT).show();
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
