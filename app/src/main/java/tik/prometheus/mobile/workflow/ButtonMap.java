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

    private static void onClick(String action, Consumer<Object> callback) {
        String username = "VYNKK";
        String los_id = "123";
        WorkflowRepos workflowRepos = HTTPConnector.createService(WorkflowRepos.class, username, "1");
        workflowRepos.action(action, los_id).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                Log.d(ButtonMap.class.toString(), action + response.code());
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    callback.accept(null);
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

            }
        });
    }

    public static Button getButton(String code, Context context, Consumer<Object> callback) {
        Button btn = new Button(context);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1.0f);
        btn.setLayoutParams(lp);
        switch (code) {
            case "apply_approve": {
                btn.setText("Trình");
                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ButtonMap.onClick("apply/approve", callback);
                    }
                });
                return btn;
            }
            case "apply_control": {
                btn.setText("Trình");
                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ButtonMap.onClick("apply/control", callback);
                    }
                });
                return btn;
            }
            case "approve": {
                btn.setText("Duyệt");
                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ButtonMap.onClick("approve", callback);
                    }
                });
                return btn;
            }
            case "close": {
                btn.setText("Đóng");
                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ButtonMap.onClick("close", callback);
                    }
                });
                return btn;
            }
            case "freeze": {
                btn.setText("Phong tỏa");
                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ButtonMap.onClick("freeze", callback);
                    }
                });
                return btn;
            }
            case "process": {
                btn.setText("Xử lý");
                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ButtonMap.onClick("process", callback);
                    }
                });
                return btn;
            }
            case "return_init": {
                btn.setText("Chuyển trả");
                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ButtonMap.onClick("return/init", callback);
                    }
                });
                return btn;
            }
            case "save": {
                btn.setText("Lưu");
                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ButtonMap.onClick("save", callback);
                    }
                });
                return btn;
            }
        }
        return null;
    }
}
