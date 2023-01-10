package com.gun.board.dao;

import java.util.ArrayList;
import java.util.Map;

import com.gun.board.vo.Message;

public interface MessageDAO {

	
	public ArrayList<Message> getMessage(Map<String, String> message) throws Exception;

	
	public int sendMessage(Message message) throws Exception;

	
	public int sendMessageFriend(Message message) throws Exception;

	
	public Message get(int message_num) throws Exception;


	public int readMessage(Map<String, Integer> read) throws Exception;


	public int delete(int message_num) throws Exception;


	public int deleteStatus(Map<String, Integer> change) throws Exception;


	public int numofMessage(Map<String, String> num) throws Exception;
}
