package com.lvlei.blog.service;

import com.lvlei.blog.po.Type;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TypeService {
        Type saveType(Type type);

        Type findByName(String name);

        List<Type> listType();

        Type updateType(Long id,Type type);

        void deleteType(Long id);

        Page<Type> listType(Pageable pageable);

        Type getType(Long id);

        List<Type> listTypeTop(Integer size);
}
