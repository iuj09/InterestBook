package service;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Scanner;

import dao.MemberDao;
import vo.Member;

public class MemberService {
	private MemberDao<Member> dao;
	
	public MemberService() {
		dao = new MemberDao<>();
	}
	
	//회원가입
	public void join(Scanner sc){
		System.out.println("회원 가입");
		try(MemberDao<Member> dao = this.dao){
			Member m = new Member();
			String id;
			
			do {
				System.out.print("id:");
				id = sc.next();
				if(dao.selectById(id) != null) {
					System.out.println("이미 사용중인 아이디입니다.");
				}
			}while(dao.selectById(id) == null);
			
			System.out.print("pwd:");
			String pwd = sc.next();
			String checkpwd;
			do {
				System.out.print("checkpwd:");
				checkpwd = sc.next();
				if(!pwd.equals(checkpwd)) {
					System.out.println("입력한 비밀번호가 맞지 않습니다.");
				}
			}while(pwd.equals(checkpwd));
			
			System.out.print("name:");
			String name = sc.next();
			System.out.print("email:");
			String email = sc.next();
			System.out.println("[지역 선택]");
			System.out.println("1.aaa 2.bbb 3.ccc");
			System.out.print("location:");
			int loc = sc.nextInt();
			System.out.println("[관심사 선택]");
			System.out.println("1.aaa 2.bbb 3.ccc");
			System.out.print("favorite:");
			int fav = sc.nextInt();
			
			m = new Member(0, id, pwd, name, email, null, loc, fav);
			
			dao.insert(m);
		} catch(SQLException e) {
			System.out.println(e.getMessage());
		}
		
	}
	
	
	//로그인
	public void login(Scanner sc) {
		
	}
	
	
	//로그아웃
	public void logout(Scanner sc) {
		
	}
	
	
	/**
	 * 마이페이지
	 *  - 내 정보 조회
	 *  - 내 정보 수정
	 *  - meet 참가 확인
	 *  - 좋아요 표시한 게시물 확인
	 */
	//내 정보 조회
	public void myInfo(Scanner sc) {
		try(MemberDao<Member> dao = this.dao){
			HashMap<String, String> map = new HashMap<String, String>();
			String id = null;
			map.put("id", id);
			dao.select(map);
		}catch(SQLException e) {
			System.out.println(e.getMessage());
		}
	}
	
	
	//내 정보 수정
	public void editInfo(Scanner sc) {
//		try(MemberDao<Member> dao = this.dao) {
			//////////////로그인 시
			
			System.out.println("내 정보 수정");
			System.out.println("본인 확인을 위해 비밀번호를 입력해주세요");
			System.out.print("pwd:");
			String pwd = sc.next();
//			if(pwd.equals(m.getPwd)) {
//				System.out.print("new pwd:");
//				m.setPwd(sc.next());
//				System.out.print("new name:");
//				m.setName(sc.next());
//				
//				dao.update(m);
//			}else {
//				System.out.println("비밀번호를 다시 확인해주세요");
//				return;
			}
//		}catch(SQLException e) {
//			System.out.println(e.getMessage());
//		}
//	}
	
	
	// meet 참가 확인
	public void checkMeet(Scanner sc) {
		try(MemberDao<Member> dao = this.dao) {
			////////////////// 로그인 시
			
			
		}catch(SQLException e) {
			System.out.println(e.getMessage());
		}
	}
	
	//좋아요 표시한 게시물 확인
	public void searchLike(Scanner sc) {
		try(MemberDao<Member> dao = this.dao) {
			////////////////// 로그인 시
			
			
		}catch(SQLException e) {
			System.out.println(e.getMessage());
		}
	}
	
	//회원 검색(by no)
	public void searchByNum(Scanner sc) {
		try(MemberDao<Member> dao = this.dao){
			HashMap<String, String> map = new HashMap<String, String>();
			String no = sc.next();
			map.put("no", no);
			dao.select(map);
		} catch(SQLException e) {
			System.out.println(e.getMessage());
		}
	}
	
	
	//전체 검색
	public void searchAll(Scanner sc) {
		try (MemberDao<Member> dao = this.dao) {
			dao.select(null);
		} catch(SQLException e) {
			System.out.println(e.getMessage());
		}
	}
	
	
	//회원 탈퇴
	public void delMember(Scanner sc) {
		try(MemberDao<Member> dao = this.dao){
			/////////////////로그인 시
			
			System.out.println("회원 탈퇴");
			
			System.out.println("본인 확인을 위해 비밀번호를 입력해주세요");
			System.out.print("pwd:");
			String pwd = sc.next();
			
			HashMap<String, String> map = new HashMap<String, String>();
//			String id = props.;
//			map.put("id", props.);
//			Member m = dao.select(map);
//			
//			if(pwd.equals(m.getPwd)) {
//				dao.delete(m.getNo);
//			}else {
//				System.out.println("비밀번호를 다시 확인해주세요");
//				return;
//			}
		} catch(SQLException e) {
			System.out.println(e.getMessage());
		}
	}
	
}
