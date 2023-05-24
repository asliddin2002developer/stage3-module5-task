package com.mjc.school.service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NewsDTORequest {
        private String title;
        private String content;
        private Long authorId;
        private Set<Long> tagIds;


    @Override
        public String toString() {
            return "NewsDto{" +
                    ", title='" + title + '\'' +
                    ", content='" + content + '\'' +
                    ", authorId=" + authorId +
                    '}';
        }
}


