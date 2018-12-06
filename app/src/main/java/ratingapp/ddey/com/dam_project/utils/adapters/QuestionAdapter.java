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
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;

import ratingapp.ddey.com.dam_project.R;
import ratingapp.ddey.com.dam_project.activities.teacher.NewQuestionActivity;
import ratingapp.ddey.com.dam_project.models.Answer;
import ratingapp.ddey.com.dam_project.models.Question;
import ratingapp.ddey.com.dam_project.utils.others.Constants;
import ratingapp.ddey.com.dam_project.utils.database.DbHelper;

public class QuestionAdapter extends ArrayAdapter<Question> {
    private Context mContext;
    private int mResource;
    private List<Question> mList;
    private Activity mActivity;

    private DbHelper mDb;
    private int globalPosition;

    public QuestionAdapter(@NonNull Context context, int resource, List<Question> list, Activity activity) {
        super(context, resource, list);
        this.mContext = context;
        this.mResource = resource;
        this.mList = list;
        this.mActivity = activity;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View view = convertView;
        globalPosition = position;

        if (view == null) {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            view = inflater.inflate(mResource, null);
        }

        Question question = getItem(position);

        if (question != null) {
            TextView tvNrAnswers = view.findViewById(R.id.lv_question_nranswers);
            TextView tvQuestion = view.findViewById(R.id.lv_question_text);
            TextView tvCorrectAnswers = view.findViewById(R.id.lv_question_nrcorrectanswers);

            if (question.getAnswersList().size() > 0) {
                String strTemp = mContext.getString(R.string.questionadapter_nranswer) + String.valueOf(question.getAnswersList().size());
                tvNrAnswers.setText(strTemp);
            }

            if (question.getQuestionText() != null) {
                tvQuestion.setText(question.getQuestionText());
            }

            if (question.getAnswersList() != null) {
                int nrCorrect = 0;
                for (Answer a : question.getAnswersList()) {
                    if (a != null) {
                        if (a.isCorrect())
                            nrCorrect++;
                    }
                }
                String strTemp = mContext.getString(R.string.questionadapter_nrcorrect) + nrCorrect;
                tvCorrectAnswers.setText(strTemp);
            }
        }

        mDb = new DbHelper(mActivity);
        ImageButton deleteButton = view.findViewById(R.id.imgbutton_delete);
        deleteButton.setTag(position);

        deleteButton.setOnClickListener(deleteEvent());

        ImageButton modifyButton = view.findViewById(R.id.imgbutton_modify);
        modifyButton.setTag(position);

        modifyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int index = (int) v.getTag();
                Question currentQuestion = mList.get(index);
                Intent intent = new Intent(mActivity, NewQuestionActivity.class);
                intent.putExtra(Constants.MODIFY_QUESTION_KEY, currentQuestion);
                intent.putExtra(Constants.LV_INDEX_MODIFY_QUESTION_KEY, index);
                mActivity.startActivityForResult(intent, Constants.REQUEST_CODE_MODIFY_QUESTION);
            }
        });

        return view;
    }

    @NonNull
    private ImageButton.OnClickListener deleteEvent() {
        return new ImageButton.OnClickListener() {
            @Override
            public void onClick(View v) {
                int index = (int) v.getTag();
                Question currentQuestion = mList.get(index);
                mDb.deleteQuestionSolely(currentQuestion);
                mList.remove(index);
                notifyDataSetChanged();
            }
        };
    }


    public void refreshList(List<Question> list) {
        mList.clear();
        mList.addAll(list);
        notifyDataSetChanged();
    }

}
