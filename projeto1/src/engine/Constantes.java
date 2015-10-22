package engine;

/**
 *
 * Classe utilizada pelo sistema para armazenar as informações de porta
 * nome do servidor
 * e endereço do servidor
 * facilitando a parametrização desse.
 *
 */
public final class Constantes {
	public static int portaServidor = 1099;
	public static String nomeServidor = "iot";
	public static String enderecoServidor = "localhost";

	public static String getNamingLokupServer(){
		return String.format("rmi://%s:%d/%s", enderecoServidor, portaServidor, nomeServidor);
	} 
	
}
