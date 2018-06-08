package com.feng.core.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.feng.core.bean.Test;
import com.feng.core.dao.TestDao;

@Service("testService")
@Transactional
public class TestServiceImpl implements TestService {
	@Autowired
	private TestDao testDao;
	
	public void insertTest(Test test) {
		testDao.insertTest(test);
//		throw new RuntimeException();
	}
}
