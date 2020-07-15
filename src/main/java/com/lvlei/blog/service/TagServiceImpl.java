package com.lvlei.blog.service;

import com.lvlei.blog.NotFoundException;
import com.lvlei.blog.dao.TagRepository;
import com.lvlei.blog.po.Tag;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class TagServiceImpl implements TagService {
    @Autowired
    TagRepository tagRepository;

    @Transactional
    @Override
    public Tag saveTag(Tag tag) {
        return   tagRepository.save(tag);
    }

    @Transactional
    @Override
    public Tag getTag(Long id) {
        return  tagRepository.getOne(id);
    }

    @Transactional
    @Override
    public void deleteTag(Long id) {
        tagRepository.delete(id);
    }

    @Transactional
    @Override
    public Tag updateTag(Long id, Tag tag) {
        Tag t=tagRepository.getOne(id);
        if(t==null) {
            throw new NotFoundException("没有该tag");
        }
        BeanUtils.copyProperties(tag,t);
        return tagRepository.save(t);
    }

    @Transactional
    @Override//就是全部都调用tagRepository就可以了，
    public Page<Tag> listTag(Pageable pageable) {
        return  tagRepository.findAll(pageable);
    }

    @Transactional
    @Override
    public Tag finTagByName(String name) {
        return tagRepository.findTagByName(name);
    }

    @Override
    public List<Tag> listTagTop(Integer size) {
        Sort sort=new Sort(Sort.DEFAULT_DIRECTION,"blogs.size");
        Pageable pageable=new PageRequest(0,size,sort);
        return tagRepository.findTop(pageable);
    }

    @Override
    public List<Tag> listTag() {
        return tagRepository.findAll();
    }

    @Override
    public List<Tag> listTag(String ids) {//1，2，3的方式
        return tagRepository.findAll(converToList(ids));
    }

    private  List<Long> converToList(String ids){
        List<Long> list=new ArrayList<>();
        if(!"".equals(ids)&&ids!=null){
            String[] idarray=ids.split(",");
            for(int i=0;i<idarray.length;i++){
                list.add(new Long(idarray[i]));
            }
        }
        return list;
    }
}
