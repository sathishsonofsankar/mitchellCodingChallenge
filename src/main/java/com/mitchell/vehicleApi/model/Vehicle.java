package com.mitchell.vehicleApi.model;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="vehicles")
public class Vehicle {
	@Id
	private int id;
	
	@Min(value = 1950,message="{vehicle.year.limit}")
	@Max(value = 2050,message="{vehicle.year.limit}")
	private int year;
	
	@NotEmpty(message="{vehicle.make.notempty}")
	@NotNull(message="{vehicle.make.notnull}")
	private String make;
	
	
	@NotEmpty(message="{vehicle.model.notempty}")
	@NotNull(message="{vehicle.model.notnull}")
	private String model;
	
	public Vehicle() {}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public String getMake() {
		return make;
	}

	public void setMake(String make) {
		this.make = make;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}
	
	
	
}
