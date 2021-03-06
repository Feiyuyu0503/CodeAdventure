package net.feiyuyu.game.checkpoint.learnReference;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.Point;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;

import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
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

    Button taskBtn;
    Button okBtn;
    CardView card;
    TextView cpName;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab1, null);
        gameView = view.findViewById(R.id.gameView);
        setHasOptionsMenu(true);
        timeTextView = view.findViewById(R.id.timeText);
        timeTextView.setVisibility(View.INVISIBLE);
        startButton = view.findViewById(R.id.startButton);
        taskBtn = view.findViewById(R.id.createBtn);
        okBtn = view.findViewById(R.id.okBtn);
        card = view.findViewById(R.id.card);
        cpName = view.findViewById(R.id.cpName);
        //sdp = soundPool.load(getContext(),R.raw.sdp,1);
        //wrong = soundPool.load(getContext(),R.raw.wrong,1);

        //Toast.makeText(getContext(), sb.toString(), Toast.LENGTH_SHORT).show();
        init();
        return view;
    }


    /**
     * ????????????????????????
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
                taskBtn.setVisibility(View.GONE);
                startGame(0);
                startButton.setVisibility(View.INVISIBLE);
                //gameView.setBackgroundColor(0xFFFFFFFF);
                timeTextView.setVisibility(View.VISIBLE);
                cpName.setVisibility(View.GONE);
            }
        });
        // ?????????????????????????????????????????????
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
        // ?????????????????????????????????
        lostDialog = createDialog("GAME OVER", "???????????????????????????", R.drawable.lost)
                .setPositiveButton("??????", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        startGame(0);
                    }
                });
        // ?????????????????????????????????
        successDialog = createDialog("????????????", "??????????????????:\nint a;\nint &b=a;\n?????????????????????????????????????????????",
                R.drawable.success).setPositiveButton("??????",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        //String input = et.getText().toString();

                        //????????????
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

        //??????????????????
        taskBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                card.setVisibility(View.VISIBLE);
            }
        });
        //???????????????????????????????????????
        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                card.setVisibility(View.GONE);
            }
        });
    }

    /**
     * Handler??????????????????
     */
    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0x123:
                    timeTextView.setTextColor(Color.RED);
                    timeTextView.setText("??????????????? " + gameTime);
                    gameTime++; // ????????????????????????
                    // ????????????0, ????????????
                    if (gameTime > 200) {
                        // ????????????
                        stopTimer();
                        // ?????????????????????
                        isPlaying = false;
                        // ????????????????????????
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
        // ????????????
        stopTimer();
    }

    @Override
    public void onResume() {
        super.onResume();
        // ???????????????????????????
        if(isPlaying) {
            startGame(0);
        }
    }

    /**
     * ?????????????????????????????????
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
        // ?????????????????????????????????
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
     * ?????????????????????????????????
     *
     * @param e
     */
    private void gameViewTouchUp(MotionEvent e) {
        this.gameView.postInvalidate();
    }

    /**
     * ???gameTime???????????????????????????????????????
     *
     * @param gameTime
     *            ????????????
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
     * ?????????????????????
     *
     * @param linkInfo
     *            ????????????
     * @param prePiece
     *            ?????????????????????
     * @param currentPiece
     *            ??????????????????
     * @param pieces
     *            ??????????????????????????????
     */
    private void handleSuccessLink(LinkInfo linkInfo, Piece prePiece,
                                   Piece currentPiece, Piece[][] pieces) {
        // ??????????????????, ???GamePanel??????LinkInfo
        this.gameView.setLinkInfo(linkInfo);
        // ???gameView????????????????????????null
        this.gameView.setSelectedPiece(null);
        this.gameView.postInvalidate();
        // ?????????Piece????????????????????????
        pieces[prePiece.getIndexX()][prePiece.getIndexY()] = null;
        pieces[currentPiece.getIndexX()][currentPiece.getIndexY()] = null;
        // ????????????????????????null???
        this.selectedPiece = null;
        if(((learnReferenceActivity)getActivity()).sound){
            soundPool.play(sdp, 0.5f, 0.5f, 0, 0, 1);
        }
        // ?????????????????????????????????, ????????????, ????????????
        if (!this.gameService.hasPieces()) {
            // ????????????
            this.successDialog.show();
            // ???????????????
            stopTimer();
            // ??????????????????
            //isPlaying = false;
        }
    }

    /**
     * ??????????????????????????????
     *
     * @param title
     *            ??????
     * @param message
     *            ??????
     * @param imageResource
     *            ??????
     * @return
     */
    private AlertDialog.Builder createDialog(String title, String message,
                                             int imageResource) {
        return new AlertDialog.Builder(getContext()).setTitle(title)
                .setMessage(message).setIcon(imageResource);
    }

    /**
     * ????????????
     */
    private void stopTimer() {
        // ???????????????
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
