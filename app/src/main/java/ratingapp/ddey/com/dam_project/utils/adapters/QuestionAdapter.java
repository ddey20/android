package ratingapp.ddey.com.dam_project.utils.adapters;

import android.content.Context;
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
import ratingapp.ddey.com.dam_project.models.Answer;
import ratingapp.ddey.com.dam_project.models.Question;

public class QuestionAdapter extends ArrayAdapter<Question> {
    private Context mContext;
    private int mResource;
    private List<Question> mList;

    public QuestionAdapter(@NonNull Context context, int resource, List<Question> list) {
        super(context, resource, list);
        this.mContext = context;
        this.mResource = resource;
        this.mList = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

       View view = convertView;

       if (view == null) {
           LayoutInflater inflater  = LayoutInflater.from(mContext);
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
                if (a.isCorrect())
                    nrCorrect++;
            }
            String strTemp = mContext.getString(R.string.questionadapter_nrcorrect) + nrCorrect;
            tvCorrectAnswers.setText(strTemp);
           }
       }

        ImageButton deleteButton = view.findViewById(R.id.imgbutton_delete);
        deleteButton.setTag(position);

        deleteButton.setOnClickListener(
                new ImageButton.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int index = (int) v.getTag();
                        mList.remove(index);
                        notifyDataSetChanged();
                    }
                }
        );


        return view;
    }
}
