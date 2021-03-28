package com.ohh.domainname;

import com.ohh.domainname.core.ResponseEntity;
import com.ohh.domainname.core.WebSiteAdaptor;
import com.ohh.domainname.core.WorkThread;
import com.ohh.domainname.entity.eb.Eb;
import com.ohh.domainname.util.Algorithm;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.*;

@SpringBootApplication
public class DomainnameApplication {

    // 写文件锁
    private static final Object WRITE_LOCK = new Object();
    // 线程池数量
    private static final int THREAD_COUNT_NUM = 50;
    // 长度范围, 最小长度
    private static final int MIN_LEN = 4;
    // 长度范围, 最大长度
    private static final int MAX_LEN = 4;
    // 导出文件路径
    private static final String FILE_PATH = "C:\\Users\\Administrator\\Desktop\\4char-start-with-[a-c]-domain.txt";
    // 最小值
    private static final String HEAD = "a---";
    // 最大值
    private static final String TAIL = "czzz";

    public static void main(String[] args) {
        // 启动
        ConfigurableApplicationContext context = SpringApplication.run(DomainnameApplication.class, args);

        // thread factory
        ThreadFactory threadFactory = r -> {
            WorkThread workThread = new WorkThread(r, new RestTemplate(context.getBean(ClientHttpRequestFactory.class)));
            System.out.println("新的线程被启动");
            return workThread;
        };

        // service
        ExecutorService executorService = Executors.newFixedThreadPool(THREAD_COUNT_NUM, threadFactory);

        // 监控任务
        executorService.execute(() -> {
            while (!executorService.isShutdown()) {
                System.out.println("remainingCapacity=" + ((ThreadPoolExecutor) executorService).getQueue().remainingCapacity());
                System.out.println("rest task size=" + ((ThreadPoolExecutor) executorService).getQueue().size());
                try {
                    TimeUnit.SECONDS.sleep(60);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("任务完成");
        });

        // 创建文件流
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
            // 搜索
            WebSiteAdaptor adaptor = new Eb();
            String api = adaptor.getApi();
            List<String> words;
            // 根据域名字符长度进行搜索
            for (int i = MIN_LEN; i <= MAX_LEN; i++) {
                System.out.println("正在搜索长度为: " + i + "的域名");
                // 设置范围, 获取长度为 i 的域名所有可能的字符组合
                words = Algorithm.builder().setHead(HEAD).setTail(TAIL).getWords(i);
                CountDownLatch countDownLatch = new CountDownLatch(words.size());
                // 搜索长度为 i 的所有域名
                for (String word : words) {
                    executorService.execute(() -> {
                        while (true) {
                            try {
                                // 发送 http 请求
                                String json = ((WorkThread) (Thread.currentThread())).getRestTemplate().getForObject(api, String.class, word);
                                // 解析 json
                                ResponseEntity responseEntity = adaptor.parseJson(json);
                                if (Objects.nonNull(responseEntity)) {
                                    // 写入文件
                                    synchronized (WRITE_LOCK) {
                                        bw.write(LocalDateTime.now() + ": 域名 '" + word + "', " + responseEntity);
                                        bw.newLine();
                                        bw.flush();
                                    }
                                }
                                countDownLatch.countDown();
                                break;
                            } catch (Throwable e) {
                                System.out.println(word + "执行异常");
                                e.printStackTrace();
                            }
                        }
                    });
                }
                System.out.println("任务提交完毕");
                countDownLatch.await();
            }
            executorService.shutdown();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}