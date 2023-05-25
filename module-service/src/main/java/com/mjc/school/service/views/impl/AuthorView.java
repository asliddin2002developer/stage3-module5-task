package com.mjc.school.service.views.impl;

import com.mjc.school.service.dto.AuthorDTOResponse;
import com.mjc.school.service.views.View;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AuthorView implements View<AuthorDTOResponse, List<AuthorDTOResponse>> {

    public void display(AuthorDTOResponse author){
        System.out.println(author);
    }

    public void displayAll(List<AuthorDTOResponse> authors){
        authors.forEach(System.out::println);
    }
}
