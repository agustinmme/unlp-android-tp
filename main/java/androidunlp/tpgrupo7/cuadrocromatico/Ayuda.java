package androidunlp.tpgrupo7.cuadrocromatico;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.juegoaentregar.R;

public class Ayuda extends AppCompatActivity {
    private MediaPlayer sound;
    private int[] buttonIDs;
    private int dificultad, music, contador, eliminar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ayuda);
        Bundle dato = getIntent().getExtras();
        contador = 4;//variable q se va a mostrar como contador de tiempo... Inicia por XML en 5 es mejor forma para que se vea graficamente el 5 4 3 2 1

        if (dato != null)//si se reciben datos
        {
            music = dato.getInt("dato2");
            if (music != 0) { //si musica es 0 inicia la musica
                sound = MediaPlayer.create(Ayuda.this, R.raw.game2);
                sound.setLooping(true);
                sound.start();
                sound.seekTo(music);
                sound.setLooping(true);
            }
            dificultad = dato.getInt("dato1"); // SACO EL INT
            eliminar = dato.getInt("dato3"); // SACO EL INT
            switch (dificultad) {//dependiedno la dificultad seteo el id de los botones a mostrar
                case 1:
                    buttonIDs = new int[]{R.id.d14, R.id.d15, R.id.d16, R.id.d17, R.id.d18
                            , R.id.d21, R.id.d22, R.id.d23, R.id.d24, R.id.d25, R.id.d28, R.id.d29, R.id.d30, R.id.d31, R.id.d32, R.id.d35, R.id.d36, R.id.d37, R.id.d38, R.id.d39, R.id.d42,
                            R.id.d43, R.id.d44, R.id.d45, R.id.d46};
                    break;
                case 2:
                    buttonIDs = new int[]{R.id.d7, R.id.d8, R.id.d9, R.id.d10, R.id.d11, R.id.d12
                            , R.id.d14, R.id.d15, R.id.d16, R.id.d17, R.id.d18, R.id.d19, R.id.d21, R.id.d22, R.id.d23, R.id.d24, R.id.d25, R.id.d26, R.id.d28, R.id.d29, R.id.d30,
                            R.id.d31, R.id.d32, R.id.d33, R.id.d35, R.id.d36, R.id.d37, R.id.d38, R.id.d39, R.id.d40, R.id.d42, R.id.d43, R.id.d44, R.id.d45, R.id.d46, R.id.d47};
                    break;
                case 3:
                    buttonIDs = new int[]{R.id.d0, R.id.d1, R.id.d2, R.id.d3, R.id.d4, R.id.d5, R.id.d6, R.id.d7, R.id.d8, R.id.d9, R.id.d10, R.id.d11, R.id.d12,
                            R.id.d13, R.id.d14, R.id.d15, R.id.d16, R.id.d17, R.id.d18, R.id.d19, R.id.d20, R.id.d21, R.id.d22, R.id.d23, R.id.d24, R.id.d25, R.id.d26, R.id.d27, R.id.d28, R.id.d29, R.id.d30,
                            R.id.d31, R.id.d32, R.id.d33, R.id.d34, R.id.d35, R.id.d36, R.id.d37, R.id.d38, R.id.d39, R.id.d40, R.id.d41, R.id.d42, R.id.d43, R.id.d44, R.id.d45, R.id.d46, R.id.d47, R.id.d48};
            }
        }


        //musetro tablero correspondiente
        for (int i = 0; i < buttonIDs.length; i++) {
            Button b = (Button) findViewById(buttonIDs[i]);
            b.setVisibility(View.VISIBLE);
        }
        //si eliminar es 1 es porq se inicio la actividad desde el boton jugar en el menu
        if (eliminar == 1) {
            final TextView texto = (TextView) findViewById(R.id.texto);
            texto.setVisibility(View.VISIBLE);
            TextView texto2 = (TextView) findViewById(R.id.texto2);
            texto2.setVisibility(View.VISIBLE);
            int delay = 0;
            while (delay != 5000) {  //hace el delay de 5 segundos
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        texto.setText(Integer.toString(contador--));
                    }

                }, delay = delay + 1000);
            }
            //handler q retorna a  la actividad de tablero
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent i = new Intent(Ayuda.this, MainActivity.class);
                    i.putExtra("dato1", dificultad);
                    if (music != 0) {
                        i.putExtra("dato2", sound.getCurrentPosition());
                    } else {
                        i.putExtra("dato2", 0);
                    }
                    startActivity(i);
                    finish();
                }
            }, 5000);
        }
    }

    //dependiendo el inicio a la actividad decide si mostrar el boton de retorno.(se llama en el oncreate implicitamente)
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater mi = getMenuInflater();
        mi.inflate(R.menu.menu_ayuda, menu);
        if (eliminar == 1) {
            return false;
        } else {
            return true;
        }
    }

    //metodo llamado al clickear un item del menu de opciones
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.back) {
            onBackPressed();
        }
        return true;
    }

    //metodos de musica
    @Override
    public void onResume() {
        super.onResume();
        if (sound != null) {
            if (!sound.isPlaying()) {
                sound.start();
            }
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (sound != null) {
            if (sound.isPlaying()) {
                sound.reset();
                sound.release();
                sound = null;
            }
        }
    }

    //retorna a la actividad anterior
    @Override
    public void onBackPressed() {
        if (eliminar == 1) {
            Toast toast = Toast.makeText(this, getString(R.string.disclaimer), Toast.LENGTH_SHORT);
            toast.show();
        } else {
            super.onBackPressed();
        }
    }
}
