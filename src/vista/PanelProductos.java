package vista;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import logica.Tienda;
import modelo.Producto;

public class PanelProductos extends JPanel implements ActionListener {

    private Tienda tienda;

    private JTextField txtCodigo;
    private JTextField txtNombre;
    private JTextField txtDescripcion;
    private JTextField txtMarca;
    private JTextField txtPrecio;
    private JButton btnRegistrar;
    private JButton btnLimpiar;
    private DefaultTableModel modelo;
    private JTable tabla;

    public PanelProductos(Tienda tienda) {
        this.tienda = tienda;
        Estilo.prepararPantalla(this);

        txtCodigo = Estilo.campo();
        txtNombre = Estilo.campo();
        txtDescripcion = Estilo.campo();
        txtMarca = Estilo.campo();
        txtPrecio = Estilo.campo();
        btnRegistrar = Estilo.boton("Registrar producto", Estilo.VERDE);
        btnLimpiar = Estilo.boton("Limpiar", Estilo.GRIS);
        btnRegistrar.addActionListener(this);
        btnLimpiar.addActionListener(this);

        JPanel formulario = Estilo.formulario();
        formulario.add(Estilo.etiqueta("Codigo"));
        formulario.add(txtCodigo);
        formulario.add(Estilo.etiqueta("Nombre"));
        formulario.add(txtNombre);
        formulario.add(Estilo.etiqueta("Descripcion"));
        formulario.add(txtDescripcion);
        formulario.add(Estilo.etiqueta("Marca"));
        formulario.add(txtMarca);
        formulario.add(Estilo.etiqueta("Precio (Q)"));
        formulario.add(txtPrecio);
        formulario.add(new JLabel(""));
        formulario.add(btnRegistrar);
        formulario.add(btnLimpiar);

        modelo = new DefaultTableModel();
        modelo.addColumn("Codigo");
        modelo.addColumn("Nombre");
        modelo.addColumn("Descripcion");
        modelo.addColumn("Marca");
        modelo.addColumn("Precio");
        modelo.addColumn("Stock total");
        tabla = Estilo.tabla(modelo);

        add(Estilo.encabezado("Productos", "Registre los productos que vende la tienda"), BorderLayout.NORTH);
        add(Estilo.tarjetaFormulario("Nuevo producto", formulario), BorderLayout.WEST);
        add(Estilo.tarjetaTabla("Productos registrados", tabla), BorderLayout.CENTER);
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
        String codigo = txtCodigo.getText().trim();
        String nombre = txtNombre.getText().trim();
        String descripcion = txtDescripcion.getText().trim();
        String marca = txtMarca.getText().trim();
        String textoPrecio = txtPrecio.getText().trim();

        if (codigo.isEmpty() || nombre.isEmpty() || marca.isEmpty() || textoPrecio.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Llene codigo, nombre, marca y precio.");
            return;
        }
        if (tienda.buscarProducto(codigo) != null) {
            JOptionPane.showMessageDialog(this, "Ya existe un producto con el codigo " + codigo);
            return;
        }

        double precio;
        try {
            precio = Double.parseDouble(textoPrecio);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "El precio debe ser un numero, por ejemplo 125.50");
            return;
        }
        if (precio <= 0) {
            JOptionPane.showMessageDialog(this, "El precio debe ser mayor que cero.");
            return;
        }

        Producto producto = new Producto(codigo, nombre, descripcion, marca, precio);
        tienda.agregarProducto(producto);
        JOptionPane.showMessageDialog(this, "Producto registrado.");
        limpiar();
        actualizar();
    }

    private void limpiar() {
        txtCodigo.setText("");
        txtNombre.setText("");
        txtDescripcion.setText("");
        txtMarca.setText("");
        txtPrecio.setText("");
    }

    public void actualizar() {
        modelo.setRowCount(0);
        for (int i = 0; i < tienda.getProductos().size(); i++) {
            Producto p = tienda.getProductos().get(i);
            modelo.addRow(new Object[]{p.getCodigo(), p.getNombre(), p.getDescripcion(), p.getMarca(),
                Estilo.dinero(p.getPrecio()), tienda.stockTotal(p.getCodigo())});
        }
    }
}
