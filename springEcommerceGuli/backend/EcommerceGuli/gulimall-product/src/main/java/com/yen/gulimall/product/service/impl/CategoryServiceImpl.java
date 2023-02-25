package com.yen.gulimall.product.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yen.gulimall.common.utils.PageUtils;
import com.yen.gulimall.common.utils.Query;
import com.yen.gulimall.product.dao.CategoryDao;
import com.yen.gulimall.product.entity.CategoryEntity;
import com.yen.gulimall.product.service.CategoryService;

// https://youtu.be/5aWkhC7plsc?t=283

@Service("categoryService")
public class CategoryServiceImpl extends ServiceImpl<CategoryDao, CategoryEntity> implements CategoryService {

    // since CategoryDao extends from BaseMapper, so we can use BaseMapper below instead for querying data
    // (public interface CategoryDao extends BaseMapper<CategoryEntity>)
    //@Autowired
    //CategoryDao categoryDao;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CategoryEntity> page = this.page(
                new Query<CategoryEntity>().getPage(params),
                new QueryWrapper<CategoryEntity>()
        );

        return new PageUtils(page);
    }

    /**
     *  method :
     *   - query all categories and their sub categories, then return as tree structure
     */
    @Override
    public List<CategoryEntity> listWithTree() {

        // step 1) get all categories
        List<CategoryEntity> entities =  baseMapper.selectList(null); //NOTE : no query condition (selectList(null)) -> query all data
        System.out.println(">>> entities = " + entities);

        // step 2) wrap as parent-child tree structure
        return entities;
    }

}