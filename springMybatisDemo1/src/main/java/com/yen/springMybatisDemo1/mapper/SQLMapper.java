package com.yen.springMybatisDemo1.mapper;

// https://www.youtube.com/watch?v=sgmK0NNNQdE&list=PLmOn9nNkQxJEWFBs6hVmDC5m8SbbIiDwY&index=35
// https://www.youtube.com/watch?v=8XqiR7n3O5U&list=PLmOn9nNkQxJEWFBs6hVmDC5m8SbbIiDwY&index=36

import com.yen.springMybatisDemo1.bean.MyUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface SQLMapper {

    /** select user by fuzzy search */
    List<MyUser> getUserByLike(@Param("username") String username);

    /** bulk delete */
    int deleteMulti(@Param("ids") String ids);  // e.g. : "id1,id2"
}
