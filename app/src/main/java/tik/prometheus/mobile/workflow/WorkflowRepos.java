package tik.prometheus.mobile.workflow;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import tik.prometheus.mobile.models.Document;
import tik.prometheus.mobile.models.User;

import java.util.List;

public interface WorkflowRepos {

    @GET("/api/v1/{los_id}/state-guide")
    Call<JsonObject> getStateGuide(@Path("los_id") String los_id);

    @POST("/api/v1/documents/{los_id}/{action}")
    Call<JsonObject> action(@Path("action") String action, @Path("los_id") String los_id);

    @GET("/api/v1/security/")
    Call<List<User>> getUsers();

    @GET("/api/v1/documents/")
    Call<List<Document>> getDocuments();

    @POST("/api/v1/documents/")
    Call<JsonObject> postDocument();
}
