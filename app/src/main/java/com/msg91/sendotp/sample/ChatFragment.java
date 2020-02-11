package com.msg91.sendotp.sample;







import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;

import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.Fragment;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;

import static android.content.Context.MODE_PRIVATE;

public class ChatFragment extends Fragment   implements
        AdapterView.OnItemSelectedListener {
        String[] neww = { "male","female","other"};
    private DatePicker datePicker;

    private TextView dateView;
    DatePickerDialog datePickerDialog;
    int year;
    int month;
    int dayOfMonth;
    Calendar calendar;
    View root;
    SharedPreferences shq, shh, logout;
    Button btn, im;
    private Bitmap bitmap;
    private Uri filePath;
    EditText cname,age,dob;
    Spinner gender;
    SharedPreferences sh;
    final int RequestPermissionCode = 1;
    ImageView imgview;
    static final int REQUEST_CODE = 1;
    List<String> imagesEncodedList;
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        setHasOptionsMenu(true);
        root = inflater.inflate(R.layout.fragment_chat, container, false);
        cname = root.findViewById(R.id.oname);
       age = root.findViewById(R.id.oage);
      dob = root.findViewById(R.id.odob);
       gender= root.findViewById(R.id.ogender);
        imgview = root.findViewById(R.id.imgg11);
         sh=getActivity().getSharedPreferences("data1",MODE_PRIVATE);
        btn = root.findViewById(R.id.o);
     //   Toast.makeText(getActivity(),   sh.getString("orphanage_name",null), Toast.LENGTH_LONG).show();

        ArrayAdapter as = new ArrayAdapter(getActivity(),android.R.layout.simple_spinner_item,neww);
        as.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        gender.setAdapter(as);
        dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datePickerDialog = new DatePickerDialog(getActivity(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                                dob.setText(day + "/" + (month + 1) + "/" + year);
                            }
                        }, year, month, dayOfMonth);
                // datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
                datePickerDialog.show();
            }
        });



        imgview.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {
                if (getActivity().checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
                {
                    requestPermissions(new String[]{Manifest.permission.CAMERA}, 100);
                }
                else
                {
                    Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(cameraIntent, 2);
                    //startActivityForResult(image, 200);
                }
            }
        });


       btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (cname.getText().toString().isEmpty()) {
                    cname.setError("null");


                } else if (age.getText().toString().isEmpty()) {
                  age.setError("null");

                }
                else if (dob.getText().toString().isEmpty()) {
                   dob.setError("null");

                }
                else {

                    class UploadImage extends AsyncTask<Bitmap, Void, String> {

                        ProgressDialog loading;
                        RequestHandler rh = new RequestHandler();

                        @Override
                        protected void onPreExecute() {
                            super.onPreExecute();
                            loading = ProgressDialog.show(getContext(), "Uploading...", null, true, false);
                        }


                        @Override
                        protected void onPostExecute(String s) {
                            super.onPostExecute(s);
                            loading.dismiss();
                            Toast.makeText(getContext(), s, Toast.LENGTH_LONG).show();

                            if (s.equals("success")) {

                                new SweetAlertDialog(getContext(), SweetAlertDialog.WARNING_TYPE)
                                        .setTitleText(" Success")
                                        .setContentText("Back To Home")
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
                                                                Intent in = new Intent(getContext(), MainActivityhome.class);
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

                        @SuppressLint("WrongThread")
                        @Override
                        protected String doInBackground(Bitmap... params) {
                            bitmap = params[0];
                            String uploadImage = getStringImage(bitmap);

                            HashMap<String, String> data = new HashMap<>();

                            data.put("a", cname.getText().toString());
                            data.put("b", age.getText().toString());
                            data.put("c", dob.getText().toString());
                            data.put("d",gender.getSelectedItem().toString());
                            data.put("ph",sh.getString("phone",null));
                            data.put("o",sh.getString("orphanage_name",null));
                            data.put("img", uploadImage);
                            String result = rh.sendPostRequest("https://androidprojectstechsays.000webhostapp.com/Orphanage_manegument_system/admin_add_chaild.php", data);

                            return result;
                        }
                    }
                    UploadImage ui = new UploadImage();
                    ui.execute(bitmap);


                }


            }


        });


        return root;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 100)
        {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                Toast.makeText(getActivity(), "camera permission granted", Toast.LENGTH_LONG).show();
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, 2);
            }
            else
            {
                Toast.makeText(getActivity(), "camera permission denied", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (requestCode == 2 && resultCode == Activity.RESULT_OK)
        {
            bitmap = (Bitmap) data.getExtras().get("data");
            imgview.setImageBitmap(bitmap);
            getStringImage(bitmap);
        }
    }

    public String getStringImage(Bitmap bmp) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }

    @Override
    public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
//        Toast.makeText(getApplicationContext(),dance[position] , Toast.LENGTH_LONG).show();
    }
    @Override
    public void onNothingSelected(AdapterView<?> arg0) {

    }


}