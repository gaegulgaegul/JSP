package com.myhome.board;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.myhome.board.action.Action;
import com.myhome.board.action.ActionForward;
import com.myhome.board.action.BoardListAction;
import com.myhome.board.action.BoardWriteAction;

@WebServlet("*.do")
public class Controller extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// URI에서 사용자가 요청한 페이지 추출
		String requestURI = request.getRequestURI();  // 요청된 URI(주소) : "/myhome/board/BoardList.do"
		int lstIndex = requestURI.lastIndexOf("/") + 1; // 13 + 1 --> 14
		String requestPage = requestURI.substring(lstIndex);  // "BoardList.do"
		
		System.out.println("requestURI : " + requestURI );  // "/myhome/board/BoardList.do"
		System.out.println("requestPage : " + requestPage); // "BoardList.do"
		
		Action action = null;
		ActionForward actionForward = null;
		try {
			if(requestPage.equals("BoardList.do")) {
				action = new BoardListAction();
				actionForward = action.execute(request, response);
				// nextPath : "BoardListView.do"
				// isRedirect : false
			}
			else if(requestPage.equals("BoardListView.do")) {
				actionForward = new ActionForward();
				actionForward.setNextPath("boardListView.jsp");
				actionForward.setRedirect(false);
				// nextPath : "boardListView.jsp"
				// isRedirect : false
			}
			else if(requestPage.equals("BoardWriteForm.do")) {
				actionForward = new ActionForward();
				actionForward.setNextPath("boardWriteView.jsp");
				actionForward.setRedirect(false);
				// nextPath : "boardWriteView.jsp"
				// isRedirect : false
			}
			else if(requestPage.equals("BoardWrite.do")) {
				action = new BoardWriteAction();
				actionForward = action.execute(request, response);
				// nextPath : "Result.do"
				// isRedirect : false
			}
			else if(requestPage.equals("Result.do")) {
				actionForward = new ActionForward();
				actionForward.setNextPath("boardResultView.jsp");
				actionForward.setRedirect(false);
				// nextPath : "boardResultView.jsp"
				// isRedirect : false 
				// (글쓰기 이후 request 바구니 : state - "write" / result - true/false)
			}
			
			if(actionForward != null) {
				if(actionForward.isRedirect()) { // 리다이렉트방식으로 nextPath 이동
					response.sendRedirect(actionForward.getNextPath()); // nextPath로 redirect
				} else { // 포워드 방식으로 nextPath로 이동
					request.getRequestDispatcher(actionForward.getNextPath()).forward(request , response); // nextPath로 forward
				}
			}
			
		} catch (Exception e){
			e.printStackTrace();
		} 
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
