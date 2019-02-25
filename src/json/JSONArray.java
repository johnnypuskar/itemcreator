package json;

import java.util.ArrayList;

public class JSONArray implements Element {
    private ArrayList<Element> values;

    public JSONArray() {
        this.values = new ArrayList<Element>();
    }

    public JSONArray(ArrayList<Element> values) {
        this.values = values;
    }

    public void add(Element value) {
        values.add(value);
    }

    public Element get(int index) {
        return values.get(index);
    }

    public int size() {
        return values.size();
    }

    public String json() {
        String toReturn = "[";
        for(int i = 0; i < values.size(); i++) {
            toReturn += values.get(i).json();
            if(i + 1 < values.size()) {
                toReturn += ",";
            }
        }
        return toReturn + "]";
    }

    public JSONType getType() {
        return JSONType.ARRAY;
    }
}
