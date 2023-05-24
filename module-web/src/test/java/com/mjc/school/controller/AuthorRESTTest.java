package com.mjc.school.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.mjc.school.service.dto.AuthorDTORequest;
import com.mjc.school.service.dto.AuthorDTOResponse;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class AuthorRESTTest {

    private ObjectMapper mapper = new ObjectMapper();

    {
        mapper.registerModule(new JavaTimeModule());
    }


    @BeforeAll
    public static void setup() {
        String port = System.getProperty("server.port");
        if (port == null) {
            RestAssured.port = 8080;
        }
        else{
            RestAssured.port = Integer.parseInt(port);
        }


        String basePath = System.getProperty("server.base");
        if(basePath==null){
            basePath = "/api/v1";
        }
        RestAssured.basePath = basePath;

        String baseHost = System.getProperty("server.host");
        if(baseHost==null){
            baseHost = "http://localhost";
        }
        RestAssured.baseURI = baseHost;

    }

    @Test
    void CreateAuthorTest() throws JsonProcessingException {
        final int EXPECTED_STATUS_CODE = 201;
        Response response = given()
                .contentType(ContentType.JSON)
                .body(new AuthorDTORequest("Test User"))
                .when()
                .post("/authors");

        String responseAsString = response.asString();
        AuthorDTOResponse createdAuthor = mapper.readValue(responseAsString, AuthorDTOResponse.class);
        assertEquals(EXPECTED_STATUS_CODE, response.getStatusCode());
        assertNotNull(responseAsString);

//        delete author
        delete("/authors/"+createdAuthor.getId())
                .then()
                .statusCode(204);
    }

    @Test
    void GetAuthorsTest() throws JsonProcessingException{
        final int EXPECTED_STATUS_CODE = 200;
        Response response = get("/authors");
        String responseAsString = response.asString();
        AuthorDTOResponse[] createdAuthor  = mapper.readValue(responseAsString, AuthorDTOResponse[].class);

        assertEquals(EXPECTED_STATUS_CODE, response.getStatusCode());
        assertNotNull(response);
    }

    @Test
    void GetAuthorTest() throws JsonProcessingException {
        final int EXPECTED_STATUS_CODE = 200;

        //create new Author
        Response testUser = given()
                .contentType(ContentType.JSON)
                .body(new AuthorDTORequest("Test User"))
                .when()
                .post("/authors");

        String createdUserString = testUser.asString();
        AuthorDTOResponse createdAuthor = mapper.readValue(createdUserString, AuthorDTOResponse.class);

        // request created Author
        Response response = get("authors/" + createdAuthor.getId());
        String requestedUserString = response.asString();
        AuthorDTOResponse requestedAuthor = mapper.readValue(requestedUserString, AuthorDTOResponse.class);

        assertEquals(EXPECTED_STATUS_CODE, response.getStatusCode());
        assertNotNull(requestedAuthor);

        //delete author
        delete("/authors/"+createdAuthor.getId())
                .then()
                .statusCode(204);

    }


    @Test
    void DeleteAuthorTest() throws  JsonProcessingException{
        final int EXPECTED_STATUS_CODE = 200;

        //create new Author
        Response testUser = given()
                .contentType(ContentType.JSON)
                .body(new AuthorDTORequest("Test User"))
                .when()
                .post("/authors");

        String createdUserString = testUser.asString();
        AuthorDTOResponse createdAuthor = mapper.readValue(createdUserString, AuthorDTOResponse.class);

        //delete author
        delete("/authors/"+createdAuthor.getId())
                .then()
                .statusCode(204);
    }
}
