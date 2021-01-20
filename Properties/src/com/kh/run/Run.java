package com.kh.run;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class Run {

	public static void main(String[] args) {
		
		// Properties => Map �迭 (key + value ��Ʈ��) 
		// Ư¡ : key��, value�� ��δ� String ���ڿ�!!
		// 		properties�� ����ִ� key + value ���� �ܺ� ���� (.properties)�� ��� ����
		//		�Ǵ� �ݴ�� �ܺ� ����(.properties)�� ����� �����͸� �Է¹޾ƿ��� �� ���� 
		
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