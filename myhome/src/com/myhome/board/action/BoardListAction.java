package com.myhome.board.action;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.myhome.dao.BoardDao;
import com.myhome.dto.BoardDto;

public class BoardListAction implements Action{
	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ArrayList<BoardDto> list = BoardDao.getInstance().getList(); // DB에서 list 정보를 받아온다.
		ActionForward actionForward = new ActionForward();
		request.setAttribute("list", list); // request영역으로 list 속성을 추가한다.
		actionForward.setNextPath("BoardListView.do"); // execute() 종료 후 BoardListView.do를 요청하겠다.
		actionForward.setRedirect(false);   // forward 하겠다.
		return actionForward; 
	}
}
