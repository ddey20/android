package ratingapp.ddey.com.dam_project.utils.adapters;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import ratingapp.ddey.com.dam_project.models.Quiz;
import ratingapp.ddey.com.dam_project.models.Result;
import ratingapp.ddey.com.dam_project.utils.DbHelper;

public class StudentResultsAdapter extends ArrayAdapter<Result> {
    private Context mContext;
    private int mResource;
    private List<Result> mList;
    private Activity mActivity;

    private DbHelper mDb;

    public StudentResultsAdapter(@NonNull Context context, int resource, @NonNull List<Result> list, Activity activity) {
        super(context, resource, list);
        mContext = context;
        mResource = resource;
        mList = list;
        mActivity = activity;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View view = convertView;

        if (view == null) {
            LayoutInflater inflater  = LayoutInflater.from(mContext);
            view = inflater.inflate(mResource, null);
        }

        TextView tvQuizz = view.findViewById(R.id.lv_student_results_quizzname);
        TextView tvName = view.findViewById(R.id.lv_student_results_student_name);
        TextView tvScore = view.findViewById(R.id.lv_student_results_score);
        TextView tvDate = view.findViewById(R.id.lv_student_results_date);

        final Result r = getItem(position);

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


        // DELETE RESULT
        mDb = new DbHelper(mActivity);
        view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(mActivity);
                dialog.setMessage(mContext.getString(R.string.student_delete_dialog_message))
                        .setTitle(mContext.getString(R.string.student_delete_dialog_title))
                        .setPositiveButton(mContext.getString(R.string.student_delete_dialog_yes), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                mList.remove(position);
                                notifyDataSetChanged();
                                mDb.deleteResult(r);
                            }
                        })
                        .setNegativeButton(mContext.getString(R.string.student_delete_dialog_no), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });

                dialog.create().show();
                return true;
            }
        });
        return view;
    }
}


