package modelo;

import java.util.ArrayList;

public class Inventario {

    private String idInventario;
    private Bodega bodega;
    private ArrayList<DetalleProducto> productos;

    public Inventario(String idInventario, Bodega bodega) {
        this.idInventario = idInventario;
        this.bodega = bodega;
        this.productos = new ArrayList<DetalleProducto>();
    }

    public void agregarStock(Producto producto, int cantidad) {
        for (int i = 0; i < productos.size(); i++) {
            DetalleProducto detalle = productos.get(i);
            if (detalle.getProducto().getCodigo().equals(producto.getCodigo())) {
                detalle.setCantidad(detalle.getCantidad() + cantidad);
                return;
            }
        }
        productos.add(new DetalleProducto(producto, cantidad));
    }

    public int retirarStock(String codigo, int cantidad) {
        for (int i = 0; i < productos.size(); i++) {
            DetalleProducto detalle = productos.get(i);
            if (detalle.getProducto().getCodigo().equals(codigo)) {
                int retirado;
                if (detalle.getCantidad() >= cantidad) {
                    retirado = cantidad;
                } else {
                    retirado = detalle.getCantidad();
                }
                detalle.setCantidad(detalle.getCantidad() - retirado);
                return retirado;
            }
        }
        return 0;
    }

    public int getStock(String codigo) {
        for (int i = 0; i < productos.size(); i++) {
            if (productos.get(i).getProducto().getCodigo().equals(codigo)) {
                return productos.get(i).getCantidad();
            }
        }
        return 0;
    }

    public int getTotalUnidades() {
        int total = 0;
        for (int i = 0; i < productos.size(); i++) {
            total = total + productos.get(i).getCantidad();
        }
        return total;
    }

    public String getIdInventario() {
        return idInventario;
    }

    public void setIdInventario(String idInventario) {
        this.idInventario = idInventario;
    }

    public Bodega getBodega() {
        return bodega;
    }

    public void setBodega(Bodega bodega) {
        this.bodega = bodega;
    }

    public ArrayList<DetalleProducto> getProductos() {
        return productos;
    }
}
