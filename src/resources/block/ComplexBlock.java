package resources.block;

import resources.ItemType;

public class ComplexBlock extends Block {
    private String textures;
    private boolean directional;

    public ComplexBlock(String name, String texturesJSON, boolean directional) {
        super(name, name.trim().toLowerCase().replaceAll(" ","_"), 0);
        this.type = ItemType.COMPLEX;
    }
}
