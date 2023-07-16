package ingresos.models;

import java.util.List;

public class InfoAcademica {

    private Long id;

    private InfoBachillerato bachillerato;

    private OtroEstudioFormal otrosEstudios;

    private List<MatriculaSemestre> informacionU;

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public InfoBachillerato getBachillerato(){
        return this.bachillerato;
    }

    public void setBachillerato(InfoBachillerato bachillerato){
        this.bachillerato = bachillerato;
    }

    public OtroEstudioFormal getOtrosEstudios(){
        return this.otrosEstudios;
    }

    public void setOtrosEstudios(OtroEstudioFormal otrosEstudios){
        this.otrosEstudios = otrosEstudios;
    }

    public void setInformacionU(List<MatriculaSemestre> informacionU){
        this.informacionU = informacionU;
    }

    public List<MatriculaSemestre> getInformacionU(){
        return this.informacionU;
    }

    public void addMatriculaSemestre(MatriculaSemestre matriculaSemestre){
        this.informacionU.add(matriculaSemestre);
    }

    public void removeMatriculaSemestre(MatriculaSemestre matriculaSemestre){
        this.informacionU.remove(matriculaSemestre);
    }

}
