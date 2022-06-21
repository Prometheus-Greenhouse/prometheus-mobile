package tik.prometheus.mobile.workflow;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import com.google.gson.JsonObject;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tik.prometheus.mobile.databinding.FragmentDocumentBinding;
import tik.prometheus.mobile.services.HTTPConnector;

public class DocumentFragment extends Fragment {
    private DocumentViewModel documentViewModel;
    private FragmentDocumentBinding binding;

    private WorkflowRepos workflowRepos;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        documentViewModel =
                new ViewModelProvider(this).get(DocumentViewModel.class);

        binding = FragmentDocumentBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textDocument;

        documentViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

        onCreate(textView);
        return root;
    }

    private void onCreate(TextView textView) {
        workflowRepos = HTTPConnector.createService(WorkflowRepos.class, "VYNKK", "1");
        workflowRepos.getStateGuide("123").enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()) {
                    textView.setText(response.body().toString());
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
