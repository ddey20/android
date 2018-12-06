package ratingapp.ddey.com.dam_project.activities.student;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import ratingapp.ddey.com.dam_project.R;
import ratingapp.ddey.com.dam_project.models.Result;
import ratingapp.ddey.com.dam_project.utils.database.DbHelper;
import ratingapp.ddey.com.dam_project.utils.others.Constants;
import ratingapp.ddey.com.dam_project.utils.others.Session;
import ratingapp.ddey.com.dam_project.utils.adapters.ResultHistoryAdapter;

public class QuizHistoryActivity extends AppCompatActivity {
    private DbHelper mDb;
    private Session mSession;
    private ResultHistoryAdapter mAdapter;

    private List<Result> listResults;

    private Toolbar toolbar;
    private ListView lvHistory;
    private Button btnExportTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quizz_history);
        setToolbar();
        init();
    }

    private void init(){
        btnExportTxt = findViewById(R.id.btn_quiz_history_export_txt);
        mDb = new DbHelper(this);
        mSession = new Session(this);
        listResults = mDb.getResultsByName(mSession, mDb.getName(mSession));
        mAdapter = new ResultHistoryAdapter(this, R.layout.lv_quizz_history_row, listResults);
        lvHistory = findViewById(R.id.lv_history);
        lvHistory.setAdapter(mAdapter);
        btnExportTxt.setOnClickListener(exportResultToTxtEvent());


    }

    @NonNull
    private View.OnClickListener exportResultToTxtEvent() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listResults != null) {
                    writeQuizHistoryReportToText();
                    Toast.makeText(getApplicationContext(), getString(R.string.success_txt_export), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), getString(R.string.err_export_txt), Toast.LENGTH_SHORT).show();
                }
            }
        };
    }

    // SALVEAZA FISIERUL .TXT pe alt thread decat cel principal IN data/data/ratingapp.ddey.com.dam_project/cache/myQuizHistory.txt
    private void writeQuizHistoryReportToText() {
        Runnable myRunnable = new Runnable() {
            @Override
            public void run() {
                File cacheDir = getApplicationContext().getCacheDir();
                File txtFile = new File(cacheDir, Constants.TXT_FILE_NAME);
                final String fileAbsolutePath = txtFile.getAbsolutePath();

                FileWriter fw = null;
                BufferedWriter bw = null;

                try {
                    fw = new FileWriter(fileAbsolutePath);
                    bw = new BufferedWriter(fw);

                    String mainLine = "Quiz history report for current logged in user: " + mDb.getName(mSession);
                    int resultsCounter = 1;
                    bw.write(mainLine);
                    bw.newLine();
                    for (Result r : listResults) {
                        bw.newLine();
                        bw.write("Quiz number and title: ");
                        bw.write(String.valueOf(resultsCounter++) + " ");
                        bw.write(r.getQuizzName());
                        bw.write(", taken on date: ");
                        bw.write(r.getDateTime().substring(0,11).trim());
                        bw.write( " at ");
                        bw.write(r.getDateTime().substring(12, 17).trim());
                        bw.write(" o'clock, final score:  ");
                        bw.write(String.valueOf(r.getScore()));
                        bw.write(" points.");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (bw != null) {
                        try {
                            bw.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
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
