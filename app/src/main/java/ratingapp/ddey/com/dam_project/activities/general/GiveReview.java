package ratingapp.ddey.com.dam_project.activities.general;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RatingBar;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

import ratingapp.ddey.com.dam_project.R;
import ratingapp.ddey.com.dam_project.utils.others.Constants;
import ratingapp.ddey.com.dam_project.utils.others.Session;

public class GiveReview extends AppCompatActivity {

    private RatingBar ratingBar;
    private Session session;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_give_review);

        session=new Session(this);
        ratingBar=findViewById(R.id.review_ratingbar);
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                // Write a message to the database
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                final DatabaseReference myRef = database.getReference("reviews");

                HashMap<String, String> user = session.getUserDetails();
                //String key = myRef.push().getKey();
                myRef.child(user.get(Constants.KEY_EMAIL).toString()
                        .replace('@',' ')
                        .replace('.',' ')
                        .replace('_',' ')
                        .replaceAll("\\s+",""))
                        .setValue(rating);
                finish();

            }
        });
    }
}
