package com.myagency.travelagencyobjectdb.vista;

import com.myagency.travelagencyobjectdb.modelo.Turista;
import com.myagency.travelagencyobjectdb.servicio.TuristaService;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.*;

public class TuristaFrame extends JFrame {

    private JTextField txtCodigo, txtNombre, txtApellidos, txtDireccion, txtTelefono;
    private JButton btnGuardar, btnEliminar, btnActualizar, btnLimpiar, btnRefrescar;
    private JTable tablaTuristas;
    private TuristaTableModel tableModel;

    private TuristaService turistaService;
    private Turista turistaSeleccionado;

    public TuristaFrame() {
        turistaService = new TuristaService();
        initComponents();
        configureFrame();
        loadData();
    }

    private void initComponents() {
        setTitle("Gestión de Turistas");
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
        panel.setBorder(new TitledBorder("Datos del Turista"));
        panel.setPreferredSize(new Dimension(300, 0));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        // Campos
        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(new JLabel("Código:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL;
        txtCodigo = new JTextField(15); panel.add(txtCodigo, gbc);

        gbc.gridx = 0; gbc.gridy++;
        gbc.fill = GridBagConstraints.NONE; panel.add(new JLabel("Nombre:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL;
        txtNombre = new JTextField(15); panel.add(txtNombre, gbc);

        gbc.gridx = 0; gbc.gridy++;
        panel.add(new JLabel("Apellidos:"), gbc);
        gbc.gridx = 1; txtApellidos = new JTextField(15); panel.add(txtApellidos, gbc);

        gbc.gridx = 0; gbc.gridy++;
        panel.add(new JLabel("Dirección:"), gbc);
        gbc.gridx = 1; txtDireccion = new JTextField(15); panel.add(txtDireccion, gbc);

        gbc.gridx = 0; gbc.gridy++;
        panel.add(new JLabel("Teléfono:"), gbc);
        gbc.gridx = 1; txtTelefono = new JTextField(15); panel.add(txtTelefono, gbc);

        // Botones
        JPanel buttonPanel = new JPanel(new GridLayout(5, 1, 5, 5));
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
        panel.setBorder(new TitledBorder("Turistas Registrados"));

        tableModel = new TuristaTableModel();
        tablaTuristas = new JTable(tableModel);
        tablaTuristas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tablaTuristas.setRowHeight(25);

        JScrollPane scrollPane = new JScrollPane(tablaTuristas);
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    private void configureFrame() {
        setSize(850, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    private void configureEvents() {
        tablaTuristas.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int row = tablaTuristas.getSelectedRow();
                if (row != -1) {
                    turistaSeleccionado = tableModel.getTuristaAt(row);
                    cargarDatos(turistaSeleccionado);
                    btnActualizar.setEnabled(true);
                    btnEliminar.setEnabled(true);
                } else {
                    limpiarSeleccion();
                }
            }
        });

        btnGuardar.addActionListener(e -> guardarTurista());
        btnActualizar.addActionListener(e -> actualizarTurista());
        btnEliminar.addActionListener(e -> eliminarTurista());
        btnLimpiar.addActionListener(e -> limpiarFormulario());
        btnRefrescar.addActionListener(e -> loadData());
    }

    private void guardarTurista() {
        if (validarCampos()) {
            Turista t = new Turista(
                txtCodigo.getText().trim(),
                txtNombre.getText().trim(),
                txtApellidos.getText().trim(),
                txtDireccion.getText().trim(),
                txtTelefono.getText().trim()
            );
            turistaService.registrarTurista(t);
            loadData();
            limpiarFormulario();
            mostrarMensaje("Turista guardado correctamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void actualizarTurista() {
        if (turistaSeleccionado != null && validarCampos()) {
            turistaSeleccionado.setCodigoTurista(txtCodigo.getText().trim());
            turistaSeleccionado.setNombre(txtNombre.getText().trim());
            turistaSeleccionado.setApellidos(txtApellidos.getText().trim());
            turistaSeleccionado.setDireccion(txtDireccion.getText().trim());
            turistaSeleccionado.setTelefono(txtTelefono.getText().trim());

            turistaService.actualizarTurista(turistaSeleccionado);
            loadData();
            limpiarFormulario();
            mostrarMensaje("Turista actualizado correctamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void eliminarTurista() {
        if (turistaSeleccionado != null) {
            int confirm = JOptionPane.showConfirmDialog(this, "¿Eliminar este turista?", "Confirmar", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                turistaService.eliminarTurista(turistaSeleccionado.getId());
                loadData();
                limpiarFormulario();
                mostrarMensaje("Turista eliminado", "Información", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }

    private void limpiarFormulario() {
        txtCodigo.setText("");
        txtNombre.setText("");
        txtApellidos.setText("");
        txtDireccion.setText("");
        txtTelefono.setText("");
        limpiarSeleccion();
    }

    private void limpiarSeleccion() {
        tablaTuristas.clearSelection();
        turistaSeleccionado = null;
        btnActualizar.setEnabled(false);
        btnEliminar.setEnabled(false);
    }

    private void cargarDatos(Turista t) {
        txtCodigo.setText(t.getCodigoTurista());
        txtNombre.setText(t.getNombre());
        txtApellidos.setText(t.getApellidos());
        txtDireccion.setText(t.getDireccion());
        txtTelefono.setText(t.getTelefono());
    }

    private void loadData() {
        tableModel.setTuristas(turistaService.obtenerTodos());
    }

    private boolean validarCampos() {
        if (txtCodigo.getText().trim().isEmpty() || txtNombre.getText().trim().isEmpty() ||
            txtApellidos.getText().trim().isEmpty() || txtDireccion.getText().trim().isEmpty() ||
            txtTelefono.getText().trim().isEmpty()) {
            mostrarMensaje("Todos los campos son obligatorios", "Validación", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        return true;
    }

    private void mostrarMensaje(String mensaje, String titulo, int tipo) {
        JOptionPane.showMessageDialog(this, mensaje, titulo, tipo);
    }
}
