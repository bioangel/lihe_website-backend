# 编译打包
* gradle build -Denv=local
* gradle version 4.6

## MySQL/MariaDB
### 本地配置
* IP:10.122.2.165
* 默认端口:3306 
* schema:lihe
* 应用账户:lihe_app  密码:lihe_app
* 开发者账户:lihe_dev  密码:lihe_dev

## 表结构维护
* flyway关闭，启动server报错时请在server目录下执行
```
gradle  flywayMigrate -Dport=3307 -Dhost=localhost -Dflyway.user=root -Dflyway.password=root
```
或
```
gradle  flywayMigrate -Dport=3306 -Dhost=10.122.2.165 -Dflyway.user=lihe_dev -Dflyway.password=lihe_dev
```

## 代码质量检查
```
gradle check
```
