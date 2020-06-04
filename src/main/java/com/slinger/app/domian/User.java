package com.slinger.app.domian;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String username;
    private String email;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private List<Post> posts;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private List<Comment> comments;

    public User addPost(Post post) {
        if(posts == null) {
            posts = new ArrayList<>();
        }

        post.setUser(this);
        posts.add(post);
        return this;
    }

    public void addComment(Comment comment) {
        if(comments == null) {
            comments = new ArrayList<>();
        }

        comment.setUser(this);
        comments.add(comment);
    }


}
