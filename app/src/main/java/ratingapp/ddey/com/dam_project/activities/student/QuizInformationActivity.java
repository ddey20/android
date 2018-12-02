package ratingapp.ddey.com.dam_project.activities.student;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import ratingapp.ddey.com.dam_project.R;
import ratingapp.ddey.com.dam_project.models.Question;
import ratingapp.ddey.com.dam_project.models.Quiz;
import ratingapp.ddey.com.dam_project.utils.Constants;

public class QuizInformationActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView tvTitle;
    private TextView tvDesc;
    private TextView tvNrQuestions;
    private TextView tvTime;
    private RadioButton rb1;
    private RadioButton rb2;
    private Button btnStart;
    private Button btnBack;

    private Quiz currentQuiz;
    private int totalTime = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quizz_information);
        init();
    }

    public void init() {
        tvTitle = findViewById(R.id.info_title);
        tvDesc = findViewById(R.id.info_description);
        tvNrQuestions = findViewById(R.id.info_nr_questions);
        tvTime = findViewById(R.id.info_total_time);
        rb1 = findViewById(R.id.info_rb_1);
        rb2 = findViewById(R.id.info_rb_2);
        btnStart = findViewById(R.id.info_btn_start);
        btnBack = findViewById(R.id.info_btn_back);

        incomingIntent();

        btnStart.setOnClickListener(this);
        btnBack.setOnClickListener(this);
    }

    public void incomingIntent() {
        Intent intent = getIntent();
        if (intent != null) {

            //public quiz
            currentQuiz = intent.getParcelableExtra(Constants.SHOW_QUIZZ_INFO_KEY);
            if (currentQuiz != null) {
                if (currentQuiz.getTitle() != null) {
                    tvTitle.setText(currentQuiz.getTitle());
                }
                if (currentQuiz.getDescription() != null) {
                    tvDesc.setText(currentQuiz.getDescription());
                }
                if (currentQuiz.getQuestionsList() != null) {
                    String strTemp = "A total of " + currentQuiz.getQuestionsList().size() + " questions!";
                    tvNrQuestions.setText(strTemp);

                    for (Question q : currentQuiz.getQuestionsList()) {
                        if (q != null)
                            totalTime += q.getAnswerTime();
                    }
                    String strTemp2 = "Total time: " + String.valueOf(totalTime);
                    tvTime.setText(strTemp2);
                }
                rb2.setChecked(true);
                totalTime = 0;
            } else {
                //private quiz
                currentQuiz = intent.getParcelableExtra(Constants.PRIVATE_QUIZ_KEY);
                if (currentQuiz != null) {
                    if (currentQuiz.getTitle() != null) {
                        tvTitle.setText(currentQuiz.getTitle());
                    }
                    if (currentQuiz.getDescription() != null) {
                        tvDesc.setText(currentQuiz.getDescription());
                    }
                    if (currentQuiz.getQuestionsList() != null) {
                        String strTemp = "A total of " + currentQuiz.getQuestionsList().size() + " questions!";
                        tvNrQuestions.setText(strTemp);

                        for (Question q : currentQuiz.getQuestionsList()) {
                            if (q != null)
                                totalTime += q.getAnswerTime();
                        }
                        String strTemp2 = "Total time: " + String.valueOf(totalTime);
                        tvTime.setText(strTemp2);
                    }
                    rb2.setChecked(true);
                    totalTime = 0;
                }
            }

        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.info_btn_start:
                if (rb1.isChecked()) {
                    Intent intent = new Intent(getApplicationContext(), QuizActivity.class);
                    intent.putExtra(Constants.START_QUIZZ_KEY, currentQuiz);
                    startActivity(intent);
                    finish();
                }
                else
                    Toast.makeText(getApplicationContext(), R.string.quizzinfo_checkyes, Toast.LENGTH_SHORT).show();
                break;
            case R.id.info_btn_back:
                finish();
                break;
            default:
                break;
        }
    }
}
