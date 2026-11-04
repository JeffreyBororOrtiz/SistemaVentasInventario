package modelo;

public class Cliente extends Persona {

    private String fechaNacimiento;
    private String tipo;

    public Cliente(String dpi, String nombre, String direccion, String fechaNacimiento, String tipo) {
        super(dpi, nombre, direccion);
        this.fechaNacimiento = fechaNacimiento;
        this.tipo = tipo;
    }

    public String getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
