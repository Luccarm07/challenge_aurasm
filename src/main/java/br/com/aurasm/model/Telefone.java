package br.com.aurasm.model;

public class Telefone {
    private Integer cdTelefone;
    private Integer cdPessoa;
    private Integer cdAcompanhante;
    private Integer ddd;
    private Long nmTelefone;

    public Telefone() {
    }

    public Telefone(Integer cdTelefone, Integer cdPessoa, Integer cdAcompanhante, Integer ddd, Long nmTelefone) {
        this.cdTelefone = cdTelefone;
        this.cdPessoa = cdPessoa;
        this.cdAcompanhante = cdAcompanhante;
        this.ddd = ddd;
        this.nmTelefone = nmTelefone;
    }

    public Integer getCdTelefone() {
        return cdTelefone;
    }

    public void setCdTelefone(Integer cdTelefone) {
        this.cdTelefone = cdTelefone;
    }

    public Integer getCdPessoa() {
        return cdPessoa;
    }

    public void setCdPessoa(Integer cdPessoa) {
        this.cdPessoa = cdPessoa;
    }

    public Integer getCdAcompanhante() {
        return cdAcompanhante;
    }

    public void setCdAcompanhante(Integer cdAcompanhante) {
        this.cdAcompanhante = cdAcompanhante;
    }

    public Integer getDdd() {
        return ddd;
    }

    public void setDdd(Integer ddd) {
        this.ddd = ddd;
    }

    public Long getNmTelefone() {
        return nmTelefone;
    }

    public void setNmTelefone(Long nmTelefone) {
        this.nmTelefone = nmTelefone;
    }
}
