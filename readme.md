# 可用域名搜索器

#注意

这是一个提供给域名购买者使用的工具, 该工具由本人开发, 不得商用.

#使用:

1. 设置参数
    ```
       // 线程池数量
       private static final int THREAD_COUNT_NUM = 20;
       // 长度范围, 最小长度
       private static final int MIN_LEN = 4;
       // 长度范围, 最大长度
       private static final int MAX_LEN = 4;
       // 导出文件路径
       private static final String FILE_PATH = "[your file path]";
   ```
   
2. 运行启动类

3. 程序启动后可以通过控制台查看如下初始信息
    ```
        正在搜索长度为: *的域名
        // 起始位置, 结束位置
        defaultHead: **, defaultTail: **
        // 本次域名列表长度
        words list size:50653
        任务提交完毕
        // 阻塞队列容量
        remainingCapacity=2147433014
        // 剩余待搜索数量
        rest task size=50633
        长度为*的任务执行完毕
    ```
   
4. 等待执行完毕, 查看生成的文件列表, 它可能是这样的

    ```
    2021-03-14T23:38:32.347: 域名 '01k9', UrlEb(result=true)
    2021-03-14T23:38:33.526: 域名 '01k7', UrlEb(result=true)
    2021-03-14T23:38:35.223: 域名 '01l7', UrlEb(result=true)
    2021-03-14T23:38:36.257: 域名 '01l0', UrlEb(result=true)
    2021-03-14T23:38:36.261: 域名 '01l3', UrlEb(result=true)
    2021-03-14T23:38:38.535: 域名 '01m1', UrlEb(result=true)
    2021-03-14T23:38:38.686: 域名 '01m6', UrlEb(result=true)
    ```
   
   每行表示一个可注册域名
   
# 提示

如果控制台报错, 未成功的任务会自动重新添加, 不需要处理