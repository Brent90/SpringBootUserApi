package com.slinger.app.api.v1.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentDTO {

    private String body;
    private String commentUrl;
    private String postUrl;
    private String userUrl;

}
