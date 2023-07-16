package ingresos.models;

public class MatriculaSemestre {

    private Long id;

    private String tipoIngreso;

    private String tipoAspirante;

    private String version;

    private String modalidad;

    private String sede;

    private Integer year;

    private String cohorte;

    private String estado;

    private Double promedioSemestre;

    private Double promedioAcumulado;

    private String liquidacionMatricula;

    private String estadoPago;

    private String situacionAcademica;

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTipoIngreso() {
        return this.tipoIngreso;
    }

    public void setTipoIngreso(String tipoIngreso) {
        this.tipoIngreso = tipoIngreso;
    }

    public String getTipoAspirante() {
        return this.tipoAspirante;
    }

   public void setTipoAspirante(String tipoAspirante) {
        this.tipoAspirante = tipoAspirante;
    }

    public String getVersion() {
        return this.version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getModalidad() {
        return this.modalidad;
    }

    public void setModalidad(String modalidad) {
        this.modalidad = modalidad;
    }

    public String getSede() {
        return this.sede;
    }

    public void setSede(String sede) {
        this.sede = sede;
    }

    public Integer getYear() {
        return this.year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getCohorte() {
        return this.cohorte;
    }

    public void setCohorte(String cohorte) {
        this.cohorte = cohorte;
    }

    public String getEstado() {
        return this.estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Double getPromedioSemestre() {
        return this.promedioSemestre;
    }

    public void setPromedioSemestre(Double promedioSemestre) {
        this.promedioSemestre = promedioSemestre;
    }

    public Double getPromedioAcumulado() {
        return this.promedioAcumulado;
    }

    public void setPromedioAcumulado(Double promedioAcumulado) {
        this.promedioAcumulado = promedioAcumulado;
    }

    public String getLiquidacionMatricula() {
        return this.liquidacionMatricula;
    }

    public void setLiquidacionMatricula(String liquidacionMatricula) {
        this.liquidacionMatricula = liquidacionMatricula;
    }

    public String getEstadoPago() {
        return this.estadoPago;
    }

    public void setEstadoPago(String estadoPago) {
        this.estadoPago = estadoPago;
    }

    public String getSituacionAcademica() {
        return this.situacionAcademica;
    }

    public void setSituacionAcademica(String situacionAcademica) {
        this.situacionAcademica = situacionAcademica;
    }

}
