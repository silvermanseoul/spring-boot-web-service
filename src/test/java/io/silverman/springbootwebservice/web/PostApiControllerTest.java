package io.silverman.springbootwebservice.web;

import io.silverman.springbootwebservice.domain.post.Post;
import io.silverman.springbootwebservice.domain.post.PostRepository;
import io.silverman.springbootwebservice.web.dto.PostResponseDto;
import io.silverman.springbootwebservice.web.dto.PostSaveRequestDto;
import io.silverman.springbootwebservice.web.dto.PostUpdateRequestDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PostApiControllerTest {

    @LocalServerPort
    int port;

    @Autowired
    TestRestTemplate restTemplate;

    @Autowired
    PostRepository postRepository;

    @AfterEach
    void tearDown() throws Exception {
        postRepository.deleteAll();
    }

    @Test
    void 게시글_등록() throws Exception {
        // Given
        String title = "테스트 제목";
        String content = "테스트 내용";
        String author = "테스트 작성자";
        PostSaveRequestDto requestDto = PostSaveRequestDto.builder()
                .title(title)
                .content(content)
                .author(author)
                .build();
        String url = "http://localhost:" + port + "/api/v1/posts";

        // When
        ResponseEntity<Long> responseEntity = restTemplate.postForEntity(url, requestDto, Long.class);

        // Then
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertTrue(Optional.ofNullable(responseEntity.getBody()).orElseThrow() > 0L);

        List<Post> posts = postRepository.findAll();
        assertEquals(title, posts.get(0).getTitle());
        assertEquals(content, posts.get(0).getContent());
        assertEquals(author, posts.get(0).getAuthor());
    }

    @Test
    void 게시글_수정() throws Exception {
        // Given
        String title = "테스트 제목";
        String content = "테스트 내용";
        String author = "테스트 작성자";
        Post post = postRepository.save(Post.builder()
                .title(title)
                .content(content)
                .author(author)
                .build());
        Long id = post.getId();

        String newTitle = "수정된 테스트 제목";
        String newContent = "수정된 테스트 내용";
        PostUpdateRequestDto requestDto = PostUpdateRequestDto.builder()
                .title(newTitle)
                .content(newContent)
                .build();
        HttpEntity<PostUpdateRequestDto> requestEntity = new HttpEntity<>(requestDto);
        String url = "http://localhost:" + port + "/api/v1/posts/" + id;

        // When
        ResponseEntity<Long> responseEntity = restTemplate.exchange(url, HttpMethod.PATCH, requestEntity, Long.class);

        // Then
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertTrue(Optional.ofNullable(responseEntity.getBody()).orElseThrow() > 0L);

        List<Post> posts = postRepository.findAll();
        assertEquals(newTitle, posts.get(0).getTitle());
        assertEquals(newContent, posts.get(0).getContent());
    }

    @Test
    void 게시글_조회() throws Exception {
        // Given
        String title = "테스트 제목";
        String content = "테스트 내용";
        String author = "테스트 작성자";
        Post post = postRepository.save(Post.builder()
                .title(title)
                .content(content)
                .author(author)
                .build());
        Long id = post.getId();
        String url = "http://localhost:" + port + "/api/v1/posts/" + id;

        // When
        ResponseEntity<PostResponseDto> responseEntity = restTemplate.getForEntity(url, PostResponseDto.class);

        // Then
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        PostResponseDto responseDto = Optional.ofNullable(responseEntity.getBody()).orElseThrow();
        assertEquals(id, responseDto.getId());
        assertEquals(title, responseDto.getTitle());
        assertEquals(content, responseDto.getContent());
        assertEquals(author, responseDto.getAuthor());
    }
}