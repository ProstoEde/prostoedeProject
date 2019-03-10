package com.prostoede.dao.mapper;

import com.prostoede.entity.Base;
import com.prostoede.entity.Users;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 *
 * @author prostoede
 */
public interface UsersDaoMapper {

    public List<Users> selectUsers(@Param("id") Integer id);

    public List<Users> selectByName(@Param("name") String name);

    public Users insert(@Param("test") Base test);

    public Long count();

}
