package com.zhp.authority.dao;

import com.zhp.authority.model.AuthorityApi;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AuthorityApiMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(AuthorityApi record);

    int insertSelective(AuthorityApi record);

    AuthorityApi selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(AuthorityApi record);

    int updateByPrimaryKey(AuthorityApi record);

    List<AuthorityApi> listByAuthId(@Param("authorityId") String authorityId);
}