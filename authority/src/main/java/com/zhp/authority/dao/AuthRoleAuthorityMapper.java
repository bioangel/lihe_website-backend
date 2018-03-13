package com.zhp.authority.dao;


import com.zhp.authority.model.AuthRoleAuthority;
import com.zhp.common.model.CommonKvDTO;

import java.util.List;

public interface AuthRoleAuthorityMapper {
    int insert(AuthRoleAuthority record);

    int insertSelective(AuthRoleAuthority record);

    List<CommonKvDTO> selectUidApi();
}