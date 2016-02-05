package matrux.game.jeu;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.hardware.display.DisplayManager;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by Nico on 05/02/2016.
 */
public class SurfaceViewEnnemi extends SurfaceView implements SurfaceHolder.Callback {
    private SurfaceHolder sh;
    private final Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private int width;
    private int height;
    EnnemiThread thread;
    Context ctx;

    public SurfaceViewEnnemi(Context context, DisplayMetrics metrics) {
        super(context);
        sh = getHolder();
        sh.addCallback(this);
        paint.setColor(Color.BLUE);
        paint.setStyle(Paint.Style.FILL);
        ctx = context;
        width = metrics.widthPixels;
        height = metrics.heightPixels;
        setFocusable(true);
    }
    public void surfaceCreated(SurfaceHolder holder) {
        Canvas canvas = sh.lockCanvas();
        canvas.drawColor(Color.BLACK);
        canvas.drawCircle(100, 200, 50, paint);
        sh.unlockCanvasAndPost(canvas);
        thread = new EnnemiThread(sh, ctx, new Handler(),height,width);
        thread.setRunning(true);
        thread.start();
    }
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        thread.doStart();
    }
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;
        thread.setRunning(false);
        while (retry) {
            try {
                thread.join();
                retry = false;
            }
            catch (InterruptedException e) {

            }
        }
    }
}

class EnnemiThread extends Thread {
    private final Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

    private int canvasWidth;
    private int canvasHeight;
    private boolean run = false;
    private SurfaceHolder sh;
    private Context ctx;

    private float posX;
    private float posY;
    private float targX;
    private float targY;

    public EnnemiThread(SurfaceHolder surfaceHolder, Context context, Handler handler,int h,int w) {
        sh = surfaceHolder;
        handler = handler;
        ctx = context;
        canvasHeight = h;
        canvasWidth = w;
    }
    public void doStart() {
        posX = (int) Math.random()%canvasHeight;
        posY = (int) Math.random()%canvasWidth;
        targX = (int) Math.random()%canvasHeight;
        targY = (int) Math.random()%canvasWidth;
    }
    public void run() {
        while (run) {
            Canvas c = null;
            try {
                c = sh.lockCanvas(null);
                synchronized (sh) {
                    doDraw(c);
                }
            } finally {
                if (c != null) {
                    sh.unlockCanvasAndPost(c);
                }
            }
        }
    }

    public void setRunning(boolean b) {
        run = b;
    }

    private void doDraw(Canvas canvas) {
        canvas.save();
        if ((posX <= targX) && (posY <= targY)) {
            posX++;
            posY++;
        } else if ((posX >= targX) && (posY >= targY)) {
            posX--;
            posY--;
        } else if ((posX <= targX) && (posY >= targY)) {
            posX++;
            posY--;
        } else if ((posX >= targX) && (posY <= targY)) {
            posX--;
            posY++;
        }
        canvas.restore();
        canvas.drawColor(Color.BLACK);
        canvas.drawCircle(posX, posY, 50, paint);
        Log.i("X",""+posX);
        if((posX == targX) && (posY == targY)) {
            targX = (int) Math.random()%canvasHeight;
            targY = (int) Math.random()%canvasWidth;
        }
    }
}