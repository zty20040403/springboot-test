# springboot-mybatis

Spring Boot 4 + MyBatis + MySQL 的最小整合示例。

## 运行

```bash
./mvnw spring-boot:run
```

启动后默认监听 `8080` 端口。

## 测试接口

```bash
curl "http://localhost:8080/findById?id=1"
```

## 主要文件

- `pom.xml`: 项目依赖
- `src/main/resources/application.yml`: 数据源和 MyBatis 配置
- `src/main/resources/schema.sql`: 建表 SQL
- `src/main/resources/data.sql`: 初始化数据
- `src/main/java/com/zhu/springbootmybatis/mapper/UserMapper.java`: Mapper 接口
- `src/main/resources/mapper/UserMapper.xml`: MyBatis SQL 映射文件
- `src/main/java/com/zhu/springbootmybatis/controller/UserController.java`: REST 接口
