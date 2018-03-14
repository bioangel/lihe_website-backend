# captcha 模块
## /captcha
* 获取验证码
* GET
* 返回数据
    {
        "code": "ff489f6a738f435e8dd00886a457c18b",
        "jpgBase64": "jpg"
    }
* captcha.enabled 参数为true时，接口才可用

# authority 模块
## /account
* 获取所有账户列表和角色列表
* GET
* header: token
* 返回数据
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

## /account/login
* 获取所有账户列表和角色列表
* POST
* 参数
    {"username":"admin","password":"Abcd123456","time":3600,"verifyCode":"","verifyToken":""}
* 返回数据
    {
        "token": "",
        "menu": [],
        "uid": ""
    }