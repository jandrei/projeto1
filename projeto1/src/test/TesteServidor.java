package test;

import java.rmi.Naming;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import comum.IServidor;
import comum.tipos.Localizacao;
import comum.tipos.Tipo;
import engine.Atuador;
import engine.Sensor;

public class TesteServidor {

	IServidor iot;

	Atuador atuador;
	Sensor sensor;

	@Before
	public void init() throws Exception {
		iot = (IServidor) Naming.lookup("rmi://localhost:1099/iot");

		atuador = new Atuador(Tipo.LUZ_INTENSIDADE, Localizacao.BANHEIRO, "A Luz", "atua na luz do banheiro");
		sensor = new Sensor(Tipo.LUZ_INTENSIDADE, Localizacao.BANHEIRO, "S luz", "monitora luz do banheiro");

	}

	@Test
	public void registrando() throws Exception {
		List<Atuador> atuadores = iot.getAtuadores();
		List<Sensor> sensores = iot.getSensores();
		
		if (atuadores.isEmpty()) {
			iot.registrarAtuador(atuador);
		}
		if (sensores.isEmpty()) {
			iot.registrarSensor(sensor);
		}

		Assert.assertNotNull(iot.getAtuadores());
		Assert.assertNotNull(iot.getSensores());
	}

	@Test
	public void leValorSensor() throws Exception {
		Assert.assertNull( iot.obtemValorAtribuido(sensor));
	}
	
	@Test
	public void atuadorMudaValorSensor()throws Exception{
		atuador.setValorParaAtuar(10);
		iot.atuar(atuador);
		
		Assert.assertEquals(iot.obtemValorAtribuido(sensor),Integer.valueOf(10));
	}
	
	@Test
	public void registravalorLido()throws Exception{
		sensor.setValorLido(55);
		iot.registrarValorLido(sensor);
		
		Assert.assertEquals(iot.obtemValorLido(sensor),Integer.valueOf(55));
	}
	
}
