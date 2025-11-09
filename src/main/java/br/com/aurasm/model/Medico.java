package br.com.aurasm.model;

public class Medico {
    private Integer cdMedico;
    private Integer cdPessoa;
    private String nrCrm;
    private String dsEspecialidade;
    private Double vlSalario;

    public Medico() {
    }

    public Medico(Integer cdMedico, Integer cdPessoa, String nrCrm, String dsEspecialidade, Double vlSalario) {
        this.cdMedico = cdMedico;
        this.cdPessoa = cdPessoa;
        this.nrCrm = nrCrm;
        this.dsEspecialidade = dsEspecialidade;
        this.vlSalario = vlSalario;
    }

    public Integer getCdMedico() {
        return cdMedico;
    }

    public void setCdMedico(Integer cdMedico) {
        this.cdMedico = cdMedico;
    }

    public Integer getCdPessoa() {
        return cdPessoa;
    }

    public void setCdPessoa(Integer cdPessoa) {
        this.cdPessoa = cdPessoa;
    }

    public String getNrCrm() {
        return nrCrm;
    }

    public void setNrCrm(String nrCrm) {
        this.nrCrm = nrCrm;
    }

    public String getDsEspecialidade() {
        return dsEspecialidade;
    }

    public void setDsEspecialidade(String dsEspecialidade) {
        this.dsEspecialidade = dsEspecialidade;
    }

    public Double getVlSalario() {
        return vlSalario;
    }

    public void setVlSalario(Double vlSalario) {
        this.vlSalario = vlSalario;
    }
}
