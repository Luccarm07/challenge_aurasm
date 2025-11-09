package br.com.aurasm.model;

import java.util.Date;

public class Pessoa {
    private Integer cdPessoa;
    private String tpPessoa;
    private String nmPessoa;
    private Date dtNascimento;
    private String nrCpf;

    public Pessoa() {
    }

    public Pessoa(Integer cdPessoa, String tpPessoa, String nmPessoa, Date dtNascimento, String nrCpf) {
        this.cdPessoa = cdPessoa;
        this.tpPessoa = tpPessoa;
        this.nmPessoa = nmPessoa;
        this.dtNascimento = dtNascimento;
        this.nrCpf = nrCpf;
    }

    public Integer getCdPessoa() {
        return cdPessoa;
    }

    public void setCdPessoa(Integer cdPessoa) {
        this.cdPessoa = cdPessoa;
    }

    public String getTpPessoa() {
        return tpPessoa;
    }

    public void setTpPessoa(String tpPessoa) {
        this.tpPessoa = tpPessoa;
    }

    public String getNmPessoa() {
        return nmPessoa;
    }

    public void setNmPessoa(String nmPessoa) {
        this.nmPessoa = nmPessoa;
    }

    public Date getDtNascimento() {
        return dtNascimento;
    }

    public void setDtNascimento(Date dtNascimento) {
        this.dtNascimento = dtNascimento;
    }

    public String getNrCpf() {
        return nrCpf;
    }

    public void setNrCpf(String nrCpf) {
        this.nrCpf = nrCpf;
    }
}
