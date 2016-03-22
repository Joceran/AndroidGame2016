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

class Ennemi extends ImageView implements SurfaceHolder.Callback {
    private int nbptsvie; //Nombre de points de vie de l'ennemi
    private int pts; //Nombre de points que rapporte l'ennemi au joueur
    private int ID; //Identifiant de l'ennemi
    private BitmapDrawable img = null; // image de l'ennemi
    private int x, y; // coordonnées x,y de l'ennemi en pixel
    private int ennemiW, ennemiH; // largeur et hauteur de l'ennemien pixels
    private int wEcran, hEcran; // largeur et hauteur de l'écran en pixels
    private boolean move = true; // 'true' si l'ennemi doit se déplacer automatiquement, 'false' sinon

    // pour déplacer l'ennemi on ajoutera INCREMENT à ses coordonnées x et y
    private static final int INCREMENT = 5;
    private int speedX = INCREMENT, speedY = INCREMENT;

    // contexte de l'application Android
    // il servira à accéder aux ressources, dont l'image de l'ennemi
    private final Context mContext;

    // Constructeur de l'objet "Ennemi"
    public Ennemi(final Context c, int id) {
        super(c);
        Random r = new Random();
        x = r.nextInt(700 - 0) + 0; //X et Y : Coordonnées qui gèrent l'emplacement de l'ennemi
        y = r.nextInt(700 - 0) + 0; // Emplacement de base aléatoire
        mContext = c; // sauvegarde du contexte
        this.ID = id;
        this.nbptsvie = 2;
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

    //Fonction qui permet de détruire l'ennemi
    public void destroy() {
        img.getBitmap().recycle();
        img = null;
    }

    // on attribue à l'objet "Ennemi" l'image passée en paramètre
    // w et h sont sa largeur et hauteur définis en pixels
    public BitmapDrawable setImage(final Context c, final int ressource, final int w, final int h) {
        Drawable dr = c.getResources().getDrawable(ressource);
        Bitmap bitmap = ((BitmapDrawable) dr).getBitmap();
        return new BitmapDrawable(c.getResources(), Bitmap.createScaledBitmap(bitmap, w, h, true));
    }

    //Fonction qui définit si oui ou non l'ennemi doit se déplacer
    public void setMove(boolean move) {
        this.move = move;
    }

    //Redimensionnement de l'image selon la largeur/hauteur de l'écran passés en paramètre
    public void resize(int wScreen, int hScreen) {
        // wScreen et hScreen sont la largeur et la hauteur de l'écran en pixel
        // on sauve ces informations en variable globale, car elles serviront
        // à détecter les collisions sur les bords de l'écran
        wEcran = wScreen;
        hEcran = hScreen;

        // on définit (au choix) la taille de l'ennemi à 1/5ème de la largeur de l'écran
        ennemiW = wScreen / 5;
        ennemiH = wScreen / 5;
        img = setImage(mContext, R.mipmap.robot, ennemiW, ennemiH);
    }

    // définit la coordonnée X de l'ennemi
    public void setX(int x) {
        this.x = x;
    }

    // définit la coordonnée Y de l'ennemi
    public void setY(int y) {
        this.y = y;
    }

    // retourne la coordonnée X de l'ennemi
    public float getX() {
        return x;
    }

    // retourne la coordonnée Y de l'ennemi
    public float getY() {
        return y;
    }

    // retourne la largeur de l'ennemi en pixel
    public int getEnnemiW() {
        return ennemiW;
    }

    // retourne la hauteur de l'ennemi en pixel
    public int getEnnemiH() {
        return ennemiH;
    }

    // déplace l'enemi en détectant les collisions avec les bords de l'écran
    public void moveWithCollisionDetection() {
        if (!move) {
            return;
        }

        // on incrémente les coordonnées X et Y
        x += speedX;
        y += speedY;

        // si x dépasse la largeur de l'écran, on inverse le déplacement
        if (x + ennemiW > wEcran) {
            speedX = -INCREMENT;
        }

        // si y dépasse la hauteur l'écran, on inverse le déplacement
        if (y + ennemiH > hEcran) {
            speedY = -INCREMENT;
        }

        // si x passe à gauche de l'écran, on inverse le déplacement
        if (x < 0) {
            speedX = INCREMENT;
        }

        // si y passe à dessus de l'écran, on inverse le déplacement
        if (y < 0) {
            speedY = INCREMENT;
        }
    }

    // on dessine l'ennemi, aux coordonnées X et Y
    public void ddraw(Canvas canvas) {
        if (img == null) {
            return;
        }
        canvas.drawBitmap(img.getBitmap(), x, y, null);
    }
    //Fonction obligatoires du Listener
    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        Log.e("Ennemi", "Surface crée");
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }

    public BitmapDrawable getImg() {
        return img;
    }
}
