package engine;

import com.towel.el.annotation.Resolvable;

public class Atuador extends SensorAtuadorComum {
	private static final long serialVersionUID = -1670262637425297242L;

	@Resolvable(colName="Valor para Atuar")
	private String valorParaAtuar;

	public Atuador(Tipo tipo, Localizacao localizacao, String nome, String descricao) {
		super(tipo, localizacao, nome, descricao);
	}

	public String getValorParaAtuar() {
		return valorParaAtuar;
	}

	public void setValorParaAtuar(String valorParaAtuar) {
		this.valorParaAtuar = valorParaAtuar;
	}
}
