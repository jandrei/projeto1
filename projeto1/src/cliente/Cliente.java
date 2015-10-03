package cliente;

import java.rmi.Naming;
import java.util.logging.Level;
import java.util.logging.Logger;

import comum.IServidor;
import comum.tipos.Localizacao;
import comum.tipos.Tipo;
import engine.Atuador;
import engine.Sensor;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author lhries
 */
public class Cliente {
	public static void main(String[] args) {

		try {
			
			Atuador atuador = new Atuador(Tipo.LUZ_INTENSIDADE, Localizacao.BANHEIRO, "Luz", "atua na luz do banheiro");
			Sensor sensor= new Sensor(Tipo.LUZ_INTENSIDADE, Localizacao.BANHEIRO, "luz", "monitora luz do banheiro");
			
			IServidor iot = (IServidor) Naming.lookup("rmi://localhost:1099/iot");
			iot.registrarSensor(sensor);
			iot.registrarAtuador(atuador);
			
			//registra valor inicial
//			sensor.setValorLido(0);
//		iot.registrarValorLido(sensor);
			
			//altera valor inicial
			//sensor.setValorLido(1);
			//iot.registrarValorLido(sensor);
			
			//atua sobre um valor
			atuador.setValorParaAtuar(10);
			iot.atuar(atuador);
			
			//consulta o valor que um atuador setou
//			iot.obtemValorAtribuido(sensor);
		} catch (Exception ex) {
			Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
		}

	}
}
