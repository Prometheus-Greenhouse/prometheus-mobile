package tik.prometheus.mobile.workflow;

import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import com.google.gson.JsonObject;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tik.prometheus.mobile.services.HTTPConnector;

import java.util.function.Consumer;

public class ButtonMap {
    public static class ButtonRequest {
        public String action;
        public String username;
        public String losId;

        public ButtonRequest(String action, String username, String losId) {
            this.action = action;
            this.username = username;
            this.losId = losId;
        }
    }

    private static void onClick(ButtonRequest request, Consumer<Object> callback) {
        WorkflowRepos workflowRepos = HTTPConnector.createService(WorkflowRepos.class, request.username, "1");
        workflowRepos.action(request.action, request.losId).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                Log.d(ButtonMap.class.toString(), request.action + response.code());
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    callback.accept(null);
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

            }
        });
    }

    public static Button getButton(String code, String username, String losId, Context context, Consumer<Object> callback) {
        Button btn = new Button(context);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1.0f);
        btn.setLayoutParams(lp);
        ButtonRequest req = new ButtonRequest("", username, losId);
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
                ButtonMap.onClick(req, callback);
            }
        });
        return btn;
    }
}
