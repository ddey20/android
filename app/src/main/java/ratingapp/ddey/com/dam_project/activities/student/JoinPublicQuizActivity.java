package ratingapp.ddey.com.dam_project.activities.student;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import ratingapp.ddey.com.dam_project.R;
import ratingapp.ddey.com.dam_project.utils.others.Constants;

public class JoinPublicQuizActivity extends AppCompatActivity implements CardView.OnClickListener {
    private Toolbar toolbar;
    private CardView cv1;
    private CardView cv2;
    private CardView cv3;
    private CardView cv4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_public_quizz);
        setToolbar();
        init();
    }

    public void init() {
        cv1 = findViewById(R.id.public_cv1);
        cv2 = findViewById(R.id.public_cv2);
        cv3 = findViewById(R.id.public_cv3);
        cv4 = findViewById(R.id.public_cv4);

        cv1.setOnClickListener(this);
        cv2.setOnClickListener(this);
        cv3.setOnClickListener(this);
        cv4.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(getApplicationContext(), PublicQuizzesActivity.class);

        switch (v.getId()) {
            case R.id.public_cv1:
                intent.putExtra(Constants.PUBLIC_QUIZZ_KEY, "DAM");
                break;
            case R.id.public_cv2:
                intent.putExtra(Constants.PUBLIC_QUIZZ_KEY, "PAW");
                break;
            case R.id.public_cv3:
                intent.putExtra(Constants.PUBLIC_QUIZZ_KEY, "Java");
                break;
            case R.id.public_cv4:
                intent.putExtra(Constants.PUBLIC_QUIZZ_KEY, "SQL");
                break;
            default:
                break;
        }
        startActivity(intent);
    }

    /**
     *  Metode pentru back button sus pe toolbar.
     */
    public void setToolbar() {
        toolbar = findViewById(R.id.join_publicquizz_toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        this.setTitle("Public quizzes");
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home)
            this.finish();

        return super.onOptionsItemSelected(item);
    }
}
