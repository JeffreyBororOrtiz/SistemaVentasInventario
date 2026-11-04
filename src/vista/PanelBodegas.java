package vista;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import logica.Tienda;
import modelo.Bodega;
import modelo.Empleado;

public class PanelBodegas extends JPanel implements ActionListener {

    private Tienda tienda;

    private JTextField txtId;
    private JComboBox<String> cmbEmpleado;
    private JTextField txtCapacidad;
    private JTextField txtDireccion;
    private JButton btnRegistrar;
    private JButton btnLimpiar;
    private DefaultTableModel modelo;
    private JTable tabla;

    public PanelBodegas(Tienda tienda) {
        this.tienda = tienda;
        Estilo.prepararPantalla(this);

        txtId = Estilo.campo();
        cmbEmpleado = Estilo.lista();
        txtCapacidad = Estilo.campo();
        txtDireccion = Estilo.campo();
        btnRegistrar = Estilo.boton("Registrar bodega", Estilo.VERDE);
        btnLimpiar = Estilo.boton("Limpiar", Estilo.GRIS);
        btnRegistrar.addActionListener(this);
        btnLimpiar.addActionListener(this);

        JPanel formulario = Estilo.formulario();
        formulario.add(Estilo.etiqueta("ID bodega"));
        formulario.add(txtId);
        formulario.add(Estilo.etiqueta("Empleado encargado"));
        formulario.add(cmbEmpleado);
        formulario.add(Estilo.etiqueta("Capacidad (unidades)"));
        formulario.add(txtCapacidad);
        formulario.add(Estilo.etiqueta("Direccion"));
        formulario.add(txtDireccion);
        formulario.add(new JLabel(""));
        formulario.add(btnRegistrar);
        formulario.add(btnLimpiar);

        modelo = new DefaultTableModel();
        modelo.addColumn("ID");
        modelo.addColumn("Encargado");
        modelo.addColumn("Capacidad");
        modelo.addColumn("Direccion");
        modelo.addColumn("Espacio disponible");
        tabla = Estilo.tabla(modelo);

        add(Estilo.encabezado("Bodegas", "Lugares donde se guarda la mercaderia"), BorderLayout.NORTH);
        add(Estilo.tarjetaFormulario("Nueva bodega", formulario), BorderLayout.WEST);
        add(Estilo.tarjetaTabla("Bodegas registradas", tabla), BorderLayout.CENTER);
        actualizar();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnRegistrar) {
            registrar();
        } else if (e.getSource() == btnLimpiar) {
            limpiar();
        }
    }

    private void registrar() {
        String id = txtId.getText().trim();
        String textoCapacidad = txtCapacidad.getText().trim();
        String direccion = txtDireccion.getText().trim();
        int posicionEmpleado = cmbEmpleado.getSelectedIndex();

        if (posicionEmpleado == -1) {
            JOptionPane.showMessageDialog(this, "Primero registre un empleado.");
            return;
        }
        if (id.isEmpty() || textoCapacidad.isEmpty() || direccion.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Llene todos los campos.");
            return;
        }
        if (tienda.buscarBodega(id) != null) {
            JOptionPane.showMessageDialog(this, "Ya existe una bodega con el ID " + id);
            return;
        }

        int capacidad;
        try {
            capacidad = Integer.parseInt(textoCapacidad);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "La capacidad debe ser un numero entero.");
            return;
        }
        if (capacidad <= 0) {
            JOptionPane.showMessageDialog(this, "La capacidad debe ser mayor que cero.");
            return;
        }

        Empleado encargado = tienda.getEmpleados().get(posicionEmpleado);
        Bodega bodega = new Bodega(id, encargado, capacidad, direccion);
        tienda.agregarBodega(bodega);
        JOptionPane.showMessageDialog(this, "Bodega registrada.");
        limpiar();
        actualizar();
    }

    private void limpiar() {
        txtId.setText("");
        txtCapacidad.setText("");
        txtDireccion.setText("");
    }

    public void actualizar() {
        cmbEmpleado.removeAllItems();
        for (int i = 0; i < tienda.getEmpleados().size(); i++) {
            Empleado e = tienda.getEmpleados().get(i);
            cmbEmpleado.addItem(e.getIdEmpleado() + " - " + e.getNombre());
        }

        modelo.setRowCount(0);
        for (int i = 0; i < tienda.getBodegas().size(); i++) {
            Bodega b = tienda.getBodegas().get(i);
            modelo.addRow(new Object[]{b.getIdBodega(), b.getEmpleado().getNombre(), b.getCapacidad(),
                b.getDireccion(), tienda.espacioDisponible(b)});
        }
    }
}
