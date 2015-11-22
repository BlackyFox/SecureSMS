package fr.blackyfox.securesms;

import android.content.Context;
import android.os.AsyncTask;
import android.telephony.SmsManager;
import android.widget.Toast;

/**
 * Created by BlackyFox on 22/11/2015.
 */

public class SmsSender_A extends AsyncTask<Void, Integer, Integer> {

    private String destinataire; //Num�ro du destinataire
    private String msg; //Contenu du message
    private Context c; //Contexte de l'application pour affichage des Toast

    public SmsSender_A(String destinataire, String msg, Context c) {
        this.destinataire = destinataire;
        this.msg = msg;
        this.c = c;
    }

    public void send(Context cc) {
        Toast.makeText(cc, "Starting to send the message!", Toast.LENGTH_SHORT).show();
        AESencrypt aes = new AESencrypt(c, this.msg);
        aes.cypher();
        if(aes.getEnc_msg() != null) {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(this.destinataire, null, aes.getEnc_msg(),
                    null, null);
            Toast.makeText(cc, "Message sent!", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(cc, "Ecryption error!!! Contact the dev quickly!", Toast.LENGTH_SHORT)
                    .show();
        }
    }


    @Override
    protected void onPreExecute(){
        super.onPreExecute();
        Toast.makeText(c, "Cyphering the message!", Toast.LENGTH_SHORT).show();
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
            //Toast.makeText(c, "Message sent!", Toast.LENGTH_SHORT).show();
        }else{
            return 1;
            //Toast.makeText(c, "Ecryption error!!! Contact the dev quickly!", Toast.LENGTH_SHORT)
                    //.show();;
        }
        //return null;
    }

    @Override
    protected void onPostExecute(Integer result) {
        if (result == 0)
            Toast.makeText(c, "Message sent!", Toast.LENGTH_LONG).show();
        else
            Toast.makeText(c, "Ecryption error!!! Contact the dev quickly!", Toast.LENGTH_SHORT).show();
    }
}