package matrux.game.jeu;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.SurfaceHolder;
import android.widget.ImageView;

import java.util.Random;

import matrux.game.R;

/**
 * Created by Nico on 18/01/2016.
 */

class Boss extends ImageView implements SurfaceHolder.Callback
{
    private int nbptsvie;
    private int pts;
    private int ID;
    private int CD;
    private BitmapDrawable img=null; // image de l'ennemi
    private int x,y; // coordonnées x,y de l'ennemi en pixel
    private int ennemiW, ennemiH; // largeur et hauteur de l'ennemien pixels
    private int wEcran,hEcran; // largeur et hauteur de l'écran en pixels
    private boolean move = true; // 'true' si l'ennemi doit se déplacer automatiquement, 'false' sinon
    private Random r;

    // pour déplacer l'ennemi on ajoutera INCREMENT à ses coordonnées x et y
    private static final int INCREMENT = 10;
    private int speedX=INCREMENT, speedY=INCREMENT;

    // contexte de l'application Android
    // il servira à accéder aux ressources, dont l'image de la balle
    private final Context mContext;

    // Constructeur de l'objet "Ennemi"
    public Boss(final Context c, int id)
    {
        super(c);
        Random r=new Random();
        x= r.nextInt(700-0)+0;
        y= r.nextInt(700-0)+0;
        //x=0; y=0; // position de départ à adapter avec la tête
        mContext=c; // sauvegarde du contexte
        this.ID = id;
        this.nbptsvie = 10;
        this.r = new Random();
        this.CD = 0;
    }

    public int getNbptsvie() {
        return nbptsvie;
    }

    public void setNbptsvie(int n) {
        this.nbptsvie = n;
    }

    public int getID() {
        return this.ID;
    }

    public void destroy() {
        img.getBitmap().recycle();
        img = null;
    }

    public int getCD() {
        return CD;
    }

    public void setCD(int nb) {
        CD = nb;
    }

    // on attribue à l'objet "Ennemi" l'image passée en paramètre
    // w et h sont sa largeur et hauteur définis en pixels
    public BitmapDrawable setImage(final Context c, final int ressource, final int w, final int h) {
        Drawable dr = c.getResources().getDrawable(ressource);
        Bitmap bitmap = ((BitmapDrawable) dr).getBitmap();
        return new BitmapDrawable(c.getResources(), Bitmap.createScaledBitmap(bitmap, w, h, true));
    }

    // retourne 'true' si l'ennemi se déplace automatiquement
    // 'false' sinon
    // car on la bloque sous le doigt du joueur lorsqu'il la déplace
    public boolean isMoving() {
        return move;
    }

    // définit si oui ou non la balle doit se déplacer automatiquement
    // car on la bloque sous le doigt du joueur lorsqu'il la déplace
    public void setMove(boolean move) {
        this.move = move;
    }

    // redimensionnement de l'image selon la largeur/hauteur de l'écran passés en paramètre
    public void resize(int wScreen, int hScreen) {
        // wScreen et hScreen sont la largeur et la hauteur de l'écran en pixel
        // on sauve ces informations en variable globale, car elles serviront
        // à détecter les collisions sur les bords de l'écran
        wEcran=wScreen;
        hEcran=hScreen;

        // on définit (au choix) la taille de l'ennemi à 1/5ème de la largeur de l'écran
        ennemiW=wScreen/3;
        ennemiH=wScreen/3;
        img = setImage(mContext,R.mipmap.robot,ennemiW,ennemiH);
    }

    // définit la coordonnée X de l'ennemi
    public void setX(int x) {
        this.x = x;
    }

    // définit la coordonnée Y de l'ennemi
    public void setY(int y) {
        this.y = y;
    }

    // retourne la coordonnée X de la balle
    public float getX() {
        return x;
    }

    // retourne la coordonnée Y de la balle
    public float getY() {
        return y;
    }

    // retourne la largeur de la balle en pixel
    public int getEnnemiW() {
        return ennemiW;
    }

    // retourne la hauteur de la balle en pixel
    public int getEnnemiH() {
        return ennemiH;
    }

    // déplace la balle en détectant les collisions avec les bords de l'écran
    public void moveWithCollisionDetectionNormal()
    {
        boolean boost;
        int temps;
        // si on ne doit pas déplacer la balle (lorsqu'elle est sous le doigt du joueur)
        // on quitte
        if(!move) {return;}

        // on incrémente les coordonnées X et Y
        x+=speedX;
        y+=speedY;

        // si x dépasse la largeur de l'écran, on inverse le déplacement
        if(x+ennemiW > wEcran) {speedX=-INCREMENT;}

        // si y dépasse la hauteur l'écran, on inverse le déplacement
        if(y+ennemiH > hEcran) {speedY=-INCREMENT;}

        // si x passe à gauche de l'écran, on inverse le déplacement
        if(x<0) {speedX=INCREMENT;}

        // si y passe à dessus de l'écran, on inverse le déplacement
        if(y<0) {speedY=INCREMENT;}
    }

    public void moveWithCollisionDetectionBoost()
    {
            // si on ne doit pas déplacer la balle (lorsqu'elle est sous le doigt du joueur)
            // on quitte
            if(!move) {return;}

            // on incrémente les coordonnées X et Y
            x+=speedX*1.5;
            y+=speedY*1.5;

            // si x dépasse la largeur de l'écran, on inverse le déplacement
            if(x+ennemiW > wEcran) {speedX=-INCREMENT;}

            // si y dépasse la hauteur l'écran, on inverse le déplacement
            if(y+ennemiH > hEcran) {speedY=-INCREMENT;}

            // si x passe à gauche de l'écran, on inverse le déplacement
            if(x<0) {speedX=INCREMENT;}

            // si y passe à dessus de l'écran, on inverse le déplacement
            if(y<0) {speedY=INCREMENT;}
    }

    // on dessine l'ennemi, en x et y
    public void ddraw(Canvas canvas) {
        if(img==null) {return;}
        canvas.drawBitmap(img.getBitmap(), x, y, null);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        Log.e("Boss","Surface crée");
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }
} // public class Ennemi

