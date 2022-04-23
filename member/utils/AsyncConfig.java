package jejufriends.member.utils;

import java.util.concurrent.Executor;

import javax.annotation.Resource;

import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

//email �����µ� 5�ʳ��ɷ��� �̸�ó���ϱ�
@Configuration
@EnableAsync
public class AsyncConfig implements AsyncConfigurer{
	
	private static int TASK_CORE_POOL_SIZE = 2;//�⺻�����������
	private static int TASK_MAX_POOL_SIZE = 4;//�ִ뾲���������
	private static int TASK_QUEUE_CAPACITY = 0;//Max�����尡 �����ϴ� ��� ����ϴ� ť ������
	private static String EXECUTOR_BEAN_NAME = "executor";
	
	@Resource(name="executor")
	private ThreadPoolTaskExecutor executor;
	
	@Bean(name="executor")
	@Override
	public Executor getAsyncExecutor() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		setExecutor(executor);
		return executor;
	}
	
	
	public void setExecutor(ThreadPoolTaskExecutor executor) {
		executor.setCorePoolSize(TASK_CORE_POOL_SIZE);
		executor.setMaxPoolSize(TASK_MAX_POOL_SIZE);
		executor.setQueueCapacity(TASK_QUEUE_CAPACITY);
		executor.setBeanName(EXECUTOR_BEAN_NAME);
		executor.initialize();
	}
	
	@Override
	public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
		return null;
	}
}
