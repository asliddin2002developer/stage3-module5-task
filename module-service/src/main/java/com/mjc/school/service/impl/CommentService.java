package com.mjc.school.service.impl;

import com.mjc.school.repository.impl.CommentRepository;
import com.mjc.school.repository.impl.NewsRepository;
import com.mjc.school.repository.model.impl.CommentModel;
import com.mjc.school.service.BaseService;
import com.mjc.school.service.dto.CommentDTORequest;
import com.mjc.school.service.dto.CommentDTOResponse;
import com.mjc.school.service.exception.NotFoundException;
import com.mjc.school.service.mapper.CommentMapper;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.mjc.school.service.enums.ConstantValidators.ENTITY_NOT_FOUND_MESSAGE;

@Service
public class CommentService implements BaseService<CommentDTORequest, CommentDTOResponse, Long> {
    private final CommentRepository commentRepository;
    private final NewsRepository newsRepository;
    private final CommentMapper mapper;
    private final String entityName = "Comment";
    private final String newsEntity = "News";

    @Autowired
    public CommentService(CommentRepository commentRepository, NewsRepository newsRepository) {
        this.commentRepository = commentRepository;
        this.newsRepository = newsRepository;
        this.mapper = Mappers.getMapper(CommentMapper.class);
    }

    @Override
    public List<CommentDTOResponse> readAll(int page, int size, String sortBy) {
        var modelList = commentRepository.readAll(page, size, sortBy);
        return mapper.modelListToDtoList(modelList);
    }

    @Override
    public CommentDTOResponse readById(Long id) {
        Optional<CommentModel> commentModel = commentRepository.readById(id);
        if (commentModel.isPresent()){
            return mapper.modelToDto(commentModel.get());
        }
        throw new NotFoundException(
                String.format(
                        ENTITY_NOT_FOUND_MESSAGE.getContent(), entityName, id
                )
        );
    }

    @Override
    public CommentDTOResponse create(CommentDTORequest createRequest) {
        if (!newsRepository.existById(createRequest.getNewsId())){
            throw new NotFoundException(
                    String.format(
                        ENTITY_NOT_FOUND_MESSAGE.getContent(), newsEntity, createRequest.getNewsId()
            ));
        }

        var model = mapper.dtoToModel(createRequest);
        model.setNews(newsRepository.getReference(createRequest.getNewsId()));
        var created = commentRepository.create(model);
        return mapper.modelToDto(created);
    }

    @Override
    public CommentDTOResponse update(Long id, CommentDTORequest updateRequest) {
        var model = mapper.dtoToModel(updateRequest);
        var updated = commentRepository.update(model);
        return mapper.modelToDto(updated);
    }

    @Override
    public boolean deleteById(Long id) {
        return false;
    }

    @Override
    public CommentDTOResponse getReference(Long id) {
        var reference = commentRepository.getReference(id);
        if (reference != null){
            return mapper.modelToDto(reference);
        }
        throw new NotFoundException(
                String.format(
                        ENTITY_NOT_FOUND_MESSAGE.getContent(), entityName, id
                )
        );
    }

    public List<CommentDTOResponse> readByNewsId(Long id){
        List<CommentModel> commentssByNewsId = commentRepository.readByNewsId(id);
        return mapper.modelListToDtoList(commentssByNewsId);
    }
}
