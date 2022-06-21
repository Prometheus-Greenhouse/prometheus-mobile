package tik.prometheus.mobile.workflow;
import com.google.gson.JsonObject;
import io.reactivex.Single;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface WorkflowRepos {

        @GET("/{los_id}/state-guide")
        Call<JsonObject> getStateGuide(@Path("los_id") String los_id);

//        @GET("countryInfoJSON?formatted=true&lang=en&username=tiktzuki&style=full")
//        Call<CountryInfo> getCountry(@Query("country") String countryCode);
}
