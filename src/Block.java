public abstract class Block {
    // Names: (internal block name and item display name)
    protected String displayName;
    protected String blockName;
    // Block Models: Custom block models
    protected JSONObject itemFrameHandModel;
    protected JSONObject itemFrameDispModel;
    protected JSONObject blockHandModel;
    protected JSONObject blockDispModel;

    public Block(String displayName, String blockName) {
        this.blockName = blockName;
        this.displayName = displayName;
        this.itemFrameHandModel = (JSONObject)JSONParser.parse("{'predicate':{'custom_model_data':" + Program.nextItemFrameID() + "},'model':'block/" + blockName + "'}");
        this.itemFrameDispModel = (JSONObject)JSONParser.parse("{'predicate':{'custom_model_data':" + Program.nextItemFrameID() + "},'model':'block/" + blockName + "_display'}");
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
