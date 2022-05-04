package com.myCliente;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Scanner;

public class Cliente {
	// Atributos del cliente
	// Variables socket del cliente de entrada y salida de datos
	private Socket socketS = null, socket1 = null, socket2 = null, socket3 = null, socket4 = null, socketV = null; // Conexión con otro programa, cada número hace referencia a un cliente en orden descendente
	private DataInputStream input = null, inputV = null; // Envío de mensajes
	private DataOutputStream output = null, outputV = null;; // Recibir mensajes
	private ArrayList<String>listips = new ArrayList <String>(); // Cada cliente tendra en secreto las ips de los clientes para su conexion

	public Cliente(String address, int port) throws UnknownHostException {

		// Varibales de control
		Scanner scn = new Scanner(System.in); // Solo para nombres
		Scanner d = new Scanner(System.in); // Solo para números
		boolean salidadef = false; // Salida final del programa
		String nombre;
		String ip;
		String envia = ""; // Captura mensajes de este cliente para enviarlos
		String recibe = ""; // Captura mensajes del receptor para mostrarlos
		InetAddress direccion = InetAddress.getLocalHost(); // Clase para obtener datos de red

		// Presentacion del cliente
		System.out.println("Digita tu nombre");
		nombre = scn.next();
		ip = direccion.getHostAddress();
		System.out.println("Tu direccion ip es: " + ip);

		try {
			
			// SERVIDOR
			
			// Intentar conexión con el servidor
			socketS = new Socket(address, port); // Hacer conexion con el servidor/cliente
			// Para recibir mensajes de la otra parte (cliente/servidor)
			input = new DataInputStream(socketS.getInputStream());
			// Para enviar mensajes a la otra parte
			output = new DataOutputStream(socketS.getOutputStream());
			
			// Recibir mensajes del servidor para pape de cliente --->
			recibe = input.readUTF(); //  (G-1)
			
			// PRIMER CLIENTE
			if (recibe.equals("1")) {
				System.out.println("");
				System.out.println("Cliente 1");
				System.out.println("");
				
				// Recibe mensajes del servidor
				recibe = input.readUTF(); //  (1-2)
				System.out.println(recibe);
				recibe = input.readUTF(); // (1-3)
				System.out.println(recibe);
						
				// Envío de nensajes al sevidor
				envia = nombre; 
			    output.writeUTF(envia); // Nombre del cliente // (2-1)
			    envia = ip; 
			    output.writeUTF(envia); // Ip del cliente // (2-2)
			    
			    // Recibe mensajes del servidor
			    recibe = input.readUTF();
			    System.out.println(recibe); // (1-4)
			    
			    boolean salida1 = false;
			    while(salida1 == false) {
			    	System.out.println("Esperando a un cliente para que se conecte...");
			    	recibe = input.readUTF(); // ESPERANDO CONFIRMACION PARA HABLAR CON OTRO.... (1-5)
			    	if(recibe.equals("Seguir")) {
			    		salida1 = true;
			    	}
			    }
			    
			   System.out.println("Recibiendo mensaje del servidor (Lista de personas para chatear)...");
			   recibe = input.readUTF(); // (1-6)
			   System.out.println("Hay" + recibe + " personas en el chat (contandote a ti)");
			   
			   if(recibe.equals("4") ) { // Esto es para comprobar errores
				   System.out.println("Correcto...");
			   }else {
				   System.out.println("Esto aqui no funcionara...");
			   }
			   System.out.println("=========================================================");
			   			   
			   for(int i = 0; i < 4; i++ ) {			   
				   recibe = input.readUTF();// (1-7) Nombres de los clientes
				   System.out.println((i+1) + ". Nombre:  " + recibe );
				   recibe = input.readUTF();// (1-8) Ips de los clientes
				   listips.add(recibe);
				   recibe = input.readUTF();// (1-9) Estado de ese cliente
				   System.out.println("Estado: " + recibe);
				   System.out.println("=========================================================");			   
			   }
			   
			   System.out.println("");
			   System.out.println("Selecciona el cliente con el que quieres hablar.");
			   System.out.println("Por supuesto, no se seleccione a usted mismo, no quiere provocar un error en su host");
			   System.out.println("O si lo prefiere, puede esperar a que alguien se conecte con usted automáticamente, para eso, digite el número 5...");
			   System.out.println("");
			   int de = d.nextInt();
			   if(de == 1) { // Con el primero
				   System.out.println("Se le advirtió que no lo hiciera...");
				   System.out.println("ERROR DEL SISTEMA, RESPUESTA DEL CLIENTE NO APROPAIDA");
				   System.out.println("SALIENDO DEL SERVIDOR....");
			   }else if(de == 2) { // Con el segundo
				   int conta = 0;
				   for(String ips: listips) {
					   if(conta == 1) { // HABLA CON CLIENTE 2 PERO ESTO ES ASÍ PORQUE LOS ARRAYLIST EMPIEZAN DESDE CERO TAMBIÉN
						   String theip = listips.get(conta);
						   try {
							   // Cerrar conexión con el servidor para liberar el puerto
							   socketS.close();
							   input.close();
							   output.close();
							   // Buscar chat con cliente 2...
							   try {
								   socket2 = new Socket(theip, port); // Hacer conexion con el cliente 2
								   System.out.println("La conexión ha sido un éxito");
								   // Para recibir mensajes de la otra parte (cliente/servidor)
									inputV = new DataInputStream(socket2.getInputStream());
								   // Para enviar mensajes a la otra parte
									outputV = new DataOutputStream(socket2.getOutputStream());
									boolean salida = false;
									System.out.println("");
									System.out.println("---------------------------------");
									System.out.println("¡EL CHAT A INICIADO!");
									System.out.println("---------------------------------");
									System.out.println("Envía un mensaje");
									System.out.println("---------------------------------");
									System.out.println("");
									while(salida == false) {
										envia = scn.nextLine(); // Escribir mensaje
										outputV.writeUTF(envia); // Enviar mensaje
										recibe = inputV.readUTF(); // Recibir mensaje
										System.out.println(recibe); // Mostrar el mesaje
										if(recibe.equals("FINALIZAR")) {										
											salida = true;
											envia = ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"; 
											outputV.writeUTF(envia); //(F-1)
											envia = "La persona ha finalizado el chat";
											outputV.writeUTF(envia); //(F-2)
											envia = "<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<";
											outputV.writeUTF(envia); //(F-3)
											System.out.println("ESTE CHAT SE HA TERMINADO");
										}
									}
									System.out.println("Cerrando conexiones...");
									// Cerrando conexiones
									try {
										
										socket2.close();
										inputV.close();
										outputV.close();
										
									}catch(IOException i) {
										System.out.println("Hubo un error cerrando conexiones con cliente 2");
										   System.out.println(i);
										
									}
								   
							   }catch(IOException i) {
								   System.out.println("Hubo un error conectando con cliente 2:");
								   System.out.println(i);
								   
							   }
							   
						   }catch(IOException i) {
							   System.out.println("Hubo un error:");
							   System.out.println(i);
						   }
						   break; // Para que ya no recorra más el arreglo
					   }
					   conta++;
				   }
				   
			   }else if(de == 3 ) { // Con el tercero
				   int conta = 0;
				   for(String ips: listips) {
					   if(conta == 2) { // HABLA CON CLIENTE 3 PERO ESTO ES ASÍ PORQUE LOS ARRAYLIST EMPIEZAN DESDE CERO TAMBIÉN
						   String theip = listips.get(conta);
						   try {
							   // Cerrar conexión con el servidor para liberar el puerto
							   socketS.close();
							   input.close();
							   output.close();
							   // Buscar chat con cliente 
							   try {
								   socket3 = new Socket(theip, port); // Hacer conexion con el cliente 2
								   System.out.println("La conexión ha sido un éxito");
								   // Para recibir mensajes de la otra parte (cliente/servidor)
									inputV = new DataInputStream(socket3.getInputStream());
								   // Para enviar mensajes a la otra parte
									outputV = new DataOutputStream(socket3.getOutputStream());
									boolean salida = false;
									System.out.println("");
									System.out.println("---------------------------------");
									System.out.println("¡EL CHAT A INICIADO!");
									System.out.println("---------------------------------");
									System.out.println("Envía un mensaje");
									System.out.println("---------------------------------");
									System.out.println("");
									while(salida == false) {
										envia = scn.nextLine(); // Escribir mensaje
										outputV.writeUTF(envia); // Enviar mensaje
										recibe = inputV.readUTF(); // Recibir mensaje
										System.out.println(recibe); // Mostrar el mesaje
										if(recibe.equals("FINALIZAR")) {										
											salida = true;
											envia = ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>";
											outputV.writeUTF(envia);
											envia = "La persona ha finalizado el chat";
											outputV.writeUTF(envia);
											envia = "<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<";
											outputV.writeUTF(envia);
											System.out.println("ESTE CHAT SE HA TERMINADO");
										}
									}
									System.out.println("Cerrando conexiones...");
									// Cerrando conexiones
									try {
										
										socket3.close();
										inputV.close();
										outputV.close();
										
									}catch(IOException i) {
										System.out.println("Hubo un error cerrando conexiones con cliente 3");
										   System.out.println(i);
										
									}
								   
							   }catch(IOException i) {
								   System.out.println("Hubo un error conectando con cliente 3:");
								   System.out.println(i);
								   
							   }
							   
						   }catch(IOException i) {
							   System.out.println("Hubo un error:");
							   System.out.println(i);
						   }
						   break; // Para que ya no recorra más el arreglo
					   }
					   conta++;
				   }
				   
				   
			   }else if(de == 4) { // Con el cuarto
				   int conta = 0;
				   for(String ips: listips) {
					   if(conta == 3) { // HABLA CON CLIENTE 4 PERO ESTO ES ASÍ PORQUE LOS ARRAYLIST EMPIEZAN DESDE CERO TAMBIÉN
						   String theip = listips.get(conta);
						   try {
							   // Cerrar conexión con el servidor para liberar el puerto
							   socketS.close();
							   input.close();
							   output.close();
							   // Buscar chat con cliente 	
							   try {
								   socket4 = new Socket(theip, port); // Hacer conexion con el cliente 2
								   System.out.println("La conexión ha sido un éxito");
								   // Para recibir mensajes de la otra parte (cliente/servidor)
									inputV = new DataInputStream(socket4.getInputStream());
								   // Para enviar mensajes a la otra parte
									outputV = new DataOutputStream(socket4.getOutputStream());
									boolean salida = false;
									System.out.println("");
									System.out.println("---------------------------------");
									System.out.println("¡EL CHAT A INICIADO!");
									System.out.println("---------------------------------");
									System.out.println("Envía un mensaje");
									System.out.println("---------------------------------");
									System.out.println("");
									while(salida == false) {
										envia = scn.nextLine(); // Escribir mensaje
										outputV.writeUTF(envia); // Enviar mensaje
										recibe = inputV.readUTF(); // Recibir mensaje
										System.out.println(recibe); // Mostrar el mesaje
										if(recibe.equals("FINALIZAR")) {										
											salida = true;
											envia = ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>";
											outputV.writeUTF(envia);
											envia = "La persona ha finalizado el chat";
											outputV.writeUTF(envia);
											envia = "<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<";
											outputV.writeUTF(envia);
											System.out.println("ESTE CHAT SE HA TERMINADO");
										}
									}
									System.out.println("Cerrando conexiones...");
									// Cerrando conexiones
									try {
										
										socket4.close();
										inputV.close();
										outputV.close();
										
									}catch(IOException i) {
										System.out.println("Hubo un error cerrando conexiones con cliente 4");
										   System.out.println(i);
										
									}
									
									// Cerrando
								   
							   }catch(IOException i) {
								   System.out.println("Hubo un error conectando con cliente 4:");
								   System.out.println(i);
								   
							   }
							   
						   }catch(IOException i) {
							   System.out.println("Hubo un error:");
							   System.out.println(i);
						   }
						   break; // Para que ya no recorra más el arreglo
					   }
					   conta++;
				   }
				   
			   }else if(de == 5) { // Esperando conexion de cualquier otro
				   System.out.println("");
				   System.out.println("De acuerdo... esperando a que alguien se conecte para chatear contigo...");
				   // Cerrando puerto para disponibidlidad
				   try {				   
					   socketS.close();
					   input.close();
					   output.close();
					   System.out.println("Espera....");
					   ServerSocket conect = new ServerSocket(port);
					   socketV = conect.accept(); // Esperando a que se conecte el otro cliente (visitante)...
					   System.out.println("Conexión recibida...");
					    // Para recibir mensajes de la otra parte (cliente/servidor)
						inputV = new DataInputStream(socketV.getInputStream());
						// Para enviar mensajes a la otra parte
						outputV = new DataOutputStream(socketV.getOutputStream());
						boolean salida = false;
						System.out.println("");
						System.out.println("---------------------------------");
						System.out.println("¡EL CHAT A INICIADO!");
						System.out.println("---------------------------------");
						System.out.println("---------------------------------");
						System.out.println("");
						boolean sal = false;
						while(salida == false) { // EL ORDEN CAMBIA PORQUE EL ES QUIEN RECIBE el mensaje primero
							recibe = inputV.readUTF(); // Recibir mensaje
							System.out.println(recibe); // Mostrar el mesaje
							if(recibe.equals("FINALIZAR")) {										
								System.out.println("Este chat a finalizado");
								salida = true;
								sal = true;
							}else if(sal == false) {
								envia = scn.nextLine(); // Escribir mensaje
								outputV.writeUTF(envia); // Enviar mensaje								
							}
						}
						System.out.println("Cerrando conexiones...");
						// Cerrando conexiones
						try {						
							socketV.close();
							inputV.close();
							outputV.close();
							
						}catch(IOException i) {
							System.out.println("Hubo un error cerrando conexiones al desconectar con el cliente");
							   System.out.println(i);
							
						}
						
					   
				   }catch(IOException e) {
					   System.out.println("Ups, un error intentando cerrar la conexión con el servidor y/o en la conexión con el otro");
					   System.out.println(e);
				   }
				   
				   
			   }
			   
			}
			// SEGUNDO CLIENTE
			
			else if(recibe.equals("2")) {
				System.out.println("");
				System.out.println("Cliente 2");
				System.out.println("");
				
				// Recibe mensajes del servidor
				recibe = input.readUTF(); //  (1-2)
				System.out.println(recibe);
				recibe = input.readUTF(); // (1-3)
				System.out.println(recibe);
						
				// Envío de nensajes al sevidor
				envia = nombre; 
			    output.writeUTF(envia); // Nombre del cliente // (2-1)
			    envia = ip; 
			    output.writeUTF(envia); // Ip del cliente // (2-2)
			    
			    // Recibe mensajes del servidor
			    recibe = input.readUTF();
			    System.out.println(recibe); // (1-4)
			    
			    boolean salida2 = false;
			    while(salida2 == false) {
			    	System.out.println("Esperando a un cliente para que se conecte...");
			    	recibe = input.readUTF(); // ESPERANDO CONFIRMACIÓN PARA HABLAR CON OTRO....
			    	if(recibe.equals("Seguir")) {
			    		salida2 = true;
			    	}
			    }
				   System.out.println("Recibiendo mensaje del servidor (Lista de personas para chatear)...");
				   recibe = input.readUTF(); // (1-6)
				   System.out.println("Hay" + recibe + " personas en el chat (contandote a ti)");
				   
				   if(recibe.equals("4") ) { // Esto es para comprobar errores
					   System.out.println("Correcto...");
				   }else {
					   System.out.println("Esto aqui no funcionara...");
				   }
				   System.out.println("=========================================================");
				   			   
				   for(int i = 0; i < 4; i++ ) {			   
					   recibe = input.readUTF();// (1-7) Nombres de los clientes
					   System.out.println((i+1) + "Nombre:  " + recibe );
					   recibe = input.readUTF();// (1-8) Ips de los clientes
					   listips.add(recibe);
					   recibe = input.readUTF();// (1-9) Estado de ese cliente
					   System.out.println("Estado: " + recibe);
					   System.out.println("=========================================================");			   
				   }
				   
				   System.out.println("");
				   System.out.println("Selecciona el cliente con el que quieres hablar.");
				   System.out.println("Por supuesto, no se seleccione a usted mismo, no quiere provocar un error en su host");
				   System.out.println("O si lo prefiere, puede esperar a que alguien se conecte con usted automáticamente, para eso, digite el número 5...");
				   System.out.println("");
				   int de = d.nextInt();
				   if(de == 1) { // Con el primero
					   int conta = 0;
					   for(String ips: listips) {
						   if(conta == 0) { // HABLA CON CLIENTE 1 PERO ESTO ES ASÍ PORQUE LOS ARRAYLIST EMPIEZAN DESDE CERO TAMBIÉN
							   String theip = listips.get(conta);
							   try {
								   // Cerrar conexión con el servidor para liberar el puerto
								   socketS.close();
								   input.close();
								   output.close();
								   // Buscar chat con cliente 
								   try {
									   socket2 = new Socket(theip, port); // Hacer conexion con el cliente 2
									   System.out.println("La conexión ha sido un éxito");
									   // Para recibir mensajes de la otra parte (cliente/servidor)
										inputV = new DataInputStream(socket2.getInputStream());
									   // Para enviar mensajes a la otra parte
										outputV = new DataOutputStream(socket2.getOutputStream());
										boolean salida = false;
										System.out.println("");
										System.out.println("---------------------------------");
										System.out.println("¡EL CHAT A INICIADO!");
										System.out.println("---------------------------------");
										System.out.println("Envía un mensaje");
										System.out.println("---------------------------------");
										System.out.println("");
										while(salida == false) {
											envia = scn.nextLine(); // Escribir mensaje
											outputV.writeUTF(envia); // Enviar mensaje
											recibe = inputV.readUTF(); // Recibir mensaje
											System.out.println(recibe); // Mostrar el mesaje
											if(recibe.equals("FINALIZAR")) {										
												salida = true;
												envia = ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>";
												outputV.writeUTF(envia);
												envia = "La persona ha finalizado el chat";
												outputV.writeUTF(envia);
												envia = "<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<";
												outputV.writeUTF(envia);
												System.out.println("ESTE CHAT SE HA TERMINADO");
											}
										}
										System.out.println("Cerrando conexiones...");
										// Cerrando conexiones
										try {
											
											socket2.close();
											inputV.close();
											outputV.close();
											
										}catch(IOException i) {
											System.out.println("Hubo un error cerrando conexiones con cliente 2");
											   System.out.println(i);
											
										}
									   
								   }catch(IOException i) {
									   System.out.println("Hubo un error conectando con cliente 2:");
									   System.out.println(i);
									   
								   }
								   
							   }catch(IOException i) {
								   System.out.println("Hubo un error:");
								   System.out.println(i);
							   }
							   break; // Para que ya no recorra más el arreglo
						   }
						   conta++;
					   }
				   }else if(de == 2) {
					   System.out.println("Se le advirtió que no lo hiciera...");
					   System.out.println("ERROR DEL SISTEMA, RESPUESTA DEL CLIENTE NO APROPAIDA");
					   System.out.println("SALIENDO DEL SERVIDOR....");
					   
				   }else if(de == 3 ) { // Con el tercero
					   int conta = 0;
					   for(String ips: listips) {
						   if(conta == 2) { // HABLA CON CLIENTE 3 PERO ESTO ES ASÍ PORQUE LOS ARRAYLIST EMPIEZAN DESDE CERO TAMBIÉN
							   String theip = listips.get(conta);
							   try {
								   // Cerrar conexión con el servidor para liberar el puerto
								   socketS.close();
								   input.close();
								   output.close();
								   // Buscar chat con cliente 
								   try {
									   socket3 = new Socket(theip, port); // Hacer conexion con el cliente 2
									   System.out.println("La conexión ha sido un éxito");
									   // Para recibir mensajes de la otra parte (cliente/servidor)
										inputV = new DataInputStream(socket3.getInputStream());
									   // Para enviar mensajes a la otra parte
										outputV = new DataOutputStream(socket3.getOutputStream());
										boolean salida = false;
										System.out.println("");
										System.out.println("---------------------------------");
										System.out.println("¡EL CHAT A INICIADO!");
										System.out.println("---------------------------------");
										System.out.println("Envía un mensaje");
										System.out.println("---------------------------------");
										System.out.println("");
										while(salida == false) {
											envia = scn.nextLine(); // Escribir mensaje
											outputV.writeUTF(envia); // Enviar mensaje
											recibe = inputV.readUTF(); // Recibir mensaje
											System.out.println(recibe); // Mostrar el mesaje
											if(recibe.equals("FINALIZAR")) {										
												salida = true;
												envia = ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>";
												outputV.writeUTF(envia);
												envia = "La persona ha finalizado el chat";
												outputV.writeUTF(envia);
												envia = "<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<";
												outputV.writeUTF(envia);
												System.out.println("ESTE CHAT SE HA TERMINADO");
											}
										}
										System.out.println("Cerrando conexiones...");
										// Cerrando conexiones
										try {
											
											socket3.close();
											inputV.close();
											outputV.close();
											
										}catch(IOException i) {
											System.out.println("Hubo un error cerrando conexiones con cliente 3");
											   System.out.println(i);
											
										}
									   
								   }catch(IOException i) {
									   System.out.println("Hubo un error conectando con cliente 3:");
									   System.out.println(i);
									   
								   }
								   
							   }catch(IOException i) {
								   System.out.println("Hubo un error:");
								   System.out.println(i);
							   }
							   break; // Para que ya no recorra más el arreglo
						   }
						   conta++;
					   }
					   
					    
				   }else if(de == 4) { // Con el cuarto
					   int conta = 0;
					   for(String ips: listips) {
						   if(conta == 3) { // HABLA CON CLIENTE 4 PERO ESTO ES ASÍ PORQUE LOS ARRAYLIST EMPIEZAN DESDE CERO TAMBIÉN
							   String theip = listips.get(conta);
							   try {
								   // Cerrar conexión con el servidor para liberar el puerto
								   socketS.close();
								   input.close();
								   output.close();
								   // Buscar chat con cliente 2...
								   try {
									   socket4 = new Socket(theip, port); // Hacer conexion con el cliente 2
									   System.out.println("La conexión ha sido un éxito");
									   // Para recibir mensajes de la otra parte (cliente/servidor)
										inputV = new DataInputStream(socket4.getInputStream());
									   // Para enviar mensajes a la otra parte
										outputV = new DataOutputStream(socket4.getOutputStream());
										boolean salida = false;
										System.out.println("");
										System.out.println("---------------------------------");
										System.out.println("¡EL CHAT A INICIADO!");
										System.out.println("---------------------------------");
										System.out.println("Envía un mensaje");
										System.out.println("---------------------------------");
										System.out.println("");
										while(salida == false) {
											envia = scn.nextLine(); // Escribir mensaje
											outputV.writeUTF(envia); // Enviar mensaje
											recibe = inputV.readUTF(); // Recibir mensaje
											System.out.println(recibe); // Mostrar el mesaje
											if(recibe.equals("FINALIZAR")) {										
												salida = true;
												envia = ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>";
												outputV.writeUTF(envia);
												envia = "La persona ha finalizado el chat";
												outputV.writeUTF(envia);
												envia = "<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<";
												outputV.writeUTF(envia);
												System.out.println("ESTE CHAT SE HA TERMINADO");
											}
										}
										System.out.println("Cerrando conexiones...");
										// Cerrando conexiones
										try {
											
											socket4.close();
											inputV.close();
											outputV.close();
											
										}catch(IOException i) {
											System.out.println("Hubo un error cerrando conexiones con cliente 4");
											   System.out.println(i);
											
										}
										
										// Cerrando
									   
								   }catch(IOException i) {
									   System.out.println("Hubo un error conectando con cliente 4:");
									   System.out.println(i);
									   
								   }
								   
							   }catch(IOException i) {
								   System.out.println("Hubo un error:");
								   System.out.println(i);
							   }
							   break; // Para que ya no recorra más el arreglo
						   }
						   conta++;
					   }
					   
				   }else if(de == 5) { // Esperando conexion con cualquier otro
					   System.out.println("");
					   System.out.println("De acuerdo... esperando a que alguien se conecte para chatear contigo...");
					   // Cerrando puerto para disponibidlidad
					   try {				   
						   socketS.close();
						   input.close();
						   output.close();
						   System.out.println("Espera....");
						   ServerSocket conect = new ServerSocket(port);
						   socketV = conect.accept(); // Esperando a que se conecte el otro cliente (visitante)...
						   System.out.println("Conexión recibida...");
						    // Para recibir mensajes de la otra parte (cliente/servidor)
							inputV = new DataInputStream(socketV.getInputStream());
							// Para enviar mensajes a la otra parte
							outputV = new DataOutputStream(socketV.getOutputStream());
							boolean salida = false;
							System.out.println("");
							System.out.println("---------------------------------");
							System.out.println("¡EL CHAT A INICIADO!");
							System.out.println("---------------------------------");
							System.out.println("---------------------------------");
							System.out.println("");
							boolean sal = false;
							while(salida == false) { // EL ORDEN CAMBIA PORQUE EL ES QUIEN RECIBE el mensaje primero
								recibe = inputV.readUTF(); // Recibir mensaje
								System.out.println(recibe); // Mostrar el mesaje
								if(recibe.equals("FINALIZAR")) {										
									System.out.println("Este chat a finalizado");
									salida = true;
									sal = true;
								}else if(sal == false) {
									envia = scn.nextLine(); // Escribir mensaje
									outputV.writeUTF(envia); // Enviar mensaje								
								}
							}
							System.out.println("Cerrando conexiones...");
							// Cerrando conexiones
							try {						
								socketV.close();
								inputV.close();
								outputV.close();
								
							}catch(IOException i) {
								System.out.println("Hubo un error cerrando conexiones al desconectar con el cliente");
								   System.out.println(i);
								
							}
							
						   
					   }catch(IOException e) {
						   System.out.println("Ups, un error intentando cerrar la conexión con el servidor y/o en la conexión con el otro");
						   System.out.println(e);
					   }
					   
					   
				   }
			    
			    
				
			}
			
			//TERCER CLIENTE
			else if(recibe.equals("3")) {
				System.out.println("");
				System.out.println("Cliente 3");
				System.out.println("");
				// Recibe mensajes del servidor
				recibe = input.readUTF(); //  (1-2)
				System.out.println(recibe);
				recibe = input.readUTF(); // (1-3)
				System.out.println(recibe);
						
				// Envío de nensajes al sevidor
				envia = nombre; 
			    output.writeUTF(envia); // Nombre del cliente // (2-1)
			    envia = ip; 
			    output.writeUTF(envia); // Ip del cliente // (2-2)
			    
			    // Recibe mensajes del servidor
			    recibe = input.readUTF();
			    System.out.println(recibe); // (1-4)
			    
			    boolean salida3 = false;
			    while(salida3 == false) {
			    	System.out.println("Esperando a un cliente para que se conecte...");
			    	recibe = input.readUTF(); // ESPERANDO CONFIRMACIÓN PARA HABLAR CON OTRO....
			    	if(recibe.equals("Seguir")) {
			    		salida3 = true;
			    	}
			    }
			    
				   System.out.println("Recibiendo mensaje del servidor (Lista de personas para chatear)...");
				   recibe = input.readUTF(); // (1-6)
				   System.out.println("Hay" + recibe + " personas en el chat (contandote a ti)");
				   
				   if(recibe.equals("4") ) { // Esto es para comprobar errores
					   System.out.println("Correcto...");
				   }else {
					   System.out.println("Esto aqui no funcionara...");
				   }
				   System.out.println("=========================================================");
				   			   
				   for(int i = 0; i < 4; i++ ) {			   
					   recibe = input.readUTF();// (1-7) Nombres de los clientes
					   System.out.println((i+1) + "Nombre:  " + recibe );
					   recibe = input.readUTF();// (1-8) Ips de los clientes
					   listips.add(recibe);
					   recibe = input.readUTF();// (1-9) Estado de ese cliente
					   System.out.println("Estado: " + recibe);
					   System.out.println("=========================================================");			   
				   }
				   
				   System.out.println("");
				   System.out.println("Selecciona el cliente con el que quieres hablar.");
				   System.out.println("Por supuesto, no se seleccione a usted mismo, no quiere provocar un error en su host");
				   System.out.println("O si lo prefiere, puede esperar a que alguien se conecte con usted automáticamente, para eso, digite el número 5...");
				   System.out.println("");
				   int de = d.nextInt();
				   if(de == 1) {
					   int conta = 0;
					   for(String ips: listips) {
						   if(conta == 2) { // HABLA CON CLIENTE 3 PERO ESTO ES ASÍ PORQUE LOS ARRAYLIST EMPIEZAN DESDE CERO TAMBIÉN
							   String theip = listips.get(conta);
							   try {
								   // Cerrar conexión con el servidor para liberar el puerto
								   socketS.close();
								   input.close();
								   output.close();
								   // Buscar chat con cliente 
								   try {
									   socket3 = new Socket(theip, port); // Hacer conexion con el cliente 2
									   System.out.println("La conexión ha sido un éxito");
									   // Para recibir mensajes de la otra parte (cliente/servidor)
										inputV = new DataInputStream(socket3.getInputStream());
									   // Para enviar mensajes a la otra parte
										outputV = new DataOutputStream(socket3.getOutputStream());
										boolean salida = false;
										System.out.println("");
										System.out.println("---------------------------------");
										System.out.println("¡EL CHAT A INICIADO!");
										System.out.println("---------------------------------");
										System.out.println("Envía un mensaje");
										System.out.println("---------------------------------");
										System.out.println("");
										while(salida == false) {
											envia = scn.nextLine(); // Escribir mensaje
											outputV.writeUTF(envia); // Enviar mensaje
											recibe = inputV.readUTF(); // Recibir mensaje
											System.out.println(recibe); // Mostrar el mesaje
											if(recibe.equals("FINALIZAR")) {										
												salida = true;
												envia = ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>";
												outputV.writeUTF(envia);
												envia = "La persona ha finalizado el chat";
												outputV.writeUTF(envia);
												envia = "<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<";
												outputV.writeUTF(envia);
												System.out.println("ESTE CHAT SE HA TERMINADO");
											}
										}
										System.out.println("Cerrando conexiones...");
										// Cerrando conexiones
										try {
											
											socket3.close();
											inputV.close();
											outputV.close();
											
										}catch(IOException i) {
											System.out.println("Hubo un error cerrando conexiones con cliente 3");
											   System.out.println(i);
											
										}
									   
								   }catch(IOException i) {
									   System.out.println("Hubo un error conectando con cliente 3:");
									   System.out.println(i);
									   
								   }
								   
							   }catch(IOException i) {
								   System.out.println("Hubo un error:");
								   System.out.println(i);
							   }
							   break; // Para que ya no recorra más el arreglo
						   }
						   conta++;
					   }
				   }else if(de == 2) {
					   int conta = 0;
					   for(String ips: listips) {
						   if(conta == 1) { // HABLA CON CLIENTE 2 PERO ESTO ES ASÍ PORQUE LOS ARRAYLIST EMPIEZAN DESDE CERO TAMBIÉN
							   String theip = listips.get(conta);
							   try {
								   // Cerrar conexión con el servidor para liberar el puerto
								   socketS.close();
								   input.close();
								   output.close();
								   // Buscar chat con cliente 2...
								   try {
									   socket2 = new Socket(theip, port); // Hacer conexion con el cliente 2
									   System.out.println("La conexión ha sido un éxito");
									   // Para recibir mensajes de la otra parte (cliente/servidor)
										inputV = new DataInputStream(socket2.getInputStream());
									   // Para enviar mensajes a la otra parte
										outputV = new DataOutputStream(socket2.getOutputStream());
										boolean salida = false;
										System.out.println("");
										System.out.println("---------------------------------");
										System.out.println("¡EL CHAT A INICIADO!");
										System.out.println("---------------------------------");
										System.out.println("Envía un mensaje");
										System.out.println("---------------------------------");
										System.out.println("");
										while(salida == false) {
											envia = scn.nextLine(); // Escribir mensaje
											outputV.writeUTF(envia); // Enviar mensaje
											recibe = inputV.readUTF(); // Recibir mensaje
											System.out.println(recibe); // Mostrar el mesaje
											if(recibe.equals("FINALIZAR")) {										
												salida = true;
												envia = ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>";
												outputV.writeUTF(envia);
												envia = "La persona ha finalizado el chat";
												outputV.writeUTF(envia);
												envia = "<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<";
												outputV.writeUTF(envia);
												System.out.println("ESTE CHAT SE HA TERMINADO");
											}
										}
										System.out.println("Cerrando conexiones...");
										// Cerrando conexiones
										try {
											
											socket2.close();
											inputV.close();
											outputV.close();
											
										}catch(IOException i) {
											System.out.println("Hubo un error cerrando conexiones con cliente 2");
											   System.out.println(i);
											
										}
									   
								   }catch(IOException i) {
									   System.out.println("Hubo un error conectando con cliente 2:");
									   System.out.println(i);
									   
								   }
								   
							   }catch(IOException i) {
								   System.out.println("Hubo un error:");
								   System.out.println(i);
							   }
							   break; // Para que ya no recorra más el arreglo
						   }
						   conta++;
					   }
					   
				   }else if(de == 3 ) {
					   System.out.println("Se le advirtió que no lo hiciera...");
					   System.out.println("ERROR DEL SISTEMA, RESPUESTA DEL CLIENTE NO APROPAIDA");
					   System.out.println("SALIENDO DEL SERVIDOR....");					   
					   
				   }else if(de == 4) {
					   int conta = 0;
					   for(String ips: listips) {
						   if(conta == 3) { // HABLA CON CLIENTE 4 PERO ESTO ES ASÍ PORQUE LOS ARRAYLIST EMPIEZAN DESDE CERO TAMBIÉN
							   String theip = listips.get(conta);
							   try {
								   // Cerrar conexión con el servidor para liberar el puerto
								   socketS.close();
								   input.close();
								   output.close();
								   // Buscar chat con cliente 2...
								   try {
									   socket4 = new Socket(theip, port); // Hacer conexion con el cliente 2
									   System.out.println("La conexión ha sido un éxito");
									   // Para recibir mensajes de la otra parte (cliente/servidor)
										inputV = new DataInputStream(socket4.getInputStream());
									   // Para enviar mensajes a la otra parte
										outputV = new DataOutputStream(socket4.getOutputStream());
										boolean salida = false;
										System.out.println("");
										System.out.println("---------------------------------");
										System.out.println("¡EL CHAT A INICIADO!");
										System.out.println("---------------------------------");
										System.out.println("Envía un mensaje");
										System.out.println("---------------------------------");
										System.out.println("");
										while(salida == false) {
											envia = scn.nextLine(); // Escribir mensaje
											outputV.writeUTF(envia); // Enviar mensaje
											recibe = inputV.readUTF(); // Recibir mensaje
											System.out.println(recibe); // Mostrar el mesaje
											if(recibe.equals("FINALIZAR")) {	
												envia = "FINALIZAR";
												outputV.writeUTF(envia); // Enviar mensaje
												salida = true;
												System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
												System.out.println("ESTE CHAT SE HA TERMINADO");
												System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
											}
										}
										System.out.println("Cerrando conexiones...");
										// Cerrando conexiones
										try {
											
											socket4.close();
											inputV.close();
											outputV.close();
											
										}catch(IOException i) {
											System.out.println("Hubo un error cerrando conexiones con cliente 4");
											   System.out.println(i);
											
										}
										
										// Cerrando
									   
								   }catch(IOException i) {
									   System.out.println("Hubo un error conectando con cliente 4:");
									   System.out.println(i);
									   
								   }
								   
							   }catch(IOException i) {
								   System.out.println("Hubo un error:");
								   System.out.println(i);
							   }
							   break; // Para que ya no recorra más el arreglo
						   }
						   conta++;
					   }
					   
				   }else if(de == 5) {
					   System.out.println("");
					   System.out.println("De acuerdo... esperando a que alguien se conecte para chatear contigo...");
					   // Cerrando puerto para disponibidlidad
					   try {				   
						   socketS.close();
						   input.close();
						   output.close();
						   System.out.println("Espera....");
						   ServerSocket conect = new ServerSocket(port);
						   socketV = conect.accept(); // Esperando a que se conecte el otro cliente (visitante)...
						   System.out.println("Conexión recibida...");
						    // Para recibir mensajes de la otra parte (cliente/servidor)
							inputV = new DataInputStream(socketV.getInputStream());
							// Para enviar mensajes a la otra parte
							outputV = new DataOutputStream(socketV.getOutputStream());
							boolean salida = false;
							System.out.println("");
							System.out.println("---------------------------------");
							System.out.println("¡EL CHAT A INICIADO!");
							System.out.println("---------------------------------");
							System.out.println("---------------------------------");
							System.out.println("");
							boolean sal = false;
							while(salida == false) { // EL ORDEN CAMBIA PORQUE EL ES QUIEN RECIBE el mensaje primero
								recibe = inputV.readUTF(); // Recibir mensaje
								System.out.println(recibe); // Mostrar el mesaje
								if(recibe.equals("FINALIZAR")) {										
									System.out.println("Este chat a finalizado");
									salida = true;
									sal = true;
								}else if(sal == false) {
									envia = scn.nextLine(); // Escribir mensaje
									outputV.writeUTF(envia); // Enviar mensaje								
								}
							}
							System.out.println("Cerrando conexiones...");
							// Cerrando conexiones
							try {						
								socketV.close();
								inputV.close();
								outputV.close();
								
							}catch(IOException i) {
								System.out.println("Hubo un error cerrando conexiones al desconectar con el cliente");
								   System.out.println(i);
								
							}
							
						   
					   }catch(IOException e) {
						   System.out.println("Ups, un error intentando cerrar la conexión con el servidor y/o en la conexión con el otro");
						   System.out.println(e);
					   }
					   
					   
				   }
			    
			 // CHAT CON CLIENTE 4 (BUSCA CONEXION)
				
				
			}
			// CUARTO CLIENTE
			else if(recibe.equals("4")) {
				System.out.println("");
				System.out.println("Cliente 4");
				System.out.println("");
				
				// Recibe mensajes del servidor
				recibe = input.readUTF(); //  (1-2)
				System.out.println(recibe);
				recibe = input.readUTF(); // (1-3)
				System.out.println(recibe);
						
				// Envío de nensajes al sevidor
				envia = nombre; 
			    output.writeUTF(envia); // Nombre del cliente // (2-1)
			    envia = ip; 
			    output.writeUTF(envia); // Ip del cliente // (2-2)
			    
			    // Recibe mensajes del servidor
			    recibe = input.readUTF();
			    System.out.println(recibe); // (1-4)
			    
			    boolean salida4 = false;
			    while(salida4 == false) {
			    	System.out.println("Esperando a un cliente para que se conecte...");
			    	recibe = input.readUTF(); // ESPERANDO CONFIRMACIÓN PARA HABLAR CON OTRO....
			    	if(recibe.equals("Seguir")) {
			    		salida4 = true;
			    	}
			    }
				   System.out.println("Recibiendo mensaje del servidor (Lista de personas para chatear)...");
				   recibe = input.readUTF(); // (1-6)
				   System.out.println("Hay" + recibe + " personas en el chat (contandote a ti)");
				   
				   if(recibe.equals("4") ) { // Esto es para comprobar errores
					   System.out.println("Correcto...");
				   }else {
					   System.out.println("Esto aqui no funcionara...");
				   }
				   System.out.println("=========================================================");
				   			   
				   for(int i = 0; i < 4; i++ ) {			   
					   recibe = input.readUTF();// (1-7) Nombres de los clientes
					   System.out.println((i+1) + "Nombre:  " + recibe );
					   recibe = input.readUTF();// (1-8) Ips de los clientes
					   listips.add(recibe);
					   recibe = input.readUTF();// (1-9) Estado de ese cliente
					   System.out.println("Estado: " + recibe);
					   System.out.println("=========================================================");			   
				   }
				   
				   System.out.println("");
				   System.out.println("Selecciona el cliente con el que quieres hablar.");
				   System.out.println("Por supuesto, no se seleccione a usted mismo, no quiere provocar un error en su host");
				   System.out.println("O si lo prefiere, puede esperar a que alguien se conecte con usted automáticamente, para eso, digite el número 5...");
				   System.out.println("");
				   int de = d.nextInt();
				   if(de == 1) {
					   int conta = 0;
					   for(String ips: listips) {
						   if(conta == 0) { // HABLA CON CLIENTE 1 PERO ESTO ES ASÍ PORQUE LOS ARRAYLIST EMPIEZAN DESDE CERO TAMBIÉN
							   String theip = listips.get(conta);
							   try {
								   // Cerrar conexión con el servidor para liberar el puerto
								   socketS.close();
								   input.close();
								   output.close();
								   // Buscar chat con cliente 
								   try {
									   socket4 = new Socket(theip, port); // Hacer conexion con el cliente 2
									   System.out.println("La conexión ha sido un éxito");
									   // Para recibir mensajes de la otra parte (cliente/servidor)
										inputV = new DataInputStream(socket4.getInputStream());
									   // Para enviar mensajes a la otra parte
										outputV = new DataOutputStream(socket4.getOutputStream());
										boolean salida = false;
										System.out.println("");
										System.out.println("---------------------------------");
										System.out.println("¡EL CHAT A INICIADO!");
										System.out.println("---------------------------------");
										System.out.println("Envía un mensaje");
										System.out.println("---------------------------------");
										System.out.println("");
										while(salida == false) {
											envia = scn.nextLine(); // Escribir mensaje
											outputV.writeUTF(envia); // Enviar mensaje
											recibe = inputV.readUTF(); // Recibir mensaje
											System.out.println(recibe); // Mostrar el mesaje
											if(recibe.equals("FINALIZAR")) {										
												salida = true;
												envia = ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>";
												outputV.writeUTF(envia);
												envia = "La persona ha finalizado el chat";
												outputV.writeUTF(envia);
												envia = "<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<";
												outputV.writeUTF(envia);
												System.out.println("ESTE CHAT SE HA TERMINADO");
											}
										}
										System.out.println("Cerrando conexiones...");
										// Cerrando conexiones
										try {
											
											socket4.close();
											inputV.close();
											outputV.close();
											
										}catch(IOException i) {
											System.out.println("Hubo un error cerrando conexiones con cliente 4");
											   System.out.println(i);
											
										}
										
										// Cerrando
									   
								   }catch(IOException i) {
									   System.out.println("Hubo un error conectando con cliente 4:");
									   System.out.println(i);
									   
								   }
								   
							   }catch(IOException i) {
								   System.out.println("Hubo un error:");
								   System.out.println(i);
							   }
							   break; // Para que ya no recorra más el arreglo
						   }
						   conta++;
					   }
				   }else if(de == 2) {
					   int conta = 0;
					   for(String ips: listips) {
						   if(conta == 1) { // HABLA CON CLIENTE 2 PERO ESTO ES ASÍ PORQUE LOS ARRAYLIST EMPIEZAN DESDE CERO TAMBIÉN
							   String theip = listips.get(conta);
							   try {
								   // Cerrar conexión con el servidor para liberar el puerto
								   socketS.close();
								   input.close();
								   output.close();
								   // Buscar chat con cliente 2...
								   try {
									   socket2 = new Socket(theip, port); // Hacer conexion con el cliente 2
									   System.out.println("La conexión ha sido un éxito");
									   // Para recibir mensajes de la otra parte (cliente/servidor)
										inputV = new DataInputStream(socket2.getInputStream());
									   // Para enviar mensajes a la otra parte
										outputV = new DataOutputStream(socket2.getOutputStream());
										boolean salida = false;
										System.out.println("");
										System.out.println("---------------------------------");
										System.out.println("¡EL CHAT A INICIADO!");
										System.out.println("---------------------------------");
										System.out.println("Envía un mensaje");
										System.out.println("---------------------------------");
										System.out.println("");
										while(salida == false) {
											envia = scn.nextLine(); // Escribir mensaje
											outputV.writeUTF(envia); // Enviar mensaje
											recibe = inputV.readUTF(); // Recibir mensaje
											System.out.println(recibe); // Mostrar el mesaje
											if(recibe.equals("FINALIZAR")) {										
												salida = true;
												envia = ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>";
												outputV.writeUTF(envia);
												envia = "La persona ha finalizado el chat";
												outputV.writeUTF(envia);
												envia = "<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<";
												outputV.writeUTF(envia);
												System.out.println("ESTE CHAT SE HA TERMINADO");
											}
										}
										System.out.println("Cerrando conexiones...");
										// Cerrando conexiones
										try {
											
											socket2.close();
											inputV.close();
											outputV.close();
											
										}catch(IOException i) {
											System.out.println("Hubo un error cerrando conexiones con cliente 2");
											   System.out.println(i);
											
										}
									   
								   }catch(IOException i) {
									   System.out.println("Hubo un error conectando con cliente 2:");
									   System.out.println(i);
									   
								   }
								   
							   }catch(IOException i) {
								   System.out.println("Hubo un error:");
								   System.out.println(i);
							   }
							   break; // Para que ya no recorra más el arreglo
						   }
						   conta++;
					   }
					   
				   }else if(de == 3 ) {
					   int conta = 0;
					   for(String ips: listips) {
						   if(conta == 2) { // HABLA CON CLIENTE 3 PERO ESTO ES ASÍ PORQUE LOS ARRAYLIST EMPIEZAN DESDE CERO TAMBIÉN
							   String theip = listips.get(conta);
							   try {
								   // Cerrar conexión con el servidor para liberar el puerto
								   socketS.close();
								   input.close();
								   output.close();
								   // Buscar chat con cliente 2...
								   try {
									   socket3 = new Socket(theip, port); // Hacer conexion con el cliente 2
									   System.out.println("La conexión ha sido un éxito");
									   // Para recibir mensajes de la otra parte (cliente/servidor)
										inputV = new DataInputStream(socket3.getInputStream());
									   // Para enviar mensajes a la otra parte
										outputV = new DataOutputStream(socket3.getOutputStream());
										boolean salida = false;
										System.out.println("");
										System.out.println("---------------------------------");
										System.out.println("¡EL CHAT A INICIADO!");
										System.out.println("---------------------------------");
										System.out.println("Envía un mensaje");
										System.out.println("---------------------------------");
										System.out.println("");
										while(salida == false) {
											envia = scn.nextLine(); // Escribir mensaje
											outputV.writeUTF(envia); // Enviar mensaje
											recibe = inputV.readUTF(); // Recibir mensaje
											System.out.println(recibe); // Mostrar el mesaje
											if(recibe.equals("FINALIZAR")) {										
												salida = true;
												envia = ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>";
												outputV.writeUTF(envia);
												envia = "La persona ha finalizado el chat";
												outputV.writeUTF(envia);
												envia = "<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<";
												outputV.writeUTF(envia);
												System.out.println("ESTE CHAT SE HA TERMINADO");
											}
										}
										System.out.println("Cerrando conexiones...");
										// Cerrando conexiones
										try {
											
											socket3.close();
											inputV.close();
											outputV.close();
											
										}catch(IOException i) {
											System.out.println("Hubo un error cerrando conexiones con cliente 3");
											   System.out.println(i);
											
										}
									   
								   }catch(IOException i) {
									   System.out.println("Hubo un error conectando con cliente 3:");
									   System.out.println(i);
									   
								   }
								   
							   }catch(IOException i) {
								   System.out.println("Hubo un error:");
								   System.out.println(i);
							   }
							   break; // Para que ya no recorra más el arreglo
						   }
						   conta++;
					   }
					   
					   
				   }else if(de == 4) {
					   System.out.println("Se le advirtió que no lo hiciera...");
					   System.out.println("ERROR DEL SISTEMA, RESPUESTA DEL CLIENTE NO APROPAIDA");
					   System.out.println("SALIENDO DEL SERVIDOR....");
					   
				   }else if(de == 5) {
					   System.out.println("");
					   System.out.println("De acuerdo... esperando a que alguien se conecte para chatear contigo...");
					   // Cerrando puerto para disponibidlidad
					   try {				   
						   socketS.close();
						   input.close();
						   output.close();
						   System.out.println("Espera....");
						   ServerSocket conect = new ServerSocket(port);
						   socketV = conect.accept(); // Esperando a que se conecte el otro cliente (visitante)...
						   System.out.println("Conexión recibida...");
						    // Para recibir mensajes de la otra parte (cliente/servidor)
							inputV = new DataInputStream(socketV.getInputStream());
							// Para enviar mensajes a la otra parte
							outputV = new DataOutputStream(socketV.getOutputStream());
							boolean salida = false;
							System.out.println("");
							System.out.println("---------------------------------");
							System.out.println("¡EL CHAT A INICIADO!");
							System.out.println("---------------------------------");
							System.out.println("---------------------------------");
							System.out.println("");
							boolean sal = false;
							while(salida == false) { // EL ORDEN CAMBIA PORQUE EL ES QUIEN RECIBE el mensaje primero
								recibe = inputV.readUTF(); // Recibir mensaje
								System.out.println(recibe); // Mostrar el mesaje
								if(recibe.equals("FINALIZAR")) {										
									System.out.println("Este chat a finalizado");
									salida = true;
									sal = true;
								}else if(sal == false) {
									envia = scn.nextLine(); // Escribir mensaje
									outputV.writeUTF(envia); // Enviar mensaje								
								}
							}
							System.out.println("Cerrando conexiones...");
							// Cerrando conexiones
							try {						
								socketV.close();
								inputV.close();
								outputV.close();
								
							}catch(IOException i) {
								System.out.println("Hubo un error cerrando conexiones al desconectar con el cliente");
								   System.out.println(i);
								
							}
							
						   
					   }catch(IOException e) {
						   System.out.println("Ups, un error intentando cerrar la conexión con el servidor y/o en la conexión con el otro");
						   System.out.println(e);
					   }
					   
					   
				   }
						
			}
			

		} catch (IOException i) {
			System.out.println("Error");
			System.out.println(i);
		}
		
		
		System.out.println("Fin de la conexión total");
		System.out.println("Adios...");
		
		// Cerrar conexión con el servidor
		try {
			socketS.close();
			
		}catch(IOException e) {
			System.out.println("Error...");
			System.out.println("Pudo haber sido un error o que se cerró la conexión con el servidor en transcurso de proceso de chat");
			System.out.println(e);
			
		}
		

	}

	public static void main(String[] args) throws UnknownHostException {

		Cliente cliente = new Cliente("172.19.0.2", 5000);

	}
	

}
