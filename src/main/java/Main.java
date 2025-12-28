package main.java;
import frontEnd.dashboard.Dashboard;
import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class Main {
    public static void main(String[] args) {
        Dashboard dashboard = new Dashboard();
        try {
            dashboard.setSize(1920,1080);
            dashboard.setResizable(false);
            dashboard.setTitle("NekoCode");
            dashboard.setVisible(true);
            dashboard.setIconImage(new ImageIcon(Objects.requireNonNull(Main.class.getResource("/assets/icon.png"))).getImage());
            dashboard.getContentPane().setBackground(Color.WHITE);
            dashboard.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}