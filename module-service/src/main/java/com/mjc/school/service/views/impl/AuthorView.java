package com.mjc.school.service.views.impl;

import com.mjc.school.service.dto.AuthorDTOResponse;
import com.mjc.school.service.views.View;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AuthorView implements View<AuthorDTOResponse, List<AuthorDTOResponse>> {

    static final Logger logger = LoggerFactory.getLogger(AuthorView.class);

    public void display(AuthorDTOResponse author){
        System.out.println(author);
    }

    public void displayAll(List<AuthorDTOResponse> authors){
        for (var author : authors){
            logger.info(author.toString());
        }
    }
}
