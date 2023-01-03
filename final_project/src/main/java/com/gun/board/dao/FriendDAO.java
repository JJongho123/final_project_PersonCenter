package com.gun.board.dao;

import java.util.ArrayList;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.gun.board.vo.Customer;
import com.gun.board.vo.Friend;

public interface FriendDAO {

	
	public ArrayList<Customer> findFriends(Map<String, String> search) throws Exception;
    
	public int numofFriendRequest(String cus_id) throws Exception;

	public String getStatus(Map<String, String> search) throws Exception;

	
	public int friendRequest(Map<String, String> request) throws Exception;

	public int friendRequestFriend(Map<String, String> request) throws Exception;

	public ArrayList<String> getList(String cus_id) throws Exception;

	public ArrayList<String> getRequestList(@Param("cus_id")String cus_id, @Param("board_num")int board_num) throws Exception;

	public int accept(Map<String, String> accept) throws Exception;

	public int acceptFriend(Map<String, String> accept) throws Exception;

	public int remove(Map<String, String> remove) throws Exception;

	public int removeFriend(Map<String, String> remove) throws Exception;

	public String getfriend(String login_id) throws Exception;
	
	public int accept_2(Map<String, Object> accept)throws Exception;

	public void acceptFriend_2(Map<String, Object> accept)throws Exception;

	public String getStatus_2(Map<String, Object> search)throws Exception;

	public int friendRequest_2(Map<String, Object> request)throws Exception;

	public void friendRequestFriend_2(Map<String, Object> request)throws Exception;

	public String getfriend_2(Map<String, Object> request)throws Exception;

	public String getStatus_board(Map<String, Object> search)throws Exception;

	public Friend getTrader(Map<String, Object> search)throws Exception;
	
	
}
