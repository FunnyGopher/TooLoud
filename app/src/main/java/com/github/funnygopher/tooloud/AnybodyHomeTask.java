package com.github.funnygopher.tooloud;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import java.io.IOException;

public class AnybodyHomeTask extends AsyncTask<Void, Void, Boolean> {

    private String anybodyHomeAddress;

    private Context context;
    private AnybodyHomeable homeable;
    private ProgressDialog dialog;

    public AnybodyHomeTask(Context context, AnybodyHomeable homeable, String ipAddress) {
        this.context = context;
        this.homeable = homeable;
        this.anybodyHomeAddress = "http://" + ipAddress + "/tooloud/";

        dialog = new ProgressDialog(context);
    }

    @Override
    protected void onPreExecute() {
        dialog.setMessage("Looking for a volume control...");
        dialog.show();
    }

    @Override
    protected void onPostExecute(Boolean success) {
        dialog.dismiss();

        if(!success) {
            homeable.nobodyIsHome();
        } else {
            homeable.somebodyIsHome();
        }
    }

    @Override
    protected Boolean doInBackground(Void... params) {
        try {
            HttpRequest req = new HttpRequest(HttpRequest.GET, anybodyHomeAddress);
            req.send();
            return true;
        } catch (IOException e) {
            return false;
        }
    }
}
