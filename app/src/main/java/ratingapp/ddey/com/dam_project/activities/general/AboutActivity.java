package ratingapp.ddey.com.dam_project.activities.general;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import ratingapp.ddey.com.dam_project.R;
import ratingapp.ddey.com.dam_project.models.Review;
import ratingapp.ddey.com.dam_project.utils.DbHelper;
import ratingapp.ddey.com.dam_project.utils.others.Session;
import ratingapp.ddey.com.dam_project.utils.others.Constants;

import static android.content.ContentValues.TAG;

public class AboutActivity extends AppCompatActivity {
    private Button btnViewMaps;
    private Toolbar toolbar;
    private RatingBar ratingBar;
    private Session session;
    private Review reviewNew;
    float reviewSum;
    int nr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        //super.onCreateDrawer();

        //BACK BUTTON pt toolbar pus in locul actionbarului
        this.setTitle("About");
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        session=new Session(this);
        reviewNew=new Review("test",55);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference("reviews");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                reviewSum=0;
                nr=0;
                Iterable<DataSnapshot> list= dataSnapshot.getChildren();
                for(DataSnapshot i:list){
                    reviewSum+=Float.parseFloat(i.getValue().toString());
                    nr++;
                }
                if(nr!=0)
                ratingBar.setRating(reviewSum/nr);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        btnViewMaps = findViewById(R.id.about_btn_view_maps);
        ratingBar=findViewById(R.id.about_ratingbar);



        ratingBar.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Intent intent=new Intent(getApplicationContext(),GiveReview.class);
                ratingBar.setIsIndicator(true);
                startActivity(intent);
                return false;
            }
        });
        btnViewMaps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AboutActivity.this, MapsActivity.class);
                startActivity(intent);
            }
        });

    }


    //Back button
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home)
            this.finish();

        return super.onOptionsItemSelected(item);
    }

    public void ShowText(Object obj){
        Toast.makeText(this,obj.toString(),Toast.LENGTH_LONG).show();
    }

}
