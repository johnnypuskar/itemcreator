package main;

import items.ItemType;
import items.block.*;
import json.*;

public class Program {

    public static int NEXT_ITEM_FRAME_ID = 0;
    public static Function mainFunction = new Function("main");
    public static JSONObject itemFrameModel = (JSONObject) JSONParser.parse("{'parent':'items.item/generated','textures':{'layer0':'items.item/item_frame'}}");

    public static void main(String[] args) {
        addBlock(ItemType.SIMPLE, "Basalt", "basalt.png");
        mainFunction.addLine("say hi");
        mainFunction.addLine("execute as @p run give @p minecraft:diamond");
        mainFunction.addLine("tp @p ~ ~1 ~");
        mainFunction.createFile("main");
    }

    public static int nextItemFrameID() {
        // Value of 0 never used -> intentional!!
        NEXT_ITEM_FRAME_ID++;
        return NEXT_ITEM_FRAME_ID;
    }

    public static Block addBlock(ItemType type, String name, String texture) {
        return addBlock(type, name, texture, false);
    }

    public static Block addBlock(ItemType type, String name, String texture, boolean directional) {
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
            block = new ComplexBlock(name, texture, directional);
        }

        return block;
    }
}
