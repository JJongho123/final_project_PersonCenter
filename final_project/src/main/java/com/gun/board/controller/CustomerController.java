package com.gun.board.controller;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Random;

import javax.inject.Inject;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.gun.board.repository.AuctionRepository;
import com.gun.board.repository.BoardRepository;
import com.gun.board.repository.CustomerRepository;
import com.gun.board.repository.DataRepository;
import com.gun.board.repository.FreeRepository;
import com.gun.board.repository.FriendRepository;
import com.gun.board.repository.MessageRepository;
import com.gun.board.repository.NoticeRepository;
import com.gun.board.repository.UnionRepository;
import com.gun.board.util.Configuration;
import com.gun.board.util.FileService;
import com.gun.board.util.Pagination;
import com.gun.board.util.Pagination_Auction;
import com.gun.board.util.Pagination_Data;
import com.gun.board.util.Pagination_Free;
import com.gun.board.vo.Auction;
import com.gun.board.vo.Board;
import com.gun.board.vo.Customer;
import com.gun.board.vo.Data;
import com.gun.board.vo.Free;
import com.gun.board.vo.MailHandler;
import com.gun.board.vo.Notice;
import com.gun.board.vo.TempKey;
import com.gun.board.vo.Union;

@RequestMapping(value = "/customer")
@Controller
public class CustomerController {

	private static final Logger logger = LoggerFactory.getLogger(CustomerController.class);

	@Inject
	CustomerRepository cRepository;

	@Inject
	FriendRepository fRepository;

	@Inject
	MessageRepository mRepository;

	@Inject
	BoardRepository bRepository;

	@Inject
	NoticeRepository nRepository;

	@Inject
	FreeRepository frRepository;

	@Inject
	DataRepository dRepository;

	@Inject
	AuctionRepository aRepository;

	@Inject
	UnionRepository uRepository;

	@Inject
	BCryptPasswordEncoder pwdEncoder;
	
	@Autowired
	JavaMailSender mailSender;

	Pagination Pagination = new Pagination();
	Pagination_Free Paginationf = new Pagination_Free();
	Pagination_Data Paginationd = new Pagination_Data();
	Pagination_Auction Paginationa = new Pagination_Auction();

	@Inject
	HttpSession session;
	
	private int authNumber ;

	@RequestMapping(value = "/join", method = RequestMethod.GET)
	public String home(Model model) {
		logger.info("회원가입");
		return "customer/join";
	}
	
//	@RequestMapping(value = "/join", method = RequestMethod.GET)
//	public String emailConfirm(Customer customer)throws Exception{
//		cRepository.updateMailAuth(customer);
//		
//		return "home";
//	}
	
	public void makeRandomNumber() {
		// 난수의 범위 111111 ~ 999999 (6자리 난수)
		Random r = new Random();
		int checkNum = r.nextInt(888888) + 111111;
		System.out.println("인증번호 : " + checkNum);
		authNumber = checkNum;
		
	}
	

	@RequestMapping(value = "/join", method = RequestMethod.POST)
	public String join(Model model, Customer customer, MultipartFile upload)throws Exception {
		
		
		// 이메일인증
		makeRandomNumber();
		
		cRepository.updateMailAuth(customer);
		
        String mail_key = new TempKey().getKey(30,false);
        customer.setMail_key(mail_key);

        cRepository.updateMailKey(customer);
        System.out.println("customer.getEmail() : " + customer.getEmail());
        MailHandler sendMail = new MailHandler(mailSender);
        sendMail.setSubject("인증메일입니다");
        sendMail.setText(
        		"<h1>메일인증</h1>" +
        		"<br>아래 [이메일 인증 확인]을 눌러주세요."+
        		"<br><h1>인증 번호는 "+authNumber+"입니다.</h1>");
        		sendMail.setFrom("jjh33534@gmail.com","JJH");
        		sendMail.setTo(customer.getEmail());
        		sendMail.send();
        
        // 이메일 끝
        
		// 암호화 작업
		String inputpasswd = customer.getCus_pw();
		String encodeigpasswd = pwdEncoder.encode(inputpasswd);
		customer.setCus_pw(encodeigpasswd);
		logger.info(customer.getCus_pw());
		// 암호화 작업 끝

		cRepository.insertCustomer(customer);

		System.out.println("upload  : " + upload);

		if (!upload.isEmpty()) {
			System.out.println("upload file's name: " + upload.getOriginalFilename());
			String board_uploadfileid = FileService.saveFile(upload, Configuration.PHOTOPATH);
			customer.setBoard_fileid(upload.getOriginalFilename());
			customer.setBoard_uploadfileid(board_uploadfileid);
			cRepository.insertPhoto(customer);
		}

		// 거래글(6개)
		int page = 1;
		for (int i = 1; i < 7; i++) {
			ArrayList<Board> boards = bRepository.getBoards_home(i);
			boards = Pagination.totalPosts_home(boards, page);
			model.addAttribute("boards_home_" + i, boards);
		}
		model.addAttribute("page", page);

		// 공지사항
		ArrayList<Notice> Notice_boards = nRepository.getNotice_home();
		model.addAttribute("Notice_boards", Notice_boards);

		// 자유게시판
		ArrayList<Free> free = frRepository.getFree_home();
		free = Paginationf.totalPosts_home_free(free, page);
		model.addAttribute("boards_free", free);

		// 정보게시판
		ArrayList<Data> data = dRepository.getData_home();
		data = Paginationd.totalPosts_home_data(data, page);
		model.addAttribute("boards_data", data);

		// 경매게시판
		ArrayList<Auction> auction = aRepository.getAuction_home();
		auction = Paginationa.totalPosts_home(auction, page);
		model.addAttribute("auction", auction);

		logger.info("회원정보: : " + customer);
		return "home";

	}

	// 파일 받아오기
	@RequestMapping(value = "/download", method = RequestMethod.GET)
	public String download(HttpServletResponse response) {

		String cus_id = (String) session.getAttribute("loginid");

		Customer customer = cRepository.getPhoto(cus_id);

		String originalfile = customer.getBoard_fileid();

		try {
			response.setHeader("Content-Disposition",
					"attachment;filename=" + URLEncoder.encode(originalfile, "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String fullpath = Configuration.PHOTOPATH + "/" + customer.getBoard_uploadfileid();

		ServletOutputStream fileout = null;
		FileInputStream filein = null;

		try {
			filein = new FileInputStream(fullpath);
			fileout = response.getOutputStream();
			FileCopyUtils.copy(filein, fileout);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if (filein != null) {
					filein.close();
				}
				if (fileout != null) {
					fileout.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login() {
		return "home";
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(Authentication authentication, HttpServletRequest request, HttpServletResponse response,
			Model model, Customer customer) throws IOException {

		Customer cusCompare = cRepository.selectCustomer(customer.getCus_id());

		if (cusCompare == null || !pwdEncoder.matches(customer.getCus_pw(), cusCompare.getCus_pw())) {

			response.setCharacterEncoding("EUC-KR");
			PrintWriter writer = response.getWriter();
			writer.println("<script type='text/javascript'>");
			writer.println("alert('아이디 또는 비밀번호가 불일치합니다.');");
			writer.println("history.back();");
			writer.println("</script>");
			writer.flush();
			model.addAttribute("loginResult", "Login failed");
			return null;
		} else {
			logger.info("로그인");
			model.addAttribute("loginResult", "Login succeeded");
			session.setAttribute("loginid", cusCompare.getCus_id());
			session.setAttribute("loginNickname", cusCompare.getCus_nickname());

			// 아이디 닉네임 설정
			int numofFriendRequest = fRepository.numofFriendRequest(customer.getCus_id());
			session.setAttribute("numofFriendRequest", numofFriendRequest);
			int numofReadMessage = mRepository.numofMessage(customer.getCus_id(), "read");
			session.setAttribute("numofReadMessage", numofReadMessage);
			int numofSentMessage = mRepository.numofMessage(customer.getCus_id(), "sent");
			session.setAttribute("numofSentMessage", numofSentMessage);

			// 이미지 파일 get
			Customer customer2 = cRepository.getPhoto(customer.getCus_id());
			session.setAttribute("customer", customer2.getBoard_fileid());

			// 거래글(6개)
			int page = 1;
			for (int i = 1; i < 7; i++) {
				ArrayList<Board> boards = bRepository.getBoards_home(i);
				boards = Pagination.totalPosts_home(boards, page);
				model.addAttribute("boards_home_" + i, boards);
			}
			model.addAttribute("page", page);

			// 공지사항
			ArrayList<Notice> Notice_boards = nRepository.getNotice_home();
			model.addAttribute("Notice_boards", Notice_boards);

			// 자유게시판
			ArrayList<Free> free = frRepository.getFree_home();
			free = Paginationf.totalPosts_home_free(free, page);
			model.addAttribute("boards_free", free);

			// 정보게시판
			ArrayList<Data> data = dRepository.getData_home();
			data = Paginationd.totalPosts_home_data(data, page);
			model.addAttribute("boards_data", data);

			// 경매게시판
			ArrayList<Auction> auction = aRepository.getAuction_home();
			auction = Paginationa.totalPosts_home(auction, page);
			model.addAttribute("auction", auction);

			// 최신글뽑기위한 전체게시판 통합
			ArrayList<Union> union = uRepository.getUnion_home();
			union = Pagination.totalPosts_uhome(union, page);
			model.addAttribute("union", union);

			return "home";
		}
	}

	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout(Model model) {
		session.invalidate();
		logger.info("로그아웃");

		// 거래글(6개)
		int page = 1;
		for (int i = 1; i < 7; i++) {
			ArrayList<Board> boards = bRepository.getBoards_home(i);
			boards = Pagination.totalPosts_home(boards, page);
			model.addAttribute("boards_home_" + i, boards);
		}
		model.addAttribute("page", page);

		// 공지사항
		ArrayList<Notice> Notice_boards = nRepository.getNotice_home();
		model.addAttribute("Notice_boards", Notice_boards);

		// 자유게시판
		ArrayList<Free> free = frRepository.getFree_home();
		free = Paginationf.totalPosts_home_free(free, page);
		model.addAttribute("boards_free", free);

		// 정보게시판
		ArrayList<Data> data = dRepository.getData_home();
		data = Paginationd.totalPosts_home_data(data, page);
		model.addAttribute("boards_data", data);

		// 경매게시판
		ArrayList<Auction> auction = aRepository.getAuction_home();
		auction = Paginationa.totalPosts_home(auction, page);
		model.addAttribute("auction", auction);

		model.addAttribute("loginResult", "Logout succeeded");

		return "home";
	}

	@RequestMapping(value = "/findPassword", method = RequestMethod.GET)
	public String findPassword() {
		return "customer/findPassword";
	}

	@RequestMapping(value = "/findPassword", method = RequestMethod.POST)
	public @ResponseBody String findPassword(Customer customer) {
		String password = cRepository.findPassword(customer);
		if (password == null) {
			return "false";
		} else {
			return password;
		}
	}

	// 로그아웃 했다가 다시 로그인
	@RequestMapping(value = "customer/login", method = RequestMethod.POST)
	public String logout_login(HttpServletRequest request, HttpServletResponse response, Model model, Customer customer)
			throws IOException {
		Customer cusCompare = cRepository.selectCustomer(customer.getCus_id());
		if (cusCompare == null || !pwdEncoder.matches(customer.getCus_pw(), cusCompare.getCus_pw())) {
			response.setCharacterEncoding("EUC-KR");
			PrintWriter writer = response.getWriter();
			writer.println("<script type='text/javascript'>");
			writer.println("alert('아이디 또는 비밀번호가 불일치합니다.');");
			writer.println("history.back();");
			writer.println("</script>");
			writer.flush();
			model.addAttribute("loginResult", "Login failed");
			return null;
		} else {
			logger.info("로그인");
			model.addAttribute("loginResult", "Login succeeded");
			session.setAttribute("loginid", cusCompare.getCus_id());
			session.setAttribute("loginNickname", cusCompare.getCus_nickname());
			// 아이디 닉네임 설정
			int numofFriendRequest = fRepository.numofFriendRequest(customer.getCus_id());
			session.setAttribute("numofFriendRequest", numofFriendRequest);
			int numofReadMessage = mRepository.numofMessage(customer.getCus_id(), "read");
			session.setAttribute("numofReadMessage", numofReadMessage);
			int numofSentMessage = mRepository.numofMessage(customer.getCus_id(), "sent");
			session.setAttribute("numofSentMessage", numofSentMessage);
			
			Customer customer2 = cRepository.getPhoto(customer.getCus_id());
			session.setAttribute("customer", customer2.getBoard_fileid());

			// 거래글(6개)
			int page = 1;
			for (int i = 1; i < 7; i++) {
				ArrayList<Board> boards = bRepository.getBoards_home(i);
				boards = Pagination.totalPosts_home(boards, page);
				model.addAttribute("boards_home_" + i, boards);
			}
			model.addAttribute("page", page);

			// 공지사항
			ArrayList<Notice> Notice_boards = nRepository.getNotice_home();
			model.addAttribute("Notice_boards", Notice_boards);

			// 자유게시판
			ArrayList<Free> free = frRepository.getFree_home();
			free = Paginationf.totalPosts_home_free(free, page);
			model.addAttribute("boards_free", free);

			// 정보게시판
			ArrayList<Data> data = dRepository.getData_home();
			data = Paginationd.totalPosts_home_data(data, page);
			model.addAttribute("boards_data", data);

			// 경매게시판
			ArrayList<Auction> auction = aRepository.getAuction_home();
			auction = Paginationa.totalPosts_home(auction, page);
			model.addAttribute("auction", auction);

			model.addAttribute("loginResult", "Logout succeeded");

			return "home";

		}
	}

	// 회원 정보 수정 뷰
	@RequestMapping(value = "/vertify", method = RequestMethod.GET)
	public String vertify_info(Customer customer, Model model) {
		String cus_id = (String) session.getAttribute("loginid");
		Customer cusCompare = cRepository.getPhoto(cus_id);
		model.addAttribute("customer", cusCompare);
		logger.info("회원수정 페이지 이동");
		return "customer/vertify";
	}

	// 회원 정보 수정
	@RequestMapping(value = "/vertifyUpdate", method = RequestMethod.POST)
	public String vertify_info(Model model, Customer customer, MultipartFile upload) {
		logger.info("회원수정 완료 !!! ");

		// 암호화 작업
		String inputpasswd = customer.getCus_pw();
		String encodeigpasswd = pwdEncoder.encode(inputpasswd);
		customer.setCus_pw(encodeigpasswd);
		
		// 암호화 작업 끝

		String cus_id = (String) session.getAttribute("loginid");
		Customer originalBoard = cRepository.getPhoto(cus_id);
		String board_fileid = "";
		String board_uploadfileid = "";

		if (originalBoard.getBoard_fileid() != null) {
			board_fileid = originalBoard.getBoard_fileid();
			board_uploadfileid = originalBoard.getBoard_uploadfileid();
		}
		if (upload.getOriginalFilename() != null) {
			if (originalBoard.getBoard_uploadfileid() != null) {
				board_uploadfileid = originalBoard.getBoard_uploadfileid();
				FileService.deleteFile(board_uploadfileid);
			}
			board_uploadfileid = FileService.saveFile(upload, Configuration.PHOTOPATH);
			System.out.println("after adjustment: " + board_uploadfileid);
		}
		if (originalBoard.getBoard_fileid() != null || upload.getOriginalFilename() != null) {
			customer.setBoard_fileid(board_fileid);
			customer.setBoard_uploadfileid(board_uploadfileid);
		}

		ArrayList<Customer> customers = cRepository.updateCustomer(customer);

		return "home";
	}

	// 회원가입시 아이디 중복체크
	@RequestMapping(value = "/joinIdCheck", method = RequestMethod.POST)

	public @ResponseBody int joinIdCheck(String cus_id) {
		logger.info("joinIdCheck 도달@@ cus_id : ", cus_id);
		System.out.println("joinIdCheck 도달@@ cus_id : " + cus_id);
		int result = cRepository.joinIdCheck(cus_id);
		return result;
	}

	// 회원 탈퇴 뷰로 이동
	@RequestMapping(value = "/withdrawalView", method = RequestMethod.GET)
	public String withdrawal_info(Customer customer, Model model) {
		

		logger.info("회원 탈퇴 뷰 페이지 이동 컨트롤러  !!! ");
		return "customer/withdrawalView";
	}

	// 회원 탈퇴 !!
	@RequestMapping(value = "/deleteCustomer", method = RequestMethod.POST)
	public String deleteCustomer(Model model) {

		String cus_id = (String) session.getAttribute("loginid");
		cRepository.deleteCustomer(cus_id);
		session.invalidate();

		logger.info("회원 탈퇴 컨트롤러 !! @@ : ");

		return "home";
	}

	// 회원탈퇴 비밀번호 확인
	@ResponseBody
	@RequestMapping(value = "/passChk", method = RequestMethod.POST)
	public int passChk(Customer customer) throws Exception {
		

		Customer cusCompare = cRepository.selectCustomer(customer.getCus_id());
		

		int result = 0;
		if (pwdEncoder.matches(customer.getCus_pw(), cusCompare.getCus_pw())) {
			result = 1;
			return result;

		} else {
			result = 0;
			return result;
		}

	}
	
	// 이메일인증
	@RequestMapping(value = "/emailView", method = RequestMethod.GET)
	public String email(Customer customer, Model model) {
		

		logger.info("이메일 뷰 페이지 이동 컨트롤러  !!! ");
		return "customer/email";
	}

//	
//	// 이메일 인증
//	@GetMapping("/mailCheck")
//	@ResponseBody
//	public String mailCheck(String email) {
//		System.out.println("이메일 인증 요청이 들어옴!");
//		System.out.println("이메일 인증 이메일 : " + email);
//		return joinEmail(email);
//	}
//	
//	@Autowired
//	private JavaMailSenderImpl mailSender;
//	private int authNumber; 
//	// 난수 발생(여러분들 맘대러)
//	
//		public void makeRandomNumber() {
//			// 난수의 범위 111111 ~ 999999 (6자리 난수)
//			Random r = new Random();
//			int checkNum = r.nextInt(888888) + 111111;
//			System.out.println("인증번호 : " + checkNum);
//			authNumber = checkNum;
//		}
//		
//		
//				//이메일 보낼 양식! 
//		public String joinEmail(String email) {
//			makeRandomNumber();
//			String setFrom = ".com"; // email-config에 설정한 자신의 이메일 주소를 입력 
//			String toMail = email;
//			String title = "회원 가입 인증 이메일 입니다."; // 이메일 제목 
//			String content = 
//					"홈페이지를 방문해주셔서 감사합니다." + 	//html 형식으로 작성 ! 
//	                "<br><br>" + 
//				    "인증 번호는 " + authNumber + "입니다." + 
//				    "<br>" + 
//				    "해당 인증번호를 인증번호 확인란에 기입하여 주세요."; //이메일 내용 삽입
//			mailSend(setFrom, toMail, title, content);
//			return Integer.toString(authNumber);
//		}
//		
//		//이메일 전송 메소드
//		public void mailSend(String setFrom, String toMail, String title, String content) { 
//			MimeMessage message = mailSender.createMimeMessage();
//			// true 매개값을 전달하면 multipart 형식의 메세지 전달이 가능.문자 인코딩 설정도 가능하다.
//			try {
//				MimeMessageHelper helper = new MimeMessageHelper(message,true,"utf-8");
//				helper.setFrom(setFrom);
//				helper.setTo(toMail);
//				helper.setSubject(title);
//				// true 전달 > html 형식으로 전송 , 작성하지 않으면 단순 텍스트로 전달.
//				helper.setText(content,true);
//				mailSender.send(message);
//			} catch (MessagingException e) {
//				e.printStackTrace();
//			}
//		}
//		
//	

	// 마지막 닫는괄호
}
