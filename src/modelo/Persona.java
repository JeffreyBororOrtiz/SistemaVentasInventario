package modelo;

public class Persona {

    private String dpi;
    private String nombre;
    private String direccion;

    public Persona(String dpi, String nombre, String direccion) {
        this.dpi = dpi;
        this.nombre = nombre;
        this.direccion = direccion;
    }

    public String getDpi() {
        return dpi;
    }

    public void setDpi(String dpi) {
        this.dpi = dpi;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
}
