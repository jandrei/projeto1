package formatter;

import com.towel.bean.Formatter;

import engine.Tipo;

/**
 * utilizado pelo compoente towel para obter as informações da tela ou do pojo e
 * setar na tela ou pojo no formato correto.
 *
 */
public class TipoFormatter implements Formatter {
	public Object format(Object obj) {
		Tipo d = (Tipo) obj;
		return d.toString();
	}

	public Object parse(Object obj) {
		return Tipo.valueOf((String) obj);
	}

	public String getName() {
		return "tipo";
	}
}
