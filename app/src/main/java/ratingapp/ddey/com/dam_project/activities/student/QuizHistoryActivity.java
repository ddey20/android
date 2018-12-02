package ratingapp.ddey.com.dam_project.activities.student;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ListView;

import java.util.List;

import ratingapp.ddey.com.dam_project.R;
import ratingapp.ddey.com.dam_project.models.Result;
import ratingapp.ddey.com.dam_project.utils.DbHelper;
import ratingapp.ddey.com.dam_project.utils.Session;
import ratingapp.ddey.com.dam_project.utils.adapters.ResultHistoryAdapter;

public class QuizHistoryActivity extends AppCompatActivity {
    private DbHelper mDb;
    private Session mSession;
    private ResultHistoryAdapter mAdapter;

    private List<Result> listResults;

    private Toolbar toolbar;
    private ListView lvHistory;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quizz_history);
        setToolbar();
        populateHistory();
    }

    private void populateHistory(){
        mDb = new DbHelper(this);
        mSession = new Session(this);
        listResults = mDb.getResultsByName(mSession, mDb.getName(mSession));
        mAdapter = new ResultHistoryAdapter(this, R.layout.lv_quizz_history_row, listResults);
        lvHistory = findViewById(R.id.lv_history);
        lvHistory.setAdapter(mAdapter);

    }

    /**
     *  Metode pentru back button sus pe toolbar.
     */
    public void setToolbar() {
        toolbar = findViewById(R.id.quizz_history_toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        this.setTitle("Quiz history");
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
