package com.myhome.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.myhome.dto.BoardDto;

public class BoardDao {
	private static BoardDao dao;
	private Connection con;
	private PreparedStatement ps;
	private ResultSet rs;
	private String sql;
	private static DataSource ds; 
	static { 
		try {
			System.out.println("start DBCP!");
			Context context = new InitialContext(); 
			ds = (DataSource) context.lookup("java:comp/env/jdbc/oracle");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	
	// �̱��� ����
	private BoardDao() {}
	public static BoardDao getInstance() {
		if(dao == null) {
			dao = new BoardDao();
		} 
		return dao;
	}
	
	// close�ϴ� �޼ҵ�1
	private static void close(Connection con, PreparedStatement ps, ResultSet rs) {
		try {
			if(rs != null) {
				rs.close();
			}
			if(ps != null) {
				ps.close();
			}
			if(con != null) { 
				con.close(); 
			} 
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	// close�ϴ� �޼ҵ�2 (�����ε�)
	private static void close(Connection con, PreparedStatement ps) {
		close(con, ps, null);
	}
	
	// �Խñ� �߰� �޼ҵ�
	public boolean insert(BoardDto dto) {
		boolean result = false;
		sql = "INSERT INTO board VALUES( board_seq.NEXTVAL, ?, ?, ?, ?, 0, SYSDATE)";
														// ������, �ۺ���, �ۼ���ID, �ۼ���NICK
		try {
			con = ds.getConnection();
			ps = con.prepareStatement(sql); 
			ps.setString(1, dto.getTitle());
			ps.setString(2, dto.getContent());
			ps.setString(3, dto.getWriter());
			ps.setString(4, dto.getNickname());
			result = ps.executeUpdate() == 1;
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			close(con, ps);
		}
		return result; 
	}
	
	// �Խñ� ��� �޼ҵ� 
	public ArrayList<BoardDto> getList(){
		ArrayList<BoardDto> list = new ArrayList<BoardDto>();
		BoardDto dto = null;
		sql = "SELECT num, title, content, nickname, hit, regdate FROM board ORDER BY regdate DESC"; // ������ �ۼ��� ID�� ������ ��� ���� ��ȸ
		try {
			con = ds.getConnection();
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			while(rs.next()) {
				dto = new BoardDto();
				dto.setNum( rs.getInt("num" )); // �Խñ� ��ȣ
				dto.setTitle( rs.getString("title" )); // �� ����
				dto.setNickname( rs.getString("nickname")); // �ۼ��ڴг���
				dto.setHit( rs.getInt("hit")); // ��ȸ��
				dto.setRegdate( rs.getString("regdate")); // �������
				list.add(dto);
			}
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			close(con, ps, rs);
		}
		return list.isEmpty() ? null : list;
	}
	
	// �Խñ� ���� ��ȸ �޼ҵ� 
	public BoardDto select(int num){
		BoardDto dto = null;
		sql = "SELECT * FROM board WHERE num = ?"; 
		try {
			con = ds.getConnection();
			ps = con.prepareStatement(sql);
			ps.setInt(1, num);
			rs = ps.executeQuery();
			if(rs.next()) {
				dto = new BoardDto();
				dto.setNum( rs.getInt("num" )); // �Խñ� ��ȣ
				dto.setTitle( rs.getString("title" )); // �� ����
				dto.setContent( rs.getString("content")); // �� ����
				dto.setWriter( rs.getString("writer")); // �ۼ���ID
				dto.setNickname( rs.getString("nickname")); // �ۼ��ڴг���
				dto.setHit( rs.getInt("hit")); // ��ȸ��
				dto.setRegdate( rs.getString("regdate")); // �������
			}
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			close(con, ps, rs);
		}
		return dto;
	}
	
	// �Խñ� ���� �޼ҵ�
	public boolean update(int num, String newTitle, String newContent) {
		boolean result = false;
		sql = "UPDATE board SET title = ?, content = ? WHERE num = ?";
		try {
			con = ds.getConnection();
			ps = con.prepareStatement(sql);
			ps.setString(1, newTitle);
			ps.setString(2, newContent);
			ps.setInt(3, num);
			result = ps.executeUpdate() == 1;
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			close(con, ps, rs);
		}
		return result;
	}
	
	// �Խñ� ���� �޼ҵ�
	public boolean delete(int num) {
		boolean result = false;
		sql = "DELETE FROM board WHERE num = ?";
		try {
			con = ds.getConnection();
			ps = con.prepareStatement(sql);
			ps.setInt(1, num);
			result = ps.executeUpdate() == 1;
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			close(con, ps, rs);
		}
		return result;
	}
	
	// ��ȸ�� 1 ���� �޼ҵ�
	public boolean updateHit(int num) {
		boolean result = false;
		sql = "UPDATE board SET hit = hit + 1 WHERE num = ?";
		try {
			con = ds.getConnection();
			ps = con.prepareStatement(sql);
			ps.setInt(1, num);
			result = ps.executeUpdate() == 1;
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			close(con, ps, rs);
		}
		return result;
	}
}
