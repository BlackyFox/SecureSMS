package fr.blackyfox.securesms;

import android.content.Context;

import java.nio.charset.Charset;
import java.security.AlgorithmParameters;
import java.security.spec.KeySpec;
import java.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by Antoine on 27/07/2015.
 */
public class AESencrypt {

    private Context c;
    private String msg;
    private byte[] enc_msg = null;
    private String password;
    private String salt;

    public AESencrypt(Context c, String msg) {
        this.c = c;
        this.msg = msg;
        this.password = "m0td3p@$$3@ch@ng3r";
        this.salt = "$31";
    }

    public void cypher(){
        try {
            /*Key derivation*/
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            KeySpec spec = new PBEKeySpec(password.toCharArray(), salt.getBytes(
                    Charset.defaultCharset()), 65536, 256);
            SecretKey tmp = factory.generateSecret(spec);
            SecretKey secret = new SecretKeySpec(tmp.getEncoded(), "AES");
            /*Message encryption*/
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secret);
            AlgorithmParameters parameters = cipher.getParameters();
            byte[] iv = parameters.getParameterSpec(IvParameterSpec.class).getIV();
            enc_msg = cipher.doFinal(this.msg.getBytes(Charset.defaultCharset()));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public String getEnc_msg() {
        if(enc_msg != null){
            return "SecureSMS message - AES256\n"+ Arrays.toString(enc_msg);
        }else{
            return null;
        }
    }
}
