package data;

import json.Element;
import json.JSONObject;
import json.JSONParser;
import resources.ItemType;
import resources.ResourcePack;
import resources.RotationType;
import resources.block.Block;
import resources.block.ComplexBlock;
import resources.item.ComplexItem;
import resources.item.Item;

import java.io.File;

public class Datapack {

    private String name;

    private JSONObject packMCMETA;

    public Datapack(String name, String desc) {
        this.name = name;
        packMCMETA = (JSONObject) JSONParser.parse("{'pack':{'pack_format':3,'description':'" + desc + "'}}");
    }

    public void exportPack(ResourcePack rp) {
        /* Creating Datapack folder tree and pack.mcmeta file */
        (new File("output/" + name + "/data/give/functions")).mkdirs();
        JSONParser.writeToFile(packMCMETA, "output/" + name + "/pack.mcmeta");
        String packLocation = "output/" + name + "/data/";
        (new File(packLocation + "blocks/functions")).mkdirs();
        (new File(packLocation + "utility/functions")).mkdirs();
        (new File(packLocation + "items/functions")).mkdirs();
        (new File(packLocation + "minecraft/tags/functions")).mkdirs();
        (new File(packLocation + "minecraft/tags/blocks")).mkdirs();
        (new File(packLocation + "minecraft/loot_tables/blocks")).mkdirs();

            /* [ Creating default objects ] */

        /* MAIN.MCFUNCTION */
        Function main = new Function("main");

        // Main Function Header
        main.addLine("# Main Function: Runs once per tick and manages the entire datapack");
        main.addLine("#  - Generated using JohnnyHotshot's Java Block/Item Generation Tool");
        // Default commands for killing experience orbs from breaking spawners
        main.addLine("execute as @e[nbt={Item:{tag:{EntityTag:{Tags:[\"block_item_frame\"]}}}}] unless entity @s[nbt={Item:{tag:{KilledXP:1b}}}] at @s align xyz run kill @e[type=experience_orb,dx=1,dy=1,dz=1]");
        main.addLine("execute as @e[nbt={Item:{tag:{EntityTag:{Tags:[\"block_item_frame\"]}}}}] unless entity @s[nbt={Item:{tag:{KilledXP:1b}}}] run data merge entity @s {Item:{tag:{KilledXP:1b}}}");
        // Separator
        main.addLine("");
        // Loop through all custom blocks from the resource pack and add in commands to place them into main.mcfunction
        for(Block block : rp.getBlocks()) {
            main.addLine("execute as @e[tag=block_" + block.getBlockName() + "] at @s unless block ~ ~ ~ #minecraft:intangible as @p[scores={placeItemFrame=1..},limit=1,gamemode=!creative] run function give:" + block.getBlockName());
            if(block.getType() == ItemType.SIMPLE) {
                main.addLine("execute as @e[tag=block_" + block.getBlockName() + "] at @s if block ~ ~ ~ #minecraft:intangible run setblock ~ ~ ~ spawner{MaxNearbyEntities:0,RequiredPlayerRange:0s,SpawnData:{id:\"minecraft:armor_stand\",Invisible:1,Marker:1,ArmorItems:[{},{},{},{id:\"minecraft:item_frame\",Count:1b,tag:{ItemCustomData:" + block.getModelID() + ",CustomModelData:" + (block.getModelID() + 1) + ",EntityTag:{Tags:[\"block_" + block.getBlockName() + "\",\"block_item_frame\"],Item:{id:\"minecraft:item_frame\",Count:1b,tag:{CustomModelData:" + (block.getModelID() + 1) + "}}},display:{Name:\"{\\\"text\\\":\\\"" + block.getDisplayName() + "\\\",\\\"italic\\\":\\\"false\\\"}\"}}}]},SpawnerBlock:1b}");
            }
            else {
                main.addLine("execute as @e[tag=block_" + block.getBlockName() + "] at @s if block ~ ~ ~ #minecraft:intangible align xyz positioned ~0.5 ~ ~0.5 run function blocks:" + block.getBlockName() + "/place");
                if(((ComplexBlock)block).getRotation() == RotationType.DIRECTIONAL) {
                    main.addLine("execute as @e[tag=complex_block,tag=" + block.getBlockName() + ",tag=rotated_up] at @s positioned ~ ~0.226 ~-0.225 run function blocks:" + block.getBlockName() + "/tick");
                    main.addLine("execute as @e[tag=complex_block,tag=" + block.getBlockName() + ",tag=rotated_down] at @s positioned ~ ~0.226 ~0.225 run function blocks:" + block.getBlockName() + "/tick");
                    main.addLine("execute as @e[tag=complex_block,tag=" + block.getBlockName() + ",tag=!rotated_up,tag=!rotated_down] at @s run function blocks:" + block.getBlockName() + "/tick");
                }
                else {
                    main.addLine("execute as @e[tag=complex_block,tag=" + block.getBlockName() + "] at @s run function blocks:" + block.getBlockName() + "/tick");
                }
            }
        }
        // Separator
        main.addLine("");
        // Killing item frames dropped by vanilla spawners and the placement item frames
        main.addLine("kill @e[type=item,nbt={Item:{id:\"minecraft:item_frame\",tag:{CustomModelData:0}}}]");
        main.addLine("kill @e[tag=block_item_frame]");
        // Separator
        main.addLine("");
        // Misc scoreboard and NBT data management
        main.addLine("execute as @e[tag=complex_block] run data modify entity @s Fire set value 2s");
        main.addLine("scoreboard players set @a[scores={placeItemFrame=1..}] placeItemFrame 0");
        // Separator
        main.addLine("");
        // Adding right click functionality
        main.addLine("execute as @a[scores={clickCOAS=1..}] at @s run function utility:right_click");

        main.exportToFolder(packLocation + "utility/functions");
        /* MAIN.MCFUNCTION */
        /* LOAD.MCFUNCTION */
        Function init = new Function("init");

        // Load Function Header
        init.addLine("# Init Function: Runs on world load or /reload to initialize datapack");
        init.addLine("#  - Generated using JohnnyHotshot's Java Block/Item Generation Tool");

        init.addLine("");
        init.addLine("scoreboard objectives add placeItemFrame minecraft.used:minecraft.item_frame");
        init.addLine("scoreboard objectives add clickCOAS minecraft.used:minecraft.carrot_on_a_stick");
        init.addLine("scoreboard objectives add value dummy");

        init.exportToFolder(packLocation + "utility/functions");
        /* LOAD.MCFUNCTION */
        /* SPAWNER.JSON */
        JSONObject spawner = (JSONObject)JSONParser.parse("{'type':'minecraft:block','pools':[{'rolls':1,'entries':[{'type':'minecraft:item','name':'minecraft:item_frame','functions':[{'function':'set_nbt','tag':'{CustomModelData:0}'},{'function':'copy_nbt','source':'block_entity','ops':[{'source':'SpawnData.ArmorItems[3].tag.display','target':'display','op':'replace'},{'source':'SpawnData.ArmorItems[3].tag.ItemCustomData','target':'CustomModelData','op':'replace'},{'source':'SpawnData.ArmorItems[3].tag.EntityTag','target':'EntityTag','op':'replace'}]}]}],'conditions':[{'condition':'minecraft:survives_explosion'}]}]}");
        JSONParser.writeToFile(spawner, packLocation + "minecraft/loot_tables/blocks/spawner.json");
        /* SPAWNER.JSON */
        /* TICK.JSON */
        JSONObject tick = (JSONObject)JSONParser.parse("{'values':['utility:main']}");
        JSONParser.writeToFile(tick, packLocation + "minecraft/tags/functions/tick.json");
        /* TICK.JSON */
        /* LOAD.JSON */
        JSONObject load = (JSONObject)JSONParser.parse("{'values':['utility:init']}");
        JSONParser.writeToFile(load, packLocation + "minecraft/tags/functions/load.json");
        /* LOAD.JSON */
        /* BLOCK SPECIFIC FUNCTIONS */
        for(Block block : rp.getBlocks()) {
            Function give = new Function(block.getBlockName());
            give.addLine("give @s minecraft:item_frame{EntityTag:{Tags:[\"block_" + block.getBlockName() + "\",\"block_item_frame\"],Item:{id:\"minecraft:item_frame\",Count:1b,tag:{CustomModelData:" + (block.getModelID() + 1) + "}}},CustomModelData:" + block.getModelID() + ",KilledXP:1b,display:{Name:\"{\\\"text\\\":\\\"" + block.getDisplayName() + "\\\",\\\"italic\\\":\\\"false\\\"}\"}}");
            give.exportToFolder(packLocation + "give/functions");
            if(block.getType() == ItemType.COMPLEX) {
                (new File(packLocation + "blocks/functions/" + block.getBlockName())).mkdirs();
                ((ComplexBlock)block).exportFunctions(packLocation + "blocks/functions/" + block.getBlockName());
            }
        }
        /* BLOCK SPECIFIC FUNCTIONS */
        /* ITEM SPECIFIC FUNCTIONS */
        Function rightClick = new Function("right_click");
        for(Item item : rp.getItems()) {
            item.getGiveFunction().exportToFolder(packLocation + "give/functions");
            if(item.getType() == ItemType.COMPLEX) {
                rightClick.addLine("execute if entity @s[nbt={SelectedItem:{tag:{CustomModelData:" + item.getModelID() + "}}}] run function items:" + item.getItemName());
                rightClick.addLine("execute unless entity @s[nbt={SelectedItem:{id:\"minecraft:carrot_on_a_stick\"}}] if entity @s[nbt={Inventory:[{Slot:-106b,tag:{CustomModelData:" + item.getModelID() + "}}]}] run function items:" + item.getItemName());
                ((ComplexItem)item).getClickFunction().exportToFolder(packLocation + "items/functions");
            }
        }
        rightClick.addLine("scoreboard players reset @s clickCOAS");
        rightClick.exportToFolder(packLocation + "utility/functions");
        /* ITEM SPECIFIC FUNCTIONS */
        /* INTANGIBLE BLOCK TAG */
        Element intangible = JSONParser.parse("{'values':['minecraft:air','minecraft:grass','minecraft:fern','minecraft:water','minecraft:lava','minecraft:seagrass','minecraft:tall_grass','minecraft:large_fern','minecraft:snow','minecraft:lily_pad','minecraft:vine']}");
        JSONParser.writeToFile(intangible, packLocation + "minecraft/tags/blocks/intangible.json");
        /* INTANGIBLE BLOCK TAG */

    }
}
