package com.zhp.authority.dao;

import com.zhp.authority.model.AuthorityApi;

public interface AuthorityApiMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(AuthorityApi record);

    int insertSelective(AuthorityApi record);

    AuthorityApi selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(AuthorityApi record);

    int updateByPrimaryKey(AuthorityApi record);
}