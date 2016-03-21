package com.github.funnygopher.tooloud;

import android.os.AsyncTask;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class VolumeChangeTask extends AsyncTask<Void, Void, Void>{

    private String address;
    private float value;

    public VolumeChangeTask(String address, int value) {
        this.address = "http://" + address + "/volume/";
        this.value = (float) value / 100f;
    }

    @Override
    protected Void doInBackground(Void... params) {
        try {
            HttpRequest req = new HttpRequest(HttpRequest.POST, address);
            Map<String, String> parameters = new HashMap<>();
            parameters.put("command", "change");
            parameters.put("value", Float.toString(value));
            req.withParameters(parameters).send();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
