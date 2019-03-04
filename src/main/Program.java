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
            if(blockFile.isFile() && blockFile.getName().length() >= 6 && blockFile.getName().substring(blockFile.getName().length() - 5).equals(".ttdp")) {
                try {
                    BufferedReader br = new BufferedReader(new FileReader(blockFile));

                    JSONObject blockData = new JSONObject();

                    String line = br.readLine();
                    while(line != null) {
                        String[] fields = line.split(":");
                        if(fields.length == 2) {
                            blockData.set(fields[0].strip(), fields[1].strip());
                        }
                        line = br.readLine();
                    }
                    br.close();

                    pack.addBlock(blockData);
                }
                catch(FileNotFoundException e) {
                    System.out.println("So a file that exists isn't found. Uh oh. (Line 26 of Program)");
                }
                catch(IOException e) {
                    System.out.println(e);
                }
            }
        }

        /* Export resource pack and datapack to output folder */
        pack.exportPack();
        dpack.exportPack(pack);
    }
}
