package br.com.aurasm.service;

import br.com.aurasm.dao.PessoaDAO;
import br.com.aurasm.exception.BusinessException;
import br.com.aurasm.model.Pessoa;

import java.util.List;

public class PessoaService {

    private final PessoaDAO pessoaDAO = new PessoaDAO();

    public List<Pessoa> listarTodos() {
        return pessoaDAO.listarTodos();
    }

    public Pessoa buscarPorId(Integer id) {
        Pessoa pessoa = pessoaDAO.buscarPorId(id);
        if (pessoa == null) {
            throw new BusinessException("Pessoa não encontrada com ID: " + id);
        }
        return pessoa;
    }

    public Pessoa criar(Pessoa pessoa) {
        // Validações
        if (pessoa.getNmPessoa() == null || pessoa.getNmPessoa().trim().isEmpty()) {
            throw new BusinessException("Nome da pessoa é obrigatório");
        }
        
        if (pessoa.getNrCpf() == null || pessoa.getNrCpf().trim().isEmpty()) {
            throw new BusinessException("CPF é obrigatório");
        }
        
        if (pessoa.getTpPessoa() == null || pessoa.getTpPessoa().trim().isEmpty()) {
            throw new BusinessException("Tipo de pessoa é obrigatório");
        }
        
        if (!pessoa.getTpPessoa().equals("Médico") && 
            !pessoa.getTpPessoa().equals("Paciente") && 
            !pessoa.getTpPessoa().equals("Usuário")) {
            throw new BusinessException("Tipo de pessoa inválido. Use: Médico, Paciente ou Usuário");
        }
        
        if (pessoa.getDtNascimento() == null) {
            throw new BusinessException("Data de nascimento é obrigatória");
        }
        
        // Verificar se CPF já existe
        Pessoa pessoaExistente = pessoaDAO.buscarPorCpf(pessoa.getNrCpf());
        if (pessoaExistente != null) {
            throw new BusinessException("CPF já cadastrado");
        }
        
        Integer id = pessoaDAO.criar(pessoa);
        if (id == -1) {
            throw new BusinessException("Erro ao criar pessoa");
        }
        
        return pessoaDAO.buscarPorId(id);
    }

    public Pessoa atualizar(Integer id, Pessoa pessoa) {
        Pessoa pessoaExistente = buscarPorId(id);
        
        // Validações
        if (pessoa.getNmPessoa() != null && !pessoa.getNmPessoa().trim().isEmpty()) {
            pessoaExistente.setNmPessoa(pessoa.getNmPessoa());
        }
        
        if (pessoa.getTpPessoa() != null) {
            if (!pessoa.getTpPessoa().equals("Médico") && 
                !pessoa.getTpPessoa().equals("Paciente") && 
                !pessoa.getTpPessoa().equals("Usuário")) {
                throw new BusinessException("Tipo de pessoa inválido");
            }
            pessoaExistente.setTpPessoa(pessoa.getTpPessoa());
        }
        
        if (pessoa.getDtNascimento() != null) {
            pessoaExistente.setDtNascimento(pessoa.getDtNascimento());
        }
        
        if (pessoa.getNrCpf() != null && !pessoa.getNrCpf().equals(pessoaExistente.getNrCpf())) {
            Pessoa cpfExistente = pessoaDAO.buscarPorCpf(pessoa.getNrCpf());
            if (cpfExistente != null && !cpfExistente.getCdPessoa().equals(id)) {
                throw new BusinessException("CPF já cadastrado para outra pessoa");
            }
            pessoaExistente.setNrCpf(pessoa.getNrCpf());
        }
        
        pessoaDAO.atualizar(pessoaExistente);
        return pessoaExistente;
    }

    public void deletar(Integer id) {
        buscarPorId(id); // Verifica se existe
        pessoaDAO.deletar(id);
    }
}
