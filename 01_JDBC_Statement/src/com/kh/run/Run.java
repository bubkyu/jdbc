package com.kh.run;

import com.kh.view.MemberMenu;

public class Run {

	public static void main(String[] args) {
		
		/*
		 * model 	= �����Ϳ� ���� ó�� ����
		 * 	> vo  	= �����͸� ���� �� �ִ� ����
		 * 	> dao	= DB�� ���������� �����ؼ� sql�� ���� �� ���
		 * 	
		 * 
		 * view		= ����ڰ� ���Ե� �ð����� ���(ȭ��) -> �Է� �� ��� (Scanner / print)
		 * 
		 * view - > Controller	: ��û, �޼ҵ� ȣ��[���ް�]
		 * Controller - > view : �޼ҵ� ȣ��, 
		 * 
		 * controller = ����ڰ� ��û�� ������ ó�� ����
		 * view ���� ���޹��� �����͸� ����ó�� �� Dao ��û
		 * 
		 * Controller -> DAO : �޼ҵ� ȣ��[���ް�]
		 * Dao ���� ��ȯ���� ����� �������� �������� �Ǵ�(���ǽ�)
		 * 
		 * Dao		= DB�� ���� ���� �� �� sql �� ���� 1) DB�� ���� 2) sql�� ���� �� ��� �ޱ� , ���� DML�����̿��ٸ�, Ʈ�����ó�� 3) JDBC��ü �ڿ� �ݳ� 4) ��� ��ȯ
		 * 
		 * Dao - > Controller :  return ���
		 *   
		 */
			// ���α׷� ���ุ ���( ��, ����ڰ� ���Ե� ù ȭ���� ����ֱ⸸ �ϸ��)
		new MemberMenu().mainMenu();
		
	}

}

















