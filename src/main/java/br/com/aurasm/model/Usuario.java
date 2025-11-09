package br.com.aurasm.model;

public class Usuario {
    private Integer cdUsuario;
    private Integer cdPessoa;
    private String dsEmail;
    private String dsSenha;
    private String nmUsuario;

    public Usuario() {
    }

    public Usuario(Integer cdUsuario, Integer cdPessoa, String dsEmail, String dsSenha, String nmUsuario) {
        this.cdUsuario = cdUsuario;
        this.cdPessoa = cdPessoa;
        this.dsEmail = dsEmail;
        this.dsSenha = dsSenha;
        this.nmUsuario = nmUsuario;
    }

    public Integer getCdUsuario() {
        return cdUsuario;
    }

    public void setCdUsuario(Integer cdUsuario) {
        this.cdUsuario = cdUsuario;
    }

    public Integer getCdPessoa() {
        return cdPessoa;
    }

    public void setCdPessoa(Integer cdPessoa) {
        this.cdPessoa = cdPessoa;
    }

    public String getDsEmail() {
        return dsEmail;
    }

    public void setDsEmail(String dsEmail) {
        this.dsEmail = dsEmail;
    }

    public String getDsSenha() {
        return dsSenha;
    }

    public void setDsSenha(String dsSenha) {
        this.dsSenha = dsSenha;
    }

    public String getNmUsuario() {
        return nmUsuario;
    }

    public void setNmUsuario(String nmUsuario) {
        this.nmUsuario = nmUsuario;
    }
}
