package ingresos.models;

public class OtroEstudioFormal {

    private Long id;

    private String institucion;

    private Long estudio;

    private Boolean graduado;

    private Integer year;

    private String tituloObtenido;

    private String nivel;


    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getInstitucion() {
        return this.institucion;
    }

    public void setInstitucion(String institucion) {
        this.institucion = institucion;
    }

    public Long getEstudio() {
        return this.estudio;
    }

    public void setEstudio(Long estudio) {
        this.estudio = estudio;
    }

    public Boolean getGraduado() {
        return this.graduado;
    }

    public void setGraduado(Boolean graduado) {
        this.graduado = graduado;
    }

    public Integer getYear() {
        return this.year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getTituloObtenido() {
        return this.tituloObtenido;
    }

    public void setTituloObtenido(String tituloObtenido) {
        this.tituloObtenido = tituloObtenido;
    }

    public String getNivel() {
        return this.nivel;
    }

    public void setNivel(String nivel) {
        this.nivel = nivel;
    }
    
}
