package com.example.blogsample.service;

import com.example.blogsample.domain.Blog;
import com.example.blogsample.repository.BlogRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BlogServiceImpl implements BlogService {
    private final BlogRepository blogRepository;

    public BlogServiceImpl(BlogRepository blogRepository) {
        this.blogRepository = blogRepository;
    }

    @Override
    public Blog create(Blog blog) {
        return blogRepository.save(blog);
    }

    @Override
    public Blog findById(int id) {
        return blogRepository.getOne(id);
    }

    @Override
    public List<Blog> findAll() {
        return blogRepository.findAll();
    }

    @Override
    public void deleteById(int id) {
        blogRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void update(int id, Blog blog) {
        Blog b = findById(id);
        b.setAuthor(blog.getAuthor());
        b.setCategory(blog.getCategory());
        b.setTitle(blog.getTitle());
        b.setBody(blog.getBody());
    }
}
