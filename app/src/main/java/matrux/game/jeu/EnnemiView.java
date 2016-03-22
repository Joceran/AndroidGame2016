package matrux.game.jeu;

/**
 * Created by Nico on 05/02/2016.
 */

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.hardware.Camera;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.Random;

import matrux.game.R;
import matrux.game.util.Dessin_Tete;

public class EnnemiView extends SurfaceView implements SurfaceHolder.Callback {

    /* déclaration de l'objet définissant la boucle principale de déplacement et de rendu, cette
    cette surface permet également l'interaction avec les ennemis, donc leur apparition et
    élimination
    */
    private GameLoopThread gameLoopThread;
    private Ennemi ennemi;
    private Ennemi ennemi2;
    private Ennemi ennemi3;
    private Ennemi ennemi4;
    private Ennemi[] tab_ennemi;
    private CameraSurfacePreview csp;
    private Boss b;
    private Random r;
    private Joueur j;
    private static long end;


    public EnnemiView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public EnnemiView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    public EnnemiView(Context context) {
        super(context);
        init(context);

    }

    public void init(Context context) {
        getHolder().addCallback(this);
        getHolder().setFormat(PixelFormat.TRANSPARENT);
        this.setBackgroundColor(Color.TRANSPARENT);
        this.setZOrderOnTop(true);
        gameLoopThread = new GameLoopThread(this);
        this.tab_ennemi = new Ennemi[4];
        this.r=new Random();
        this.j = (Joueur) findViewById(R.id.joueur);


        // création d'un tableau d'ennemis, chaque ennemi à un ID propre
        // Les boss ont leur propre ID
        ennemi = new Ennemi(this.getContext(),1);
        ennemi2 = new Ennemi(this.getContext(),2);
        ennemi3 = new Ennemi(this.getContext(),3);
        ennemi4 = new Ennemi(this.getContext(),4);
        b = new Boss(this.getContext(),1);
        tab_ennemi[0]=ennemi;
        tab_ennemi[1]=ennemi2;
        tab_ennemi[2]=ennemi3;
        tab_ennemi[3]=ennemi4;

        end = System.currentTimeMillis()+3000;
    }

    // Fonction qui "dessine" un écran de jeu
    public void doDraw(Canvas canvas) {
        if (canvas == null) {
            return;
        }
        // on efface l'écran, tout en gérant la transparence
        canvas.drawColor(0, PorterDuff.Mode.CLEAR);

        // on dessine les ennemis
        for(int i=0;i<tab_ennemi.length;i++) {
            if(tab_ennemi[i].getImg() != null) {
                tab_ennemi[i].ddraw(canvas);
                Log.i("test", "ennemi " + i + " créé");
            }
        }

        b.ddraw(canvas); //Dessin du boss (test)
    }

    // Fonction appelée par la boucle principale (gameLoopThread)
    // On gère ici le déplacement des objets
    public void update() {
        //Log.e("System",""+System.currentTimeMillis()+"///"+end);
        for(int i=0;i<tab_ennemi.length;i++) {
            tab_ennemi[i].moveWithCollisionDetection();
        }
        if(System.currentTimeMillis() < end) {
            b.moveWithCollisionDetectionBoost();
            b.setCD(500);
        }
        else {
            b.moveWithCollisionDetectionNormal();
            b.setCD(b.getCD() - 1);
            Log.e("Boss",""+b.getCD());
            if(b.getCD() == 0) {
                end = System.currentTimeMillis()+3000;
                Log.e("Boss", "Boost dispo");
            }
        }
    }

    // Fonction obligatoire de l'objet SurfaceView
    // Fonction appelée immédiatement après la création de l'objet SurfaceView
    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        // création du processus GameLoopThread si cela n'est pas fait
        //this.demarrer();
    }

    // Fonction obligatoire de l'objet SurfaceView
    // Fonction appelée juste avant que l'objet ne soit détruit.
    // on tente ici de stopper le processus de gameLoopThread
    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        boolean retry = true;
        gameLoopThread.setRunning(false);
        while (retry) {
            try {
                gameLoopThread.join();
                retry = false;
            }

            catch (InterruptedException e) {

            }
        }
    }

    // Gère l'interaction entre les ennemis et le joueur, donc l'élimination de ceux-ci
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int currentX = (int) event.getX();
        int currentY = (int) event.getY();

        switch (event.getAction()) {

            // code exécuté lorsque le doigt touche l'écran.
            case MotionEvent.ACTION_DOWN:
                // si le doigt touche la balle :
                //ennemi = getEnnemi();
                for (int i = 0; i < tab_ennemi.length; i++) {
                    if (currentX >= tab_ennemi[i].getX() &&
                            currentX <= tab_ennemi[i].getX() + tab_ennemi[i].getEnnemiW() &&
                            currentY >= tab_ennemi[i].getY() && currentY <= tab_ennemi[i].getY() + tab_ennemi[i].getEnnemiH()) {
                        // on arrête de déplacer la balle
                        tab_ennemi[i].setMove(false);
                        tab_ennemi[i].setNbptsvie(tab_ennemi[i].getNbptsvie() - 1);
                        if (tab_ennemi[i].getNbptsvie() == 0) {
                            tab_ennemi[i].destroy();
                            //delEnnemi(ennemi.getID());
                        } else {
                            tab_ennemi[i].setMove(true);
                        }
                    }
                }
                if (csp.getDessinTete().getBitmap() != null) {
                    if (currentX >= csp.getDessinTete().getX() &&
                            currentX <= csp.getDessinTete().getX() + csp.getDessinTete().getWidth() &&
                            currentY >= csp.getDessinTete().getY() && currentY <= csp.getDessinTete().getY() + csp.getDessinTete().getHeight()) {
                        Log.e("test", "le doigt touche sa sale face");

                        //csp.setTete(false);
                        //csp.getDessinTete().destroy();
                        this.demarrer();
                    }
                }
                if (currentX >= b.getX() + b.getEnnemiW() &&
                        currentY <= b.getY() + b.getEnnemiH()) {
                    b.setMove(false);
                    b.setNbptsvie(b.getNbptsvie() - 1);
                    if (b.getNbptsvie() == 0) {
                        b.destroy();
                    } else {
                        b.setMove(true);
                    }
                }
                break;
        }
        return true; // On retourne "true" pour indiquer qu'on a géré l'évènement
    }

    // Fonction obligatoire de l'objet SurfaceView
    // Fonction appelée à la CREATION et MODIFICATION et ONRESUME de l'écran
    // nous obtenons ici la largeur/hauteur de l'écran en pixels
    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int w, int h) {
        for(int j=0;j<tab_ennemi.length;j++) {
            tab_ennemi[j].resize(w,h); // on définit la taille des ennemis selon la taille de l'écran
        }
        b.resize(w,h);

    }

    public void demarrer() {
        if (gameLoopThread.getState() == Thread.State.TERMINATED) {
            gameLoopThread = new GameLoopThread(this);
        }
        gameLoopThread.setRunning(true);
        gameLoopThread.start();
    }
}
