package ratingapp.ddey.com.dam_project.activities.teacher;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import ratingapp.ddey.com.dam_project.R;
import ratingapp.ddey.com.dam_project.models.Quiz;
import ratingapp.ddey.com.dam_project.utils.Constants;
import ratingapp.ddey.com.dam_project.utils.DbHelper;

public class ActivateQuizActivity extends AppCompatActivity {
    private TextView tvTitle;
    private RadioButton cbY;
    private RadioButton cbN;
    private Switch aSwitch;
    private TextView tvGeneratedCode;
    private TextView tvText;
    private Button btnOk;
    private Button btnCancel;

    private Quiz recv;

    private DbHelper mDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activate_quizz);
        mDb = new DbHelper(this);
        init();
    }

    public void init() {
        tvTitle = findViewById(R.id.activate_tv_title);
        cbY = findViewById(R.id.activate_radioButtonY);
        cbN = findViewById(R.id.activate_radioButtonN);
        aSwitch = findViewById(R.id.activate_switch);
        tvGeneratedCode = findViewById(R.id.activate_tv_access_code);
        tvText = findViewById(R.id.activate_tv);
        btnOk = findViewById(R.id.activate_btn_ok);
        btnCancel = findViewById(R.id.activate_btn_cancel);
        cbN.setChecked(true);

        Intent intent = getIntent();

        if (intent != null) {
            recv = intent.getParcelableExtra(Constants.ACTIVATE_QUIZZ_KEY);


            if (recv != null) {
                if (recv.getTitle() != null)
                    tvTitle.setText(recv.getTitle());
            }
        }

        aSwitch.setOnCheckedChangeListener(new Switch.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    int accessCode = (int) (Math.random() * 10000);
                    recv.setAccessCode(accessCode);
                    tvGeneratedCode.setText(String.valueOf(accessCode));
                    tvGeneratedCode.setVisibility(View.VISIBLE);
                    tvText.setVisibility(View.VISIBLE);
                } else
                {
                    tvGeneratedCode.setText("");
                    tvGeneratedCode.setVisibility(View.INVISIBLE);
                    tvText.setVisibility(View.INVISIBLE);
                }
            }
        });

        btnOk.setOnClickListener(eventOk());
        btnCancel.setOnClickListener(eventCancel());
    }

    public View.OnClickListener eventOk() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isOk()) {
                    Intent returnIntent = getIntent();
                    returnIntent.putExtra(Constants.ACTIVATE_QUIZZ_KEY, recv);
                    mDb.updateVisibility(recv, recv.getVisibility());
                    mDb.updateCode(recv, recv.getAccessCode());
                    setResult(RESULT_OK, returnIntent);
                    finish();
                }
            }
        };
    }

    public View.OnClickListener eventCancel() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent returnIntent = getIntent();
                setResult(RESULT_CANCELED, returnIntent);
                finish();
            }
        };
    }
    public boolean isOk() {
        if (cbN.isChecked()) {
            Toast.makeText(getApplicationContext(), R.string.activatequizz_checkyes,Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }



}
