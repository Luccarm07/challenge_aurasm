package br.com.aurasm.model;

import java.util.Date;

public class HistoricoConsulta {
    private Integer cdHistorico;
    private Integer cdConsulta;
    private Integer cdMedico;
    private String nmMedico;
    private Date dtInicio;
    private Date dtFim;
    private String dsConsulta;

    public HistoricoConsulta() {
    }

    public HistoricoConsulta(Integer cdHistorico, Integer cdConsulta, Integer cdMedico, String nmMedico, Date dtInicio, Date dtFim, String dsConsulta) {
        this.cdHistorico = cdHistorico;
        this.cdConsulta = cdConsulta;
        this.cdMedico = cdMedico;
        this.nmMedico = nmMedico;
        this.dtInicio = dtInicio;
        this.dtFim = dtFim;
        this.dsConsulta = dsConsulta;
    }

    public Integer getCdHistorico() {
        return cdHistorico;
    }

    public void setCdHistorico(Integer cdHistorico) {
        this.cdHistorico = cdHistorico;
    }

    public Integer getCdConsulta() {
        return cdConsulta;
    }

    public void setCdConsulta(Integer cdConsulta) {
        this.cdConsulta = cdConsulta;
    }

    public Integer getCdMedico() {
        return cdMedico;
    }

    public void setCdMedico(Integer cdMedico) {
        this.cdMedico = cdMedico;
    }

    public String getNmMedico() {
        return nmMedico;
    }

    public void setNmMedico(String nmMedico) {
        this.nmMedico = nmMedico;
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

    public String getDsConsulta() {
        return dsConsulta;
    }

    public void setDsConsulta(String dsConsulta) {
        this.dsConsulta = dsConsulta;
    }
}
