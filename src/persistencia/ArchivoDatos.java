package persistencia;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import logica.Tienda;
import modelo.Bodega;
import modelo.Cliente;
import modelo.DetalleProducto;
import modelo.Empleado;
import modelo.Inventario;
import modelo.OrdenCompra;
import modelo.Producto;

public class ArchivoDatos {

    private String carpeta = "datos";

    public void guardarTodo(Tienda tienda) {
        new File(carpeta).mkdirs();
        guardarProductos(tienda);
        guardarClientes(tienda);
        guardarEmpleados(tienda);
        guardarBodegas(tienda);
        guardarInventarios(tienda);
        guardarOrdenes(tienda);
    }

    private void guardarProductos(Tienda tienda) {
        try {
            PrintWriter escritor = new PrintWriter(new FileWriter(carpeta + "/productos.txt"));
            for (int i = 0; i < tienda.getProductos().size(); i++) {
                Producto p = tienda.getProductos().get(i);
                escritor.println(limpiar(p.getCodigo()) + ";" + limpiar(p.getNombre()) + ";"
                        + limpiar(p.getDescripcion()) + ";" + limpiar(p.getMarca()) + ";" + p.getPrecio());
            }
            escritor.close();
        } catch (IOException e) {
            System.out.println("Error al guardar productos: " + e.getMessage());
        }
    }

    private void guardarClientes(Tienda tienda) {
        try {
            PrintWriter escritor = new PrintWriter(new FileWriter(carpeta + "/clientes.txt"));
            for (int i = 0; i < tienda.getClientes().size(); i++) {
                Cliente c = tienda.getClientes().get(i);
                escritor.println(c.getDpi() + ";" + limpiar(c.getNombre()) + ";" + limpiar(c.getDireccion())
                        + ";" + limpiar(c.getFechaNacimiento()) + ";" + c.getTipo());
            }
            escritor.close();
        } catch (IOException e) {
            System.out.println("Error al guardar clientes: " + e.getMessage());
        }
    }

    private void guardarEmpleados(Tienda tienda) {
        try {
            PrintWriter escritor = new PrintWriter(new FileWriter(carpeta + "/empleados.txt"));
            for (int i = 0; i < tienda.getEmpleados().size(); i++) {
                Empleado e = tienda.getEmpleados().get(i);
                escritor.println(limpiar(e.getIdEmpleado()) + ";" + limpiar(e.getNombre()) + ";" + e.getDpi()
                        + ";" + limpiar(e.getDireccion()) + ";" + limpiar(e.getPuesto()) + ";" + e.getSalario());
            }
            escritor.close();
        } catch (IOException e) {
            System.out.println("Error al guardar empleados: " + e.getMessage());
        }
    }

    private void guardarBodegas(Tienda tienda) {
        try {
            PrintWriter escritor = new PrintWriter(new FileWriter(carpeta + "/bodegas.txt"));
            for (int i = 0; i < tienda.getBodegas().size(); i++) {
                Bodega b = tienda.getBodegas().get(i);
                escritor.println(limpiar(b.getIdBodega()) + ";" + b.getEmpleado().getIdEmpleado() + ";"
                        + b.getCapacidad() + ";" + limpiar(b.getDireccion()));
            }
            escritor.close();
        } catch (IOException e) {
            System.out.println("Error al guardar bodegas: " + e.getMessage());
        }
    }

    private void guardarInventarios(Tienda tienda) {
        try {
            PrintWriter escritor = new PrintWriter(new FileWriter(carpeta + "/inventarios.txt"));
            for (int i = 0; i < tienda.getInventarios().size(); i++) {
                Inventario inv = tienda.getInventarios().get(i);
                escritor.println(inv.getIdInventario() + ";" + inv.getBodega().getIdBodega() + ";"
                        + unirProductos(inv.getProductos()));
            }
            escritor.close();
        } catch (IOException e) {
            System.out.println("Error al guardar inventarios: " + e.getMessage());
        }
    }

    private void guardarOrdenes(Tienda tienda) {
        try {
            PrintWriter escritor = new PrintWriter(new FileWriter(carpeta + "/ordenes.txt"));
            for (int i = 0; i < tienda.getOrdenes().size(); i++) {
                OrdenCompra o = tienda.getOrdenes().get(i);
                escritor.println(o.getIdOrden() + ";" + o.getCliente().getDpi() + ";" + o.getFecha() + ";"
                        + o.getEstado() + ";" + o.getTotal() + ";" + unirProductos(o.getProductos()));
            }
            escritor.close();
        } catch (IOException e) {
            System.out.println("Error al guardar ordenes: " + e.getMessage());
        }
    }

    private String unirProductos(ArrayList<DetalleProducto> lista) {
        String texto = "";
        for (int i = 0; i < lista.size(); i++) {
            if (i > 0) {
                texto = texto + "/";
            }
            texto = texto + lista.get(i).getProducto().getCodigo() + "=" + lista.get(i).getCantidad();
        }
        return texto;
    }

    private String limpiar(String texto) {
        return texto.replace(";", ",");
    }

    public void cargarTodo(Tienda tienda) {
        cargarProductos(tienda);
        cargarClientes(tienda);
        cargarEmpleados(tienda);
        cargarBodegas(tienda);
        cargarInventarios(tienda);
        cargarOrdenes(tienda);
    }

    private void cargarProductos(Tienda tienda) {
        File archivo = new File(carpeta + "/productos.txt");
        if (!archivo.exists()) {
            return;
        }
        try {
            BufferedReader lector = new BufferedReader(new FileReader(archivo));
            String linea = lector.readLine();
            while (linea != null) {
                String[] campos = linea.split(";", -1);
                Producto p = new Producto(campos[0], campos[1], campos[2], campos[3],
                        Double.parseDouble(campos[4]));
                tienda.getProductos().add(p);
                linea = lector.readLine();
            }
            lector.close();
        } catch (IOException e) {
            System.out.println("Error al leer productos: " + e.getMessage());
        }
    }

    private void cargarClientes(Tienda tienda) {
        File archivo = new File(carpeta + "/clientes.txt");
        if (!archivo.exists()) {
            return;
        }
        try {
            BufferedReader lector = new BufferedReader(new FileReader(archivo));
            String linea = lector.readLine();
            while (linea != null) {
                String[] campos = linea.split(";", -1);
                Cliente c = new Cliente(campos[0], campos[1], campos[2], campos[3], campos[4]);
                tienda.getClientes().add(c);
                linea = lector.readLine();
            }
            lector.close();
        } catch (IOException e) {
            System.out.println("Error al leer clientes: " + e.getMessage());
        }
    }

    private void cargarEmpleados(Tienda tienda) {
        File archivo = new File(carpeta + "/empleados.txt");
        if (!archivo.exists()) {
            return;
        }
        try {
            BufferedReader lector = new BufferedReader(new FileReader(archivo));
            String linea = lector.readLine();
            while (linea != null) {
                String[] campos = linea.split(";", -1);
                Empleado e = new Empleado(campos[0], campos[1], campos[2], campos[3], campos[4],
                        Double.parseDouble(campos[5]));
                tienda.getEmpleados().add(e);
                linea = lector.readLine();
            }
            lector.close();
        } catch (IOException e) {
            System.out.println("Error al leer empleados: " + e.getMessage());
        }
    }

    private void cargarBodegas(Tienda tienda) {
        File archivo = new File(carpeta + "/bodegas.txt");
        if (!archivo.exists()) {
            return;
        }
        try {
            BufferedReader lector = new BufferedReader(new FileReader(archivo));
            String linea = lector.readLine();
            while (linea != null) {
                String[] campos = linea.split(";", -1);
                Empleado encargado = tienda.buscarEmpleado(campos[1]);
                if (encargado != null) {
                    Bodega b = new Bodega(campos[0], encargado, Integer.parseInt(campos[2]), campos[3]);
                    tienda.getBodegas().add(b);
                }
                linea = lector.readLine();
            }
            lector.close();
        } catch (IOException e) {
            System.out.println("Error al leer bodegas: " + e.getMessage());
        }
    }

    private void cargarInventarios(Tienda tienda) {
        File archivo = new File(carpeta + "/inventarios.txt");
        if (!archivo.exists()) {
            return;
        }
        try {
            BufferedReader lector = new BufferedReader(new FileReader(archivo));
            String linea = lector.readLine();
            while (linea != null) {
                String[] campos = linea.split(";", -1);
                Bodega bodega = tienda.buscarBodega(campos[1]);
                if (bodega != null) {
                    Inventario inv = new Inventario(campos[0], bodega);
                    if (!campos[2].isEmpty()) {
                        String[] items = campos[2].split("/");
                        for (int i = 0; i < items.length; i++) {
                            String[] partes = items[i].split("=");
                            Producto p = tienda.buscarProducto(partes[0]);
                            if (p != null) {
                                inv.agregarStock(p, Integer.parseInt(partes[1]));
                            }
                        }
                    }
                    tienda.getInventarios().add(inv);
                }
                linea = lector.readLine();
            }
            lector.close();
        } catch (IOException e) {
            System.out.println("Error al leer inventarios: " + e.getMessage());
        }
    }

    private void cargarOrdenes(Tienda tienda) {
        File archivo = new File(carpeta + "/ordenes.txt");
        if (!archivo.exists()) {
            return;
        }
        try {
            BufferedReader lector = new BufferedReader(new FileReader(archivo));
            String linea = lector.readLine();
            while (linea != null) {
                String[] campos = linea.split(";", -1);
                Cliente cliente = tienda.buscarCliente(campos[1]);
                if (cliente != null) {
                    OrdenCompra orden = new OrdenCompra(campos[0], cliente, campos[2]);
                    orden.setEstado(campos[3]);
                    if (!campos[5].isEmpty()) {
                        String[] items = campos[5].split("/");
                        for (int i = 0; i < items.length; i++) {
                            String[] partes = items[i].split("=");
                            Producto p = tienda.buscarProducto(partes[0]);
                            if (p != null) {
                                orden.agregarProducto(p, Integer.parseInt(partes[1]));
                            }
                        }
                    }
                    tienda.getOrdenes().add(orden);
                }
                linea = lector.readLine();
            }
            lector.close();
        } catch (IOException e) {
            System.out.println("Error al leer ordenes: " + e.getMessage());
        }
    }
}
