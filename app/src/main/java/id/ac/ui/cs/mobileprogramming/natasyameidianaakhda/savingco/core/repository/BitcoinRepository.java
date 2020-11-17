package id.ac.ui.cs.mobileprogramming.natasyameidianaakhda.savingco.core.repository;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;

public class BitcoinRepository {
    private String bitcoinIndex;
    private final static String BITCOIN_INDEX_API = "https://api.coindesk.com/v1/bpi/historical/close.json?currency=IDR";

    public BitcoinRepository() {
    }

    public String getBitcoinValue() {
        try {
            bitcoinIndex = new FetchDataAPIBitcoin().execute().get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitcoinIndex;
    }

    private class FetchDataAPIBitcoin extends AsyncTask<Void, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Void... params) {
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;
            String bitcoinIndexStr = null;

            try {

                URL url = new URL(BITCOIN_INDEX_API);
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    buffer.append(line + "\n");
                }

                if (buffer.length() == 0) {
                    return null;
                }
                bitcoinIndexStr = buffer.toString();

                JSONObject jsonObj = new JSONObject(bitcoinIndexStr);
                JSONObject jsonBPI = jsonObj.getJSONObject("bpi");
                Iterator<String> keys = jsonBPI.keys();
                String lastKey = "";
                while (keys.hasNext()) {
                    lastKey = keys.next();
                }
                long value = Math.round(jsonBPI.getDouble(lastKey));
                bitcoinIndexStr = String.valueOf(value);
                return bitcoinIndexStr;
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (Exception e) {
                        Log.e("BitcoinRepository", "Error closing stream", e);
                    }
                }
            }
            return bitcoinIndexStr;
        }


        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }
    }
}