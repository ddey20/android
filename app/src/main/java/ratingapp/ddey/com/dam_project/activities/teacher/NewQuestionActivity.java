package ratingapp.ddey.com.dam_project.activities.teacher;

import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import ratingapp.ddey.com.dam_project.R;
import ratingapp.ddey.com.dam_project.models.Answer;
import ratingapp.ddey.com.dam_project.models.Question;
import ratingapp.ddey.com.dam_project.utils.Constants;

public class NewQuestionActivity extends AppCompatActivity {
    private Spinner spnDuration;
    private TextInputEditText tieQuestion;
    private TextInputEditText tieAnswer1;
    private TextInputEditText tieAnswer2;
    private TextInputEditText tieAnswer3;
    private TextInputEditText tieAnswer4;
    private Button btnAdd;
    private Button btnCancel;

    private CheckBox cb1;
    private CheckBox cb2;
    private CheckBox cb3;
    private CheckBox cb4;

    private int[] correctAnswers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_question);
        init();
    }

    private void init() {
        spnDuration = findViewById(R.id.newquestion_spn_duration);
        tieQuestion = findViewById(R.id.newquestion_tie_questiontext);
        tieAnswer1 = findViewById(R.id.newquestion_tie_answer1);
        tieAnswer2 = findViewById(R.id.newquestion_tie_answer2);
        tieAnswer3 = findViewById(R.id.newquestion_tie_answer3);
        tieAnswer4 = findViewById(R.id.newquestion_tie_answer4);

        cb1 = findViewById(R.id.newquestion_cb1_correct);
        cb2 = findViewById(R.id.newquestion_cb2_correct);
        cb3 = findViewById(R.id.newquestion_cb3_correct);
        cb4 = findViewById(R.id.newquestion_cb4_correct);

        btnAdd = findViewById(R.id.newquestion_btn_add);
        btnCancel = findViewById(R.id.newquestion_btn_cancel);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getApplicationContext(), R.array.newquestion_spn_duration, R.layout.support_simple_spinner_dropdown_item);
        spnDuration.setAdapter(adapter);

        spnDuration.setSelection(0);
        btnAdd.setOnClickListener(saveEvent());
        btnCancel.setOnClickListener(cancelEvent());
    }


    public View.OnClickListener saveEvent() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isValid()) {

                    String questionText = tieQuestion.getText().toString();
                    int answerTime = Integer.valueOf(spnDuration.getSelectedItem().toString().substring(0,3).trim());

                    List<Answer> answers = new ArrayList<>();
                    answers.add(new Answer(tieAnswer1.getText().toString()));
                    answers.add(new Answer(tieAnswer2.getText().toString()));

                    if (tieAnswer3.getText() != null && !tieAnswer3.getText().toString().trim().isEmpty())
                        answers.add(new Answer(tieAnswer3.getText().toString()));
                    if (tieAnswer4.getText() != null && !tieAnswer4.getText().toString().trim().isEmpty())
                        answers.add(new Answer(tieAnswer4.getText().toString()));

                    if (cb1.isChecked())
                        answers.get(0).setCorrect(true);
                    else
                        answers.get(0).setCorrect(false);

                    if (cb2.isChecked())
                        answers.get(1).setCorrect(true);
                    else
                        answers.get(1).setCorrect(false);

                    if (answers.size() > 2) {
                        if (cb3.isChecked())
                            answers.get(2).setCorrect(true);
                        else
                            answers.get(2).setCorrect(false);
                    }

                    if (answers.size() > 3) {
                        if (cb4.isChecked())
                            answers.get(3).setCorrect(true);
                        else
                            answers.get(3).setCorrect(false);
                    }


                    Question newQuestion = new Question(questionText, answerTime, answers);

                    Intent returnIntent = getIntent();
                    returnIntent.putExtra(Constants.ADD_QUESTION_KEY, newQuestion);
                    setResult(RESULT_OK, returnIntent);
                    finish();
                }
            }
        };
    }

    public View.OnClickListener cancelEvent() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Intent returnIntent = getIntent();
                    setResult(RESULT_CANCELED, returnIntent);
                    finish();
            }
        };
    }


    private boolean isValid() {
            if (tieQuestion.getText() == null || tieQuestion.getText().toString().trim().isEmpty()) {
                tieQuestion.setError(getString(R.string.newquestion_insertquestion_error));
                tieQuestion.requestFocus();
                return false;
            } else if (tieAnswer1.getText() == null || tieAnswer1.getText().toString().trim().isEmpty()) {
                tieAnswer1.setError(getString(R.string.newquestion_insertanswer_error));
                tieAnswer1.requestFocus();
                return false;
            } else if (tieAnswer2.getText() == null || tieAnswer2.getText().toString().trim().isEmpty()) {
                tieAnswer2.setError(getString(R.string.newquestion_insertanswer_error));
                tieAnswer2.requestFocus();
                return false;
            } else if (!cb1.isChecked() && !cb2.isChecked() && !cb3.isChecked() && !cb4.isChecked()) {
                Toast.makeText(getApplicationContext(), R.string.newquestion_pick_error, Toast.LENGTH_SHORT).show();
                return false;
            } else if (cb3.isChecked() && (tieAnswer3.getText() == null || tieAnswer3.getText().toString().trim().isEmpty())) {
                tieAnswer3.setError(getString(R.string.newquestion_unfilled_error));
                tieAnswer3.requestFocus();
                return false;
            } else if (cb4.isChecked() && (tieAnswer4.getText() == null || tieAnswer4.getText().toString().trim().isEmpty())) {
                tieAnswer4.setError(getString(R.string.newquestion_unfilled_error));
                tieAnswer4.requestFocus();
                return false;
            }
            return true;
    }
}
