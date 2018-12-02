package ratingapp.ddey.com.dam_project.activities.teacher;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import ratingapp.ddey.com.dam_project.R;
import ratingapp.ddey.com.dam_project.models.Question;
import ratingapp.ddey.com.dam_project.models.Quiz;
import ratingapp.ddey.com.dam_project.utils.Constants;
import ratingapp.ddey.com.dam_project.utils.DbHelper;
import ratingapp.ddey.com.dam_project.utils.adapters.QuestionAdapter;

public class NewQuizActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TextInputEditText tieTitle;
    private TextInputEditText tieDescription;
    private Spinner spnVisibility;
    private Spinner spnCategory;
    private ListView lvQuestions;
    private Button btnCreate;
    private FloatingActionButton btnAdd;

    private List<Question> questionList;
    private QuestionAdapter mAdapter;

    private int semaforNewOrModify;
    private int index;

    private DbHelper mDb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_quizz);
        setToolbar();
        mDb = new DbHelper(this);
        init();
        incomingIntent();
    }

    private void init() {
        tieTitle = findViewById(R.id.newquizz_tie_title);
        tieDescription = findViewById(R.id.newquizz_tie_description);
        spnVisibility = findViewById(R.id.newquizz_spn_visibility);
        spnCategory = findViewById(R.id.newquizz_spn_category);
        btnCreate = findViewById(R.id.newquizz_btn_create);
        btnAdd = findViewById(R.id.newquizz_btn_addquestion);
        lvQuestions = findViewById(R.id.newquizz_lv_questions);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getApplicationContext(), R.array.newquizz_spn_visibility, R.layout.support_simple_spinner_dropdown_item);
        spnVisibility.setAdapter(adapter);
        spnVisibility.setSelection(0);

        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(getApplicationContext(), R.array.spn_category, R.layout.support_simple_spinner_dropdown_item);
        spnCategory.setAdapter(adapter2);
        spnCategory.setSelection(0);

        questionList = new ArrayList<>();

        mAdapter = new QuestionAdapter(getApplicationContext(), R.layout.lv_questions_row, questionList);
        lvQuestions.setAdapter(mAdapter);

        btnAdd.setOnClickListener(addEvent());
        btnCreate.setOnClickListener(saveEvent());

        semaforNewOrModify = 0;
    }

    public void incomingIntent() {
        Intent intent = getIntent();

        if (intent != null) {
            Quiz modifyQuiz = intent.getParcelableExtra(Constants.MODIFY_QUIZZ_KEY);
            index = intent.getIntExtra("index", -1);

            if (modifyQuiz != null) {
                semaforNewOrModify = 1;
                if (modifyQuiz.getTitle() != null) {
                    tieTitle.setText(modifyQuiz.getTitle());
                }
                if (modifyQuiz.getDescription() != null) {
                    tieDescription.setText(modifyQuiz.getDescription());
                }
                if (modifyQuiz.getQuestionsList() != null) {
                    questionList = modifyQuiz.getQuestionsList();
                    mAdapter.notifyDataSetChanged();
                }
            }
        }
    }

    public View.OnClickListener addEvent() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), NewQuestionActivity.class);
                startActivityForResult(intent, Constants.REQUEST_CODE_ADD_QUESTION);
            }
        };
    }

    public View.OnClickListener saveEvent() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isValid()) {

                    String title = tieTitle.getText().toString();
                    String description = tieDescription.getText().toString();
                    String visibility = spnVisibility.getSelectedItem().toString();
                    String category = spnCategory.getSelectedItem().toString();
                    long accessCode = 0;

                    if (visibility.equals("Private")) {
                        accessCode = (int) (Math.random() * 10000);
                    }

                    Quiz newQuiz = new Quiz(title, description, visibility, accessCode, questionList, category);

                    if (semaforNewOrModify == 0) {
                        Intent returnIntent = getIntent();
                        setResult(RESULT_OK, returnIntent);

                        //default se duce in inactive
                        newQuiz.setActive(false);
                        mDb.insertQuizz(newQuiz);

                        Toast.makeText(getApplicationContext(), R.string.newquizz_created, Toast.LENGTH_SHORT).show();
                        finish();
                    } else if (semaforNewOrModify == 1) {
                        Intent returnIntent = getIntent();
                        returnIntent.putExtra(Constants.MODIFY_QUIZZ_KEY, newQuiz);
                        returnIntent.putExtra("index", index);
                        setResult(RESULT_OK, returnIntent);

                        Toast.makeText(getApplicationContext(), R.string.newquizz_modified, Toast.LENGTH_SHORT).show();
                        finish();
                    }

                } else {
                    Toast.makeText(getApplicationContext(), R.string.newquizz_error, Toast.LENGTH_SHORT).show();
                }

            }
        };
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == Constants.REQUEST_CODE_ADD_QUESTION) {

            if (resultCode == RESULT_OK && data != null) {
                Question receivedQuestion = data.getParcelableExtra(Constants.ADD_QUESTION_KEY);

                if (receivedQuestion != null) {
                    Toast.makeText(getApplicationContext(), R.string.newquizz_add_success, Toast.LENGTH_SHORT).show();
                    questionList.add(receivedQuestion);
                    mAdapter.notifyDataSetChanged();
                }
            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(getApplicationContext(), R.string.newquizz_add_failed,
                        Toast.LENGTH_LONG).show();
            }

        }
    }


    private boolean isValid() {
        if (tieTitle.getText() == null || tieTitle.getText().toString().trim().isEmpty()) {
            tieTitle.setError(getString(R.string.newquizz_title_error));
            tieTitle.requestFocus();
            return false;
        } else if (tieDescription.getText() == null || tieDescription.getText().toString().trim().isEmpty()) {
            tieDescription.setError(getString(R.string.newquizz_desc_error));
            tieDescription.requestFocus();
            return false;
        } else if (questionList.size() < 1) {
            Toast.makeText(getApplicationContext(), R.string.newquizz_questionnr_error, Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    /**
     *  Metode pentru back button sus pe toolbar.
     */
    public void setToolbar() {
        toolbar = findViewById(R.id.newquizz_toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        this.setTitle("Quiz details");
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
