package com.sky.controller.admin;

import com.sky.constant.JwtClaimsConstant;
import com.sky.dto.EmployeeDTO;
import com.sky.dto.EmployeeLoginDTO;
import com.sky.dto.EmployeePageQueryDTO;
import com.sky.entity.Employee;
import com.sky.properties.JwtProperties;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.EmployeeService;
import com.sky.utils.JwtUtil;
import com.sky.vo.EmployeeLoginVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 员工管理
 */
@RestController
@RequestMapping("/admin/employee")
@Slf4j
@Api(tags = "Employee related interface")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private JwtProperties jwtProperties;

    /**
     * 登录
     *
     * @param employeeLoginDTO
     * @return
     */
    @ApiOperation(value = "employee login ")
    @PostMapping("/login")
    public Result<EmployeeLoginVO> login(@RequestBody EmployeeLoginDTO employeeLoginDTO) {
        log.info("员工登录：{}", employeeLoginDTO);

        //1. 调用server中的login方法，debug模式下ctral+alt+B进入实现的Impl
        Employee employee = employeeService.login(employeeLoginDTO);

        //2. 登录成功后，生成jwt令牌
        Map<String, Object> claims = new HashMap<>();
        claims.put(JwtClaimsConstant.EMP_ID, employee.getId());
        //2.1 生成token
        String token = JwtUtil.createJWT(
                jwtProperties.getAdminSecretKey(),
                jwtProperties.getAdminTtl(), //令牌过期时间
                claims);

        //3. 后端封装相应给前端页面
        EmployeeLoginVO employeeLoginVO = EmployeeLoginVO.builder()//VO对象上加上Builder注解，使用builder()构建器的方式去封装
                .id(employee.getId())
                .userName(employee.getUsername())
                .name(employee.getName())
                .token(token)
                .build();//build()构建好

        return Result.success(employeeLoginVO); //Result.success（）将后端的结果封装在success对象里面
    }

    /**
     * 退出
     *
     * @return
     */
    @PostMapping("/logout")
    @ApiOperation(value = "employee logout")
    public Result<String> logout() {
        return Result.success();
    }


    /**
     * 新增员工
     *
     * @return
     */
    @PostMapping
    @ApiOperation(value = "add a new employee")
    public Result addNewEmployee(@RequestBody EmployeeDTO employeeDTO){ //Json类型的数据
        log.info("新增员工：{}",employeeDTO);
        //真正的新增操作
        Employee employee = employeeService.save(employeeDTO); //alt+enter
        return Result.success();
    }

    /**
     * 分页查询员工
     *
     * @return
     */
    @GetMapping("/page")
    @ApiOperation(value = "Page Query Employee")
    public Result<PageResult> queryEmployee(EmployeePageQueryDTO employeePageQueryDTO){//query的数据格式

        log.info("分页请求员工数据，参数为：{}",employeePageQueryDTO);
        //将分页查询的结果，封装成PageResult
        PageResult pageResult = employeeService.pageQuery(employeePageQueryDTO);

        return Result.success(pageResult);
    }
}
