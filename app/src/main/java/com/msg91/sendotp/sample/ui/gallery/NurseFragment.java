package com.msg91.sendotp.sample.ui.gallery;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.msg91.sendotp.sample.MainActivity2;
import com.msg91.sendotp.sample.R;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import cn.pedant.SweetAlert.SweetAlertDialog;

import static android.content.Context.MODE_PRIVATE;

public class NurseFragment extends Fragment {
    SharedPreferences sh;
    Button wu;
    EditText date,nc,nw,nf;
    private DatePicker datePicker;

    private TextView dateView;
    DatePickerDialog datePickerDialog;
    int year;
    int month;
    int dayOfMonth;
    Calendar calendar;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        setHasOptionsMenu(true);
        final View root = inflater.inflate(R.layout.fragment_nurse, container, false);
        //  sh=getActivity().getSharedPreferences("data",MODE_PRIVATE);
        date = root.findViewById(R.id.wdate);
        nc = root.findViewById(R.id.nc);
        nw = root.findViewById(R.id.nw);
        nf = root.findViewById(R.id.nf);
        wu = root.findViewById(R.id.wu);
        sh= Objects.requireNonNull(getActivity()).getSharedPreferences("data",MODE_PRIVATE);
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datePickerDialog = new DatePickerDialog(getActivity(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                                date.setText(day + "/" + (month + 1) + "/" + year);
                            }
                        }, year, month, dayOfMonth);
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
                datePickerDialog.show();
            }
        });


       wu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(),"submitted",Toast.LENGTH_LONG).show();
                if (date.getText().toString().isEmpty()) {
                    date.setError("null");


                } else if (nc.getText().toString().isEmpty()) {
                    nc.setError("null");

                }  else if (nw.getText().toString().isEmpty()) {
               nw.setError("null");

           }
            else if (nf.getText().toString().isEmpty()) {
               nf.setError("null");

           }
                else {

                    StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://androidprojectstechsays.000webhostapp.com/Farmer_app/reprot.php",
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
//                                    date.getText().clear();
//                                    q.getText().clear();
                                    //  Toast.makeText(getActivity(),sh.getString("longitude",null),Toast.LENGTH_LONG).show();
                                    Toast.makeText(getActivity(),response,Toast.LENGTH_LONG).show();
                                    if(response.equals("ok"))
                                    {

                                        new SweetAlertDialog(getActivity(), SweetAlertDialog.WARNING_TYPE)
                                                .setTitleText(" Success")
                                                .setContentText("Login to Dashboard!")
                                                .setConfirmText("Yes,Login")
                                                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                                    @Override
                                                    public void onClick(SweetAlertDialog sDialog) {
                                                        sDialog
                                                                .setTitleText("Logining...!")

                                                                .setConfirmText("OK")

                                                                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                                                    @Override
                                                                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                                                                        Intent in=new Intent(getActivity(), MainActivity2.class);
                                                                        startActivity(in);
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
//You can handle error here if you want
                                }

                            }) {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> params = new HashMap<>();

                            params.put("a", date.getText().toString());
                            params.put("b", nc.getText().toString());
                            params.put("c", nw.getText().toString());
                            params.put("d", nf.getText().toString());
                            params.put("ph",sh.getString("phone",null));


// Toast.makeText(MainActivity.this,"submitted",Toast.LENGTH_LONG).show();

//returning parameter
                            return params;
                        }

                    };

// m = Integer.parseInt(ba) - Integer.parseInt(result.getContents());
// balance.setText(m+"");


//Adding the string request to the queue
                    RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
                    requestQueue.add(stringRequest);

                }
            }
        });
        return root;
    }
}

