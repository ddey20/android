package ratingapp.ddey.com.dam_project.activities.student;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import ratingapp.ddey.com.dam_project.R;
import ratingapp.ddey.com.dam_project.activities.general.MainActivity;
import ratingapp.ddey.com.dam_project.models.Question;
import ratingapp.ddey.com.dam_project.models.Quiz;
import ratingapp.ddey.com.dam_project.models.Result;
import ratingapp.ddey.com.dam_project.utils.others.Constants;
import ratingapp.ddey.com.dam_project.utils.database.DbHelper;
import ratingapp.ddey.com.dam_project.utils.others.Session;
import ratingapp.ddey.com.dam_project.utils.adapters.ResultQuestionAdapter;

public class QuizResultActivity extends AppCompatActivity {
    private TextView tvUser;
    private TextView tvResult;
    private ListView lvResult;
    private Button btnFinish;

    private ResultQuestionAdapter mAdapter;
    private Session mSession;
    private DbHelper mDb;
    private List<Question> questionList;
    private Quiz quiz;
    private boolean[] results;
    private int score;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quizz_result);
        init();
        incomingIntent();
    }

    public void init() {
        tvUser = findViewById(R.id.result_tv_username);
        tvResult = findViewById(R.id.result_tv_score);
        lvResult = findViewById(R.id.result_lv);
        btnFinish = findViewById(R.id.result_btn_return);

        mSession = new Session(this);
        mDb = new DbHelper(this);
        String currentUser = mDb.getName(mSession);
        String strTemp = "Congratulations " + currentUser + ", you finished the quiz!";
        tvUser.setText(strTemp);

        btnFinish.setOnClickListener(goToMainMenuEvent());
    }

    public void incomingIntent() {
        Intent intent = getIntent();
        if (intent != null) {
            score = intent.getIntExtra(Constants.RESULT_SCORE_KEY, -1);
            tvResult.setText("Score: " + String.valueOf(score));

            results = intent.getBooleanArrayExtra(Constants.RESULT_ANSWERS_KEY);
            quiz = intent.getParcelableExtra(Constants.RESULT_QUIZZ_KEY);
            questionList = quiz.getQuestionsList();
            mAdapter = new ResultQuestionAdapter(getApplicationContext(), R.layout.lv_result_question_row, questionList, results);
            lvResult.setAdapter(mAdapter);

            //inserare rezultat in baza de date
            DateFormat date = new SimpleDateFormat("MMM dd yyyy, h:mm");
            String dateFormat = date.format(Calendar.getInstance().getTime());
            Result newResult = new Result(mDb.getName(mSession), quiz.getTitle(), score, dateFormat);
            mDb.insertResult(newResult);

        }
    }

    public View.OnClickListener goToMainMenuEvent() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        };
    }
}
