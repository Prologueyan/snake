package utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;

/**
 * Created by acer on 2016/4/4.
 */
public class MyTile extends View {

    private static String tag = MyTile.class.getSimpleName();

    private int size = 14;
    protected static int xcount;
    protected static int ycount;
    protected int xoffset;
    protected int yoffset;

    private Bitmap[] mTileArray = null; //位图数组
    private int[][] mTileGrid = null; //映射整个游戏画面的数组
    private final Paint mPaint = new Paint();


    public MyTile(Context context, AttributeSet attrs) {
        super(context, attrs);
        Log.i(tag, "TileView Constructor");
        Log.i(tag, "mTilesize = " + size);


        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;
        xcount = (int) Math.floor(width / size);
        ycount = (int) Math.floor(height / size);
        mTileGrid = new int[xcount][ycount];

        Log.i(tag, "xcount = " + xcount + " ycount = " + ycount);

    }

    //reset the length of Bitmaps
    public void resetTiles(int tilecount) {
        mTileArray = new Bitmap[tilecount];
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        Log.i(tag, "onSizeChanged," + "w=" + w + " h=" + h + " oldw=" + oldw + " oldh=" + oldh);
        xcount = (int) Math.floor(w / size);
        ycount = (int) Math.floor(h / size);
        Log.i(tag, "mXTileCount=" + xcount);
        Log.i(tag, "mYTileCount=" + ycount);
        xoffset = ((w - (size * xcount)) / 2);
        yoffset = ((h - (size * ycount)) / 2);
        Log.i(tag, "mXOffset=" + xoffset);
        Log.i(tag, "mYOffset=" + yoffset);

        mTileGrid = new int[xcount][ycount];
        clearTiles();
    }

    //清空图形显示
    public void clearTiles() {
        Log.i(tag, "TileView.clearTiles");
        for (int x = 0; x < xcount; x++) {
            for (int y = 0; y < ycount; y++) {
                setTile(0, x, y);
            }
        }
    }

    //在相应的坐标位置绘制相应的砖块
    public void setTile(int tileindex, int x, int y) {
        mTileGrid[x][y] = tileindex;
    }

    public void loadTile(int key, Drawable tile) {
        Bitmap bitmap = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        tile.setBounds(0, 0, size, size);
        tile.draw(canvas);
        mTileArray[key] = bitmap;
    }


    //将直接操作的画布绘制到手机界面上
    public void onDraw(Canvas canvas) {
        Log.i(tag, "onDraw");
        super.onDraw(canvas);
        Bitmap bmp;
        float left;
        float top;
        for (int x = 0; x < xcount; x++) {
            for (int y = 0; y < ycount; y++) {
                if (mTileGrid[x][y] > 0) {
                    bmp = mTileArray[mTileGrid[x][y]];
                    left = x * size + xoffset;
                    top = y * size + yoffset;
                    canvas.drawBitmap(bmp, left, top, mPaint);
                }
            }
        }
    }
}
