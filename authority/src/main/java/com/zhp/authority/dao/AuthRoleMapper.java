package com.zhp.authority.dao;

import com.zhp.authority.dto.RoleAccountSaveDTO;
import com.zhp.authority.model.AuthRole;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AuthRoleMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(AuthRole record);

    int insertSelective(AuthRole record);

    AuthRole selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(AuthRole record);

    int updateByPrimaryKey(AuthRole record);

    List<AuthRole> selectAll();

    List<AuthRole> selectAllWithEnable();

    void batchSaveAccountRole(RoleAccountSaveDTO record);

    void deleteAccountRole(String uuid);

    int insertRoleAuth(@Param("rid") String rid, @Param("menuList") List<String> menuList);

    void deleteRoleAuthById(String rid);

    void deleteRoleAuthByIdList(@Param("ridList") List<String> ridList);

    void deleteRoleById(@Param("ridList") List<String> ridList);

    void deleteRoleAccountById(@Param("ridList") List<String> ridList);

    void deleteRoleDataById(@Param("ridList") List<String> ridList);
}