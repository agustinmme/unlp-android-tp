package androidunlp.tpgrupo7.cuadrocromatico.Dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;

import androidx.fragment.app.DialogFragment;

import com.example.juegoaentregar.R;
import androidunlp.tpgrupo7.cuadrocromatico.Ranking;
import androidunlp.tpgrupo7.cuadrocromatico.Ayuda;
import androidunlp.tpgrupo7.cuadrocromatico.Menu;

public class ConfirmacionDialogFragmentGanador extends DialogFragment {
    int dificultad;
    int contador;
    int debugmusica;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(),AlertDialog.THEME_DEVICE_DEFAULT_DARK);
        final EditText input = new EditText(getActivity());
        builder.setTitle(R.string.ganar);
        builder.setView(input);
        input.setHint(R.string.nombrejugador);
        input.setHintTextColor(getResources().getColor(R.color.md_cyan_100));
        input.setTextColor(getResources().getColor(R.color.md_cyan_200));
        builder.setMessage(R.string.puntaje)
                .setPositiveButton(R.string.volverajugar, new DialogInterface.OnClickListener() {
                                                    public void onClick(DialogInterface dialog, int id) {
                                                        getActivity().startActivity(new Intent(getActivity(), Ayuda.class)
                                                                .putExtra("dato1",dificultad)
                                                                .putExtra("dato2",debugmusica)
                                                                .putExtra("dato3",1));
                                                        getActivity().finish();


                                                    }
                                                })
                .setNegativeButton(R.string.cambiardificul, new DialogInterface.OnClickListener() {
                                                    public void onClick(DialogInterface dialog, int id) {

                                                        getActivity().startActivity(new Intent(getActivity(), Menu.class).putExtra("dato2",1));

                                                        getActivity().finish();
                                                    }
                                                });
        builder.setNeutralButton(R.string.ranking,
                new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int id)
                    {
                        getActivity().startActivity(new Intent(getActivity(), Ranking.class)
                                .putExtra("dato1",0)
                                .putExtra("dato2",contador)
                                .putExtra("dato3",dificultad)
                                .putExtra("dato4",input.getText().toString()));
                        getActivity().finish();

                    }
                });
        return
                builder.create();
    }
    //metodo llamado en el main, setea los valores a usar
    public void setValue(int dificultad, int contador,int debugmusica) {
        this.dificultad = dificultad;
        this.contador = contador;
        this.debugmusica = debugmusica;
    }

}

