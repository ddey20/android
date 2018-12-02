package ratingapp.ddey.com.dam_project.utils.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;

import ratingapp.ddey.com.dam_project.R;
import ratingapp.ddey.com.dam_project.activities.teacher.NewQuizActivity;
import ratingapp.ddey.com.dam_project.activities.teacher.ActivateQuizActivity;
import ratingapp.ddey.com.dam_project.models.Quiz;
import ratingapp.ddey.com.dam_project.utils.Constants;
import ratingapp.ddey.com.dam_project.utils.DbHelper;

public class InactiveQuizAdapter extends ArrayAdapter<Quiz> {
    private Context mContext;
    private int mResource;
    private List<Quiz> mList;
    private Activity mActivity;
    private DbHelper mDb;

    public InactiveQuizAdapter(@NonNull Context context, int resource, List<Quiz> list, Activity activity) {
        super(context, resource, list);
        this.mContext = context;
        this.mResource = resource;
        this.mList = list;
        this.mActivity = activity;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View view = convertView;

        if (view == null) {
            LayoutInflater inflater  = LayoutInflater.from(mContext);
            view = inflater.inflate(mResource, null);
        }

        Quiz quiz = getItem(position);

        if (quiz != null) {
            TextView tvTitle = view.findViewById(R.id.lv_quizz_title);
            TextView tvDescription = view.findViewById(R.id.lv_quizz_description);
            TextView tvVisibility = view.findViewById(R.id.lv_quizz_visibility);

            if (quiz.getTitle() != null) {
                tvTitle.setText(quiz.getTitle());
            }
            if (quiz.getDescription() != null) {
                tvDescription.setText(quiz.getDescription());
            }
            if (quiz.getVisibility() != null) {
                if (quiz.getVisibility().equals("Private") && quiz.getAccessCode() != 0) {
                    String strTemp = quiz.getVisibility() + "& code: " + quiz.getAccessCode();
                    tvVisibility.setText(strTemp);
                }
                else
                    tvVisibility.setText(quiz.getVisibility());
            }
        }

        ImageButton deleteButton = view.findViewById(R.id.lv_inactivequizz_imgbutton_delete);
        deleteButton.setTag(position);

        deleteButton.setOnClickListener(
                new ImageButton.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int index = (int) v.getTag();
                        Quiz q = mList.get(index);
                        mDb.deleteQuizz(q);
                        mList.remove(index);
                        notifyDataSetChanged();
                    }
                }
        );

        Button btnActivate = view.findViewById(R.id.lv_inactive_quizzes_btn_activate);
        btnActivate.setTag(position);

       // mDb = new DbHelper(mActivity);
        btnActivate.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                int index = (int) v.getTag();
                Quiz currentQuiz = mList.get(index);
                Intent intent = new Intent(mContext, ActivateQuizActivity.class);
                intent.putExtra(Constants.ACTIVATE_QUIZZ_KEY, currentQuiz);
                mActivity.startActivityForResult(intent, Constants.REQUEST_CODE_ACTIVATE_QUIZZ);
            }
        });

//        ImageButton settingsButton = view.findViewById(R.id.lv_inactivequizz_imgbutton_settings);
//        settingsButton.setTag(position);
//
//        settingsButton.setOnClickListener(
//                new ImageButton.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        int index = (int) v.getTag();
//                        Quiz currentQuiz = mList.get(index);
//                        Intent intent = new Intent(mContext, NewQuizActivity.class);
//                        intent.putExtra(Constants.MODIFY_QUIZZ_KEY, currentQuiz);
//                        intent.putExtra("index", index);
//                        mActivity.startActivityForResult(intent, Constants.REQUEST_CODE_MODIFY_QUIZZ);
//                    }
//                }
//        );

        return view;
    }


}
