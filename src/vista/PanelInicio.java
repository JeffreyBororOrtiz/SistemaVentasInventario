package vista;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import logica.Tienda;

public class PanelInicio extends JPanel {

    private Tienda tienda;

    private JLabel lblProductos;
    private JLabel lblClientes;
    private JLabel lblBodegas;
    private JLabel lblPendientes;

    public PanelInicio(Tienda tienda) {
        this.tienda = tienda;
        Estilo.prepararPantalla(this);

        lblProductos = new JLabel("0");
        lblClientes = new JLabel("0");
        lblBodegas = new JLabel("0");
        lblPendientes = new JLabel("0");

        JPanel resumen = new JPanel(new GridLayout(1, 4, 18, 18));
        resumen.setBackground(Estilo.FONDO);
        resumen.add(crearTarjeta("Productos", lblProductos, Estilo.AZUL));
        resumen.add(crearTarjeta("Clientes", lblClientes, Estilo.VERDE));
        resumen.add(crearTarjeta("Bodegas", lblBodegas, new Color(142, 68, 173)));
        resumen.add(crearTarjeta("Ordenes pendientes", lblPendientes, Estilo.ROJO));

        JPanel pasos = Estilo.formulario();
        pasos.add(texto("1.  Registre sus productos y empleados."));
        pasos.add(texto("2.  Cree las bodegas y asigne un empleado encargado a cada una."));
        pasos.add(texto("3.  En Inventario, ingrese cuantas unidades hay de cada producto."));
        pasos.add(texto("4.  Registre a sus clientes."));
        pasos.add(texto("5.  En Ordenes, arme la venta y luego presione Completar venta."));
        pasos.add(texto("     Al completarla se descuenta el stock automaticamente."));

        JPanel centro = new JPanel(new BorderLayout(18, 18));
        centro.setBackground(Estilo.FONDO);
        centro.add(resumen, BorderLayout.NORTH);
        centro.add(Estilo.tarjetaFormulario("Como usar el sistema", pasos), BorderLayout.CENTER);

        add(Estilo.encabezado("Bienvenido", "Resumen general de la tienda"), BorderLayout.NORTH);
        add(centro, BorderLayout.CENTER);
        actualizar();
    }

    private JPanel crearTarjeta(String titulo, JLabel lblNumero, Color color) {
        lblNumero.setFont(Estilo.NUMERO_GRANDE);
        lblNumero.setForeground(color);

        JLabel lblTitulo = new JLabel(titulo);
        lblTitulo.setFont(Estilo.NEGRITA);
        lblTitulo.setForeground(Estilo.TEXTO_SUAVE);

        JPanel tarjeta = new JPanel(new BorderLayout());
        tarjeta.setBackground(Estilo.BLANCO);
        tarjeta.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 6, 0, 0, color),
                BorderFactory.createEmptyBorder(16, 18, 16, 18)));
        tarjeta.add(lblNumero, BorderLayout.CENTER);
        tarjeta.add(lblTitulo, BorderLayout.SOUTH);
        return tarjeta;
    }

    private JLabel texto(String texto) {
        JLabel etiqueta = new JLabel(texto);
        etiqueta.setFont(Estilo.NORMAL);
        etiqueta.setForeground(Estilo.TEXTO);
        return etiqueta;
    }

    public void actualizar() {
        int pendientes = 0;
        for (int i = 0; i < tienda.getOrdenes().size(); i++) {
            if (tienda.getOrdenes().get(i).getEstado().equals("Pendiente")) {
                pendientes = pendientes + 1;
            }
        }
        lblProductos.setText("" + tienda.getProductos().size());
        lblClientes.setText("" + tienda.getClientes().size());
        lblBodegas.setText("" + tienda.getBodegas().size());
        lblPendientes.setText("" + pendientes);
    }
}
