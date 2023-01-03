package com.gun.board.repository;

import java.util.ArrayList;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.gun.board.dao.UnionDAO;
import com.gun.board.vo.Union;

@Repository
public class UnionRepository {

	@Inject
	private SqlSession sqlSession;
	
	UnionDAO udao;
	
	public ArrayList<Union> getUnion_home() {
		ArrayList<Union> result = new ArrayList();
		udao = sqlSession.getMapper(UnionDAO.class);
		try {
			result = udao.getUnion_home();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
}
