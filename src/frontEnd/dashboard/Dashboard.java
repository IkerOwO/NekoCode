package frontEnd.dashboard;

import utils.CreateFileClass;
import frontEnd.editor.Editor;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.Objects;

public class Dashboard extends JFrame {
    final JLabel fileNameLbl;
    final JTextField fileNameField;
    final JButton submitBTN;

    public Dashboard(){
        setLayout(null);

        fileNameLbl = new JLabel("File name");
        fileNameLbl.setBounds(900,70,100,100);
        fileNameLbl.setFont(new Font("Aptos", Font.PLAIN, 22));
        fileNameLbl.setVisible(true);
        add(fileNameLbl);

        fileNameField = new JTextField();
        fileNameField.setBounds(900,150,120,25);
        fileNameField.setVisible(true);
        add(fileNameField);

        submitBTN = new JButton("Create");
        submitBTN.setBounds(900,200,120,40);
        submitBTN.addActionListener(e -> createFile(e, this));
        add(submitBTN);
    }

    public void createFile(ActionEvent e, Dashboard dashboard){
        String fileName = fileNameField.getText().trim();
        if(fileName.isEmpty()){
            JOptionPane.showMessageDialog(
                    null,
                    "There is no File Name",
                    "File Error",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }
        // CREATE FILE VIA UTILS CLASS
        File file = CreateFileClass.createFile(fileName);
        try{
            Editor page = new Editor(file);
            page.setVisible(true);
            page.setTitle("Editor");
            page.setSize(1920,1080);
            page.setIconImage(new ImageIcon(Objects.requireNonNull(Editor.class.getResource("/assets/icon.png"))).getImage());
            page.setResizable(true);
            page.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            dashboard.setVisible(false);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }

    }
}
