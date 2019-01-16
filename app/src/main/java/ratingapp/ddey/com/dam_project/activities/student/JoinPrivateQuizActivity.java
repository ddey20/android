package ratingapp.ddey.com.dam_project.activities.student;

import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import ratingapp.ddey.com.dam_project.R;
import ratingapp.ddey.com.dam_project.models.Quiz;
import ratingapp.ddey.com.dam_project.utils.others.Constants;
import ratingapp.ddey.com.dam_project.utils.database.DbHelper;

public class JoinPrivateQuizActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private TextInputEditText tieAccessCode;
    private Button btnFind;

    private DbHelper mDb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_private_quizz);
        setToolbar();
        mDb = new DbHelper(this);
        tieAccessCode = findViewById(R.id.private_join_tie_code);
        btnFind = findViewById(R.id.private_join_btn_find);
        btnFind.setOnClickListener(openInformation());
    }

    public View.OnClickListener openInformation() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long code = -1;
                Intent intent = new Intent(getApplicationContext(), QuizInformationActivity.class);
                if (!tieAccessCode.getText().toString().trim().isEmpty() && tieAccessCode.getText() != null)
                    code = Long.valueOf(tieAccessCode.getText().toString());
                    Quiz found = mDb.getPrivateQuiz(code);

                    if (found != null && code != -1) {
                        intent.putExtra(Constants.PRIVATE_QUIZ_KEY, found);
                        startActivity(intent);
                        Toast.makeText(getApplicationContext(), "The access code is correct! Here's information about this quiz!", Toast.LENGTH_SHORT).show();
                        finish();
                    }

                else
                    Toast.makeText(getApplicationContext(), "Fill in the access code", Toast.LENGTH_SHORT).show();
            }
        };
    }


    /**
     *  Metode pentru back button sus pe toolbar.
     */
    public void setToolbar() {
        toolbar = findViewById(R.id.private_join_toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        this.setTitle("Private quizzes");
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
