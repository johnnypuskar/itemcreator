package data;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Function {
    private String name;
    private ArrayList<String> commands = new ArrayList<String>();

    public Function(String name) {
        this.name = name;
    }

    public void addLine(String command) {
        commands.add(command);
    }

    public void exportToFolder(String folder) {
        try {
            File file = new File(folder + "/" + name + ".mcfunction");
            if(!file.exists()) {
                file.mkdirs();
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
