package vista;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class Estilo {

    public static final Color MENU = new Color(28, 37, 54);
    public static final Color MENU_ACTIVO = new Color(0, 150, 136);
    public static final Color FONDO = new Color(236, 240, 243);
    public static final Color BLANCO = Color.WHITE;
    public static final Color TEXTO = new Color(44, 62, 80);
    public static final Color TEXTO_SUAVE = new Color(120, 133, 145);
    public static final Color VERDE = new Color(0, 150, 136);
    public static final Color AZUL = new Color(41, 128, 185);
    public static final Color ROJO = new Color(211, 84, 0);
    public static final Color GRIS = new Color(149, 165, 166);
    public static final Color BORDE = new Color(214, 220, 226);

    public static final Font TITULO = new Font("SansSerif", Font.BOLD, 24);
    public static final Font SUBTITULO = new Font("SansSerif", Font.PLAIN, 13);
    public static final Font NORMAL = new Font("SansSerif", Font.PLAIN, 13);
    public static final Font NEGRITA = new Font("SansSerif", Font.BOLD, 13);
    public static final Font NUMERO_GRANDE = new Font("SansSerif", Font.BOLD, 34);

    public static JButton boton(String texto, Color color) {
        JButton boton = new JButton(texto);
        boton.setBackground(color);
        boton.setForeground(BLANCO);
        boton.setFont(NEGRITA);
        boton.setFocusPainted(false);
        boton.setBorderPainted(false);
        boton.setOpaque(true);
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        boton.setPreferredSize(new Dimension(150, 34));
        return boton;
    }

    public static JButton botonMenu(String texto) {
        JButton boton = new JButton(texto);
        boton.setBackground(MENU);
        boton.setForeground(BLANCO);
        boton.setFont(new Font("SansSerif", Font.PLAIN, 15));
        boton.setHorizontalAlignment(JButton.LEFT);
        boton.setFocusPainted(false);
        boton.setBorderPainted(false);
        boton.setOpaque(true);
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return boton;
    }

    public static JLabel etiqueta(String texto) {
        JLabel etiqueta = new JLabel(texto);
        etiqueta.setFont(NEGRITA);
        etiqueta.setForeground(TEXTO);
        return etiqueta;
    }

    public static JTextField campo() {
        JTextField campo = new JTextField();
        campo.setFont(NORMAL);
        campo.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDE),
                BorderFactory.createEmptyBorder(5, 8, 5, 8)));
        return campo;
    }

    public static JComboBox<String> lista() {
        JComboBox<String> lista = new JComboBox<String>();
        lista.setFont(NORMAL);
        lista.setBackground(BLANCO);
        return lista;
    }

    public static JPanel encabezado(String titulo, String subtitulo) {
        JPanel panel = new JPanel(new GridLayout(2, 1));
        panel.setBackground(FONDO);
        JLabel lblTitulo = new JLabel(titulo);
        lblTitulo.setFont(TITULO);
        lblTitulo.setForeground(TEXTO);
        JLabel lblSubtitulo = new JLabel(subtitulo);
        lblSubtitulo.setFont(SUBTITULO);
        lblSubtitulo.setForeground(TEXTO_SUAVE);
        panel.add(lblTitulo);
        panel.add(lblSubtitulo);
        return panel;
    }

    public static JPanel formulario() {
        JPanel panel = new JPanel(new GridLayout(0, 1, 0, 6));
        panel.setBackground(BLANCO);
        return panel;
    }

    public static JPanel tarjetaFormulario(String titulo, JPanel formulario) {
        JLabel lblTitulo = new JLabel(titulo);
        lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 16));
        lblTitulo.setForeground(TEXTO);
        lblTitulo.setBorder(BorderFactory.createEmptyBorder(0, 0, 12, 0));

        JPanel contenido = new JPanel(new BorderLayout());
        contenido.setBackground(BLANCO);
        contenido.add(lblTitulo, BorderLayout.NORTH);
        contenido.add(formulario, BorderLayout.CENTER);

        JPanel tarjeta = new JPanel(new BorderLayout());
        tarjeta.setBackground(BLANCO);
        tarjeta.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDE),
                BorderFactory.createEmptyBorder(18, 18, 18, 18)));
        tarjeta.setPreferredSize(new Dimension(310, 0));
        tarjeta.add(contenido, BorderLayout.NORTH);
        return tarjeta;
    }

    public static JTable tabla(DefaultTableModel modelo) {
        JTable tabla = new JTable(modelo);
        tabla.setDefaultEditor(Object.class, null);
        tabla.setFont(NORMAL);
        tabla.setRowHeight(28);
        tabla.setShowVerticalLines(false);
        tabla.setGridColor(BORDE);
        tabla.setSelectionBackground(new Color(178, 223, 219));
        tabla.setSelectionForeground(TEXTO);
        tabla.getTableHeader().setFont(NEGRITA);
        tabla.getTableHeader().setBackground(MENU);
        tabla.getTableHeader().setForeground(BLANCO);
        tabla.getTableHeader().setPreferredSize(new Dimension(0, 32));
        tabla.getTableHeader().setReorderingAllowed(false);
        return tabla;
    }

    public static JPanel tarjetaTabla(String titulo, JTable tabla) {
        JLabel lblTitulo = new JLabel(titulo);
        lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 16));
        lblTitulo.setForeground(TEXTO);
        lblTitulo.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));

        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setBorder(BorderFactory.createLineBorder(BORDE));
        scroll.getViewport().setBackground(BLANCO);

        JPanel tarjeta = new JPanel(new BorderLayout());
        tarjeta.setBackground(BLANCO);
        tarjeta.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDE),
                BorderFactory.createEmptyBorder(18, 18, 18, 18)));
        tarjeta.add(lblTitulo, BorderLayout.NORTH);
        tarjeta.add(scroll, BorderLayout.CENTER);
        return tarjeta;
    }

    public static void prepararPantalla(JPanel pantalla) {
        pantalla.setLayout(new BorderLayout(18, 18));
        pantalla.setBackground(FONDO);
        pantalla.setBorder(BorderFactory.createEmptyBorder(22, 24, 22, 24));
    }

    public static String dinero(double cantidad) {
        return "Q " + String.format("%.2f", cantidad);
    }
}
