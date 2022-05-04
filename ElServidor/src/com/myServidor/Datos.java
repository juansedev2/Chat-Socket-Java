package com.myServidor;

// Esta clase está destinada a guardar los datos de los clientes para que el servidor les haga el respectivo tratamiento

public class Datos {
	
	// Atributos de la clase
	private String nombre; // Nombre del cliente
	private String ip; // Direccion ip del cliente
	private String puerto; // Numero del puerto del cliente para la conexion ¡EXPERMIENTAL!
	private String estado; // Estado del cliente ("disponible","ocupado")
	
	// Constructores
	public Datos(String nombre, String ip, String puerto, String estado) {
		this.nombre = nombre;
		this.ip = ip;
		this.puerto = puerto;
		this.estado = estado;
	}
	
	public Datos() {
		this.nombre = "";
		this.ip = "";
		this.puerto = "";
		this.estado = "";
	}

	// Getter and setters
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getPuerto() {
		return puerto;
	}

	public void setPuerto(String puerto) {
		this.puerto = puerto;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}
	
	
	
	

}
