package androidunlp.tpgrupo7.cuadrocromatico.Dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;

import com.example.juegoaentregar.R;

public class ConfirmacionDialogFragment extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(),AlertDialog.THEME_DEVICE_DEFAULT_DARK);
        builder.setMessage(R.string.confirmacionDialog)
                .setPositiveButton(R.string.aceptarDialog, new DialogInterface.OnClickListener() {
                                                    public void onClick(DialogInterface dialog, int id) {
                                                        getActivity().finish(); }
                                                })
                .setNegativeButton(R.string.cancelarDialog, new DialogInterface.OnClickListener() {
                                                    public void onClick(DialogInterface dialog, int id) { }
                                                });
        return
                builder.create();
    }

}

