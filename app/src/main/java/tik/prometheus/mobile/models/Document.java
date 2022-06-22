package tik.prometheus.mobile.models;

import java.util.Map;

public class Document {
    String losId;
    String stateId;
    String stateName;
    Map<Object, Object> content;

    public String getLosId() {
        return losId;
    }

    public String getStateId() {
        return stateId;
    }

    public String getStateName() {
        return stateName;
    }

    public Map<Object, Object> getContent() {
        return content;
    }
}
