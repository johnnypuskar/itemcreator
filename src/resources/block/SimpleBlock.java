package resources.block;

import json.JSONObject;
import json.JSONParser;
import resources.ItemType;

public class SimpleBlock extends Block {
    public SimpleBlock(String name, int modelID) {
        super(name, name.trim().toLowerCase().replaceAll(" ","_"), modelID);
        this.blockHandModel = (JSONObject)JSONParser.parse("{'parent':'resources.block/cube_all','textures':{'all':'resources.block/'" + blockName + "'}}");
        this.blockDispModel = (JSONObject)JSONParser.parse("{'parent':'resources.block/cube_all','textures':{'all':'resources.block/'" + blockName + "'},'display':{'head':{'rotation':[0,0,0],'translation':[0,-30.75,-7.25],'scale':[3.025,3.025,3.025]},'fixed':{'rotation':[0,0,0],'translation':[0,0,-14.05],'scale':[2.005,2.005,2.005]}}}");
        this.type = ItemType.SIMPLE;
    }
}
