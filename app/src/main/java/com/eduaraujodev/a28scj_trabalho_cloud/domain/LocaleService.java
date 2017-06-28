package com.eduaraujodev.a28scj_trabalho_cloud.domain;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class LocaleService {
    private static final int TIMEOUT_MILLIS = 15000;
    private static final String TAG = "Http";
    private static final String URL = "http://www.mocky.io/v2/595305b0270000b000b2a976";
    private static final String CHARSET = "UTF-8";

    public static List<Locale> getLocales(Context context) throws IOException {
        String resultado = "";
        URL url = new URL(URL);

        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setReadTimeout(15000);
        conn.setConnectTimeout(10000);

        conn.setRequestMethod("GET");
        conn.setDoOutput(true);

        if(conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
            InputStream is = conn.getInputStream();
            BufferedReader buffer = new BufferedReader(new InputStreamReader(is));

            StringBuilder result = new StringBuilder();
            String linha;

            while ((linha = buffer.readLine()) != null) {
                result.append(linha);
            }

            resultado = result.toString();
            conn.disconnect();
        }

        Log.d(TAG, resultado);

        return null;
    }

    private static List<Locale> parserJSON(Context context, String json) throws IOException {
        List<Locale> locales = new ArrayList<>();

        try {
            JSONObject root = new JSONObject(json);
            JSONObject obj = root.getJSONObject("locales");
            JSONArray jsonLocales = obj.getJSONArray("locale");

            for (int i = 0; i < jsonLocales.length(); i++) {
                JSONObject jsonLocale = jsonLocales.getJSONObject(i);
                Locale locale = new Locale();
                locale.dataHora = jsonLocale.optString("datahora");
                locale.log = jsonLocale.optString("log");
                locale.lat = jsonLocale.optString("lat");
                locales.add(locale);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return locales;
    }
}