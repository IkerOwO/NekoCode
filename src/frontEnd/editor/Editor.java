package frontEnd.editor;

import utils.Compiler;
import utils.CreateFileClass;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.Objects;

public class Editor extends JFrame {
    private JLabel fileNameLbl;
    private JTextArea codeArea;
    private JTextArea terminalArea;
    private JButton writeInfoBTN, compileBTN, themeBTN, createFileBTN, openFileBTN;
    private File file;

    public Editor() {
        setSize(1920,1080);
        setResizable(true);
        setTitle("NekoCode");
        setLayout(null);
        setVisible(true);
        setIconImage(new ImageIcon(Objects.requireNonNull(Editor.class.getResource("/assets/icon.png"))).getImage());
        getContentPane().setBackground(Color.WHITE);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        initUI();
        }

        private void initUI(){
            terminalArea = new JTextArea();
            terminalArea.setEditable(false);
            terminalArea.setLineWrap(true);
            terminalArea.setWrapStyleWord(true);

            JScrollPane terminalScroll = new JScrollPane(terminalArea);
            terminalScroll.setBounds(200, 800, 1650, 250);
            add(terminalScroll);

            compileBTN = new JButton("Compile");
            compileBTN.setBounds(10, 250, 120, 30);
            compileBTN.setVisible(true);
            compileBTN.addActionListener(e -> Compiler.compiler(file, terminalArea));
            add(compileBTN);

            writeInfoBTN = new JButton("Write Code");
            writeInfoBTN.setBounds(10,300,120,30);
            writeInfoBTN.setVisible(true);
            writeInfoBTN.addActionListener(this::writeIntoFile);
            add(writeInfoBTN);

            fileNameLbl = new JLabel("No file opened");
            fileNameLbl.setFont(new Font("Aptos", Font.PLAIN, 22));
            fileNameLbl.setBounds(10, 0, 300, 100);
            fileNameLbl.setVisible(true);
            add(fileNameLbl);

            themeBTN = new JButton("Theme");
            themeBTN.setBounds(10, 350, 120, 30);
            themeBTN.setVisible(true);
            themeBTN.addActionListener(this::changeTheme);
            add(themeBTN);

            createFileBTN = new JButton("Create");
            createFileBTN.setBounds(10, 150, 120, 30);
            createFileBTN.setVisible(true);
            createFileBTN.addActionListener(this::createFile);
            add(createFileBTN);

            openFileBTN = new JButton("Open");
            openFileBTN.setBounds(10, 200, 120, 30);
            openFileBTN.setVisible(true);
            openFileBTN.addActionListener(this::openFile);
            add(openFileBTN);

            codeArea = new JTextArea();
            codeArea.setLineWrap(false);
            codeArea.setWrapStyleWord(false);
            codeArea.setFont(new Font("Aptos", Font.PLAIN, 14));

            // TABS LIKE ON A NORMAL IDE
            codeArea.setTabSize(4);
            codeArea.getInputMap().put(KeyStroke.getKeyStroke("TAB"), "insert-tab");
            codeArea.getActionMap().put("insert-tab", new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    codeArea.replaceSelection("    ");
                }
            });

            // TAB EVERY TIME THE USER PRESS ENTER
            codeArea.addKeyListener(new KeyAdapter() {
                @Override
                public void keyPressed(KeyEvent e) {
                    if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                        try {
                            int caretPos = codeArea.getCaretPosition();
                            int line = codeArea.getLineOfOffset(caretPos);
                            int start = codeArea.getLineStartOffset(line - 1);
                            int end = codeArea.getLineEndOffset(line - 1);
                            String prevLine = codeArea.getText(start, end - start);

                            String indent = "";
                            for (char c : prevLine.toCharArray()) {
                                if (c == ' ' || c == '\t') indent += c;
                                else break;
                            }

                            String finalIndent = indent;
                            SwingUtilities.invokeLater(() -> codeArea.insert(finalIndent, codeArea.getCaretPosition()));
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                }
            });

            JScrollPane codeScroll = new JScrollPane(codeArea);
            codeScroll.setBounds(200, 10, 1650, 780);
            add(codeScroll);

        }

        private void openFile(ActionEvent e){
            JFileChooser chooser = new JFileChooser();
            chooser.setDialogTitle("Open File");

            int result = chooser.showOpenDialog(this);

            if (result != JFileChooser.APPROVE_OPTION) {
                return;
            }

            File selectedFile = chooser.getSelectedFile();

            if (selectedFile == null || !selectedFile.exists()) {
                JOptionPane.showMessageDialog(this, "Invalid file");
                return;
            }

            String content = CreateFileClass.readFile(selectedFile);
            codeArea.setText(content);

            this.file = selectedFile;
            fileNameLbl.setText("Editing: " + selectedFile.getName());

            terminalArea.append("Opened: " + selectedFile.getAbsolutePath() + "\n");

        }

        private void createFile(ActionEvent e) {
            String fileName = JOptionPane.showInputDialog(this, "Enter file name");

            if (fileName == null || fileName.trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Invalid file name");
                return;
            }

            File newFile = CreateFileClass.createFile(fileName.trim());

            if (newFile == null) {
                JOptionPane.showMessageDialog(this, "Could not create file");
                return;
            }

            if (newFile.exists() && newFile.length() > 0) {
                int option = JOptionPane.showConfirmDialog(
                        this,
                        "File already exists.\nDo you want to open it?",
                        "File exists",
                        JOptionPane.YES_NO_OPTION
                );
                if (option != JOptionPane.YES_OPTION) {
                    return;
                }
                codeArea.setText(CreateFileClass.readFile(newFile));
            } else {
                codeArea.setText("");
            }
            this.file = newFile;
            fileNameLbl.setText("Editing: " + newFile.getName());
        }


        public void writeIntoFile(ActionEvent a) {
            String code = codeArea.getText();
            CreateFileClass.writeToFile(file, code);
        }

        public void changeTheme(ActionEvent e){
            Color getBackground = getContentPane().getBackground();
            if(getBackground.equals(Color.WHITE)){
                getContentPane().setBackground(Color.DARK_GRAY);
                getContentPane().setForeground(Color.WHITE);
                fileNameLbl.setForeground(Color.WHITE);
                codeArea.setBackground(Color.DARK_GRAY);
                codeArea.setForeground(Color.WHITE);
                terminalArea.setBackground(Color.DARK_GRAY);
                terminalArea.setForeground(Color.WHITE);
            } else {
                getContentPane().setBackground(Color.WHITE);
                getContentPane().setForeground(Color.BLACK);
                fileNameLbl.setForeground(Color.BLACK);
                codeArea.setBackground(Color.WHITE);
                codeArea.setForeground(Color.BLACK);
                terminalArea.setBackground(Color.WHITE);
                terminalArea.setForeground(Color.BLACK);
            }
        }
}

