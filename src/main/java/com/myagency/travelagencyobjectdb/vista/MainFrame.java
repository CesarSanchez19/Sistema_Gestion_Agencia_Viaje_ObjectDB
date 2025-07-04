package com.myagency.travelagencyobjectdb.vista;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MainFrame extends JFrame {

    private JButton btnTuristas;
    private JButton btnReservas;

    public MainFrame() {
        setTitle("Agencia de Viajes - Menú Principal");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Título principal
        JLabel titulo = new JLabel("Agencia de Viajes", SwingConstants.CENTER);
        titulo.setFont(new Font("SansSerif", Font.BOLD, 32));
        titulo.setForeground(new Color(33, 37, 41));
        titulo.setBorder(BorderFactory.createEmptyBorder(30, 10, 10, 10));
        add(titulo, BorderLayout.NORTH);

        // Panel central con botones
        JPanel centro = new JPanel();
        centro.setLayout(new BoxLayout(centro, BoxLayout.Y_AXIS));
        centro.setBorder(BorderFactory.createEmptyBorder(30, 200, 50, 200));
        centro.setBackground(Color.WHITE);

        // Texto guía
        JLabel subtitulo = new JLabel("Seleccione un módulo para comenzar:", SwingConstants.CENTER);
        subtitulo.setFont(new Font("SansSerif", Font.PLAIN, 20));
        subtitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        subtitulo.setBorder(BorderFactory.createEmptyBorder(10, 10, 30, 10));
        centro.add(subtitulo);

        // Botones
        btnTuristas = crearBoton("Registros de Turistas");
        btnReservas = crearBoton("Reservaciones de Hoteles");

        centro.add(btnTuristas);
        centro.add(Box.createRigidArea(new Dimension(0, 25)));
        centro.add(btnReservas);

        add(centro, BorderLayout.CENTER);

        // Listeners de navegación
        btnTuristas.addActionListener(e -> {
            this.setVisible(false);
            new TuristaFrame(this).setVisible(true);
        });

        btnReservas.addActionListener(e -> {
            this.setVisible(false);
            new ReservaHotelFrame(this).setVisible(true);
        });

        // Configuración general
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setMinimumSize(new Dimension(800, 500));
        setVisible(true);
    }

    private JButton crearBoton(String texto) {
        JButton btn = new JButton(texto);
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 18));
        btn.setPreferredSize(new Dimension(320, 60));
        btn.setMaximumSize(new Dimension(350, 60));
        btn.setFocusPainted(false);
        btn.setBackground(new Color(240, 240, 240));
        btn.setForeground(Color.BLACK);

        // Borde negro por defecto
        btn.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));

        // Hover effect: borde azul al pasar el mouse
        btn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btn.setBorder(BorderFactory.createLineBorder(new Color(30, 144, 255), 3));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btn.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
            }
        });

        return btn;
    }
}
