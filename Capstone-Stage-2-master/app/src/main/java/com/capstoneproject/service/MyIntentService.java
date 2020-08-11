package com.capstoneproject.service;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import com.capstoneproject.Models.Question;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class MyIntentService extends IntentService {

    // TODO: Rename actions, choose action names that describe tasks that this
    // IntentService can perform, e.g. ACTION_FETCH_NEW_ITEMS
    public static final int STATUS_RUNNING = 0;
    public static final int STATUS_FINISHED = 1;
    public static final int STATUS_ERROR = 2;
    private static final String TAG = "MyIntentService";

    // TODO: Rename parameters

    public MyIntentService() {
        super("MyIntentService");
    }

    /**
     * Starts this service to perform action Foo with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.d(TAG, "Service Started!");
        final ResultReceiver receiver = intent.getParcelableExtra("receiver");
        String url = intent.getStringExtra("url");
        Bundle bundle = new Bundle();

        if (!TextUtils.isEmpty(url)) {
            /* Update UI: Download Service is Running */
            receiver.send(STATUS_RUNNING, Bundle.EMPTY);

            try {
                ArrayList<Question> questions = downloadData(url);

                /* Sending result back to activity */
                if (null != questions && questions.size() > 0) {
                    //bundle.putStringArray("result", results);
                    bundle.putParcelableArrayList("result", questions);
                    receiver.send(STATUS_FINISHED, bundle);
                }
            } catch (Exception e) {

                /* Sending error message back to activity */
                bundle.putString(Intent.EXTRA_TEXT, e.toString());
                receiver.send(STATUS_ERROR, bundle);
            }
        }

        Log.d(TAG, "Service Stopping!");
        this.stopSelf();
    }

    private ArrayList<Question> downloadData(String requestUrl) throws IOException, DownloadException {
        InputStream inputStream = null;
        HttpURLConnection urlConnection = null;

        /* forming th java.net.URL object */
        URL url = new URL(requestUrl);
        urlConnection = (HttpURLConnection) url.openConnection();

        /* optional request header */
        urlConnection.setRequestProperty("Content-Type", "application/json");

        /* optional request header */
        urlConnection.setRequestProperty("Accept", "application/json");

        /* for Get request */
        urlConnection.setRequestMethod("GET");
        int statusCode = urlConnection.getResponseCode();

        /* 200 represents HTTP OK */
        if (statusCode == 200) {
            inputStream = new BufferedInputStream(urlConnection.getInputStream());
            String response = convertInputStreamToString(inputStream);
            ArrayList<Question> questions = parseResult(response);
            return questions;
        } else {
            throw new DownloadException("Failed to fetch data!!");
        }
    }

    private ArrayList<Question> parseResult(String response) {
        ArrayList<Question> questions = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray items = jsonObject.getJSONArray("results");
            JSONObject questionObj;
            for (int i = 0; i < items.length(); i++) {
                questionObj = items.getJSONObject(i);

                JSONArray st = questionObj.getJSONArray("incorrect_answers");
                String[] inc = new String[st.length()];
                for (int j = 0; j < st.length(); j++) {
                    inc[j] = st.getString(j).replaceAll("&quot;", "\"").replaceAll("&#039;", "'");

                    //  Log.e("Inc",inc[j]);
                }

                Question question = new Question(i, questionObj.getString("question").replaceAll("&quot;", "\"").replaceAll("&#039;", "'"), questionObj.getString("category"), questionObj.getString("correct_answer").replaceAll("&quot;", "\"").replaceAll("&#039;", "'"), inc, questionObj.getString("difficulty"));
                questions.add(question);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return questions;
    }

    private String convertInputStreamToString(InputStream inputStream) throws IOException {

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String line = "";
        String result = "";

        while ((line = bufferedReader.readLine()) != null) {
            result += line;
        }

        /* Close Stream */
        if (null != inputStream) {
            inputStream.close();
        }

        return result;
    }

    /**
     * Handle action Foo in the provided background thread with the provided
     * parameters.
     */
    public class DownloadException extends Exception {

        public DownloadException(String message) {
            super(message);
        }

        public DownloadException(String message, Throwable cause) {
            super(message, cause);
        }
    }

}

