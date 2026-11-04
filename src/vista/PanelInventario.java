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
import modelo.DetalleProducto;
import modelo.Inventario;
import modelo.Producto;

public class PanelInventario extends JPanel implements ActionListener {

    private Tienda tienda;

    private JComboBox<String> cmbBodega;
    private JComboBox<String> cmbProducto;
    private JTextField txtCantidad;
    private JButton btnAgregar;
    private DefaultTableModel modelo;
    private JTable tabla;

    public PanelInventario(Tienda tienda) {
        this.tienda = tienda;
        Estilo.prepararPantalla(this);

        cmbBodega = Estilo.lista();
        cmbProducto = Estilo.lista();
        txtCantidad = Estilo.campo();
        btnAgregar = Estilo.boton("Agregar al inventario", Estilo.VERDE);
        btnAgregar.addActionListener(this);

        JPanel formulario = Estilo.formulario();
        formulario.add(Estilo.etiqueta("Bodega"));
        formulario.add(cmbBodega);
        formulario.add(Estilo.etiqueta("Producto"));
        formulario.add(cmbProducto);
        formulario.add(Estilo.etiqueta("Cantidad a ingresar"));
        formulario.add(txtCantidad);
        formulario.add(new JLabel(""));
        formulario.add(btnAgregar);

        modelo = new DefaultTableModel();
        modelo.addColumn("Inventario");
        modelo.addColumn("Bodega");
        modelo.addColumn("Codigo");
        modelo.addColumn("Producto");
        modelo.addColumn("Cantidad (stock)");
        tabla = Estilo.tabla(modelo);

        add(Estilo.encabezado("Inventario", "Cantidad de cada producto guardada en cada bodega"), BorderLayout.NORTH);
        add(Estilo.tarjetaFormulario("Ingresar mercaderia", formulario), BorderLayout.WEST);
        add(Estilo.tarjetaTabla("Inventario actual", tabla), BorderLayout.CENTER);
        actualizar();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnAgregar) {
            agregar();
        }
    }

    private void agregar() {
        int posicionBodega = cmbBodega.getSelectedIndex();
        int posicionProducto = cmbProducto.getSelectedIndex();

        if (posicionBodega == -1 || posicionProducto == -1) {
            JOptionPane.showMessageDialog(this, "Primero registre bodegas y productos.");
            return;
        }

        int cantidad;
        try {
            cantidad = Integer.parseInt(txtCantidad.getText().trim());
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "La cantidad debe ser un numero entero.");
            return;
        }
        if (cantidad <= 0) {
            JOptionPane.showMessageDialog(this, "La cantidad debe ser mayor que cero.");
            return;
        }

        Bodega bodega = tienda.getBodegas().get(posicionBodega);
        Producto producto = tienda.getProductos().get(posicionProducto);

        if (cantidad > tienda.espacioDisponible(bodega)) {
            JOptionPane.showMessageDialog(this, "No hay espacio suficiente en la bodega. Espacio disponible: "
                    + tienda.espacioDisponible(bodega));
            return;
        }

        tienda.agregarAInventario(bodega, producto, cantidad);
        JOptionPane.showMessageDialog(this, "Productos agregados al inventario.");
        txtCantidad.setText("");
        actualizar();
    }

    public void actualizar() {
        cmbBodega.removeAllItems();
        for (int i = 0; i < tienda.getBodegas().size(); i++) {
            Bodega b = tienda.getBodegas().get(i);
            cmbBodega.addItem(b.getIdBodega() + " - " + b.getDireccion());
        }

        cmbProducto.removeAllItems();
        for (int i = 0; i < tienda.getProductos().size(); i++) {
            Producto p = tienda.getProductos().get(i);
            cmbProducto.addItem(p.getCodigo() + " - " + p.getNombre());
        }

        modelo.setRowCount(0);
        for (int i = 0; i < tienda.getInventarios().size(); i++) {
            Inventario inv = tienda.getInventarios().get(i);
            for (int j = 0; j < inv.getProductos().size(); j++) {
                DetalleProducto d = inv.getProductos().get(j);
                modelo.addRow(new Object[]{inv.getIdInventario(), inv.getBodega().getIdBodega(),
                    d.getProducto().getCodigo(), d.getProducto().getNombre(), d.getCantidad()});
            }
        }
    }
}
