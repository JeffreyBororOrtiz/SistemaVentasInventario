import logica.Tienda;
import vista.VentanaPrincipal;

public class Main {

    public static void main(String[] args) {
        Tienda tienda = new Tienda();
        tienda.cargarDatos();

        VentanaPrincipal ventana = new VentanaPrincipal(tienda);
        ventana.setVisible(true);
    }
}
