package com.mitchell.vehicleApi;

import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.stereotype.Component;

import com.mitchell.vehicleApi.exceptionMapper.VehicleServiceGenericExceptionMapper;
import com.mitchell.vehicleApi.exceptionMapper.VehicleValidationExceptionMapper;
import com.mitchell.vehicleApi.resource.VehicleService;
 
@Component
public class JerseyConfig extends ResourceConfig 
{
    public JerseyConfig() 
    {
    	register(VehicleValidationExceptionMapper.class);
    	register(VehicleServiceGenericExceptionMapper.class);
        register(VehicleService.class);
    }
}
