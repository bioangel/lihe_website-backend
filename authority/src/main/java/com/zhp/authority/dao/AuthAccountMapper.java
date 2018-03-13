package com.zhp.authority.dao;

import com.zhp.authority.model.AccountRole;
import com.zhp.authority.model.AuthAccount;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AuthAccountMapper {
    int deleteByPrimaryKey(String id);

    int insert(AuthAccount record);

    int insertSelective(AuthAccount record);

    AuthAccount selectByPrimaryKey(String id);

    List<AuthAccount> selectByUsername(String username);

    List<AuthAccount> selectByUid(String uid);

    int updateByPrimaryKeySelective(AuthAccount record);

    int updateByPrimaryKey(AuthAccount record);

    List<AccountRole> selectAccountRole();

    void deleteAccountRoleById(@Param("uidList") List<String> uidList);

    void deleteAccountById(@Param("uidList") List<String> uidList);

    List<AuthAccount> selectByRoleId(String rid);

    List<String> selectSameOryByUsername(String username);
}