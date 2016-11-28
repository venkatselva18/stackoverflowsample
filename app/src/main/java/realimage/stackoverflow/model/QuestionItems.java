package realimage.stackoverflow.model;

/**
 * Created by Venkatesh
 * venkatselva8@gmail.com
 */

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QuestionItems {

    private ArrayList<String> tags = new ArrayList<String>();
    private QuestionOwner owner;

    @SerializedName("up_vote_count")
    private int upVoteCount;

    @SerializedName("creation_date")
    private long creationDate;
    private String title;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * @return The tags
     */
    public ArrayList<String> getTags() {
        return tags;
    }

    /**
     * @param tags The tags
     */
    public void setTags(ArrayList<String> tags) {
        this.tags = tags;
    }

    /**
     * @return The owner
     */
    public QuestionOwner getOwner() {
        return owner;
    }

    /**
     * @param owner The owner
     */
    public void setOwner(QuestionOwner owner) {
        this.owner = owner;
    }

    /**
     * @return The upVoteCount
     */
    public int getUpVoteCount() {
        return upVoteCount;
    }

    /**
     * @param upVoteCount The up_vote_count
     */
    public void setUpVoteCount(int upVoteCount) {
        this.upVoteCount = upVoteCount;
    }

    /**
     * @return The creationDate
     */
    public long getCreationDate() {
        return creationDate;
    }

    /**
     * @param creationDate The creation_date
     */
    public void setCreationDate(int creationDate) {
        this.creationDate = creationDate;
    }

    /**
     * @return The title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title The title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}