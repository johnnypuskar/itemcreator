package resources.item;

import json.JSONObject;
import resources.ItemType;

public class SimpleItem extends Item {

    public SimpleItem(String name, JSONObject nbt, String base, int modelID) {
        super(name, nbt, base, modelID, "generated");
        this.type = ItemType.SIMPLE;
    }

    public SimpleItem(String name, JSONObject nbt, String base, int modelID, String type) {
        super(name, nbt, base, modelID, type);
        this.type = ItemType.SIMPLE;
    }
}
