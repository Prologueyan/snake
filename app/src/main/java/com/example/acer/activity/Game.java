package com.example.acer.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.widget.TextView;

import com.example.acer.snake.R;

import utils.SnakeView;

/**
 * Created by acer on 2016/4/4.
 */
public class Game extends Activity {

    private static String ICICLE_KEY = "snake-view";
    private static String tag = Game.class.getSimpleName();

    private int mMode = READY;
    public static final int PAUSE = 0;
    public static final int READY = 1;
    public static final int RUNNING = 2;
    public static final int LOSE = 3;

    private SnakeView mSnakeView = null;
    private TextView mTextView = null;
    private GestureDetector mGestureDetector = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game);
        mSnakeView = (SnakeView) findViewById(R.id.mSnakeView);

        mSnakeView.setBackgroundResource(R.drawable.background);
        mTextView = (TextView) findViewById(R.id.tv);
        mSnakeView.setStatusTextView(mTextView);


        if (savedInstanceState == null) {
            mSnakeView.setMode(SnakeView.READY);
        } else {
            Bundle bundle = savedInstanceState.getBundle(ICICLE_KEY);
            if (bundle != null) {
                mSnakeView.restoreState(bundle);
            } else {
                mSnakeView.setMode(SnakeView.PAUSE);
            }
        }

        startGame();
    }

    private void startGame() {
        Log.i("Game", "startGame");
        if (mMode == READY | mMode == LOSE) {
            mSnakeView.initNewGame();
            mSnakeView.setMode(RUNNING);
            mSnakeView.update();
        }
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putBundle(ICICLE_KEY, mSnakeView.saveState());
    }


}
