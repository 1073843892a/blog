package com.lvlei.blog.service;

import com.lvlei.blog.po.Blog;
import com.lvlei.blog.po.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TagService {
    Tag saveTag(Tag tag);
    Tag getTag(Long id);
    void deleteTag(Long id);
    Tag updateTag(Long id,Tag tag);
    Page<Tag> listTag(Pageable pageable);
    Tag finTagByName(String name);
    List<Tag>listTagTop(Integer size);
    List<Tag> listTag();
    List<Tag> listTag(String ids);

}
