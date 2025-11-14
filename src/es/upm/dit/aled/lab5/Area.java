package es.upm.dit.aled.lab5;

import java.awt.Color;
import java.util.Objects;

import es.upm.dit.aled.lab5.gui.Position2D;

/**
 * Models an area in a hospital ER. Each Area is characterized by its name
 * (which must be unique), the time a patient must spend there to be treated (in
 * milliseconds), the capacity (maximum number of patients that can be treated
 * at the same time), the number of patients being treated at a given time, and
 * the number of patients currently waiting.
 * 
 * This class is a monitor that ensures that there is a thread safe way to enter
 * and exit the area.
 * 
 * Each Area is represented graphically by a square of side 120 px, centered in
 * a given position and with a custom color.
 * 
 * @author rgarciacarmona
 */
public class Area {

	protected String name;
	private int time;
	private Position2D position;
	private Color color;
	protected int capacity;
	protected int numPatients;
	protected int waiting;

	/**
	 * Builds a new Area.
	 * 
	 * @param name     The name of the Area.
	 * @param time     The amount of time (in milliseconds) needed to treat a
	 *                 Patient in this Area.
	 * @param capacity The number of Patients that can be treated at the same time.
	 * @param position The location of the Area in the GUI.
	 */
	public Area(String name, int time, int capacity, Position2D position) {
		this.name = name;
		this.time = time;
		this.capacity = capacity;
		this.position = position;
		this.color = Color.GRAY; // Default color
	}

	/**
	 * Returns the name of the Area.
	 * 
	 * @return The name.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Returns the time (in milliseconds) that a Patient must spend in the Area to
	 * be treated.
	 * 
	 * @return The time.
	 */
	public int getTime() {
		return time;
	}

	/**
	 * Returns the position of the center of the Area in the GUI.
	 * 
	 * @return The position.
	 */
	public Position2D getPosition() {
		return position;
	}

	/**
	 * Returns the color of Area in the GUI.
	 * 
	 * @return The color.
	 */
	public Color getColor() {
		return color;
	}

	/**
	 * Changes the color of the Area in the GUI.
	 * 
	 * @param color The new color.
	 */
	public void setColor(Color color) {
		this.color = color;
	}

	/**
	 * Thread safe method that allows a Patient to enter the area. If the Area is
	 * currently full, the Patient thread must wait.
	 * 
	 * @param p The patient that wants to enter.
	 */
	
	// TODO: method enter
	public synchronized void enter(Patient p) {
		try {
			// Mientras el área esté llena el paciente debe de esperar
		while (numPatients >= capacity) {
			waiting++; // Si el paciente está forzado a esperar, waiting aumenta en 1
			wait();	
			// El paciente ha logrado accede a las sección --> Waiting se reduce en 1
						waiting--;
		}
		// El paciente entra al área
		numPatients++;
		
		} catch(InterruptedException e) {
			e.printStackTrace();
		}
	}
	/**
	 * Thread safe method that allows a Patient to exit the area. After the Patient
	 * has left, this method notifys all waiting Patients.
	 * 
	 * @param p The patient that wants to enter.
	 */
	// TODO method exit
	public synchronized void exit(Patient p) {
		// Los pacientes salen del area y notifica a todos los pacientes en espera
		numPatients--;
		notifyAll();
	}
	
	/**
	 * Returns the capacity of the Area. This method must be thread safe.
	 * 
	 * @return The capacity.
	 */
	
	public int getCapacity() {
		return capacity;
	}

	
	// TODO: method getCapacity
	
	/**
	 * Returns the current number of Patients being treated at the Area. This method must be thread safe.
	 * 
	 * @return The number of Patients being treated.
	 */
	// TODO: method getNumPatients
	public synchronized int getNumPatients() {
		return numPatients;
	}
	/**
	 * Returns the current number of Patients waiting to be treated at the Area. This method must be thread safe.
	 * 
	 * @return The number of Patients waiting to be treated.
	 */
	// TODO method getWaiting
	public int getWaiting() {
		return waiting;
	}

	@Override
	public int hashCode() {
		return Objects.hash(name);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Area other = (Area) obj;
		return Objects.equals(name, other.name);
	}

	@Override
	public String toString() {
		return name;
	}
}