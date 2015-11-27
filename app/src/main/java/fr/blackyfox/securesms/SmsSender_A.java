package fr.blackyfox.securesms;

import android.app.NotificationManager;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.app.NotificationCompat;
import android.telephony.SmsManager;
import android.widget.Toast;

/**
 * Created by BlackyFox on 22/11/2015.
 */

public class SmsSender_A extends AsyncTask<Void, Integer, Integer> {

    private String destinataire; //Num�ro du destinataire
    private String msg; //Contenu du message
    private Context c; //Contexte de l'application pour affichage des Toast
    private NotificationManager mNotificationManager;

    public SmsSender_A(String destinataire, String msg, Context c, Object o) {
        this.destinataire = destinataire;
        this.msg = msg;
        this.c = c;
        this.mNotificationManager = (NotificationManager) o;
    }


    @Override
    protected void onPreExecute(){
        super.onPreExecute();
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(c);
        mBuilder.setSmallIcon(R.mipmap.ic_process);
        mBuilder.setContentTitle("SecureSMS Sending...");
        mBuilder.setContentText("SMS to " + this.destinataire + " is being cyphered and send");

        // notificationID allows you to update the notification later on.
        this.mNotificationManager.notify(13, mBuilder.build());

        //Toast.makeText(c, "Cyphering message to: "+destinataire, Toast.LENGTH_LONG).show();
        //Toast.makeText(c, "Cyphering the message!", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected Integer doInBackground(Void... arg0) {
        //Toast.makeText(c, "Starting to send the message!", Toast.LENGTH_SHORT).show();
        AESencrypt aes = new AESencrypt(c, this.msg);
        aes.cypher();
        if(aes.getEnc_msg() != null) {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(this.destinataire, null, aes.getEnc_msg(),
                    null, null);
            return 0;
        }else{
            return 1;
        }
    }

    @Override
    protected void onPostExecute(Integer result) {
        if (result == 0) {
            //Toast.makeText(c, "Message sent!", Toast.LENGTH_LONG).show();
            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(c);
            mBuilder.setSmallIcon(R.mipmap.ic_done);
            mBuilder.setContentTitle("SecureSMS succes");
            mBuilder.setContentText("SMS sent correctly to " + this.destinataire);

            // notificationID allows you to update the notification later on.
            this.mNotificationManager.notify(13, mBuilder.build());
        }else {
            //Toast.makeText(c, "Ecryption error!!! Contact the dev quickly!", Toast.LENGTH_SHORT).show();
            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(c);
            mBuilder.setSmallIcon(R.mipmap.ic_error);
            mBuilder.setContentTitle("SecureSMS error");
            mBuilder.setContentText("Sending SMS to " + this.destinataire + " failed!\n " +
                    "Try again or contact the dev!");

            // notificationID allows you to update the notification later on.
            this.mNotificationManager.notify(13, mBuilder.build());
        }
    }
}