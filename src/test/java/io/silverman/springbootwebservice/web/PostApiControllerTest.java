package io.silverman.springbootwebservice.web;

import io.silverman.springbootwebservice.domain.post.Post;
import io.silverman.springbootwebservice.domain.post.PostRepository;
import io.silverman.springbootwebservice.web.dto.PostSaveRequestDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
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
}