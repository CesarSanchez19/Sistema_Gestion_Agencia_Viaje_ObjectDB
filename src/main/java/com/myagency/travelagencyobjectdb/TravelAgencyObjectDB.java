package com.myagency.travelagencyobjectdb;

import com.myagency.travelagencyobjectdb.vista.MainFrame;
import javax.swing.*;

public class TravelAgencyObjectDB {

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            System.err.println("Error al configurar Look and Feel: " + e.getMessage());
        }

        SwingUtilities.invokeLater(() -> {
            MainFrame main = new MainFrame();
            main.setVisible(true);
        });
    }
}
