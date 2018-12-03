package com.example.rodrigosoares.appconsultas.activities;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rodrigosoares.appconsultas.NetworkFragment;
import com.example.rodrigosoares.appconsultas.R;
import com.example.rodrigosoares.appconsultas.database.Consulta;
import com.example.rodrigosoares.appconsultas.database.Consultas;
import com.example.rodrigosoares.appconsultas.interfaces.DownloadCallback;
import com.google.gson.Gson;

public class NovaConsultaActivity extends AppCompatActivity implements DownloadCallback<String> {
    NetworkFragment mNetworkFragment;
    Consulta mNovaConsulta;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nova_consulta);
        ConstraintLayout btnVoltar = findViewById(R.id.voltar);
        ConstraintLayout btnEnviar = findViewById(R.id.enviar);

        btnVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    sendNovaConsulta();
                }catch (Exception e){

                }
            }
        });
    }

    private void sendNovaConsulta(){
        try {
            Integer id = Integer.parseInt(((TextView) findViewById(R.id.txt_num_consulta)).getText().toString());
            String data_agenda = ((TextView) findViewById(R.id.txt_nova_data)).getText().toString();
            String observacao = ((TextView) findViewById(R.id.txt_observacao)).getText().toString();
            String local = ((TextView) findViewById(R.id.txt_local)).getText().toString();
            Gson gson = new Gson();
            mNovaConsulta = new Consulta(id, data_agenda, observacao, local);
            //TODO alterar IP aqui
            String url = "http://192.168.43.159:8080/inserirconsulta?" +
                    "id="+id+"&" +
                    "local="+local+"&" +
                    "data_agenda="+data_agenda+"&" +
                    "observacao="+observacao;

            mNetworkFragment = NetworkFragment.getInstance(getSupportFragmentManager(), url);
            mNetworkFragment.mCallback = this;
            mNetworkFragment.mUrlString = url;
            mNetworkFragment.startDownload();
        }
        catch (Exception e){
            Toast.makeText(this, "Erro ao gerar nova consulta", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void updateFromDownload(String result) {
        Log.d("RDEBUG", result);
    }

    @Override
    public NetworkInfo getActiveNetworkInfo() {
        ConnectivityManager connectivityManager =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo;
    }

    @Override
    public void onProgressUpdate(int progressCode, int percentComplete) {
    }

    @Override
    public void finishDownloading() {
        Consultas.CONSULTAS.add(mNovaConsulta);
        Toast.makeText(this, "Nova consulta cadastrada", Toast.LENGTH_SHORT).show();
        Log.d("RDEBUG", "Download feito");
    }
}
