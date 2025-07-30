package api.services;

import api.models.User;
import config.ConfigLoader;
import io.restassured.RestAssured;
import io.restassured.response.Response;

import static io.restassured.http.ContentType.JSON;

public class UserService {

    public UserService() {}

    /** Creates a new user via the API
     Return type is Response to allow flexible handling of API responses,
     and to facilitate further processing or assertions in tests.
     * @param user The User object containing user details to be created
     * @return Response object containing the API response
     */
    public Response createUser(User user) {
        return RestAssured.given()
                .contentType(JSON)
                .body(user)
                .when()
                .post("/auth/register")
                .andReturn();
    }
}
