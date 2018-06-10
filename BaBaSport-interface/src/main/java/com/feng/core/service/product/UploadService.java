package com.feng.core.service.product;

public interface UploadService {
	/**
	 * 上传图片
	 * @param pic 图片二进制数组
	 * @param name 图片名字(主要为获得扩展名称)
	 * @param size 大小
	 * @return
	 */
	public String uploadPic(byte[] pic,String name,long size) ;
}
