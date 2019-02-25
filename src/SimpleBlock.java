public class SimpleBlock extends Block {
    private String texture;

    public SimpleBlock(String name, String texture) {
        super(name, name.trim().toLowerCase().replaceAll(" ","_"));
        this.texture = texture;
        this.blockHandModel = (JSONObject)JSONParser.parse("{'parent':'block/cube_all','textures':{'all':'block/'" + blockName + "'}}");
        this.blockDispModel = (JSONObject)JSONParser.parse("{'parent':'block/cube_all','textures':{'all':'block/'" + blockName + "'},'display':{'head':{'rotation':[0,0,0],'translation':[0,-30.75,-7.25],'scale':[3.025,3.025,3.025]},'fixed':{'rotation':[0,0,0],'translation':[0,0,-14.05],'scale':[2.005,2.005,2.005]}}}");
    }
}
