package androidunlp.tpgrupo7.cuadrocromatico;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import androidunlp.tpgrupo7.cuadrocromatico.Dialogs.ConfirmacionDialogFragmentSalirRanking;
import androidunlp.tpgrupo7.cuadrocromatico.Objects.Jugador;
import androidunlp.tpgrupo7.cuadrocromatico.Objects.Nivel;

import com.example.juegoaentregar.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Ranking extends AppCompatActivity {
    private Nivel lvl[] = new Nivel[3];
    private String nombre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking);
        Bundle dato = getIntent().getExtras();

        if (dato != null) {
            int donde = dato.getInt("dato1");
            if (donde == 0) {//llamado para agregar un dato al ranking
                int cambios = dato.getInt("dato2");
                int difi = dato.getInt("dato3");
                nombre = dato.getString("dato4");
                try {
                    ranking(cambios, difi);
                    mostarRanking(desarmarJson(leerPreferencias()));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {//solo muestra
                try {
                    mostarRanking(desarmarJson(leerPreferencias()));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        guardarUltimosRanking();
    }

    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        MenuInflater mi = getMenuInflater();
        mi.inflate(R.menu.menu_rank, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.home) { //vuelve al menu
            Intent i = new Intent(this, Menu.class);
            startActivity(i);
            finish();
        }
        if (item.getItemId() == R.id.share) {  //comparte el ranking
            int[] JugadoresIDs = new int[]{R.id.rn0, R.id.rn1, R.id.rn2, R.id.rn3, R.id.rn4, R.id.rn5, R.id.rn6, R.id.rn7, R.id.rn8, R.id.rn9, R.id.rn10, R.id.rn11, R.id.rn12, R.id.rn13, R.id.rn14};
            int[] InterambiosIDs = new int[]{R.id.ri0, R.id.ri1, R.id.ri2, R.id.ri3, R.id.ri4, R.id.ri5, R.id.ri6, R.id.ri7, R.id.ri8, R.id.ri9, R.id.ri10, R.id.ri11, R.id.ri12, R.id.ri13, R.id.ri14};
            int contadordebug = 0;
            String aCompartir = "";
            String DifiNivel = "";
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 5; j++) {
                    TextView t = (TextView) findViewById(JugadoresIDs[contadordebug]);//Varible porque al usar i o j se generaba un bug...
                    TextView s = (TextView) findViewById(InterambiosIDs[contadordebug]);
                    if (i == 0) {
                        DifiNivel = "FACIL";
                    }
                    if (i == 1) {
                        DifiNivel = "MEDIO";
                    }
                    if (i == 2) {
                        DifiNivel = "DIFICIL";
                    }
                    aCompartir = aCompartir + "Nivel" + "   " + DifiNivel + "   " + t.getText() + "   " + s.getText() + "\n";// \n salto de linea

                    contadordebug++;
                }
            }
            //intent implicito
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, aCompartir);
            sendIntent.setType("text/plain");
            startActivity(sendIntent);
        }
        return true;
    }

    private String generaJson() throws JSONException {
        JSONObject rankinglevelJson = new JSONObject();
        for (int j = 0; j < 3; j++) {
            rankinglevelJson.put("nombre" + j, "ranking" + j);
            JSONArray JugadoresJson = new JSONArray();
            for (int i = 0; i < lvl[j].player.length; i++) {
                JSONObject jugadorJson = new JSONObject();
                jugadorJson.put("nombre", lvl[j].player[i].getName());
                jugadorJson.put("score", lvl[j].player[i].getScore());
                JugadoresJson.put(i, jugadorJson);
            }
            rankinglevelJson.put("integrantes" + j, JugadoresJson);
        }
        String cadenaJson = rankinglevelJson.toString();
        return cadenaJson;
    }

    private Nivel[] desarmarJson(String algoJson) throws JSONException {
        Nivel nivel[] = new Nivel[3];
        JSONObject rankinglevelJson = new JSONObject(algoJson);
        JSONArray jugadoresJson;
        for (int j = 0; j < nivel.length; j++) {
            jugadoresJson = rankinglevelJson.getJSONArray("integrantes" + j);
            nivel[j] = new Nivel();
            nivel[j].nombre = rankinglevelJson.getString("nombre" + j);
            nivel[j].player = new Jugador[jugadoresJson.length()];
            for (int i = 0; i < jugadoresJson.length(); i++) {
                JSONObject jugadorJson = jugadoresJson.getJSONObject(i);
                Jugador jugador = new Jugador();
                jugador.Name = jugadorJson.getString("nombre");
                jugador.Score = jugadorJson.getInt("score");
                nivel[j].player[i] = jugador;
            }
        }
        return nivel;
    }

    //guarda y lee el json
    private void guardarPreferencias() throws JSONException {
        SharedPreferences preferencias = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = preferencias.edit();
        String json = generaJson();
        editor.putString("ranking", json);
        editor.commit();
    }

    private String leerPreferencias() {
        SharedPreferences preferencias = getPreferences(MODE_PRIVATE);
        String topjuego = preferencias.getString("ranking", "0");
        return topjuego;
    }


    private void ranking(int cambios, int difi) throws JSONException {
        lvl[0] = new Nivel();
        lvl[1] = new Nivel();
        lvl[2] = new Nivel();
        String rankingJson = leerPreferencias();
        if (rankingJson.equals("0")) { // si el json no se creo nunca (0 valor por def) inicializo valores de ñranking
            for (int j = 0; j < lvl.length; j++) {
                lvl[j].setNombre("ranking" + j);
                lvl[j].player = new Jugador[5];
                for (int i = 0; i < lvl[j].player.length; i++) {
                    Jugador integrante = new Jugador();
                    integrante.Name = getString(R.string.noplayer);
                    integrante.Score = 9999;
                    lvl[j].player[i] = integrante;
                }
            }
            Jugador integrante = new Jugador(); // cargo e rpimer jugador
            integrante.Name = "" + nombre;
            integrante.Score = cambios;
            lvl[difi - 1].player[0] = integrante;
        } else {//corrimiento en el ranking al agregar un nuevo jugador
            lvl = desarmarJson(rankingJson);
            if (lvl[difi - 1].getPlayer()[0].getScore() > cambios) {
                lvl[difi - 1].getPlayer()[4].setScore(lvl[difi - 1].getPlayer()[3].getScore());
                lvl[difi - 1].getPlayer()[3].setScore(lvl[difi - 1].getPlayer()[2].getScore());
                lvl[difi - 1].getPlayer()[2].setScore(lvl[difi - 1].getPlayer()[1].getScore());
                lvl[difi - 1].getPlayer()[1].setScore(lvl[difi - 1].getPlayer()[0].getScore());

                lvl[difi - 1].getPlayer()[4].setName(lvl[difi - 1].getPlayer()[3].getName());
                lvl[difi - 1].getPlayer()[3].setName(lvl[difi - 1].getPlayer()[2].getName());
                lvl[difi - 1].getPlayer()[2].setName(lvl[difi - 1].getPlayer()[1].getName());
                lvl[difi - 1].getPlayer()[1].setName(lvl[difi - 1].getPlayer()[0].getName());

                lvl[difi - 1].getPlayer()[0].setName("" + nombre);
                lvl[difi - 1].getPlayer()[0].setScore(cambios);
            } else {
                if (lvl[difi - 1].getPlayer()[1].getScore() > cambios) {
                    lvl[difi - 1].getPlayer()[4].setScore(lvl[difi - 1].getPlayer()[3].getScore());
                    lvl[difi - 1].getPlayer()[3].setScore(lvl[difi - 1].getPlayer()[2].getScore());
                    lvl[difi - 1].getPlayer()[2].setScore(lvl[difi - 1].getPlayer()[1].getScore());

                    lvl[difi - 1].getPlayer()[4].setName(lvl[difi - 1].getPlayer()[3].getName());
                    lvl[difi - 1].getPlayer()[3].setName(lvl[difi - 1].getPlayer()[2].getName());
                    lvl[difi - 1].getPlayer()[2].setName(lvl[difi - 1].getPlayer()[1].getName());

                    lvl[difi - 1].getPlayer()[1].setScore(cambios);
                    lvl[difi - 1].getPlayer()[1].setName("" + nombre);
                } else {
                    if (lvl[difi - 1].getPlayer()[2].getScore() > cambios) {
                        lvl[difi - 1].getPlayer()[4].setScore(lvl[difi - 1].getPlayer()[3].getScore());
                        lvl[difi - 1].getPlayer()[3].setScore(lvl[difi - 1].getPlayer()[2].getScore());

                        lvl[difi - 1].getPlayer()[4].setName(lvl[difi - 1].getPlayer()[3].getName());
                        lvl[difi - 1].getPlayer()[3].setName(lvl[difi - 1].getPlayer()[2].getName());

                        lvl[difi - 1].getPlayer()[2].setScore(cambios);
                        lvl[difi - 1].getPlayer()[2].setName("" + nombre);
                    } else {
                        if (lvl[difi - 1].getPlayer()[3].getScore() > cambios) {
                            lvl[difi - 1].getPlayer()[4].setScore(lvl[difi - 1].getPlayer()[3].getScore());
                            ;

                            lvl[difi - 1].getPlayer()[4].setName(lvl[difi - 1].getPlayer()[3].getName());


                            lvl[difi - 1].getPlayer()[3].setScore(cambios);
                            lvl[difi - 1].getPlayer()[3].setName("" + nombre);
                        } else {
                            if (lvl[difi - 1].getPlayer()[4].getScore() > cambios) {
                                lvl[difi - 1].getPlayer()[4].setScore(cambios);
                                lvl[difi - 1].getPlayer()[4].setName("" + nombre);
                            }
                        }
                    }
                }
            }
        }
        guardarPreferencias();
    }

    //utilizado para ver q dialogo mostrar. Toma los top5 de cada ranking
    private void guardarUltimosRanking() {
        SharedPreferences.Editor editor = getSharedPreferences("top5", MODE_PRIVATE).edit();
        TextView textView = (TextView) findViewById(R.id.ri4);
        editor.putInt("rank1", Integer.parseInt(textView.getText().toString()));
        TextView textView2 = (TextView) findViewById(R.id.ri9);
        editor.putInt("rank2", Integer.parseInt(textView2.getText().toString()));
        TextView textView3 = (TextView) findViewById(R.id.ri14);
        editor.putInt("rank3", Integer.parseInt(textView3.getText().toString()));
        editor.apply();
    }

    //setea el xml
    private void mostarRanking(Nivel[] RankingTotal) {
        int[] JugadoresIDs = new int[]{R.id.rn0, R.id.rn1, R.id.rn2, R.id.rn3, R.id.rn4, R.id.rn5, R.id.rn6, R.id.rn7, R.id.rn8, R.id.rn9, R.id.rn10, R.id.rn11, R.id.rn12, R.id.rn13, R.id.rn14};
        int[] InterambiosIDs = new int[]{R.id.ri0, R.id.ri1, R.id.ri2, R.id.ri3, R.id.ri4, R.id.ri5, R.id.ri6, R.id.ri7, R.id.ri8, R.id.ri9, R.id.ri10, R.id.ri11, R.id.ri12, R.id.ri13, R.id.ri14};
        int contadordebug = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 5; j++) {
                TextView t = (TextView) findViewById(JugadoresIDs[contadordebug]);
                TextView s = (TextView) findViewById(InterambiosIDs[contadordebug]);
                t.setText(RankingTotal[i].player[j].getName());
                s.setText("" + RankingTotal[i].player[j].getScore());
                contadordebug++;
            }
        }
    }

    //activa el dialogo de regreso
    @Override
    public void onBackPressed() {
        ConfirmacionDialogFragmentSalirRanking nuevo = new ConfirmacionDialogFragmentSalirRanking();
        nuevo.show(getSupportFragmentManager(), "agConfirmacion");
    }
}
