package com.gun.board.dao;

import java.util.ArrayList;

import com.gun.board.vo.Reply;

public interface ReplyDAO {


	public ArrayList<Reply> getReplies(int board_num) throws Exception;

	public int insertReply(Reply reply) throws Exception;

	public int deleteReply(int reply_num) throws Exception;

	public Reply getReply(int reply_num) throws Exception;

	public int updateReply(Reply reply) throws Exception;


	public int recentlyAddedReplynum() throws Exception;

	public int updateRReply_num(int reply_num) throws Exception;

}
