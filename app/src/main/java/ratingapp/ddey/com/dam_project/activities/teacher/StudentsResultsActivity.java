package ratingapp.ddey.com.dam_project.activities.teacher;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ListView;

import java.util.List;

import ratingapp.ddey.com.dam_project.R;
import ratingapp.ddey.com.dam_project.models.Result;
import ratingapp.ddey.com.dam_project.utils.DbHelper;
import ratingapp.ddey.com.dam_project.utils.adapters.StudentResultsAdapter;

public class StudentsResultsActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private ListView lv;
    private List<Result> listResults;

    private StudentResultsAdapter mAdapter;
    private DbHelper mDb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_students_results);
        setToolbar();
        init();
    }


    public void init() {
        lv = findViewById(R.id.lv_student_results);
        mDb = new DbHelper(this);
        listResults = mDb.getAllResults();
        mAdapter = new StudentResultsAdapter(this, R.layout.lv_students_results, listResults);
        lv.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
    }

    /**
     *  Metode pentru back button sus pe toolbar.
     */
    public void setToolbar() {
        toolbar = findViewById(R.id.student_results_toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        this.setTitle("Students results");
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
