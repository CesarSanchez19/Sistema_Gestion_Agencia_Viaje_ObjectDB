package com.myagency.travelagencyobjectdb.vista;

import com.myagency.travelagencyobjectdb.modelo.ReservaHotel;
import com.myagency.travelagencyobjectdb.modelo.Turista;
import com.myagency.travelagencyobjectdb.servicio.ReservaHotelService;
import com.myagency.travelagencyobjectdb.servicio.TuristaService;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

public class ReservaHotelFrame extends JFrame {

    private JTextField txtCodigoReserva, txtNombreHotel, txtFechaEntrada, txtFechaSalida;
    private JComboBox<Turista> cbTuristas;
    private JButton btnGuardar, btnEliminar, btnActualizar, btnLimpiar, btnRefrescar;
    private JTable tablaReservas;
    private ReservaHotelTableModel tableModel;

    private ReservaHotelService reservaService;
    private TuristaService turistaService;
    private ReservaHotel reservaSeleccionada;

    public ReservaHotelFrame() {
        reservaService = new ReservaHotelService();
        turistaService = new TuristaService();
        initComponents();
        configureFrame();
        loadData();
        loadTuristas();
    }

    private void initComponents() {
        setTitle("Gestión de Reservas de Hotel");
        setLayout(new BorderLayout());

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel formPanel = createFormPanel();
        mainPanel.add(formPanel, BorderLayout.WEST);

        JPanel tablePanel = createTablePanel();
        mainPanel.add(tablePanel, BorderLayout.CENTER);

        add(mainPanel);

        configureEvents();
    }

    private JPanel createFormPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(new TitledBorder("Datos de la Reserva"));
        panel.setPreferredSize(new Dimension(320, 0));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5,5,5,5);
        gbc.anchor = GridBagConstraints.WEST;

        // Código Reserva
        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(new JLabel("Código Reserva:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL;
        txtCodigoReserva = new JTextField(15);
        panel.add(txtCodigoReserva, gbc);

        // Nombre Hotel
        gbc.gridx = 0; gbc.gridy++;
        gbc.fill = GridBagConstraints.NONE;
        panel.add(new JLabel("Nombre Hotel:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL;
        txtNombreHotel = new JTextField(15);
        panel.add(txtNombreHotel, gbc);

        // Fecha Entrada (yyyy-MM-dd)
        gbc.gridx = 0; gbc.gridy++;
        gbc.fill = GridBagConstraints.NONE;
        panel.add(new JLabel("Fecha Entrada (yyyy-MM-dd):"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL;
        txtFechaEntrada = new JTextField(15);
        panel.add(txtFechaEntrada, gbc);

        // Fecha Salida (yyyy-MM-dd)
        gbc.gridx = 0; gbc.gridy++;
        gbc.fill = GridBagConstraints.NONE;
        panel.add(new JLabel("Fecha Salida (yyyy-MM-dd):"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL;
        txtFechaSalida = new JTextField(15);
        panel.add(txtFechaSalida, gbc);

        // Turista (ComboBox)
        gbc.gridx = 0; gbc.gridy++;
        gbc.fill = GridBagConstraints.NONE;
        panel.add(new JLabel("Turista:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL;
        cbTuristas = new JComboBox<>();
        panel.add(cbTuristas, gbc);

        // Botones
        JPanel buttonPanel = new JPanel(new GridLayout(5,1,5,5));
        btnGuardar = new JButton("Guardar");
        btnActualizar = new JButton("Actualizar"); btnActualizar.setEnabled(false);
        btnEliminar = new JButton("Eliminar"); btnEliminar.setEnabled(false);
        btnLimpiar = new JButton("Limpiar");
        btnRefrescar = new JButton("Refrescar");

        buttonPanel.add(btnGuardar);
        buttonPanel.add(btnActualizar);
        buttonPanel.add(btnEliminar);
        buttonPanel.add(btnLimpiar);
        buttonPanel.add(btnRefrescar);

        gbc.gridx = 0; gbc.gridy++;
        gbc.gridwidth = 2; gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(buttonPanel, gbc);

        return panel;
    }

    private JPanel createTablePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(new TitledBorder("Reservas de Hotel Registradas"));

        tableModel = new ReservaHotelTableModel();
        tablaReservas = new JTable(tableModel);
        tablaReservas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tablaReservas.setRowHeight(25);

        JScrollPane scrollPane = new JScrollPane(tablaReservas);
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    private void configureFrame() {
        setSize(900, 520);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    private void configureEvents() {
        tablaReservas.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int row = tablaReservas.getSelectedRow();
                if (row != -1) {
                    reservaSeleccionada = tableModel.getReservaAt(row);
                    cargarDatos(reservaSeleccionada);
                    btnActualizar.setEnabled(true);
                    btnEliminar.setEnabled(true);
                } else {
                    limpiarSeleccion();
                }
            }
        });

        btnGuardar.addActionListener(e -> guardarReserva());
        btnActualizar.addActionListener(e -> actualizarReserva());
        btnEliminar.addActionListener(e -> eliminarReserva());
        btnLimpiar.addActionListener(e -> limpiarFormulario());
        btnRefrescar.addActionListener(e -> loadData());
    }

    private void guardarReserva() {
        if (validarCampos()) {
            try {
                LocalDate entrada = LocalDate.parse(txtFechaEntrada.getText().trim());
                LocalDate salida = LocalDate.parse(txtFechaSalida.getText().trim());

                ReservaHotel r = new ReservaHotel(
                    txtCodigoReserva.getText().trim(),
                    txtNombreHotel.getText().trim(),
                    entrada,
                    salida,
                    (Turista) cbTuristas.getSelectedItem()
                );
                reservaService.registrarReserva(r);
                loadData();
                limpiarFormulario();
                mostrarMensaje("Reserva guardada correctamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            } catch (DateTimeParseException ex) {
                mostrarMensaje("Formato de fecha inválido. Use yyyy-MM-dd", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void actualizarReserva() {
        if (reservaSeleccionada != null && validarCampos()) {
            try {
                LocalDate entrada = LocalDate.parse(txtFechaEntrada.getText().trim());
                LocalDate salida = LocalDate.parse(txtFechaSalida.getText().trim());

                reservaSeleccionada.setCodigoReserva(txtCodigoReserva.getText().trim());
                reservaSeleccionada.setNombreHotel(txtNombreHotel.getText().trim());
                reservaSeleccionada.setFechaEntrada(entrada);
                reservaSeleccionada.setFechaSalida(salida);
                reservaSeleccionada.setTurista((Turista) cbTuristas.getSelectedItem());

                reservaService.actualizarReserva(reservaSeleccionada);
                loadData();
                limpiarFormulario();
                mostrarMensaje("Reserva actualizada correctamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            } catch (DateTimeParseException ex) {
                mostrarMensaje("Formato de fecha inválido. Use yyyy-MM-dd", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void eliminarReserva() {
        if (reservaSeleccionada != null) {
            int confirm = JOptionPane.showConfirmDialog(this, "¿Eliminar esta reserva?", "Confirmar", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                reservaService.eliminarReserva(reservaSeleccionada.getId());
                loadData();
                limpiarFormulario();
                mostrarMensaje("Reserva eliminada", "Información", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }

    private void limpiarFormulario() {
        txtCodigoReserva.setText("");
        txtNombreHotel.setText("");
        txtFechaEntrada.setText("");
        txtFechaSalida.setText("");
        cbTuristas.setSelectedIndex(-1);
        limpiarSeleccion();
    }

    private void limpiarSeleccion() {
        tablaReservas.clearSelection();
        reservaSeleccionada = null;
        btnActualizar.setEnabled(false);
        btnEliminar.setEnabled(false);
    }

    private void cargarDatos(ReservaHotel r) {
        txtCodigoReserva.setText(r.getCodigoReserva());
        txtNombreHotel.setText(r.getNombreHotel());
        txtFechaEntrada.setText(r.getFechaEntrada() != null ? r.getFechaEntrada().toString() : "");
        txtFechaSalida.setText(r.getFechaSalida() != null ? r.getFechaSalida().toString() : "");
        cbTuristas.setSelectedItem(r.getTurista());
    }

    private void loadData() {
        List<ReservaHotel> lista = reservaService.obtenerTodas();
        tableModel.setReservas(lista);
    }

    private void loadTuristas() {
        List<Turista> listaTuristas = turistaService.obtenerTodos();
        cbTuristas.removeAllItems();
        for (Turista t : listaTuristas) {
            cbTuristas.addItem(t);
        }
        cbTuristas.setSelectedIndex(-1);
    }

    private boolean validarCampos() {
        if (txtCodigoReserva.getText().trim().isEmpty() ||
            txtNombreHotel.getText().trim().isEmpty() ||
            txtFechaEntrada.getText().trim().isEmpty() ||
            txtFechaSalida.getText().trim().isEmpty() ||
            cbTuristas.getSelectedItem() == null) {
            mostrarMensaje("Todos los campos son obligatorios", "Validación", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        return true;
    }

    private void mostrarMensaje(String mensaje, String titulo, int tipo) {
        JOptionPane.showMessageDialog(this, mensaje, titulo, tipo);
    }
}
