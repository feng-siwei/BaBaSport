package com.feng.common.conversion;

import org.springframework.core.convert.converter.Converter;

/**
 * 自定义转换器
 * 去掉空格
 * @author 冯思伟
 *Converter<S, T>
 *S 页面上类型
 *T 转换后类型
 */
public class CustomConverter implements Converter<String, String> {

	//去掉前后空格
	@Override
	public String convert(String source) {
		try {
			if (null != source) {
				source = source.trim();
				if (!"".equals(source)) {
					return source;
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}
	
}
