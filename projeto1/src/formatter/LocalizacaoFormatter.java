package formatter;

import com.towel.bean.Formatter;

import engine.Localizacao;

/**
 *utilizado pelo compoente towel para obter as informações da tela ou do pojo e setar na tela ou pojo no formato correto. 
 *
 */
public class LocalizacaoFormatter implements Formatter {
	public Object format(Object obj) {
		Localizacao d = (Localizacao) obj;
		return d.toString();
	}

	public Object parse(Object obj) {
		return Localizacao.valueOf((String) obj);
	}

	public String getName() {
		return "tipo";
	}
}