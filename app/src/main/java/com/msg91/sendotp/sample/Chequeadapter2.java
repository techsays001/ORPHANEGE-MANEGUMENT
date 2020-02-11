package com.msg91.sendotp.sample;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Objects;

import static android.content.Context.MODE_PRIVATE;

public class Chequeadapter2 extends RecyclerView.Adapter<Chequeadapter2.ProductViewHolder> {
    Intent i;


    private Context mCtx;
    private List<Cheque2> productList;

    public Chequeadapter2(Context mCtx, List<Cheque2> productList) {
        this.mCtx = mCtx;
        this.productList = productList;
    }

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.recycler_c1, null);
        return new ProductViewHolder(view);








    }

    @Override
    public void onBindViewHolder(final ProductViewHolder holder, int position) {
        final   Cheque2 cheque;   cheque = productList.get(position);


        holder.username.setText(cheque.getPrize1());
        holder.userphone.setText(cheque.getPrize2());
        holder.useradddress.setText(cheque.getPrize3());
        holder.userla.setText(cheque.getPrize11());
        holder.userlo.setText(cheque.getPrize5());
        holder.chaildname.setText(cheque.getPrize7());
        holder.chaildage.setText(cheque.getPrize8());
        holder.chailddob.setText(cheque.getPrize9());
        holder.chaildgender.setText(cheque.getPrize10());
       // Picasso.get().load(cheque.getPrize6()).into(holder.userimage);
        Picasso.get().load(cheque.getPrize4()).into(holder.chaildimage);

        sh= mCtx.getSharedPreferences("data",MODE_PRIVATE);

        holder.usermap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Intent.ACTION_VIEW, Uri.parse("google.navigation:q="+cheque.getPrize11()+","+cheque.getPrize5()));
                mCtx.startActivity(intent);







            }
        });

        holder.usercall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:"+cheque.getPrize2()));
                mCtx.startActivity(intent);




            }
        });



    }




    @Override
    public int getItemCount() {
        return productList.size();
    }
    SharedPreferences sh;
    class ProductViewHolder extends RecyclerView.ViewHolder {



        TextView username,userphone,useradddress,usermap,usercall,userla,userlo;
        TextView chaildname,chaildage,chailddob,chaildgender;
        ImageView userimage,chaildimage;




        public ProductViewHolder(View itemView) {
            super(itemView);

           username = itemView.findViewById(R.id.username);
            userphone= itemView.findViewById(R.id.userph);
           useradddress= itemView.findViewById(R.id.useraddress);
            usermap= itemView.findViewById(R.id.usermap);
            usercall= itemView.findViewById(R.id.usercall);
            userla= itemView.findViewById(R.id.userlangitude1);
            userlo= itemView.findViewById(R.id.userloongitude);
           chaildname= itemView.findViewById(R.id.chaildname);
            chaildage = itemView.findViewById(R.id.chaildage);
            chailddob= itemView.findViewById(R.id.chailddob);
            chaildgender= itemView.findViewById(R.id.chaildgender);
           // userimage= itemView.findViewById(R.id.userimage);
          chaildimage = itemView.findViewById(R.id.chaildimage);




        }


    }

}