package com.mjc.school.service.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "Author model")
public class AuthorDTORequest {
    @ApiModelProperty(notes = "Name of the author", example = "John Doe")
    private String name;

    @Override
    public String toString() {
        return "AuthorDto{name='" + name + '\'' +
                '}';
    }
}
