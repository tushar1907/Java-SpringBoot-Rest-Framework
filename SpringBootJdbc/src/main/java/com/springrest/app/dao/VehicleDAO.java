package com.springrest.app.dao;

import org.springframework.stereotype.Component;

import com.springrest.app.model.Vehicle;

@Component
public interface VehicleDAO {

	public abstract void createVehicle(Vehicle vehicle);
	public abstract Vehicle getVehicleById(String vin); 
	public abstract void updateVehicleById(Vehicle vehicle); 
	//public abstract void createVehicle(Vehicle vehicle); 
	
}
