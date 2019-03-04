package json;

public class JSONNumber implements Element {
    private double value;

    public JSONNumber(double value) {
        this.value = value;
    }

    public String json() {
        if(value == 0 || (value/(int)value) == 1) {
            return (int)value + "";
        }
        return value + "";
    }

    public JSONType getType() {
        return JSONType.NUMBER;
    }

    public String toString() { return value + ""; }
}
