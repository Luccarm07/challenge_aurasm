package br.com.aurasm.model;

import java.util.Date;

public class Consulta {
    private Integer cdConsulta;
    private Integer cdPaciente;
    private Integer cdMedico;
    private Date dtInicio;
    private Date dtFim;
    private String dsObservacao;
    private String dsStatus;

    public Consulta() {
    }

    public Consulta(Integer cdConsulta, Integer cdPaciente, Integer cdMedico, Date dtInicio, Date dtFim, String dsObservacao, String dsStatus) {
        this.cdConsulta = cdConsulta;
        this.cdPaciente = cdPaciente;
        this.cdMedico = cdMedico;
        this.dtInicio = dtInicio;
        this.dtFim = dtFim;
        this.dsObservacao = dsObservacao;
        this.dsStatus = dsStatus;
    }

    public Integer getCdConsulta() {
        return cdConsulta;
    }

    public void setCdConsulta(Integer cdConsulta) {
        this.cdConsulta = cdConsulta;
    }

    public Integer getCdPaciente() {
        return cdPaciente;
    }

    public void setCdPaciente(Integer cdPaciente) {
        this.cdPaciente = cdPaciente;
    }

    public Integer getCdMedico() {
        return cdMedico;
    }

    public void setCdMedico(Integer cdMedico) {
        this.cdMedico = cdMedico;
    }

    public Date getDtInicio() {
        return dtInicio;
    }

    public void setDtInicio(Date dtInicio) {
        this.dtInicio = dtInicio;
    }

    public Date getDtFim() {
        return dtFim;
    }

    public void setDtFim(Date dtFim) {
        this.dtFim = dtFim;
    }

    public String getDsObservacao() {
        return dsObservacao;
    }

    public void setDsObservacao(String dsObservacao) {
        this.dsObservacao = dsObservacao;
    }

    public String getDsStatus() {
        return dsStatus;
    }

    public void setDsStatus(String dsStatus) {
        this.dsStatus = dsStatus;
    }
}
