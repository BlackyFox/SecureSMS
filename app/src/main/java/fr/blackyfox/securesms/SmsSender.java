package fr.blackyfox.securesms;

import android.content.Context;
import android.content.Intent;
import android.telephony.SmsManager;
import android.widget.Toast;

import java.util.Arrays;

/**
 * Created by Antoine on 27/07/2015.
 */
public class SmsSender {

    private String destinataire; //Numéro du destinataire
    private String msg; //Contenu du message
    private Context c; //Contexte de l'application pour affichage des Toast

    /**
     * Constructeur récupérant les variables pour l'envoie du sms
     * @param destinataire //numéro du destinataire
     * @param msg //contenu du message
     * @param c //contexte de l'application
     */
    public SmsSender(String destinataire, String msg, Context c) {
        this.destinataire = destinataire;
        this.msg = msg;
        this.c = c;
    }

    /**
     * Méthode permettant d'utiliser l'API SmsManager pour envoyer des SMS
     *
     * TODO : A mettre dans une asyncTask !
     */
    public void send() {
        Toast.makeText(c, "Starting to send the message!", Toast.LENGTH_SHORT).show();
        AESencrypt aes = new AESencrypt(c, this.msg);
        aes.cypher();
        if(aes.getEnc_msg() != null) {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(this.destinataire, null, aes.getEnc_msg(),
                    null, null);
            Toast.makeText(c, "Message sent!", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(c, "Ecryption error!!! Contact the dev quickly!", Toast.LENGTH_SHORT)
                    .show();
        }
    }

}
