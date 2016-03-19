package matrux.game.menu;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import matrux.game.R;
import matrux.game.jeu.WorldActivity;


public class Menu extends Activity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);


        Button jouer = (Button) findViewById(R.id.button_jouer);
        jouer.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                startActivity(new Intent(Menu.this, WorldActivity.class));
            }
         });


        Button apropos = (Button) findViewById(R.id.button_apropos);
        apropos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Menu.this, Apropos.class));
            }
        });

        Button exit = (Button) findViewById(R.id.button_quitter);
        exit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                finish();
                System.exit(0);
            }
        });


    }
}
