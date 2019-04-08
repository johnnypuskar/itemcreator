package main;

import data.Datapack;
import data.Function;
import json.*;
import resources.ItemType;
import resources.ResourcePack;
import resources.block.SimpleBlock;

import java.io.*;

public class Program {

    public static Function mainFunction = new Function("main");
    public static JSONObject itemFrameModel = null;

    public static void main(String[] args) {
        String packName = "Custom Pack";
        String packDesc = "A custom made datapack which adds new blocks and items";
        if(args.length > 0) {
            packName = args[0];
        }
        if(args.length > 1) {
            packDesc = args[1];
        }
        ResourcePack pack = new ResourcePack(packName, packDesc + " - Resource Pack");
        Datapack dpack = new Datapack(packName.strip().toLowerCase().replaceAll(" ", "_"), packDesc + " - Datapack");
        File[] blockFiles = new File("input/blocks").listFiles();

        /* Reading block .ttdp files and adding them as new Block objects to pack */
        for(File blockFile : blockFiles) {
            if(blockFile.isFile() && blockFile.getName().length() >= 5 && blockFile.getName().substring(blockFile.getName().length() - 4).equals(".dpb")) {
                pack.addFromFile(blockFile);
            }
        }

        File[] itemFiles = new File("input/items").listFiles();
        /* Reading block .ttdp files and adding them as new Block objects to pack */
        for(File itemFile : itemFiles) {
            if(itemFile.isFile() && itemFile.getName().length() >= 5 && itemFile.getName().substring(itemFile.getName().length() - 4).equals(".dpi")) {
                pack.addFromFile(itemFile);
            }
        }

        /* Export resource pack and datapack to output folder */
        pack.exportPack();
        dpack.exportPack(pack);
    }
}
