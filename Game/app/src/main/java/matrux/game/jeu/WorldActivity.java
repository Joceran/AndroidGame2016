﻿package matrux.game.jeu; /**
 * Created by Nico on 18/01/2016.
 */

import android.app.Activity;
import android.graphics.Rect;
import android.hardware.Camera;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Context;

import java.util.Random;
import java.util.Vector;

import matrux.game.R;
import matrux.game.util.Dessin_Tete;


public class WorldActivity extends Activity implements SensorEventListener, Camera.FaceDetectionListener {

    /*
    FACE DETECTOR
     */
    private SurfaceView preview=null;
    private SurfaceHolder previewHolder=null;
    private Camera camera=null;
    private Camera.FaceDetectionListener camList = null;
    private boolean inPreview=false;
    private boolean cameraConfigured=false;
    private TextView textDetect;
    private Dessin_Tete dessinTete;
    private Random isEnnemi;
    private Vector tetesEnnemies;

     /*
    BOUSSOLE
     */
    private Radar radar;
    private static SensorManager sensorService;
    private Sensor sensor;

    private float[] r;
    private float[] g;
    private float[] resultat;

    private static final int LIMITE_X = 359;
    private int hauteur_tel;
    DisplayMetrics metrics;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main);
        //setContentView(new Ennemi(this,1,1));

        /*
        BOUSSOLE :
         */
        radar = (Radar) findViewById(R.id.radar);
        radar.setWillNotDraw(false);
        sensorService = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensor = sensorService.getDefaultSensor(Sensor.TYPE_ORIENTATION);
        if (sensor != null) {
            sensorService.registerListener(this, sensor,
                    SensorManager.SENSOR_DELAY_NORMAL);
            Log.i("Compass MainActivity", "Registerered for ORIENTATION Sensor");
        } else {
            Log.e("Compass MainActivity", "Registerered for ORIENTATION Sensor");
            Toast.makeText(this, "ORIENTATION Sensor not found",
                    Toast.LENGTH_LONG).show();
            finish();
        }

        /*
        FACEDETECTOR
         */
        Camera.Face[] faces;
        setContentView(R.layout.content_main);
        textDetect = (TextView) findViewById(R.id.visage_detect);
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
        dessinTete = new Dessin_Tete(this);
        ViewGroup.LayoutParams layoutParamsDrawing
                = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT,
                ViewGroup.LayoutParams.FILL_PARENT);
        this.addContentView(dessinTete, layoutParamsDrawing);



        preview=(SurfaceView)findViewById(R.id.surface_view);
        previewHolder=preview.getHolder();
        previewHolder.addCallback(surfaceCallback);
        previewHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
    }

    public void onResume(){
        super.onResume();

        camera = Camera.open();
        camera.setFaceDetectionListener(this);
        //camera.startFaceDetection();
        startPreview();

    }

    public void onPause(){
        if (inPreview) {
            camera.stopPreview();
        }

        camera.release();
        camera=null;
        inPreview=false;

        super.onPause();
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

    private void initPreview(int width, int height) {
        if (camera!=null && previewHolder.getSurface()!=null) {
            try {
                camera.setPreviewDisplay(previewHolder);
            }
            catch (Throwable t) {
                Log.e("PreviewDemo-surfaceCallback",
                        "Exception in setPreviewDisplay()", t);
                Toast
                        .makeText(WorldActivity.this, t.getMessage(), Toast.LENGTH_LONG)
                        .show();
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

    SurfaceHolder.Callback surfaceCallback=new SurfaceHolder.Callback() {
        public void surfaceCreated(SurfaceHolder holder) {
            // no-op -- wait until surfaceChanged()
        }

        public void surfaceChanged(SurfaceHolder holder,
                                   int format, int width,
                                   int height) {
            initPreview(width, height);
            startPreview();
        }

        public void surfaceDestroyed(SurfaceHolder holder) {
            // no-op
        }
    };


    public int getHauteur_tel() {
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);

        return metrics.heightPixels;
    }

    public void genererRandomEnnemi(int nb_ennemi) {
        for(int i = 0;i < nb_ennemi;i++) {

        }
    }

    public void genererEnnemiAtPos() {

    }

    public void genererHUD() {

    }


    @Override
    public void onSensorChanged(SensorEvent event) {
        float azimuth = event.values[0];
        //textSensor.setText(""+azimuth);
        //runOn
        radar.updateData(azimuth);
        radar.invalidate();

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
//Aléatoire fonctionnel, prochaine étape c'est d'arrêter le refresh constant
    /*public void onFaceDetection(Camera.Face[] faces, Camera camera) {
        tetesEnnemies = new Vector();
        Vector<Integer> ids = new Vector<Integer>();
        if (tetesEnnemies.isEmpty()) {
            for (int i = 0; i < faces.length; i++) {
                if (isEnnemi.nextBoolean()) {
                    tetesEnnemies.add(i, faces[i]);
                }
            }
        }
        else
        {
            Camera.Face teteTest = (Camera.Face) tetesEnnemies.firstElement();
            for(int i=0;i<faces.length;i++){
                if(teteTest.id == faces[i].id){

                }
            }
        }


        if(tetesEnnemies.isEmpty())
        {
          //  textDetect.setText(null);
            dessinTete.setHaveFace(false);
        }
        else
        {
           // textDetect.setText(R.string.visage_detect);
            dessinTete.setFaces(tetesEnnemies);
            dessinTete.setHaveFace(true);
        }
        dessinTete.invalidate();
        //Log.i("dtct","Visage detécté !");
    }*/


    /* Version de base, pas d'aléatoire */
    public void onFaceDetection(Camera.Face[] faces, Camera camera) {
        tetesEnnemies = new Vector();
        Vector<Integer> ids = new Vector<Integer>();
        for(int i=0;i<faces.length;i++){
            tetesEnnemies.add(faces[i]);
        }
        if(tetesEnnemies.isEmpty()){
            textDetect.setText(null);
            dessinTete.setHaveFace(false);
        }
        else
        {
            textDetect.setText(R.string.visage_detect);
            dessinTete.setFaces(tetesEnnemies);
            dessinTete.setHaveFace(true);
            Log.i("cc","Visage detecté");
        }
        dessinTete.invalidate();
        //Log.i("dtct","Visage detécté !");
    }

}




