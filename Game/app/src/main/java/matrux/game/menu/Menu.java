package matrux.game.menu;

import matrux.game.R;
import matrux.game.jeu.WorldActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class Menu extends Activity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_menu);

        Button jouer = (Button) findViewById(R.id.button_jouer);
        jouer.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                startActivity(new Intent(Menu.this, WorldActivity.class));
            }
         });


        Button options = (Button) findViewById(R.id.button_options);
        options.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Menu.this, Options.class));
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
