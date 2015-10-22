package engine;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

import comum.IServidor;
import comum.IServidorTela;

/**
 *
 * Gerencia todas instancias de atuadores e sensores registradas e guardando ou enviando valores.
 *
 */
public class Servidor extends UnicastRemoteObject implements IServidor {
	private static final long serialVersionUID = -656243459860317218L;

	//local onte serão armazenadas as intancias de atuadores e sensores.
	private List<Sensor> sensores = new ArrayList<Sensor>();
	private List<Atuador> atuadores = new ArrayList<Atuador>();

	IServidorTela tela;
	

	/**
	 * recebe uma interface para avisar algum outro componente quando uma ação no servidor foi 
	 * executada.
	 * @param tela
	 * @throws RemoteException
	 */
	public Servidor(IServidorTela tela) throws RemoteException {
		super();
		this.tela = tela;
		
	}

	@Override
	public void registrarSensor(Sensor sensor) throws RemoteException {
		//chama as validações do objeto
		sensor.check();
		//nao permite dublicar objetos
		validaSeJaRegistrado(sensores, sensor);
		//adiciona na lista
		sensores.add(sensor);
		System.out.println("Registrando = " + sensor);
		//chama método da interface para avisar que ouve alterações nos sensores
		tela.atualizaSensores();
	}

	
	@Override
	public void registrarAtuador(Atuador atuador) throws RemoteException {
		//chama validação do objeto
		atuador.check();
		//nao permite duplicações de objetos
		validaSeJaRegistrado(atuadores, atuador);
		//adiciona o objeto
		atuadores.add(atuador);
		System.out.println("Registrando = " + atuador);
		//avisa o objeto que esta interagindo com o servidor que ouve alterações nos sensores.
		tela.atualizaAtuadores();
	}

	
	
	@Override
	public String obtemValorLido(Sensor sensor) throws RemoteException {
		//identifica se existe o sensor buscado
		int posicao = sensores.indexOf(sensor);
		if (posicao < 0) {
			throw new RemoteException("Sensor não encontrado para obter o valor atribuido por algo ou alguém");
		}
		//obtem o sensor da lista
		Sensor alterado = sensores.get(posicao);
		//le o valor
		String valor = alterado.getValorLido();
		System.out.println("obtemValorLido= " + valor);
		//retorna o valor armazenado no servidor
		return valor;
	}

	@Override
	public void registrarValorLido(Sensor sensor) throws RemoteException {
		//verifica se o sensor foi registrado e existe na lista
		int posicao = sensores.indexOf(sensor);
		if (posicao < 0) {
			throw new RemoteException("Sensor não encontrado para atribuir um valor");
		}
		//ontem o sensor
		Sensor alterado = sensores.get(posicao);
		//registra o valor novo
		alterado.setValorLido(sensor.getValorLido());
		System.out.println("registraValorLido = " + sensor.getValorLido());
		//avisa a tela que valores novos chegaram
		tela.atualizaSensores();
	}

	/**
	 * retorna um atuador se existe
	 */
	public Atuador obtemAtuador(Atuador atuador){
		int index = getAtuadores().indexOf(atuador);
		if (index >=0 ){
			return getAtuadores().get(index);
		}
		return null;
	}
	
	/**
	 * altera o valor de um atuador no servidor, permitindo que alguem acessando ao servidor
	 * obtenha essa informação armazenada no servidor.
	 */
	public void atuar(Atuador atuador) throws RemoteException {
		int index = atuadores.indexOf(atuador);
		if (index >=0){
			atuadores.set(index, atuador);
		}else{
			System.out.println("Atuador "+atuador.getNome()+" não foi encontrado ");
		}
	}

	/**
	 * nao permite incluir objetos com mesmo nome nas listas
	 * @param lista
	 * @param obj
	 * @throws RemoteException
	 */
	private void validaSeJaRegistrado(List lista, SensorAtuadorComum obj) throws RemoteException {
		if (lista.contains(obj)) {
			throw new RemoteException("Já existe um " + obj.getClass().getSimpleName() + " registrado com esse nome");
		}
	}

	/**
	 * inicia o servidor
	 * @throws Exception
	 */
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
