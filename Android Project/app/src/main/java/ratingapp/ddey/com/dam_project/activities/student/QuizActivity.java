package ratingapp.ddey.com.dam_project.activities.student;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Collections;
import java.util.List;

import ratingapp.ddey.com.dam_project.R;
import ratingapp.ddey.com.dam_project.models.Question;
import ratingapp.ddey.com.dam_project.models.Quiz;
import ratingapp.ddey.com.dam_project.utils.others.Constants;

public class QuizActivity extends AppCompatActivity {
    private Button btnNext;
    private TextView question;
    private TextView timer;
    private TextView counter;

    private CheckBox cb1;
    private CheckBox cb2;
    private CheckBox cb3;
    private CheckBox cb4;

    private TextView tv1;
    private TextView tv2;
    private TextView tv3;
    private TextView tv4;

    private Quiz quiz;
    private List<Question> questionsList;
    private Question currentQuestion;
    private static int contor = 0;
    private int timeLeft;
    private int score = 0;
    private boolean outOftime = false;
    private CountDownTimer mCountdownTimer;
    private boolean[] resultQuestions;

    /*
    Verific daca contorul a depasit numarul de intrebari.
    Daca inca ma incadrez in lista -> dau clear la cb, inlocuiesc textviewurile, preiau raspunsurile corecte in correctAnswers.
    Folosesc clasa CountDownTimer pentru a realiza timerul, in cazul in care expira raspunsul va fi gresit indiferent de alegere.
    Folosesc isAnswerCorrect() si isAnswerPicked() pt a verifica daca raspunsurile sunt corecte sau daca studentul a ales vreo varianta.
    In nextEvent() incrementez scorul si salvez intrebarile la care s-a raspuns corect sau nu.
    In momentul in care se termina quizzul, buttonul de next devine "Finish" si se va transmite catre QuizResultActivity: scorul, quizzul, si un vector de boolean care arata care au fost intrebarile corecte/gresite.
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quizz);
        incomingIntent();
        init();
    }

    public void init() {

        question = findViewById(R.id.quizz_tv_question_text);
        timer = findViewById(R.id.quizz_tv_timer);
        counter = findViewById(R.id.quizz_tv_question_counter);

        cb1 = findViewById(R.id.quizz_cb1);
        cb2 = findViewById(R.id.quizz_cb2);
        cb3 = findViewById(R.id.quizz_cb3);
        cb4 = findViewById(R.id.quizz_cb4);

        tv1 = findViewById(R.id.quizz_tv1);
        tv2 = findViewById(R.id.quizz_tv2);
        tv3 = findViewById(R.id.quizz_tv3);
        tv4 = findViewById(R.id.quizz_tv4);

        btnNext = findViewById(R.id.quizz_btn_next);
        btnNext.setOnClickListener(nextEvent());

        contor = 0;
        questionsList = quiz.getQuestionsList();
        Collections.shuffle(questionsList);
        resultQuestions = new boolean[questionsList.size()];
        nextQuestion();
    }


    public View.OnClickListener nextEvent() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isAnythingPicked()) {
                    mCountdownTimer.cancel();
                    int valTemp = contor;
                    if (isAnswerCorrect() && !outOftime) {
                        score += 10;
                        valTemp = contor;
                        Toast.makeText(getApplicationContext(), R.string.quizz_activity_correct_answer, Toast.LENGTH_SHORT).show();
                        resultQuestions[valTemp-1] = true;
                    } else
                    {
                        if (outOftime){
                            Toast.makeText(getApplicationContext(), R.string.quizz_outoftime, Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getApplicationContext(), R.string.quizz_wrong, Toast.LENGTH_SHORT).show();
                        }
                        resultQuestions[valTemp-1] = false;
                    }
                    nextQuestion();
                }
                else
                {
                    Toast.makeText(getApplicationContext(), R.string.quizz_pickanswer, Toast.LENGTH_SHORT).show();
                }
            }
        };
    }

    public void nextQuestion() {

        if (contor < questionsList.size()) {
            cb1.setChecked(false);
            cb2.setChecked(false);
            cb3.setChecked(false);
            cb4.setChecked(false);

            currentQuestion = questionsList.get(contor);

            //raspunsuri

            tv1.setText(currentQuestion.getAnswersList().get(0).getAnswer());
            tv2.setText(currentQuestion.getAnswersList().get(1).getAnswer());
            tv3.setText(currentQuestion.getAnswersList().get(2).getAnswer());
            tv4.setText(currentQuestion.getAnswersList().get(3).getAnswer());

            //intrebare
            question.setText(currentQuestion.getQuestionText());

            //time left
            timeLeft = currentQuestion.getAnswerTime();
            timer.setText(String.valueOf(timeLeft));
            long millisInFuture = timeLeft * 1000;

            outOftime = false;
            mCountdownTimer = new CountDownTimer(millisInFuture, 1000) {

                public void onTick(long millisUntilFinished) {
                    timer.setText(String.valueOf(millisUntilFinished / 1000));
                }

                public void onFinish() {
                    timer.setText("Out of time!");
                    outOftime = true;
                }
            }.start();


            //contor
            contor++;
            String str = contor + "/" + questionsList.size();
            counter.setText(str);

            if (contor == questionsList.size())
                btnNext.setText("Finish");
        }
        else {
            mCountdownTimer.cancel();

            Intent intent = new Intent(getApplicationContext(), QuizResultActivity.class);
            intent.putExtra(Constants.RESULT_SCORE_KEY, score);
            intent.putExtra(Constants.RESULT_ANSWERS_KEY, resultQuestions);
            intent.putExtra(Constants.RESULT_QUIZZ_KEY, quiz);
            startActivity(intent);
            finish();
        }
    }

    public boolean isAnswerCorrect() {
            if (cb1.isChecked() && !currentQuestion.getAnswersList().get(0).isCorrect())
                return false;
            if (cb2.isChecked() && !currentQuestion.getAnswersList().get(1).isCorrect())
                return false;
            if (cb3.isChecked() && !currentQuestion.getAnswersList().get(2).isCorrect())
                return false;
            if (cb4.isChecked() && !currentQuestion.getAnswersList().get(3).isCorrect())
                return false;
            if (currentQuestion.getAnswersList().get(0).isCorrect() && !cb1.isChecked())
                return false;
            if (currentQuestion.getAnswersList().get(1).isCorrect() && !cb2.isChecked())
                return false;
            if (currentQuestion.getAnswersList().get(2).isCorrect() && !cb3.isChecked())
                return false;
            if (currentQuestion.getAnswersList().get(3).isCorrect() && !cb4.isChecked())
                return false;
            return true;
    }

    public boolean isAnythingPicked() {
        if (!cb1.isChecked() && !cb2.isChecked() && !cb3.isChecked() && !cb4.isChecked())
            return false;
        return true;
    }

    public void incomingIntent() {
        Intent intent = getIntent();
        if (intent != null) {
            quiz = intent.getParcelableExtra(Constants.START_QUIZZ_KEY);

            if (quiz != null) {
                questionsList = quiz.getQuestionsList();
            }
        }
    }
}
