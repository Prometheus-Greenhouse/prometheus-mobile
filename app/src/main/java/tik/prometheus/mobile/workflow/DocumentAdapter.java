package tik.prometheus.mobile.workflow;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import org.jetbrains.annotations.NotNull;
import tik.prometheus.mobile.models.Document;

import java.util.List;

public class DocumentAdapter extends ArrayAdapter<Document> {
    public DocumentAdapter(@NonNull Context context, List<Document> items) {
        super(context, android.R.layout.simple_list_item_2, items);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Document doc = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(android.R.layout.simple_list_item_2, parent, false);
            TextView tvLosId = convertView.findViewById(android.R.id.text1);
            TextView tvStateName = convertView.findViewById(android.R.id.text2);
            tvLosId.setText(doc.getLosId());
            tvStateName.setText(doc.getStateName());
        }
        return convertView;
    }

    @Override
    public View getDropDownView(int position, @Nullable @org.jetbrains.annotations.Nullable View convertView, @NonNull @NotNull ViewGroup parent) {
        Document doc = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(android.R.layout.simple_list_item_2, parent, false);
            TextView tvLosId = convertView.findViewById(android.R.id.text1);
            TextView tvStateName = convertView.findViewById(android.R.id.text2);
            tvLosId.setText(doc.getLosId());
            tvStateName.setText(doc.getStateName());
        }
        return convertView;
    }
}
