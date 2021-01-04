package androidunlp.tpgrupo7.cuadrocromatico;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.juegoaentregar.R;

import java.util.concurrent.ThreadLocalRandom;

public class Creditos extends AppCompatActivity {
    private MediaPlayer sound;
    private boolean musica;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creditos);
        Bundle dato = getIntent().getExtras();
        if (dato != null)//si se reciben datos
        {
            int musicaa = dato.getInt("dato2");
            if (musicaa != 0) { //si musica es 0 inicia la musica
                sound = MediaPlayer.create(Creditos.this, R.raw.game2);
                sound.setLooping(true);
                sound.start();
                sound.setVolume(0.3f, 0.3f);
                sound.seekTo(musicaa);
            }
        }


        int musicaglobal = leerPreferencias();
        if (musicaglobal == 1) {
            musica = true;
        } else {
            musica = false;
        }
        randomTpose();
    }

    private void randomTpose() {
        int random = ThreadLocalRandom.current().nextInt(0, 2);
        TextView t = (TextView) findViewById(R.id.quintoCreditos);
        TextView t2 = (TextView) findViewById(R.id.sextoCreditos);
        if (random == 1) {
            t.setText("Joaquin Grosso");
            t2.setText("Agustin Mansilla");
        } else {
            t.setText("Agustin Mansilla");
            t2.setText("Joaquin Grosso");
        }
    }

    public void OnClickGithub(View view) {
        String url = "https://github.com/agustinmme/Chroma-Chart";
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
    }

    public void OnClickZapsplat(View view) {
        String url = "https://www.zapsplat.com/";
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
    }

    public void Atras(View view) {
        Intent i = new Intent(this, Menu.class);
        if (musica) {
            i.putExtra("dato2", sound.getCurrentPosition());
        } else {
            i.putExtra("dato2", 0);
        }
        startActivity(i);
        finish();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (sound != null) {
            if (!sound.isPlaying()) {
                if (musica) {
                    new Handler().postDelayed(new Runnable() {     //Desbugear el sonido
                        @Override
                        public void run() {
                            sound.start();
                        }
                    }, 600);
                }
            }
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (musica) {
            if (sound.isPlaying()) {
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
                }
            }
        }
    }

    private int leerPreferencias() {
        SharedPreferences usuario = this.getSharedPreferences("detallesUsuario", MODE_PRIVATE);
        int devolver = usuario.getInt("esta", 1);
        return devolver;
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(this, Menu.class);
        if (musica) {
            i.putExtra("dato2", sound.getCurrentPosition());
        } else {
            i.putExtra("dato2", 0);
        }
        startActivity(i);
        finish();
    }
}