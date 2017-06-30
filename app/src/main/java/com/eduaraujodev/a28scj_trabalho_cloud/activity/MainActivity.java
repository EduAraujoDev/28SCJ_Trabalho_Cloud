package com.eduaraujodev.a28scj_trabalho_cloud.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.eduaraujodev.a28scj_trabalho_cloud.R;
import com.eduaraujodev.a28scj_trabalho_cloud.adapter.ListLocaleAdapter;
import com.eduaraujodev.a28scj_trabalho_cloud.domain.Locale;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String URL = "http://54.214.215.34:8080/api/v1/getMarkers";

    private List<Locale> locales = new ArrayList<>();;
    private RecyclerView rvLocales;
    private TextView tvSemLocalizacao;
    private ListLocaleAdapter listLocaleAdapter;
    private SwipeRefreshLayout swipeLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rvLocales = (RecyclerView) findViewById(R.id.rvLocales);
        tvSemLocalizacao = (TextView) findViewById(R.id.tvSemLocalizacao);

        swipeLayout = (SwipeRefreshLayout) findViewById(R.id.swipeToRefresh);
        swipeLayout.setOnRefreshListener(OnRefreshListener());
        swipeLayout.setColorSchemeResources(
                R.color.colorPrimary);

        String permissions[] = new String [] {
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
        };

        if (!validate(this, permissions)) {
            finish();
        }
    }

    private SwipeRefreshLayout.OnRefreshListener OnRefreshListener() {
        return new SwipeRefreshLayout.OnRefreshListener() {

            @Override
            public void onRefresh() {
                locales = new ArrayList<>();
                new BuscaLocales().execute();
            }
        };
    }

    public static boolean validate(Activity activity, String... permissions) {
        List<String> list = new ArrayList<>();
        for (String permission : permissions) {
            boolean ok = ContextCompat.checkSelfPermission(activity, permission) == PackageManager.PERMISSION_GRANTED;
            if (!ok) {
                list.add(permission);
            }
        }
        if (list.isEmpty()) {
            return true;
        }

        String[] newPermissions = new String[list.size()];
        list.toArray(newPermissions);

        ActivityCompat.requestPermissions(activity, newPermissions, 1);

        return false;
    }

    private class BuscaLocales extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... params) {
            try {
                URL url = new URL(URL);

                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(15000);
                conn.setConnectTimeout(10000);

                conn.setRequestMethod("GET");
                //conn.setDoOutput(true);

                if(conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    InputStream is = conn.getInputStream();
                    BufferedReader buffer = new BufferedReader(new InputStreamReader(is));

                    StringBuilder result = new StringBuilder();
                    String linha;

                    while ((linha = buffer.readLine()) != null) {
                        result.append(linha);
                    }

                    conn.disconnect();

                    return result.toString();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            swipeLayout.setRefreshing(false);

            if (s == null) {
                Toast.makeText(MainActivity.this, "Erro ao buscar localizações", Toast.LENGTH_LONG).show();
            } else {
                try {
                    JSONArray json = new JSONArray(s);

                    if (json != null) {
                        for (int i = 0; i < json.length(); i++) {
                            JSONObject data = json.getJSONObject(i);
                            Locale locale = new Locale();
                            locale.log = data.getDouble("longitude");
                            locale.lat = data.getDouble("latitude");
                            locale.dataHora = "29/06/2017 - 20:37";

                            locales.add(locale);
                        }

                        setUpLista(locales);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void setUpLista(List<Locale> lista) {
        listLocaleAdapter = new ListLocaleAdapter(this, lista, localeOnClickListener());
        rvLocales.setLayoutManager(new LinearLayoutManager(this));
        rvLocales.setAdapter(listLocaleAdapter);

        if (locales.size() > 0) {
            tvSemLocalizacao.setText("");
        }
    }

    private ListLocaleAdapter.LocaleOnClickListener localeOnClickListener() {
        return new ListLocaleAdapter.LocaleOnClickListener() {
            @Override
            public void onClickLocale(View view, int posicao) {
                Locale locale = locales.get(posicao);

                Intent intent = new Intent(MainActivity.this, MapsActivity.class);
                intent.putExtra("locale", locale);

                startActivity(intent);
            }
        };
    }
}