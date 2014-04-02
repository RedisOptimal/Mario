# Mario

A ZooKeeper monitor platform.
一个ZooKeeper的监控报警平台

首先需要安装maven环境和jetty服务器

检出项目
`git clone https://github.com/x841122987/Mario.git`

导入数据库表结构和基础数据
`mysql -u root -p db_name < Mario.sql`

##xweb工程

修改数据库配置文件
```
xweb/src/main/resources/application.properties
```

将工程打成war包
```
cd xweb
mvn package -Dmaven.test.skip=true
```

运行
```
cd xweb
cp target/xweb.jar $JETTY_HOME/webapps/xweb.jar
$JETTY_HOME/bin/jetty.sh restart
```

##Wario工程

修改数据库配置文件
```
Wario/src/main/resources/application.properties
```

编译工程
```
cd Wario
./build.sh
```

运行
```
cd Wario
./run.sh start
```

###添加自定义扩展插件
