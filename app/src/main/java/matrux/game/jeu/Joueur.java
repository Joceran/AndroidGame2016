package matrux.game.jeu;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by joceran on 19/03/16.
 *
 * Un joueur possède x nombres de points de vie
 * Il a aussi un score.
 * Et un nom par défaut (Néon)
 *
 */
public class Joueur extends View {

    private int score,vie;
    private Paint pnt;


    //Constructeur personnalisé (si le paramètre de difficulté est changé)
    public Joueur(Context context){
        super(context);
        this.score = 0;
        this.vie = 10;
        this.pnt = new Paint();
        pnt.setColor(Color.WHITE);
        pnt.setTextSize(40);
        pnt.setAntiAlias(true);
    }
    public Joueur(Context context,AttributeSet attrs){
        super(context,attrs);
        this.score = 0;
        this.vie = 10;
        this.pnt = new Paint();
        pnt.setColor(Color.WHITE);
        pnt.setTextSize(40);
        pnt.setAntiAlias(true);
    }
    public Joueur(Context context,AttributeSet attrs, int defStyle){
        super(context,attrs,defStyle);
        this.score = 0;
        this.vie = 10;
        this.pnt = new Paint();
        pnt.setColor(Color.WHITE);
        pnt.setTextSize(40);
        pnt.setAntiAlias(true);
    }

    public int getScore() {
        return score;
    }

    public int getVie() {
        return vie;
    }

    public void incrementeScore(){
        score++;
    }

    public void decrementeVie(){
        vie--;
    }

    @Override
    public String toString() {
        return "Vie : "+getVie()+" | Score : "+getScore();
    }

    protected void onDraw(Canvas canvas){
        super.onDraw(canvas);
        canvas.drawText(this.toString(),0,50, pnt);

    }
}
