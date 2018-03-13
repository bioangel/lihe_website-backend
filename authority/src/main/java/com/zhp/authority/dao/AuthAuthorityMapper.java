package com.zhp.authority.dao;

import com.zhp.authority.dto.AuthAuthorityDTO;
import com.zhp.authority.model.AuthAuthority;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AuthAuthorityMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(AuthAuthority record);

    int insertSelective(AuthAuthority record);

    AuthAuthority selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(AuthAuthority record);

    int updateByPrimaryKey(AuthAuthority record);

    List<AuthAuthority> findMenuAll();

    List<AuthAuthority> findMenuByRoleId(@Param("roleId") List<String> roleId);

    List<AuthAuthorityDTO> findAuthTree(String id);

    List<String> selectAuthByRoleId(String rid);

    List<AuthAuthorityDTO> findAllAuth();

    List<AuthAuthority> findAll();

    void deleteAuth(@Param("aidList") List<String> aidList);

    void deleteAuthWithRole(@Param("aidList") List<String> aidList);
}