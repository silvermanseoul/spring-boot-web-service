package io.silverman.springbootwebservice.service;

import io.silverman.springbootwebservice.domain.post.PostRepository;
import io.silverman.springbootwebservice.web.dto.PostSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Service
public class PostService {

    private final PostRepository postRepository;

    @Transactional
    public Long save(PostSaveRequestDto requestDto) {
        postRepository.save(requestDto.toEntity().getId());
    }
}
