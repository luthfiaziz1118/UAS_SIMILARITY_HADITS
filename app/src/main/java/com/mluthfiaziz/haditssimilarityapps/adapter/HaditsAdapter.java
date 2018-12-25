package com.mluthfiaziz.haditssimilarityapps.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.activeandroid.query.Select;
import com.mluthfiaziz.haditssimilarityapps.R;
import com.mluthfiaziz.haditssimilarityapps.activity.MainActivity;
import com.mluthfiaziz.haditssimilarityapps.activity.SimilarityActivity;
import com.mluthfiaziz.haditssimilarityapps.model.Hadits;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HaditsAdapter extends RecyclerView.Adapter<HaditsAdapter.ListMenuViewHolder>  {
    private ImageView check,edit,delete;
    private Context context;
    private final List<Hadits> listHadits;

    public HaditsAdapter(Context context, List<Hadits> listHadits) {
        this.context = context;
        this.listHadits = listHadits;
    }

    @Override
    public ListMenuViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View mItemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_hadits, null, false);

        RecyclerView.LayoutParams layoutParams = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        mItemView.setLayoutParams(layoutParams);

        return new ListMenuViewHolder(mItemView, this);
    }

    @Override
    public void onBindViewHolder(ListMenuViewHolder holder, int position) {
        final Hadits hadits = listHadits.get(position);
        holder.txtDeskripsi.setText(hadits.description);
        holder.txtHadits.setText(String.valueOf("1. "+hadits.haditsSatu+"\n2. "+hadits.haditsDua));
    }

    @Override
    public int getItemCount() {
        return listHadits.size();
    }

    public class ListMenuViewHolder extends RecyclerView.ViewHolder /*implements View.OnClickListener*/ {
        @BindView(R.id.txt_deskripsi)
        TextView txtDeskripsi;
        @BindView(R.id.txt_hadits)
        TextView txtHadits;

        final HaditsAdapter mAdapter;

        public ListMenuViewHolder(View itemView, HaditsAdapter adapter) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            this.mAdapter = adapter;
        }

        @OnClick(R.id.check_hadits)
        public void cekHadits(){
            final Hadits hadis = listHadits.get(getAdapterPosition());
            Intent i = new Intent(context, SimilarityActivity.class);
            i.putExtra("deskripsi", hadis.description);
            i.putExtra("hadits_satu", hadis.haditsSatu);
            i.putExtra("hadits_dua", hadis.haditsDua);
            context.startActivity(i);
        }

        @OnClick(R.id.edit_hadits)
        public void editHadits(){

            showInputDialog();

        }

        @OnClick(R.id.delete_hadits)
        public void deleteHadits(){
            final Hadits hadis = listHadits.get(getAdapterPosition());
            Hadits.delete(Hadits.class, hadis.getId());

            listHadits.remove(getAdapterPosition());
            notifyDataSetChanged();
        }

        @OnClick(R.id.cv_hadits)
        public void buttonPujasera(){



            final Hadits hadis = listHadits.get(getAdapterPosition());

            final CharSequence[] dialogitem = {"Proses Similarity", "Ganti Hadits", "Hapus Hadits"};
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("Pilihan");
            builder.setItems(dialogitem, new DialogInterface.OnClickListener(){
                public void onClick(DialogInterface dialog, int item){
                    switch(item){
                        case 0 : {
                            Intent i = new Intent(context, SimilarityActivity.class);
                            i.putExtra("deskripsi", hadis.description);
                            i.putExtra("hadits_satu", hadis.haditsSatu);
                            i.putExtra("hadits_dua", hadis.haditsDua);
                            context.startActivity(i);
                            break;
                        }
                        case 1 : {
                            showInputDialog();
                            break;
                        }
                        case 2 : {
                            Hadits.delete(Hadits.class, hadis.getId());

                            listHadits.remove(getAdapterPosition());
                            notifyDataSetChanged();
                            break;
                        }
                    }
                }
            });
            builder.create().show();
        }

        public void showInputDialog() {
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            View promptView = layoutInflater.inflate(R.layout.input_dialog, null);
            android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(context);
            alertDialogBuilder.setView(promptView);

            final EditText etDeskripsi = (EditText) promptView.findViewById(R.id.et_deskripsi);
            final EditText etHaditsSatu = (EditText) promptView.findViewById(R.id.et_hadits_satu);
            final EditText etHaditsDua = (EditText) promptView.findViewById(R.id.et_hadits_dua);

            final Hadits mCurrent = listHadits.get(getAdapterPosition());
            etDeskripsi.setText(mCurrent.description);
            etHaditsSatu.setText(mCurrent.haditsSatu);
            etHaditsDua.setText(mCurrent.haditsDua);

            final Long idCurrent = mCurrent.getId();
            Log.e("ID", ""+idCurrent);

            final Hadits hadits = new Select().from(Hadits.class).where("id = ?", idCurrent).executeSingle();

            alertDialogBuilder.setCancelable(true)
                    .setPositiveButton("Simpan", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            hadits.description = etDeskripsi.getText().toString();
                            hadits.haditsSatu = etHaditsSatu.getText().toString();
                            hadits.haditsDua = etHaditsDua.getText().toString();
                            hadits.save();

                            txtDeskripsi.setText(hadits.description);
                            txtHadits.setText(String.valueOf("1. "+hadits.haditsSatu+"\n2. "+hadits.haditsDua));
                            notifyDataSetChanged();
                        }
                    })
                    .setNegativeButton("Batal", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });

            android.app.AlertDialog alert = alertDialogBuilder.create();
            alert.show();
        }
    }
}
