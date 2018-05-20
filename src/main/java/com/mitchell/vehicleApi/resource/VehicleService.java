package com.mitchell.vehicleApi.resource;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.springframework.beans.factory.annotation.Autowired;

import com.mitchell.vehicleApi.exceptionMapper.VehicleValidationExceptionMapper;
import com.mitchell.vehicleApi.model.Vehicle;
import com.mitchell.vehicleApi.repository.VehicleRepository;


@Path("/vehicles")
public class VehicleService {

	@Autowired
	private VehicleRepository vehicleRepo;
	
	@GET
	@Produces("application/json")
	public List<Vehicle> getVehicles()
	{
		List<Vehicle> vehicles= vehicleRepo.findAll();
		return vehicles;
	}
	
	@GET
	@Path("/{id}")
	@Produces("application/json")
	public Vehicle getVehicleById(@PathParam("id") int id)
	{
			Optional<Vehicle> v = vehicleRepo.findById(id);
			if(!v.isPresent())
			{
				throw new RuntimeException("No Vehicle Found for the ID "+id);
			}
			else
				return v.get();
	}
	
	@POST
	@Consumes("application/json")
	@Produces("application/json")
	public Vehicle insertVehicle( @Valid Vehicle v) 
	{
		if(!vehicleRepo.findById(v.getId()).isPresent())
			return vehicleRepo.save(v);
		else
			throw new RuntimeException("Vehicle with ID "+v.getId() + " Already Present");
		
	}
	
	@PUT
	@Consumes("application/json")
	@Produces("application/json")
	public Vehicle updateVehicle( @Valid Vehicle v)
	{
		if(!vehicleRepo.findById(v.getId()).isPresent())
			throw new RuntimeException("No such Vehicle with ID "+v.getId() + " available to update ");
		else
			return vehicleRepo.save(v);
	}
	
	@DELETE
	@Path("/{id}")
	public void deleteVehicleById(@PathParam("id") int id)
	{
		if(!vehicleRepo.findById(id).isPresent())
			throw new RuntimeException("No such Vehicle with ID "+id + " available to delete ");
		else
			vehicleRepo.deleteById(id);
	}
	
	@DELETE
	public void deleteVehicles()
	{
		vehicleRepo.deleteAll();
	}
	
	
	//filter Methods
	@GET
	@Path("/make/{make}")
	@Produces("application/json")
	public List<Vehicle> getVehiclesByMake(@PathParam("make") String make)
	{
		return vehicleRepo.findByMake(make);
	}
	
	@GET
	@Path("/model/{model}")
	@Produces("application/json")
	public List<Vehicle> getVehiclesByModel(@PathParam("model") String model)
	{
		return vehicleRepo.findByModel(model);
	}
	
}
