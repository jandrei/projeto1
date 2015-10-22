package comum;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import engine.Atuador;
import engine.Sensor;

/**
 *Interface com os metodos que serão visiveis pelos sensores e atuadores para interagir com o servidor 
 *
 */
public interface IServidor extends Remote {
	/**
	 * serve para registrar {@link Sensor} no servidor de monitoramento
	 * 
	 * @param sensor
	 * @throws RemoteException
	 */
	public void registrarSensor(Sensor sensor) throws RemoteException;

	/**
	 * serve para registrar {@link Atuador} no servidor de monitoramento
	 * 
	 * @param atuador
	 * @throws RemoteException
	 */
	public void registrarAtuador(Atuador atuador) throws RemoteException;

	/**
	 * o sensor informara por meio desse método ao servidor o valor que ele esta
	 * gerando.
	 * se um id for informado e não existir no servidor, uma exceção será
	 * lançada.
	 * 
	 * @param ID
	 * @param valor
	 * @throws RemoteException
	 */
	public void registrarValorLido(Sensor sensor) throws RemoteException;

	public String obtemValorLido(Sensor sensor) throws RemoteException;
	/**
	 * deve receber o atuador com o valor que ele deseja setar em sensores de
	 * mesmo tipo e localização.
	 * 
	 * @param atuador
	 * @throws RemoteException
	 */
	public void atuar(Atuador atuador) throws RemoteException;

	public List<Atuador> getAtuadores() throws RemoteException;

	public List<Sensor> getSensores() throws RemoteException;
	
	public Atuador obtemAtuador(Atuador atuador) throws RemoteException;
	

}
