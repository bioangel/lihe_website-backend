# 编译打包
* gradle build -Denv=local
* gradle version 4.6

## MySQL
### 本地配置
* 默认端口3306 
* schema lihe
* 用户  密码 

## 表结构维护
* flyway关闭，启动server报错时请在server目录下执行
```
gradle  flywayMigrate -Dport=3307
```
或
```
gradle  flywayMigrate
```

## 代码检查
```
gradle check
```