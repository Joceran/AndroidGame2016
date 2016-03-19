package matrux.game.jeu;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;


public class Radar extends View {

    private Paint paint;
    private float orientation;
    private Point[] locationEnnemies;

    public Radar(Context context) {
        super(context);
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStrokeWidth(2);
        paint.setTextSize(25);
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.WHITE);
    }
    public Radar(Context context, AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStrokeWidth(2);
        paint.setTextSize(25);
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.WHITE);
    }

    public Radar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStrokeWidth(2);
        paint.setTextSize(25);
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.WHITE);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int xPoint = this.getMeasuredWidth() / 2;
        int yPoint = this.getMeasuredHeight() / 2;

        float rayon = (float) yPoint;
        canvas.drawCircle(xPoint, yPoint, rayon, paint);

        canvas.drawLine(xPoint,
                yPoint,
                (float) (xPoint + rayon
                        * Math.sin((double) (-orientation) / 180 * 3.143)),
                (float) (yPoint - rayon
                        * Math.cos((double) (-orientation) / 180 * 3.143)), paint);

        canvas.drawText(String.valueOf(orientation), xPoint, yPoint, paint);
    }

    public void updateData(float position) {
        this.orientation = position;
        Log.i("position :",""+orientation);

        this.invalidate();
    }

}