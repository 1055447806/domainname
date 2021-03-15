package com.ohh.domainname;

import com.ohh.domainname.entity.core.EntityClass;
import com.ohh.domainname.entity.core.WebSiteEntity;
import com.ohh.domainname.entity.core.WorkThread;
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
    private static final int THREAD_COUNT_NUM = 20;
    // 长度范围, 最小长度
    private static final int MIN_LEN = 4;
    // 长度范围, 最大长度
    private static final int MAX_LEN = 4;
    // 导出文件路径
    private static final String FILE_PATH = "C:\\Users\\Administrator\\Desktop\\4char-start-with-5-domain.txt";

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(DomainnameApplication.class, args);
        ThreadFactory threadFactory = r -> {
            WorkThread workThread = new WorkThread(r, new RestTemplate(context.getBean(ClientHttpRequestFactory.class)));
            System.out.println("新的线程被启动");
            return workThread;
        };
        ExecutorService executorService = Executors.newFixedThreadPool(THREAD_COUNT_NUM, threadFactory);

        // 输出到文件
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
            // 搜索
            WebSiteEntity entity = new Eb();
            String api = entity.getApi();
            List<String> words;
            // 根据域名字符长度进行搜索
            for (int i = MIN_LEN; i <= MAX_LEN; i++) {
                System.out.println("正在搜索长度为: " + i + "的域名");
                // 获取长度为 i 的域名所有可能的字符组合
                Algorithm.setRound("5---","5zzz");
                words = Algorithm.getWords(i);
                CountDownLatch countDownLatch = new CountDownLatch(words.size());
                // 搜索长度为 i 的所有域名
                for (String word : words) {
                    executorService.execute(() -> {
                        while (true) {
                            try {
                                // 发送 http 请求
                                String json = ((WorkThread) (Thread.currentThread())).getRestTemplate().getForObject(api, String.class, word);
                                // 解析 json
                                EntityClass entityClass = entity.parseJson(json);
                                if (Objects.nonNull(entityClass)) {
                                    // 写入文件
                                    synchronized (WRITE_LOCK) {
                                        bw.write(LocalDateTime.now() + ": 域名 '" + word + "', " + entityClass);
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
                System.out.println("remainingCapacity=" + ((ThreadPoolExecutor) executorService).getQueue().remainingCapacity());
                System.out.println("rest task size=" + ((ThreadPoolExecutor) executorService).getQueue().size());
                countDownLatch.await();
                System.out.println("长度为" + i + "的任务执行完毕");
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}