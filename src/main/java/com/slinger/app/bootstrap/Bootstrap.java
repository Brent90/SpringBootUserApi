package com.slinger.app.bootstrap;

import com.slinger.app.domian.Comment;
import com.slinger.app.domian.Post;
import com.slinger.app.domian.User;
import com.slinger.app.repositories.CommentRepository;
import com.slinger.app.repositories.PostRepository;
import com.slinger.app.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class Bootstrap implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    public Bootstrap(UserRepository userRepository, PostRepository postRepository, CommentRepository commentRepository) {
        this.userRepository = userRepository;
        this.postRepository = postRepository;
        this.commentRepository = commentRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        loadData();
        log.debug("Load users, size: " + userRepository.count());
        log.debug("Load posts, size: " + postRepository.count());
    }

    private void loadData() {
        //load users
        User user1 = User.builder().id(1L).name("Brent").username("brent90").email("brent@gmail.com").build();
        User user2 = User.builder().id(2L).name("Petey").username("dog2").email("dog@gmail.com").build();
        User user3 = User.builder().id(3L).name("Paul").username("gilbert78").email("gilbert@gmail.com").build();
        User user4 = User.builder().id(4L).name("Jon").username("jon374").email("jon@gmail.com").build();
        User user5 = User.builder().id(5L).name("Tessa").username("flower_head34").email("tessa@gmail.com").build();
        User user6 = User.builder().id(6L).name("Rebecca").username("spring_girl945").email("rebecca@gmail.com").build();
        User user7 = User.builder().id(7L).name("Kate").username("linux_girl34").email("kde@gmail.com").build();
        User user8 = User.builder().id(8L).name("Marty").username("guitar_man343").email("gibsonman@gmail.com").build();
        User user9 = User.builder().id(9L).name("Tony").username("ironman34").email("steelman@yahoo.com").build();
        User user10 = User.builder().id(10L).name("NA").username("docker002").email("whale@gmail.com").build();

        userRepository.save(user1);
        userRepository.save(user2);
        userRepository.save(user3);
        userRepository.save(user4);
        userRepository.save(user5);
        userRepository.save(user6);
        userRepository.save(user7);
        userRepository.save(user8);
        userRepository.save(user9);
        userRepository.save(user10);


        //load Posts
        Post post1 = Post.builder().id(1L).title("Title 1").body("Post 1 body").build();
        post1.setUser(user2);

        Post post2 = Post.builder().id(2L).title("Title 2").body("Post 2 body").build();
        post2.setUser(user2);

        Post post3 = Post.builder().id(3L).title("Title 3").body("Post 3 body").build();
        post3.setUser(user1);

        Post post4 = Post.builder().id(4L).title("Title 4").body("Post 4 body").build();
        post4.setUser(user5);

        Post post5 = Post.builder().id(5L).title("Title 5").body("Post 5 body").build();
        post5.setUser(user7);

        Post post6 = Post.builder().id(6L).title("Title 6").body("Post 6 body").build();
        post6.setUser(user9);

        postRepository.save(post1);
        postRepository.save(post2);
        postRepository.save(post3);
        postRepository.save(post4);
        postRepository.save(post5);
        postRepository.save(post6);

        //load comments
        Comment comment1 = Comment.builder().id(1L).body("Great Post").build();
        post2.addComment(comment1);
        user2.addComment(comment1);

        Comment comment2 = Comment.builder().id(2L).body("Bad Post").build();
        post1.addComment(comment2);
        user2.addComment(comment2);

        Comment comment3 = Comment.builder().id(3L).body("Okay Post").build();
        post5.addComment(comment3);
        user7.addComment(comment3);

        Comment comment4 = Comment.builder().id(4L).body("Delete this Post").build();
        post3.addComment(comment4);
        user2.addComment(comment4);

        commentRepository.save(comment1);
        commentRepository.save(comment2);
        commentRepository.save(comment3);
        commentRepository.save(comment4);


    }
}




























