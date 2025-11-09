package br.com.aurasm.model;

public class Acompanhante {
    private Integer cdAcompanhante;
    private Integer cdPaciente;
    private String nmAcompanhante;
    private String dsParentesco;

    public Acompanhante() {
    }

    public Acompanhante(Integer cdAcompanhante, Integer cdPaciente, String nmAcompanhante, String dsParentesco) {
        this.cdAcompanhante = cdAcompanhante;
        this.cdPaciente = cdPaciente;
        this.nmAcompanhante = nmAcompanhante;
        this.dsParentesco = dsParentesco;
    }

    public Integer getCdAcompanhante() {
        return cdAcompanhante;
    }

    public void setCdAcompanhante(Integer cdAcompanhante) {
        this.cdAcompanhante = cdAcompanhante;
    }

    public Integer getCdPaciente() {
        return cdPaciente;
    }

    public void setCdPaciente(Integer cdPaciente) {
        this.cdPaciente = cdPaciente;
    }

    public String getNmAcompanhante() {
        return nmAcompanhante;
    }

    public void setNmAcompanhante(String nmAcompanhante) {
        this.nmAcompanhante = nmAcompanhante;
    }

    public String getDsParentesco() {
        return dsParentesco;
    }

    public void setDsParentesco(String dsParentesco) {
        this.dsParentesco = dsParentesco;
    }
}
