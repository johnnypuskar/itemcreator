package json;

public class JSONString implements Element {
    private String value;

    public JSONString(String value) {
        this.value = value;
    }

    public String json() {
        return "'" + value + "'";
    }

    public JSONType getType() {
        return JSONType.STRING;
    }
}
