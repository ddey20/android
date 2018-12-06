package ratingapp.ddey.com.dam_project.activities.student;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ListView;

import java.util.List;

import ratingapp.ddey.com.dam_project.R;
import ratingapp.ddey.com.dam_project.models.Quiz;
import ratingapp.ddey.com.dam_project.utils.others.Constants;
import ratingapp.ddey.com.dam_project.utils.database.DbHelper;
import ratingapp.ddey.com.dam_project.utils.adapters.PublicQuizAdapter;

public class PublicQuizzesActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private ListView lvQuizzes;

    private PublicQuizAdapter mAdapter;
    private DbHelper mDb;
    private List<Quiz> quizList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_public_quizzes);
        setToolbar();
        mDb = new DbHelper(this);
        incomingIntent();
        init();
    }

    public void init() {
        lvQuizzes = findViewById(R.id.publicquizz_lv_activequizz);
        mAdapter = new PublicQuizAdapter(getApplicationContext(), R.layout.lv_public_quizz_row, quizList, this);
        lvQuizzes.setAdapter(mAdapter);
    }
    public void incomingIntent() {
        Intent intent = getIntent();
        if (intent != null) {

            String quizzCategory = intent.getStringExtra(Constants.PUBLIC_QUIZZ_KEY);

            switch (quizzCategory) {
                case "DAM":
                    this.setTitle("DAM");
                    quizList = mDb.getQuizzesByCategory("DAM");
                    break;

                case "PAW":
                    this.setTitle("PAW");
                    quizList = mDb.getQuizzesByCategory("PAW");
                    break;

                case "Java":
                    this.setTitle("Java");
                    quizList = mDb.getQuizzesByCategory("Java");
                    break;
                case "SQL":
                    this.setTitle("SQL");
                    quizList = mDb.getQuizzesByCategory("SQL");
                    break;
                default:
                    break;
            }
        }

    }

    /**
     *  Metode pentru back button sus pe toolbar.
     */
    public void setToolbar() {
        toolbar = findViewById(R.id.list_publicquizz_toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
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
