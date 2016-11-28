package realimage.stackoverflow.model;

import com.google.gson.annotations.SerializedName;

import java.util.HashMap;
import java.util.Map;
/**
 * Created by Venkatesh
 * venkatselva8@gmail.com
 */
public class QuestionOwner {

    @SerializedName("display_name")
    private String displayName;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     *
     * @return
     * The displayName
     */
    public String getDisplayName() {
        return displayName;
    }

    /**
     *
     * @param displayName
     * The display_name
     */
    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}