package com.zhp.authority.dao;


import com.zhp.authority.model.AuthOrganization;

import java.util.List;

/**
 * Created by cs on 17-3-22.
 */
public interface AuthOrganizationMapper {

    int insertAuthOrganization(AuthOrganization authOrganization);

    int deleteAuthOrganizationById(Integer id);

    int updateAuthOrganization(AuthOrganization authOrganization);

    List<AuthOrganization> selectAuthOrganizationList();

    AuthOrganization selectAuthOrganizationById(Integer id);
}
