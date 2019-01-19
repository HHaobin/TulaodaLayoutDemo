package com.gjs.gjsmall.utils;


import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class StreamTool {

	/**
	 * 解析输入流为字符串
	 * @param is
	 * @return  解析好的字符串
	 */
	public static String readStrem(InputStream is) {
//		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		StringBuilder stringBuilder = new StringBuilder();
		String        str           = null;
		try {
			byte [] buffer = new byte[1024];
			int len =0 ;
			while(  (len = is.read(buffer) )  != -1){
				
				stringBuilder.append(new String(buffer, 0, len , "gbk"));
//				bos.write(buffer, 0, len);
			}
			is.close();
			str =  stringBuilder.toString();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return str;
	}

	//就 用于将一个 流 的数据转换成   字符串 返回
	public static String decodeStream(InputStream in) throws IOException {

		//底层流
		ByteArrayOutputStream baos = new ByteArrayOutputStream();

		int len=0;
		byte[] buf = new byte[1024];
		while((len=in.read(buf))>0){
			baos.write(buf, 0, len);
		}

		baos.close();
		//返回 字符 数据
		return baos.toString();
	}


}
