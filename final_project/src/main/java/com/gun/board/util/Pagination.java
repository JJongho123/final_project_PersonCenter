package com.gun.board.util;

import java.util.ArrayList;

import com.gun.board.vo.Board;
import com.gun.board.vo.Customer;
import com.gun.board.vo.Message;
import com.gun.board.vo.Union;

public class Pagination {
	// ����¡ó�� ���� Ŭ����

	// Ȩ�� ��Ÿ�� �Խù�(7���� �ڸ� ��)
	public ArrayList<Board> totalPosts_home(ArrayList<Board> boards, int page) {
		ArrayList<Board> result = new ArrayList();
		int startPost = (page - 1) * Configuration.POSTS_HOME;
		int endPost = startPost + Configuration.POSTS_HOME;
		if (endPost > boards.size()) {
			endPost = boards.size();
		}
		for (int a = startPost; a < endPost; a++) {
			result.add(boards.get(a));
		}
		return result;
	}

	// �� ������ ���
	public int totalPages(ArrayList<Board> boards) {
		int result = 0;
		result = (boards.size() / Configuration.POSTS);
		if (boards.size() % Configuration.POSTS != 0 || result == 0) {
			result += 1;
		}
		return result;
	}

	public int totalPagesMessage(ArrayList<Message> message) {
		int result = 0;
		result = (message.size() / Configuration.POSTS);
		if (message.size() % Configuration.POSTS != 0 || result == 0) {
			result += 1;
		}
		return result;
	}

	// ������ ������ ���ϱ�
	public int endPage(int page, int totalPages) {
		int endPage = 0;
		endPage = page + Configuration.PAGES;
		if (endPage > totalPages) {
			endPage = totalPages;
		}
		return endPage;
	}

	// �� �������� ��Ÿ���� �Խñ�
	public ArrayList<Board> totalPosts(ArrayList<Board> boards, int page) {
		ArrayList<Board> result = new ArrayList();
		int startPost = (page - 1) * Configuration.POSTS;
		int endPost = startPost + Configuration.POSTS;
		if (endPost > boards.size()) {
			endPost = boards.size();
		}
		for (int a = startPost; a < endPost; a++) {
			result.add(boards.get(a));
		}
		return result;
	}

	public ArrayList<Message> totalPostsMessage(ArrayList<Message> message, int page) {
		ArrayList<Message> result = new ArrayList();
		int startPost = (page - 1) * Configuration.POSTS;
		int endPost = startPost + Configuration.POSTS;
		if (endPost > message.size()) {
			endPost = message.size();
		}
		for (int a = startPost; a < endPost; a++) {
			result.add(message.get(a));
		}
		return result;
	}

	// ������ �� ����
	public int getCurrentPage(int page, int totalPages) {
		if (page <= 0) {
			page = 1;
		} else if (page > totalPages && totalPages != 0) {
			page = totalPages;
		}
		return page;
	}

	// ������ ������ ���������̼� �߰��Ȱ�
	public static int cus_totalPages(ArrayList<Customer> customers) {
		int result = 0;
		result = (customers.size() / Configuration.POSTS);
		if (customers.size() % Configuration.POSTS != 0 || result == 0) {
			result += 1;
		}
		return result;
	}

	public static ArrayList<Customer> cus_totalPosts(ArrayList<Customer> customers, int page) {
		ArrayList<Customer> result = new ArrayList();
		int startPost = (page - 1) * Configuration.POSTS;
		int endPost = startPost + Configuration.POSTS;
		if (endPost > customers.size()) {
			endPost = customers.size();
		}
		for (int a = startPost; a < endPost; a++) {
			result.add(customers.get(a));
		}
		return result;
	}

	public static int cus_getCurrentPage(int page, int totalPages) {
		if (page <= 0) {
			page = 1;
		} else if (page > totalPages && totalPages != 0) {
			page = totalPages;
		}
		return page;
	}

	public static int cus_endPage(int page, int totalPages) {
		int endPage = 0;
		endPage = page + Configuration.PAGES;
		if (endPage > totalPages) {
			endPage = totalPages;
		}
		return endPage;
	}

	public ArrayList<Union> totalPosts_uhome(ArrayList<Union> union, int page) {
		ArrayList<Union> result = new ArrayList();
		int startPost = (page - 1) * Configuration.POSTS_HOME;
		int endPost = startPost + Configuration.POSTS_HOME;
		if (endPost > union.size()) {
			endPost = union.size();
		}
		for (int a = startPost; a < endPost; a++) {
			result.add(union.get(a));
		}
		return result;
	}

}
