package com.mjc.school.controller.impl;

import com.mjc.school.controller.BaseController;
import com.mjc.school.service.AuthorService;
import com.mjc.school.service.dto.AuthorDTORequest;
import com.mjc.school.service.dto.AuthorDTOResponse;
import com.mjc.school.service.view.View;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/v1/authors")
@Api(tags = "Author CRUD | REST API", produces = "application/json")
public class AuthorController implements BaseController<AuthorDTORequest, AuthorDTOResponse, Long> {
    private final AuthorService model;
    private final View<AuthorDTOResponse, List<AuthorDTOResponse>> view;

    @Autowired
    public AuthorController(AuthorService model,
                            View<AuthorDTOResponse, List<AuthorDTOResponse>> view) {
        this.model = model;
        this.view = view;
    }

    @Override
    @GetMapping
    @ApiOperation(value = "Retrieve all authors", response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved all authors"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
            @ApiResponse(code = 500, message = "Application failed to process the request")
    })
    public ResponseEntity<List<AuthorDTOResponse>> readAll(
            @ApiParam(value = "Page number", example = "1")
            @RequestParam(value = "page", required = false, defaultValue = "0") int page,
            @ApiParam(value = "Number of authors that is displayed")
            @RequestParam(value = "size", required = false, defaultValue = "5") int size,
            @ApiParam(value = "How to sort author by?", defaultValue = "name")
            @RequestParam(value = "sort_by", required = false, defaultValue = "name") String sortBy)

    {
        var authorDTOResponses = model.readAll(page, size, sortBy);
        view.displayAll(authorDTOResponses);
        return new ResponseEntity<>(authorDTOResponses, HttpStatus.OK);
    }

    @Override
    @GetMapping("/{id}")
    @ApiOperation(value = "Retrieve author by id", response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved author"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    public ResponseEntity<AuthorDTOResponse> readById(
            @ApiParam(value = "Author id", example = "1")
            @PathVariable Long id)
    {
        var authorDTOResponse = model.readById(id);
        view.display(authorDTOResponse);
        return new ResponseEntity<>(authorDTOResponse, HttpStatus.OK);
    }

    @Override
    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    @ApiOperation(value = "Create a new author", response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successfully created an author"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
            @ApiResponse(code = 500, message = "Application failed to process the request")
    })
    public ResponseEntity<AuthorDTOResponse> create(
            @ApiParam(value = "JSON object that contains name field, id (optional)")
            @RequestBody AuthorDTORequest createRequest)
    {
        var authorDTOResponse = model.create(createRequest);
        view.display(authorDTOResponse);
        return new ResponseEntity<>(authorDTOResponse, HttpStatus.CREATED);
    }

    @Override
    @PatchMapping("/{id}")
    @ApiOperation(value = "Update author by id", response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully updated author"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
            @ApiResponse(code = 500, message = "Application failed to process the request")
    })
    public ResponseEntity<AuthorDTOResponse> update(
            @ApiParam(value = "Author id", example="1")
            @PathVariable Long id,

            @ApiParam(value = "JSON object that contains author id you want to update and new name to update)")
            @RequestBody AuthorDTORequest updateRequest)
    {
        var authorDTOResponse = model.update(updateRequest);
        view.display(authorDTOResponse);
        return new ResponseEntity<>(authorDTOResponse, HttpStatus.OK);
    }

    @Override
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation(value = "Delete author by id")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Successfully deleted author"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
            @ApiResponse(code = 500, message = "Application failed to process the request")
    })
    public void deleteById(
            @ApiParam(value = "Author id", example = "1")
            @PathVariable Long id)
    {
        model.deleteById(id);

    }

    @GetMapping("/news/{id}")
    @ApiOperation(value = "Retrieve author by news id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved author"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
            @ApiResponse(code = 500, message = "Application failed to process the request")
    })
    public ResponseEntity<AuthorDTOResponse> getAuthorByNewsId(
            @ApiParam(value = "News id", example = "1")
            @PathVariable Long id)
    {
        var resp = model.readByNewsId(id);
        view.display(resp);
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }
}

