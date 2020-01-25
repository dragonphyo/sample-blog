package com.example.blogsample.controller;

import com.example.blogsample.domain.Blog;
import com.example.blogsample.service.AuthorService;
import com.example.blogsample.service.BlogService;
import org.dom4j.rule.Mode;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;

@Controller
public class BlogController {
    private final BlogService blogService;
    private final AuthorService authorService;

    public BlogController(BlogService blogService, AuthorService authorService) {
        this.blogService = blogService;
        this.authorService = authorService;
    }
    @GetMapping("/blog")
    public String create(Model model){
        model.addAttribute("blog",new Blog());
        model.addAttribute("authors",authorService.findAll());
        return "admin/blogForm";
    }
    @PostMapping("/blog")
    public String process(@Valid Blog blog, BindingResult result, Model model,RedirectAttributes redirectAttributes){
        if(result.hasErrors()){
            model.addAttribute("authors",authorService.findAll());
            return "admin/blogForm";

        }
        blogService.create(blog);
        redirectAttributes.addFlashAttribute("success",true);
        return "redirect:/blogs";
    }
    @GetMapping("/blogs")
    public String showAllBlogs(Model model){
        model.addAttribute("success",model.containsAttribute("success"));
        model.addAttribute("blogs",blogService.findAll());
        model.addAttribute("delete",model.containsAttribute("delete"));
        model.addAttribute("update",model.containsAttribute("update"));
        return "admin/blogs";
    }
    @GetMapping("/blog/{id}")
    public String showBlogDetails(@PathVariable  int id, Model model){
        model.addAttribute("blog",blogService.findById(id));
        return "admin/blog";
    }
    @GetMapping("/blog/delete/{cid}")
    public String deleteBlog(@PathVariable("cid") int id,Model model,RedirectAttributes redirectAttributes){
        blogService.deleteById(id);
        //model.addAttribute("blogs",blogService.findAll());
        redirectAttributes.addFlashAttribute("delete",true);
        return "redirect:/blogs";
    }

    @GetMapping("/blog/update/{id}")
    public String updateBlog(@PathVariable int id,Model model){
        model.addAttribute("blog",blogService.findById(id));
        model.addAttribute("authors",authorService.findAll());
        this.bid=id;
        return "admin/updateForm";
    }

    int bid;
    @PostMapping("/blog/update")
    public String processUpdateBlog(Blog blog,RedirectAttributes redirectAttributes){
        blogService.update(bid,blog);
        redirectAttributes.addFlashAttribute("update",true);
        return "redirect:/blogs";
    }
    @ModelAttribute(name = "categories")
    public List<String> categoryNames(){
        return Arrays.asList("Horror","Comedy","Triller","Tragedy","Romance","Adventure","Young Adult","Action","Travelling","Sports");
    }
}
