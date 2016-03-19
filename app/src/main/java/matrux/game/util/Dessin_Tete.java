package matrux.game.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.hardware.Camera;
import android.view.View;

import java.util.Vector;

import matrux.game.R;

/**
 * Created by Joceran on 01/02/2016.
 */
public class Dessin_Tete extends View {

    boolean haveFace;
    Vector faces;
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

    public void setFaces(Vector faces)
    {
        this.faces=faces;
        invalidate();
    }

    protected void onDraw(Canvas canvas) {
        Paint p;
        /* Si haveface, pour chaque tête on dessine un carré autour de cette dernière.
        A faire : Remplacer le carré par l'une des têtes du jeu */
        if(haveFace){
            int vWidth = getWidth();
            int vHeight = getHeight();
            for(int i=0; i<faces.size(); i++){
                Camera.Face face = (Camera.Face) faces.get(i);

                int l = face.rect.left;
                int t = face.rect.top;
                int r = face.rect.right;
                int b = face.rect.bottom;
                int left = (l+1000) * vWidth/2000;
                int top  = (t+1000) * vHeight/2000;
                int right = (r+1000) * vWidth/2000;
                int bottom = (b+1000) * vHeight/2000;


                //smith.setImageResource(i);

                Bitmap btm = BitmapFactory.decodeResource(getResources(), R.drawable.supersmith);
                Rect rect = new Rect(left,top,right,bottom);
                /*canvas.drawRect(
                        left, top, right, bottom,
                        drawingPaint);*/
                p=new Paint(Color.TRANSPARENT);
                canvas.drawBitmap(btm,null,rect,p);
            }
        }else{
            canvas.drawColor(Color.TRANSPARENT);
        }
    }

}
