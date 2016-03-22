package matrux.game.jeu;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
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

    private int score; //Score du joueur, de base le score est de 0
    private int vie; //Nombre de points de vie du joueur
    private Paint pnt; //Permet l'affichage du score et de la vie du joueur


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

    //Incrémente le score du joueur lors de la mort d'un ennemi/boss
    public void incrementeScore(){
        score++;
        Log.i("SCORE", "Incrementation");
        this.invalidate();
    }
    //Décrémente la vie du joueur lorsqu'il ne vainc pas un ennemi
    public void decrementeVie(){
        vie--;
        Log.i("VIE", "Decrementation");
        this.invalidate();
    }

    @Override
    public String toString() {
        return "Vie : "+getVie()+" | Score : "+getScore();
    }

    //Dessine sur la vue les paramètres de vie et de score du joueur
    protected void onDraw(Canvas canvas){
        super.onDraw(canvas);
        canvas.drawText(this.toString(),0,50, pnt);

    }
}
