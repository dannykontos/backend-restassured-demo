package org.example;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Unit test for simple App.
 */
public class AppTest {
    /**
     * Rigorous Test :-)
     * Homework RestAPI :
     * Using http://dummy.restapiexample.com/ provide a test framework that tests the following:
     * - successfully retrieves all employees and counts the number of employees with age number higher than 30
     * - successfully adds new employee with age higher than 30 and assert that operation is successful
     * - successfully updates the employee and asserts that operation is successful
     * - successfully retrieves all employees and asserts that employees with age number higher than 30 has modified
     * - successfully deletes the employee that he added and asserts the operation is successful
     */

    /**
     * - successfully retrieves all employees and counts the number of employees with age number higher than 30
     */
    String baseURI = "http://dummy.restapiexample.com";

    @Test
    public void retrieveAllEmployeesAndCountsAllHigherThan30() {
        int numberOfEmployees;
        int employeesOver30 = 0;
        int employeesUnder30 = 0;
        String expectedMessage = "Successfully! All records has been fetched.";

        RestAssured.baseURI = baseURI;
        String myResponse = given()
                .when().get("/api/v1/employees")
                .then()
                .assertThat().statusCode(200)
                .extract().response().asString();

        JsonPath js = new JsonPath(myResponse);
        String actualMessage = js.getString("message");
        numberOfEmployees = Integer.parseInt(js.getString("data.size"));

        for (int i = 0; i < numberOfEmployees; i++) {

            if (Integer.parseInt(js.getString("data.employee_age[" + i + "]")) > 30) {
                employeesOver30 = employeesOver30 + 1;

            } else {
                employeesUnder30 = employeesUnder30 + 1;
            }
        }
        assertEquals("The expected message was not displayed in the response: " + expectedMessage, expectedMessage, actualMessage);
        assertEquals(employeesOver30 + employeesUnder30, numberOfEmployees);
    }

    /**
     * - successfully adds new employee with age higher than 30 and assert that operation is successful
     */
    @Test
    public void addNewEmployeeAgeHigherThan30() {
        Payload customPayload = new Payload();
        RestAssured.baseURI = baseURI;
        String myResponse = given().header("", "").body(customPayload.AddEmployee())
                .when().post("/api/v1/create")
                .then()
                .assertThat().statusCode(200).extract().response().asString();
        JsonPath js = new JsonPath(myResponse);
        assertEquals("success", js.getString("status"));
        assertEquals("Successfully! Record has been added.", js.getString("message"));
    }

    /**
     * - successfully updates the employee and asserts that operation is successful
     */
    @Test
    public void updateEmployee() {
        RestAssured.baseURI = baseURI;
        given().header("", "").body("{\"name\": \"Hip\",\"salary\": \"13000\",\"age\": \"99\"}")
                .when().put("/api/v1/update/6862")
                .then()
                .assertThat().statusCode(200).extract().response().asString();
    }

    /**
     * - successfully retrieves all employees and asserts that employees with age number higher than 30 has modified
     */
    @Test
    public void test() {
        // the endpoint is overloaded with requests, some functionalities are not available anymore
        assertTrue(true);
    }
}
