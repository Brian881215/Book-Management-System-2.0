package com.example.cathayLibrarySystemDemo;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootTest;

import com.cathay.libraryManagement.platform.CathayLibrarySystemDemoApplication;

import mockit.Expectations;
import mockit.Injectable;
import mockit.Verifications;
//@RunWith(SpringRunner.class)
@SpringBootTest(classes = com.cathay.libraryManagement.platform.CathayLibrarySystemDemoApplication.class)
class CathayLibrarySystemDemoApplicationTests {

//	 @Test
//	  void testMain() {
//	        // 模拟 Spring Boot 的 SpringApplication.run 方法
//	        try (MockedStatic<SpringApplication> springAppMock = Mockito.mockStatic(SpringApplication.class)) {
//	            // 模拟日志
//	            Logger logger = LoggerFactory.getLogger(CathayLibrarySystemDemoApplication.class);
//	            // 调用 main 方法
//	            CathayLibrarySystemDemoApplication.main(new String[] {});
//	            // 验证 Spring Boot 应用程序是否成功启动
//	            springAppMock.verify(() -> SpringApplication.run(CathayLibrarySystemDemoApplication.class, any(String[].class)));
//	            // 验证是否记录了预期的日志消息
//	            Mockito.verify(logger).info("Hello World");
//	        } 
//	    }

	    @Injectable
	    private Logger logger; // 模拟日志记录器

	    @Test
	    public void testMain() {
	        // 模拟 Spring Boot 的 SpringApplication.run 方法
	        new Expectations(SpringApplication.class) {{
	            SpringApplication.run(CathayLibrarySystemDemoApplication.class, (String[]) any);
	        }};

	        CathayLibrarySystemDemoApplication.main(new String[] {});

	        // 验证是否记录了预期的日志消息
	        new Verifications() {{
	            logger.info("Hello World");
	        }};
	    }
	 
	 
}
