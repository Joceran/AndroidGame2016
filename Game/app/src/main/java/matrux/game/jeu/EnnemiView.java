package matrux.game.jeu;

/**
 * Created by Nico on 05/02/2016.
 */
import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import matrux.game.R;

public class EnnemiView extends SurfaceView implements SurfaceHolder.Callback {

    // déclaration de l'objet définissant la boucle principale de déplacement et de rendu
    private GameLoopThread gameLoopThread;
    private Ennemi ennemi;

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

        // création d'un objet "balle", dont on définira la largeur/hauteur
        // selon la largeur ou la hauteur de l'écran
        ennemi = new Ennemi(this.getContext());
    }

    // Fonction qui "dessine" un écran de jeu
    public void doDraw(Canvas canvas) {
        if (canvas == null) {
            return;
        }
        // on efface l'écran, en blanc
        //canvas.drawColor(Color.TRANSPARENT);

        // on dessine la balle
        ennemi.draw(canvas);
    }

    // Fonction appelée par la boucle principale (gameLoopThread)
    // On gère ici le déplacement des objets
    public void update() {
        ennemi.moveWithCollisionDetection();
    }

    // Fonction obligatoire de l'objet SurfaceView
    // Fonction appelée immédiatement après la création de l'objet SurfaceView
    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        // création du processus GameLoopThread si cela n'est pas fait
        if (gameLoopThread.getState() == Thread.State.TERMINATED) {
            gameLoopThread = new GameLoopThread(this);
        }
        gameLoopThread.setRunning(true);
        gameLoopThread.start();
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
                if (currentX >= ennemi.getX() &&
                        currentX <= ennemi.getX() + ennemi.getEnnemiW() &&
                        currentY >= ennemi.getY() && currentY <= ennemi.getY() + ennemi.getEnnemiH()) {
                    // on arrête de déplacer la balle
                    ennemi.setMove(false);
                }
                break;

            // code exécuté lorsque le doight glisse sur l'écran.
            case MotionEvent.ACTION_MOVE:
                // on déplace la balle sous le doigt du joueur
                // si elle est déjà sous son doigt (oui si on a setMove à false)
                if (!ennemi.isMoving()) {
                    ennemi.setX(currentX);
                    ennemi.setY(currentY);
                }
                break;

            // lorsque le doigt quitte l'écran
            case MotionEvent.ACTION_UP:
                // on reprend le déplacement de la balle
                ennemi.setMove(true);
        }

        return true;  // On retourne "true" pour indiquer qu'on a géré l'évènement
    }

    // Fonction obligatoire de l'objet SurfaceView
    // Fonction appelée à la CREATION et MODIFICATION et ONRESUME de l'écran
    // nous obtenons ici la largeur/hauteur de l'écran en pixels
    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int w, int h) {
        ennemi.resize(w,h); // on définit la taille de la balle selon la taille de l'écran
    }

    public void updateView(Activity act) {
        final EnnemiView ev = this;
        act.runOnUiThread(new Runnable() {
            public void run() {
                ev.invalidate();
            }
        });
    }

    /*public void demarrer() {
        gameLoopThread.setRunning(true);
    }*/
}
