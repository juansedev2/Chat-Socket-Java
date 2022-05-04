package com.myServidor;

// Librerias necesarias
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Servidor {

	// Atributos del servidor
	private Socket socket1 = null, socket2 = null, socket3 = null, socket4 = null; // Conexión con otro programa																									// programa
	private ServerSocket server = null; // Propio de un servidor para actuar como el y para recibir conexión
	private DataInputStream input1 = null, input2 = null, input3 = null, input4 = null; // Variable para recibir  mensajes del cliente
	private DataOutputStream output1 = null, output2 = null, output3 = null, output4 = null; // Variable para enviar  mensajes al cliente
	private ArrayList<Datos> datosclient = new ArrayList<Datos>(); // Arreglo para ir guardando los clientes (INDEXACIÓN)

	public Servidor(int port) { // Puerto destinado para la comuncacion será de 5000

		// Varibles de control
		boolean salida = false; // Para cortar comunicación
		String recibe = ""; // Captura mensajes del cliente que se RECIBEN
		String envia = ""; // Captura mensajes para ENVIAR al cliente
		int total = 0; // Llevar la cuenta de cuantos clientes se han conectado
		Datos datos1 = new Datos(); // Para primer cliente
		Datos datos2 = new Datos(); // Para segundo cliente
		Datos datos3 = new Datos(); // Para tercer cliente
		Datos datos4 = new Datos(); // Para cuarto cliente

		// Iniciar la conexión con los clientes
		try {

			// Servidor
			// ServerSocket clase que recibe un cliente por el puerto indicado
			System.out.println("Soy el servidor");
			System.out.println("Iniciando..-");
			System.out.println("");
			server = new ServerSocket(port); // Para el primer cliente
			
			// Primer cliente
			System.out.println("Esperando cliente...");
			socket1 = server.accept(); // Esperando conexion con el primer cliente
			total++; // 1
			System.out.println("Un cliente se ha conectado");
			
			// Para recibir mensajes de la otra parte (cliente/servidor)
			input1 = new DataInputStream(socket1.getInputStream());
			// Para enviar mensajes a la otra parte
			output1 = new DataOutputStream(socket1.getOutputStream());
			
			// Como ya hay un cliente, debo hacer la operación de distinguir entre ellos en sus opciones, se entiende más en cliente
			// Enviar mensaje a cliente con identificador 1:
			// Envío = 1 Recibir = 2
			envia = "1";
			output1.writeUTF(envia); // Envío de mensaje (G-1)
			envia = "Hola y bienvenido a la sala de chat"; 
			output1.writeUTF(envia);// Envío de mensaje (1-2)
			envia = "Obteniendo tu nombre y tu ip para unirte a la sala de chat, no te preocupes, tu ip es tratado como privado";
			output1.writeUTF(envia); // Envío de mensaje (1-3)
			recibe = input1.readUTF(); // Obtencion de mensaje (2-1) // Nombre del cliente
			System.out.println("");
			System.out.println("Nombre del cliente: " + recibe);
			datos1.setNombre(recibe);
			recibe = input1.readUTF(); // Obtencion de mensaje (2-2) // Dirrecion ip
			System.out.println("Direccion ip de ese cliente: " + recibe);
			//System.out.println("Numero de caracteres de esa ip: " + recibe.length()); 
			datos1.setIp(recibe);
			datos1.setEstado("Disponible");
			datosclient.add(datos1); // Añadir ese cliente a la lista
			envia = "Eres el primero en llegar, por favor esperemos mientras se una más gente...";
			output1.writeUTF(envia); // Envío de mensaje (1-4)
			
			
			// Segundo cliente
			System.out.println("");
			System.out.println("Esperando otro cliente...");
			socket2 = server.accept();
			total++; // 2
			System.out.println("Un segundo cliente se ha conectado...");
			// Para recibir mensajes de la otra parte (cliente/servidor)
			input2 = new DataInputStream(socket2.getInputStream());
			// Para enviar mensajes a la otra parte
			output2 = new DataOutputStream(socket2.getOutputStream());
			envia = "2";
			output2.writeUTF(envia); // Envío de mensaje (G-1)
			envia = "Hola y bienvenido a la sala de chat"; 
			output2.writeUTF(envia);// Envío de mensaje (1-2)
			envia = "Obteniendo tu nombre y tu ip para unirte a la sala de chat, no te preocupes, tu ip es tratado como privado";
			output2.writeUTF(envia); // Envío de mensaje (1-3)
			recibe = input2.readUTF(); // Obtencion de mensaje (2-1) // Nombre del cliente
			System.out.println("");
			System.out.println("Nombre del cliente: " + recibe);
			datos2.setNombre(recibe);
			recibe = input2.readUTF(); // Obtencion de mensaje (2-2) // Dirrecion ip
			System.out.println("Direccion ip de ese cliente: " + recibe);
			//System.out.println("Numero de caracteres de esa ip: " + recibe.length()); 
			datos2.setIp(recibe);
			datos2.setEstado("Disponible");
			datosclient.add(datos2); // Añadir ese cliente a la lista
			envia = "Eres el segundo en llegar, por favor esperemos mientras se una más gente...";
			output2.writeUTF(envia); // Envío de mensaje (1-4)
			
			// Tercer cliente
			System.out.println("");
			System.out.println("Esperando otro cliente...");
			socket3 = server.accept();
			total++; // 3
			System.out.println("Un tercer cliente se ha conectado...");
			// Para recibir mensajes de la otra parte (cliente/servidor)
			input3 = new DataInputStream(socket3.getInputStream());
			// Para enviar mensajes a la otra parte
			output3 = new DataOutputStream(socket3.getOutputStream());
			envia = "3";
			output3.writeUTF(envia); // Envío de mensaje (G-1)
			envia = "Hola y bienvenido a la sala de chat"; 
			output3.writeUTF(envia);// Envío de mensaje (1-2)
			envia = "Obteniendo tu nombre y tu ip para unirte a la sala de chat, no te preocupes, tu ip es tratado como privado";
			output3.writeUTF(envia); // Envío de mensaje (1-3)
			recibe = input3.readUTF(); // Obtencion de mensaje (2-1) // Nombre del cliente
			System.out.println("");
			System.out.println("Nombre del cliente: " + recibe);
			datos3.setNombre(recibe);
			recibe = input3.readUTF(); // Obtencion de mensaje (2-2) // Dirrecion ip
			System.out.println("Direccion ip de ese cliente: " + recibe);
			//System.out.println("Numero de caracteres de esa ip: " + recibe.length()); 
			datos3.setIp(recibe);
			datos3.setEstado("Disponible");
			datosclient.add(datos3); // Añadir ese cliente a la lista
			envia = "Eres el segundo en llegar, por favor esperemos mientras se una más gente...";
			output3.writeUTF(envia); // Envío de mensaje (1-4)
			
			// Cuarto cliente
			System.out.println("");
			System.out.println("Esperando otro cliente...");
			socket4 = server.accept();
			total++; // 4
			System.out.println("Un cuarto cliente se ha conectado...");
			// Para recibir mensajes de la otra parte (cliente/servidor)
			input4 = new DataInputStream(socket4.getInputStream());
			// Para enviar mensajes a la otra parte
			output4 = new DataOutputStream(socket4.getOutputStream());
			envia = "4";
			output4.writeUTF(envia); // Envío de mensaje (G-1)
			envia = "Hola y bienvenido a la sala de chat"; 
			output4.writeUTF(envia);// Envío de mensaje (1-2)
			envia = "Obteniendo tu nombre y tu ip para unirte a la sala de chat, no te preocupes, tu ip es tratado como privado";
			output4.writeUTF(envia); // Envío de mensaje (1-3)
			recibe = input4.readUTF(); // Obtencion de mensaje (2-1) // Nombre del cliente
			System.out.println("");
			System.out.println("Nombre del cliente: " + recibe);
			datos4.setNombre(recibe);
			recibe = input4.readUTF(); // Obtencion de mensaje (2-2) // Dirrecion ip
			System.out.println("Direccion ip de ese cliente: " + recibe);
			//System.out.println("Numero de caracteres de esa ip: " + recibe.length()); 
			datos4.setIp(recibe);
			datos4.setEstado("Disponible");
			datosclient.add(datos4); // Añadir ese cliente a la lista
			envia = "Eres el segundo en llegar, por favor esperemos mientras se una más gente...";
			output4.writeUTF(envia); // Envío de mensaje (1-4)
			
			// Después de haber recibido clientes, el procesos se acerca a su fin...
			
			// Mostrar la indexación de los clientes para revisar que todo esté correcto y enviarselos a ellos como corresponde
			System.out.println("Comprobación de indexación...");
			System.out.println("");
			System.out.println("=========================================================");
			int num = 1;
			for(Datos daticos : datosclient) {
				System.out.println("Cliente número " + num + ":");
				System.out.println("");
				System.out.println("Nombre: " + daticos.getNombre() + " con ip " + daticos.getIp() + " y estado: " + daticos.getEstado());
				System.out.println("=========================================================");	
				num++;
			}
		
			// Enviar datos a los clientes para que puedan chatear entre si...
			
			// CLIENTE 1 - activo para buscar
			envia = "Seguir";
			output1.writeUTF(envia); // Envío de mensaje (1-5) 
			
			// Saber cuantos clientes hay, SE QUE ES OBVIO; pero es por tema de programación a prueba de errores
			int tam = datosclient.size(); // Tamaño del arreglo, en teoría el número de clientes (4)
			envia = String.valueOf(tam); // Hacer conversion de entero a String
			System.out.println("El valor de envía es: " + envia); // Para saber si funciona
			
			output1.writeUTF(envia); // Envío de mensaje (1-6)
			
			String laip = "";
			String elnom = "";
			String esta = "";
			
			for(Datos daticos : datosclient) { // ESTO SERÍA ENVIADO UN TOTAL DE CUATRO VECES
				elnom = daticos.getNombre();
				envia = elnom;
				output1.writeUTF(envia); // Envío de mensaje (1-7)
				laip = daticos.getIp();
				envia = laip;
			    output1.writeUTF(envia); // Envío de mensaje (1-8)
				esta = daticos.getEstado();
				envia = esta;
				output1.writeUTF(envia); // Envío de mensaje (1-9)			
			}
			
			// Finaliza conexión con el servidor porque ya no lo necesita... (OJO ESTO ES TEORÍA Y EXPERIMENTAL)
			// Pienso que puede ser necesario obtener el tema de puertos, no se porque algo me dice eso, si fuera el caso, vengo acá y lo arreglo
			try {
				socket1.close(); // Cerrar conexión con el cliente 1
				input1.close();
				output1.close();
				
			}catch(IOException e) {
				System.out.println("Hubo un error al tratar de cerrar la conexión con el primer cliente");
				System.out.println(e);
			}
			
			// CLIENTE 2 - 
			envia = "Seguir";
			output2.writeUTF(envia); // Envío de mensaje (1-5) 
						
			// Saber cuantos clientes hay, SE QUE ES OBVIO; pero es por tema de programación a prueba de errores
			int tam2 = datosclient.size(); // Tamaño del arreglo, en teoría el número de clientes (4)
			envia = String.valueOf(tam2); // Hacer conversion de entero a String
			System.out.println("El valor de envía es: " + envia); // Para saber si funciona
				
			output2.writeUTF(envia); // Envío de mensaje (1-6)
						
			String laip2 = "";
			String elnom2 = "";
			String esta2 = "";
						
			for(Datos daticos : datosclient) { // ESTO SERÍA ENVIADO UN TOTAL DE CUATRO VECES
				elnom2 = daticos.getNombre();
				envia = elnom2;
				output2.writeUTF(envia); // Envío de mensaje (1-7)
				laip2 = daticos.getIp();
				envia = laip2;
				output2.writeUTF(envia); // Envío de mensaje (1-8)
			    esta2 = daticos.getEstado();
				envia = esta2;
				output2.writeUTF(envia); // Envío de mensaje (1-9)			
			}
						
			// Finaliza conexión con el servidor porque ya no lo necesita... (OJO ESTO ES TEORÍA Y EXPERIMENTAL)
			// Pienso que puede ser necesario obtener el tema de puertos, no se porque algo me dice eso, si fuera el caso, vengo acá y lo arreglo
			try {
				socket2.close(); // Cerrar conexión con el cliente 12
				input2.close();
				output2.close();
							
		    }catch(IOException e) {
			System.out.println("Hubo un error al tratar de cerrar la conexión con el segundo cliente");
			System.out.println(e);
			}
			
			
			// CLIENTE 3 - 
			envia = "Seguir";
			output3.writeUTF(envia); // Envío de mensaje (1-5) 
									
			// Saber cuantos clientes hay, SE QUE ES OBVIO; pero es por tema de programación a prueba de errores
		    int tam3 = datosclient.size(); // Tamaño del arreglo, en teoría el número de clientes (4)
			envia = String.valueOf(tam3); // Hacer conversion de entero a String
			System.out.println("El valor de envía es: " + envia); // Para saber si funciona
							
			output3.writeUTF(envia); // Envío de mensaje (1-6)
									
			String laip3 = "";
			String elnom3 = "";
			String esta3 = "";
									
			for(Datos daticos : datosclient) { // ESTO SERÍA ENVIADO UN TOTAL DE CUATRO VECES
				elnom3 = daticos.getNombre();
				envia = elnom3;
				output3.writeUTF(envia); // Envío de mensaje (1-7)
				laip3 = daticos.getIp();
				envia = laip3;
				output3.writeUTF(envia); // Envío de mensaje (1-8)
				esta3 = daticos.getEstado();
				envia = esta3;
				output3.writeUTF(envia); // Envío de mensaje (1-9)			
			}
									
			// Finaliza conexión con el servidor porque ya no lo necesita... (OJO ESTO ES TEORÍA Y EXPERIMENTAL)
			// Pienso que puede ser necesario obtener el tema de puertos, no se porque algo me dice eso, si fuera el caso, vengo acá y lo arreglo
			try {
				socket3.close(); // Cerrar conexión con el cliente 3
				input3.close();
				output3.close();
										
			}catch(IOException e) {
			    System.out.println("Hubo un error al tratar de cerrar la conexión con el tercer cliente");
				System.out.println(e);
			}
			
			// CLIENTE 4 - 
			envia = "Seguir";
			output4.writeUTF(envia); // Envío de mensaje (1-5) 
												
			// Saber cuantos clientes hay, SE QUE ES OBVIO; pero es por tema de programación a prueba de errores
			int tam4 = datosclient.size(); // Tamaño del arreglo, en teoría el número de clientes (4)
			envia = String.valueOf(tam4); // Hacer conversion de entero a String
			System.out.println("El valor de envía es: " + envia); // Para saber si funciona
										
			output4.writeUTF(envia); // Envío de mensaje (1-6)
												
			String laip4 = "";
			String elnom4 = "";
			String esta4 = "";
												
			for(Datos daticos : datosclient) { // ESTO SERÍA ENVIADO UN TOTAL DE CUATRO VECES
				elnom4 = daticos.getNombre();
				envia = elnom4;
				output4.writeUTF(envia); // Envío de mensaje (1-7)
				laip4 = daticos.getIp();
				envia = laip4;
				output4.writeUTF(envia); // Envío de mensaje (1-8)
				esta4 = daticos.getEstado();
				envia = esta4;
				output4.writeUTF(envia); // Envío de mensaje (1-9)			
			}
												
			// Finaliza conexión con el servidor porque ya no lo necesita... (OJO ESTO ES TEORÍA Y EXPERIMENTAL)
			// Pienso que puede ser necesario obtener el tema de puertos, no se porque algo me dice eso, si fuera el caso, vengo acá y lo arreglo
			try {
				socket4.close(); // Cerrar conexión con el cliente 4
				input4.close();
				output4.close();
													
			}catch(IOException e) {
			    System.out.println("Hubo un error al tratar de cerrar la conexión con el cuarto cliente");
				System.out.println(e);
			}
								
			// EL SERVIDOR VA A FINALIZAR, fin de proceso de enviar los mensajes....
			System.out.println("El servidor, al igual que el meme de Mi momento ha llegado, finaliza su trabajo aquí...");
			System.out.println("Apagando...");
						

		} catch (IOException i) {
			System.out.println("Error...");
			System.out.println(i);

		}

	}

	public static void main(String[] args) {
		
		Servidor servidor = new Servidor (5000);

	}

}