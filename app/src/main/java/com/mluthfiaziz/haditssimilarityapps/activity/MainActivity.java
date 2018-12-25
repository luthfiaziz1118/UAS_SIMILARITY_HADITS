package com.mluthfiaziz.haditssimilarityapps.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.activeandroid.query.Select;
import com.mluthfiaziz.haditssimilarityapps.R;
import com.mluthfiaziz.haditssimilarityapps.adapter.HaditsAdapter;
import com.mluthfiaziz.haditssimilarityapps.model.Hadits;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.rv_hadits)
    RecyclerView mRecyclerView;

    private HaditsAdapter mAdapter;

    List<Hadits> listHadits = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        listHadits = new Select().from(Hadits.class).orderBy("id ASC").execute();

        mAdapter = new HaditsAdapter(MainActivity.this, listHadits);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @OnClick(R.id.btn_add)
    public void addHadits(){
        startActivity(new Intent(MainActivity.this,InputHadits.class));
        /*LayoutInflater layoutInflater = LayoutInflater.from(MainActivity.this);
        View promptView = layoutInflater.inflate(R.layout.input_dialog, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
        alertDialogBuilder.setView(promptView);

        final EditText etDeskripsi = (EditText) promptView.findViewById(R.id.et_deskripsi);
        final EditText etHaditsSatu = (EditText) promptView.findViewById(R.id.et_hadits_satu);
        final EditText etHaditsDua = (EditText) promptView.findViewById(R.id.et_hadits_dua);

        alertDialogBuilder.setCancelable(true)
                .setPositiveButton("Simpan", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Hadits hadits = new Hadits();
                        hadits.description = etDeskripsi.getText().toString();
                        hadits.haditsSatu = etHaditsSatu.getText().toString();
                        hadits.haditsDua = etHaditsDua.getText().toString();
                        hadits.save();
                        recreate();

                        listHadits.add(hadits);
                        mAdapter.notifyDataSetChanged();
                    }
                })
                .setNegativeButton("Batal", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert = alertDialogBuilder.create();
        alert.show();*/
    }
}
