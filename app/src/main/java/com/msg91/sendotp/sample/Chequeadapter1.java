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

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import cn.pedant.SweetAlert.SweetAlertDialog;

import static android.content.Context.MODE_PRIVATE;

public class Chequeadapter1 extends RecyclerView.Adapter<Chequeadapter1.ProductViewHolder> {
    SharedPreferences sh;

    private Context mCtx;
    private List<Cheque1> productList;

    public Chequeadapter1(Context mCtx, List<Cheque1> productList) {
        sh = Objects.requireNonNull(mCtx).getSharedPreferences("data", MODE_PRIVATE);
        this.mCtx = mCtx;
        this.productList = productList;
    }

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.activity_event, null);
        return new ProductViewHolder(view);

    }

    @Override
    public void onBindViewHolder(final ProductViewHolder holder, int position) {
        final   Cheque1 cheque;   cheque = productList.get(position);

        //loading the image
        Picasso.get().load(cheque.getImage()).into(holder.image);
        holder.name1.setText(cheque.getUser());
        holder.age.setText(cheque.getPrize());
        holder.dob.setText(cheque.getStatus());
        holder.gender.setText(cheque.getDes());
        holder.phh.setText(cheque.getph());


        holder.msg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(holder.sms.getText().toString().isEmpty()){

                    holder.sms.setError("Enter a message");
                }
                else {


                    Uri uri = Uri.parse("smsto:" + cheque.getph());
                    Intent i = new Intent(Intent.ACTION_SENDTO, uri);
                    i.putExtra("sms_body", holder.sms.getText().toString());
                    i.setPackage("com.android.mms");
                    mCtx.startActivity(i);
                    holder.sms.getText().clear();
                }

            }
        });



        holder.call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:"+cheque.getph()));
                mCtx.startActivity(intent);




            }
        });
        holder.apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                StringRequest stringRequest = new StringRequest(com.android.volley.Request.Method.POST, "https://androidprojectstechsays.000webhostapp.com/Orphanage_manegument_system/intrested.php",
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                                // Toast.makeText(mCtx, response, Toast.LENGTH_LONG).show();
                                if(response.equals("Successful"))
                                {

                                    new SweetAlertDialog(mCtx, SweetAlertDialog.WARNING_TYPE)
                                            .setTitleText(" Success")
                                            .setContentText("Back to Home!")
                                            .setConfirmText("Yes")
                                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                                @Override
                                                public void onClick(SweetAlertDialog sDialog) {
                                                    sDialog
                                                            .setTitleText("Logining...!")

                                                            .setConfirmText("OK")

                                                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                                                @Override
                                                                public void onClick(SweetAlertDialog sweetAlertDialog) {
                                                                    Intent in=new Intent(mCtx,MainActivity2.class);
                                                                    mCtx.startActivity(in);
                                                                }
                                                            })
                                                            .changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
                                                }
                                            })
                                            .show();




//
                                }

                            }





                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                            }


                        }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<>();
                        //Adding parameters to request


                        params.put("d",cheque.getStatus());
                        params.put("b", cheque.getPrize());
                        params.put("c", cheque.getUser());
                        params.put("a", cheque.getDes());
                        params.put("e", cheque.getph());
                        params.put("name", sh.getString("name",null));
                        params.put("ph", sh.getString("phone",null));
                        params.put("add", sh.getString("address",null));
                        params.put("la", sh.getString("latitude",null));
                        params.put("lo", sh.getString("longitude",null));
                        params.put("f",cheque.getImage());
                        params.put("im", sh.getString("image",null));
                        return params;
                    }

                };


                //Adding the string request to the queue
                RequestQueue requestQueue = Volley.newRequestQueue(mCtx);
                requestQueue.add(stringRequest);


            }








        });


    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    class ProductViewHolder extends RecyclerView.ViewHolder {



        TextView name1,age,dob,gender,call,msg,phh;
ImageView image;
EditText sms;
Button apply;
        public ProductViewHolder(View itemView) {
            super(itemView);




           image=itemView.findViewById(R.id.cimage);
           name1=itemView.findViewById(R.id.cname1);
           age=itemView.findViewById(R.id.cage);
         dob=itemView.findViewById(R.id.cdob);
            gender=itemView.findViewById(R.id.cgender);
            sms=itemView.findViewById(R.id.csms);
            call=itemView.findViewById(R.id.ccall);
            msg=itemView.findViewById(R.id.cmsg);
           phh=itemView.findViewById(R.id.cph);
            apply=itemView.findViewById(R.id.apply);

        }

    }



}