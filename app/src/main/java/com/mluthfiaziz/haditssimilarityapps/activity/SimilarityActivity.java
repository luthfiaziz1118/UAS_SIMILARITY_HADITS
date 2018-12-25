package com.mluthfiaziz.haditssimilarityapps.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.mluthfiaziz.haditssimilarityapps.R;
import com.mluthfiaziz.haditssimilarityapps.utils.Cosine;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SimilarityActivity extends AppCompatActivity {

    @BindView(R.id.txt_hadits_1)
    TextView txtHaditsSatu;
    @BindView(R.id.txt_hadits_2)
    TextView txtHaditsDua;
    @BindView(R.id.txt_hasil_similarity)
    TextView txtHasilSimilarity;

    String hadisSatu, hadisDua;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_similarity);
        ButterKnife.bind(this);

        initToolbar();
        initIntentData();
        initUI();
    }

    private void initUI() {
        txtHaditsSatu.setText(hadisSatu);
        txtHaditsDua.setText(hadisDua);

        Cosine cos = new Cosine(2);
        double dSimilarity = Math.round(cos.similarity(hadisSatu, hadisDua) * 100.0);
        txtHasilSimilarity.setText(String.valueOf(dSimilarity+"%"));
    }

    private void initIntentData() {
        hadisSatu = getIntent().getStringExtra("hadits_satu");
        hadisDua = getIntent().getStringExtra("hadits_dua");
    }

    private void initToolbar() {
        getSupportActionBar().setTitle("Similarity Hadits");
        getSupportActionBar().setSubtitle(getIntent().getStringExtra("deskripsi"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home : {
                finish();
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }
}


