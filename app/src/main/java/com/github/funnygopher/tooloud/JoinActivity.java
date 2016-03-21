package com.github.funnygopher.tooloud;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class JoinActivity extends AppCompatActivity implements AnybodyHomeable{

    private SessionManager session;
    private String address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);

        session = new SessionManager(getApplicationContext());

        Intent intent = getIntent();
        if(intent.getDataString() != null) {
            String dataString = intent.getDataString();
            address = dataString.replace("tooloud://", "");
            join();
        }

        if(session.inSession()) {
            address = session.getSessionPreferences().get(SessionManager.KEY_ADDRESS);
            join();
        }
    }

    private void join() {
        if(!validate()) {
            return;
        }

        checkIfAnybodyIsHome(address);
    }

    private boolean validate() {
        ConnectivityManager connManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifi = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

        if (!wifi.isConnected()) {
            showMessage("No Wifi", "You must be on the same wifi network as the TooLoud server!");
            return false;
        }

        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_join, menu);
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

    public void checkIfAnybodyIsHome(String address) {
        AnybodyHomeTask anybodyTask = new AnybodyHomeTask(JoinActivity.this, this, address);
        anybodyTask.execute();
    }

    @Override
    public void nobodyIsHome() {
        showMessage("Can't Find Volume", "I couldn't find a volume control...");
    }

    @Override
    public void somebodyIsHome() {
        session.createSession(address);
        Intent intent = new Intent(JoinActivity.this, VolumeActivity.class);
        startActivity(intent);
        finish();
    }

    private void showMessage(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(JoinActivity.this)
                .setTitle(title)
                .setMessage(message)
                .setNeutralButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
