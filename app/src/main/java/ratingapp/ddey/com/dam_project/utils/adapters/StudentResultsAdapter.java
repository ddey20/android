package ratingapp.ddey.com.dam_project.utils.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import ratingapp.ddey.com.dam_project.R;
import ratingapp.ddey.com.dam_project.models.Result;

public class StudentResultsAdapter extends ArrayAdapter<Result> {
    private Context mContext;
    private int mResource;
    private List<Result> mList;

    public StudentResultsAdapter(@NonNull Context context, int resource, @NonNull List<Result> list) {
        super(context, resource, list);
        mContext = context;
        mResource = resource;
        mList = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View view = convertView;

        if (view == null) {
            LayoutInflater inflater  = LayoutInflater.from(mContext);
            view = inflater.inflate(mResource, null);
        }

        TextView tvQuizz = view.findViewById(R.id.lv_student_results_quizzname);
        TextView tvName = view.findViewById(R.id.lv_student_results_student_name);
        TextView tvScore = view.findViewById(R.id.lv_student_results_score);
        TextView tvDate = view.findViewById(R.id.lv_student_results_date);

        Result r = getItem(position);

        if (r != null) {
            if (r.getQuizzName() != null) {
                tvQuizz.setText(r.getQuizzName());
            }
            if (r.getStudentName() != null) {
                tvName.setText(r.getStudentName());
            }
            if (r.getScore() != -1) {
                String strTemp = "Score: " + String.valueOf(r.getScore());
                tvScore.setText(strTemp);
            }
            if (r.getDateTime() != null) {
                String strTemp = "Date: " + r.getDateTime();
                tvDate.setText(strTemp);
            }
        }
        return view;
    }
}


