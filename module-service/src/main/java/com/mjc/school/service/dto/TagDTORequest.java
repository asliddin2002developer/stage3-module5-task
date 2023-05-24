package com.mjc.school.service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TagDTORequest {
    private String name;

    @Override
    public String toString() {
        return "TagDtoRequest{" +
                ", name='" + name + '\'' +
                '}';
    }
}
