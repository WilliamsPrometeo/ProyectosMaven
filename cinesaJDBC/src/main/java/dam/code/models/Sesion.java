package dam.code.models;

import dam.code.models.utils.Rol;

public class Sesion {
    private static Usuario usuarioActual;

    public static void setUsuario(Usuario usuario){
        usuarioActual = usuario;
    }

    public static Usuario getUsuario(){
        return usuarioActual;
    }

    public static Rol getRol(){
        return usuarioActual.getRol();
    }
}
