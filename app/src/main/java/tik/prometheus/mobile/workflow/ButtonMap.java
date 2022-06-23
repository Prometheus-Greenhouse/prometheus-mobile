package tik.prometheus.mobile.workflow;

import android.content.Context;
import android.content.res.Resources;
import android.os.Build;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import androidx.annotation.RequiresApi;
import com.google.gson.JsonObject;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tik.prometheus.mobile.R;
import tik.prometheus.mobile.services.HTTPConnector;

import java.util.function.Consumer;

public class ButtonMap {
    public static class ButtonRequest {
        public String action;
        public String username;
        public String losId;

        public DocumentFragment fragment;

        public ButtonRequest(String action, String username, String losId, DocumentFragment fragment) {
            this.action = action;
            this.username = username;
            this.losId = losId;
            this.fragment = fragment;
        }
    }

    private static void onClick(ButtonRequest request) {
        WorkflowRepos workflowRepos = HTTPConnector.createService(WorkflowRepos.class, request.username, "1");


        workflowRepos.action(request.action, request.losId).enqueue(new Callback<JsonObject>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                Log.d(ButtonMap.class.toString(), request.action + response.code());
                request.fragment.loadStateGuide(null);
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
            }
        });
    }

    public static Button getButton(String code, String username, String losId, DocumentFragment fragment, Context context) {
        Button btn = new Button(context);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1.0f);
        lp.setMargins(5, 0, 5, 0);
        btn.setLayoutParams(lp);
        ButtonRequest req = new ButtonRequest("", username, losId, fragment);
        switch (code) {
            case "apply_approve": {
                btn.setText("Trình PD");
                req.action = "apply/approve";
                break;
            }
            case "apply_control": {
                btn.setText("Trình KS");
                req.action = "apply/control";
                break;
            }
            case "approve": {
                btn.setText("Duyệt");
                req.action = "approve";
                break;
            }
            case "close": {
                btn.setText("Đóng");
                req.action = "close";
                break;
            }
            case "freeze": {
                btn.setText("Phong tỏa");
                req.action = "freeze";
                break;
            }
            case "process": {
                btn.setText("Xử lý");
                req.action = "process";
                break;
            }
            case "return_init": {
                btn.setText("Chuyển trả");
                req.action = "return/init";
                break;
            }
            case "save": {
                btn.setText("Lưu");
                req.action = "save";
                break;
            }
        }
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ButtonMap.onClick(req);
            }
        });
        TypedValue typedValue = new TypedValue();
        Resources.Theme theme = context.getTheme();
        theme.resolveAttribute(R.attr.colorPrimary, typedValue, true);
        btn.setBackgroundColor(typedValue.data);
        theme.resolveAttribute(R.attr.colorOnPrimary, typedValue, true);
        btn.setTextColor(typedValue.data);
        return btn;
    }
}
