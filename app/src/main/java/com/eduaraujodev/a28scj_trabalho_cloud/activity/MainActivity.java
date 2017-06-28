package com.eduaraujodev.a28scj_trabalho_cloud.activity;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;
import android.widget.Toast;

import com.eduaraujodev.a28scj_trabalho_cloud.R;
import com.eduaraujodev.a28scj_trabalho_cloud.adapter.ListLocaleAdapter;
import com.eduaraujodev.a28scj_trabalho_cloud.domain.Locale;
import com.eduaraujodev.a28scj_trabalho_cloud.domain.LocaleService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private List<Locale> locales;
    private RecyclerView rvLocales;
    private TextView tvSemLocalizacao;
    private ListLocaleAdapter listLocaleAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rvLocales = (RecyclerView) findViewById(R.id.rvLocales);
        tvSemLocalizacao = (TextView) findViewById(R.id.tvSemLocalizacao);

        String permissions[] = new String [] {
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
        };

        if (!validate(this, permissions)) {
            finish();
        } else {
            carregaLocales();
        }
    }

    private void carregaLocales() {
        locales = new ArrayList<>();

        try {
            LocaleService.getLocales(getApplicationContext());
        } catch (IOException e) {
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }

        if (locales.size() > 0) {

        } else {
            tvSemLocalizacao.setText("Sem localizações");
        }
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

    private class BuscaLocales extends AsyncTask<Void, Void, Void> {



        @Override
        protected Void doInBackground(Void... params) {
            return null;
        }
    }
}