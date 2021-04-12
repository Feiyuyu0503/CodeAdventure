package net.feiyuyu.game.ui;

import android.accessibilityservice.AccessibilityService;
import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import net.feiyuyu.game.R;

public class myGameView extends View {

    //myGameView应该只承担绘图呈现的作用，任何响应的功能性作用应该放在 关卡类 中实现

    int height;
    int width;

    public myGameView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        System.out.println("Constructor1");
        //Canvas canvas = new Canvas();
        //onDraw(canvas);
    }

    public myGameView(Context context) {
        super(context);
        System.out.println("Constructor2");
        //Canvas canvas = new Canvas();
        //onDraw(canvas);
    }

    public myGameView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        System.out.println("Constructor3");
    }

    //Button btn = findViewById(R.id.createBtn);


    @Override
    protected void onDraw(Canvas canvas){
        super.onDraw(canvas);

        setBackgroundColor(Color.parseColor("#974CAF50"));
        //setBackground();
        //获取宽高的方法view.getMeasureWidth();
        width = this.getMeasuredWidth();
        height = this.getMeasuredHeight();
        System.out.println(width+'\n');
        System.out.println(height);

        // 设置画笔的一些属性
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        // 设置抗锯齿
        paint.setAntiAlias(true);
        paint.setColor(Color.YELLOW);
        paint.setTextSize(40);

        //测试绘图
        //canvas.drawLine(50,100,50,200,paint);
        //canvas.drawCircle(0,0,20,paint);

        //画小球
        //canvas.drawCircle(65,height/2,50,paint);
        //画右测区域
        //paint.setColor(Color.GREEN);
        paint.setColor(Color.parseColor("#974CAF50"));
        canvas.drawRect(width-350,0,width,height/2,paint);

        paint.setColor(Color.parseColor("#4F2196F3"));
        canvas.drawRect(width-350,height/2,width,height,paint);
        paint.setColor(Color.BLUE);

        //canvas.rotate(-90);
        canvas.drawText("void main(){",width-340,height/4-80,paint);
        canvas.drawText("____;",width-340,height/4-40,paint);
        canvas.drawText("cout<<var<<endl;",width-340,height/4+30,paint);
        canvas.drawText("}",width-340,height/4+80,paint);
        //canvas.drawText("常量1",width-75,height/4,paint);
        paint.setColor(Color.RED);
        //canvas.drawText("变量",320,this.getMeasuredHeight()-50,paint);
        canvas.drawText("void main(){",width-340,height/4*3-80,paint);
        canvas.drawText("____;",width-340,height/4*3-40,paint);
        canvas.drawText("var++;",width-340,height/4*3+30,paint);
        canvas.drawText("cout<<var<<endl;",width-310,height/4*3+80,paint);
        canvas.drawText("}",width-340,height/4*3+120,paint);

        //view也是控件吗？view中加不了其他控件？在xml文件中可并列叠加控件

        System.out.println("onDraw is completed.");

    }
}
