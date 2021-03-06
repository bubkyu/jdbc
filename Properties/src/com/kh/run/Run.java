package com.kh.run;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class Run {

	public static void main(String[] args) {
		
		// Properties => Map 계열 (key + value 세트로) 
		// 특징 : key값, value값 모두다 String 문자열!!
		// 		properties에 담겨있는 key + value 들을 외부 파일 (.properties)로 출력 가능
		//		또는 반대로 외부 파일(.properties)에 기술된 데이터를 입력받아오는 거 가능 
		
		Properties prop = new Properties(); 
		
		try {
			prop.load(new FileReader("resources/test.properties"));
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println(prop);
		
		
		System.out.println(prop.getProperty("Create"));
		System.out.println(prop.getProperty("Read"));
		
	}

}