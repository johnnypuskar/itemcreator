package resources.item;

import data.Function;
import json.JSONObject;
import json.JSONParser;
import resources.ItemType;

public abstract class Item {
    protected String displayName;
    protected String itemName;
    protected String base;
    protected JSONObject nbt;
    protected JSONObject modelOverride;
    protected JSONObject customModel;
    protected Function give;
    protected ItemType type;
    protected int modelID;

    public Item(String name, JSONObject nbt, String base, int modelID) {
        this(name, nbt, base, modelID, "generated");
    }

    public Item(String name, JSONObject nbt, String base, int modelID, String type) {
        this.displayName = name;
        this.itemName = name.toLowerCase().strip().replaceAll(" ", "_");
        this.modelID = modelID;

        this.base = base;

        this.nbt = nbt;
        if(this.nbt.get("CustomModelData") == null) {
            this.nbt.set("CustomModelData", modelID);
        }
        if(this.nbt.get("display") == null) {
            this.nbt.set("display", new JSONObject());
        }
        ((JSONObject)this.nbt.get("display")).set("Name", "{\\\"text\\\":\\\"" + this.displayName + "\\\",\\\"italic\\\":\\\"false\\\",\\\"color\\\":\\\"white\\\"}");

        this.give = new Function(this.itemName);
        this.give.addLine("give @s " + base + this.nbt.json().replaceAll("'", "\""));

        this.modelOverride = (JSONObject)JSONParser.parse("{'predicate':{'custom_model_data':" + modelID + "},'model':'item/" + this.itemName + "'}");
        this.customModel = (JSONObject)JSONParser.parse("{'parent':'item/" + type + "','textures':{'layer0':'item/" + this.itemName + "'}}");
    }

    public Function getGiveFunction() {
        return give;
    }

    public JSONObject getModelOverride() {
        return modelOverride;
    }

    public JSONObject getCustomModel() {
        return customModel;
    }

    public String getBase() { return base; }

    public String getItemName() { return itemName; }

    public String getDisplayName() { return displayName; }

    public ItemType getType() { return type; }

    public int getModelID() { return modelID; }
}
