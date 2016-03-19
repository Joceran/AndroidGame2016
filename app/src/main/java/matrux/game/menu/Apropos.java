package matrux.game.menu;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import matrux.game.R;

public class Apropos extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_propos);

        Button btn_retour = (Button) findViewById(R.id.button_retour);

        btn_retour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
