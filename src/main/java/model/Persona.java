package model;

import java.util.Objects;

/**
 * Clase Persona.
 */
public class Persona {

	/** El nombre. */
	private String nombre;
	
	/** Los apellidos. */
	private String apellidos;
	
	/** La edad. */
	private int edad;
	
	/**
	 * Constructor de persona.
	 *
	 * @param nombre El nombre
	 * @param apellidos Los apellidos
	 * @param edad La edad
	 */
	public Persona(String nombre,String apellidos,int edad){
		this.nombre=nombre;
		this.apellidos=apellidos;
		this.edad=edad;
	}

	/**
	 * Hash code.
	 *
	 * @return the int
	 */
	@Override
	public int hashCode() {
		return Objects.hash(apellidos, edad, nombre);
	}

	/**
	 * Equals.
	 *
	 * @param obj El obj
	 * @return true, si son iguales
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Persona other = (Persona) obj;
		return Objects.equals(apellidos, other.apellidos) && edad 
				== other.edad && Objects.equals(nombre, other.nombre);
	}

	/**
	 * Getter del nombre.
	 *
	 * @return El nombre
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * Getter de los apellidos.
	 *
	 * @return Los apellidos
	 */
	public String getApellidos() {
		return apellidos;
	}

	/**
	 * Getter de la edad.
	 *
	 * @return La edad
	 */
	public int getEdad() {
		return edad;
	}

	/**
	 * To string de una persona.
	 *
	 * @return El toString
	 */
	@Override
	public String toString() {
		return this.nombre+" "+this.apellidos+", "+this.edad+" años";
	}
	
}
