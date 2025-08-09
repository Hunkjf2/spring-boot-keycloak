package com.example.keycloak.service.pessoa;

import com.example.keycloak.config.exception.CpfJaCadastradoException;
import com.example.keycloak.dto.PessoaDto;
import com.example.keycloak.config.exception.PessoaNaoEncontradaException;
import com.example.keycloak.mapper.PessoaMapper;
import com.example.keycloak.model.Pessoa;
import com.example.keycloak.repository.PessoaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import static com.example.keycloak.constants.global.MenssagemSistema.*;
import static com.example.keycloak.constants.pessoa.Pessoa.*;

@Service
@RequiredArgsConstructor
public class PessoaService {

    private final PessoaRepository pessoaRepository;
    private final PessoaMapper pessoaMapper;

    public Pessoa consultar(Long id) {
        return pessoaRepository.findById(id)
                .orElseThrow(() -> new PessoaNaoEncontradaException(REGISTRO_NAO_ENCONTRADO));
    }

    @Transactional
    public Pessoa cadastrar(PessoaDto pessoaDto) {
        validarCpfUnico(pessoaDto.cpf());
        Pessoa pessoa = pessoaMapper.toEntity(pessoaDto);
//        pessoa.setNegativado(negativado);
        return salvar(pessoa);
    }

    @Transactional
    public Pessoa editar(Long id, PessoaDto pessoaDto) {
        validarCpfUnicoParaEdicao(pessoaDto.cpf(), id);
        Pessoa pessoaAtualizada = this.salvar(atualizaDados(pessoaDto, this.consultar(id)));
        return pessoaAtualizada;
    }

    @Transactional
    public void deletarPessoa(Long id) {
        Pessoa pessoa = this.consultar(id);
        pessoaRepository.deleteById(id);
    }

    private Pessoa atualizaDados(PessoaDto pessoaDto, Pessoa pessoa) {
        pessoa.setNome(pessoaDto.nome());
        pessoa.setDataNascimento(pessoaDto.dataNascimento());
        return pessoa;
    }

    private Pessoa salvar(Pessoa pessoa) {
        return pessoaRepository.save(pessoa);
    }

    private void validarCpfUnicoParaEdicao(String cpf, Long id) {
        if (pessoaRepository.existsByCpfAndIdNot(cpf, id)) {
            throw new CpfJaCadastradoException(CPF_CADASTRADO);
        }
    }

    private void validarCpfUnico(String cpf) {
        if (pessoaRepository.existsByCpf(cpf)) {
            throw new CpfJaCadastradoException(CPF_CADASTRADO);
        }
    }

}