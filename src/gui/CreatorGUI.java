package gui;

import javax.swing.*;

public class CreatorGUI {
    private JPanel creatorView;
    private JTabbedPane tabbedPane1;
    private JComboBox blockList;
    private JCheckBox makeComplexCheckBox;
    private JButton createNewBlock;
    private JComboBox comboBox1;
    private JCheckBox makeComplexCheckBox1;
    private JButton addItemButton;
    private JTextField textField1;
    private JTextField textField2;

    public static void main(String[] args) {
        JFrame frame = new JFrame("CreatorGUI");
        frame.setContentPane(new CreatorGUI().creatorView);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
