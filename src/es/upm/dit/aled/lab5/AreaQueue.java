package es.upm.dit.aled.lab5;

import java.util.LinkedList;
import java.util.Queue;

import es.upm.dit.aled.lab5.gui.Position2D;

/**
 * Extension of Area that maintains a strict queue of the Patients waiting to
 * enter in it. After a Patient exits, the first one in the queue will be
 * allowed to enter.
 * 
 * @author rgarciacarmona
 */
public class AreaQueue extends Area {
	
	Queue<Patient> waitQueue = new LinkedList<Patient>();
	
	
	public AreaQueue(String name, int time, int capacity, Position2D position) {
		super(name, time, capacity, position);
		this.waitQueue = new LinkedList<Patient>();

	}
	
	@Override
	public synchronized void enter(Patient patient) {
		// Añadimos el paciente al final de la cola:
		waitQueue.add(patient);
		
		// Añadimos ahora las 2 condiciones que nos piden:
		// 1. El número de pacientes NO puede ser mayor que la capacidad. En caso de que lo sea, este esperará
		// 2. Si el paciente no es primero de la cola, esperará
		try {
		while (numPatients >= capacity || waitQueue.peek() != patient) {
			waiting++;
			wait();
			waiting--;
		}
		// Si no cumple la condición, entrará a la ubicación el primero de ellos y se borrará a continuación:
		waitQueue.remove(patient);
		// Aumentamos el número de pacientes:
		numPatients++;
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}
	
	}

