package com.sky.mapper;

import com.sky.entity.Employee;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface EmployeeMapper {

    /**
     * 根据用户名查询员工
     * @param username
     * @return
     */
    //通过注解的方式(简单的sql），编写了SQL语句，查询数据库
    @Select("select * from employee where username = #{username}")
    Employee getByUsername(String username);

}
