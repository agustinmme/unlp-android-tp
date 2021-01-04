package androidunlp.tpgrupo7.cuadrocromatico;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidunlp.tpgrupo7.cuadrocromatico.Dialogs.ConfirmacionDialogFragmentGanador;
import androidunlp.tpgrupo7.cuadrocromatico.Dialogs.ConfirmacionDialogFragmentSalirPartido;

import com.example.juegoaentregar.R;


import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button aux;
    private TextView puntos;
    private Drawable[] arreglo, arregloFinal;//2 arreglos uno q se pinta y el otro para chequear el ganador
    private Drawable colorId;//aux q guarda el color
    private int swap, fondoactivado, tablero, difi, cambios;
    private int[] buttonIDs;//botones del xml
    private MediaPlayer sound, soundButton, ganador, swapsound;
    private boolean musica = true;


    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ganador = MediaPlayer.create(MainActivity.this, R.raw.ganador);     //musica de ganador
        soundButton = MediaPlayer.create(MainActivity.this, R.raw.boton);   //musica de boton de menu
        sound = MediaPlayer.create(MainActivity.this, R.raw.game2);         //musica del juego
        swapsound = MediaPlayer.create(MainActivity.this, R.raw.swap);      //musica swap
        sound.setLooping(true);                                                     //loop de la musica
        puntos = findViewById(R.id.puntos);                                         //cantidad de movimientos
        puntos.setText("0");
        swap = 0;                                                                   //para el primer intercambio de botones
        int musicaglobal = leerPreferencias();
        if (musicaglobal == 1) {
            musica = true;
        } else {
            musica = false;
        }
        if (fondoactivado != 10) {    //maneja densidad del fondo de pantalla
            LinearLayout fondo = (LinearLayout) findViewById(R.id.filtro);// Filtro gamer
            fondo.setVisibility(View.VISIBLE);
        }
        Bundle dato = getIntent().getExtras();
        if (dato != null) {
            int dificultad = dato.getInt("dato1"); // SACO EL INT
            int musicaa = dato.getInt("dato2");
            if (musicaa != 0) { //si musica es 0 inicia la musica
                sound = MediaPlayer.create(MainActivity.this, R.raw.game2);
                sound.setLooping(true);
                sound.start();
                sound.seekTo(musicaa);
            }
            difi = dificultad;
            switch (dificultad) {   //seteo tablero correspondiente
                case 1:
                    tablero = 25;
                    arreglo = new Drawable[tablero];
                    arregloFinal = new Drawable[tablero];
                    buttonIDs = new int[]{R.id.d14, R.id.d15, R.id.d16, R.id.d17, R.id.d18
                            , R.id.d21, R.id.d22, R.id.d23, R.id.d24, R.id.d25, R.id.d28, R.id.d29, R.id.d30, R.id.d31, R.id.d32, R.id.d35, R.id.d36, R.id.d37, R.id.d38, R.id.d39, R.id.d42,
                            R.id.d43, R.id.d44, R.id.d45, R.id.d46};
                    break;
                case 2:
                    tablero = 36;
                    arreglo = new Drawable[tablero];
                    arregloFinal = new Drawable[tablero];
                    buttonIDs = new int[]{R.id.d7, R.id.d8, R.id.d9, R.id.d10, R.id.d11, R.id.d12
                            , R.id.d14, R.id.d15, R.id.d16, R.id.d17, R.id.d18, R.id.d19, R.id.d21, R.id.d22, R.id.d23, R.id.d24, R.id.d25, R.id.d26, R.id.d28, R.id.d29, R.id.d30,
                            R.id.d31, R.id.d32, R.id.d33, R.id.d35, R.id.d36, R.id.d37, R.id.d38, R.id.d39, R.id.d40, R.id.d42, R.id.d43, R.id.d44, R.id.d45, R.id.d46, R.id.d47};

                    break;
                case 3:
                    tablero = 49;
                    arreglo = new Drawable[tablero];
                    arregloFinal = new Drawable[tablero];
                    buttonIDs = new int[]{R.id.d0, R.id.d1, R.id.d2, R.id.d3, R.id.d4, R.id.d5, R.id.d6, R.id.d7, R.id.d8, R.id.d9, R.id.d10, R.id.d11, R.id.d12,
                            R.id.d13, R.id.d14, R.id.d15, R.id.d16, R.id.d17, R.id.d18, R.id.d19, R.id.d20, R.id.d21, R.id.d22, R.id.d23, R.id.d24, R.id.d25, R.id.d26, R.id.d27, R.id.d28, R.id.d29, R.id.d30,
                            R.id.d31, R.id.d32, R.id.d33, R.id.d34, R.id.d35, R.id.d36, R.id.d37, R.id.d38, R.id.d39, R.id.d40, R.id.d41, R.id.d42, R.id.d43, R.id.d44, R.id.d45, R.id.d46, R.id.d47, R.id.d48};
            }
        }
        for (int i = 0; i < buttonIDs.length; i++) { //hago visible el tablero
            Button b = (Button) findViewById(buttonIDs[i]);
            b.setVisibility(View.VISIBLE);
            b.setOnClickListener(this);
            arreglo[i] = b.getBackground();

        }
        random();

    }

    //crea menu
    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        MenuInflater mi = getMenuInflater();
        mi.inflate(R.menu.menu_principal, menu);

        return true;
    }

    //modifica el menu, cambia los iconos segun la preferencia de musica
    @Override
    public boolean onPrepareOptionsMenu(android.view.Menu menu) {
        if (musica) {
            menu.findItem(R.id.volumen).setIcon(R.drawable.ic_volume);
        } else {
            menu.findItem(R.id.volumen).setIcon(R.drawable.ic_volume_off);
        }
        return super.onPrepareOptionsMenu(menu);
    }


    //random del tablero
    private void random() {

        ArrayList<Integer> numbers = new ArrayList<Integer>();
        Random randomGenerator = new Random();
        int actual = 0;
        while (numbers.size() < tablero) {
            int random = randomGenerator.nextInt(tablero); // Genero piosicion randoms 0 - 24 0 - 35 0-48
            if (!numbers.contains(random)) {    //chequea q no se repita la miusma posicion
                numbers.add(random);            //lo agrega a la estructura auxiliar
                Button btn = ((Button) findViewById(buttonIDs[actual]));    //toma el boton correspondiente
                btn.setBackground(arreglo[random]);                         //setea el color del boton
                actual++;
            }
        }
    }

    //realiza el cambio de colores
    @Override
    public void onClick(View v) {
        if (swap == 0) {  //si es el primer click guyardo el color
            aux = (Button) v;
            colorId = v.getBackground();
            swap = 1;
        } else {        //si es el segundo click cambio los colores
            if (!v.getBackground().equals(aux.getBackground())) {
                cambios++;      //aumento los cambios
                puntos.setText("" + cambios);
                if (musica) {
                    swapsound.seekTo(0);
                    swapsound.start();
                }
            }
            aux.setBackground(v.getBackground());
            v.setBackground(colorId);
            swap = 0;

            onChequearGanador();


        }
    }

    //compara los 2 arreglos
    public void onChequearGanador() {
        int actual = 0;
        for (int i = 0; i < arregloFinal.length; i++) {
            Button btn = ((Button) findViewById(buttonIDs[actual]));
            arregloFinal[i] = btn.getBackground();
            actual++;
        }
        int contador = 0;
        boolean condiCorte = true;
        while (condiCorte && contador < tablero) {
            if (arregloFinal[contador].equals(arreglo[contador])) { //compara ambos vectores
                contador++;
            } else {
                condiCorte = false;
            }
        }
        if (condiCorte) {       //si es ganador
            sound.setVolume(0.3f, 0.3f);
            if (musica) {  //baja la musica y pone la de ganador
                ganador.seekTo(0);
                ganador.start();
            }
            if (leerPreferenciasUltimas(difi) > cambios) {      //toma el ultimo de la tabla segun su dificultad y lo compara con los cambios
                ConfirmacionDialogFragmentGanador dialogo = new ConfirmacionDialogFragmentGanador();    //si entraste en el top se llama al dialogo de confirmacion
                dialogo.setValue(difi, cambios, sound.getCurrentPosition());
                dialogo.show(getSupportFragmentManager(), "agConfirmacion");
            } else {                                             //si no gano se muestra cartel de ganar y puntaje hecho + (cartel es un layout con 2 botones) reset y seleccionar lvl
                LinearLayout cartel = (LinearLayout) findViewById(R.id.cartel);
                cartel.setVisibility(View.VISIBLE);
                TextView punto = (TextView) findViewById(R.id.puntaje);
                punto.setText("" + cambios);

            }
            for (int i = 0; i < buttonIDs.length; i++) {            //invisibiliza el tablero
                Button b = (Button) findViewById(buttonIDs[i]);
                b.setVisibility(View.INVISIBLE);
                TextView inter = (TextView) findViewById(R.id.inter);
                inter.setVisibility(View.INVISIBLE);
                puntos.setVisibility(View.INVISIBLE);
            }
        }

    }

    public void soundboton() {
        MediaPlayer soundboton = MediaPlayer.create(MainActivity.this, R.raw.boton);
        if (musica) {
            soundButton.seekTo(0);
            soundButton.start();
        }


    }

    //on click del cartel, resetea variables del juego y activa el random
    public void resetearlvl(View v) {
        sound.setVolume(1f, 1f);
        soundboton();
        for (int i = 0; i < buttonIDs.length; i++) {
            Button b = (Button) findViewById(buttonIDs[i]);
            b.setVisibility(View.VISIBLE);
        }
        random();
        LinearLayout cartel = (LinearLayout) findViewById(R.id.cartel);
        cartel.setVisibility(View.INVISIBLE);
        cambios = 0;
        puntos.setVisibility(View.VISIBLE);
        TextView inter = (TextView) findViewById(R.id.inter);
        inter.setVisibility(View.VISIBLE);
        puntos.setText("" + cambios);
    }

    //on click de cartel mata actividad actual e inicia menu
    public void selecionarNivel(View v) {
        soundboton();
        Intent i = new Intent(this, Menu.class);
        startActivity(i);
        finish();

    }

    //inicia ayuda
    public void ayuda() {
        soundboton();
        Intent i = new Intent(this, Ayuda.class);
        i.putExtra("dato1", difi);
        i.putExtra("dato3", 10);
        if (musica) {
            i.putExtra("dato2", sound.getCurrentPosition());
        } else {
            i.putExtra("dato2", 0);
        }
        startActivity(i);
    }

    //flitro del fondo
    public void fondo() {
        soundboton();
        LinearLayout fondo = (LinearLayout) findViewById(R.id.filtro);// Filtro gamer
        fondo.setVisibility(View.VISIBLE);
        switch (fondoactivado) {
            case 1:
                fondo.setBackgroundColor(Color.parseColor("#60000000"));
                fondoactivado++;
                break;
            case 2:
                fondo.setBackgroundColor(Color.parseColor("#70000000"));
                fondoactivado++;
                break;
            case 3:
                fondo.setBackgroundColor(Color.parseColor("#80000000"));
                fondoactivado++;
                break;
            case 4:
                fondo.setBackgroundColor(Color.parseColor("#90000000"));
                fondoactivado++;
                break;
            default:
                fondoactivado = 1;
                fondo.setBackgroundColor(Color.parseColor("#00000000"));
        }
        guardarPreferencias();


    }

    //manejo de musica
    public void stopMusic(MenuItem item) {
        soundboton();
        if (sound.isPlaying()) {
            sound.pause();
            musica = false;
            item.setIcon(ContextCompat.getDrawable(this, R.drawable.ic_volume_off));
        } else {
            musica = true;
            sound.start();
            item.setIcon(ContextCompat.getDrawable(this, R.drawable.ic_volume));

        }

        guardarPreferencias();
    }

    //seleccion en menu
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.ayuda) {
            ayuda();
        }
        if (item.getItemId() == R.id.fondo) {
            fondo();
        }
        if (item.getItemId() == R.id.volumen) {
            stopMusic(item);
        }
        if (item.getItemId() == R.id.salir) {
            salir();
        }
        return true;
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
                    ganador.release();
                }
            }
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean("mutear", musica);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        musica = savedInstanceState.getBoolean("mutear");

    }

    //manejo de preferrencias de usuario: musica y filtro
    private int leerPreferencias() {
        SharedPreferences usuario = this.getSharedPreferences("detallesUsuario", MODE_PRIVATE);
        int devolver = usuario.getInt("esta", 1);
        fondoactivado = usuario.getInt("filtro", 10);
        return devolver;
    }

    private void guardarPreferencias() {
        SharedPreferences usuario = this.getSharedPreferences("detallesUsuario", MODE_PRIVATE);
        SharedPreferences.Editor edit = usuario.edit();
        if (musica) {
            edit.putInt("esta", 1);
        } else {
            edit.putInt("esta", 0);
        }
        edit.putInt("filtro", fondoactivado);
        edit.apply();
    }

    //toma el ultimodel top segun dificultad (si no hay nadie en la posicion devuelve por defecto 9999)
    private int leerPreferenciasUltimas(int difi) {
        SharedPreferences prefs = getSharedPreferences("top5", MODE_PRIVATE);
        switch (difi) {
            case 1:
                int top1 = prefs.getInt("rank1", 9999);//"No name defined" is the default value.
                return top1;
            case 2:
                int top2 = prefs.getInt("rank2", 9999); //0 is the default value.
                return top2;
            case 3:
                int top3 = prefs.getInt("rank3", 9999); //0 is the default value.;
                return top3;
        }
        return 0;
    }

    //activa dialogo de salida
    public void salir() {
        ConfirmacionDialogFragmentSalirPartido dialogo = new ConfirmacionDialogFragmentSalirPartido();
        dialogo.show(getSupportFragmentManager(), "agConfirmacion");
    }

    //boton de retorno
    @Override
    public void onBackPressed() {
        salir();
    }
}
