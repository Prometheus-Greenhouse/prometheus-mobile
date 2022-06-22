package tik.prometheus.mobile.workflow;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tik.prometheus.mobile.databinding.FragmentDocumentBinding;
import tik.prometheus.mobile.services.HTTPConnector;

import java.util.Map;
import java.util.function.Consumer;

public class DocumentFragment extends Fragment {
    private DocumentViewModel documentViewModel;
    private FragmentDocumentBinding binding;

    private WorkflowRepos workflowRepos;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        documentViewModel = new ViewModelProvider(this).get(DocumentViewModel.class);

        binding = FragmentDocumentBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textDocument;
        Spinner snUsername = binding.snUsername;

        documentViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        documentViewModel.getUsername().observe(getViewLifecycleOwner(), new Observer<String[]>() {
            @Override
            public void onChanged(String[] strings) {
                snUsername.setAdapter(
                        new ArrayAdapter<>(binding.getRoot().getContext(), android.R.layout.simple_spinner_item, strings)
                );
            }
        });

        onCreate(binding);
        return root;
    }

    private void onCreate(FragmentDocumentBinding binding) {
        binding.ivReloadStateGuide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadStateGuide(null);
            }
        });

    }

    public void loadStateGuide(Object o) {
        String username = (String) binding.snUsername.getSelectedItem();
        String losId = binding.txtLosId.getText().toString();
        workflowRepos = HTTPConnector.createService(WorkflowRepos.class, username, "1");
        Consumer<Object> callback = this::loadStateGuide;
        workflowRepos.getStateGuide(losId).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()) {
                    final TextView textView = binding.textDocument;
                    textView.setText(response.body().toString());

                    final LinearLayout ln = binding.lnBtnLine;
                    JsonObject data = response.body().getAsJsonObject("data");
                    String state_id = data.get("current_state_id").getAsString();
                    binding.tvStateId.setText(state_id);
                    binding.tvStateName.setText(state_id);
                    JsonObject guide = data.getAsJsonObject("guide");
                    ln.removeAllViews();
                    for (Map.Entry<String, JsonElement> entry : guide.entrySet()) {
                        Button btn = ButtonMap.getButton(entry.getKey(), binding.getRoot().getContext(), callback);
                        ln.addView(btn);
                    }

                } else {
                    Log.d("Load failed", String.format("%s", response.code()));
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.d("HIHI", "Failure");
            }
        });
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
