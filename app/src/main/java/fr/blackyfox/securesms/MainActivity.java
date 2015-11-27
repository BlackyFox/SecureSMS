package fr.blackyfox.securesms;

import android.content.Context;
import android.content.Intent;
import android.provider.ContactsContract;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity {

    private EditText number;
    private EditText msg;
    static final int PICK_CONTACT=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        number = (EditText) findViewById(R.id.phone_num);
        msg = (EditText) findViewById(R.id.msg);

        number.requestFocus();
    }

    public void onSendSms(View view){
        if(!number.getText().toString().matches("")){
            if(!msg.getText().toString().matches("")){
                SmsSender_A sms = new SmsSender_A(number.getText().toString(),
                        msg.getText().toString(), getApplicationContext(),
                        getSystemService(Context.NOTIFICATION_SERVICE));
                sms.execute();
            }else{
                Toast.makeText(this.getApplicationContext(), "You cannot send an empty message!",
                        Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(this.getApplicationContext(), "You need to give us a correct phone " +
                    "number!", Toast.LENGTH_SHORT).show();
        }
    }

    public void onChooseContact(View view){
        Toast.makeText(this.getApplicationContext(), "Choose contact", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
        startActivityForResult(intent, PICK_CONTACT);


    }

    @Override
    public void onActivityResult(int reqCode, int resultCode, Intent data){
        super.onActivityResult(reqCode, resultCode, data);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
