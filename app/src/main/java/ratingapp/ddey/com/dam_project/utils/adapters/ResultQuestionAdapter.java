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
import ratingapp.ddey.com.dam_project.models.Question;

public class ResultQuestionAdapter extends ArrayAdapter<Question> {
    private Context mContext;
    private int mResource;
    private List<Question> mList;
    private boolean[] results;

    public ResultQuestionAdapter(@NonNull Context context, int resource, List<Question> list, boolean[] results) {
        super(context, resource, list);
        this.mContext = context;
        this.mResource = resource;
        this.mList = list;
        this.results = results;
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
        boolean ifCorrect = results[position];
        if (question != null) {
            TextView tvResult = view.findViewById(R.id.tv_result);

            if (question.getQuestionText() != null) {
                String strTemp = String.valueOf(position + 1) + ". " + question.getQuestionText();
                tvResult.setText(strTemp);

                if (ifCorrect) {
                    view.setBackgroundColor(0xFF00FF00);
                } else {
                    view.setBackgroundColor(0xFFFF0000);
                }
            }
        }

        return view;
    }
}
