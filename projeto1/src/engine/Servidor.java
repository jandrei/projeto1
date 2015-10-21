package engine;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

import comum.IServidor;
import comum.IServidorTela;

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
		sensor.check();
		validaSeJaRegistrado(sensores, sensor);
		sensores.add(sensor);
		System.out.println("Registrando = " + sensor);
		tela.atualizaSensores();
	}

	@Override
	public void registrarAtuador(Atuador atuador) throws RemoteException {
		atuador.check();
		validaSeJaRegistrado(atuadores, atuador);
		atuadores.add(atuador);
		System.out.println("Registrando = " + atuador);
		tela.atualizaAtuadores();
	}

	
	
	@Override
	public String obtemValorLido(Sensor sensor) throws RemoteException {
		int posicao = sensores.indexOf(sensor);
		if (posicao < 0) {
			throw new RemoteException("Sensor não encontrado para obter o valor atribuido por algo ou alguém");
		}
		Sensor alterado = sensores.get(posicao);
		String valor = alterado.getValorLido();
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
		tela.atualizaSensores();
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
	}

	private void validaSeJaRegistrado(List lista, SensorAtuadorComum obj) throws RemoteException {
		if (lista.contains(obj)) {
			throw new RemoteException("Já existe um " + obj.getClass().getSimpleName() + " registrado com esse nome");
		}
	}

	public static void mainasd(String[] args) throws Exception {
		new Servidor(new IServidorTela() {

			@Override
			public void atualizaAtuadores() {
				System.out.println("atualizaAtuadores");
			}
			@Override
			public void atualizaSensores() {
				System.out.println("Atualiza sensores");
				
			}
		}).startServer();
	}
	

	public void startServer() throws Exception {
		IServidor server = new Servidor(tela);
		LocateRegistry.createRegistry(Constantes.portaServidor);
		Naming.rebind(Constantes.nomeServidor, server);
	}

	public List<Atuador> getAtuadores() {
		return atuadores;
	}

	public List<Sensor> getSensores() {
		return sensores;
	}
}
