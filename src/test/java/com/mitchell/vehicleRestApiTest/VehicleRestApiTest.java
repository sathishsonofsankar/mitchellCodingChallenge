package com.mitchell.vehicleRestApiTest;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.is;

import org.apache.http.HttpStatus;
import org.junit.Before;
import org.junit.Test;

import com.mitchell.vehicleApi.model.Vehicle;
import com.mitchell.vehicleCreator.VehicleCreator;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;


public class VehicleRestApiTest {   
	
  private static final String ID_FIELD = "id";
  private static final String YEAR_FIELD = "year";
  private static final String MAKE_FIELD = "make";
  private static final String MODEL_FIELD = "model";
  
  
  private static final String VEHICLES_RESOURCE = "/vehicles";
  private static final String VEHICLE_RESOURCE = "/vehicles/{id}";
  private static final int NON_EXISTING_VEHICLEID = 9;
  
  private static final int FIRST_VEHICLE_ID = 1;
  private static final int SECOND_VEHICLE_ID = 2;
  private static final int THIRD_VEHICLE_ID = 3;
  
  private static final int FIRST_VEHICLE_YEAR = 2016;
  private static final int SECOND_VEHICLE_YEAR = 2017;
  private static final int THIRD_VEHICLE_YEAR = 2018;
  
  
  private static final String FIRST_VEHICLE_MAKE = "Ford";
  private static final String SECOND_VEHICLE_MAKE = "Ford";
  private static final String THIRD_VEHICLE_MAKE = "Chevy";
  
  private static final String FIRST_VEHICLE_MODEL = "MUSTANG_GT";
  private static final String SECOND_VEHICLE_MODEL = "MUSTANG_ECOBOOST";
  private static final String THIRD_VEHICLE_MODEL = "CAMARO";
  
  private static final String FIRST_VEHICLE_MODEL_UPDATE = "MUSTANG_GT_CONVERTIBLE";
  
  private static final int INVALID_VEHICLE_YEAR=1578;
  
  
  private static final Vehicle FIRST_VEHICLE = new VehicleCreator()
	.id(FIRST_VEHICLE_ID)
    .year(FIRST_VEHICLE_YEAR)
    .make(FIRST_VEHICLE_MAKE)
    .model(FIRST_VEHICLE_MODEL)
    .build();
  
  private static final Vehicle SECOND_VEHICLE = new VehicleCreator()
			.id(SECOND_VEHICLE_ID)
		    .year(SECOND_VEHICLE_YEAR)
		    .make(SECOND_VEHICLE_MAKE)
		    .model(SECOND_VEHICLE_MODEL)
		    .build();
  
  private static final Vehicle THIRD_VEHICLE = new VehicleCreator()
			.id(THIRD_VEHICLE_ID)
		    .year(THIRD_VEHICLE_YEAR)
		    .make(THIRD_VEHICLE_MAKE)
		    .model(THIRD_VEHICLE_MODEL)
		    .build();
  
  private static final Vehicle FIRST_VEHICLE_UPDATE = new VehicleCreator()
		  	.id(FIRST_VEHICLE_ID)
		    .year(FIRST_VEHICLE_YEAR)
		    .make(FIRST_VEHICLE_MAKE)
		    .model(FIRST_VEHICLE_MODEL_UPDATE)
		    .build();
  
  private static final Vehicle INVALIDYEAR_VEHICLE = new VehicleCreator()
		  .id(THIRD_VEHICLE_ID)
		  .year(INVALID_VEHICLE_YEAR)
		  .build();
  
  private static final Vehicle EMPTYMAKE_VEHICLE = new VehicleCreator()
		  .id(THIRD_VEHICLE_ID)
		  .year(THIRD_VEHICLE_YEAR)
		  .model(THIRD_VEHICLE_MODEL)
		  .build();
  
  private static final Vehicle EMPTYMODEL_VEHICLE = new VehicleCreator()
		  .id(THIRD_VEHICLE_ID)
		  .year(THIRD_VEHICLE_YEAR)
		  .make(THIRD_VEHICLE_MAKE)
		  .build();
  

  
  @Before
  public void setUp() {
	  
	  when().delete(VEHICLES_RESOURCE);
	  System.out.println("Deleted ALl Data");
	  given().body(FIRST_VEHICLE).contentType(ContentType.JSON).when().post(VEHICLES_RESOURCE);
	  given().body(SECOND_VEHICLE).contentType(ContentType.JSON).when().post(VEHICLES_RESOURCE);
	  System.out.println("Inserted 2 records");
    RestAssured.port = 8080;
  }
  
  /*@GET
   * URI - /vehicles
   */
  
  @Test
  public void getVehiclesShouldReturnTwoVehicles() {
    when()
      .get(VEHICLES_RESOURCE)
    .then()
      .statusCode(HttpStatus.SC_OK)
      .body(ID_FIELD, hasItems(FIRST_VEHICLE_ID, SECOND_VEHICLE_ID))
      .body(YEAR_FIELD, hasItems(FIRST_VEHICLE_YEAR, SECOND_VEHICLE_YEAR))
      .body(MAKE_FIELD, hasItems(FIRST_VEHICLE_MAKE,SECOND_VEHICLE_MAKE))
      .body(MODEL_FIELD, hasItems(FIRST_VEHICLE_MODEL,SECOND_VEHICLE_MODEL));
  }
  
  /*@GET
   * URI - /vehicles/{ValidID}
   */
  @Test
  public void getVehicleByIdShouldReturnCorrectVehicle() {
    when()
      .get(VEHICLE_RESOURCE,FIRST_VEHICLE_ID)
    .then()
      .statusCode(HttpStatus.SC_OK)
      .body(ID_FIELD, is(FIRST_VEHICLE_ID))
      .body(YEAR_FIELD, is(FIRST_VEHICLE_YEAR))
      .body(MAKE_FIELD, is(FIRST_VEHICLE_MAKE))
      .body(MODEL_FIELD, is(FIRST_VEHICLE_MODEL));
  }
  
  /*@GET
   * URI - /vehicles/{InvalidID}
   */
  @Test
  public void getVehicleByInvalidIdShouldReturn400BadRequest() {
    when()
      .get(VEHICLE_RESOURCE,THIRD_VEHICLE_ID)
    .then()
      .statusCode(HttpStatus.SC_BAD_REQUEST)
      .body(containsString("No Vehicle Found for the ID"));
  }
  
  
  /*@POST
   * URI - /vehicles/
   * Valid JSON Vehicle in RequestBody
   */
  @Test
  public void addVehicleShouldReturnSavedVehicle() {
    given()
      .body(THIRD_VEHICLE)
      .contentType(ContentType.JSON)
    .when()
      .post(VEHICLES_RESOURCE)
    .then()
      .statusCode(HttpStatus.SC_OK)
      .body(ID_FIELD, is(THIRD_VEHICLE_ID))
      .body(YEAR_FIELD, is(THIRD_VEHICLE_YEAR))
      .body(MAKE_FIELD, is(THIRD_VEHICLE_MAKE))
      .body(MODEL_FIELD, is(THIRD_VEHICLE_MODEL));
  }
  
  /*@POST
   * URI - /vehicles/
   * Without RequestBody
   */
  @Test
  public void addVehicleShouldReturn400BadRequestWithoutBody() {
    when()
      .post(VEHICLES_RESOURCE)
    .then()
      .statusCode(HttpStatus.SC_BAD_REQUEST);
  }
  
  /*@POST
   * URI - /vehicles/
   * With Valid Vehicle in RequestBody without content-Type
   */
  @Test
  public void addVehicleShouldReturnNotSupportedMediaTypeWithoutJSONContentType() {
    given()
      .body(THIRD_VEHICLE)
    .when()
      .post(VEHICLES_RESOURCE)
    .then()
      .statusCode(HttpStatus.SC_BAD_REQUEST);
  }
  
  /*@POST
   * URI - /vehicles/
   * With Duplicate Vehicle
   */
  @Test
  public void addDuplicateVehicleShouldReturn400BadRequest() {
	  given()
	  	.body(SECOND_VEHICLE)
	  	.contentType(ContentType.JSON)
	  .when()
	  	.post(VEHICLES_RESOURCE)
	  .then()
	  .statusCode(HttpStatus.SC_BAD_REQUEST)
	  .body(containsString("Already Present"));
  }
  
  /*@POST
   * URI - /vehicles/
   * With Invlaid Vehicle Year
   */
  @Test
  public void addVehicleWithInvalidYearShouldReturnError() {
	  given()
	  	.body(INVALIDYEAR_VEHICLE)
	  	.contentType(ContentType.JSON)
	  .when()
	  	.post(VEHICLES_RESOURCE)
	  .then()
	  .statusCode(HttpStatus.SC_BAD_REQUEST)
	  .body(containsString("Vehicle Year must be between 1950 and 2050"));
  }
  
  /*@POST
   * URI - /vehicles/
   * With Empty Make
   */
  @Test
  public void addVehicleWithEmptyMakeShouldReturnError() {
	  given()
	  	.body(EMPTYMAKE_VEHICLE)
	  	.contentType(ContentType.JSON)
	  .when()
	  	.post(VEHICLES_RESOURCE)
	  .then()
	  .statusCode(HttpStatus.SC_BAD_REQUEST)
	  .body(containsString("Vehicle Make cant be empty"));
  }
  
  /*@POST
   * URI - /vehicles/
   * With Empty Model
   */
  @Test
  public void addVehicleWithEmptyModelShouldReturnError() {
	  given()
	  	.body(EMPTYMODEL_VEHICLE)
	  	.contentType(ContentType.JSON)
	  .when()
	  	.post(VEHICLES_RESOURCE)
	  .then()
	  .statusCode(HttpStatus.SC_BAD_REQUEST)
	  .body(containsString("Vehicle Model cant be empty"));
  }
  
  /*@PUT
   * URI - /vehicles/
   * Update Valid JSON Vehicle in RequestBody
   */
  @Test
  public void updateVehicleShouldReturnSavedVehicle() {
    given()
      .body(FIRST_VEHICLE_UPDATE)
      .contentType(ContentType.JSON)
    .when()
      .put(VEHICLES_RESOURCE)
    .then()
      .statusCode(HttpStatus.SC_OK)
      .body(ID_FIELD, is(FIRST_VEHICLE_ID))
      .body(YEAR_FIELD, is(FIRST_VEHICLE_YEAR))
      .body(MAKE_FIELD, is(FIRST_VEHICLE_MAKE))
      .body(MODEL_FIELD, is(FIRST_VEHICLE_MODEL_UPDATE));
  }
  
  /*@PUT
   * URI - /vehicles/
   * Update invalid vehicle
   */
  @Test
  public void testUpdateInvalidVehicleShouldReturn400BadRequest() {
	  given()
	  	.body(THIRD_VEHICLE)
	  	.contentType(ContentType.JSON)
	  .when()
	  	.put(VEHICLES_RESOURCE)
	  .then()
	  .statusCode(HttpStatus.SC_BAD_REQUEST)
	  .body(containsString("No such Vehicle with ID"));
  }
  
  /*@DELETE
   * URI - /vehicles/
   * Delete valid vehicle by ID
   */
  @Test
  public void deleteVehicleByIDShouldReturnNoContent() {
	  when()
	  	.delete(VEHICLE_RESOURCE,FIRST_VEHICLE_ID)
	  .then()
	  .statusCode(HttpStatus.SC_NO_CONTENT);
  }
  
  /*@DELETE
   * URI - /vehicles/
   * Delete vehicle by INvalidID
   */
  @Test
  public void deleteVehicleByInvalidIDShouldReturn400BadRequest() {
	  when()
	  	.delete(VEHICLE_RESOURCE,THIRD_VEHICLE_ID)
	  .then()
	  .statusCode(HttpStatus.SC_BAD_REQUEST)
	  .body(containsString("No such Vehicle with ID"));
  }
  
}
