package com.panjacreation.activitytracker;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

public class ShowHistoryActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    ArrayList<ActivityDetails> arrayActivityDetails = new ArrayList<>();
    String activityName,location;
    RecyclerActivityHistoryAdapter recyclerActivityHistoryAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_history);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        recyclerActivityHistoryAdapter = new RecyclerActivityHistoryAdapter(getApplicationContext(),arrayActivityDetails);
        recyclerView.setAdapter(recyclerActivityHistoryAdapter);

        //getActivitiesFromDatabase();

    }

    void getActivitiesFromDatabase(){
        arrayActivityDetails.clear();
        recyclerActivityHistoryAdapter.notifyDataSetChanged();
        String androidId = MainActivity.sharedPreferences.getString("ID",null);
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Users").child(androidId).child("Activities");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.getChildrenCount()>0){
                    for (DataSnapshot childSnap : snapshot.getChildren()){
                        ActivityDetails activityDetails = new ActivityDetails(activityName,location);

                        databaseReference.child(Objects.requireNonNull(childSnap.getKey())).child("ActivityName").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DataSnapshot> task) {
                                if (!task.isSuccessful()){
                                    Log.e(TAG, "Firebase error: " +task.getException());
                                } else {
                                    activityName = String.valueOf(task.getResult().getValue());
                                    activityDetails.setActivityName(activityName);
                                    //Toast.makeText(ShowHistoryActivity.this, "On Demand: "+activityDetails.getActivityName(), Toast.LENGTH_SHORT).show();
                                    databaseReference.child(Objects.requireNonNull(childSnap.getKey())).child("Location").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<DataSnapshot> task) {
                                            if (!task.isSuccessful()){
                                                Log.e(TAG, "Firebase error: " +task.getException());
                                            } else {
                                                location = String.valueOf(task.getResult().getValue());
                                                activityDetails.setLocation(location);
                                                //Toast.makeText(ShowHistoryActivity.this, "On dem: "+activityDetails.getLocation(), Toast.LENGTH_SHORT).show();
                                                arrayActivityDetails.add(activityDetails);
                                                //recyclerView.notifyAll();
                                                recyclerActivityHistoryAdapter.notifyDataSetChanged();
                                            }
                                        }
                                    });
                                }
                            }
                        });
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        getActivitiesFromDatabase();
    }
}