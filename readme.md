# ALL

[TOC]

# 1. 前言

所有文档汇总总和，提炼工作中所有的需要记录的知识点

# 2. 文档

暂无



# 3. shell

## 3.1 主机

程序运行检查命令

```shell
# 以GB为单位显示内存使用情况。
# total：内存总数； used：已经使用的内存数； free：空闲的内存数；
# shared：当前已经废弃不用； buffers Buffer：缓存内存数； cached Page：缓存内存数。
free -g

# 查看本地程序连接远程的哪个端口
netstat -antp | grep 10.113

# 检查
jstat -gcutil 程序id

---------------磁盘命令
# 这个命令组合用于查找当前目录及其子目录下所有大于100MB的文件，并显示它们的磁盘使用情况，然后按大小从大到小排序。
find . -type f -size +100M   -exec du ah {} + | sort -rh
```

统计命令

```shell
# 统计正则表达式中的所有文件 的行数
wc -l srvlog202407*
```





# 3.2 java语言

### 3.2.1 jar命令

替换jar包

```shell
步骤1：解压jar包   jar -xvf  *.jar
PS D:\ttmp> jar -xvf *.jar
  已创建: META-INF/
  已解压: META-INF/MANIFEST.MF
  已创建: BOOT-INF/
  已创建: BOOT-INF/classes/
  已创建: BOOT-INF/classes/com/
步骤2：替换jar包
rm -rf BOOT-INF/lib/ojdbc14-10.2.0.4.0.jar
mv ojdbc7-12.1.0.2.0.jar BOOT-INF/lib/
步骤3：重新压缩jar
 jar -cfM0 new.jar BOOT-INF/ META-INF/ org/
 
 配置文件是
 jar -uvf old.jar BOOT-INF/classes/application.properties
 
# 例子
jar -uvf0 dbroute-service-3.x-SNAPSHOT-jdk17.jar BOOT-INF/lib/mysql-connector-java-8.0.19.jar
```

### 3.2.2 jvm

```shell
# 导出堆栈日志，进程号是1
jmap -dump:format=b,file=heap.hprof 1

```





# 4. git

```shell
# 获取最新一层的项目
git clone --depth=1 https://github.com/c-smile/sciter-sdk
```

# 5. SQL

## 5.1 mysql8

### 5.1.1 驱动url配置

MySQL8 提示Public Key Retrieval is not allowed错误解决方法：allowPublicKeyRetrieval=true&useSSL=false

> 如果用户使用了 sha256_password 认证，密码在传输过程中必须使用 TLS 协议保护，但是如果 RSA 公钥不可用，可以使用服务器提供的公钥；可以在连接中通过 ServerRSAPublicKeyFile 指定服务器的 RSA 公钥，或者AllowPublicKeyRetrieval=True参数以允许客户端从服务器获取公钥；但是需要注意的是 AllowPublicKeyRetrieval=True可能会导致恶意的代理通过中间人攻击(MITM)获取到明文密码，所以默认是关闭的，必须显式开启







# 6.常规脚本

每隔两秒输出一次监听的端口是否存活的信息到文件中

```shell
#!/bin/bash

# 定义要监控的端口号
PORT=8090|8091

# 定义日志文件路径
LOG_FILE="port_status.log"

while true; do
   
    # 将当前时间和查询结果一起追加到日志文件
    echo "[$(date)] $(netstat -anp | grep -E ":$PORT ")" >> "$LOG_FILE"
  
    # 等待2秒后继续下一次查询
    sleep 2
done
```

# 7.技巧

## 7.1 Windows

**查看电脑关机 时间**

Windows系统

在Windows系统中，你可以使用事件查看器（Event Viewer）来查看关机日志：

1. 打开“开始”菜单，然后搜索并打开“事件查看器”（Event Viewer）。
2. 在事件查看器中，展开“Windows 日志”（Windows Logs）。
3. 选择“系统”（System）日志。
4. 在右侧的操作面板中，点击“筛选当前日志”（Filter Current Log）。
5. 在筛选器中，设置“事件源”为“所有事件”和“事件ID”为“1074”，然后点击“确定”。
6. 这将显示所有与系统关闭相关的事件。你可以通过查看“时间”列来找到关机日志的时间。









# 8.go语言

## 8.1 gogc

>gogc参数含义
>
>考虑一个Go程序，其活堆大小为8 MiB，goroutine栈为1 MiB，全局变量中的指针为1 MiB。那么，在GOGC值为100的情况下，在下一次GC运行前分配的新内存量将是10 MiB，或者是10 MiB工作的100%，总的堆占用量为18 MiB。如果GOGC值为50，那么它将是50%，或5 MiB。如果GOGC值为200，则为200%，即20 MiB
>
>来源：[Go 垃圾收集器指南 | Go 中文档集 (before80.github.io)](https://before80.github.io/go_docs/docs/UsingAndUnderstandingGo/AGuideToTheGoGarbageCollector/)



![gogc1](https://before80.github.io/go_docs/docs/UsingAndUnderstandingGo/AGuideToTheGoGarbageCollector_img/gogc1.gif)

# 9. k8s

## 9.1 delte容器

k8s删除容器的过程

## 9.2 docker

[docker下载arm或x86镜像方法_docker下载arm镜像-CSDN博客](https://blog.csdn.net/shijie1103/article/details/131305201)

# 10.优秀帖子学习

## 10.1 线程使用不当故障处理

原帖：[聊一次线程池使用不当导致的生产故障 (qq.com)](https://mp.weixin.qq.com/s/3p01lP4f5XYK6Mf_TIkZsw)

心得：结论模板

| 根因         | 业务代码中不合理使用自定义线程池导致的 “瓶口效应”。          |
| ------------ | ------------------------------------------------------------ |
| 诱因         | 下游 getTagInfo 接口耗时抖动                                 |
| 其他次要因素 | 切流导致流量增加；Pod 数量少；探活机制脆弱；用户重试导致流量异常增涨； |
| 结论         | 业务代码中不合理使用自定义线程池导致 “瓶口效应”，间接限制了 tomcat 的吞吐能力。10 月 26 日晚切流后，请求量达到前一天的两倍，自定义线程池处理能力已经接近瓶颈（20 task/s）。碰巧遇到 10 月 27 日早晨 getTagInfo 耗时抖动，子任务执行耗时显著增加，自定义线程池处理能力迅速触动崩溃，进而导致 tomcat 处理请求排队。长时间积压在 tomcat 线程池工作队列中的探活请求超时，导致探活失败，触发 k8s 先后重启了 zone-2 仅有的 2 个 Pod，最终导致整个 zone-2 不可用。 |

## 10.2 maven配置全解

原帖：https://mp.weixin.qq.com/s/lpTNvo6aQdgaJbsAY9WEyg

最大收获：相同依赖的使用顺序，先定义先使用，层级高先使用

**依赖顺序**

在 maven 工程中遵循先定义先导入的原则，即当存在多个相同间接依赖，优先导入其父依赖定义在前的简洁依赖。

举个例子，如工程中引入 `Dependency-A` 与 `Dependency-B` 两个依赖，二者又分别引用了不同版本的 `Dependency-C` ，但对于 Maven 而言最终编译时同一个依赖即便是不同的版本也只会选择一份。

其计算规则如下：若 `Dependency-A` 定义在 `Dependency-B` 之前则最终将导入 `Dependency-A` 中的 C-1.0 版本。而在右侧图例中虽然 `Dependency-A` 引入优先级高于 `Dependency-B` ，但是 C-2.0 的间接依赖层级高于 C-1.0，因此将导入 C-2.0 版本。

![图片](https://mmbiz.qpic.cn/mmbiz_png/eQPyBffYbudohzQNTzDDARXU3icPLHwR6FCVdklIUCQia9bibNWicc1Kyybbbib5UUeHBMlUiaEqwpLtGVQoQHR7UsVw/640?wx_fmt=png&from=appmsg&tp=wxpic&wxfrom=5&wx_lazy=1&wx_co=1)

## 10.3 linux基础命令

原帖内容: [从新手到大师：Linux命令大全，掌握Linux绝不是问题！](https://mp.weixin.qq.com/s/22IURAO3HMuoueSzYprGdQ)

最大收获：目录结构说明

![图片](https://mmbiz.qpic.cn/mmbiz_png/QFzRdz9libEYrXR9wqMryOlPiaHbiaRPmjPD2uoTN2zvVgW3IlBZbtJDZVGFe8xvFNPQvImX7teiaHUicKZs3mib8cQQ/640?wx_fmt=png&from=appmsg&tp=wxpic&wxfrom=5&wx_lazy=1&wx_co=1)



## 10.4 影响业务故障原因

原帖：[故障是一定会发生的：IT 业务连续性保障该如何建设？](https://mp.weixin.qq.com/s/MO6oqmwvewtSdwy_b09jdQ)

最大的收获：引起故障的原因和优化手段



![图片](https://mmbiz.qpic.cn/mmbiz_png/wbohibYspCs8BwyERVclic2fVg9cXuX4HuiauEfpXdUkelgaWBxek0jA0vZM14tiaXfDqyjHcEgStbdDHwPLlOXTEg/640?wx_fmt=png&from=appmsg&wxfrom=5&wx_lazy=1&wx_co=1&tp=wxpic)



# 11. 概念

## 11.1 监控

软件监控领域，主要通过metrics（指标）、log（日志）、trace（调用链）三个工具进行监控系统的运行状态和性能

针对metric，Prometheus是比较出众的代表

简单来说，prometheus的指标是根据采样间隔进行记录的指标数据，比如15s采样一次是比较常见的间隔，意味着Prometheus将会15s一次去请求spring应用的Prometheus接口

那我统计的时间范围刚好卡在两个采样频率之间呢，那么Prometheus将会通过“线性外推”的估算方式，取时间范围内的最近的时点，进行计算斜率，再补充上两个时间位置的值

<img src="https://mmbiz.qpic.cn/sz_mmbiz_png/dOibM5x5WibXk709wfVfMwUCFAWKJGZpU8AhoRGUIHbwc0Bev4tGzXaDelhoctccoFzYY7EJrX2Rib25eHLV1DRVA/640?wx_fmt=png&from=appmsg&tp=webp&wxfrom=5&wx_lazy=1&wx_co=1" alt="图片" style="zoom:50%;" />



所以Prometheus的数据会有不精确是正常的，本身得到的值就是估算值

> [Prometheus 指标值不准：是 feature，还是 bug？](https://mp.weixin.qq.com/s/A3W3hSCpQi8DQYJxOS1ZGA)



https://mp.weixin.qq.com/s/3QLzDufsxUkgQ93EZmmFFg

