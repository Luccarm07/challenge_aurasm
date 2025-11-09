package br.com.aurasm.model;

public class Paciente {
    private Integer cdPaciente;
    private Integer cdPessoa;
    private String nmPlanoSaude;

    public Paciente() {
    }

    public Paciente(Integer cdPaciente, Integer cdPessoa, String nmPlanoSaude) {
        this.cdPaciente = cdPaciente;
        this.cdPessoa = cdPessoa;
        this.nmPlanoSaude = nmPlanoSaude;
    }

    public Integer getCdPaciente() {
        return cdPaciente;
    }

    public void setCdPaciente(Integer cdPaciente) {
        this.cdPaciente = cdPaciente;
    }

    public Integer getCdPessoa() {
        return cdPessoa;
    }

    public void setCdPessoa(Integer cdPessoa) {
        this.cdPessoa = cdPessoa;
    }

    public String getNmPlanoSaude() {
        return nmPlanoSaude;
    }

    public void setNmPlanoSaude(String nmPlanoSaude) {
        this.nmPlanoSaude = nmPlanoSaude;
    }
}
