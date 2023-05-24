package com.mjc.school.service.mapper;

import com.mjc.school.repository.impl.NewsRepository;
import com.mjc.school.repository.model.impl.CommentModel;
import com.mjc.school.service.dto.CommentDTORequest;
import com.mjc.school.service.dto.CommentDTOResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.NullValueCheckStrategy;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Mapper(componentModel = "spring", nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public abstract class CommentMapper {

    @Autowired
    protected NewsRepository newsRepository;

    public abstract List<CommentDTOResponse> modelListToDtoList(List<CommentModel> commentModelList);

    @Mappings(value = {
            @Mapping(target = "id", source = "id"),
            @Mapping(target = "createDate", source = "createDate"),
            @Mapping(target = "lastUpdateDate", source = "lastUpdateDate"),
    })
    public abstract CommentDTOResponse modelToDto(CommentModel commentModel);

    @Mappings(value = {
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "createDate", ignore = true),
            @Mapping(target = "lastUpdateDate", ignore = true),
            @Mapping(target = "news", ignore = true),
    })
    public abstract CommentModel dtoToModel(CommentDTORequest commentDTORequest);

}
