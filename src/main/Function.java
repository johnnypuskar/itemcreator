package main;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Function {
    private String filename;
    private ArrayList<String> commands = new ArrayList<String>();

    public Function(String function_name) {
        this.filename = "src/files/output/" + function_name + ".mcfunction";
    }

    public void addLine(String command) {
        commands.add(command);
    }

    public void createFile(String path) {
        try {
            File file = new File(filename);
            if(!file.exists()) {
                file.createNewFile();
            }
            BufferedWriter wrt = new BufferedWriter(new FileWriter(file));
            for(String command : commands) {
                wrt.write(command + "\n");
            }
            wrt.close();
        }
        catch(IOException e) {
            System.out.println("Could not create/write to file!\n" + e);
        }
    }

    public ArrayList<String> getCommands() {
        return commands;
    }
}
