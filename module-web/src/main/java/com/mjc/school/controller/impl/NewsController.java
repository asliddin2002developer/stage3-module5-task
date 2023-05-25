package com.mjc.school.controller.impl;

import com.mjc.school.controller.BaseController;
import com.mjc.school.service.dto.NewsDTORequest;
import com.mjc.school.service.dto.NewsDTOResponse;
import com.mjc.school.service.dto.NewsParamsRequest;
import com.mjc.school.service.impl.NewsService;
import com.mjc.school.service.views.View;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.mjc.school.controller.RestPathConst.NEWS_API_ROOT_PATH;

@RestController
@RequestMapping(value = NEWS_API_ROOT_PATH, produces = MediaType.APPLICATION_JSON_VALUE)
@Api(tags = "News CRUD | REST API")
public class NewsController implements BaseController<NewsDTORequest, NewsDTOResponse, Long> {
    private final NewsService model;
    private final View<NewsDTOResponse, List<NewsDTOResponse>> view;

    @Autowired
    public NewsController(NewsService model,
                          View<NewsDTOResponse, List<NewsDTOResponse>> view) {
        this.model = model;
        this.view = view;
    }

    @GetMapping
    @ApiOperation(value = "Retrieve all news", response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved all news"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
            @ApiResponse(code = 500, message = "Application failed to process the request")

    })
    public ResponseEntity<List<NewsDTOResponse>> readAll(
            @ApiParam(value = "Page number", example = "1")
            @RequestParam(value = "page", required = false, defaultValue = "0") int page,
            @ApiParam(value = "Number of authors that is displayed")
            @RequestParam(value = "size", required = false, defaultValue = "5") int size,
            @ApiParam(value = "How to sort news by?", defaultValue = "title")
            @RequestParam(value = "sort_by", required = false, defaultValue = "createDate::desc") String sortBy)
    {
        var newsList = model.readAll(page, size, sortBy);
        view.displayAll(newsList);
        return new ResponseEntity<>(newsList, HttpStatus.OK);
    }


    @GetMapping("/{id}")
    @ApiOperation(value = "Retrieve news by id", response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved news"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    public ResponseEntity<NewsDTOResponse> readById(
            @ApiParam(value = "News id", example = "1")
            @PathVariable("id") Long id) {
        var newsDTOResponse = model.readById(id);
        view.display(newsDTOResponse);
        return new ResponseEntity<>(newsDTOResponse, HttpStatus.OK);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "Create a new news", response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successfully created a news"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
            @ApiResponse(code = 500, message = "Application failed to process the request")
    })
    public ResponseEntity<NewsDTOResponse> create(
            @ApiParam(value = "JSON object that contains: title, content, authorId, tagIds(optional)")
            @RequestBody NewsDTORequest createRequest)
    {
        var newsDTOResponse = model.create(createRequest);
        view.display(newsDTOResponse);
        return new ResponseEntity<>(newsDTOResponse, HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    @ApiOperation(value = "Update news by id", response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully updated news"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
            @ApiResponse(code = 500, message = "Application failed to process the request")
    })
    public ResponseEntity<NewsDTOResponse> update(
            @ApiParam(value = "News id", example = "1")
            @PathVariable Long id,
            @ApiParam(value = "JSON object that contains: id, title, content, authorId, tagIds(optional)")
            @RequestBody NewsDTORequest updateRequest)
    {
        var newsDTOResponse = model.update(id, updateRequest);
        view.display(newsDTOResponse);
        return new ResponseEntity<>(newsDTOResponse, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation(value = "Delete news by id")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Successfully deleted news"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
            @ApiResponse(code = 500, message = "Application failed to process the request")
    })
    public void deleteById(
            @ApiParam(value = "News id", example = "1")
            @PathVariable Long id) {
        model.deleteById(id);
    }

    @GetMapping("/search")
    @ApiOperation(value = "Search news by title, author, content, tagIds, tagNames. Sort news by page, size, sortBy")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved news"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
            @ApiResponse(code = 500, message = "Application failed to process the request")
    })
    public ResponseEntity<List<NewsDTOResponse>> searchNews(@ApiParam(value = "title") @RequestParam(value = "title", required = false) String title,
                                                            @ApiParam(value = "author") @RequestParam(value = "author", required = false) String author,
                                                            @ApiParam(value = "content") @RequestParam(value = "content", required = false) String content,
                                                            @ApiParam(value = "tagIds") @RequestParam(value = "tagIds", required = false) List<Long> tagIds,
                                                            @ApiParam(value = "tagNames") @RequestParam(value = "tagNames", required = false) List<String> tagNames,
                                                            @ApiParam(value = "page") @RequestParam(value = "page", required = false, defaultValue = "0") int page,
                                                            @ApiParam(value = "size") @RequestParam(value = "size", required = false, defaultValue = "5") int size,
                                                            @ApiParam(value = "sortBy") @RequestParam(value = "sort_by", required = false, defaultValue = "title") String sortBy)
    {
        NewsParamsRequest newsParamsRequest = new NewsParamsRequest(author, title, content, tagIds, tagNames);
        List<NewsDTOResponse> newsDTORespons = model.readByQueryParams(newsParamsRequest);
        view.displayAll(newsDTORespons);
        return new ResponseEntity<>(newsDTORespons, HttpStatus.OK);
    }


}
