import java.util.ArrayList;

public class Program {

    public static int NEXT_ITEM_FRAME_ID = 0;
    public static JSONObject itemFrameModel = (JSONObject)JSONParser.parse("{'parent':'item/generated','textures':{'layer0':'item/item_frame'}}");

    public static void main(String[] args) {
        addBlock(ItemType.SIMPLE, "Basalt", "basalt.png");
    }

    public static int nextItemFrameID() {
        // Value of 0 never used -> intentional!!
        NEXT_ITEM_FRAME_ID++;
        return NEXT_ITEM_FRAME_ID;
    }

    public static Block addBlock(ItemType type, String name, String texture) {
        // Get overrides tag, creating if it doesn't already exist
        JSONArray overrides;
        if(itemFrameModel.get("overrides") == null) {
            overrides = new JSONArray();
            itemFrameModel.set("overrides", overrides);
        }
        else {
            overrides = (JSONArray)itemFrameModel.get("overrides");
        }
        Block block;
        if(type == ItemType.SIMPLE) {
            block = new SimpleBlock(name, texture);
            overrides.add(block.getItemFrameHandModel());
            overrides.add(block.getItemFrameDispModel());
        }
        else {
            block = new SimpleBlock(name, texture);
        }

        return block;
    }
}
