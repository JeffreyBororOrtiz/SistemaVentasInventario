package vista;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import logica.Tienda;

public class VentanaPrincipal extends JFrame implements ActionListener {

    private CardLayout cartas;
    private JPanel contenido;

    private PanelInicio panelInicio;
    private PanelProductos panelProductos;
    private PanelClientes panelClientes;
    private PanelEmpleados panelEmpleados;
    private PanelBodegas panelBodegas;
    private PanelInventario panelInventario;
    private PanelOrdenes panelOrdenes;

    private JButton btnInicio;
    private JButton btnProductos;
    private JButton btnClientes;
    private JButton btnEmpleados;
    private JButton btnBodegas;
    private JButton btnInventario;
    private JButton btnOrdenes;
    private JButton[] botonesMenu;

    public VentanaPrincipal(Tienda tienda) {
        setTitle("Sistema de Ventas e Inventario");
        setSize(1150, 720);
        setMinimumSize(new Dimension(1000, 620));
        setLocationRelativeTo(null);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        panelInicio = new PanelInicio(tienda);
        panelProductos = new PanelProductos(tienda);
        panelClientes = new PanelClientes(tienda);
        panelEmpleados = new PanelEmpleados(tienda);
        panelBodegas = new PanelBodegas(tienda);
        panelInventario = new PanelInventario(tienda);
        panelOrdenes = new PanelOrdenes(tienda);

        cartas = new CardLayout();
        contenido = new JPanel(cartas);
        contenido.add(panelInicio, "inicio");
        contenido.add(panelProductos, "productos");
        contenido.add(panelClientes, "clientes");
        contenido.add(panelEmpleados, "empleados");
        contenido.add(panelBodegas, "bodegas");
        contenido.add(panelInventario, "inventario");
        contenido.add(panelOrdenes, "ordenes");

        btnInicio = Estilo.botonMenu("   Inicio");
        btnProductos = Estilo.botonMenu("   Productos");
        btnClientes = Estilo.botonMenu("   Clientes");
        btnEmpleados = Estilo.botonMenu("   Empleados");
        btnBodegas = Estilo.botonMenu("   Bodegas");
        btnInventario = Estilo.botonMenu("   Inventario");
        btnOrdenes = Estilo.botonMenu("   Ordenes de compra");
        botonesMenu = new JButton[]{btnInicio, btnProductos, btnClientes, btnEmpleados,
            btnBodegas, btnInventario, btnOrdenes};

        JPanel listaBotones = new JPanel(new GridLayout(0, 1, 0, 4));
        listaBotones.setBackground(Estilo.MENU);
        for (int i = 0; i < botonesMenu.length; i++) {
            botonesMenu[i].addActionListener(this);
            botonesMenu[i].setPreferredSize(new Dimension(220, 44));
            listaBotones.add(botonesMenu[i]);
        }

        JLabel lblLogo = new JLabel("<html>Sistema de<br>Ventas e Inventario</html>");
        lblLogo.setFont(new Font("SansSerif", Font.BOLD, 18));
        lblLogo.setForeground(Estilo.BLANCO);
        lblLogo.setBorder(BorderFactory.createEmptyBorder(26, 20, 26, 20));

        JLabel lblAutor = new JLabel("<html>Jeffrey Anderson<br>Boror Ortiz</html>");
        lblAutor.setFont(Estilo.SUBTITULO);
        lblAutor.setForeground(Estilo.GRIS);
        lblAutor.setBorder(BorderFactory.createEmptyBorder(16, 20, 20, 20));

        JPanel parteArriba = new JPanel(new BorderLayout());
        parteArriba.setBackground(Estilo.MENU);
        parteArriba.add(lblLogo, BorderLayout.NORTH);
        parteArriba.add(listaBotones, BorderLayout.CENTER);

        JPanel menu = new JPanel(new BorderLayout());
        menu.setBackground(Estilo.MENU);
        menu.setPreferredSize(new Dimension(230, 0));
        menu.add(parteArriba, BorderLayout.NORTH);
        menu.add(lblAutor, BorderLayout.SOUTH);

        setLayout(new BorderLayout());
        add(menu, BorderLayout.WEST);
        add(contenido, BorderLayout.CENTER);

        marcarBoton(btnInicio);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnInicio) {
            panelInicio.actualizar();
            cartas.show(contenido, "inicio");
        } else if (e.getSource() == btnProductos) {
            panelProductos.actualizar();
            cartas.show(contenido, "productos");
        } else if (e.getSource() == btnClientes) {
            panelClientes.actualizar();
            cartas.show(contenido, "clientes");
        } else if (e.getSource() == btnEmpleados) {
            panelEmpleados.actualizar();
            cartas.show(contenido, "empleados");
        } else if (e.getSource() == btnBodegas) {
            panelBodegas.actualizar();
            cartas.show(contenido, "bodegas");
        } else if (e.getSource() == btnInventario) {
            panelInventario.actualizar();
            cartas.show(contenido, "inventario");
        } else if (e.getSource() == btnOrdenes) {
            panelOrdenes.actualizar();
            cartas.show(contenido, "ordenes");
        }
        marcarBoton((JButton) e.getSource());
    }

    private void marcarBoton(JButton seleccionado) {
        for (int i = 0; i < botonesMenu.length; i++) {
            if (botonesMenu[i] == seleccionado) {
                botonesMenu[i].setBackground(Estilo.MENU_ACTIVO);
            } else {
                botonesMenu[i].setBackground(Estilo.MENU);
            }
        }
    }
}
