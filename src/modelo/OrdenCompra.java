package modelo;

import java.util.ArrayList;

public class OrdenCompra {

    private String idOrden;
    private Cliente cliente;
    private ArrayList<DetalleProducto> productos;
    private String fecha;
    private double total;
    private String estado;

    public OrdenCompra(String idOrden, Cliente cliente, String fecha) {
        this.idOrden = idOrden;
        this.cliente = cliente;
        this.fecha = fecha;
        this.productos = new ArrayList<DetalleProducto>();
        this.total = 0;
        this.estado = "Pendiente";
    }

    public void agregarProducto(Producto producto, int cantidad) {
        for (int i = 0; i < productos.size(); i++) {
            DetalleProducto detalle = productos.get(i);
            if (detalle.getProducto().getCodigo().equals(producto.getCodigo())) {
                detalle.setCantidad(detalle.getCantidad() + cantidad);
                calcularTotal();
                return;
            }
        }
        productos.add(new DetalleProducto(producto, cantidad));
        calcularTotal();
    }

    public void calcularTotal() {
        total = 0;
        for (int i = 0; i < productos.size(); i++) {
            total = total + productos.get(i).getSubtotal();
        }
    }

    public String getIdOrden() {
        return idOrden;
    }

    public void setIdOrden(String idOrden) {
        this.idOrden = idOrden;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public ArrayList<DetalleProducto> getProductos() {
        return productos;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public double getTotal() {
        return total;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
