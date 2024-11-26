package com.univerisity.Common.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

public class Message {

    public static String Success;
    public static String Error;

    public static void showDialog(Context context, String title, String message, Runnable onConfirm) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title); // Título del diálogo
        builder.setMessage(message); // Mensaje del diálogo

        // Botón "Aceptar"
        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Ejecutar acción al confirmar
                if (onConfirm != null) {
                    onConfirm.run();
                }
                dialog.dismiss();
            }
        });

        // Botón "Cancelar"
        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Cerrar el diálogo
                dialog.dismiss();
            }
        });

        // Mostrar el diálogo
        builder.show();
    }
}
