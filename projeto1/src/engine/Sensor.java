package engine;

import com.towel.el.annotation.Resolvable;

import engine.tipos.Localizacao;
import engine.tipos.Tipo;

public class Sensor extends SensorAtuadorComum {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7811770673171536523L;

	public Sensor(Tipo tipo, Localizacao localizacao, String nome, String descricao) {
		super(tipo, localizacao, nome, descricao);
	}

	@Resolvable(colName = "Valor Lido")
	private Integer valorLido;

	@Resolvable(colName = "Valor Atribuido")
	private Integer valorAtribuido;
	
	
	

	public Integer getValorLido() {
		return valorLido;
	}

	public void setValorLido(Integer valorLido) {
		
		this.valorLido = valorLido;
	}

	public Integer getValorAtribuido() {
		return valorAtribuido;
	}

	public void setValorAtribuido(Integer valorAtribuido) {
		
		this.valorAtribuido = valorAtribuido;
	}

}
