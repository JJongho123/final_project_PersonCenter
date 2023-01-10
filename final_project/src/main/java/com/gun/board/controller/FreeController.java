package com.gun.board.controller;

import java.io.FileInputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.gun.board.repository.FreeRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gun.board.repository.CustomerRepository;
import com.gun.board.repository.FriendRepository;
import com.gun.board.repository.ReplyFreeRepository;
import com.gun.board.util.Configuration;
import com.gun.board.util.FileService;
import com.gun.board.util.Pagination_Free;
import com.gun.board.vo.Customer;
import com.gun.board.vo.Free;
import com.gun.board.vo.Notice;
import com.gun.board.vo.Reply_Free;

@RequestMapping(value = "/boards_free")
@Controller
public class FreeController {

   private static final Logger logger = LoggerFactory.getLogger(FreeController.class);

   @Inject
   HttpSession session;

   @Inject
   FreeRepository frRepository;

   @Inject
   ReplyFreeRepository rRepository;

   @Inject
   CustomerRepository cRepository;

   @Inject
   FriendRepository fRepository;

   Pagination_Free Paginationfr = new Pagination_Free();
    
   @RequestMapping(value = "", method = RequestMethod.GET)
   public String getFree(Model model, @RequestParam(value = "page", defaultValue = "1") int page,
         @RequestParam(value = "friend_id", defaultValue = "") String friend_id) {
	   logger.info("게시판 홈");
      String cus_id = (String) session.getAttribute("loginid");
      if (friend_id.equals("") || friend_id.equals(cus_id)) {
         friend_id = cus_id;
         session.setAttribute("status", "myself");
      } else {
         String status = fRepository.getStatus(cus_id, friend_id);
         if (status == null || !status.equals("friend")) {
            session.setAttribute("status", "notYet");
         } else if (status.equals("friend")) {
            session.setAttribute("status", "friend");
         }
      }
      
      ArrayList<Free> boards_free = frRepository.getFree(friend_id);
      int totalPages = Paginationfr.totalPages(boards_free);
      page = Paginationfr.getCurrentPage(page, totalPages);
      boards_free = Paginationfr.totalPosts(boards_free, page);
      int endPage = Paginationfr.endPage(page, totalPages);
      logger.info("총 페이지: " + totalPages + ", 끝페이지 : " + endPage + "현재페이지 :  " + page + "게시물 수 : " + boards_free.size()
            + " status: " + (String) session.getAttribute("status"));
      model.addAttribute("free", boards_free);
      model.addAttribute("page", page);
      model.addAttribute("endPage", endPage);
      model.addAttribute("friend_id", friend_id);
      System.out.println("friend_id =" + friend_id);
      return "boards_free/free";
   }

   @RequestMapping(value = "/insert", method = RequestMethod.GET)
   public String insertBoards_free() {
	  logger.info("글 작성 화면으로 이동");
      return "boards_free/insert";
   }

   @RequestMapping(value = "/insert", method = RequestMethod.POST)
   public String insertBoards_free(Free free, MultipartFile upload, Model model) {
      String board_id = (String) session.getAttribute("loginid");
      String board_nickname = cRepository.selectNickname(board_id);
      free.setBoard_id(board_id);
      free.setBoard_nickname(board_nickname);
      frRepository.insertBoards_free(free);
      
   // 회원등급제를 위한 메서드 추가
		
   		cRepository.countUp(board_id);
   		
   		int countCheck = cRepository.checkCount(board_id);
   		
   		if(countCheck > 0 && countCheck <= 2) {
   			int cus_count = 1;
   			cRepository.GradeUpdate(board_id,cus_count);
   		}
   		else if(countCheck > 2 && countCheck <= 4) {
   			int cus_count = 2;
   			cRepository.GradeUpdate(board_id,cus_count);
   		}
   		else if(countCheck > 4 && countCheck <= 6) {
   			int cus_count = 3;
   			cRepository.GradeUpdate(board_id,cus_count);
   		}
   		else {
   			int cus_count = 4;
   			cRepository.GradeUpdate(board_id,cus_count);
   		}
   		
   		// 회원등급제를 위한 메서드 추가 끝
   		
   		
      if (!upload.isEmpty()) {
         System.out.println("upload file's name @@@: " + upload.getOriginalFilename());
         String board_uploadfileid = FileService.saveFile(upload, Configuration.PHOTOPATH);
         free.setBoard_fileid(upload.getOriginalFilename());
         free.setBoard_uploadfileid(board_uploadfileid);
         frRepository.insertPhoto_free(free);
      }
      String friend_id = (String) session.getAttribute("loginid");
      int page = 1;
      ArrayList<Free> boards_free = frRepository.getFree(friend_id);
      int totalPages = Paginationfr.totalPages(boards_free);
      page = Paginationfr.getCurrentPage(page, totalPages);
      boards_free = Paginationfr.totalPosts(boards_free, page);
      int endPage = Paginationfr.endPage(page, totalPages);
      model.addAttribute("free", boards_free);
      model.addAttribute("page", page);
      model.addAttribute("endPage", endPage);
      model.addAttribute("friend_id", friend_id);
      return "boards_free/free";
   }

   @RequestMapping(value = "/get", method = RequestMethod.GET)
   public String getBoards_free(int board_num, Model model, int page, String friend_id) {
      String loginid = (String) session.getAttribute("loginid");
      Free free = frRepository.getBoards_free(board_num);
      if (!loginid.equals(free.getBoard_id())) {
     
         frRepository.upHits_free(board_num);
         free = frRepository.getBoards_free(board_num);
      }
  
      model.addAttribute("free", free);
      model.addAttribute("page", page);
      model.addAttribute("friend_id", friend_id);
     

 
      ArrayList<Reply_Free> reply = rRepository.getReplies(board_num);
      model.addAttribute("reply", reply);
      return "boards_free/get";
   }
      
   @RequestMapping(value = "/download", method = RequestMethod.GET)
   public String download(int board_num, HttpServletResponse response) {

      Free free = frRepository.getBoards_free(board_num);

      String originalfile = free.getBoard_fileid();

      try {
         response.setHeader("Content-Disposition",
               "attachment;filename=" + URLEncoder.encode(originalfile, "UTF-8"));
      } catch (UnsupportedEncodingException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }

      String fullpath = Configuration.PHOTOPATH + "/" + free.getBoard_uploadfileid();

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

   @RequestMapping(value = "/insertReply", method = RequestMethod.POST)
   public @ResponseBody int insertReply(int board_num, String reply_content,
         @RequestParam(value = "reply_num", defaultValue = "-1") int reply_num) {
      String reply_Id = (String) session.getAttribute("loginid");
      String reply_Nickname = (String) session.getAttribute("loginNickname");
      Reply_Free newReply = new Reply_Free();
      newReply.setBoard_num(board_num);
      newReply.setReply_content(reply_content);
      Reply_Free reply = rRepository.getReply(reply_num);
      int result = 0;
      if (reply_num == -1) {
    
         newReply.setReply_id(reply_Id);
         newReply.setReply_nickname(reply_Nickname);
      } else {
    
         newReply.setReply_id(reply.getReply_id());
         newReply.setReply_nickname(reply.getReply_nickname());
         newReply.setRreply_id(reply_Id);
         newReply.setRreply_nickname(reply_Nickname);
         newReply.setRreply_num(reply_num);
      }
      result = rRepository.insertReply(newReply);
 
      if (reply_num == -1) {
         reply_num = rRepository.recentlyAddedReplynum();
        
         rRepository.updateRReply_num(reply_num);
      }
 
      frRepository.changeReply_free(board_num, 0);
    
      return board_num;
   }

   @RequestMapping(value = "/deleteReply", method = RequestMethod.POST)
   public @ResponseBody int deleteReply(int reply_num, int board_num) {
      int result = rRepository.deleteReply(reply_num);
     
      frRepository.changeReply_free(board_num, 1);
 
      return board_num;
   }

   @RequestMapping(value = "/updateReply", method = RequestMethod.POST)
   public @ResponseBody int updateReply(int reply_num, String reply_content, int board_num) {
	   Reply_Free reply = rRepository.getReply(reply_num);
      reply.setReply_content(reply_content);
      int result = rRepository.updateReply(reply);
   
      return board_num;
   }

   @RequestMapping(value = "/delete", method = RequestMethod.POST)
   public @ResponseBody int deleteBoards_free(Model model, int board_num) {
      Free free = frRepository.getBoards_free(board_num);
      boolean fileDeleteResult = false;
      if (free.getBoard_uploadfileid() != null) {
         fileDeleteResult = FileService.deleteFile(Configuration.PHOTOPATH + "/" + free.getBoard_uploadfileid());
      }
      int result = frRepository.deleteBoards_free(board_num);
    
      return result;
   }

   @RequestMapping(value = "/update", method = RequestMethod.GET)
   public String updateBoard(int board_num, Model model, int page, String friend_id) {
      Free free = frRepository.getBoards_free(board_num);
      String loginid = (String) session.getAttribute("loginid");
      if (free.getBoard_fileid() != null) {
         FileService.deleteFile(free.getBoard_uploadfileid());
      }
      model.addAttribute("page", page);
      model.addAttribute("friend_id", friend_id);
      if (free.getBoard_id().equals(loginid)) {
  
         model.addAttribute("free", free);
         return "boards_free/update";
      }
      return "boards_free/free";
   }

   @RequestMapping(value = "/update", method = RequestMethod.POST)
   public String updateBoard(int board_num, Free free, int page, String friend_id, Model model,
         MultipartFile upload) {
      Free originalBoard = frRepository.getBoards_free(board_num);
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
         free.setBoard_fileid(board_fileid);
         free.setBoard_uploadfileid(board_uploadfileid);
      }
      int result = frRepository.updateBoards_free(free);
     
      ArrayList<Free> boards_free = frRepository.getFree(friend_id);
      int totalPages = Paginationfr.totalPages(boards_free);
      page = Paginationfr.getCurrentPage(page, totalPages);
      boards_free = Paginationfr.totalPosts(boards_free, page);
      int endPage = Paginationfr.endPage(page, totalPages);
      model.addAttribute("free", free);
      model.addAttribute("page", page);
      model.addAttribute("endPage", endPage);
      model.addAttribute("friend_id", friend_id);
      return "boards_free/free";
   }
   
   @RequestMapping(value = "home", method = RequestMethod.GET)
   public String gethome(Model model, @RequestParam(value = "page", defaultValue = "1") int page,
         @RequestParam(value = "friend_id", defaultValue = "") String friend_id) {
   
      String cus_id = (String) session.getAttribute("loginid");
      if (friend_id.equals("") || friend_id.equals(cus_id)) {
         friend_id = cus_id;
         session.setAttribute("status", "myself");
      } else {
         String status = fRepository.getStatus(cus_id, friend_id);
         if (status == null || !status.equals("friend")) {
            session.setAttribute("status", "notYet");
         } else if (status.equals("friend")) {
            session.setAttribute("status", "friend");
         }
      }
      
      ArrayList<Free> free = frRepository.getFree(friend_id);
      int totalPages = Paginationfr.totalPages(free);
      page = Paginationfr.getCurrentPage(page, totalPages);
      free = Paginationfr.totalPosts(free, page);
      int endPage = Paginationfr.endPage(page, totalPages);
 
      model.addAttribute("free", free);
      model.addAttribute("page", page);
      model.addAttribute("endPage", endPage);
      model.addAttribute("friend_id", friend_id);
      System.out.println("friend_id =" + friend_id);
      return "boards_free/free";
   }
   
   @RequestMapping(value = "/findfree", method = RequestMethod.POST)
   public String findfree(Model model, @RequestParam(value = "searchType", defaultValue = "" ) String searchType,
         @RequestParam(value = "searchContent", defaultValue = "") String searchContent) {
      
      int page = 1;
      int result = 0;
      ArrayList<Free> searchFree = new ArrayList();
      searchFree = frRepository.findfree(searchType, searchContent);
      int totalPages = Paginationfr.totalPages(searchFree);
      page = Paginationfr.getCurrentPage(page, totalPages);
      searchFree = Paginationfr.totalPosts(searchFree, page);
      int endPage = Paginationfr.endPage(page, totalPages);
      
      logger.info("searchType: " + searchType + ", searchContent: " + searchContent + " , " + searchFree.size());
      System.out.println("searchType: " + searchType + ", searchContent: " + searchContent + " , " + searchFree.size());
      System.out.println("searchFree : " + searchFree);
      
      model.addAttribute("free", searchFree);
      model.addAttribute("page", page);
      model.addAttribute("endPage", endPage);
      
      result = searchFree.size();
      return "boards_free/free";
   }
   
   // �옄�쑀寃뚯떆�뙋 �젙�젹
   @RequestMapping(value ="/SortFree", method = RequestMethod.GET)
   public  String SortFree(String sortValue, Model model) {   
 
      ArrayList<Free> free = frRepository.getSort(sortValue);
      
      int page = 1;
     
     int totalPages = Paginationfr.totalPages(free);
     page = Paginationfr.getCurrentPage(page, totalPages);
     free = Paginationfr.totalPosts(free, page);
     int endPage = Paginationfr.endPage(page, totalPages);
     
     model.addAttribute("page", page);
     model.addAttribute("endPage", endPage);
     model.addAttribute("free", free);
     
        return "boards_free/free";
   }
}