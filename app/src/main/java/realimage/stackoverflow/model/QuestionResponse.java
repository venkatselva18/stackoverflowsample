package realimage.stackoverflow.model;

/**
 * Created by Venkatesh
 * venkatselva8@gmail.com
 */

        import java.util.ArrayList;
        import java.util.HashMap;
        import java.util.List;
        import java.util.Map;
public class QuestionResponse {

    private ArrayList<QuestionItems> items = new ArrayList<QuestionItems>();
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     *
     * @return
     * The items
     */
    public ArrayList<QuestionItems> getItems() {
        return items;
    }

    /**
     *
     * @param items
     * The items
     */
    public void setItems(ArrayList<QuestionItems> items) {
        this.items = items;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}