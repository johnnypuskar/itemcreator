package resources;

import resources.block.Block;
import resources.block.ComplexBlock;
import resources.block.SimpleBlock;
import json.JSONArray;
import json.JSONObject;
import json.JSONParser;
import resources.item.Item;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.HashMap;

public class ResourcePack {
    private String name;

    private JSONObject packMCMETA;
    private JSONObject itemFrameModel;
    private JSONObject fontDefault;

    private HashMap<String, Block> blocks = new HashMap<String, Block>();
    private HashMap<String, Item> items = new HashMap<String, Item>();

    private static int NEXT_ITEM_FRAME_ID = 7499;

    public ResourcePack(String name, String description) {
        this.name = name;
        packMCMETA = (JSONObject)JSONParser.parse("{'pack':{'pack_format':4,'description':'" + description + "'}}");

        itemFrameModel = (JSONObject)JSONParser.parse("{'parent':'item/generated','textures':{'layer0':'item/item_frame'}}");
        fontDefault = (JSONObject)JSONParser.parse("{'providers':[{'type':'bitmap','file':'minecraft:font/nonlatin_european.png','ascent':7,'chars':['\\u00a1\\u2030\\u00ad\\u00b7\\u20b4\\u2260\\u00bf\\u00d7\\u00d8\\u00de\\u00df\\u00f0\\u00f8\\u00fe\\u0391\\u0392','\\u0393\\u0394\\u0395\\u0396\\u0397\\u0398\\u0399\\u039a\\u039b\\u039c\\u039d\\u039e\\u039f\\u03a0\\u03a1\\u03a3','\\u03a4\\u03a5\\u03a6\\u03a7\\u03a8\\u03a9\\u03b1\\u03b2\\u03b3\\u03b4\\u03b5\\u03b6\\u03b7\\u03b8\\u03b9\\u03ba','\\u03bb\\u03bc\\u03bd\\u03be\\u03bf\\u03c0\\u03c1\\u03c2\\u03c3\\u03c4\\u03c5\\u03c6\\u03c7\\u03c8\\u03c9\\u0402','\\u0405\\u0406\\u0408\\u0409\\u040a\\u040b\\u0410\\u0411\\u0412\\u0413\\u0414\\u0415\\u0416\\u0417\\u0418\\u041a','\\u041b\\u041c\\u041d\\u041e\\u041f\\u0420\\u0421\\u0422\\u0423\\u0424\\u0425\\u0426\\u0427\\u0428\\u0429\\u042a','\\u042b\\u042c\\u042d\\u042e\\u042f\\u0430\\u0431\\u0432\\u0433\\u0434\\u0435\\u0436\\u0437\\u0438\\u043a\\u043b','\\u043c\\u043d\\u043e\\u043f\\u0440\\u0441\\u0442\\u0443\\u0444\\u0445\\u0446\\u0447\\u0448\\u0449\\u044a\\u044b','\\u044c\\u044d\\u044e\\u044f\\u0454\\u0455\\u0456\\u0458\\u0459\\u045a\\u2013\\u2014\\u2018\\u2019\\u201c\\u201d','\\u201e\\u2026\\u204a\\u2190\\u2191\\u2192\\u2193\\u21c4\\uff0b\\u018f\\u0259\\u025b\\u026a\\u04ae\\u04af\\u04e8','\\u04e9\\u02bb\\u02cc\\u037e\\u0138\\u1e9e\\u00df\\u20bd\\u20ac\\u0462\\u0463\\u0474\\u0475\\u0406\\u0472\\u0473','\\u2070\\u00b9\\u00b3\\u2074\\u2075\\u2076\\u2077\\u2078\\u2079\\u207a\\u207b\\u207c\\u207d\\u207e\\u2071\\u2122','\\u0294\\u0295\\u29c8\\u2694\\u2620\\u0000\\u0000\\u0000\\u0000\\u0000\\u0000\\u0000\\u0000\\u0000\\u0000\\u0000']},{'type':'bitmap','file':'minecraft:font/accented.png','height':12,'ascent':10,'chars':['\\u00c0\\u00c1\\u00c2\\u00c3\\u00c4\\u00c5\\u00c6\\u00c7\\u00c8\\u00c9\\u00ca\\u00cb\\u00cc\\u00cd\\u00ce\\u00cf','\\u00d0\\u00d1\\u00d2\\u00d3\\u00d4\\u00d5\\u00d6\\u00d9\\u00da\\u00db\\u00dc\\u00dd\\u00e0\\u00e1\\u00e2\\u00e3','\\u00e4\\u00e5\\u00e6\\u00e7\\u00ec\\u00ed\\u00ee\\u00ef\\u00f1\\u00f2\\u00f3\\u00f4\\u00f5\\u00f6\\u00f9\\u00fa','\\u00fb\\u00fc\\u00fd\\u00ff\\u0100\\u0101\\u0102\\u0103\\u0104\\u0105\\u0106\\u0107\\u0108\\u0109\\u010a\\u010b','\\u010c\\u010d\\u010e\\u010f\\u0110\\u0111\\u0112\\u0113\\u0114\\u0115\\u0116\\u0117\\u0118\\u0119\\u011a\\u011b','\\u011c\\u011d\\u1e20\\u1e21\\u011e\\u011f\\u0120\\u0121\\u0122\\u0123\\u0124\\u0125\\u0126\\u0127\\u0128\\u0129','\\u012a\\u012b\\u012c\\u012d\\u012e\\u012f\\u0130\\u0131\\u0134\\u0135\\u0136\\u0137\\u0139\\u013a\\u013b\\u013c','\\u013d\\u013e\\u013f\\u0140\\u0141\\u0142\\u0143\\u0144\\u0145\\u0146\\u0147\\u0148\\u014a\\u014b\\u014c\\u014d','\\u014e\\u014f\\u0150\\u0151\\u0152\\u0153\\u0154\\u0155\\u0156\\u0157\\u0158\\u0159\\u015a\\u015b\\u015c\\u015d','\\u015e\\u015f\\u0160\\u0161\\u0162\\u0163\\u0164\\u0165\\u0166\\u0167\\u0168\\u0169\\u016a\\u016b\\u016c\\u016d','\\u016e\\u016f\\u0170\\u0171\\u0172\\u0173\\u0174\\u0175\\u0176\\u0177\\u0178\\u0179\\u017a\\u017b\\u017c\\u017d','\\u017e\\u01fc\\u01fd\\u01fe\\u01ff\\u0218\\u0219\\u021a\\u021b\\u0386\\u0388\\u0389\\u038a\\u038c\\u038e\\u038f','\\u0390\\u03aa\\u03ab\\u03ac\\u03ad\\u03ae\\u03af\\u03b0\\u03ca\\u03cb\\u03cc\\u03cd\\u03ce\\u0400\\u0401\\u0403','\\u0407\\u040c\\u040d\\u040e\\u0419\\u0439\\u0450\\u0451\\u0452\\u0453\\u0457\\u045b\\u045c\\u045d\\u045e\\u045f','\\u0490\\u0491\\u1e02\\u1e03\\u1e0a\\u1e0b\\u1e1e\\u1e1f\\u1e22\\u1e23\\u1e30\\u1e31\\u1e40\\u1e41\\u1e56\\u1e57','\\u1e60\\u1e61\\u1e6a\\u1e6b\\u1e80\\u1e81\\u1e82\\u1e83\\u1e84\\u1e85\\u1ef2\\u1ef3\\u00e8\\u00e9\\u00ea\\u00eb','\\u0149\\u01e7\\u01eb\\u040f\\u1e0d\\u1e25\\u1e5b\\u1e6d\\u1e92\\u1eca\\u1ecb\\u1ecc\\u1ecd\\u1ee4\\u1ee5\\u2116','\\u0207\\u0194\\u0263\\u0283\\u0000\\u0000\\u0000\\u0000\\u0000\\u0000\\u0000\\u0000\\u0000\\u0000\\u0000\\u0000']},{'type':'bitmap','file':'minecraft:font/ascii.png','ascent':7,'chars':['\\u00c0\\u00c1\\u00c2\\u00c8\\u00ca\\u00cb\\u00cd\\u00d3\\u00d4\\u00d5\\u00da\\u00df\\u00e3\\u00f5\\u011f\\u0130','\\u0131\\u0152\\u0153\\u015e\\u015f\\u0174\\u0175\\u017e\\u0207\\u0000\\u0000\\u0000\\u0000\\u0000\\u0000\\u0000','\\u0020\\u0021\\\"\\u0023\\u0024\\u0025\\u0026\\u0027\\u0028\\u0029\\u002a\\u002b\\u002c\\u002d\\u002e\\u002f','\\u0030\\u0031\\u0032\\u0033\\u0034\\u0035\\u0036\\u0037\\u0038\\u0039\\u003a\\u003b\\u003c\\u003d\\u003e\\u003f','\\u0040\\u0041\\u0042\\u0043\\u0044\\u0045\\u0046\\u0047\\u0048\\u0049\\u004a\\u004b\\u004c\\u004d\\u004e\\u004f','\\u0050\\u0051\\u0052\\u0053\\u0054\\u0055\\u0056\\u0057\\u0058\\u0059\\u005a\\u005b\\\\\\u005d\\u005e\\u005f','\\u0060\\u0061\\u0062\\u0063\\u0064\\u0065\\u0066\\u0067\\u0068\\u0069\\u006a\\u006b\\u006c\\u006d\\u006e\\u006f','\\u0070\\u0071\\u0072\\u0073\\u0074\\u0075\\u0076\\u0077\\u0078\\u0079\\u007a\\u007b\\u007c\\u007d\\u007e\\u0000','\\u00c7\\u00fc\\u00e9\\u00e2\\u00e4\\u00e0\\u00e5\\u00e7\\u00ea\\u00eb\\u00e8\\u00ef\\u00ee\\u00ec\\u00c4\\u00c5','\\u00c9\\u00e6\\u00c6\\u00f4\\u00f6\\u00f2\\u00fb\\u00f9\\u00ff\\u00d6\\u00dc\\u00f8\\u00a3\\u00d8\\u00d7\\u0192','\\u00e1\\u00ed\\u00f3\\u00fa\\u00f1\\u00d1\\u00aa\\u00ba\\u00bf\\u00ae\\u00ac\\u00bd\\u00bc\\u00a1\\u00ab\\u00bb','\\u2591\\u2592\\u2593\\u2502\\u2524\\u2561\\u2562\\u2556\\u2555\\u2563\\u2551\\u2557\\u255d\\u255c\\u255b\\u2510','\\u2514\\u2534\\u252c\\u251c\\u2500\\u253c\\u255e\\u255f\\u255a\\u2554\\u2569\\u2566\\u2560\\u2550\\u256c\\u2567','\\u2568\\u2564\\u2565\\u2559\\u2558\\u2552\\u2553\\u256b\\u256a\\u2518\\u250c\\u2588\\u2584\\u258c\\u2590\\u2580','\\u03b1\\u03b2\\u0393\\u03c0\\u03a3\\u03c3\\u03bc\\u03c4\\u03a6\\u0398\\u03a9\\u03b4\\u221e\\u2205\\u2208\\u2229','\\u2261\\u00b1\\u2265\\u2264\\u2320\\u2321\\u00f7\\u2248\\u00b0\\u2219\\u00b7\\u221a\\u207f\\u00b2\\u25a0\\u0000']},{'type':'legacy_unicode','sizes':'minecraft:font/glyph_sizes.bin','template':'minecraft:font/unicode_page_%s.png'}]}");

    }

    public void exportPack() {
        /* Creating Resource Pack folder tree and pack.mcmeta file */
        (new File("output/" + name + "/assets/minecraft/models/block")).mkdirs();
        JSONParser.writeToFile(packMCMETA, "output/" + name + "/pack.mcmeta");
        String packLocation = "output/" + name + "/assets/minecraft/";
        (new File(packLocation + "models/item")).mkdir();
        (new File(packLocation + "textures/block")).mkdirs();
        (new File(packLocation + "textures/item")).mkdir();
        (new File(packLocation + "textures/font")).mkdir();
        (new File(packLocation + "font")).mkdir();

        /* Creating font/default.json file */
        // TODO: Add a way to give the ResourcePack custom GUI images to be added to fontDefault
        JSONParser.writeToFile(fontDefault,packLocation + "font/default.json");
        // TODO: Copy font image files to textures/font

        /* Creating models/item/item_frame.json */
        JSONParser.writeToFile(itemFrameModel, packLocation + "models/item/item_frame.json");

        for(String blockName : blocks.keySet()) {
            Block block = blocks.get(blockName);
            JSONParser.writeToFile(block.getBlockHandModel(), packLocation + "models/block/" + block.getBlockName() + ".json");
            JSONParser.writeToFile(block.getBlockDispModel(), packLocation + "models/block/" + block.getBlockName() + "_display.json");
            if(block.getType() == ItemType.SIMPLE) {
                try {
                    Files.copy(Paths.get("input/blocks/textures/" + block.getBlockName() + ".png"), Paths.get(packLocation + "textures/block/" + block.getBlockName() + ".png"), StandardCopyOption.REPLACE_EXISTING);
                }
                catch(IOException e) {
                    System.out.println("Error copying texture file for " + block.getDisplayName() + ":\n" + e);
                }
            }
            else {
                for(String texture : ((ComplexBlock)block).getTextures()) {
                    try {
                        Files.copy(Paths.get("input/blocks/textures/" + texture + ".png"), Paths.get(packLocation + "textures/block/" + texture + ".png"), StandardCopyOption.REPLACE_EXISTING);
                    }
                    catch(IOException e) {
                        System.out.println("Error copying texture file [" + texture + "] for " + block.getDisplayName() + ":\n" + e);
                    }
                }
            }
        }
    }

    private static int nextItemFrameID() {
        // Value of 0 never used -> intentional!!
        NEXT_ITEM_FRAME_ID += 2;
        return NEXT_ITEM_FRAME_ID;
    }

    public ArrayList<Block> getBlocks() {
        ArrayList<Block> blockList = new ArrayList<Block>();
        for(String key : blocks.keySet()) {
            blockList.add(blocks.get(key));
        }
        return blockList;
    }

    public ArrayList<Item> getItems() {
        ArrayList<Item> itemList = new ArrayList<Item>();
        for(String key : items.keySet()) {
            itemList.add(items.get(key));
        }
        return itemList;
    }

    public void addBlock(JSONObject data) {
        String name = data.get("name").toString();
        ItemType type;
        if(data.get("type").toString().equals("simple")) {
            type = ItemType.SIMPLE;
        }
        else {
            type = ItemType.COMPLEX;
        }
        System.out.println("Adding Block: " + name + " (" + type + ")");
        if(type == ItemType.SIMPLE) {
            // Simple Block
            SimpleBlock newBlock = new SimpleBlock(name, nextItemFrameID());
            addCustomModels(newBlock);
        }
        else {
            // Complex Block
            RotationType rotation = RotationType.FIXED;
            try {
                rotation = RotationType.valueOf(data.get("rotation").toString().toUpperCase());
            }
            catch(IllegalArgumentException e) {
                System.out.println("Unknown rotation type error for block '" + name + "': Defaulting to fixed");
            }
            ComplexBlock newBlock = new ComplexBlock(name, data, rotation, nextItemFrameID());
            addCustomModels(newBlock);
        }
    }

    private void addCustomModels(Block block) {
        JSONArray overrides;
        if(itemFrameModel.get("overrides") == null) {
            overrides = new JSONArray();
            itemFrameModel.set("overrides", overrides);
        }
        else {
            overrides = (JSONArray)itemFrameModel.get("overrides");
        }
        overrides.add(block.getItemFrameHandOverride());
        overrides.add(block.getItemFrameDispOverride());
        blocks.put(block.getBlockName(), block);
    }

}
