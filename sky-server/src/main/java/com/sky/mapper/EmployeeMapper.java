package com.sky.mapper;

import com.sky.entity.Employee;
import org.apache.ibatis.annotations.Insert;
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


    /**
     * 插入员工信息
     * @param employee
     */
    @Insert("insert into employee (name,username,password,phone,sex,id_number,status," +
            "create_time,update_time,create_user,update_user)" +
            "values"+
            "(#{name},#{username},#{password},#{phone},#{sex},#{idNumber},#{status}," +
            "#{createTime},#{updateTime},#{createUser},#{updateUser}) ") //单表的插入操作,注意顺序
    void insert(Employee employee);

}
