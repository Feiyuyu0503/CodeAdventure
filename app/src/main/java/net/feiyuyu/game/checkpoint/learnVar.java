package net.feiyuyu.game.checkpoint;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import net.feiyuyu.game.R;

import org.apache.http.util.EncodingUtils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class learnVar extends Activity {

    View cardView;
    View cardView1;

    TextView tvScore; //积分显示

    Button taskBtn;
    TextView taskContent; //任务要求内容"声明一个变量"

    Boolean isCorrect = false; //任务答案选择
    RadioButton choice1,choice2; //任务选项
    Button okBtn; //确认选项按钮

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_view);

        initGame();

    }

    public void initGame(){
        //隐藏任务要求显示框，并重新设置任务内容
        cardView = (View)findViewById(R.id.card);
        cardView1 = (View)findViewById(R.id.card1);
        cardView.setVisibility(View.GONE);
        cardView1.setVisibility(View.GONE);
        taskContent = (TextView)findViewById(R.id.taskContent);
        taskContent.setText("声明一个变量");

        //设置积分
        tvScore = (TextView)findViewById(R.id.score);
        String res = String.valueOf(readFile("score.txt"));
        tvScore.setText(res);

        //设置任务要求按钮
        taskBtn = (Button)findViewById(R.id.createBtn);
        taskBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cardView.setVisibility(View.VISIBLE);
            }
        });

        //任务选项按钮事件
        choice1 = (RadioButton) findViewById(R.id.constVar);
        choice2 = (RadioButton) findViewById(R.id.var);
        choice1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isCorrect = false;
            }
        });
        choice2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isCorrect = true;
            }
        });

        //确认选项按钮
        okBtn = (Button)findViewById(R.id.okBtn);
        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isCorrect){
                    cardView.setVisibility(View.GONE);
                    gameStart();
                }else{
                    AlertDialog.Builder builder = new AlertDialog.Builder(learnVar.this);
                    builder.setTitle("提示");
                    builder.setMessage("再好好想一想吧~");
                    builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(learnVar.this, "请重新选择", Toast.LENGTH_SHORT).show();
                        }
                    });
                    //builder.setNeutralButton("取消", null);
                    builder.show();
                }
            }
        });


    }

    //选项正确，开始游戏
    public void gameStart(){
        myObj obj = new myObj(learnVar.this);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-1,-1);
        addContentView(obj,layoutParams);     //在游戏view中添加移动对象
    }

    //内部类中设置移动对象
    class myObj extends View{

        public myObj(Context context) {
            super(context);
        }

        public myObj(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
            super(context, attrs, defStyleAttr);
        }

        Paint paint = new Paint();
        public void onDraw(Canvas canvas) {
            //paint画笔的属性必须在onDraw中设定？
            paint.setStyle(Paint.Style.FILL);
            paint.setAntiAlias(true);
            paint.setColor(Color.RED);
            paint.setTextSize(200);

            //test obj
            canvas.drawText("hello",60,200,paint);

            Bitmap birdPic = BitmapFactory.decodeResource(this.getResources(), R.drawable.bird);
            int width = birdPic.getWidth();
            int height = birdPic.getHeight();
            Matrix matrix=new Matrix();
            matrix.postScale(0.35f, 0.35f);//获取缩放比例
            Bitmap birdbmp = Bitmap.createBitmap(birdPic,0,0,
                    width,height,matrix,true);//根据缩放比例获取新的位图

            Bitmap varPic = BitmapFactory.decodeResource(this.getResources(), R.drawable.var);
            int vwidth = varPic.getWidth();
            int vheight = varPic.getHeight();
            matrix.postScale(0.4f, 0.4f);//获取缩放比例
            Bitmap varbmp = Bitmap.createBitmap(varPic,0,0,
                    vwidth,vheight,matrix,true);//根据缩放比例获取新的位图

        }
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
