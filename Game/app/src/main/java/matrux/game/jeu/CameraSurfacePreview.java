package matrux.game.jeu;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.hardware.Camera;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorManager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.Random;
import java.util.Vector;

import matrux.game.R;
import matrux.game.util.Dessin_Tete;

/**
 * Created by Nathan on 29/02/2016.
 */
public class CameraSurfacePreview extends SurfaceView implements SurfaceHolder.Callback,Camera.FaceDetectionListener {
    private SurfaceHolder mHolder;
    private boolean inPreview=false;
    private boolean cameraConfigured=false;
    private Camera camera=null;
    private Camera.FaceDetectionListener camList = null;

    private Random isEnnemi;
    private Vector tetesEnnemies;
    private Dessin_Tete dessinTete;
    private EnnemiView ev;
    private float[] r;
    private float[] g;
    private float[] resultat;

    public CameraSurfacePreview(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public CameraSurfacePreview(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    public CameraSurfacePreview(Context context) {
        super(context);
        init(context);

    }

    public void init(Context context) {
        mHolder=getHolder();
        mHolder.addCallback(this);
        mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

         /*
        FACEDETECTOR
         */
        Camera.Face[] faces;
        //textDetect = (TextView) findViewById(R.id.visage_detect);
        camList = new Camera.FaceDetectionListener() {

            private Rect[] TabRecFace = new Rect[100];
            public void onFaceDetection(Camera.Face[] faces, Camera camera) {
                for(int i=0;i<faces.length;i++){
                    this.TabRecFace[i] = faces[i].rect;
                }

            }

        };

         /* TETE ENNEMI */
        isEnnemi = new Random();
        dessinTete = new Dessin_Tete(context);

    }

    public Dessin_Tete getDessinTete() {
        return dessinTete;
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        initPreview(width, height);
        startPreview();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }


    public void onResumeC() {
        camera = Camera.open();
        camera.setFaceDetectionListener(this);
        //camera.startFaceDetection();
        startPreview();
    }
    public void onPauseC() {
        if (inPreview) {
            camera.stopPreview();
        }

        camera.release();
        camera=null;
        inPreview=false;
    }



    private void initPreview(int width, int height) {
        if (camera!=null && mHolder.getSurface()!=null) {
            try {
                camera.setPreviewDisplay(mHolder);
            }
            catch (Throwable t) {
                Log.e("PreviewDemo-surfaceCallback",
                        "Exception in setPreviewDisplay()", t);

            }

            if (!cameraConfigured) {
                Camera.Parameters parameters=camera.getParameters();
                Camera.Size size=getBestPreviewSize(width, height,parameters);

                if (size!=null) {
                    parameters.setPreviewSize(size.width, size.height);
                    camera.setParameters(parameters);
                    cameraConfigured=true;
                }
            }
        }
    }

    private void startPreview() {
        if (cameraConfigured && camera!=null) {
            camera.startPreview();
            camera.startFaceDetection();
            inPreview=true;
        }
    }

    private Camera.Size getBestPreviewSize(int width, int height, Camera.Parameters parameters) {
        Camera.Size result=null;
        for (Camera.Size size : parameters.getSupportedPreviewSizes()) {
            if (size.width<=width && size.height<=height) {
                if (result==null) {
                    result=size;
                }
                else {
                    int resultArea=result.width*result.height;
                    int newArea=size.width*size.height;

                    if (newArea>resultArea) {
                        result=size;
                    }
                }
            }
        }

        return(result);
    }




    public void onFaceDetection(Camera.Face[] faces, Camera camera) {
        tetesEnnemies = new Vector();

        int j = 0;
        for(int i=0;i<faces.length;i++){
            if(isEnnemi.nextBoolean() == true){
                tetesEnnemies.add(j, faces[i]);
                j++;
                //java.lang.NullPointerException: Attempt to write to null array

            }
        }

        if(tetesEnnemies.isEmpty())
        {
            //textDetect.setText(null);
            dessinTete.setHaveFace(false);
        }
        else
        {
            //textDetect.setText(R.string.visage_detect);
            dessinTete.setFaces(tetesEnnemies);
            dessinTete.setHaveFace(true);


        }
        dessinTete.invalidate();
        //ev.demarrer();
        //Log.i("dtct","Visage detécté !");

    }
}
