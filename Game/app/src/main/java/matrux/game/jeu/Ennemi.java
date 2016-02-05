package matrux.game.jeu;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.view.SurfaceView;
import matrux.game.R;

/**
 * Created by Nico on 18/01/2016.
 */


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

class Ennemi
{
    private int nbptsvie;
    private int pts;
    private BitmapDrawable img=null; // image de l'ennemi
    private int x,y; // coordonnées x,y de l'ennemi en pixel
    private int ennemiW, ennemiH; // largeur et hauteur de l'ennemien pixels
    private int wEcran,hEcran; // largeur et hauteur de l'écran en pixels
    private boolean move = true; // 'true' si l'ennemi doit se déplacer automatiquement, 'false' sinon

    // pour déplacer l'ennemi on ajoutera INCREMENT à ses coordonnées x et y
    private static final int INCREMENT = 5;
    private int speedX=INCREMENT, speedY=INCREMENT;

    // contexte de l'application Android
    // il servira à accéder aux ressources, dont l'image de la balle
    private final Context mContext;

    // Constructeur de l'objet "Ennemi"
    public Ennemi(final Context c)
    {
        x=0; y=0; // position de départ
        mContext=c; // sauvegarde du contexte
    }

    // on attribue à l'objet "Ennemi" l'image passée en paramètre
    // w et h sont sa largeur et hauteur définis en pixels
    public BitmapDrawable setImage(final Context c, final int ressource, final int w, final int h)
    {
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
        ennemiW=wScreen/5;
        ennemiH=wScreen/5;
        img = setImage(mContext,R.mipmap.alien,ennemiW,ennemiH);
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
    public int getX() {
        return x;
    }

    // retourne la coordonnée Y de la balle
    public int getY() {
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
    public void moveWithCollisionDetection()
    {
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

    // on dessine l'ennemi, en x et y
    public void draw(Canvas canvas)
    {
        if(img==null) {return;}
        canvas.drawBitmap(img.getBitmap(), x, y, null);
    }

} // public class Ennemi
