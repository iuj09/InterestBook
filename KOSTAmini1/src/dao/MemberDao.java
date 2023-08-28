package dao;

import java.util.ArrayList;

import vo.Member;

public class MemberDao {

	
	public  MemberDao() {
		
	}
	
	//회원 가입
	public void insert(Member m) {
		
	}
	
	
	/**
	 * 마이페이지
	 * 	- 내 정보 조회
	 *  - 내 정보 수정
	 *  - meet 참가 확인
	 *  - 좋아요 표시한 게시물 확인
	 */
	//내 정보 조회
	public Member selectMyInfo(int num) {
		Member m = null;
		
		return m;
	}
	
	
	//내 정보 수정
	public void update(Member m) {
		
	}
	
	
	//meet 참가 확인
//	public ArrayList<Meet> select(int num) {
//		ArrayList<Meet> list = new ArrayList<>();
//		
//		return list;
//	}
	

	//좋아요 표시한 게시물 확인
//	public ArrayList<Article> select(int num){
//		ArrayList<Article> list = new ArrayList<>();
//		
//		return list;
//	}
	
	//회원 검색(select id, name, locNum, favNum where num(관리자용))
	public Member selectByNum(int num) {
		Member m = null;
		
		return m;
	}
	
	
	//회원 검색(select id, name, locNum, favNum where id)
	public Member selectById(String id) {
		Member m = null;
		
		return m;
	}
	
	
	//회원 검색(select id, name, locNum, favNum where name(중복 허용))
	public ArrayList<Member> selectByName(String name){
		ArrayList<Member> list = new ArrayList<>();
		
		return list;
	}
	
	
	//회원 탈퇴
	public void delete(int num) {
		
	}
	
	
	
}
