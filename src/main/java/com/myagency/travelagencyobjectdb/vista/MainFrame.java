package com.myagency.travelagencyobjectdb.vista;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    private JButton btnTuristas;
    private JButton btnReservas;

    public MainFrame() {
        setTitle("Agencia de Viajes - Menú Principal");
        setLayout(new FlowLayout(FlowLayout.CENTER, 20, 30));
        setSize(300, 150);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        btnTuristas = new JButton("Gestión de Turistas");
        btnReservas = new JButton("Gestión de Reservas de Hotel");

        add(btnTuristas);
        add(btnReservas);

        btnTuristas.addActionListener(e -> {
            TuristaFrame tf = new TuristaFrame();
            tf.setVisible(true);
        });

        btnReservas.addActionListener(e -> {
            ReservaHotelFrame rf = new ReservaHotelFrame();
            rf.setVisible(true);
        });
    }
}
