package resources.block;

import data.Function;
import json.JSONObject;
import json.JSONParser;
import resources.Coordinates;
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
    private boolean hasGUI;

    private static final JSONObject MODEL_DISPLAY_TAG = (JSONObject)JSONParser.parse("{'head':{'rotation':[0,0,0],'translation':[0,-14.65,0],'scale':[2.29,2.29,2.29]}}");

    public static int nextFontCharID = 60161;

    public ComplexBlock(String name, JSONObject texturesJSON, RotationType rotation, int modelID, int fontCharID) {
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
                    parent = "block/cube_bottom_top";
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
                    ((JSONObject)this.blockHandModel.get("textures")).set("top", "block/" + top);
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
            case DIRECTIONAL:
                parent  = "block/cube_directional";
                if(texturesJSON.get("front") != null) {
                    front = texturesJSON.get("front").toString();
                    textures.add(front);
                }
                else {
                    System.out.println("Error: Front texture not found for " + name + ", defaulting to glass");
                }
                if(texturesJSON.get("side") != null) {
                    side = texturesJSON.get("side").toString();
                    textures.add(side);
                }
                else {
                    System.out.println("Error: Side texture not found for " + name + ", defaulting to dirt");
                }
                if(texturesJSON.get("back") != null) {
                    bottom = texturesJSON.get("back").toString();
                    textures.add(bottom);
                }
                else {
                    bottom = side;
                }
                this.blockHandModel = (JSONObject)JSONParser.parse("{'parent':'" + parent + "','textures':{'up':'block/" + side + "','down':'block/" + side + "','east':'block/" + side + "','west':'block/" + side + "','north':'block/" + front + "','south':'block/" + bottom + "'}}");
                break;
        }
        this.blockDispModel = (JSONObject)JSONParser.parse(this.blockHandModel.json());
        this.blockDispModel.set("display", MODEL_DISPLAY_TAG);

        String[] lines;
        if(rotation == RotationType.FIXED || rotation == RotationType.UPRIGHT) {
            placeFunction.addLine("summon minecraft:armor_stand ~ ~ ~ {Rotation:[0.0f,0.0f],Invisible:1b,Invunlerable:1b,Fire:2s,Small:1b,Marker:1b,Tags:[\"" + this.blockName + "\",\"complex_block\"],ArmorItems:[{},{},{},{id:\"minecraft:item_frame\",Count:1b,tag:{CustomModelData:" + (this.modelID + 1) + "}}]}");
        }
        else if(rotation == RotationType.FLAT) {
            placeFunction.addLine("execute if entity @p[scores={placeItemFrame=1..},y_rotation=-135..-45] run summon minecraft:armor_stand ~ ~ ~ {Rotation:[90.0f,0.0f],Invisible:1b,Invunlerable:1b,Fire:2s,Small:1b,Marker:1b,Tags:[\"" + this.blockName + "\",\"complex_block\"],ArmorItems:[{},{},{},{id:\"minecraft:item_frame\",Count:1b,tag:{CustomModelData:" + (this.modelID + 1) + "}}]}");
            placeFunction.addLine("execute if entity @p[scores={placeItemFrame=1..},y_rotation=-45..45] unless entity @e[tag=" + this.blockName + ",distance=..0.1] run summon minecraft:armor_stand ~ ~ ~ {Rotation:[180.0f,0.0f],Invisible:1b,Invunlerable:1b,Fire:2s,Small:1b,Marker:1b,Tags:[\"" + this.blockName + "\",\"complex_block\"],ArmorItems:[{},{},{},{id:\"minecraft:item_frame\",Count:1b,tag:{CustomModelData:" + (this.modelID + 1) + "}}]}");
            placeFunction.addLine("execute if entity @p[scores={placeItemFrame=1..},y_rotation=45..135] unless entity @e[tag=" + this.blockName + ",distance=..0.1] run summon minecraft:armor_stand ~ ~ ~ {Rotation:[-90.0f,0.0f],Invisible:1b,Invunlerable:1b,Fire:2s,Small:1b,Marker:1b,Tags:[\"" + this.blockName + "\",\"complex_block\"],ArmorItems:[{},{},{},{id:\"minecraft:item_frame\",Count:1b,tag:{CustomModelData:" + (this.modelID + 1) + "}}]}");
            placeFunction.addLine("execute if entity @p[scores={placeItemFrame=1..},y_rotation=135..180] unless entity @e[tag=" + this.blockName + ",distance=..0.1] run summon minecraft:armor_stand ~ ~ ~ {Rotation:[0.0f,0.0f],Invisible:1b,Invunlerable:1b,Fire:2s,Small:1b,Marker:1b,Tags:[\"" + this.blockName + "\",\"complex_block\"],ArmorItems:[{},{},{},{id:\"minecraft:item_frame\",Count:1b,tag:{CustomModelData:" + (this.modelID + 1) + "}}]}");
            placeFunction.addLine("execute if entity @p[scores={placeItemFrame=1..},y_rotation=-180..-135] unless entity @e[tag=" + this.blockName + ",distance=..0.1] run summon minecraft:armor_stand ~ ~ ~ {Rotation:[0.0f,0.0f],Invisible:1b,Invunlerable:1b,Fire:2s,Small:1b,Marker:1b,Tags:[\"" + this.blockName + "\",\"complex_block\"],ArmorItems:[{},{},{},{id:\"minecraft:item_frame\",Count:1b,tag:{CustomModelData:" + (this.modelID + 1) + "}}]}");
        }
        else if(rotation == RotationType.DIRECTIONAL) {
            placeFunction.addLine("execute if entity @p[scores={placeItemFrame=1..},x_rotation=-90..-45] run summon minecraft:armor_stand ~ ~-0.226 ~0.225 {Pose:{Head:[90.0f,0.0f,0.0f]},Rotation:[0.0f,90.0f],Invisible:1b,Invunlerable:1b,Fire:2s,Small:1b,Marker:1b,Tags:[\"" + this.blockName + "\",\"complex_block\",\"rotated_down\"],ArmorItems:[{},{},{},{id:\"minecraft:item_frame\",Count:1b,tag:{CustomModelData:" + (this.modelID + 1) + "}}]}");
            placeFunction.addLine("execute if entity @p[scores={placeItemFrame=1..},x_rotation=45..90] unless entity @e[tag=" + this.blockName + ",distance=..0.4] run summon minecraft:armor_stand ~ ~-0.226 ~-0.225 {Pose:{Head:[-90.0f,0.0f,0.0f]},Rotation:[0.0f,-90.0f],Invisible:1b,Invunlerable:1b,Fire:2s,Small:1b,Marker:1b,Tags:[\"" + this.blockName + "\",\"complex_block\",\"rotated_up\"],ArmorItems:[{},{},{},{id:\"minecraft:item_frame\",Count:1b,tag:{CustomModelData:" + (this.modelID + 1) + "}}]}");
            placeFunction.addLine("execute if entity @p[scores={placeItemFrame=1..},y_rotation=-135..-45] unless entity @e[tag=" + this.blockName + ",distance=..0.4] run summon minecraft:armor_stand ~ ~ ~ {Rotation:[90.0f,0.0f],Invisible:1b,Invunlerable:1b,Fire:2s,Small:1b,Marker:1b,Tags:[\"" + this.blockName + "\",\"complex_block\"],ArmorItems:[{},{},{},{id:\"minecraft:item_frame\",Count:1b,tag:{CustomModelData:" + (this.modelID + 1) + "}}]}");
            placeFunction.addLine("execute if entity @p[scores={placeItemFrame=1..},y_rotation=-45..45] unless entity @e[tag=" + this.blockName + ",distance=..0.4] run summon minecraft:armor_stand ~ ~ ~ {Rotation:[180.0f,0.0f],Invisible:1b,Invunlerable:1b,Fire:2s,Small:1b,Marker:1b,Tags:[\"" + this.blockName + "\",\"complex_block\"],ArmorItems:[{},{},{},{id:\"minecraft:item_frame\",Count:1b,tag:{CustomModelData:" + (this.modelID + 1) + "}}]}");
            placeFunction.addLine("execute if entity @p[scores={placeItemFrame=1..},y_rotation=45..135] unless entity @e[tag=" + this.blockName + ",distance=..0.4] run summon minecraft:armor_stand ~ ~ ~ {Rotation:[-90.0f,0.0f],Invisible:1b,Invunlerable:1b,Fire:2s,Small:1b,Marker:1b,Tags:[\"" + this.blockName + "\",\"complex_block\"],ArmorItems:[{},{},{},{id:\"minecraft:item_frame\",Count:1b,tag:{CustomModelData:" + (this.modelID + 1) + "}}]}");
            placeFunction.addLine("execute if entity @p[scores={placeItemFrame=1..},y_rotation=135..180] unless entity @e[tag=" + this.blockName + ",distance=..0.4] run summon minecraft:armor_stand ~ ~ ~ {Rotation:[0.0f,0.0f],Invisible:1b,Invunlerable:1b,Fire:2s,Small:1b,Marker:1b,Tags:[\"" + this.blockName + "\",\"complex_block\"],ArmorItems:[{},{},{},{id:\"minecraft:item_frame\",Count:1b,tag:{CustomModelData:" + (this.modelID + 1) + "}}]}");
            placeFunction.addLine("execute if entity @p[scores={placeItemFrame=1..},y_rotation=-180..-135] unless entity @e[tag=" + this.blockName + ",distance=..0.4] run summon minecraft:armor_stand ~ ~ ~ {Rotation:[0.0f,0.0f],Invisible:1b,Invunlerable:1b,Fire:2s,Small:1b,Marker:1b,Tags:[\"" + this.blockName + "\",\"complex_block\"],ArmorItems:[{},{},{},{id:\"minecraft:item_frame\",Count:1b,tag:{CustomModelData:" + (this.modelID + 1) + "}}]}");
        }
        String guiNBT = "";
        if(fontCharID >= 0) {
            String fontChar = Integer.toHexString(fontCharID);
            guiNBT = "{CustomName:\"{\\\"text\\\":\\\"\\\\u" + fontChar + "\\\",\\\"color\\\":\\\"white\\\"}\"}";
            this.hasGUI = true;
        }
        placeFunction.addLine("setblock ~ ~ ~ " + baseBlock + guiNBT);
        placeFunction.addLine("kill @s");

        // Piston Functionality
        Coordinates[] locations = {new Coordinates(0, -1, 0), new Coordinates(0, 1, 0), new Coordinates(0, 0, -1), new Coordinates(0, 0, 1), new Coordinates(-1, 0, 0), new Coordinates(1, 0, 0)};
        String offset = (this.rotation == RotationType.DIRECTIONAL) ? "positioned ~ ~0.226 ~ positioned ^ ^-0.225 ^ " : "";
        for(int i = 0; i < 6; i++) {
            tickFunction.addLine("execute at @s[tag=!piston_moved] " + offset + "if block " + locations[i].getRelative() + " minecraft:moving_piston{facing:" + i +",extending:1b,blockState:{Name:\"" + this.baseBlock + "\"}} at @s run tp @s " + locations[i].getRelative());
            tickFunction.addLine("execute at @s[tag=!piston_moved] " + offset + "if block " + locations[i].getInverseRelative() + " minecraft:moving_piston{facing:" + i +",extending:0b,blockState:{Name:\"" + this.baseBlock + "\"}} at @s run tp @s " + locations[i].getInverseRelative());
        }

        tickFunction.addLine("execute if entity @s[tag=piston_moved] run tag @s remove piston_moved");
        tickFunction.addLine("execute at @s if block ~ ~ ~ minecraft:moving_piston{blockState:{Name:\"" + this.baseBlock + "\"}} run tag @s add piston_moved");

        tickFunction.addLine("execute if entity @s[tag=!rotated_up,tag=!rotated_down] at @s unless block ~ ~ ~ " + baseBlock + " unless block ~ ~ ~ minecraft:moving_piston run function blocks:" + blockName + "/drop");
        tickFunction.addLine("execute if entity @s[tag=rotated_up] at @s positioned ~ ~0.226 ~-0.225 unless block ~ ~ ~ " + baseBlock + " unless block ~ ~ ~ minecraft:moving_piston run function blocks:" + blockName + "/drop");
        tickFunction.addLine("execute if entity @s[tag=rotated_down] at @s positioned ~ ~0.226 ~0.225 unless block ~ ~ ~ " + baseBlock + " unless block ~ ~ ~ minecraft:moving_piston run function blocks:" + blockName + "/drop");


        dropFunction.addLine("execute align xyz run data modify entity @e[type=item,limit=1,dx=1,dy=1,dz=1,nbt={Item:{id:\"" + this.baseBlock + "\"}}] Item set value {id:\"minecraft:item_frame\",Count:1b,tag:{EntityTag:{Tags:[\"block_" + this.blockName + "\",\"block_item_frame\"],Item:{id:\"minecraft:item_frame\",Count:1b,tag:{CustomModelData:" + (this.modelID + 1) + "}}},CustomModelData:" + this.modelID + ",KilledXP:1b,display:{Name:\"{\\\"text\\\":\\\"" + this.displayName + "\\\",\\\"italic\\\":\\\"false\\\"}\"}}}");
        dropFunction.addLine("kill @s");
    }

    public void exportFunctions(String path) {
        placeFunction.exportToFolder(path);
        tickFunction.exportToFolder(path);
        dropFunction.exportToFolder(path);
    }

    public boolean hasGUI() {
        return hasGUI;
    }

    public ArrayList<String> getTextures() {
        return textures;
    }

    public RotationType getRotation() {
        return rotation;
    }
}
