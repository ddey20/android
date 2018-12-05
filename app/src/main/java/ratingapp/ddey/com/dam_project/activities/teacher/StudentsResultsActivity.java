package ratingapp.ddey.com.dam_project.activities.teacher;

import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import ratingapp.ddey.com.dam_project.R;
import ratingapp.ddey.com.dam_project.models.Result;
import ratingapp.ddey.com.dam_project.utils.Constants;
import ratingapp.ddey.com.dam_project.utils.DbHelper;
import ratingapp.ddey.com.dam_project.utils.adapters.StudentResultsAdapter;

public class StudentsResultsActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private ListView lv;
    private List<Result> listResults;
    private Button btnCsv;
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
        mAdapter = new StudentResultsAdapter(this, R.layout.lv_students_results, listResults, this);
        lv.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();

        btnCsv = findViewById(R.id.student_results_btn_export_csv);
        btnCsv.setOnClickListener(generateCSV());
    }

    @NonNull
    private View.OnClickListener generateCSV() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listResults.size() > 0) {
                    writeResultsReportToCsv();
                } else {
                    Toast.makeText(getApplicationContext(), getString(R.string.err_generate_CSV), Toast.LENGTH_SHORT).show();
                }
            }
        };
    }

    // SALVEAZA FISIERUL .CSV IN data/data/ratingapp.ddey.com.dam_project/cache/studentResults.csv
    private void writeResultsReportToCsv() {
        Runnable myRunnable = new Runnable() {
            @Override
            public void run() {
                FileWriter fw = null;
                try {
                    File cacheDir = getApplicationContext().getCacheDir();
                    File csvFile = new File(cacheDir, Constants.CSV_FILE_NAME);
                    final String fileName = csvFile.getAbsolutePath();
                    fw = new FileWriter(fileName);
                    fw.append(Constants.FILE_HEADER);
                    for (Result result : listResults) {
                        fw.append(Constants.NEW_LINE_SEPARATOR);
                        fw.append(String.valueOf(result.getIdResult()));
                        fw.append(Constants.COMMA_DELIMITER);
                        fw.append(result.getStudentName());
                        fw.append(Constants.COMMA_DELIMITER);
                        fw.append(result.getQuizzName());
                        fw.append(Constants.COMMA_DELIMITER);
                        fw.append(result.getDateTime());
                        fw.append(Constants.COMMA_DELIMITER);
                        fw.append(String.valueOf(result.getScore()));
                        fw.append(Constants.COMMA_DELIMITER);
                    }
                    fw.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (fw != null) {
                        try {
                            fw.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        };

        Thread t = new Thread(myRunnable);
        t.start();
        Toast.makeText(getApplicationContext(), getString(R.string.success_csv), Toast.LENGTH_SHORT).show();
    }


    /**
     * Metode pentru back button sus pe toolbar.
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
