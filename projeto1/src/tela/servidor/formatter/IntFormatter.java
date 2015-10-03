package tela.servidor.formatter;

import com.towel.bean.Formatter;

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
