package ratingapp.ddey.com.dam_project.activities.teacher;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONException;

import java.util.List;

import ratingapp.ddey.com.dam_project.R;
import ratingapp.ddey.com.dam_project.models.Quiz;
import ratingapp.ddey.com.dam_project.network.HttpManager;
import ratingapp.ddey.com.dam_project.network.QuizParser;
import ratingapp.ddey.com.dam_project.utils.database.DbHelper;
import ratingapp.ddey.com.dam_project.utils.adapters.ActiveQuizAdapter;
import ratingapp.ddey.com.dam_project.utils.others.Constants;

public class ActiveQuizzesActivity extends AppCompatActivity {
    private List<Quiz> activeQuizList;
    private ListView lvActiveQuizzes;
    private Toolbar toolbar;
    private ActiveQuizAdapter mAdapter;
    private DbHelper mDb;

    private HttpManager httpManager;
    private final String URL = "https://api.myjson.com/bins/874su";
    private Button btnImport;
    private List<Quiz> fromJsonList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_active_quizzes);
        setToolbar();
        init();
        incomingIntent();
        initHTTPManager();
    }

    @SuppressLint("StaticFieldLeak")
    public void initHTTPManager() {
        httpManager = new HttpManager() {
            @Override
            protected void onPostExecute(String s) {
                try {
                    fromJsonList = QuizParser.getListFromJson(s);
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), R.string.parsing_error_active, Toast.LENGTH_SHORT).show();
                }
            }
        };
        httpManager.execute(URL);
    }
    public void init() {
        lvActiveQuizzes = findViewById(R.id.lv_activequizz);
        mDb = new DbHelper(this);
        activeQuizList = mDb.getQuizzes(true);
        mAdapter = new ActiveQuizAdapter(getApplicationContext(), R.layout.lv_active_quizz_row, activeQuizList, this);
        lvActiveQuizzes.setAdapter(mAdapter);
        btnImport = findViewById(R.id.active_btn_import_from_json_api);
        btnImport.setOnClickListener(importEvent());
    }

    public View.OnClickListener importEvent() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (fromJsonList != null) {
                    for (Quiz q : fromJsonList) {
                        mDb.insertQuizz(q);
                    }
                    Toast.makeText(getApplicationContext(), String.valueOf(fromJsonList.size()) + " " + getString(R.string.import_event_activequizzes), Toast.LENGTH_SHORT).show();
                }
                mAdapter.refreshList(mDb.getQuizzes(true));
            }
        };
    }

    public void incomingIntent() {
        Intent intent = getIntent();
        Quiz recvQuiz = intent.getParcelableExtra(Constants.ACTIVATE_QUIZZ_KEY);
        if (recvQuiz != null) {
            Toast.makeText(this, getString(R.string.inc_intent_new_quizz) + recvQuiz.getTitle(), Toast.LENGTH_SHORT).show();
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == Constants.REQUEST_CODE_ACCESSTYPE_QUIZZ && data != null) {
            if (resultCode == RESULT_OK) {
                int position = data.getIntExtra("index", -1);
                Quiz currentQuiz = data.getParcelableExtra(Constants.CHANGE_ACCESSTYPE_QUIZZ_KEY);
                mDb.updateVisibility(currentQuiz, currentQuiz.getVisibility());
                mDb.updateCategory(currentQuiz, currentQuiz.getCategory());
                mDb.updateCode(currentQuiz, currentQuiz.getAccessCode());
                activeQuizList.set(position, currentQuiz);
                mAdapter.refreshList(mDb.getQuizzes(true));
            }
            if (resultCode == RESULT_CANCELED) {
                Toast.makeText(getApplicationContext(), R.string.activequizzes_nochangesmade, Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void setToolbar() {
        toolbar = findViewById(R.id.active_toolbar);
        setSupportActionBar(toolbar);
        this.setTitle("Active quizzes");
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case android.R.id.home:
                this.finish();
        }
        return true;
    }

}
