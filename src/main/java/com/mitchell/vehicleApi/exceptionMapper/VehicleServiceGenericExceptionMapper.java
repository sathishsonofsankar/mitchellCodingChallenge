package com.mitchell.vehicleApi.exceptionMapper;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;


@Provider
public class VehicleServiceGenericExceptionMapper implements ExceptionMapper<RuntimeException>{

		@Override
		public Response toResponse(RuntimeException exception) {
			return Response.status(Response.Status.BAD_REQUEST).entity(exception.getMessage()).type("text/plain").build();
		}
    
}
