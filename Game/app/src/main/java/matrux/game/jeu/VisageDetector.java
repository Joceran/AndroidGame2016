package matrux.game.jeu;

import android.graphics.Rect;
import android.hardware.Camera;

/**
 * Created by axelm on 14/12/2015.
 */
public class VisageDetector implements Camera.FaceDetectionListener {

    public static Rect[] tabRecFace = new Rect[100];
    @Override
    public void onFaceDetection(Camera.Face[] faces, Camera camera) {
        for(int i=0;i<faces.length;i++){
            this.tabRecFace[i] = faces[i].rect;
        }
    }
    public static Rect[] getRect(){
        return tabRecFace;
    }
}


