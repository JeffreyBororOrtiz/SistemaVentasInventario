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
import modelo.Cliente;

public class PanelClientes extends JPanel implements ActionListener {

    private Tienda tienda;

    private JTextField txtDpi;
    private JTextField txtNombre;
    private JTextField txtDireccion;
    private JTextField txtFecha;
    private JComboBox<String> cmbTipo;
    private JButton btnRegistrar;
    private JButton btnLimpiar;
    private DefaultTableModel modelo;
    private JTable tabla;

    public PanelClientes(Tienda tienda) {
        this.tienda = tienda;
        Estilo.prepararPantalla(this);

        txtDpi = Estilo.campo();
        txtNombre = Estilo.campo();
        txtDireccion = Estilo.campo();
        txtFecha = Estilo.campo();
        cmbTipo = Estilo.lista();
        cmbTipo.addItem("Regular");
        cmbTipo.addItem("VIP");
        btnRegistrar = Estilo.boton("Registrar cliente", Estilo.VERDE);
        btnLimpiar = Estilo.boton("Limpiar", Estilo.GRIS);
        btnRegistrar.addActionListener(this);
        btnLimpiar.addActionListener(this);

        JPanel formulario = Estilo.formulario();
        formulario.add(Estilo.etiqueta("DPI (13 numeros)"));
        formulario.add(txtDpi);
        formulario.add(Estilo.etiqueta("Nombre"));
        formulario.add(txtNombre);
        formulario.add(Estilo.etiqueta("Direccion"));
        formulario.add(txtDireccion);
        formulario.add(Estilo.etiqueta("Fecha de nacimiento (dd/mm/aaaa)"));
        formulario.add(txtFecha);
        formulario.add(Estilo.etiqueta("Tipo de cliente"));
        formulario.add(cmbTipo);
        formulario.add(new JLabel(""));
        formulario.add(btnRegistrar);
        formulario.add(btnLimpiar);

        modelo = new DefaultTableModel();
        modelo.addColumn("DPI");
        modelo.addColumn("Nombre");
        modelo.addColumn("Direccion");
        modelo.addColumn("Fecha de nacimiento");
        modelo.addColumn("Tipo");
        tabla = Estilo.tabla(modelo);

        add(Estilo.encabezado("Clientes", "Registre a las personas que compran en la tienda"), BorderLayout.NORTH);
        add(Estilo.tarjetaFormulario("Nuevo cliente", formulario), BorderLayout.WEST);
        add(Estilo.tarjetaTabla("Clientes registrados", tabla), BorderLayout.CENTER);
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
        String dpi = txtDpi.getText().trim();
        String nombre = txtNombre.getText().trim();
        String direccion = txtDireccion.getText().trim();
        String fecha = txtFecha.getText().trim();
        String tipo = (String) cmbTipo.getSelectedItem();

        if (dpi.isEmpty() || nombre.isEmpty() || direccion.isEmpty() || fecha.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Llene todos los campos.");
        } else if (!tienda.dpiValido(dpi)) {
            JOptionPane.showMessageDialog(this, "El DPI debe tener exactamente 13 numeros.");
        } else if (tienda.buscarCliente(dpi) != null) {
            JOptionPane.showMessageDialog(this, "Ya existe un cliente con ese DPI.");
        } else if (fecha.length() != 10) {
            JOptionPane.showMessageDialog(this, "Escriba la fecha asi: dd/mm/aaaa (ejemplo 25/03/2001)");
        } else {
            Cliente cliente = new Cliente(dpi, nombre, direccion, fecha, tipo);
            tienda.agregarCliente(cliente);
            JOptionPane.showMessageDialog(this, "Cliente registrado.");
            limpiar();
            actualizar();
        }
    }

    private void limpiar() {
        txtDpi.setText("");
        txtNombre.setText("");
        txtDireccion.setText("");
        txtFecha.setText("");
        cmbTipo.setSelectedIndex(0);
    }

    public void actualizar() {
        modelo.setRowCount(0);
        for (int i = 0; i < tienda.getClientes().size(); i++) {
            Cliente c = tienda.getClientes().get(i);
            modelo.addRow(new Object[]{c.getDpi(), c.getNombre(), c.getDireccion(),
                c.getFechaNacimiento(), c.getTipo()});
        }
    }
}
