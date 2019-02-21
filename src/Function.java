import java.util.ArrayList;

public class Function {
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
