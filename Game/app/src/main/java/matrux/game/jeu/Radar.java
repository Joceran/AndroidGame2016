package matrux.game.jeu;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

import matrux.game.R;

/**
 * TODO: document your custom view class.
 */
public class Radar extends View {

    private Paint paint;
    private float orientation;
    private Point[] locationEnnemies;
    private int tailleEcran;

    public Radar(Context context) {
        super(context);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        for(int i=0;i<locationEnnemies.length;i++){
            if(locationEnnemies[i].x < tailleEcran*orientation*10)
            canvas.drawCircle(locationEnnemies[i].x/orientation,locationEnnemies[i].y,1,paint);
        }
    }


}