package com.gun.board.dao;

import java.util.ArrayList;
import java.util.Map;

import com.gun.board.vo.Customer;

public interface CustomerDAO {


	public int insertCustomer(Customer customer) throws Exception;


	public Customer selectCustomer(String cus_id) throws Exception;


	public String selectNickname(String cus_id) throws Exception;


	public String findPassword(Customer customer) throws Exception;
	

	public void insertPhoto(Customer customer) throws Exception;
	

	public Customer getPhoto(String cus_id) throws Exception;
	

	public void updateCustomer(Customer customer) throws Exception;
	
	
	public void updatePhoto(Customer customer) throws Exception;
	

	public int joinIdCheck(String cus_id) throws Exception;
	

	public int passChk(Customer customer) throws Exception;
	

	public void deleteCustomer(String cus_id) throws Exception;
	

	public ArrayList<Customer> selectAllCustomer() throws Exception;

	// 글쓰기시 카운트업
	public void countUp(String cus_id)throws Exception;

	public int checkCount(String cus_id)throws Exception;
	
	public void GradeUpdate(Map<String, Object> grade)throws Exception;

	public String getcus_grade(String board_id)throws Exception;
	
	// 임시 비밀번호로 업데이트
	public void temporaryPassword(Map<String, String> temporary)throws Exception;
	

}
