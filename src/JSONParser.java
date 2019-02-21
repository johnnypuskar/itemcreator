import java.util.ArrayList;

public abstract class JSONParser {

    public static Element parse(String json) {
        json = json.trim();
        JSONType type = getJSONType(json);
        if(type == JSONType.OBJECT) {
            JSONObject toReturn = new JSONObject();
            json = removeOutsideCharacter('{','}',json);
            int index = 0;
            while(index < json.length()) {
                int nextComma = nextCommaIndex(json, index);
                String element = json.substring(index, nextComma);
                toReturn.set(getKey(element), parse(getValue(element)));
                index = nextComma + 1;
            }
            return toReturn;
        }
        else if(type == JSONType.STRING) {
            return new JSONString(removeOutsideCharacter('\'', '\'', json));
        }
        else if(type == JSONType.NUMBER) {
            return new JSONNumber(Double.parseDouble(json));
        }
        else if(type == JSONType.ARRAY) {
            json = removeOutsideCharacter('[', ']', json);
            int index = 0;
            ArrayList<Element> list = new ArrayList<Element>();
            while (index < json.length()) {
                int nextComma = nextCommaIndex(json, index);
                String element = json.substring(index, nextComma).trim();
                list.add(parse(element));
                index = nextComma + 1;
            }
            return new JSONArray(list);
        }
        return null;
    }

    private static int nextCommaIndex(String json, int index) {
        int layer = 0;
        while(index < json.length() && ((json.charAt(index) != ',' && layer == 0) || layer > 0)) {
            char current = json.charAt(index);
            if(current == '{' || current == '[') { layer++; }
            if(current == '}' || current == ']') { layer--; }
            index++;
        }
        return index;
    }

    private static int getClosingBracket(String json, int index) {
        if(json.charAt(index) != '{' && json.charAt(index) != '[') { return index; }
        int layer = 1;
        while(index < json.length() - 1 && layer > 0) {
            index++;
            char current = json.charAt(index);
            if(current == '}' || current == ']') { layer--; }
            if(current == '{' || current == '[') { layer++; }
        }
        if(json.charAt(index) != '}' || json.charAt(index) != ']') { return index; }
        return -1;
    }

    private static String getKey(String jsonSegment) {
        return removeOutsideCharacter('\'', '\'', jsonSegment.substring(0,jsonSegment.indexOf(':')).trim());
    }

    private static String getValue(String jsonSegment) {
        return jsonSegment.substring(jsonSegment.indexOf(':') + 1).trim();
    }

    private static JSONType getJSONType(String json) {
        String value = json.trim();
        char firstChar = value.charAt(0);
        if(firstChar == '\'') { return JSONType.STRING; }
        else if(firstChar == '-' || Character.isDigit(firstChar)) { return JSONType.NUMBER; }
        else if(firstChar == '[') { return JSONType.ARRAY; }
        else if(firstChar == '{') { return JSONType.OBJECT; }
        return null;
    }

    private static String removeOutsideCharacter(char chFront, char chBack, String str) {
        if(str.charAt(0) == chFront) { str = str.substring(1); }
        if(str.charAt(str.length() - 1) == chBack) { str = str.substring(0, str.length() - 1); }
        return str;
    }
}
