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
import android.widget.TextView;

import java.util.List;

import ratingapp.ddey.com.dam_project.R;
import ratingapp.ddey.com.dam_project.activities.student.QuizInformationActivity;
import ratingapp.ddey.com.dam_project.models.Quiz;
import ratingapp.ddey.com.dam_project.utils.Constants;

public class PublicQuizAdapter extends ArrayAdapter<Quiz> {
    private Context mContext;
    private int mResource;
    private List<Quiz> mList;
    private Activity mActivity;

    public PublicQuizAdapter(@NonNull Context context, int resource, @NonNull List<Quiz> list, Activity activity) {
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
            LayoutInflater inflater = LayoutInflater.from(mContext);
            view = inflater.inflate(mResource, null);
        }

        final Quiz quiz = getItem(position);

        if (quiz != null) {
            TextView tvTitle = view.findViewById(R.id.publicquizz_tv_title);
            TextView tvDescription = view.findViewById(R.id.publicquizz_tv_description);


            if (quiz.getTitle() != null) {
                tvTitle.setText(quiz.getTitle());
            }
            if (quiz.getDescription() != null) {
                tvDescription.setText(quiz.getDescription());
            }
        }

        Button btnJoin = view.findViewById(R.id.publicquizz_btn_join);
        btnJoin.setTag(position);

        btnJoin.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(final View v) {

                                int index = (int) v.getTag();
                                Quiz currentQuiz = mList.get(index);
                                Intent intent = new Intent(mContext, QuizInformationActivity.class);
                                intent.putExtra(Constants.SHOW_QUIZZ_INFO_KEY, currentQuiz);
                                mActivity.startActivity(intent);
                                notifyDataSetChanged();
            }
        });


        return view;
    }
}
