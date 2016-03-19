package matrux.game.jeu;

/**
 * Created by Nico on 05/02/2016.
 */

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.Random;

public class EnnemiView extends SurfaceView implements SurfaceHolder.Callback {

    // déclaration de l'objet définissant la boucle principale de déplacement et de rendu
    private GameLoopThread gameLoopThread;
    private Ennemi ennemi;
    private Ennemi ennemi2;
    private Ennemi ennemi3;
    private Ennemi ennemi4;
    private Ennemi[] tab_ennemi;


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
        Random r=new Random();


        // création d'un objet "balle", dont on définira la largeur/hauteur
        // selon la largeur ou la hauteur de l'écran
        ennemi = new Ennemi(this.getContext(),1);
        ennemi2 = new Ennemi(this.getContext(),2);
        ennemi3 = new Ennemi(this.getContext(),3);
        ennemi4 = new Ennemi(this.getContext(),4);
        tab_ennemi[0]=ennemi;
        tab_ennemi[1]=ennemi2;
        tab_ennemi[2]=ennemi3;
        tab_ennemi[3]=ennemi4;
    }
/*
    public void addEnnemi(Ennemi e) {
        tab_ennemi.add(e);
    }

    public void delEnnemi(int ID) {
        for(int i=0;i<tab_ennmi.length;i++) {
            if(tab_ennemi[i].getID()==ID) {

            }
        }
    }

    public Ennemi getEnnemi(int ID) {
        Iterator<Ennemi> it = tab_ennemi.iterator();
        while(it.hasNext()) {
            if(ID == it.next().getID()) {
                return it.next();
            }
        }
        return null;
    } */

    // Fonction qui "dessine" un écran de jeu
    public void doDraw(Canvas canvas) {
        if (canvas == null) {
            return;
        }
        // on efface l'écran, en blanc
        canvas.drawColor(0, PorterDuff.Mode.CLEAR);

        // on dessine la balle
        for(int i=0;i<tab_ennemi.length;i++) {

           tab_ennemi[i].ddraw(canvas);

            Log.e("test", "ennemi " + i + " créé");
        }
    }

    // Fonction appelée par la boucle principale (gameLoopThread)
    // On gère ici le déplacement des objets
    public void update() {

        for(int i=0;i<tab_ennemi.length;i++) {

            tab_ennemi[i].moveWithCollisionDetection();


        }
    }

    // Fonction obligatoire de l'objet SurfaceView
    // Fonction appelée immédiatement après la création de l'objet SurfaceView
    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        // création du processus GameLoopThread si cela n'est pas fait
        this.demarrer();
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

    // Gère les touchés sur l'écran
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int currentX = (int) event.getX();
        int currentY = (int) event.getY();

        switch (event.getAction()) {

            // code exécuté lorsque le doigt touche l'écran.
            case MotionEvent.ACTION_DOWN:
                // si le doigt touche la balle :
                //ennemi = getEnnemi();
                if (currentX >= ennemi.getX() &&
                        currentX <= ennemi.getX() + ennemi.getEnnemiW() &&
                        currentY >= ennemi.getY() && currentY <= ennemi.getY() + ennemi.getEnnemiH()) {
                    // on arrête de déplacer la balle
                    ennemi.setMove(false);
                    ennemi.setNbptsvie(ennemi.getNbptsvie()- 1);
                    if(ennemi.getNbptsvie() == 0) {
                        ennemi.destroy();
                        //delEnnemi(ennemi.getID());
                    }
                    else {
                        ennemi.setMove(true);
                    }
                }
                break;
        }

        return true;  // On retourne "true" pour indiquer qu'on a géré l'évènement
    }

    // Fonction obligatoire de l'objet SurfaceView
    // Fonction appelée à la CREATION et MODIFICATION et ONRESUME de l'écran
    // nous obtenons ici la largeur/hauteur de l'écran en pixels
    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int w, int h) {
        for(int j=0;j<tab_ennemi.length;j++) {

            tab_ennemi[j].resize(w,h); // on définit la taille de la balle selon la taille de l'écran


        }

    }

    public void demarrer() {
        if (gameLoopThread.getState() == Thread.State.TERMINATED) {
            gameLoopThread = new GameLoopThread(this);
        }
        gameLoopThread.setRunning(true);
        gameLoopThread.start();
    }
}
