package idnp.app.bicikm.Objetos;

public class Usuario {
    private String id;
    private String nombre;
    private String apellido;
    private String celular;
    private String puntos;
    private String kilometraje;
    private String contra;
    private String token;

    public String getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public String getCelular() {
        return celular;
    }

    public String getPuntos() {
        return puntos;
    }

    public String getKilometraje() {
        return kilometraje;
    }

    public String getContra() {
        return contra;
    }

    public String getToken() {
        return token;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public void setPuntos(String puntos) {
        this.puntos = puntos;
    }

    public void setKilometraje(String kilometraje) {
        this.kilometraje = kilometraje;
    }

    public void setContra(String contra) {
        this.contra = contra;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
