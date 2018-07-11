package com.example.android.postest.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.support.v7.widget.CardView;
import android.widget.Toast;

import com.example.android.postest.Objek.Barang;
import com.example.android.postest.R;
import com.example.android.postest.SetOnItemRecycleListener;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

/**
 * Created by Leonardo on 6/27/2018.
 */

public class BarangTransaksiAdapter extends RecyclerView.Adapter<BarangTransaksiAdapter.holder> implements Filterable {

    private Context cntx;
    private List<Barang> list;
    private List<Barang> listFull;
    private SetOnItemRecycleListener mItemClickListener;
    private TextView tvEmpty;
    private CardView cv;

    @Override
    public Filter getFilter() {
        return listFilter;
    }

    private Filter listFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Barang> filteredList = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(listFull);
                //Toast.makeText(cntx, "Data tidak ditemukan atau stok tidak ada", Toast.LENGTH_SHORT).show();
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                ;
                for (Barang barang : listFull) {
                    if (barang.getNama().toLowerCase().contains(filterPattern)) {
                        filteredList.add(barang);
                    }

                }


            }
            FilterResults results = new FilterResults();
            results.values = filteredList;

            return results;
        }
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            list.clear();
            list.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };
    class holder extends RecyclerView.ViewHolder{
        //deklarasi variable yang akan digunakan
        private TextView namaBarang, hargaBarang;
        private ImageView gambarBarang;
        //private CardView cardv;

        private holder(View itemView){
            super(itemView);

            //mengakses id text view pada layout dan juga cardview
            hargaBarang = itemView.findViewById(R.id.txtHarga);
            namaBarang = itemView.findViewById(R.id.txtTBarang);
            gambarBarang = itemView.findViewById(R.id.ivTBarang);
            //cardv = itemView.findViewById(R.id.cardViewTransaksi);
            Typeface roboto = Typeface.createFromAsset(itemView.getContext().getAssets(), "fonts/Roboto-Regular.ttf");
            namaBarang.setTypeface(roboto);
            //tvEmpty = itemView.findViewById(R.id.emptyData);
            cv = itemView.findViewById(R.id.cardViewTransaksi);

        }
    }
    public BarangTransaksiAdapter(Context cntx, List<Barang> list, SetOnItemRecycleListener mItemClickListener){
        this.cntx=cntx;
        this.list=list;
        this.mItemClickListener = mItemClickListener;
        listFull = new ArrayList<>(list);
    }

    @Override
    public holder onCreateViewHolder(ViewGroup parent, int viewType) {
        //membuat view baru

        View view = LayoutInflater.from(cntx).inflate(R.layout.transaksi_layout, parent, false);
        final holder hldr = new holder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mItemClickListener.onItemClick(v, hldr.getAdapterPosition());
            }
        });
        return hldr;
    }

    @Override
    public void onBindViewHolder(holder holder, int position) {
        Barang data = list.get(position);
        holder.namaBarang.setText(data.getNama());

        Locale localeID = new Locale("in", "ID");
        NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);

        holder.hargaBarang.setText(formatRupiah.format(data.getHarga()));
        byte[] test = data.getGambar();
        ByteArrayInputStream imageStream = new ByteArrayInputStream(test);
        Bitmap theImage= BitmapFactory.decodeStream(imageStream);
        holder.gambarBarang.setImageBitmap(theImage);


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

  /*  public Barang getData(int position){
        return list.get(position);
    }
    public void filterList(ArrayList<Barang> filterList){
        list = filterList;
        notifyDataSetChanged();
    }
*/

}

