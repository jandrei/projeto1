package tela.servidor;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

import comum.IServidor;
import comum.IServidorTela;
import engine.Atuador;
import engine.Sensor;
import engine.SensorAtuadorComum;

public class Servidor extends UnicastRemoteObject implements IServidor {
	private static final long serialVersionUID = -656243459860317218L;

	private List<Sensor> sensores = new ArrayList<Sensor>();
	private List<Atuador> atuadores = new ArrayList<Atuador>();

	IServidorTela tela;
	

	public Servidor(IServidorTela tela) throws RemoteException {
		super();
		this.tela = tela;
		
	}

	@Override
	public void registrarSensor(Sensor sensor) throws RemoteException {
		validaSeJaRegistrado(sensores, sensor);
		sensores.add(sensor);
		System.out.println("Registrando = " + sensor);
		tela.atualizaTela();
	}

	@Override
	public void registrarAtuador(Atuador atuador) throws RemoteException {
		validaSeJaRegistrado(atuadores, atuador);
		atuadores.add(atuador);
		System.out.println("Registrando = " + atuador);
		tela.atualizaTela();
	}

	@Override
	public Integer obtemValorAtribuido(Sensor sensor) throws RemoteException {
		int posicao = sensores.indexOf(sensor);
		if (posicao < 0) {
			throw new RemoteException("Sensor não encontrado para obter o valor atribuido por algo ou alguém");
		}
		Sensor alterado = sensores.get(posicao);
		Integer valorAtribuido = alterado.getValorAtribuido();
		System.out.println("obtemValorAtribuido = " + valorAtribuido);
		return valorAtribuido;
	}
	
	@Override
	public Integer obtemValorLido(Sensor sensor) throws RemoteException {
		int posicao = sensores.indexOf(sensor);
		if (posicao < 0) {
			throw new RemoteException("Sensor não encontrado para obter o valor atribuido por algo ou alguém");
		}
		Sensor alterado = sensores.get(posicao);
		Integer valor = alterado.getValorLido();
		System.out.println("obtemValorLido= " + valor);
		return valor;
	}

	@Override
	public void registrarValorLido(Sensor sensor) throws RemoteException {
		int posicao = sensores.indexOf(sensor);
		if (posicao < 0) {
			throw new RemoteException("Sensor não encontrado para atribuir um valor");
		}

		Sensor alterado = sensores.get(posicao);
		alterado.setValorLido(sensor.getValorLido());
		System.out.println("registraValorLido = " + sensor.getValorLido());
		tela.atualizaTela();
	}

	public Atuador obtemAtuador(Atuador atuador){
		int index = getAtuadores().indexOf(atuador);
		if (index >=0 ){
			return getAtuadores().get(index);
		}
		return null;
	}
	
	public void atuar(Atuador atuador) throws RemoteException {
		int index = atuadores.indexOf(atuador);
		if (index >=0){
			atuadores.set(index, atuador);
		}else{
			System.out.println("Atuador "+atuador.getNome()+" não foi encontrado ");
		}
		tela.atualizaTela();
	}

	private void validaSeJaRegistrado(List lista, SensorAtuadorComum obj) throws RemoteException {
		if (lista.contains(obj)) {
			throw new RemoteException("Já existe um " + obj.getClass().getSimpleName() + " registrado com esse nome");
		}
	}

	public static void main(String[] args) throws Exception {
		new Servidor(new IServidorTela() {

			@Override
			public void atualizaTela() {
				System.out.println("atualizaTela");
			}
		}).startServer();
	}

	public void startServer() throws Exception {
		IServidor server = new Servidor(tela);
		LocateRegistry.createRegistry(1099);
		Naming.rebind("iot", server);
	}

	public List<Atuador> getAtuadores() {
		return atuadores;
	}

	public List<Sensor> getSensores() {
		return sensores;
	}
}
