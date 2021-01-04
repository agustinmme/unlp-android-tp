package androidunlp.tpgrupo7.cuadrocromatico.Dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;

import com.example.juegoaentregar.R;
import androidunlp.tpgrupo7.cuadrocromatico.Menu;

public class ConfirmacionDialogFragmentSalirRanking extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(),AlertDialog.THEME_DEVICE_DEFAULT_DARK);
        builder.setMessage(R.string.SalidaDialogrank)
                .setPositiveButton(R.string.salirdDialog, new DialogInterface.OnClickListener() {
                                                    public void onClick(DialogInterface dialog, int id) {
                                                        getActivity().finish();
                                                         }
                                                })
                .setNegativeButton(R.string.cancelarDialog, new DialogInterface.OnClickListener() {
                                                    public void onClick(DialogInterface dialog, int id) {
                                                    }
                                                });
        builder.setNeutralButton(R.string.inicio2,
                new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int id)
                    {
                        getActivity().startActivity(new Intent(getActivity(), Menu.class));
                        getActivity().finish();

                    }
                });
        return
                builder.create();
    }

}

