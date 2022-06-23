package tik.prometheus.mobile.workflow;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tik.prometheus.mobile.R;
import tik.prometheus.mobile.databinding.FragmentDocumentBinding;
import tik.prometheus.mobile.models.Document;
import tik.prometheus.mobile.models.User;
import tik.prometheus.mobile.services.HTTPConnector;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class DocumentFragment extends Fragment {
    private DocumentViewModel documentViewModel;
    private FragmentDocumentBinding binding;

    private WorkflowRepos workflowRepos;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        documentViewModel = new ViewModelProvider(this).get(DocumentViewModel.class);
        workflowRepos = HTTPConnector.createService(WorkflowRepos.class, "VYNKK", "1");
        binding = FragmentDocumentBinding.inflate(inflater, container, false);

        Spinner snUsername = binding.snUsername;
        ArrayAdapter<User> adapter = new UsersAdapter(binding.getRoot().getContext(), new ArrayList<>());
        snUsername.setAdapter(adapter);
        workflowRepos.getUsers().enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                Log.d("--->", "" + response.code());
                if (response.isSuccessful()) {
                    adapter.clear();
                    adapter.addAll(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                Log.d("--->", t.toString());
            }
        });
        onCreate(binding);
        return binding.getRoot();
    }

    private void onCreate(FragmentDocumentBinding binding) {


        ListView lvDocument = binding.lvDocument;
        ArrayAdapter<Document> documentArrayAdapter = new DocumentAdapter(binding.getRoot().getContext(), new ArrayList<>());
        lvDocument.setAdapter(documentArrayAdapter);
        workflowRepos.getDocuments().enqueue(new Callback<List<Document>>() {
            @Override
            public void onResponse(Call<List<Document>> call, Response<List<Document>> response) {
                documentArrayAdapter.clear();
                documentArrayAdapter.addAll(response.body());
            }

            @Override
            public void onFailure(Call<List<Document>> call, Throwable t) {

            }
        });
        lvDocument.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView tv = view.findViewById(android.R.id.text1);
                binding.txtLosId.setText(tv.getText());
            }
        });

        binding.ivReloadStateGuide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadStateGuide(null);
            }
        });

    }

    public void loadStateGuide(Object o) {
        User user = (User) binding.snUsername.getSelectedItem();
        workflowRepos = HTTPConnector.createService(WorkflowRepos.class, user.getUsername(), "1");
        String losId = binding.txtLosId.getText().toString();
        Log.d(DocumentFragment.class.toString(), user.getUsername());
        Consumer<Object> callback = this::loadStateGuide;
        ProgressBar progressBar = binding.pbWaiting;
        progressBar.setVisibility(View.VISIBLE);
        workflowRepos.getStateGuide(losId).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()) {
                    final LinearLayout ln = binding.lnBtnLine;
                    JsonObject data = response.body().getAsJsonObject("data");
                    String state_id = data.get("state_id").getAsString();
                    String state_name = data.get("state_name").getAsString();
                    binding.tvStateId.setText(state_id);
                    binding.tvStateName.setText(state_name);
                    JsonObject guide = data.getAsJsonObject("guide");
                    ln.removeAllViews();
                    for (Map.Entry<String, JsonElement> entry : guide.entrySet()) {
                        Button btn = ButtonMap.getButton(entry.getKey(), user.getUsername(), losId, binding.getRoot().getContext(), callback);
                        ln.addView(btn);
                    }

                } else {
                    Log.d("Load failed", String.format("%s", response.code()));
                }
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.d("HIHI", "Failure");
                progressBar.setVisibility(View.GONE);
            }
        });
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
