package ratingapp.ddey.com.dam_project.activities.teacher;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import ratingapp.ddey.com.dam_project.R;
import ratingapp.ddey.com.dam_project.models.Quiz;
import ratingapp.ddey.com.dam_project.utils.Constants;

public class QuizAccessTypeActivity extends AppCompatActivity {
    private TextView tvTitle;
    private Spinner spnCategory;
    private Switch switchAccessType;
    private TextView tvAccessCode;
    private TextView tvInfo;
    private Button btnOk;
    private Button btnCancel;

    private Quiz currentQuiz;
    private int index;
    private int accessCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quizz_access_type);
        init();
        incomingIntent();
    }

    public void init() {
        tvTitle = findViewById(R.id.accesstype_tv_title);
        spnCategory = findViewById(R.id.accesstype_spn_category);
        switchAccessType = findViewById(R.id.accesstype_switch);
        tvAccessCode = findViewById(R.id.accesstype_tv_accesscode);
        tvInfo = findViewById(R.id.accesstype_text);
        btnCancel = findViewById(R.id.accesstype_btn_cancel);
        btnOk = findViewById(R.id.accesstype_btn_ok);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.spn_category, R.layout.support_simple_spinner_dropdown_item);
        spnCategory.setAdapter(adapter);
        spnCategory.setSelection(0);

        switchAccessType.setOnCheckedChangeListener(onSwitch());
        btnOk.setOnClickListener(onSaveEvent());
        btnCancel.setOnClickListener(onCancelEvent());
    }

    public Switch.OnCheckedChangeListener onSwitch(){
        return new Switch.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    switchAccessType.setText("Private");
                    accessCode = (int) (Math.random() * 10000);
                    tvAccessCode.setText(String.valueOf(accessCode));
                    tvAccessCode.setVisibility(View.VISIBLE);
                    tvInfo.setVisibility(View.VISIBLE);
                }

                if (!isChecked) {
                        tvAccessCode.setText("");
                        tvAccessCode.setVisibility(View.INVISIBLE);
                        tvInfo.setVisibility(View.INVISIBLE);
                        switchAccessType.setText("Public");
                }
            }
        };
    }

    public void incomingIntent() {
        Intent returnIntent = getIntent();
        if (returnIntent != null) {
            currentQuiz = returnIntent.getParcelableExtra(Constants.CHANGE_ACCESSTYPE_QUIZZ_KEY);
            index = returnIntent.getIntExtra("index", -1);
            if (currentQuiz != null) {
                if (currentQuiz.getTitle() != null)
                    tvTitle.setText(currentQuiz.getTitle());
                if (currentQuiz.getVisibility() != null) {
                    if (currentQuiz.getVisibility().equals("Public"))
                        switchAccessType.setChecked(false);
                    else {
                        if (currentQuiz.getAccessCode() != -1) {
                            tvAccessCode.setText(String.valueOf(currentQuiz.getAccessCode()));
                        }
                        switchAccessType.setChecked(true);
                    }
                }

            }
        }
    }
    public View.OnClickListener onSaveEvent() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    if (spnCategory.getSelectedItem() != null && !spnCategory.getSelectedItem().toString().trim().isEmpty())
                        currentQuiz.setCategory(spnCategory.getSelectedItem().toString());
                    if (switchAccessType.getText().toString().equals("Private")) {
                        currentQuiz.setAccessCode(accessCode);
                        currentQuiz.setVisibility("Private");
                    } else if (switchAccessType.getText().toString().equals("Public")) {
                        currentQuiz.setVisibility("Public");
                        currentQuiz.setAccessCode(0);
                    }

                    Intent returnIntent = getIntent();
                    returnIntent.putExtra(Constants.CHANGE_ACCESSTYPE_QUIZZ_KEY, currentQuiz);
                    returnIntent.putExtra("index", index);
                    setResult(RESULT_OK, returnIntent);
                    finish();
            }
        };
    }

    public View.OnClickListener onCancelEvent() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent returnIntent = getIntent();
                setResult(RESULT_CANCELED, returnIntent);
                finish();
            }
        };
    }
}
