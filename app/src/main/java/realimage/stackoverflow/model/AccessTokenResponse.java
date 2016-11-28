package realimage.stackoverflow.model;

/**
 * Created by Venkatesh
 * venkatselva8@gmail.com
 */
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AccessTokenResponse {

    @SerializedName("access_token")
    @Expose
    private String accessToken;
    @SerializedName("expires")
    @Expose
    private Integer expires;

    /**
     *
     * @return
     * The accessToken
     */
    public String getAccessToken() {
        return accessToken;
    }

    /**
     *
     * @param accessToken
     * The access_token
     */
    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    /**
     *
     * @return
     * The expires
     */
    public Integer getExpires() {
        return expires;
    }

    /**
     *
     * @param expires
     * The expires
     */
    public void setExpires(Integer expires) {
        this.expires = expires;
    }

}
