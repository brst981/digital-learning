package app.com.digitallearning.Utill;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.Gravity;
import android.widget.TextView;

/**
 *
 * @author Shalvi Sharma
 *
 */
public class LogMessage {

    public static void showDialog(Context context, String title,
                                  String Message, String text) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
        alertDialog.setTitle(title).setMessage(Message).setCancelable(false)
                .setPositiveButton(text, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
                        dialog.dismiss();

                    }
                });

        AlertDialog dialog = alertDialog.create();
        dialog.show();
        TextView messageText = (TextView) dialog
                .findViewById(android.R.id.message);
        messageText.setGravity(Gravity.CENTER);

    }

}
