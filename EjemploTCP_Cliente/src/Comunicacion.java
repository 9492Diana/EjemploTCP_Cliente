import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class Comunicacion extends Thread {

	int puerto;
	Socket servidor;

	public Comunicacion(int i) {
		puerto = i;

		try {
			servidor = new Socket(InetAddress.getByName("127.0.0.1"), puerto);
			System.out.println("exito!");
		} catch (UnknownHostException uhe) {
			uhe.printStackTrace();
		} catch (IOException io) {
			io.printStackTrace();
		}
	}

	@Override
	public void run() {
		while (true) {
			//recibirAvisos();
			try {
				enviar("hola amigo UTF Servidor", 0);
				sleep(1000);
				enviar("hola amigo Objeto Servidor", 1);
				sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}

	private void recibirAvisos() {
		// TODO algun protocolo de avisos
	}

	private void enviarAviso(String msj) {
		OutputStream salidaBytes;
		DataOutputStream salidaDatos;
		try {
			salidaBytes = servidor.getOutputStream();
			salidaDatos = new DataOutputStream(salidaBytes);
			salidaDatos.writeUTF(msj);
			System.out.println("mensaje enviado: "  + msj );
			salidaDatos.flush();			
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void enviar(Object datos, int i) {
		if (datos instanceof String) {
			String temp = (String) datos;

			switch (i) {
			case 0:
				try {
					enviarAviso("texto");
					OutputStream salidaByte = servidor.getOutputStream();
					DataOutputStream salidaDatos = new DataOutputStream(
							salidaByte);					
					System.out.println("envio aviso texto");
					salidaDatos.writeUTF(""+temp);
					salidaDatos.flush();
				} catch (IOException e) {
					e.printStackTrace();
				}

				break;
			case 1:
				try {
					enviarAviso("objeto");
					OutputStream salidaByte = servidor.getOutputStream();
					ObjectOutputStream salidaObjetos = new ObjectOutputStream(
							salidaByte);					
					System.out.println("envio aviso objeto");
					salidaObjetos.writeObject(datos);
					salidaObjetos.flush();
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
			}
		}
	}
}
