package com.gun.board.vo;

import java.util.Date;

public class Union {
	
	private int board_num;
	
	private Date board_date;
	
	private String board_title;
	
	private int board_check;
	
	
	
	public int getBoard_check() {
		return board_check;
	}

	public void setBoard_check(int board_check) {
		this.board_check = board_check;
	}

	public int getBoard_num() {
		return board_num;
	}

	public void setBoard_num(int board_num) {
		this.board_num = board_num;
	}

	public Date getBoard_date() {
		return board_date;
	}

	public void setBoard_date(Date board_date) {
		this.board_date = board_date;
	}

	public String getBoard_title() {
		return board_title;
	}

	public void setBoard_title(String board_title) {
		this.board_title = board_title;
	}

	@Override
	public String toString() {
		return "Union [board_num=" + board_num + ", board_date=" + board_date + ", board_title=" + board_title
				+ ", board_check=" + board_check + "]";
	}

}
