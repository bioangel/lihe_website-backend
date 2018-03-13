package com.zhp.authority.dbservice;

import com.zhp.authority.dao.AuthOrganizationMapper;
import com.zhp.authority.model.AuthOrganization;
import com.zhp.cache.base.CacheConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by cs on 17-3-22.
 */
@Service
@Transactional
public class AuthOrganizationService {
    @Autowired
    AuthOrganizationMapper authOrganizationMapper;

    @CacheEvict(value = CacheConstants.CACHE_PREFIX, key = "'orgList'")
    public int saveOrg(AuthOrganization authOrganization) {
        int result = authOrganizationMapper.insertAuthOrganization(makeUpOrgInfo(authOrganization));
        return result;
    }

    @CacheEvict(value = CacheConstants.CACHE_PREFIX, key = "'orgList'")
    public int deleteOrg(Integer id) {
        int result = authOrganizationMapper.deleteAuthOrganizationById(id);
        return result;
    }

    @CacheEvict(value = CacheConstants.CACHE_PREFIX, key = "'orgList'")
    public int updateOrg(AuthOrganization authOrganization) {
        int result = authOrganizationMapper.updateAuthOrganization(makeUpOrgInfo(authOrganization));
        return result;
    }

    public AuthOrganization getOrgById(Integer id) {
        return authOrganizationMapper.selectAuthOrganizationById(id);
    }

    @Cacheable(value = CacheConstants.CACHE_PREFIX, key = "'orgList'")
    public List<AuthOrganization> getOrgList() {
        return authOrganizationMapper.selectAuthOrganizationList();
    }

    private AuthOrganization makeUpOrgInfo(AuthOrganization authOrganization) {
        authOrganization.setEnable(true);
        authOrganization.setLevelcode("");
        authOrganization.setParentid(0);
        authOrganization.setVersion(0);
        authOrganization.setPosition(0);
        authOrganization.setThevalue("");
        return authOrganization;
    }
}
