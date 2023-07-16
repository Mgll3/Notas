package ingresos.models;


public class Estudiante extends Usuario  {

    private int id;
    
    private Boolean diligenciado;
    
    private InfoPersonal estudianteP;

    private InfoEconomica estudianteE;

    private InfoAcademica estudianteA;

    
    public Estudiante() {

    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Boolean getDiligenciado() {
        return this.diligenciado;
    }

    public void setDiligenciado(Boolean diligenciado) {
        this.diligenciado = diligenciado;
    }

    public InfoPersonal getEstudianteP() {
        return this.estudianteP;
    }

    public void setEstudianteP(InfoPersonal infoPersonal) {
        this.estudianteP = infoPersonal;
    }

    public InfoEconomica getEstudianteE() {
        return this.estudianteE;
    }
    
    public void setEstudianteE(InfoEconomica infoEconomica) {
        this.estudianteE = infoEconomica;
    }

    public InfoAcademica getEstudianteA() {
        return this.estudianteA;
    }

    public void setEstudianteA(InfoAcademica infoAcademica) {
        this.estudianteA = infoAcademica;
    }

    
}
