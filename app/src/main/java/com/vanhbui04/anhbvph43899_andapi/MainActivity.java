package com.vanhbui04.anhbvph43899_andapi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {
    TextView tvKQ;
    FirebaseFirestore db;
    Context context =this;
    String strKQ="";
    ToDo toDo=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvKQ = findViewById(R.id.tvKQ);
        db = FirebaseFirestore.getInstance();
        Delete();
    }
    void Insert(){
        String id = UUID.randomUUID().toString();
        toDo = new ToDo(id,"title 11", "content 11");
        db.collection("TODO").document(id).set(toDo.convertToHashMap()).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(context, "insert thanh cong", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(context, "insert that bai", Toast.LENGTH_SHORT).show();
            }
        });
    }
    void Update(){
        String id="c225fa2c-1faa-4abb-8ee2-e187c1fa98eb";
        toDo = new ToDo(id, "title 11 update", "content 11 update");
        db.collection("TODO").document(id).update(toDo.convertToHashMap()).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(context, "update thanh cong", Toast.LENGTH_SHORT).show();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(context, "update that bai", Toast.LENGTH_SHORT).show();

            }
        });
    }
    void Delete(){
        String id="c225fa2c-1faa-4abb-8ee2-e187c1fa98eb";
        db.collection("TODO").document(id).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(context, "delete thanh cong", Toast.LENGTH_SHORT).show();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(context, "delete that bai", Toast.LENGTH_SHORT).show();

            }
        });
    }
    ArrayList<ToDo> Select() {
        ArrayList<ToDo> list = new ArrayList<>();
        db.collection("TODO").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    strKQ="";
                    for (QueryDocumentSnapshot doc: task.getResult()){
                        ToDo t = doc.toObject(ToDo.class);
                        list.add(t);
                        strKQ+= "id: "+t.getId()+"\n";
                        strKQ+= "id: "+t.getTitle()+"\n";
                        strKQ+= "id: "+t.getContent()+"\n";
                        tvKQ.setText(strKQ);
                    }
                }else {
                    Toast.makeText(context, "select that bai", Toast.LENGTH_SHORT).show();

                }
            }
        });
        return list;
    }
}