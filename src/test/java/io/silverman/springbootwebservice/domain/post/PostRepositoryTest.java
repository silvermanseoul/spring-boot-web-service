package io.silverman.springbootwebservice.domain.post;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest
class PostRepositoryTest {

    @Autowired
    PostRepository postRepository;

    @AfterEach
    void cleanUp() {
        postRepository.deleteAll();
    }

    @Test
    void 게시글_저장_조회() {
        // Given
        String title = "테스트 제목";
        String content = "테스트 본문";

        postRepository.save(Post.builder()
                .title(title)
                .content(content)
                .build());

        // When
        List<Post> posts = postRepository.findAll();

        // Then
        Post post = posts.get(0);
        assertEquals(title, post.getTitle());
        assertEquals(content, post.getContent());
        assertNull(post.getAuthor());
    }
}