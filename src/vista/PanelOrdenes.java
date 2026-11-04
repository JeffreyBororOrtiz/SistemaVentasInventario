package vista;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
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
import modelo.DetalleProducto;
import modelo.OrdenCompra;
import modelo.Producto;

public class PanelOrdenes extends JPanel implements ActionListener {

    private Tienda tienda;

    private ArrayList<DetalleProducto> carrito;

    private JComboBox<String> cmbCliente;
    private JComboBox<String> cmbProducto;
    private JTextField txtCantidad;
    private JButton btnAgregarProducto;
    private JButton btnRegistrarOrden;
    private JButton btnLimpiar;
    private JLabel lblTotal;
    private DefaultTableModel modeloCarrito;
    private JTable tablaCarrito;

    private DefaultTableModel modeloOrdenes;
    private JTable tablaOrdenes;
    private JButton btnCompletar;
    private JButton btnCancelar;
    private JButton btnVerDetalle;

    public PanelOrdenes(Tienda tienda) {
        this.tienda = tienda;
        this.carrito = new ArrayList<DetalleProducto>();
        Estilo.prepararPantalla(this);

        cmbCliente = Estilo.lista();
        cmbProducto = Estilo.lista();
        txtCantidad = Estilo.campo();
        btnAgregarProducto = Estilo.boton("+ Agregar producto", Estilo.AZUL);
        btnRegistrarOrden = Estilo.boton("Registrar orden", Estilo.VERDE);
        btnLimpiar = Estilo.boton("Limpiar", Estilo.GRIS);
        lblTotal = new JLabel("Total: Q 0.00");
        lblTotal.setFont(new Font("SansSerif", Font.BOLD, 20));
        lblTotal.setForeground(Estilo.VERDE);
        btnAgregarProducto.addActionListener(this);
        btnRegistrarOrden.addActionListener(this);
        btnLimpiar.addActionListener(this);

        JPanel formulario = Estilo.formulario();
        formulario.add(Estilo.etiqueta("Cliente"));
        formulario.add(cmbCliente);
        formulario.add(Estilo.etiqueta("Producto"));
        formulario.add(cmbProducto);
        formulario.add(Estilo.etiqueta("Cantidad"));
        formulario.add(txtCantidad);
        formulario.add(btnAgregarProducto);
        formulario.add(new JLabel(""));
        formulario.add(lblTotal);
        formulario.add(btnRegistrarOrden);
        formulario.add(btnLimpiar);

        modeloCarrito = new DefaultTableModel();
        modeloCarrito.addColumn("Codigo");
        modeloCarrito.addColumn("Producto");
        modeloCarrito.addColumn("Cantidad");
        modeloCarrito.addColumn("Precio");
        modeloCarrito.addColumn("Subtotal");
        tablaCarrito = Estilo.tabla(modeloCarrito);

        modeloOrdenes = new DefaultTableModel();
        modeloOrdenes.addColumn("No. orden");
        modeloOrdenes.addColumn("Fecha");
        modeloOrdenes.addColumn("Cliente");
        modeloOrdenes.addColumn("Total");
        modeloOrdenes.addColumn("Estado");
        tablaOrdenes = Estilo.tabla(modeloOrdenes);

        btnCompletar = Estilo.boton("Completar venta", Estilo.VERDE);
        btnCancelar = Estilo.boton("Cancelar orden", Estilo.ROJO);
        btnVerDetalle = Estilo.boton("Ver detalle", Estilo.AZUL);
        btnCompletar.addActionListener(this);
        btnCancelar.addActionListener(this);
        btnVerDetalle.addActionListener(this);
        JPanel botones = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 10));
        botones.setBackground(Estilo.BLANCO);
        botones.add(btnCompletar);
        botones.add(new JLabel("   "));
        botones.add(btnCancelar);
        botones.add(new JLabel("   "));
        botones.add(btnVerDetalle);

        JPanel tarjetaOrdenes = Estilo.tarjetaTabla("Ordenes registradas (seleccione una)", tablaOrdenes);
        tarjetaOrdenes.add(botones, BorderLayout.SOUTH);

        JPanel derecha = new JPanel(new GridLayout(2, 1, 0, 18));
        derecha.setBackground(Estilo.FONDO);
        derecha.add(Estilo.tarjetaTabla("Productos de la orden nueva", tablaCarrito));
        derecha.add(tarjetaOrdenes);

        add(Estilo.encabezado("Ordenes de compra", "Arme la venta, registrela y luego completela para descontar el stock"), BorderLayout.NORTH);
        add(Estilo.tarjetaFormulario("Nueva orden", formulario), BorderLayout.WEST);
        add(derecha, BorderLayout.CENTER);
        actualizar();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnAgregarProducto) {
            agregarProducto();
        } else if (e.getSource() == btnRegistrarOrden) {
            registrarOrden();
        } else if (e.getSource() == btnLimpiar) {
            limpiar();
        } else if (e.getSource() == btnCompletar) {
            completarOrden();
        } else if (e.getSource() == btnCancelar) {
            cancelarOrden();
        } else if (e.getSource() == btnVerDetalle) {
            verDetalle();
        }
    }

    private void agregarProducto() {
        int posicionProducto = cmbProducto.getSelectedIndex();
        if (posicionProducto == -1) {
            JOptionPane.showMessageDialog(this, "Primero registre productos.");
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

        Producto producto = tienda.getProductos().get(posicionProducto);
        carrito.add(new DetalleProducto(producto, cantidad));
        txtCantidad.setText("");
        mostrarCarrito();
    }

    private void registrarOrden() {
        int posicionCliente = cmbCliente.getSelectedIndex();
        if (posicionCliente == -1) {
            JOptionPane.showMessageDialog(this, "Primero registre clientes.");
            return;
        }
        if (carrito.size() == 0) {
            JOptionPane.showMessageDialog(this, "La orden debe tener al menos un producto.");
            return;
        }

        Cliente cliente = tienda.getClientes().get(posicionCliente);
        OrdenCompra orden = new OrdenCompra(tienda.generarIdOrden(), cliente, tienda.fechaDeHoy());
        for (int i = 0; i < carrito.size(); i++) {
            orden.agregarProducto(carrito.get(i).getProducto(), carrito.get(i).getCantidad());
        }
        tienda.agregarOrden(orden);

        JOptionPane.showMessageDialog(this, "Orden " + orden.getIdOrden() + " registrada como Pendiente.\n"
                + "Total: " + Estilo.dinero(orden.getTotal()));
        limpiar();
        actualizar();
    }

    private void limpiar() {
        carrito.clear();
        txtCantidad.setText("");
        mostrarCarrito();
    }

    private void mostrarCarrito() {
        double total = 0;
        modeloCarrito.setRowCount(0);
        for (int i = 0; i < carrito.size(); i++) {
            DetalleProducto d = carrito.get(i);
            modeloCarrito.addRow(new Object[]{d.getProducto().getCodigo(), d.getProducto().getNombre(),
                d.getCantidad(), Estilo.dinero(d.getProducto().getPrecio()), Estilo.dinero(d.getSubtotal())});
            total = total + d.getSubtotal();
        }
        lblTotal.setText("Total: " + Estilo.dinero(total));
    }

    private OrdenCompra ordenSeleccionada() {
        int fila = tablaOrdenes.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione una orden en la tabla.");
            return null;
        }
        return tienda.getOrdenes().get(fila);
    }

    private void completarOrden() {
        OrdenCompra orden = ordenSeleccionada();
        if (orden == null) {
            return;
        }
        if (!orden.getEstado().equals("Pendiente")) {
            JOptionPane.showMessageDialog(this, "Solo se pueden completar ordenes Pendientes.");
            return;
        }

        String productoFaltante = tienda.productoSinStock(orden);
        if (productoFaltante != null) {
            JOptionPane.showMessageDialog(this, "No hay stock suficiente de: " + productoFaltante);
            return;
        }

        tienda.completarOrden(orden);
        JOptionPane.showMessageDialog(this, "Venta completada. Se desconto el stock del inventario.");
        actualizar();
    }

    private void cancelarOrden() {
        OrdenCompra orden = ordenSeleccionada();
        if (orden == null) {
            return;
        }
        if (!orden.getEstado().equals("Pendiente")) {
            JOptionPane.showMessageDialog(this, "Solo se pueden cancelar ordenes Pendientes.");
            return;
        }
        tienda.cancelarOrden(orden);
        JOptionPane.showMessageDialog(this, "Orden cancelada.");
        actualizar();
    }

    private void verDetalle() {
        OrdenCompra orden = ordenSeleccionada();
        if (orden == null) {
            return;
        }
        String texto = "Orden: " + orden.getIdOrden() + "\n"
                + "Fecha: " + orden.getFecha() + "\n"
                + "Cliente: " + orden.getCliente().getNombre() + "\n"
                + "Estado: " + orden.getEstado() + "\n\n";
        for (int i = 0; i < orden.getProductos().size(); i++) {
            DetalleProducto d = orden.getProductos().get(i);
            texto = texto + d.getCantidad() + " x " + d.getProducto().getNombre()
                    + " = " + Estilo.dinero(d.getSubtotal()) + "\n";
        }
        texto = texto + "\nTotal: " + Estilo.dinero(orden.getTotal());
        JOptionPane.showMessageDialog(this, texto);
    }

    public void actualizar() {
        cmbCliente.removeAllItems();
        for (int i = 0; i < tienda.getClientes().size(); i++) {
            Cliente c = tienda.getClientes().get(i);
            cmbCliente.addItem(c.getNombre() + " (" + c.getDpi() + ")");
        }

        cmbProducto.removeAllItems();
        for (int i = 0; i < tienda.getProductos().size(); i++) {
            Producto p = tienda.getProductos().get(i);
            cmbProducto.addItem(p.getCodigo() + " - " + p.getNombre() + " (stock: "
                    + tienda.stockTotal(p.getCodigo()) + ")");
        }

        modeloOrdenes.setRowCount(0);
        for (int i = 0; i < tienda.getOrdenes().size(); i++) {
            OrdenCompra o = tienda.getOrdenes().get(i);
            modeloOrdenes.addRow(new Object[]{o.getIdOrden(), o.getFecha(), o.getCliente().getNombre(),
                Estilo.dinero(o.getTotal()), o.getEstado()});
        }
        mostrarCarrito();
    }
}
