package androidunlp.tpgrupo7.cuadrocromatico;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.juegoaentregar.R;

public class Splash extends AppCompatActivity {
    //espera 2000 milisegundos, pasado el tiempo inicia la actividad (menu) y se finaliza a si mismo (se borra de memoria con el finish)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(Splash.this, Menu.class);
                startActivity(i);
                finish();
            }
        }, 2000);
    }
}

