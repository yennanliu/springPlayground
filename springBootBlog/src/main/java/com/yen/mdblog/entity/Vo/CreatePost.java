package com.yen.mdblog.entity.Vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreatePost {

    private long id;
    private String title;
    private String content;
    private String synopsis;
    private Long authorId;
    private LocalDateTime dateTime;
}
