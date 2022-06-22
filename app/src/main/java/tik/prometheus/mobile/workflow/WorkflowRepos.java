package tik.prometheus.mobile.workflow;

import com.google.gson.JsonObject;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface WorkflowRepos {

    @GET("/{los_id}/state-guide")
    Call<JsonObject> getStateGuide(@Path("los_id") String los_id);

    @POST("/{los_id}/apply/approve")
    Call<JsonObject> apply_approve(@Path("los_id") String los_id);

    @POST("/{los_id}/apply/control")
    Call<JsonObject> apply_control(@Path("los_id") String los_id);

    @POST("/{los_id}/approve")
    Call<JsonObject> approve(@Path("los_id") String los_id);

    @POST("/{los_id}/close")
    Call<JsonObject> close(@Path("los_id") String los_id);

    @POST("/{los_id}/freeze")
    Call<JsonObject> freeze(@Path("los_id") String los_id);

    @POST("/{los_id}/process")
    Call<JsonObject> process(@Path("los_id") String los_id);

    @POST("/{los_id}/return/init")
    Call<JsonObject> return_init(@Path("los_id") String los_id);

    @POST("/{action}")
    Call<JsonObject> action(@Path("action") String action, @Query("los_id") String los_id);
}
