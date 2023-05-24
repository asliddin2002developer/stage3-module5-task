package com.mjc.school.service.mapper;

import com.mjc.school.repository.model.impl.TagModel;
import com.mjc.school.service.dto.TagDTORequest;
import com.mjc.school.service.dto.TagDTOResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TagMapper {
    @Mapping(target = "tag", source = "tag")
    List<TagDTOResponse> modelListToDtoList(List<TagModel> tagModelList);
    TagDTOResponse modelToDto(TagModel entity);

    // when we have @ManyToMany relationship inside TagModel
//    @Mapping(target = "news", ignore = true)
    @Mappings(value = {
        @Mapping(target = "news", ignore = true),
        @Mapping(target = "id", ignore = true)
    })
    TagModel dtoToModel(TagDTORequest request);
}
