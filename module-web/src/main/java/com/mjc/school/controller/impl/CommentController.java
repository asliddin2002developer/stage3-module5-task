package com.mjc.school.controller.impl;

import com.mjc.school.controller.BaseController;
import com.mjc.school.service.dto.CommentDTORequest;
import com.mjc.school.service.dto.CommentDTOResponse;
import com.mjc.school.service.impl.CommentService;
import com.mjc.school.service.view.View;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/comments")
@Api(tags = "Comment CRUD | REST API")
public class CommentController implements BaseController<CommentDTORequest, CommentDTOResponse, Long> {

    private final CommentService model;
    private final View<CommentDTOResponse, List<CommentDTOResponse>> view;

    @Autowired
    public CommentController(CommentService model,
                             View<CommentDTOResponse, List<CommentDTOResponse>> view) {
        this.model = model;
        this.view = view;
    }


    @Override
    @GetMapping
    @ApiOperation(value = "Retrieve all comments", response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved all comments"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
            @ApiResponse(code = 500, message = "Application failed to process the request")
    })
    public ResponseEntity<List<CommentDTOResponse>> readAll(
            @ApiParam(value = "Page number", example = "1")
            @RequestParam(value = "page", required = false, defaultValue = "0") int page,
            @ApiParam(value = "Number of comments that is displayed")
            @RequestParam(value = "size", required = false, defaultValue = "5") int size,
            @ApiParam(value = "How to sort comments by?", defaultValue = "content")
            @RequestParam(value = "sort_by", required = false, defaultValue = "content") String sortBy)
    {
        var modelList = model.readAll(page, size, sortBy);
        if (modelList == null){
            return new ResponseEntity<>(modelList, HttpStatus.NOT_FOUND);
        }
        view.displayAll(modelList);
        return new ResponseEntity<>(modelList, HttpStatus.OK);
    }

    @Override
    @GetMapping("/{id}")
    @ApiOperation(value = "Retrieve comment by id", response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved author by id"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    public ResponseEntity<CommentDTOResponse> readById(
            @ApiParam(value = "Comment id", example = "1")
            @PathVariable Long id)
    {
        var commentDTOResponse = this.model.readById(id);
//        if (commentDTOResponse == null){
//            return new ResponseEntity<>(commentDTOResponse, HttpStatus.NOT_FOUND);
//        }
        view.display(commentDTOResponse);
        return new ResponseEntity<>(commentDTOResponse, HttpStatus.OK);

    }

    @Override
    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    @ApiOperation(value = "Create a new comment", response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successfully created new comment"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
            @ApiResponse(code = 500, message = "Application failed to process the request")
    })
    public ResponseEntity<CommentDTOResponse> create(
            @ApiParam(value = "JSON object that contains content field, id is not mandatory")
            @RequestBody CommentDTORequest createRequest)
    {
        var commentDTOResponse = model.create(createRequest);
        view.display(commentDTOResponse);
        return new ResponseEntity<>(commentDTOResponse, HttpStatus.CREATED);
    }

    @Override
    @PatchMapping("/{id}")
    @ApiOperation(value = "Update comment by id", response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully updated comment"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
            @ApiResponse(code = 500, message = "Application failed to process the request")
    })
    public ResponseEntity<CommentDTOResponse> update(
            @ApiParam(value = "Comment id", example="1")
            @PathVariable Long id,
            @ApiParam(value = "JSON object that contains comment id you want to update and new content to update)")
            @RequestBody CommentDTORequest updateRequest)
    {
        var commentDTOResponse = model.update(id, updateRequest);
        view.display(commentDTOResponse);
        return new ResponseEntity<>(commentDTOResponse, HttpStatus.OK);
    }

    @Override
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation(value = "Delete comment by id")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Successfully deleted comment"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
            @ApiResponse(code = 500, message = "Application failed to process the request")
    })
    public void deleteById(
            @ApiParam(value = "Comment id", example = "1")
            @PathVariable Long id)
    {
        model.deleteById(id);

    }
    @GetMapping("/news/{id}")
    @ApiOperation(value = "Retrieve comment by news id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved comment"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
            @ApiResponse(code = 500, message = "Application failed to process the request")
    })
    public ResponseEntity<List<CommentDTOResponse>> getCommentsByNewsId(
            @ApiParam(value = "Comment id")
            @PathVariable Long id)
    {
        List<CommentDTOResponse> commentsByNewsId = model.readByNewsId(id);
        view.displayAll(commentsByNewsId);
        return new ResponseEntity<>(commentsByNewsId, HttpStatus.OK);
    }
}
