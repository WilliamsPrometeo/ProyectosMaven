package dam.code.models;

import dam.code.models.utils.Rol;

public class Usuario {
    private int id;
    private String dni;
    private String nombre;
    private String email;
    private Rol rol;

    public Usuario(String dni, String nombre, String email) {
        this.dni = dni;
        this.nombre = nombre;
        this.email = email;
        this.rol = Rol.USER;
    }

    public Usuario(String dni, String nombre, String email, Rol rol) {
        this.dni = dni;
        this.nombre = nombre;
        this.email = email;
        this.rol = rol;
    }

    public Usuario(int id, String dni, String nombre, String email,Rol rol) {
        this.id = id;
        this.dni = dni;
        this.nombre = nombre;
        this.email = email;
        this.rol = rol;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }
}
