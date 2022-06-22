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
import tik.prometheus.mobile.models.Role;
import tik.prometheus.mobile.models.User;

import java.util.List;
import java.util.stream.Collectors;

public class UsersAdapter extends ArrayAdapter<User> {
    public UsersAdapter(@NonNull Context context, @NonNull List<User> objects) {
        super(context, android.R.layout.simple_spinner_item, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        User user = getItem(position);
        if (convertView == null) {
            convertView = (View) LayoutInflater.from(getContext()).inflate(android.R.layout.simple_spinner_item, parent, false);
            String username = user.getFullname();
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                username += " - " + user.getRoleHierarchy().getRoles().stream().map(Role::getCode).collect(Collectors.joining());
            }
            TextView tv = (TextView) convertView.findViewById(android.R.id.text1);
            tv.setText(username);
        }
        return convertView;
    }

    @Override
    public View getDropDownView(int position, @Nullable @org.jetbrains.annotations.Nullable View convertView, @NonNull @NotNull ViewGroup parent) {
        User user = getItem(position);
        if (convertView == null) {
            convertView = (View) LayoutInflater.from(getContext()).inflate(android.R.layout.simple_spinner_item, parent, false);
            String username = user.getFullname();
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                username += " - " + user.getRoleHierarchy().getRoles().stream().map(Role::getCode).collect(Collectors.joining());
            }
            TextView tv = (TextView) convertView.findViewById(android.R.id.text1);
            tv.setText(username);
            return convertView;
        }
        return convertView;
    }
}
