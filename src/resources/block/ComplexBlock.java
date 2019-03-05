package resources.block;

import data.Function;
import json.JSONObject;
import json.JSONParser;
import resources.ItemType;
import resources.RotationType;

import java.util.ArrayList;

public class ComplexBlock extends Block {
    private boolean directional;
    private RotationType rotation;

    private ArrayList<String> textures = new ArrayList<String>();
    private String baseBlock;
    private Function placeFunction;
    private Function tickFunction;
    private Function dropFunction;

    private static final JSONObject MODEL_DISPLAY_TAG = (JSONObject)JSONParser.parse("{'head':{'rotation':[0,0,0],'translation':[0,-14.65,0],'scale':[2.29,2.29,2.29]}}");

    public ComplexBlock(String name, JSONObject texturesJSON, RotationType rotation, int modelID) {
        super(name, modelID);
        this.type = ItemType.COMPLEX;
        this.rotation = rotation;
        this.placeFunction = new Function("place");
        this.tickFunction = new Function("tick");
        this.dropFunction = new Function("drop");

        this.baseBlock = "minecraft:" + texturesJSON.get("base").toString();

        String side = "stone";
        String top = "dirt";
        String bottom = "cobblestone";
        String front = "glass";
        String parent;
        switch(rotation) {
            case FIXED:
                parent = "block/cube_all";
                if(texturesJSON.get("texture") != null) {
                    side = texturesJSON.get("texture").toString();
                    textures.add(side);
                }
                else if(texturesJSON.get("all") != null) {
                    side = texturesJSON.get("all").toString();
                    textures.add(side);
                }
                else {
                    System.out.println("Error: Fixed textures not found for " + name + ", defaulting to stone");
                    side = "stone";
                }
                this.blockHandModel = (JSONObject) JSONParser.parse("{'parent':'" + parent + "','textures':{'all':'block/" + side + "'}}");
                break;
            case UPRIGHT:
                if(texturesJSON.get("bottom") == null) {
                    parent = "block/cube_column";
                }
                else {
                    parent = "block/template_piston";
                    bottom = texturesJSON.get("bottom").toString();
                    textures.add(bottom);
                }
                if(texturesJSON.get("side") != null) {
                    side = texturesJSON.get("side").toString();
                    textures.add(side);
                }
                else {
                    System.out.println("Error: Side texture not found for " + name + ", defaulting to dirt");
                }
                if(texturesJSON.get("top") != null) {
                    top = texturesJSON.get("top").toString();
                    textures.add(top);
                }
                else {
                    System.out.println("Error: Top texture not found for " + name + ", defaulting to stone");
                }
                this.blockHandModel = (JSONObject) JSONParser.parse("{'parent':'" + parent + "','textures':{'side':'block/" + side + "'}}");
                if(texturesJSON.get("bottom") == null) {
                    ((JSONObject)this.blockHandModel.get("textures")).set("end", "block/" + top);
                }
                else {
                    ((JSONObject)this.blockHandModel.get("textures")).set("platform", "block/" + top);
                    ((JSONObject)this.blockHandModel.get("textures")).set("bottom", "block/" + bottom);
                }
                break;
            case FLAT:
                parent = "block/orientable";
                if(texturesJSON.get("bottom") != null) {
                    parent += "_with_bottom";
                    bottom = texturesJSON.get("bottom").toString();
                    textures.add(bottom);
                }
                if(texturesJSON.get("side") != null) {
                    side = texturesJSON.get("side").toString();
                    textures.add(side);
                }
                else {
                    System.out.println("Error: Side texture not found for " + name + ", defaulting to dirt");
                }
                if(texturesJSON.get("top") != null) {
                    top = texturesJSON.get("top").toString();
                    textures.add(top);
                }
                else {
                    System.out.println("Error: Top texture not found for " + name + ", defaulting to stone");
                }
                if(texturesJSON.get("front") != null) {
                    front = texturesJSON.get("front").toString();
                    textures.add(front);
                }
                else {
                    System.out.println("Error: Front texture not found for " + name + ", defaulting to glass");
                }
                this.blockHandModel = (JSONObject)JSONParser.parse("{'parent':'" + parent + "','textures':{'top':'block/" + top + "','front':'block/" + front + "','side':'block/" + side + "'}}");
                if(texturesJSON.get("bottom") != null) {
                    ((JSONObject)this.blockHandModel.get("textures")).set("bottom", "block/" + bottom);
                }
                break;
        }
        this.blockDispModel = (JSONObject)JSONParser.parse(this.blockHandModel.json());
        this.blockDispModel.set("display", MODEL_DISPLAY_TAG);

        if(rotation != RotationType.FLAT) {
            placeFunction.addLine("summon minecraft:armor_stand ~ ~ ~ {Rotation:[0.0f,0.0f],Invisible:1b,Invunlerable:1b,Fire:2s,Small:1b,Marker:1b,Tags:[\"" + this.blockName + "\",\"complex_block\"],ArmorItems:[{},{},{},{id:\"minecraft:item_frame\",Count:1b,tag:{CustomModelData:" + (this.modelID + 1) + "}}]}");
            placeFunction.addLine("setblock ~ ~ ~ " + baseBlock);
            placeFunction.addLine("kill @s");
        }
        else {
            // TODO: Add placement function for flat rotation blocks
        }
        tickFunction.addLine("execute unless block ~ ~ ~ " + baseBlock + " run function blocks:" + blockName + "/drop");

        // TODO: Add the actual dropping part of the drop function
        dropFunction.addLine("kill @s");
    }

    public void exportFunctions(String path) {
        placeFunction.exportToFolder(path);
        tickFunction.exportToFolder(path);
        dropFunction.exportToFolder(path);
    }

    public ArrayList<String> getTextures() {
        return textures;
    }
}
