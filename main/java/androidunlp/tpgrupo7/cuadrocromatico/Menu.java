package androidunlp.tpgrupo7.cuadrocromatico;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidunlp.tpgrupo7.cuadrocromatico.Dialogs.ConfirmacionDialogFragment;

import com.example.juegoaentregar.R;

public class Menu extends AppCompatActivity {
    private TextView t1, t2, t3;
    private Button b1, b2, b3;
    private MediaPlayer soundButton, start, sound, blocked;
    private int dificultad = 0, musicaa;
    private boolean musica;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        Bundle dato = getIntent().getExtras();
        //seteo componentes de musica
        if (dato != null)//si se reciben datos
        {
            musicaa = dato.getInt("dato2");
            if (musicaa != 0) { //si musica es 0 inicia la musica
                sound = MediaPlayer.create(Menu.this, R.raw.game2);
                sound.setLooping(true);
                sound.start();
                sound.setVolume(0.3f, 0.3f);
                sound.seekTo(musicaa);
            }
        } else {
            sound = MediaPlayer.create(Menu.this, R.raw.game2); //Dejo otra cancion para testear land y port musicaprueba2
            sound.setLooping(true);
            sound.setVolume(0.3f, 0.3f);
        }

        start = MediaPlayer.create(Menu.this, R.raw.start);
        soundButton = MediaPlayer.create(Menu.this, R.raw.boton);
        blocked = MediaPlayer.create(Menu.this, R.raw.blocked);
        blocked.setVolume(5f, 5f);
        //seteo textos y botones de pantalla

        t1 = findViewById(R.id.textobaja);
        t2 = findViewById(R.id.textomedia);
        t3 = findViewById(R.id.textoalta);
        b1 = findViewById(R.id.baja);
        b2 = findViewById(R.id.media);
        b3 = findViewById(R.id.alta);
        //seteo boton de musica, se lee preferencia en caso si ya se presiono el boton
        ImageView imageView = (ImageView) findViewById(R.id.sonidomenu);
        int musicaglobal = leerPreferencias();
        if (musicaglobal == 1) {
            musica = true;
            imageView.setImageResource(R.drawable.ic_volume);
        } else {
            musica = false;
            imageView.setImageResource(R.drawable.ic_volume_off);
        }

    }

    //activa dialogo de salida
    public void salir(View view) {
        ConfirmacionDialogFragment dialogo = new ConfirmacionDialogFragment();
        dialogo.show(getSupportFragmentManager(), "agConfirmacion");
    }

    //invoca actividad ranking y cierra menu
    public void rank(View v) {
        Intent i = new Intent(this, Ranking.class);
        i.putExtra("dato1", 10);    //se lo invoca con 10 para solo mostrar el ranking sin cargarle datos
        startActivity(i);
        finish();

    }

    public void play(View v) {
        if (dificultad != 0) {  //se selecciono una dificultad previamente
            if (musica) {   //se pone musica de juego
                start.seekTo(0);
                start.start();
            }
            //llama a actividad ayuda con la dificultad, con la musica (para q no se corte) y con un 1 ("dato3") para iniciar el handler
            Intent i = new Intent(this, Ayuda.class);
            i.putExtra("dato1", dificultad);
            if (musica) {
                i.putExtra("dato2", sound.getCurrentPosition());
            } else {
                i.putExtra("dato2", 0);
            }
            i.putExtra("dato3", 1);
            startActivity(i);
            sound.pause();
            finish();
        } else {    //si no se selecciona dificultad
            String st = getResources().getString(R.string.elegir);
            Toast toast = Toast.makeText(this, st, Toast.LENGTH_LONG);
            toast.show();
            if (musica) {
                blocked.seekTo(0);
                blocked.start();
            }
        }
    }

    public void creditosOnClick(View v) {
        Intent i = new Intent(this, Creditos.class);
        if (musica) {
            i.putExtra("dato2", sound.getCurrentPosition());
        } else {
            i.putExtra("dato2", 0);
        }
        startActivity(i);
        finish();

    }

    //efecto de musica del click
    public void soundboton() {
        if (musica) {
            soundButton.seekTo(0);
            soundButton.start();
        }
    }

    //recibe la dificultad, dependiendo de esta repinta los botones y muestra el tablero correcto
    public void selec(View v) {
        switch (v.getId()) {
            case R.id.baja:
                soundboton();
                b1.setTextColor(Color.parseColor("#FFFF00"));
                b2.setTextColor(Color.parseColor("#ffffff"));
                b3.setTextColor(Color.parseColor("#ffffff"));
                t1.setVisibility(View.VISIBLE);
                t2.setVisibility(View.INVISIBLE);
                t3.setVisibility(View.INVISIBLE);
                dificultad = 1;
                break;
            case R.id.media:
                soundboton();
                b1.setTextColor(Color.parseColor("#ffffff"));
                b2.setTextColor(Color.parseColor("#FFFF00"));
                b3.setTextColor(Color.parseColor("#ffffff"));
                t1.setVisibility(View.INVISIBLE);
                t2.setVisibility(View.VISIBLE);
                t3.setVisibility(View.INVISIBLE);
                dificultad = 2;
                break;
            case R.id.alta:
                soundboton();
                b1.setTextColor(Color.parseColor("#ffffff"));
                b2.setTextColor(Color.parseColor("#ffffff"));
                b3.setTextColor(Color.parseColor("#FFFF00"));
                t1.setVisibility(View.INVISIBLE);
                t2.setVisibility(View.INVISIBLE);
                t3.setVisibility(View.VISIBLE);
                dificultad = 3;
                break;
        }
    }

    //controlar la musica
    @Override
    public void onResume() {
        super.onResume();
        if (sound != null) {
            if (!sound.isPlaying()) {
                if (musica) {
                    sound.start();
                }
            }
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (sound != null && sound.isPlaying()) {
            if (musica) {
                sound.pause();
            }
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (sound != null) {
            if (sound.isPlaying()) {
                if (musica) {
                    sound.reset();
                    sound.release();
                    sound = null;
                    start.release();
                }
            }
        }
    }

    //cambia el icono y setea la musica. Metodo onClick
    public void sonido(View v) {
        ImageView imageView = (ImageView) findViewById(R.id.sonidomenu);
        if (sound != null && sound.isPlaying()) {
            imageView.setImageResource(R.drawable.ic_volume_off);
            sound.pause();
            musica = false;
        } else {
            imageView.setImageResource(R.drawable.ic_volume);
            musica = true;
            sound = MediaPlayer.create(this, R.raw.game2);
            sound.seekTo(musicaa);
            sound.start();
        }
        //se guarda la preferencia de musica de usuario
        guardarPreferencias();


    }


    //permite q no se borre la dificultad y la preferencia de musica cuando se gira la pantalla
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("counter", dificultad);
        sound.pause();
        outState.putInt("possition", sound.getCurrentPosition());
    }

    //restaura lo guardado en onSaveInstanceState
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        ImageView imageView = (ImageView) findViewById(R.id.sonidomenu);
        int pos = savedInstanceState.getInt("possition");
        int musicaglobal = leerPreferencias();

        if (musicaglobal == 1) {
            musica = true;
        } else {
            musica = false;
        }
        sound.seekTo(pos);
        if (musica) {
            imageView.setImageResource(R.drawable.ic_volume);
        } else {
            imageView.setImageResource(R.drawable.ic_volume_off);
        }
        int contardor = savedInstanceState.getInt("counter");
        dificultad = contardor;
        switch (dificultad) {
            case 1:
                b1.setTextColor(Color.parseColor("#FFFF00"));
                b2.setTextColor(Color.parseColor("#ffffff"));
                b3.setTextColor(Color.parseColor("#ffffff"));
                t1.setVisibility(View.VISIBLE);
                t2.setVisibility(View.INVISIBLE);
                t3.setVisibility(View.INVISIBLE);
                break;
            case 2:
                b1.setTextColor(Color.parseColor("#ffffff"));
                b2.setTextColor(Color.parseColor("#FFFF00"));
                b3.setTextColor(Color.parseColor("#ffffff"));
                t1.setVisibility(View.INVISIBLE);
                t2.setVisibility(View.VISIBLE);
                t3.setVisibility(View.INVISIBLE);
                break;
            case 3:
                b1.setTextColor(Color.parseColor("#ffffff"));
                b2.setTextColor(Color.parseColor("#ffffff"));
                b3.setTextColor(Color.parseColor("#FFFF00"));
                t1.setVisibility(View.INVISIBLE);
                t2.setVisibility(View.INVISIBLE);
                t3.setVisibility(View.VISIBLE);
                break;
        }
    }

    //lee una preferencia de usuario
    private int leerPreferencias() {
        SharedPreferences usuario = this.getSharedPreferences("detallesUsuario", MODE_PRIVATE);
        int devolver = usuario.getInt("esta", 1);
        return devolver;
    }

    //guarda preferencia de usuario
    private void guardarPreferencias() {
        SharedPreferences usuario = this.getSharedPreferences("detallesUsuario", MODE_PRIVATE);
        SharedPreferences.Editor edit = usuario.edit();
        if (musica) {
            edit.putInt("esta", 1);
        } else {
            edit.putInt("esta", 0);
        }
        edit.apply();
    }

    //muestra dialogo de retorno
    @Override
    public void onBackPressed() {
        ConfirmacionDialogFragment dialogo = new ConfirmacionDialogFragment();
        dialogo.show(getSupportFragmentManager(), "agConfirmacion");
    }
}
