package io.silverman.springbootwebservice.web;

import io.silverman.springbootwebservice.service.post.PostService;
import io.silverman.springbootwebservice.web.dto.PostResponseDto;
import io.silverman.springbootwebservice.web.dto.PostSaveRequestDto;
import io.silverman.springbootwebservice.web.dto.PostUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class PostApiController {

    private final PostService postService;

    @PostMapping("/api/v1/posts")
    public Long save(@RequestBody PostSaveRequestDto requestDto) {
        return postService.save(requestDto);
    }

    @PatchMapping("/api/v1/posts/{id}")
    public Long update(@PathVariable("id") Long id, @RequestBody PostUpdateRequestDto requestDto) {
        return postService.update(id, requestDto);
    }

    @GetMapping("/api/v1/posts/{id}")
    public PostResponseDto findById(@PathVariable("id") Long id) {
        return postService.findById(id);
    }

    @DeleteMapping("api/v1/posts/{id}")
    public Long delete(@PathVariable("id") Long id) {
        return postService.delete(id);
    }
}
