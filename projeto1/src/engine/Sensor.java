package engine;

import com.towel.el.annotation.Resolvable;

public class Sensor extends SensorAtuadorComum {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7811770673171536523L;

	public Sensor(Tipo tipo, Localizacao localizacao, String nome, String descricao) {
		super(tipo, localizacao, nome, descricao);
	}

	@Resolvable(colName = "Valor Lido")
	private String valorLido;

	public String getValorLido() {
		return valorLido;
	}

	public void setValorLido(String valorLido) {

		this.valorLido = valorLido;
	}

}
