package com.feng;


import java.sql.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.feng.core.dao.TestDao;
import com.feng.core.service.TestService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:application-context.xml"})
public class TestDaoTest {
	@Autowired
	private TestDao testDao;
	@Autowired
	private TestService testService;
	
	@Test
	public void testAdd() throws Exception{
		com.feng.core.bean.Test test = new com.feng.core.bean.Test();
		test.setName("测试数据");
		test.setBirthday(new Date(0));
		testDao.insertTest(test);
	}
	
	@Test
	public void testAdd2() throws Exception{
		com.feng.core.bean.Test test = new com.feng.core.bean.Test();
		test.setName("测试数据2");
		test.setBirthday(new Date(System.currentTimeMillis()));
		testService.insertTest(test);
	}
	
}
