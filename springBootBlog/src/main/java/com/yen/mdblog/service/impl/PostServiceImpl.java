package com.yen.mdblog.service.impl;

import com.yen.mdblog.mapper.PostMapper;
import com.yen.mdblog.service.PostService;
import com.yen.mdblog.entity.Po.Post;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class PostServiceImpl implements PostService {

    @Autowired
    PostMapper postMapper;

    @Override
    public List<Post> getPostsById(Integer id) {
        return postMapper.findById(id);
    }

    @Override
    public List<Post> getAllPost() {
        return postMapper.getAllPosts();
    }

    @Override
    public int getTotalPost() {

        return postMapper.getPostCount();
    }

    @Override
    public void savePost(Post post) {

        postMapper.insertPost(post);
    }

    @Override
    public void updatePost(Post post) {

        //log.info(">>> updatePost : post = {}", post);
        postMapper.updatePost(post);
    }

}
