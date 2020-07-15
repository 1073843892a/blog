package com.lvlei.blog.web.admin;

import com.lvlei.blog.po.Type;
import com.lvlei.blog.service.TypeService;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/admin")
public class TypeController {

    @Autowired
    private TypeService typeService;

    @GetMapping("/types")
    public String types(@PageableDefault(size=5,sort = {"id"},direction = Sort.Direction.DESC)Pageable pageable,Model model){
        model.addAttribute("page",typeService.listType(pageable));
        return "admin/types";
    }
    @GetMapping("/types/{id}/input")
    //@PathVariable接收id
    public String editInput(@PathVariable Long id, Model model){
        model.addAttribute("type",typeService.getType(id));
        return "admin/types-input";
    }


    @GetMapping("/types/input")
    public String input(Model model){
        model.addAttribute("type",new Type());
        return "admin/types-input";
    }

// type是要被传入要被保持的实体
    @PostMapping("/types")
    public String  post(@Valid Type type, BindingResult bindingResult, RedirectAttributes attributes){
        Type t1=typeService.findByName(type.getName());
        if(t1==null){
            typeService.saveType(type);
        }
        else {
            bindingResult.rejectValue("name","nameErroe","不能重复添加同名type");
        }
        if(bindingResult.hasErrors())return "admin/types-input";
        Type t=typeService.saveType(type);
        if(t==null){
            attributes.addFlashAttribute("message","新增失败");
        }
        else {
            attributes.addFlashAttribute("message","新增成功");
        }
     return "redirect:/admin/types";
    }

    @PostMapping("/types/{id}")
    //BindingResult 必须和type 挨到一起
    public String  editPost(@Valid Type type, BindingResult bindingResult,@PathVariable Long id,RedirectAttributes attributes){
        Type t1=typeService.findByName(type.getName());
        if(t1!=null){
            bindingResult.rejectValue("name","nameErroe","不能重复添加同名type");
        }
        if(bindingResult.hasErrors())return "admin/types-input";
        Type t=typeService.updateType(id,type);
        if(t==null){
            attributes.addFlashAttribute("message","更新失败");
        }
        else {
            attributes.addFlashAttribute("message","更新成功");
        }
        return "redirect:/admin/types";
    }

    @GetMapping("types/{id}/delete")
    public String  delete(@PathVariable Long id,RedirectAttributes attributes){
        typeService.deleteType(id);
        attributes.addFlashAttribute("message","删除成功");
        return "redirect:/admin/types";
    }


}
