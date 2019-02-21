import java.util.ArrayList;
import java.util.HashMap;

public class JSONObject implements Element {
    public HashMap<String, Element> values = new HashMap<String, Element>();

    public JSONObject() {

    }

    public JSONObject(String[] keys, Element[] values) {
        this.values = new HashMap<String, Element>();
        if(keys.length == values.length) {
            for(int i = 0; i < keys.length; i++) {
                this.values.put(keys[i], values[i]);
            }
        }
    }

    public void set(String key, Element value) {
        values.put(key, value);
    }

    public void set(String key, double value) {
        values.put(key, new JSONNumber(value));
    }

    public void set(String key, String value) {
        values.put(key, new JSONString(value));
    }

    public void set(String key, ArrayList<Element> value) {
        values.put(key, new JSONArray(value));
    }

    public Element get(String key) {
        return values.get(key);
    }

    public String json() {
        String toReturn = "{";
        String comma = "";
        for(String key : values.keySet()) {
            toReturn += comma + "'" + key + "':" + values.get(key).json();
            comma = ",";
        }
        return toReturn + "}";
    }

    public JSONType getType() {
        return JSONType.OBJECT;
    }
}
