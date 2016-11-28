package realimage.stackoverflow.apiService;

import okhttp3.ResponseBody;
import realimage.stackoverflow.helpers.Config;
import realimage.stackoverflow.model.AccessTokenResponse;
import realimage.stackoverflow.model.QuestionItems;
import realimage.stackoverflow.model.QuestionResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

import static realimage.stackoverflow.helpers.Config.API_KEY;

/**
 * Created by Venkatesh
 * venkatselva8@gmail.com
 */

public interface ServiceInterface {


    @GET("/questions")
    Call<QuestionResponse> getQuetions(@Query("order") String order, @Query("sort") String sort,
                                       @Query("filter") String filter, @Query("site") String site,
                                       @Query("key") String key);

    @POST("oauth/access_token")
    Call<ResponseBody> getAccessToken(@Query("client_id") String clientID, @Query("redirect_uri") String redirectUri,
                                      @Query("client_secret") String clientSecret, @Query("code") String code);


    @GET("/me/questions")
    Call<QuestionResponse> getUsersQuetions(@Query("order") String order, @Query("sort") String sort,
                                            @Query("filter") String filter, @Query("site") String site,
                                            @Query("key") String key);

    @GET("access-tokens/{access-token}/invalidate")
    Call<ResponseBody> invalidateAccessToken(@Path("access-token") String accessToken);

}



