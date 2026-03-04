package dam.code.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Entrada {

    private String nombre;
    private int edad;
    private LocalDate fechaCompra;

    public Entrada(String nombre, int edad) {
        this.nombre = nombre;
        this.edad = edad;
        this.fechaCompra = LocalDate.now();
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public LocalDate getFechaCompra() {
        return fechaCompra;
    }

    public void setFechaCompra(LocalDate fechaCompra) {
        this.fechaCompra = fechaCompra;
    }

    @Override
    public String toString() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String fecha = dtf.format(this.fechaCompra);
        return String.format("Nombre: %s, Edad: %d, Fecha de compra: %s", this.nombre, this.edad, fecha);
    }
}
