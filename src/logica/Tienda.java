package logica;

import java.time.LocalDate;
import java.util.ArrayList;
import modelo.Bodega;
import modelo.Cliente;
import modelo.DetalleProducto;
import modelo.Empleado;
import modelo.Inventario;
import modelo.OrdenCompra;
import modelo.Producto;
import persistencia.ArchivoDatos;

public class Tienda {

    private ArrayList<Producto> productos;
    private ArrayList<Cliente> clientes;
    private ArrayList<Empleado> empleados;
    private ArrayList<Bodega> bodegas;
    private ArrayList<Inventario> inventarios;
    private ArrayList<OrdenCompra> ordenes;
    private ArchivoDatos archivo;

    public Tienda() {
        productos = new ArrayList<Producto>();
        clientes = new ArrayList<Cliente>();
        empleados = new ArrayList<Empleado>();
        bodegas = new ArrayList<Bodega>();
        inventarios = new ArrayList<Inventario>();
        ordenes = new ArrayList<OrdenCompra>();
        archivo = new ArchivoDatos();
    }

    public void cargarDatos() {
        archivo.cargarTodo(this);
    }

    public void guardarDatos() {
        archivo.guardarTodo(this);
    }

    public void agregarProducto(Producto producto) {
        productos.add(producto);
        guardarDatos();
    }

    public void agregarCliente(Cliente cliente) {
        clientes.add(cliente);
        guardarDatos();
    }

    public void agregarEmpleado(Empleado empleado) {
        empleados.add(empleado);
        guardarDatos();
    }

    public void agregarBodega(Bodega bodega) {
        bodegas.add(bodega);
        guardarDatos();
    }

    public void agregarAInventario(Bodega bodega, Producto producto, int cantidad) {
        Inventario inventario = buscarInventario(bodega.getIdBodega());
        if (inventario == null) {
            inventario = new Inventario("INV-" + bodega.getIdBodega(), bodega);
            inventarios.add(inventario);
        }
        inventario.agregarStock(producto, cantidad);
        guardarDatos();
    }

    public void agregarOrden(OrdenCompra orden) {
        ordenes.add(orden);
        guardarDatos();
    }

    public Producto buscarProducto(String codigo) {
        for (int i = 0; i < productos.size(); i++) {
            if (productos.get(i).getCodigo().equals(codigo)) {
                return productos.get(i);
            }
        }
        return null;
    }

    public Cliente buscarCliente(String dpi) {
        for (int i = 0; i < clientes.size(); i++) {
            if (clientes.get(i).getDpi().equals(dpi)) {
                return clientes.get(i);
            }
        }
        return null;
    }

    public Empleado buscarEmpleado(String idEmpleado) {
        for (int i = 0; i < empleados.size(); i++) {
            if (empleados.get(i).getIdEmpleado().equals(idEmpleado)) {
                return empleados.get(i);
            }
        }
        return null;
    }

    public Bodega buscarBodega(String idBodega) {
        for (int i = 0; i < bodegas.size(); i++) {
            if (bodegas.get(i).getIdBodega().equals(idBodega)) {
                return bodegas.get(i);
            }
        }
        return null;
    }

    public Inventario buscarInventario(String idBodega) {
        for (int i = 0; i < inventarios.size(); i++) {
            if (inventarios.get(i).getBodega().getIdBodega().equals(idBodega)) {
                return inventarios.get(i);
            }
        }
        return null;
    }

    public int espacioDisponible(Bodega bodega) {
        Inventario inventario = buscarInventario(bodega.getIdBodega());
        if (inventario == null) {
            return bodega.getCapacidad();
        }
        return bodega.getCapacidad() - inventario.getTotalUnidades();
    }

    public int stockTotal(String codigo) {
        int total = 0;
        for (int i = 0; i < inventarios.size(); i++) {
            total = total + inventarios.get(i).getStock(codigo);
        }
        return total;
    }

    public String generarIdOrden() {
        return "OC-" + (ordenes.size() + 1);
    }

    public String fechaDeHoy() {
        return LocalDate.now().toString();
    }

    public String productoSinStock(OrdenCompra orden) {
        for (int i = 0; i < orden.getProductos().size(); i++) {
            DetalleProducto detalle = orden.getProductos().get(i);
            if (stockTotal(detalle.getProducto().getCodigo()) < detalle.getCantidad()) {
                return detalle.getProducto().getNombre();
            }
        }
        return null;
    }

    public void completarOrden(OrdenCompra orden) {
        for (int i = 0; i < orden.getProductos().size(); i++) {
            DetalleProducto detalle = orden.getProductos().get(i);
            int pendiente = detalle.getCantidad();

            for (int j = 0; j < inventarios.size(); j++) {
                if (pendiente > 0) {
                    int retirado = inventarios.get(j).retirarStock(detalle.getProducto().getCodigo(), pendiente);
                    pendiente = pendiente - retirado;
                }
            }
        }
        orden.setEstado("Completada");
        guardarDatos();
    }

    public void cancelarOrden(OrdenCompra orden) {
        orden.setEstado("Cancelada");
        guardarDatos();
    }

    public boolean dpiValido(String dpi) {
        if (dpi.length() != 13) {
            return false;
        }
        for (int i = 0; i < dpi.length(); i++) {
            if (!Character.isDigit(dpi.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public ArrayList<Producto> getProductos() {
        return productos;
    }

    public ArrayList<Cliente> getClientes() {
        return clientes;
    }

    public ArrayList<Empleado> getEmpleados() {
        return empleados;
    }

    public ArrayList<Bodega> getBodegas() {
        return bodegas;
    }

    public ArrayList<Inventario> getInventarios() {
        return inventarios;
    }

    public ArrayList<OrdenCompra> getOrdenes() {
        return ordenes;
    }
}
