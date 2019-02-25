package items.block;

import json.JSONObject;
import json.JSONParser;
import main.Program;

public abstract class Block {
    // Names: (internal items.block name and items.item display name)
    protected String displayName;
    protected String blockName;
    // items.block.Block Models: Custom items.block models
    protected JSONObject itemFrameHandModel;
    protected JSONObject itemFrameDispModel;
    protected JSONObject blockHandModel;
    protected JSONObject blockDispModel;

    public Block(String displayName, String blockName) {
        this.blockName = blockName;
        this.displayName = displayName;
        this.itemFrameHandModel = (JSONObject) JSONParser.parse("{'predicate':{'custom_model_data':" + Program.nextItemFrameID() + "},'model':'items.block/" + blockName + "'}");
        this.itemFrameDispModel = (JSONObject)JSONParser.parse("{'predicate':{'custom_model_data':" + Program.nextItemFrameID() + "},'model':'items.block/" + blockName + "_display'}");
    }

    public String getBlockName() {
        return blockName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public JSONObject getItemFrameHandModel() {
        return itemFrameHandModel;
    }

    public JSONObject getItemFrameDispModel() {
        return itemFrameDispModel;
    }

    public JSONObject getBlockHandModel() {
        return blockHandModel;
    }

    public JSONObject getBlockDispModel() {
        return blockDispModel;
    }
}
