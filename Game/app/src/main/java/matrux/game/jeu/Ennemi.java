package matrux.game.jeu;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceView;

/**
 * Created by Nico on 18/01/2016.
 */
public class Ennemi extends SurfaceView implements Runnable {
    private int nbptsvie;
    private int[][] position;
    private int pts;

    Ennemi(Context context, int nbptsvie, int pts) {
        super(context);
        Canvas grid = new Canvas(Bitmap.createBitmap(1,1, Bitmap.Config.ARGB_8888));
        grid.drawColor(Color.RED);
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        grid.drawCircle(1,1,1,paint);
        this.nbptsvie = nbptsvie;
        this.pts = pts;
        this.position = new int[1][1];
    }

    public void run() {

    }
}
