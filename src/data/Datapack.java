package data;

import json.JSONObject;
import json.JSONParser;
import resources.ItemType;
import resources.ResourcePack;
import resources.block.Block;

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
        (new File(packLocation + "minecraft/tags/functions")).mkdirs();
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
            // TODO: Replace 'if block ~ ~ ~ air' with a block tag for all valid placement blocks (water, tall grass, etc.)
            // TODO: Give item back if placement fails
            if(block.getType() == ItemType.SIMPLE) {
                main.addLine("execute as @e[tag=block_" + block.getBlockName() + "] at @s if block ~ ~ ~ air run setblock ~ ~ ~ spawner{MaxNearbyEntities:0,RequiredPlayerRange:0s,SpawnData:{id:\"minecraft:armor_stand\",Invisible:1,Marker:1,ArmorItems:[{},{},{},{id:\"minecraft:item_frame\",Count:1b,tag:{ItemCustomData:" + block.getModelID() + ",CustomModelData:" + (block.getModelID() + 1) + ",EntityTag:{Tags:[\"block_" + block.getBlockName() + "\",\"block_item_frame\"],Item:{id:\"minecraft:item_frame\",Count:1b,tag:{CustomModelData:" + (block.getModelID() + 1) + "}}},display:{Name:\"{\\\"text\\\":\\\"" + block.getDisplayName() + "\\\",\\\"italic\\\":\\\"false\\\"}\"}}}]},SpawnerBlock:1b}");
            }
            else {
                main.addLine("execute as @e[tag=block_" + block.getBlockName() + "] at @s if block ~ ~ ~ air align xyz positioned ~0.5 ~ ~0.5 run function blocks:" + block.getBlockName() + "/place");
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
        main.exportToFolder(packLocation + "utility/functions");

        /* MAIN.MCFUNCTION */
        /* SPAWNER.JSON */
        JSONObject spawner = (JSONObject)JSONParser.parse("{'type':'minecraft:block','pools':[{'rolls':1,'entries':[{'type':'minecraft:item','name':'minecraft:item_frame','functions':[{'function':'set_nbt','tag':'{CustomModelData:0}'},{'function':'copy_nbt','source':'block_entity','ops':[{'source':'SpawnData.ArmorItems[3].tag.display','target':'display','op':'replace'},{'source':'SpawnData.ArmorItems[3].tag.ItemCustomData','target':'CustomModelData','op':'replace'},{'source':'SpawnData.ArmorItems[3].tag.EntityTag','target':'EntityTag','op':'replace'}]}]}],'conditions':[{'condition':'minecraft:survives_explosion'}]}]}");
        JSONParser.writeToFile(spawner, packLocation + "minecraft/loot_tables/blocks/spawner.json");
        /* SPAWNER.JSON */
        /* TICK.JSON */
        JSONObject tick = (JSONObject)JSONParser.parse("{'values':['utility:main']}");
        JSONParser.writeToFile(tick, packLocation + "minecraft/tags/functions/tick.json");
        /* TICK.JSON */
        /* GIVE FUNCTIONS */
        for(Block block : rp.getBlocks()) {
            Function give = new Function(block.getBlockName());
            give.addLine("give @s minecraft:item_frame{EntityTag:{Tags:[\"block_" + block.getBlockName() + "\",\"block_item_frame\"],Item:{id:\"minecraft:item_frame\",Count:1b,tag:{CustomModelData:" + (block.getModelID() + 1) + "}}},CustomModelData:" + block.getModelID() + ",KilledXP:1b,display:{Name:\"{\\\"text\\\":\\\"" + block.getDisplayName() + "\\\",\\\"italic\\\":\\\"false\\\"}\"}}");
            give.exportToFolder(packLocation + "give/functions");
        }
        /* GIVE FUNCTIONS */
    }
}
