package net.feiyuyu.game.checkpoint;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import android.support.v7.widget.CardView;

import com.bin.david.form.core.SmartTable;     //这个包名才正确
import com.bin.david.form.core.TableConfig;
import com.bin.david.form.data.style.FontStyle;

import net.feiyuyu.game.R;
import net.feiyuyu.game.checkpoint.learnReference.learnReferenceActivity;
import net.feiyuyu.game.pointData;
import net.feiyuyu.game.ui.gamePre;
import net.feiyuyu.game.ui.myImageButton;
import net.feiyuyu.game.ui.tableLayout;

import org.apache.http.util.EncodingUtils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class learnPoint extends Activity {

    SmartTable table;

    CardView card1;
    tableLayout choice1;
    tableLayout choice2;
    tableLayout choice3;
    tableLayout choice4;
    tableLayout choice5;
    tableLayout choice6;
    tableLayout temp;

    myImageButton watermelon;
    myImageButton apple;
    myImageButton peach;
    myImageButton banana;
    myImageButton grape;
    myImageButton lemon;

    int count = 6;
    boolean isSuccess = true;

    TextView scoreTv;

    TextView gameStateTv;
    Button retryOrNextCpBtn;

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        //去除标题栏
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //去除状态栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.point_view);

        table = (SmartTable)findViewById(R.id.table);
        card1 = (CardView)findViewById(R.id.card1);
        choice1 = (tableLayout)findViewById(R.id.choice1);choice1.setSeq(6);
        choice2 = (tableLayout)findViewById(R.id.choice2);choice2.setSeq(5);
        choice3 = (tableLayout)findViewById(R.id.choice3);choice3.setSeq(4);
        choice4 = (tableLayout)findViewById(R.id.choice4);choice4.setSeq(3);
        choice5 = (tableLayout)findViewById(R.id.choice5);choice5.setSeq(2);
        choice6 = (tableLayout)findViewById(R.id.choice6);choice6.setSeq(1);

        watermelon = (myImageButton)findViewById(R.id.watermelonBtn);watermelon.setName("watermelon");
        apple = (myImageButton)findViewById(R.id.appleBtn);apple.setName("apple");
        peach = (myImageButton)findViewById(R.id.peachBtn);peach.setName("peach");
        banana = (myImageButton)findViewById(R.id.bananaBtn);banana.setName("banana");
        grape = (myImageButton)findViewById(R.id.grapeBtn);grape.setName("grape");
        lemon = (myImageButton)findViewById(R.id.lemonBtn);lemon.setName("lemon");

        scoreTv = (TextView)findViewById(R.id.scoreTv);
        gameStateTv = (TextView)findViewById(R.id.gameStateTv);
        retryOrNextCpBtn = (Button)findViewById(R.id.retryOrNextBtn);

        initGame();
    }


    public void initGame(){

        scoreTv.setText(readFile("score.txt"));

        List<String> fruitList = new ArrayList<>();
        fruitList.add("grape");
        fruitList.add("lemon");
        fruitList.add("apple");
        fruitList.add("watermelon");
        fruitList.add("banana");
        fruitList.add("peach");
        //打乱数组顺序
        Collections.shuffle(fruitList);

        List<Integer> seqList = Arrays.asList(1,2,3,4,5,6);
        Collections.shuffle(seqList);

        //随机化列表内容
        List<pointData> list = new ArrayList<>();
        for(int i=0;i<6;i++){
            list.add(new pointData(fruitList.get(i),seqList.get(i)));
        }

        //移除行列号
        TableConfig tableConfig = table.getConfig();
        tableConfig.setShowXSequence(false);
        tableConfig.setShowYSequence(false);

        table.setData(list);
        table.getConfig().setContentStyle(new FontStyle(80, Color.BLUE));
        //为水果图片绑定正确楼层
        for(pointData i :list){
            if(i.getObj()==watermelon.getName()) {
                watermelon.setSeq(i.getFloor());
                break;
            }
        }
        for(pointData i :list){
            if(i.getObj()==apple.getName()) {
                apple.setSeq(i.getFloor());
                break;
            }
        }
        for(pointData i :list){

            if(i.getObj()==peach.getName()) {
                peach.setSeq(i.getFloor());
                break;
            }
        }
        for(pointData i :list){
            if(i.getObj()==banana.getName()) {
                banana.setSeq(i.getFloor());
                break;
            }
        }
        for(pointData i :list){
            if(i.getObj()==grape.getName()) {
                grape.setSeq(i.getFloor());
                break;
            }
        }
        for(pointData i :list){
            if(i.getObj()==lemon.getName()) {
                lemon.setSeq(i.getFloor());
                break;
            }
        }

        choice1.btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //watermelon.setImageResource(R.drawable.grape_48);
                card1.setVisibility(View.VISIBLE);
                temp = choice1;     //对象temp作为对象choice的引用
                //temp.setSeq(choice1.getSeq());
            }
        });

        choice2.btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                card1.setVisibility(View.VISIBLE);
                temp = choice2;     //对象temp作为对象choice的引用
                //temp.setSeq(choice2.getSeq());
            }
        });

        choice3.btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                card1.setVisibility(View.VISIBLE);
                temp = choice3;     //对象temp作为对象choice的引用
                //temp.setSeq(choice3.getSeq());
            }
        });

        choice4.btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                card1.setVisibility(View.VISIBLE);
                temp = choice4;     //对象temp作为对象choice的引用
                //temp.setSeq(choice4.getSeq());
            }
        });

        choice5.btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                card1.setVisibility(View.VISIBLE);
                temp = choice5;     //对象temp作为对象choice的引用
                //temp.setSeq(choice5.getSeq());
            }
        });

        choice6.btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                card1.setVisibility(View.VISIBLE);
                temp = choice6;     //对象temp作为对象choice的引用
                //temp.setSeq(choice6.getSeq());
            }
        });

        watermelon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count--;
                //obj1.getDrawable();
                temp.addImage(watermelon.getDrawable());
                temp.btn.setVisibility(View.GONE);
                //obj1.setImageResource(0);
                watermelon.setVisibility(View.GONE);
                card1.setVisibility(View.GONE);
                if(watermelon.getSeq()!=temp.getSeq()) {
                    isSuccess = false;
                }
                isSuccess();
            }
        });

        apple.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count--;
                //obj1.getDrawable();
                temp.addImage(apple.getDrawable());
                temp.btn.setVisibility(View.GONE);
                apple.setVisibility(View.GONE);
                card1.setVisibility(View.GONE);
                if(apple.getSeq()!=temp.getSeq()) {
                    isSuccess = false;
                }
                isSuccess();
            }
        });

        peach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count--;
                //obj1.getDrawable();
                temp.addImage(peach.getDrawable());
                temp.btn.setVisibility(View.GONE);
                peach.setVisibility(View.GONE);
                card1.setVisibility(View.GONE);
                if(peach.getSeq()!=temp.getSeq()) {
                    isSuccess = false;
                }
                isSuccess();
            }
        });

        banana.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count--;
                //obj1.getDrawable();
                temp.addImage(banana.getDrawable());
                temp.btn.setVisibility(View.GONE);
                banana.setVisibility(View.GONE);
                card1.setVisibility(View.GONE);
                if(banana.getSeq()!=temp.getSeq()) {
                    isSuccess = false;
                }
                isSuccess();
            }
        });

        grape.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count--;
                //obj1.getDrawable();
                temp.addImage(grape.getDrawable());
                temp.btn.setVisibility(View.GONE);
                grape.setVisibility(View.GONE);
                card1.setVisibility(View.GONE);
                if(grape.getSeq()!=temp.getSeq()) {
                    isSuccess = false;
                }
                isSuccess();
            }
        });

        lemon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count--;
                //obj1.getDrawable();
                temp.addImage(lemon.getDrawable());
                temp.btn.setVisibility(View.GONE);
                lemon.setVisibility(View.GONE);
                card1.setVisibility(View.GONE);
                if(lemon.getSeq()!=temp.getSeq()) {
                    isSuccess = false;
                }
                isSuccess();
            }

        });
    }

    //判断游戏是否成功
    public void isSuccess(){
        if(count==0){
            card1.setVisibility(View.VISIBLE);
            if(isSuccess) {
                gameStateTv.setVisibility(View.VISIBLE);
                //Toast.makeText(learnPoint.this, "游戏成功！", Toast.LENGTH_SHORT).show();
                addScore(50);
                retryOrNextCpBtn.setVisibility(View.VISIBLE);
                retryOrNextCpBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(learnPoint.this, learnReferenceActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });
            }
            else {
                gameStateTv.setText("Fail");
                gameStateTv.setVisibility(View.VISIBLE);
                retryOrNextCpBtn.setText("重玩");
                retryOrNextCpBtn.setVisibility(View.VISIBLE);
                retryOrNextCpBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(getIntent());
                        finish();
                    }
                });
                //Toast.makeText(learnPoint.this, "游戏失败！", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void addScore(int i){
        String res = String.valueOf(scoreTv.getText());
        int sc = Integer.valueOf(res)+i;
        writeFile("score.txt",sc+"");
        scoreTv.setText(sc+"");
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
