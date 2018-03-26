package com.zhp.authority.dbservice;

import com.zhp.authority.dao.AuthAuthorityMapper;
import com.zhp.authority.dao.AuthRoleAuthorityMapper;
import com.zhp.authority.dao.AuthorityApiMapper;
import com.zhp.authority.dto.AuthAuthorityDTO;
import com.zhp.authority.model.AuthAuthority;
import com.zhp.authority.model.AuthorityApi;
import com.zhp.authority.model.Menu;
import com.zhp.common.model.CommonKvDTO;
import com.zhp.common.utils.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by zhouhh2 on 2016/6/27.
 */

@Service
@Transactional
public class AuthorityService {
    @Autowired
    private AuthAuthorityMapper authorityDao;

    @Autowired
    private AuthRoleAuthorityMapper authRoleAuthorityDao;

    @Autowired
    private AuthorityApiMapper authorityApiDao;

    public List<Menu> findLeftMenu(List<String> roleId) {
        List<AuthAuthority> authorityList = authorityDao.findMenuByRoleId(roleId);
        return authorityList.stream()
                .filter(authAuthority -> authAuthority.getLevelcode().equals("1"))
                .map(authAuthority -> initMenu(authAuthority, authorityList))
                .collect(Collectors.toList());
    }

    private Menu initMenu(AuthAuthority authAuthority, List<AuthAuthority> authorities) {
        Menu menu = new Menu();
        menu.setTitle(authAuthority.getName());
        menu.setId(authAuthority.getMid());
        menu.setFa(authAuthority.getItemicon());
        menu.setHasSub(false);
        menu.setTargetId("");
        menu.setTemp(authAuthority.getMatchurl());
        if (authAuthority.getThevalue().equals("true")) {
            menu.setHasSub(true);
            menu.setSub(initSubMenu(authAuthority.getId(), authorities));
        }
        return menu;
    }

    private List<Menu> initSubMenu(String parentId, List<AuthAuthority> authorities) {
        if (authorities == null || authorities.size() == 0) {
            return null;
        }
        List<Menu> list = new ArrayList<>();
        for (AuthAuthority authAuthority : authorities) {
            if (parentId.equals(authAuthority.getParentid())) {
                list.add(initMenu(authAuthority, null));
            }
        }
        return list;
    }

    public List<AuthAuthority> findAll() {
        return authorityDao.findMenuAll();
    }

    public List<AuthAuthorityDTO> findAuthTreeByID(String id) {
        return authorityDao.findAuthTree(id);
    }

    public List<AuthAuthorityDTO> findAuthTreeAll() {
        List<AuthAuthorityDTO> authAuthorityDTOList = authorityDao.findAllAuth();
        return getAuthAuthorityDTOList("0", authAuthorityDTOList);
    }

    public List<AuthAuthorityDTO> loadAuthTree(String id) {
        List<AuthAuthorityDTO> authAuthorityDTOList = findAuthTreeByID(id);
        return getAuthAuthorityDTOList(id, authAuthorityDTOList);
    }

    private List<AuthAuthorityDTO> getAuthAuthorityDTOList(String id, List<AuthAuthorityDTO> authAuthorityDTOList) {
        List<AuthAuthorityDTO> result = authAuthorityDTOList.stream()
                .filter(e -> id.equals(e.getParentid())).collect(Collectors.toList());
        result.forEach(e ->
                findAuthById(e.getId(), authAuthorityDTOList, e)
        );
        return result;
    }

    private void findAuthById(String id, List<AuthAuthorityDTO> lst, AuthAuthorityDTO obj) {
        List<AuthAuthorityDTO> tmp = lst.stream()
                .filter(e -> id.equals(e.getParentid())).collect(Collectors.toList());
        if (tmp == null || tmp.size() == 0) {
            return;
        }
        tmp.forEach(e -> findAuthById(e.getId(), lst, e));
        obj.setMenuList(tmp);
    }

    public List<String> selectAuthByRoleId(String rid) {
        return authorityDao.selectAuthByRoleId(rid);
    }

    public int insertAuth(AuthAuthority data) {
        data.setEnable(true);
        if ("0".equals(data.getParentid())) {
            data.setLevelcode("1");
        } else {
            data.setLevelcode("2");
        }
        data.setId(CommonUtils.getUuid());
        return authorityDao.insertSelective(data);
    }

    public int updateAuth(AuthAuthority data) {
        return authorityDao.updateByPrimaryKeySelective(data);
    }

    public List<AuthAuthority> findAllAuth() {
        return authorityDao.findAll();
    }

    public void deleteAuth(List<String> aidList) {
        authorityDao.deleteAuth(aidList);
        authorityDao.deleteAuthWithRole(aidList);
    }

    public Map<String, List<String>> getUidApi() {
        List<CommonKvDTO> lst = authRoleAuthorityDao.selectUidApi();
        if (lst != null && lst.size() > 0) {
            Map<String, List<String>> uapi = lst.stream()
                    .collect(Collectors.groupingBy(CommonKvDTO::getName, Collectors
                            .mapping(p -> p.getStrValue(), Collectors.toList())));
            uapi.forEach((key, value) -> value.add("/account/test"));
            return uapi;
        }
        return null;

    }

    public List<AuthorityApi> getAuthorityApiByAuthId(String authId) {
        return authorityApiDao.listByAuthId(authId);
    }
}
