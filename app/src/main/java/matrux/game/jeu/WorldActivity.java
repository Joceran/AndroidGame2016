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
import android.media.MediaPlayer;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import matrux.game.R;
import matrux.game.menu.Menu;

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
    private Ennemi e;

    /*SON*/
    private MediaPlayer media1;

    /*JOUEUR*/
    private Joueur joueur;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main);

        /*
        SON
         */
        media1= MediaPlayer.create(WorldActivity.this, R.raw.zicmu);
        media1.start();
        Button music = (Button) findViewById(R.id.button_music);
        music.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (media1.isPlaying()) {
                    media1.pause();
                    findViewById(R.id.button_music).setBackgroundResource(android.R.drawable.ic_media_pause);
                } else {
                    media1.start();
                    findViewById(R.id.button_music).setBackgroundResource(android.R.drawable.ic_media_play);
                }
            }
        });
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

        csp=(CameraSurfacePreview)findViewById(R.id.camera_view);
    /*    csp.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                joueur.incrementeScore();
            }
        });*/

        ev=(EnnemiView)findViewById(R.id.ennemi_view);
        ViewGroup.LayoutParams layoutParamsDrawing
                = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT,
                ViewGroup.LayoutParams.FILL_PARENT);
        this.addContentView(csp.getDessinTete(), layoutParamsDrawing);

        /*Joueur (difficulté par défault pour le moment)
        joueur = (Joueur) findViewById(R.id.joueur);
        joueur.setWillNotDraw(false);*/


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


    public void onBackPressed(){
        media1.stop();
        Menu.media.start();
        finish();
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




