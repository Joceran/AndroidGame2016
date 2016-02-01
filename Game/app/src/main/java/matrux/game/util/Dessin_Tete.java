package matrux.game.util;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.hardware.Camera;
import android.view.View;

/**
 * Created by Joceran on 01/02/2016.
 */
public class Dessin_Tete extends View {

    boolean haveFace;
    Camera.Face[] faces;
    Paint drawingPaint;

    public Dessin_Tete(Context context) {
        super(context);
        haveFace = false;
        drawingPaint = new Paint();
        drawingPaint.setColor(Color.GREEN);
        drawingPaint.setStyle(Paint.Style.STROKE);
        drawingPaint.setStrokeWidth(2);
    }

    public void setHaveFace(boolean h){
        haveFace = h;
        invalidate();
    }

    public void setFaces(Camera.Face[] faces)
    {
        this.faces=faces;
        invalidate();
    }

    protected void onDraw(Canvas canvas) {

        /* Si haveface, pour chaque tête on dessine un carré autour de cette dernière.
        A faire : Remplacer le carré par l'une des têtes du jeu */
        if(haveFace){
            int vWidth = getWidth();
            int vHeight = getHeight();

            for(int i=0; i<faces.length; i++){

                int l = faces[i].rect.left;
                int t = faces[i].rect.top;
                int r = faces[i].rect.right;
                int b = faces[i].rect.bottom;
                int left = (l+1000) * vWidth/2000;
                int top  = (t+1000) * vHeight/2000;
                int right = (r+1000) * vWidth/2000;
                int bottom = (b+1000) * vHeight/2000;
                canvas.drawRect(
                        left, top, right, bottom,
                        drawingPaint);
            }
        }else{
            canvas.drawColor(Color.TRANSPARENT);
        }
    }

}
