import java.util.ArrayList;

public class Program {

    public static void main(String[] args) {
        JSONObject itemFrameModel = (JSONObject)JSONParser.parse("{'parent':'item/generated','textures':{'layer0':'item/item_frame'}}");
        addBlock(itemFrameModel, "basalt");
    }

    public static void addBlock(JSONObject itemFrameModel, String blockName) {
        JSONArray overrides;
        if(itemFrameModel.get("overrides") == null) {
            overrides = new JSONArray();
            itemFrameModel.set("overrides", overrides);
        }
        else {
            overrides = (JSONArray)itemFrameModel.get("overrides");
        }
        int nextModelID = overrides.size() + 1;
        JSONObject blockHandModelTag = (JSONObject)JSONParser.parse("{'predicate':{'custom_model_data':" + nextModelID + "},'model':'block/" + blockName + "'}");
        JSONObject blockDispModelTag = (JSONObject)JSONParser.parse("{'predicate':{'custom_model_data':" + (nextModelID + 1) + "},'model':'block/" + blockName + "_display'}");
        overrides.add(blockHandModelTag);
        overrides.add(blockDispModelTag);

        JSONObject blockModel = (JSONObject)JSONParser.parse("{'parent':'block/cube_all','textures':{'all':'block/'" + blockName + "'}}");
        JSONObject blockDisplayModel = (JSONObject)JSONParser.parse("{'parent':'block/cube_all','textures':{'all':'block/'" + blockName + "'},'display':{'head':{'rotation':[0,0,0],'translation':[0,-30.75,-7.25],'scale':[3.025,3.025,3.025]},'fixed':{'rotation':[0,0,0],'translation':[0,0,-14.05],'scale':[2.005,2.005,2.005]}}}");

        System.out.println("Item Frame: " + itemFrameModel.json());
        System.out.println("Block: " + blockModel.json());
        System.out.println("Display: " + blockDisplayModel.json());
        // Creating block hand and display models
        //JSONObject blockHandModel = new JSONObject(new String[]{"parent","textures"}, new Element[]{new JSONString("block/cube_all"), new JSONObject(new String[]{"all"}, new JSONString[]{new JSONString("block/" + blockName)})});
        //JSONObject blockDisplayModel
    }
}
