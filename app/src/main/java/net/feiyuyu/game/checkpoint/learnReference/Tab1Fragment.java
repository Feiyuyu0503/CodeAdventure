package net.feiyuyu.game.checkpoint.learnReference;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Point;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;

import android.support.v4.app.Fragment;
import android.text.format.Time;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.SubMenu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import net.feiyuyu.game.R;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import static android.content.Context.MODE_PRIVATE;

public class Tab1Fragment extends Fragment {
    private GameConf config;
    private GameService gameService;
    private GameView gameView;
    private Button startButton;
    private TextView timeTextView;
    private AlertDialog.Builder lostDialog;
    private AlertDialog.Builder successDialog;
    private Timer timer;
    private int gameTime;
    private boolean isPlaying = false;
    private Piece selectedPiece = null;
    private SoundPool soundPool = new SoundPool(2, AudioManager.STREAM_SYSTEM , 8);
    private int sdp;
    private int wrong;

    private EditText et;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab1, null);
        gameView = view.findViewById(R.id.gameView);
        setHasOptionsMenu(true);
        timeTextView = view.findViewById(R.id.timeText);
        timeTextView.setVisibility(View.INVISIBLE);
        startButton = view.findViewById(R.id.startButton);
        //sdp = soundPool.load(getContext(),R.raw.sdp,1);
        //wrong = soundPool.load(getContext(),R.raw.wrong,1);

        //Toast.makeText(getContext(), sb.toString(), Toast.LENGTH_SHORT).show();
        init();
        return view;
    }


    /**
     * 初始化游戏的方法
     */
    private void init() {
        Display display = getActivity().getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int screenWidth = size.x;
        int screenHeight = size.y;
        config = new GameConf(7, 6, screenWidth, screenHeight, GameConf.DEFAULT_TIME, getContext());
        //System.out.println("here: "+screenHeight+" "+screenWidth);
        gameService = new GameServiceImpl(this.config);
        et = new EditText(getContext());
        gameView.setGameService(gameService);
        gameView.setSelectImage(ImageUtil.getSelectImage(getContext()));
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View source) {
                startGame(0);
                startButton.setVisibility(View.INVISIBLE);
                //gameView.setBackgroundColor(0xFFFFFFFF);
                timeTextView.setVisibility(View.VISIBLE);
            }
        });
        // 为游戏区域的触碰事件绑定监听器
        this.gameView.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View view, MotionEvent e) {
                if (e.getAction() == MotionEvent.ACTION_DOWN) {
                    gameViewTouchDown(e);
                }
                if (e.getAction() == MotionEvent.ACTION_UP) {
                    gameViewTouchUp(e);
                }
                return true;
            }
        });
        // 初始化游戏失败的对话框
        lostDialog = createDialog("GAME OVER", "游戏失败！重新开始", R.drawable.lost)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        startGame(0);
                    }
                });
        // 初始化游戏胜利的对话框
        successDialog = createDialog("Success", "你真厉害！恭喜你通过所有关卡！",
                R.drawable.success).setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        //String input = et.getText().toString();

                        //显示积分
                        TextView scoreTv;
                        scoreTv = learnReferenceActivity.scoreTv;
                        String res = String.valueOf(scoreTv.getText());
                        int i = Integer.valueOf(res)+100;
                        scoreTv.setText(i+"");
                        writeFile("score.txt",i+"");
                        //getContext().finishGame();
                        getActivity().finish();
                    }
                });
    }

    /**
     * Handler类，异步处理
     */
    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0x123:
                    timeTextView.setText("目前用时： " + gameTime);
                    gameTime++; // 游戏剩余时间减少
                    // 时间小于0, 游戏失败
                    if (gameTime > 200) {
                        // 停止计时
                        stopTimer();
                        // 更改游戏的状态
                        isPlaying = false;
                        // 失败后弹出对话框
                        lostDialog.show();
                        return;
                    }
                    break;
            }
        }
    };

    @Override
    public void onPause() {
        super.onPause();
        // 暂停游戏
        stopTimer();
    }

    @Override
    public void onResume() {
        super.onResume();
        // 如果处于游戏状态中
        if(isPlaying) {
            startGame(0);
        }
    }

    /**
     * 触碰游戏区域的处理方法
     *
     * @param event
     */
    private void gameViewTouchDown(MotionEvent event) {
        Piece[][] pieces = gameService.getPieces();
        float touchX = event.getX();
        Log.i("X", String.valueOf(touchX));
        float touchY = event.getY();
        Log.i("Y", String.valueOf(touchY));
        Piece currentPiece = gameService.findPiece(touchX, touchY);
        if (currentPiece == null)
            return;
        this.gameView.setSelectedPiece(currentPiece);
        if (this.selectedPiece == null) {
            this.selectedPiece = currentPiece;
            this.gameView.postInvalidate();
            return;
        }
        // 表示之前已经选择了一个
        if (this.selectedPiece != null) {
            LinkInfo linkInfo = this.gameService.link(this.selectedPiece,
                    currentPiece);
            if (linkInfo == null) {
                this.selectedPiece = currentPiece;
                if(((learnReferenceActivity)getActivity()).sound){
                    soundPool.play(wrong, 0.5f, 0.5f, 0, 0, 1);
                }
                this.gameView.postInvalidate();
            } else {
                handleSuccessLink(linkInfo, this.selectedPiece, currentPiece, pieces);
            }
        }
    }

    /**
     * 触碰游戏区域的处理方法
     *
     * @param e
     */
    private void gameViewTouchUp(MotionEvent e) {
        this.gameView.postInvalidate();
    }

    /**
     * 以gameTime作为剩余时间开始或恢复游戏
     *
     * @param gameTime
     *            剩余时间
     */
    private void startGame(int gameTime) {
        this.gameTime = gameTime;
        gameView.startGame();
        isPlaying = true;
        if(timer==null) {
            this.timer = new Timer();
            this.timer.schedule(new TimerTask() {
                public void run() {
                    handler.sendEmptyMessage(0x123);
                }
            }, 0, 1000);
        }
        this.selectedPiece = null;
    }

    /**
     * 成功连接后处理
     *
     * @param linkInfo
     *            连接信息
     * @param prePiece
     *            前一个选中方块
     * @param currentPiece
     *            当前选择方块
     * @param pieces
     *            系统中还剩的全部方块
     */
    private void handleSuccessLink(LinkInfo linkInfo, Piece prePiece,
                                   Piece currentPiece, Piece[][] pieces) {
        // 它们可以相连, 让GamePanel处理LinkInfo
        this.gameView.setLinkInfo(linkInfo);
        // 将gameView中的选中方块设为null
        this.gameView.setSelectedPiece(null);
        this.gameView.postInvalidate();
        // 将两个Piece对象从数组中删除
        pieces[prePiece.getIndexX()][prePiece.getIndexY()] = null;
        pieces[currentPiece.getIndexX()][currentPiece.getIndexY()] = null;
        // 将选中的方块设置null。
        this.selectedPiece = null;
        if(((learnReferenceActivity)getActivity()).sound){
            soundPool.play(sdp, 0.5f, 0.5f, 0, 0, 1);
        }
        // 判断是否还有剩下的方块, 如果没有, 游戏胜利
        if (!this.gameService.hasPieces()) {
            // 游戏胜利
            this.successDialog.show();
            // 停止定时器
            stopTimer();
            // 更改游戏状态
            //isPlaying = false;
        }
    }

    /**
     * 创建对话框的工具方法
     *
     * @param title
     *            标题
     * @param message
     *            内容
     * @param imageResource
     *            图片
     * @return
     */
    private AlertDialog.Builder createDialog(String title, String message,
                                             int imageResource) {
        return new AlertDialog.Builder(getContext()).setTitle(title)
                .setMessage(message).setIcon(imageResource);
    }

    /**
     * 停止计时
     */
    private void stopTimer() {
        // 停止定时器
        if(timer!=null) {
            this.timer.cancel();
            this.timer = null;
        }
    }

    public void writeFile(String fileName,String msg){
        try{
            FileOutputStream fout = getContext().openFileOutput(fileName,MODE_PRIVATE);
            byte [] bytes = msg.getBytes();
            fout.write(bytes);
            fout.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
