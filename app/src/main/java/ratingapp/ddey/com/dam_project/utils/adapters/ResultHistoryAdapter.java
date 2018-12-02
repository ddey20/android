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

public class ResultHistoryAdapter extends ArrayAdapter<Result> {
    private Context mContext;
    private int mResource;
    private List<Result> mList;
    private static int nrResults = 1;

    public ResultHistoryAdapter(@NonNull Context context, int resource, List<Result> list) {
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

        TextView tvNr = view.findViewById(R.id.tl_history_headerNumber);
        TextView tvScore = view.findViewById(R.id.tl_history_headerQuizzResult);
        TextView tvQuizzName = view.findViewById(R.id.tl_history_headerQuizzName);

        Result result = getItem(position);

        if (result != null) {
            if (result.getQuizzName() != null) {
                tvQuizzName.setText(result.getQuizzName());
            }

            tvScore.setText(String.valueOf(result.getScore()));
            tvNr.setText(String.valueOf(nrResults++));
        }

        return view;
    }
}
