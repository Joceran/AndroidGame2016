package matrux.game.jeu; /**
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
import android.widget.TextView;
import android.widget.Toast;

import matrux.game.R;

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
    private TextView TextDectect;


    /*
    BOUSSOLE
     */
    private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    private Sensor mMagneticField;

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
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mMagneticField = mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);

        /*
        FACEDETECTOR
         */
        setContentView(R.layout.content_main);
        TextDectect = (TextView) findViewById(R.id.visage_detect);
        camList = new Camera.FaceDetectionListener() {

            private Rect[] TabRecFace = new Rect[100];
            public void onFaceDetection(Camera.Face[] faces, Camera camera) {
                for(int i=0;i<faces.length;i++){
                    this.TabRecFace[i] = faces[i].rect;
                }

            }

        };
        Camera.Face[] faces;

        preview=(SurfaceView)findViewById(R.id.surface_view);
        previewHolder=preview.getHolder();
        previewHolder.addCallback(surfaceCallback);
        previewHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
    }

    public void onResume(){
        super.onResume();

        camera = Camera.open();
        camera.setFaceDetectionListener(camList);
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
        float[] accValue;
        float[] magnValue;
        mSensorManager.getRotationMatrix(r, g, event.values, event.values);
        if(event.sensor.getType()==Sensor.TYPE_ACCELEROMETER){
            accValue = event.values;
        }
        if(event.sensor.getType()==Sensor.TYPE_MAGNETIC_FIELD){
            magnValue = event.values;
        }
        mSensorManager.getRotationMatrix(r, g, event.values, event.values);
        mSensorManager.getOrientation(r,resultat);

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    public void onFaceDetection(Camera.Face[] faces, Camera camera) {

    }
}

