package resources.item;

import data.Function;
import json.JSONObject;
import resources.ItemType;

public class ComplexItem extends Item {

    protected Function click;

    public ComplexItem(String name, JSONObject nbt, String base, int modelID) {
        super(name, nbt, base, modelID, "generated");
        this.type = ItemType.COMPLEX;
        this.click = new Function(this.itemName);
    }

    public ComplexItem(String name, JSONObject nbt, String base, int modelID, String type) {
        super(name, nbt, base, modelID, type);
        this.type = ItemType.COMPLEX;
        this.click = new Function(this.itemName);
    }

    public Function getClickFunction() {
        return click;
    }
}
