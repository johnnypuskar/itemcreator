import java.util.ArrayList;

public class Function {
    // TODO: NOT SURE HOW THIS CLASS IS GOING TO BE USED YET, NOT PUTTING IN A PACKAGE UNTIL THAT IS DETERMINED

    private String filename;
    private ArrayList<String> commands = new ArrayList<String>();

    public Function(String filename) {
        this.filename = filename;
    }

    public void addLine(String command) {
        commands.add(command);
    }

    public void createFile(String path) {

    }

    public ArrayList<String> getCommands() {
        return commands;
    }
}
