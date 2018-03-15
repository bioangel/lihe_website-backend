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