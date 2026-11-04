package vista;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import logica.Tienda;
import modelo.Empleado;

public class PanelEmpleados extends JPanel implements ActionListener {

    private Tienda tienda;

    private JTextField txtId;
    private JTextField txtNombre;
    private JTextField txtDpi;
    private JTextField txtDireccion;
    private JTextField txtPuesto;
    private JTextField txtSalario;
    private JButton btnRegistrar;
    private JButton btnLimpiar;
    private DefaultTableModel modelo;
    private JTable tabla;

    public PanelEmpleados(Tienda tienda) {
        this.tienda = tienda;
        Estilo.prepararPantalla(this);

        txtId = Estilo.campo();
        txtNombre = Estilo.campo();
        txtDpi = Estilo.campo();
        txtDireccion = Estilo.campo();
        txtPuesto = Estilo.campo();
        txtSalario = Estilo.campo();
        btnRegistrar = Estilo.boton("Registrar empleado", Estilo.VERDE);
        btnLimpiar = Estilo.boton("Limpiar", Estilo.GRIS);
        btnRegistrar.addActionListener(this);
        btnLimpiar.addActionListener(this);

        JPanel formulario = Estilo.formulario();
        formulario.add(Estilo.etiqueta("ID empleado"));
        formulario.add(txtId);
        formulario.add(Estilo.etiqueta("Nombre"));
        formulario.add(txtNombre);
        formulario.add(Estilo.etiqueta("DPI (13 numeros)"));
        formulario.add(txtDpi);
        formulario.add(Estilo.etiqueta("Direccion"));
        formulario.add(txtDireccion);
        formulario.add(Estilo.etiqueta("Puesto"));
        formulario.add(txtPuesto);
        formulario.add(Estilo.etiqueta("Salario (Q)"));
        formulario.add(txtSalario);
        formulario.add(new JLabel(""));
        formulario.add(btnRegistrar);
        formulario.add(btnLimpiar);

        modelo = new DefaultTableModel();
        modelo.addColumn("ID");
        modelo.addColumn("Nombre");
        modelo.addColumn("DPI");
        modelo.addColumn("Direccion");
        modelo.addColumn("Puesto");
        modelo.addColumn("Salario");
        tabla = Estilo.tabla(modelo);

        add(Estilo.encabezado("Empleados", "Registre al personal de la tienda"), BorderLayout.NORTH);
        add(Estilo.tarjetaFormulario("Nuevo empleado", formulario), BorderLayout.WEST);
        add(Estilo.tarjetaTabla("Empleados registrados", tabla), BorderLayout.CENTER);
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
        String nombre = txtNombre.getText().trim();
        String dpi = txtDpi.getText().trim();
        String direccion = txtDireccion.getText().trim();
        String puesto = txtPuesto.getText().trim();
        String textoSalario = txtSalario.getText().trim();

        if (id.isEmpty() || nombre.isEmpty() || dpi.isEmpty() || direccion.isEmpty()
                || puesto.isEmpty() || textoSalario.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Llene todos los campos.");
            return;
        }
        if (tienda.buscarEmpleado(id) != null) {
            JOptionPane.showMessageDialog(this, "Ya existe un empleado con el ID " + id);
            return;
        }
        if (!tienda.dpiValido(dpi)) {
            JOptionPane.showMessageDialog(this, "El DPI debe tener exactamente 13 numeros.");
            return;
        }

        double salario;
        try {
            salario = Double.parseDouble(textoSalario);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "El salario debe ser un numero.");
            return;
        }
        if (salario <= 0) {
            JOptionPane.showMessageDialog(this, "El salario debe ser mayor que cero.");
            return;
        }

        Empleado empleado = new Empleado(id, nombre, dpi, direccion, puesto, salario);
        tienda.agregarEmpleado(empleado);
        JOptionPane.showMessageDialog(this, "Empleado registrado.");
        limpiar();
        actualizar();
    }

    private void limpiar() {
        txtId.setText("");
        txtNombre.setText("");
        txtDpi.setText("");
        txtDireccion.setText("");
        txtPuesto.setText("");
        txtSalario.setText("");
    }

    public void actualizar() {
        modelo.setRowCount(0);
        for (int i = 0; i < tienda.getEmpleados().size(); i++) {
            Empleado e = tienda.getEmpleados().get(i);
            modelo.addRow(new Object[]{e.getIdEmpleado(), e.getNombre(), e.getDpi(), e.getDireccion(),
                e.getPuesto(), Estilo.dinero(e.getSalario())});
        }
    }
}
