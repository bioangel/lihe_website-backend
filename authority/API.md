# authority 模块API
## /account
* 获取所有账户列表和角色列表
* GET
* header: token
* 返回数据
```
{
    "userList": [
        {
            "uuid": "8d1f809e5c5a4ba18f92976146b88e87",
            "nickName": "",
            "email": "",
            "username": "admin",
            "organization": null,
            "roleList": [
                {
                    "id": "70ff5fe5609211e78810fa163ef7fc74",
                    "version": null,
                    "name": "admin",
                    "enable": null
                }
            ]
        }
    ],
    "allRoleList": [
        {
            "id": "70ff5fe5609211e78810fa163ef7fc74",
            "version": 0,
            "name": "admin",
            "enable": true
        }
    ]
}
```

## /account/login
* 获取所有账户列表和角色列表
* POST
* 参数
```
{"username":"admin","password":"Abcd123456","time":3600,"verifyCode":"","verifyToken":""}
```
* 返回数据
```
{
    "token": "",
    "menu": [],
    "uid": ""
}
```
    
## /account/save
* 保存账户信息
* POST
* header: token
* 参数
```
{"username":"xxx","password":"Abcd123456","email":"aa@a.com"}
```
* 返回数据
    HTTP Status
    
## /account/test
* 测试接口，不需要token
* GET
* 返回数据
```
{"message": "ok"}
```

## /account/delete
* 删除账户
* POST
* header: token
* 参数
```
{"uid":["1111","2222"]}
```
* 返回数据
    HTTP Status
    
## /account/userList
* 获取用户列表，但不包括用户名是admin的用户
* GET
* header: token
* 返回数据
```
{
    "uid": "username",
    "uid": "username"
}
```    
## /account/role/save
* 保存用户和角色的关系
* POST
* header: token
* 参数
```
{"uuid":"uuid","roleList":["roleId1","roleId2"]}
```
* 返回数据
    HTTP Status
    
## /role
* 获取角色列表
* GET
* header: token
* 返回数据
```
[
    {
        "id": "70ff5fe5609211e78810fa163ef7fc74",
        "version": 0,
        "name": "admin",
        "enable": true
    }
]
```

## /role/switch
* 根据角色id启用或停用角色
* POST
* header: token
* 参数
```
{"id":"roleId","enable":false}
```
* 返回数据
    HTTP Status
    
## /role/save
* 保存角色信息以及角色和权限的关系
* POST
* header: token
* 参数
```
{"name":"roleName","enable":true,"menuList":["authorityId","authorityId"]}
```
* 返回数据
    HTTP Status
    
## /role/modify
* 修改角色和权限的关系
* POST
* header: token
* 参数
```
{"rid":"roleId","menuList":["authorityId","authorityId"]}
```
* 返回数据
    HTTP Status
    
## /role/delete
* 删除一个或多个角色
* POST
* header: token
* 参数
```
{"rid":["roleId","roleId"]}
```
* 返回数据
    HTTP Status

## /authority/get
* 获取完整的权限功能树
* GET
* header: token
* 返回数据
```
[
    {
        "id": "786b0563609811e78810fa163ef7fc74",
        "version": 0,
        "enable": true,
        "name": "用户权限",
        "levelcode": "1",
        "position": 10,
        "thevalue": "true",
        "url": "0",
        "matchurl": "user/",
        "itemicon": "fa-users",
        "parentid": "0",
        "mid": null,
        "menuList": [
            {
                "id": "a40f27d3609811e78810fa163ef7fc74",
                "version": 0,
                "enable": true,
                "name": "权限管理",
                "levelcode": "2",
                "position": 13,
                "thevalue": "false",
                "url": "0",
                "matchurl": "user/authority",
                "itemicon": "fa-keyboard-o",
                "parentid": "786b0563609811e78810fa163ef7fc74",
                "mid": null,
                "menuList": null
            },
            {
                "id": "ec0bf17a609811e78810fa163ef7fc74",
                "version": 0,
                "enable": true,
                "name": "用户管理",
                "levelcode": "2",
                "position": 11,
                "thevalue": "false",
                "url": "0",
                "matchurl": "user/user",
                "itemicon": "fa-user",
                "parentid": "786b0563609811e78810fa163ef7fc74",
                "mid": null,
                "menuList": null
            },
            {
                "id": "f6653cdb609811e78810fa163ef7fc74",
                "version": 0,
                "enable": true,
                "name": "角色管理",
                "levelcode": "2",
                "position": 12,
                "thevalue": "false",
                "url": "0",
                "matchurl": "user/role",
                "itemicon": "fa-building-o",
                "parentid": "786b0563609811e78810fa163ef7fc74",
                "mid": null,
                "menuList": null
            }
        ]
    }
]
```

## /authority/{roleId}
* 根据roleId获取权限列表
* GET
* header: token
* 返回数据
allAuthorList的内容同/authority/get返回中的“menuList”字段
```
{
    "authorList": [
        "a40f27d3609811e78810fa163ef7fc74",
        "ec0bf17a609811e78810fa163ef7fc74",
        "f6653cdb609811e78810fa163ef7fc74",
        "786b0563609811e78810fa163ef7fc74"
    ],
    "rid": "70ff5fe5609211e78810fa163ef7fc74",
    "allAuthorList":[],
}
```

## /authority/save
* 权限保存
* POST
* header: token
* 参数
```
{"version":0,"parentid":"{parentid}","name":"{authName}","position":0,
"url":"0","matchurl":"xxxx","mid":""}
```
* 返回数据
    HTTP Status

## /authority/update
* 更新一条权限数据
* POST
* header: token
* 参数
```
{
    "id": "f6653cdb609811e78810fa163ef7fc74",
    "version": 0,
    "enable": true,
    "name": "角色管理",
    "levelcode": "2",
    "position": 12,
    "thevalue": "false",
    "url": "0",
    "matchurl": "user/role",
    "itemicon": "fa-building-o",
    "parentid": "786b0563609811e78810fa163ef7fc74",
    "mid": "role_setting"
}
```
* 返回数据
    HTTP Status

## /authority/delete
* 根据id删除一个或多个权限
* POST
* header: token
* 参数
```
{"aid":["authId","authId"]}
```
* 返回数据
    HTTP Status

## /authority/all
* 获取所有权限,无树形结构
* GET
* header: token
* 返回数据
```
[
    {
        "id": "786b0563609811e78810fa163ef7fc74",
        "version": 0,
        "enable": true,
        "name": "用户权限",
        "levelcode": "1",
        "position": 10,
        "thevalue": "true",
        "url": "0",
        "matchurl": "user/",
        "itemicon": "fa-users",
        "parentid": "0",
        "mid": "user_unuse"
    },
    {
        "id": "a40f27d3609811e78810fa163ef7fc74",
        "version": 0,
        "enable": true,
        "name": "权限管理",
        "levelcode": "2",
        "position": 13,
        "thevalue": "false",
        "url": "0",
        "matchurl": "user/authority",
        "itemicon": "fa-keyboard-o",
        "parentid": "786b0563609811e78810fa163ef7fc74",
        "mid": "auth_setting"
    },
    {
        "id": "ec0bf17a609811e78810fa163ef7fc74",
        "version": 0,
        "enable": true,
        "name": "用户管理",
        "levelcode": "2",
        "position": 11,
        "thevalue": "false",
        "url": "0",
        "matchurl": "user/user",
        "itemicon": "fa-user",
        "parentid": "786b0563609811e78810fa163ef7fc74",
        "mid": "user_setting"
    },
    {
        "id": "f6653cdb609811e78810fa163ef7fc74",
        "version": 0,
        "enable": true,
        "name": "角色管理",
        "levelcode": "2",
        "position": 12,
        "thevalue": "false",
        "url": "0",
        "matchurl": "user/role",
        "itemicon": "fa-building-o",
        "parentid": "786b0563609811e78810fa163ef7fc74",
        "mid": "role_setting"
    }
]
```

## /authority/api/{authId}
* 根据权限id，获取对应的api路径列表
* GET
* header: token
* 返回数据
```
[
    {
        "id": null,
        "authorityId": "a40f27d3609811e78810fa163ef7fc74",
        "api": "/authority"
    }
]
```