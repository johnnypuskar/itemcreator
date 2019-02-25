package items.block;

public class ComplexBlock extends Block {
    private String textures;
    private boolean directional;

    public ComplexBlock(String name, String texturesJSON, boolean directional) {
        super(name, name.trim().toLowerCase().replaceAll(" ","_"));

    }
}
