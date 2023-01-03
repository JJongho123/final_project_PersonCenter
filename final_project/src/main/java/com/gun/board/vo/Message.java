package com.gun.board.vo;

import java.util.Date;

public class Message {
	// ��ȣ
	private int message_num;

	// ����
	private String cus_id;

	// ����
	private String friend_id;

	// ����
	private String message_title;

	// ����
	private String message;

	// ����->���� ������ ��
	private String cus_status;

	// ������ ����->Ȯ���ߴ��� ���� Ȯ�� ���ߴ���
	private String friend_status;

	// ���� ��¥
	private Date message_date;
	
	private int board_num;

	public int getBoard_num() {
		return board_num;
	}

	public void setBoard_num(int board_num) {
		this.board_num = board_num;
	}

	public int getMessage_num() {
		return message_num;
	}

	public void setMessage_num(int message_num) {
		this.message_num = message_num;
	}

	public String getMessage_title() {
		return message_title;
	}

	public void setMessage_title(String message_title) {
		this.message_title = message_title;
	}

	public Date getMessage_date() {
		return message_date;
	}

	public void setMessage_date(Date message_date) {
		this.message_date = message_date;
	}

	public String getCus_id() {
		return cus_id;
	}

	public void setCus_id(String cus_id) {
		this.cus_id = cus_id;
	}

	public String getFriend_id() {
		return friend_id;
	}

	public void setFriend_id(String friend_id) {
		this.friend_id = friend_id;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getCus_status() {
		return cus_status;
	}

	public void setCus_status(String cus_status) {
		this.cus_status = cus_status;
	}
	
	public String getFriend_status() {
		return friend_status;
	}
	
	public void setFriend_status(String friend_status) {
		this.friend_status = friend_status;
	}

	@Override
	public String toString() {
		return "Message [message_num=" + message_num + ", cus_id=" + cus_id + ", friend_id=" + friend_id
				+ ", message_title=" + message_title + ", message=" + message + ", cus_status=" + cus_status
				+ ", friend_status=" + friend_status + ", message_date=" + message_date + ", board_num=" + board_num
				+ "]";
	}


}
