package gui;

import java.util.ArrayList;
import java.util.HashMap;

public class Template {
    private String name;
    enum TemplateType {BLOCK, ITEM}
    private TemplateType type;
    private boolean isComplex;
    private HashMap<String, ArrayList<String>> textures;
    private String body;


}
