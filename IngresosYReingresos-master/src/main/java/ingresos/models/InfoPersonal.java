package ingresos.models;

public class InfoPersonal {

    private String id;

    private String tipoID;

    private String nombre;

    private String apellidos;

    private String genero;

    private String fechaNacimiento;

    private Integer edadIngreso;

    private Integer telefono;

    private Integer celular;

    private String correoPersonal;

    private String nacionalidad;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTipoID() {
        return this.tipoID;
    }

    public void setTipoID(String tipoID) {
        this.tipoID = tipoID;
    }

    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return this.apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getGenero() {
        return this.genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getFechaNacimiento() {
        return this.fechaNacimiento;
    }

    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public Integer getEdadIngreso() {
        return this.edadIngreso;
    }

    public void setEdadIngreso(Integer edadIngreso) {
        this.edadIngreso = edadIngreso;
    }

    public Integer getTelefono() {
        return this.telefono;
    }

    public void setTelefono(Integer telefono) {
        this.telefono = telefono;
    }

    public Integer getCelular() {
        return this.celular;
    }

    public void setCelular(Integer celular) {
        this.celular = celular;
    }

    public String getCorreoPersonal() {
        return this.correoPersonal;
    }

    public void setCorreoPersonal(String correoPersonal) {
        this.correoPersonal = correoPersonal;
    }

    public String getNacionalidad() {
        return this.nacionalidad;
    }

    public void setNacionalidad(String nacionalidad) {
        this.nacionalidad = nacionalidad;
    }

}
