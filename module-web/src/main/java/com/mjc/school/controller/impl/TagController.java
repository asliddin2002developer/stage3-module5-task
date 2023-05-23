package com.mjc.school.controller.impl;

import com.mjc.school.controller.BaseController;
import com.mjc.school.service.TagService;
import com.mjc.school.service.dto.TagDTORequest;
import com.mjc.school.service.dto.TagDTOResponse;
import com.mjc.school.service.view.View;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/tags")
@Api(tags = "Tag Controller | REST API")
public class TagController implements BaseController<TagDTORequest, TagDTOResponse, Long> {

    private final TagService model;
    private final View<TagDTOResponse, List<TagDTOResponse>> view;

    @Autowired
    public TagController(TagService model,
                         View<TagDTOResponse, List<TagDTOResponse>> view) {
        this.model = model;
        this.view = view;
    }
    @Override
    @GetMapping
    @ApiOperation(value = "Retrieve all tags", response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved all tags"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
            @ApiResponse(code = 500, message = "Application failed to process the request")
    })
    public ResponseEntity<List<TagDTOResponse>> readAll(
            @ApiParam(value = "Page number", example = "1")
            @RequestParam(value = "page", required = false, defaultValue = "0") int page,
            @ApiParam(value = "Number of authors that is displayed")
            @RequestParam(value = "size", required = false, defaultValue = "5") int size,
            @ApiParam(value = "How to sort author by?", defaultValue = "name")
            @RequestParam(value = "sort_by", required = false, defaultValue = "name") String sortBy)
    {
        var tags = model.readAll(page, size, sortBy);
        view.displayAll(tags);
        return new ResponseEntity<>(tags, HttpStatus.OK);
    }
    @Override
    @GetMapping("/{id}")
    @ApiOperation(value = "Retrieve tag by id", response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved tag"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    public ResponseEntity<TagDTOResponse> readById(
            @ApiParam(value = "Tag id")
            @PathVariable Long id)
    {
        var tagDTOResponse = model.readById(id);
        view.display(tagDTOResponse);
        return new ResponseEntity<>(tagDTOResponse, HttpStatus.OK);
    }

    @Override
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "Create a new tag", response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successfully created tag"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
            @ApiResponse(code = 500, message = "Application failed to process the request")
    })
    public ResponseEntity<TagDTOResponse> create(
            @ApiParam(value = "JSON object that contains tag name, id (optional)")
            @RequestBody TagDTORequest createRequest)
    {
        var tagDTOResponse = model.create(createRequest);
        view.display(tagDTOResponse);
        return new ResponseEntity<>(tagDTOResponse, HttpStatus.CREATED);
    }

    @Override
    @PatchMapping("/{id}")
    @ApiOperation(value = "Update tag by id", response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully updated tag"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
            @ApiResponse(code = 500, message = "Application failed to process the request")
    })
    public ResponseEntity<TagDTOResponse> update(
            @ApiParam(value = "Tag id")
            @PathVariable Long id,
            @ApiParam(value = "JSON object than contains tag id and updated name")
            @RequestBody TagDTORequest updateRequest)
    {
        var tagDTOResponse = model.update(updateRequest);
        view.display(tagDTOResponse);
        return new ResponseEntity<>(tagDTOResponse, HttpStatus.OK);
    }

    @Override
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation(value = "Delete tag by id")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Successfully deleted tag"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
            @ApiResponse(code = 500, message = "Application failed to process the request")
    })
    public void deleteById(
            @ApiParam(value = "Tag id", example = "1")
            @PathVariable Long id)
    {
        model.deleteById(id);
    }


    @GetMapping("/news/{id}")
    @ApiOperation(value = "Retrieve tag by news id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved tag"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
            @ApiResponse(code = 500, message = "Application failed to process the request")
    })
    public ResponseEntity<List<TagDTOResponse>> getTagsByNewsId(
            @ApiParam(value = "News id", example = "1")
            @PathVariable Long id)
    {
        var tags = model.readByNewsId(id);
        view.displayAll(tags);
        return new ResponseEntity<>(tags, HttpStatus.OK);
    }
}
