package resources.block;

import json.JSONObject;
import json.JSONParser;
import resources.ItemType;

public abstract class Block {
    // Names: (internal block name and item display name)
    protected String displayName;
    protected String blockName;
    // Block Models: Custom block models
    protected JSONObject itemFrameHandOverride;
    protected JSONObject itemFrameDispOverride;
    protected JSONObject blockHandModel;
    protected JSONObject blockDispModel;
    protected int modelID;
    protected ItemType type;

    public Block(String displayName, int modelID) {
        this.displayName = displayName;
        this.blockName = displayName.trim().toLowerCase().replaceAll(" ","_");
        this.modelID = modelID;
        this.itemFrameHandOverride = (JSONObject) JSONParser.parse("{'predicate':{'custom_model_data':" + modelID + "},'model':'block/" + blockName + "'}");
        this.itemFrameDispOverride = (JSONObject)JSONParser.parse("{'predicate':{'custom_model_data':" + (modelID + 1) + "},'model':'block/" + blockName + "_display'}");
    }

    public String getBlockName() {
        return blockName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public JSONObject getItemFrameHandOverride() {
        return itemFrameHandOverride;
    }

    public JSONObject getItemFrameDispOverride() {
        return itemFrameDispOverride;
    }

    public JSONObject getBlockHandModel() {
        return blockHandModel;
    }

    public JSONObject getBlockDispModel() {
        return blockDispModel;
    }

    public int getModelID() { return modelID; }

    public ItemType getType() { return type; }
}
