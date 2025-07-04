package com.myagency.travelagencyobjectdb.vista;

import com.myagency.travelagencyobjectdb.modelo.ReservaHotel;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

public class ReservaHotelTableModel extends AbstractTableModel {

    private final String[] columnNames = {
        "ID",
        "Código Reserva",
        "Nombre Hotel",
        "Fecha Entrada",
        "Fecha Salida",
        "Turista"
    };

    private List<ReservaHotel> reservas;
    private final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public ReservaHotelTableModel() {
        this.reservas = new ArrayList<>();
    }

    public ReservaHotelTableModel(List<ReservaHotel> reservas) {
        this.reservas = reservas != null ? reservas : new ArrayList<>();
    }

    @Override
    public int getRowCount() {
        return reservas.size();
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
        ReservaHotel r = reservas.get(rowIndex);

        switch (columnIndex) {
            case 0:
                return r.getId();
            case 1:
                return r.getCodigoReserva();
            case 2:
                return r.getNombreHotel();
            case 3:
                return r.getFechaEntrada() != null ? r.getFechaEntrada().format(dtf) : "";
            case 4:
                return r.getFechaSalida() != null ? r.getFechaSalida().format(dtf) : "";
            case 5:
                return (r.getTurista() != null) ? r.getTurista().getNombre() + " " + r.getTurista().getApellidos() : "";
            default:
                return null;
        }
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        if (columnIndex == 0) return Long.class;
        return String.class;
    }

    public void setReservas(List<ReservaHotel> reservas) {
        this.reservas = reservas != null ? reservas : new ArrayList<>();
        fireTableDataChanged();
    }

    public ReservaHotel getReservaAt(int rowIndex) {
        if (rowIndex >= 0 && rowIndex < reservas.size()) {
            return reservas.get(rowIndex);
        }
        return null;
    }

    public void addReserva(ReservaHotel reserva) {
        reservas.add(reserva);
        fireTableRowsInserted(reservas.size() - 1, reservas.size() - 1);
    }

    public void removeReserva(int rowIndex) {
        if (rowIndex >= 0 && rowIndex < reservas.size()) {
            reservas.remove(rowIndex);
            fireTableRowsDeleted(rowIndex, rowIndex);
        }
    }
}
