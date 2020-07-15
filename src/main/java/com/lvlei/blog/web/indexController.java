package com.lvlei.blog.web;


import com.lvlei.blog.NotFoundException;
import com.lvlei.blog.service.BlogService;
import com.lvlei.blog.service.TagService;
import com.lvlei.blog.service.TypeService;
import com.lvlei.blog.vo.BlogQuery;
import org.aspectj.weaver.ast.Not;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class indexController {

    @Autowired
    private BlogService blogService;

    @Autowired
    private TypeService typeService;

    @Autowired
    private TagService tagService;
    @GetMapping("/")
    public String index(@PageableDefault(size=5,sort={"updateTime"},direction = Sort.Direction.DESC)
                                    Model model, Pageable pageable){
    model.addAttribute("page",blogService.listBlog(pageable));
    model.addAttribute("types",typeService.listTypeTop(6));
    model.addAttribute("tags",tagService.listTagTop(10));
    model.addAttribute("recommendBlogs",blogService.listRecommendBlogTop(8));
    return "index";
    }

    @PostMapping("/search")
    public String srarch(@PageableDefault(size=5,sort={"updateTime"},direction = Sort.Direction.DESC)
                                 Model model, @RequestParam String query, Pageable pageable){
        model.addAttribute("page",blogService.listBlog("%"+query+"%",pageable));
        model.addAttribute("query",query);
        return "search";
    }

    @GetMapping("/blog/{id}")
    public String blog(@PathVariable Long id,Model model){
        model.addAttribute("blog",blogService.getAndConvert(id));
        return "blog";
    }

    @GetMapping("/footer/newblog")
    public String newblogs(Model model){
        model.addAttribute("newblogs",blogService.listRecommendBlogTop(3));
        return "_fragment :: newblogList";
    }
}
