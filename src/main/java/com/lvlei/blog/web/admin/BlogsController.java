package com.lvlei.blog.web.admin;

import com.lvlei.blog.po.Blog;
import com.lvlei.blog.po.Type;
import com.lvlei.blog.po.User;
import com.lvlei.blog.service.BlogService;
import com.lvlei.blog.service.TagService;
import com.lvlei.blog.service.TypeService;
import com.lvlei.blog.vo.BlogQuery;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
public class BlogsController {

    private static final String INPUT="admin/blogs-input";
    private static final String LIST="admin/blogs";
    private static final String REDIRECT_LIST="redirect:/admin/blogs";


    @Autowired
    private BlogService blogService;

    @Autowired
    private TypeService typeService;

    @Autowired
    private TagService tagService;

    @GetMapping("/blogs")
    public String blogs(@PageableDefault(size=5,sort={"updateTime"},direction = Sort.Direction.DESC)
                                    Model model, Pageable pageable, BlogQuery blog){
        model.addAttribute("types",typeService.listType());
        model.addAttribute("page",blogService.listBlog(pageable,blog));
        return LIST;
    }
//    只改变收缩的 表单里面东西，整个页面不变
    @PostMapping("/blogs/search")
    public String search(@PageableDefault(size=5,sort={"updateTime"},direction = Sort.Direction.DESC)
                                Model model, Pageable pageable, BlogQuery blog){
        model.addAttribute("page",blogService.listBlog(pageable,blog));
        return "admin/blogs :: blogList";//返回一个片段
    }
//每次初始化一个Blog
    @GetMapping("/blogs/input")
    public String input(Model model){
        setTypeAndTag(model);
        model.addAttribute("blog",new Blog());
        return INPUT;
    }

    @PostMapping("blogs")
    public String post(Blog blog, HttpSession session, RedirectAttributes attributes){
        blog.setUser((User) session.getAttribute("user"));
        blog.setType(typeService.getType(blog.getType().getId()));
        blog.setTags(tagService.listTag(blog.getTagIds()));
        Blog b=blogService.saveBlog(blog);
        if(b.getId()!=null){
            blogService.updateBlog(b.getId(),b);
        }
        else blogService.saveBlog(b);
        if(b==null){
            attributes.addFlashAttribute("message","新增失败");
        }
        else {
            attributes.addFlashAttribute("message","新增成功");
        }
        return REDIRECT_LIST;
    }

    private void setTypeAndTag(Model model){
        model.addAttribute("types",typeService.listType());
        model.addAttribute("tags",tagService.listTag());
    }

    @GetMapping("/blogs/{id}/input")
    public String editInput(@PathVariable Long id,Model model){
       setTypeAndTag(model);
       Blog blog=blogService.getBlog(id);
       blog.init();
        model.addAttribute("blog",blogService.getBlog(id));
        return INPUT;
    }

    @GetMapping("/blogs/{id}/delete")
    public String delete(@PathVariable Long id,RedirectAttributes attributes){
        blogService.deleteBlog(id);
        attributes.addFlashAttribute("message","删除成功");
        return REDIRECT_LIST;
    }

}
