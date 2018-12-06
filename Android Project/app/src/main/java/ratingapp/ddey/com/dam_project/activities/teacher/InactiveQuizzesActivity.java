package ratingapp.ddey.com.dam_project.activities.teacher;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import ratingapp.ddey.com.dam_project.R;
import ratingapp.ddey.com.dam_project.models.Quiz;
import ratingapp.ddey.com.dam_project.utils.others.Constants;
import ratingapp.ddey.com.dam_project.utils.database.DbHelper;
import ratingapp.ddey.com.dam_project.utils.adapters.InactiveQuizAdapter;

public class InactiveQuizzesActivity extends AppCompatActivity {
    private List<Quiz> inactiveQuizList;

    private ListView lvQuizzes;
    private Toolbar toolbar;


    private InactiveQuizAdapter mAdapter;
    private DbHelper mDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inactive_quizzes);
        setToolbar();
        init();
    }

    public void init() {
        lvQuizzes = findViewById(R.id.lv_inactive_quizzes);

        mDb = new DbHelper(this);
        inactiveQuizList = mDb.getQuizzes(false);

        mAdapter = new InactiveQuizAdapter(getApplicationContext(), R.layout.lv_inactive_quizz_row, inactiveQuizList, this);
        lvQuizzes.setAdapter(mAdapter);

        lvQuizzes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Quiz currentQuiz = (Quiz)parent.getItemAtPosition(position);
                Intent intent = new Intent(getApplicationContext(), ActivateQuizActivity.class);
                intent.putExtra(Constants.ACTIVATE_QUIZZ_KEY, currentQuiz);
                startActivity(intent);
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == Constants.REQUEST_CODE_ACTIVATE_QUIZZ && data != null) {

            if (resultCode == RESULT_OK) {
                Quiz recvQuiz = data.getParcelableExtra(Constants.ACTIVATE_QUIZZ_KEY);
                mDb.updateQuizActivity(recvQuiz, true);
                inactiveQuizList = mDb.getQuizzes(false);
                mAdapter.notifyDataSetChanged();

                if (recvQuiz != null) {
                    Intent intent = new Intent(getApplicationContext(), ActiveQuizzesActivity.class);
                    intent.putExtra(Constants.ACTIVATE_QUIZZ_KEY, recvQuiz);
                    startActivity(intent);
                    finish();
                }
            }
            if (resultCode == RESULT_CANCELED) {
                Toast.makeText(getApplicationContext(), R.string.inactive_nochanges, Toast.LENGTH_SHORT).show();
            }
        }

        if (requestCode == Constants.REQUEST_CODE_MODIFY_QUIZZ && data != null) {
            if (resultCode == RESULT_OK) {
                int position = data.getIntExtra(Constants.LV_INDEX_MODIFY_QUIZ_KEY, -1);
                Quiz currentQuiz = data.getParcelableExtra(Constants.MODIFY_QUIZZ_KEY);
                inactiveQuizList.set(position, currentQuiz);
                //mAdapter.notifyDataSetChanged();
                mAdapter.refreshList(mDb.getQuizzes(false));
            }
            if (resultCode == RESULT_CANCELED) {
                Toast.makeText(getApplicationContext(), R.string.inactive_nochanges, Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     *  Metode pentru back button sus pe toolbar.
     */
    public void setToolbar() {
        toolbar = findViewById(R.id.inactive_toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        this.setTitle("Inactive");
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
