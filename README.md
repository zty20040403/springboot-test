# Maven 和 Spring Boot 3 入门教程

这份教程适合刚开始学 Maven 和 Spring Boot 3 的同学。目标不是背概念，而是让你知道：

- Maven 是干什么的
- `pom.xml` 怎么看
- Spring Boot 3 项目怎么启动
- Controller、Service、Repository 分别做什么
- 怎么写一个能运行的接口

> 说明：GitHub 仓库首页会自动渲染根目录下的 `README.md`，所以这份文件可以直接在仓库页面下面阅读。

## 目录

1. [Maven 是什么](#1-maven-是什么)
2. [Maven 项目结构](#2-maven-项目结构)
3. [pom.xml 怎么看](#3-pomxml-怎么看)
4. [Spring Boot 3 是什么](#4-spring-boot-3-是什么)
5. [例子 1：最小 Hello 接口](#5-例子-1最小-hello-接口)
6. [例子 2：Controller 调用 Service](#6-例子-2controller-调用-service)
7. [例子 3：Spring Boot + H2 数据库](#7-例子-3spring-boot--h2-数据库)
8. [常用 Maven 命令](#8-常用-maven-命令)
9. [常见错误](#9-常见错误)

## 1. Maven 是什么

Maven 可以理解成 Java 项目的“管家”。

它主要负责三件事：

1. 管理依赖：比如 Spring Boot、MyBatis、MySQL 驱动。
2. 规定目录结构：比如 Java 代码放 `src/main/java`。
3. 构建项目：比如编译、测试、打包成 jar。

没有 Maven 的时候，你可能要自己下载很多 `.jar` 文件。用了 Maven，你只要在 `pom.xml` 里声明依赖：

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>
```

Maven 就会自动帮你下载 Web 开发需要的 jar 包。

## 2. Maven 项目结构

一个标准 Maven 项目通常长这样：

```text
my-project/
├── pom.xml
└── src/
    ├── main/
    │   ├── java/          # 正式 Java 代码
    │   └── resources/     # 配置文件，例如 application.yml
    └── test/
        └── java/          # 测试代码
```

几个最重要的位置：

| 路径 | 作用 |
| --- | --- |
| `pom.xml` | Maven 配置文件 |
| `src/main/java` | 正式 Java 源代码 |
| `src/main/resources` | 配置文件、SQL、XML 等资源 |
| `src/test/java` | 测试代码 |
| `target` | Maven 编译打包后的输出目录 |

## 3. pom.xml 怎么看

`pom.xml` 是 Maven 项目的核心配置文件。

一个 Spring Boot 3 项目常见写法如下：

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">

    <modelVersion>4.0.0</modelVersion>

    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>3.3.0</version>
        <relativePath/>
    </parent>

    <groupId>com.zhu</groupId>
    <artifactId>demo</artifactId>
    <version>0.0.1-SNAPSHOT</version>

    <properties>
        <java.version>17</java.version>
    </properties>

    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>
    </dependencies>

    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
            </plugin>
        </plugins>
    </build>
</project>
```

重点解释：

- `parent`：使用 Spring Boot 的父工程，帮你统一管理依赖版本。
- `groupId`：组织名或包名，比如 `com.zhu`。
- `artifactId`：项目名，比如 `demo`。
- `version`：项目版本，`SNAPSHOT` 表示开发中的版本。
- `java.version`：Spring Boot 3 至少需要 Java 17。
- `dependencies`：项目依赖都写在这里。
- `spring-boot-maven-plugin`：让 Spring Boot 项目可以打包成可运行 jar。

## 4. Spring Boot 3 是什么

Spring Boot 是用来快速创建 Spring 应用的工具。

它帮你做了很多自动配置，比如：

- 自动配置 Spring MVC
- 内置 Tomcat
- 自动处理 JSON
- 自动扫描 Controller、Service、Repository
- 支持直接打包成 jar 运行

最小启动类：

```java
package com.zhu.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication {
    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }
}
```

`@SpringBootApplication` 很重要，它会从当前包开始扫描下面的组件。

推荐包结构：

```text
com.zhu.demo
├── DemoApplication.java
├── controller
│   └── UserController.java
├── service
│   └── UserService.java
└── repository
    └── UserRepository.java
```

## 5. 例子 1：最小 Hello 接口

这个例子启动后访问 `/hello`，返回 `Hello Spring Boot 3`。

### 目录结构

```text
hello-demo/
├── pom.xml
└── src/main/java/com/zhu/hello/
    ├── HelloApplication.java
    └── HelloController.java
```

### pom.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">

    <modelVersion>4.0.0</modelVersion>

    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>3.3.0</version>
        <relativePath/>
    </parent>

    <groupId>com.zhu</groupId>
    <artifactId>hello-demo</artifactId>
    <version>0.0.1-SNAPSHOT</version>

    <properties>
        <java.version>17</java.version>
    </properties>

    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
    </dependencies>

    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
            </plugin>
        </plugins>
    </build>
</project>
```

### HelloApplication.java

```java
package com.zhu.hello;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class HelloApplication {
    public static void main(String[] args) {
        SpringApplication.run(HelloApplication.class, args);
    }
}
```

### HelloController.java

```java
package com.zhu.hello;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/hello")
    public String hello() {
        return "Hello Spring Boot 3";
    }
}
```

### 运行

```bash
cd hello-demo
mvn spring-boot:run
```

新开一个终端测试：

```bash
curl http://localhost:8080/hello
```

期望输出：

```text
Hello Spring Boot 3
```

## 6. 例子 2：Controller 调用 Service

这个例子展示 Spring 的依赖注入。

Controller 不自己 `new UserService()`，而是让 Spring 自动把 Service 传进来。

### 目录结构

```text
layered-demo/
├── pom.xml
└── src/main/java/com/zhu/layered/
    ├── LayeredApplication.java
    ├── controller/UserController.java
    └── service/UserService.java
```

`pom.xml` 可以直接使用上一个例子的 `pom.xml`，只需要把 `artifactId` 改成 `layered-demo`。

### LayeredApplication.java

```java
package com.zhu.layered;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LayeredApplication {
    public static void main(String[] args) {
        SpringApplication.run(LayeredApplication.class, args);
    }
}
```

### UserService.java

```java
package com.zhu.layered.service;

import org.springframework.stereotype.Service;

@Service
public class UserService {

    public String findNameById(Long id) {
        if (id == 1) {
            return "张三";
        }
        return "未知用户";
    }
}
```

### UserController.java

```java
package com.zhu.layered.controller;

import com.zhu.layered.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users/{id}/name")
    public String findName(@PathVariable Long id) {
        return userService.findNameById(id);
    }
}
```

运行测试：

```bash
cd layered-demo
mvn spring-boot:run

curl http://localhost:8080/users/1/name
curl http://localhost:8080/users/2/name
```

你会看到：

```text
张三
未知用户
```

## 7. 例子 3：Spring Boot + H2 数据库

这个例子使用内存数据库 H2，不需要你安装 MySQL。

流程是：

```text
浏览器 -> Controller -> Service -> Repository -> H2 数据库
```

### 目录结构

```text
user-api-demo/
├── pom.xml
└── src/main/
    ├── java/com/zhu/userapi/
    │   ├── UserApiApplication.java
    │   ├── controller/UserController.java
    │   ├── model/User.java
    │   ├── repository/UserRepository.java
    │   └── service/UserService.java
    └── resources/
        ├── application.yml
        ├── schema.sql
        └── data.sql
```

### pom.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">

    <modelVersion>4.0.0</modelVersion>

    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>3.3.0</version>
        <relativePath/>
    </parent>

    <groupId>com.zhu</groupId>
    <artifactId>user-api-demo</artifactId>
    <version>0.0.1-SNAPSHOT</version>

    <properties>
        <java.version>17</java.version>
    </properties>

    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-jdbc</artifactId>
        </dependency>

        <dependency>
            <groupId>com.h2database</groupId>
            <artifactId>h2</artifactId>
            <scope>runtime</scope>
        </dependency>
    </dependencies>

    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
            </plugin>
        </plugins>
    </build>
</project>
```

### application.yml

```yaml
server:
  port: 8080

spring:
  datasource:
    url: jdbc:h2:mem:userdb;MODE=MySQL;DATABASE_TO_UPPER=false
    username: sa
    password:
    driver-class-name: org.h2.Driver
  h2:
    console:
      enabled: true
  sql:
    init:
      mode: always
```

### schema.sql

```sql
create table app_user (
    id bigint primary key,
    name varchar(50) not null,
    age int not null
);
```

### data.sql

```sql
insert into app_user (id, name, age) values (1, '张三', 20);
insert into app_user (id, name, age) values (2, '李四', 22);
insert into app_user (id, name, age) values (3, '王五', 24);
```

### UserApiApplication.java

```java
package com.zhu.userapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class UserApiApplication {
    public static void main(String[] args) {
        SpringApplication.run(UserApiApplication.class, args);
    }
}
```

### User.java

```java
package com.zhu.userapi.model;

public record User(Long id, String name, Integer age) {
}
```

### UserRepository.java

```java
package com.zhu.userapi.repository;

import com.zhu.userapi.model.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class UserRepository {

    private final JdbcTemplate jdbcTemplate;

    public UserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<User> findAll() {
        String sql = "select id, name, age from app_user order by id";
        return jdbcTemplate.query(sql, userRowMapper());
    }

    public Optional<User> findById(Long id) {
        String sql = "select id, name, age from app_user where id = ?";
        return jdbcTemplate.query(sql, userRowMapper(), id)
                .stream()
                .findFirst();
    }

    private RowMapper<User> userRowMapper() {
        return (rs, rowNum) -> new User(
                rs.getLong("id"),
                rs.getString("name"),
                rs.getInt("age")
        );
    }
}
```

### UserService.java

```java
package com.zhu.userapi.service;

import com.zhu.userapi.model.User;
import com.zhu.userapi.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "user not found"));
    }
}
```

### UserController.java

```java
package com.zhu.userapi.controller;

import com.zhu.userapi.model.User;
import com.zhu.userapi.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<User> findAll() {
        return userService.findAll();
    }

    @GetMapping("/{id}")
    public User findById(@PathVariable Long id) {
        return userService.findById(id);
    }
}
```

运行：

```bash
cd user-api-demo
mvn spring-boot:run
```

测试：

```bash
curl http://localhost:8080/users
curl http://localhost:8080/users/1
curl http://localhost:8080/users/999
```

返回示例：

```json
[
  {
    "id": 1,
    "name": "张三",
    "age": 20
  },
  {
    "id": 2,
    "name": "李四",
    "age": 22
  }
]
```

## 8. 常用 Maven 命令

进入有 `pom.xml` 的目录后运行：

```bash
mvn clean
```

删除旧的构建结果。

```bash
mvn compile
```

编译代码。

```bash
mvn test
```

运行测试。

```bash
mvn package
```

编译、测试、打包。

```bash
mvn spring-boot:run
```

启动 Spring Boot 项目。

打包成 jar 后运行：

```bash
mvn clean package
java -jar target/user-api-demo-0.0.1-SNAPSHOT.jar
```

## 9. 常见错误

### 端口被占用

错误：

```text
Port 8080 was already in use
```

解决：换端口。

```yaml
server:
  port: 8081
```

### 找不到 Bean

错误：

```text
No qualifying bean of type 'UserService'
```

常见原因：

- `UserService` 没有加 `@Service`。
- 启动类位置太深，没扫描到 Service。
- 包名写错。

### 访问接口 404

常见原因：

- Controller 没有加 `@RestController`。
- 路径写错。
- 请求方式错了，比如接口是 POST，你用了 GET。
- Controller 没被 Spring Boot 扫描到。

### Maven 下载依赖失败

可以尝试强制更新：

```bash
mvn clean package -U
```

## 最后记住

Maven：

- `pom.xml` 管项目和依赖。
- `dependencies` 里写依赖。
- `mvn package` 可以打包。
- `mvn spring-boot:run` 可以启动项目。

Spring Boot 3：

- 需要 Java 17 或更高版本。
- `@SpringBootApplication` 是启动入口。
- Controller 接请求。
- Service 写业务。
- Repository 查数据库。
- Spring 通过依赖注入帮你管理对象。

