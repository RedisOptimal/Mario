如何添加自定义扩展插件
1.新建maven工程，依赖Wario
2.新建com.renren.Wario.plugin包
3.新建PluginNamePlugin类，继承IPlugin
4.完成run函数
5.执行mvn package命令
6.将生成的jar包重命名为PluginName.jar，放到Wario/plugins目录下
7.在plugin.json中田间PluginName插件的信息
