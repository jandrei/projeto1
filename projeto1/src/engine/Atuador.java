package engine;

import com.towel.el.annotation.Resolvable;

import comum.SensorAtuadorComum;
import comum.tipos.Localizacao;
import comum.tipos.Tipo;

public class Atuador extends SensorAtuadorComum {
	private static final long serialVersionUID = -1670262637425297242L;

	@Resolvable(colName="Valor para Atuar")
	private Integer valorParaAtuar;

	public Atuador(Tipo tipo, Localizacao localizacao, String nome, String descricao) {
		super(tipo, localizacao, nome, descricao);
	}


	public Integer getValorParaAtuar() {
		return valorParaAtuar;
	}

	public void setValorParaAtuar(Integer valorParaAtuar) {
		this.valorParaAtuar = valorParaAtuar;
	}
}
