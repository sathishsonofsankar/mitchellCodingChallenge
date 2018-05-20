package com.mitchell.vehicleCreator;

import com.mitchell.vehicleApi.model.Vehicle;

public class VehicleCreator {
	private Vehicle vehicle = new Vehicle();
  
	public VehicleCreator id(int id) {
		vehicle.setId(id);
		return this;
	}
	
	public VehicleCreator year(int year) {
		vehicle.setYear(year);
		return this;
	}
	
	public VehicleCreator make(String make) {
		vehicle.setMake(make);
		return this;
	}
	
	public VehicleCreator model(String model) {
		vehicle.setModel(model);
		return this;
	}
  
  public Vehicle build() {
    return vehicle;
  }
}
