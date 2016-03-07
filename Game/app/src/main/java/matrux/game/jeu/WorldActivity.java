package matrux.game.jeu; /**
 * Created by Nico on 18/01/2016.
 */

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.DisplayMetrics;

import android.util.Log;
import android.view.ViewGroup;
import android.widget.Toast;


import matrux.game.R;
import matrux.game.util.Dessin_Tete;


public class WorldActivity extends Activity implements SensorEventListener {

    /*
    FACE DETECTOR
    */
    private CameraSurfacePreview csp;
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

    private EnnemiView ev;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main);


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


    /*ENNEMI */


        ev = (EnnemiView) findViewById(R.id.ennemi_view);
        csp=(CameraSurfacePreview)findViewById(R.id.camera_view);
        ViewGroup.LayoutParams layoutParamsDrawing
                = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT,
                ViewGroup.LayoutParams.FILL_PARENT);
        this.addContentView(csp.getDessinTete(), layoutParamsDrawing);
        ev.updateView(this);

    }

    public void onResume(){
        super.onResume();

        csp.onResumeC();

    }

    public void onPause(){

        csp.onPauseC();
        super.onPause();
    }

    public void onSensorChanged(SensorEvent event) {
        float azimuth = event.values[0];
        //textSensor.setText(""+azimuth);
        //runOn
        radar.updateData(azimuth);
        radar.invalidate();

    }


    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }




    /* Version de base, pas d'aléatoire
    public void onFaceDetection(Camera.Face[] faces, Camera camera) {
        int i=0;

        if(faces.length == 0){
            textDetect.setText(null);
            dessinTete.setHaveFace(false);
        }
        else
        {
            textDetect.setText(R.string.visage_detect);
            dessinTete.setFaces(faces);
            dessinTete.setHaveFace(true);
            Log.i("cc","Visage detecté");
        }
        dessinTete.invalidate();
        //Log.i("dtct","Visage detécté !");
    }*/

}




