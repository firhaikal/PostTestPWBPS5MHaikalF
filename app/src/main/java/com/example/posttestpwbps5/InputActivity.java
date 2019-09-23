package com.example.posttestpwbps5;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class InputActivity extends AppCompatActivity {

    Button btnInput;
    EditText edtJudul, edtDesk;

    DatabaseReference dbSubject;

    SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy hh:mm:ss");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input);

        dbSubject = FirebaseDatabase.getInstance().getReference("subjects");

        edtJudul = (EditText) findViewById(R.id.edtJudul);
        edtDesk = (EditText) findViewById(R.id.edtDesk);

        btnInput = (Button) findViewById(R.id.btnInp);

        btnInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addSubj();
            }
        });

    }

    private void addSubj(){
        String judul = edtJudul.getText().toString().trim();
        String desk = edtDesk.getText().toString().trim();

        String curDate =  sdf.format(new Date());

        if (!TextUtils.isEmpty(judul)){

            String id = dbSubject.push().getKey();

            Subject subject = new Subject(curDate, id, judul, desk);

            dbSubject.child(id).setValue(subject);

            Toast.makeText(this, "Data berhasil dimasukkan", Toast.LENGTH_LONG).show();

        }else {
            Toast.makeText(this, "Anda Harus Mengisi Semuanya", Toast.LENGTH_LONG).show();
        }
    }
}
