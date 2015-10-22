package formatter;

import com.towel.bean.Formatter;

/**
 *utilizado pelo compoente towel para obter as informações da tela ou do pojo e setar na tela ou pojo no formato correto. 
 *
 */
public class IntFormatter implements Formatter {
	public Object format(Object obj) {
		Integer d = (Integer) obj;
		return d.toString();
	}

	public Object parse(Object obj) {
		return Integer.valueOf(Integer.parseInt((String) obj));
	}

	public String getName() {
		return "int";
	}
}
