package com.example.posttestpwbps5;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.firebase.ui.database.SnapshotParser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private FirebaseRecyclerAdapter adapter;

    Button btnTamb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerview);

        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        fetch();

        btnTamb = (Button)findViewById(R.id.btnTambh);
        btnTamb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mInput = new Intent(MainActivity.this, InputActivity.class);
                startActivity(mInput);
            }
        });
    }

    public interface onUserClickListener{
        void onUserClick(Subject subject, String act);
    }

    private void fetch(){
        Query query = FirebaseDatabase.getInstance()
                .getReference()
                .child("subjects");

        FirebaseRecyclerOptions<Subject> options =
                new FirebaseRecyclerOptions.Builder<Subject>()
                .setQuery(query, new SnapshotParser<Subject>() {
                    @NonNull
                    @Override
                    public Subject parseSnapshot(@NonNull DataSnapshot snapshot) {
                        return new Subject(snapshot.child("curDate").getValue().toString(),
                                snapshot.child("subId").getValue().toString(),
                                snapshot.child("subName").getValue().toString(),
                                snapshot.child("subDesk").getValue().toString());
                    }
                }).build();

        adapter = new FirebaseRecyclerAdapter<Subject, ViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull ViewHolder viewHolder, final int position, @NonNull Subject subject) {
                viewHolder.setDate(subject.getCurDate());
                viewHolder.setTtlSubj(subject.getSubName());
                viewHolder.setMsg(subject.getSubDesk());

                viewHolder.edit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                });

                viewHolder.delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        deleteSub(position);
                    }
                });

                viewHolder.root.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                    }
                });
            }

            @NonNull
            @Override
            public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.subj_item, parent, false);
                return new ViewHolder(view);
            }
        };
        recyclerView.setAdapter(adapter);
    }

    private void deleteSub(int subId) {
        DatabaseReference DRSub = FirebaseDatabase.getInstance().getReference("subjects").child(String.valueOf(subId));

        DRSub.removeValue();
        Toast.makeText(this, "Data sudah Dihapus", Toast.LENGTH_LONG).show();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public RelativeLayout root;
        public TextView date, ttlSubj, msg;
        public ImageView edit, delete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            root = itemView.findViewById(R.id.comp1);

            edit = itemView.findViewById(R.id.edit);
            delete = itemView.findViewById(R.id.delete);

            date = itemView.findViewById(R.id.date);
            ttlSubj = itemView.findViewById(R.id.ttlsubj);
            msg = itemView.findViewById(R.id.msg);
        }

        public void setDate (String s){
            date.setText(s);
        }

        public void setTtlSubj(String s){
            ttlSubj.setText(s);
        }

        public void setMsg(String s){
            msg.setText(s);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}
