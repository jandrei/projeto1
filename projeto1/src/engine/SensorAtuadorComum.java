package engine;

import java.io.Serializable;

import com.towel.el.annotation.Resolvable;

import engine.tipos.Localizacao;
import engine.tipos.Tipo;

public class SensorAtuadorComum implements Serializable {

	private static final long serialVersionUID = 7912615432692540656L;

	@Resolvable(colName = "Tipo")
	private Tipo tipo;
	
	@Resolvable(colName = "Localização")
	private Localizacao localizacao;
	
	@Resolvable(colName = "Nome")
	private String nome;
	
	@Resolvable(colName = "Descrição")
	private String descricao;

	public SensorAtuadorComum(Tipo tipo, Localizacao localizacao, String nome, String descricao) {
		super();
		this.tipo = tipo;
		this.localizacao = localizacao;
		this.nome = nome;
		this.descricao = descricao;
		check();
	}

	private void check() {
		if (this.tipo == null || this.localizacao == null || this.nome == null || this.descricao == null) {
			throw new RuntimeException("Todos os campos são obrigatórios.");
		}
	}

	public Tipo getTipo() {
		return tipo;
	}

	public void setTipo(Tipo tipo) {
		this.tipo = tipo;
	}

	public Localizacao getLocalizacao() {
		return localizacao;
	}

	public void setLocalizacao(Localizacao localizacao) {
		this.localizacao = localizacao;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getId() {
		return this.nome;
	}

	@Override
	public String toString() {
		return String.format("{" + getClass().getName() + " = nome=%s, tipo=%s, localização=%s}", this.nome, this.tipo.toString(), this.localizacao.toString());
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SensorAtuadorComum other = (SensorAtuadorComum) obj;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		return true;
	}

}
