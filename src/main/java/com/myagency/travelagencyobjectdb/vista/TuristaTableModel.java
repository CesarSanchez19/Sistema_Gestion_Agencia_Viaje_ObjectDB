package com.myagency.travelagencyobjectdb.vista;

import com.myagency.travelagencyobjectdb.modelo.Turista;
import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

public class TuristaTableModel extends AbstractTableModel {

    private final String[] columnNames = {"ID", "Código", "Nombre", "Apellidos", "Dirección", "Teléfono"};
    private List<Turista> turistas;

    public TuristaTableModel() {
        this.turistas = new ArrayList<>();
    }

    public TuristaTableModel(List<Turista> turistas) {
        this.turistas = turistas != null ? turistas : new ArrayList<>();
    }

    @Override
    public int getRowCount() {
        return turistas.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Turista t = turistas.get(rowIndex);

        switch (columnIndex) {
            case 0: return t.getId();
            case 1: return t.getCodigoTurista();
            case 2: return t.getNombre();
            case 3: return t.getApellidos();
            case 4: return t.getDireccion();
            case 5: return t.getTelefono();
            default: return null;
        }
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return (columnIndex == 0) ? Long.class : String.class;
    }

    public void setTuristas(List<Turista> turistas) {
        this.turistas = turistas != null ? turistas : new ArrayList<>();
        fireTableDataChanged();
    }

    public Turista getTuristaAt(int rowIndex) {
        if (rowIndex >= 0 && rowIndex < turistas.size()) {
            return turistas.get(rowIndex);
        }
        return null;
    }

    public void addTurista(Turista turista) {
        turistas.add(turista);
        fireTableRowsInserted(turistas.size() - 1, turistas.size() - 1);
    }

    public void removeTurista(int rowIndex) {
        if (rowIndex >= 0 && rowIndex < turistas.size()) {
            turistas.remove(rowIndex);
            fireTableRowsDeleted(rowIndex, rowIndex);
        }
    }
}
