package fr.blackyfox.securesms;

/**
 * Created by Antoine on 27/07/2015.
 */
public class SmsSender {

    private String destinataire;
    private String msg;

    public SmsSender(String destinataire, String msg) {
        this.destinataire = destinataire;
        this.msg = msg;
    }
}
