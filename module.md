# 项目module描述
## 1.common
* 公共模块
* 不依赖其他项目module

## 2.security
* 安全相关内容模块
* 不依赖其他模块

## 3.cache
* 缓存模块
* 依赖common模块

## 4.captcha
* 验证码模块
* 依赖cache模块

## 5.database
* 持久层模块，提供通用的数据库访问支持
* 依赖common、security模块

## 6.authority
* 权限认证模块
* 依赖sys模块

## 7.lihe-server
* lihe 应用服务器，包含server系统功能的代码
* 依赖authority模块

## 8.sys
* 系统底层通用模块
* 依赖database, captcha模块