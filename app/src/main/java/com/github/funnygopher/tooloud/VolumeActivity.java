package com.github.funnygopher.tooloud;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class VolumeActivity extends AppCompatActivity {

    private Button btnVolumeUp, btnVolumeDown;
    private SeekBar slider;
    private TextView tvSlider;

    private SessionManager session;
    private int volume;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volume);

        session = new SessionManager(getApplicationContext());
        volume = 0;

        //btnVolumeUp = (Button) findViewById(R.id.btnVolumeUp);
        //btnVolumeDown = (Button) findViewById(R.id.btnVolumeDown);
        slider = (SeekBar) findViewById(R.id.slider);
        tvSlider = (TextView) findViewById(R.id.tvSlider);

        /*
        btnVolumeUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int delta = 10;
                setVolume(volume + delta);
            }
        });

        btnVolumeDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int delta = -10;
                setVolume(volume + delta);
            }
        });
        */

        slider.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                tvSlider.setText(Integer.toString(seekBar.getProgress()));
                setVolume(seekBar.getProgress());
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        slider.setProgress(0);
        slider.setMax(100);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        session.destroySession();
    }

    private void setVolume(int value) {
        volume = value;
        tvSlider.setText(Integer.toString(value));

        String address = session.getSessionPreferences().get(SessionManager.KEY_ADDRESS);
        VolumeSetTask task = new VolumeSetTask(address, value);
        task.execute();
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
