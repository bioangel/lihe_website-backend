package com.zhp.authority.dbservice;

import com.zhp.authority.dao.AuthRoleMapper;
import com.zhp.authority.dto.RoleAccountSaveDTO;
import com.zhp.authority.dto.RoleAuthSaveDTO;
import com.zhp.authority.model.AuthRole;
import com.zhp.common.utils.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by zhouhh2 on 2016/6/27.
 */

@Service
@Transactional
public class RoleService {
    @Autowired
    private AuthRoleMapper roleDao;

    public List<AuthRole> getRoles() {
        return roleDao.selectAll();
    }

    public List<AuthRole> getRolesWithEnable() {
        return roleDao.selectAllWithEnable();
    }

    public void saveAccount(RoleAccountSaveDTO record) {
        roleDao.deleteAccountRole(record.getUuid());
        if (record.getRoleList() != null && record.getRoleList().length > 0) {
            roleDao.batchSaveAccountRole(record);
        }
    }

    public void saveAccountByCreateUser(RoleAccountSaveDTO record) {
        if (record.getRoleList() != null && record.getRoleList().length > 0) {
            roleDao.batchSaveAccountRole(record);
        }
    }

    public int updateRoleByPrimaryKeySelective(AuthRole record) {
        return roleDao.updateByPrimaryKeySelective(record);
    }

    public int saveRoleAuth(RoleAuthSaveDTO record) {
        String rid = CommonUtils.getUuid();
        roleDao.insertRoleAuth(rid, record.getMenuList());
        return roleDao.insertSelective(new AuthRole(rid, 0, record.getName(), record.getEnable()));
    }

    public void modifyRoleAuth(RoleAuthSaveDTO record) {
        roleDao.deleteRoleAuthById(record.getRid());
        roleDao.insertRoleAuth(record.getRid(), record.getMenuList());
    }

    public void deleteRoleById(List<String> ridList) {
        roleDao.deleteRoleById(ridList);
        roleDao.deleteRoleAccountById(ridList);
        roleDao.deleteRoleAuthByIdList(ridList);
        roleDao.deleteRoleDataById(ridList);
    }

    public AuthRole selectByPrimaryKey(String id) {
        return roleDao.selectByPrimaryKey(id);
    }
}
