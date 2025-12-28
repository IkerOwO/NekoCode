package frontEnd.editor;

import utils.Compiler;
import utils.CreateFileClass;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;

public class Editor extends JFrame {
    public JLabel fileNameLbl;
    public JTextArea codeArea;
    public JButton writeInfoBTN, compileBTN;
    private final File file;

    public Editor(File file) {
        this.file = file;
        setLayout(null);

        fileNameLbl = new JLabel("Editing: " + file.getName());
        fileNameLbl.setFont(new Font("Aptos", Font.PLAIN, 22));
        fileNameLbl.setBounds(10, 0, 300, 100);
        fileNameLbl.setVisible(true);
        add(fileNameLbl);

        writeInfoBTN = new JButton("Write Code");
        writeInfoBTN.setBounds(10, 150, 120, 30);
        writeInfoBTN.setVisible(true);
        writeInfoBTN.addActionListener(this::writeIntoFile);
        add(writeInfoBTN);

        compileBTN = new JButton("Compile");
        compileBTN.setBounds(10, 200, 120, 30);
        compileBTN.setVisible(true);
        compileBTN.addActionListener(this::compileFile);
        add(compileBTN);

        codeArea = new JTextArea();
        codeArea.setLineWrap(false);
        codeArea.setWrapStyleWord(false);
        codeArea.setBounds(200, 10, 1650, 1060);
        codeArea.setVisible(true);
        add(codeArea);

        // IN CASE THE FILE EXISTS, LOAD CODE INTO TEXTAREA
        String existingCode = CreateFileClass.readFile(file);
        codeArea.setText(existingCode);
    }

    public void writeIntoFile(ActionEvent a) {
        String code = codeArea.getText();
        CreateFileClass.writeToFile(file, code);
    }

    public void compileFile(ActionEvent e){
        // CALL COMPILER
        Compiler compiler = new Compiler();
    }
}

