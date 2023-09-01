package service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import common.CRUD;
import common.Manager;
import common.MemberLog;
import common.SERVICE;
import dao.MemberDao;
import vo.Article;
import vo.Favorite;
import vo.Location;
import vo.Member;

public class MemberService extends SERVICE<Member> {
	
	
	public MemberService(Scanner sc, CRUD<Member> dao, Manager manager) {
		super(sc, dao, manager);
		
	}
	
	
	//회원가입
	public void join(Scanner sc) {
		try(MemberDao<Member> dao = (MemberDao<Member>) this.dao){
			System.out.println("회원 가입");
			Member m = new Member();
			String id = "";
			ArrayList<Member> list = new ArrayList<>();
			
			do {
				HashMap<String, String> map = new HashMap<String, String>();
				System.out.print("id:");
				id = sc.next();
				map.put("id", id);
				list = dao.select(map);
				if(list.size() != 0) {
					System.out.println("이미 사용중인 아이디입니다.");
				}
			}while(list.size() != 0);
			
			System.out.print("pwd:");
			String pwd = sc.next();
			String checkpwd = "";
			do {
				System.out.print("checkpwd:");
				checkpwd = sc.next();
				if(!pwd.equals(checkpwd)) {
					System.out.println("입력한 비밀번호가 맞지 않습니다.");
				}
			}while(!pwd.equals(checkpwd));
			
			System.out.print("name:");
			String name = sc.next();
			
			System.out.print("email:");
			String email = sc.next();
			
			System.out.println("[지역 선택]");
			ArrayList<Location> loclist = ((LocationService)this.manager.getService("LocationService")).searchAllLoc();
			((LocationService)this.manager.getService("LocationService")).printList(loclist);
			System.out.print("location:");
			int loc = sc.nextInt();
			
			System.out.println("[관심사 선택]");
			ArrayList<Favorite> favlist = ((FavoriteService)this.manager.getService("FavoriteService")).searchAllFav();
			((FavoriteService)this.manager.getService("FavoriteService")).printList(favlist);
			System.out.print("favorite:");
			int fav = sc.nextInt();
			
			String admin = "0";
			
			m = new Member(0, id, pwd, name, email, null, admin, loc, fav);
			
			dao.insert(m);
			System.out.println("회원 가입 완료");
			System.out.println(m);
		}catch(SQLException e) {
			System.out.println(e.getMessage());
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	//login
	public void login(Scanner sc) {
		try(MemberDao<Member> dao = (MemberDao<Member>) this.dao){
			String id = null;
			String pwd = null;
			System.out.print("id:");
			id = sc.next();
			System.out.print("pwd:");
			pwd = sc.next();
			HashMap<String, String> map = new HashMap<String, String>();
			
			map.put("id", id);
			map.put("pwd", pwd);
			if(id.equals(dao.select(map).get(0).getId()) ||
					pwd.equals(dao.select(map).get(0).getPwd())) {
				MemberLog.member = dao.select(map).get(0);
				System.out.println("로그인");
			}else {
				System.out.println("아이디와 비밀번호를 다시 확인해주세요");
				return;
			}
		}catch(SQLException e) {
			System.out.println(e.getMessage());
		}
	}
	
	//logout
	public void logout() {
		MemberLog.member = null;
		System.out.println("로그아웃");
	}
	
	//login check
	public Member nowMember() {
		try(MemberDao<Member> dao = (MemberDao<Member>) this.dao){
			if(MemberLog.member != null) {
				HashMap<String, String> map = new HashMap<String, String>();
	            map.put("id", MemberLog.member.getId());
	                   
	            ArrayList<Member> list = dao.select(map);
	            Member m = list.get(0);
	            return m;
			}
		}catch(SQLException e) {
			System.out.println(e.getMessage());
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
		return null;
	}
	
	
	//admin 권한 부여
	public void editAdm(Scanner sc) {
		try(MemberDao<Member> dao = (MemberDao<Member>) this.dao){
			Member m = nowMember();
			
			if(m != null) {
	            if(m.getAdmin().equals("1")) {
	               	System.out.println("admin 권한 부여");
	                   	
	              	System.out.println("관리자 권한을 부여할 회원의 회원 번호를 입력해주세요");
	               	System.out.print("No:");
	               	String no = sc.next();
	               	HashMap<String, String> editmap = new HashMap<String, String>();
	               	editmap.put("no", no);
	                 	
	               	ArrayList<Member> editlist = dao.select(editmap);
	               	printList(editlist);
	               	System.out.println("위 회원에게 관리자 권한을 부여하시겠습니까? (Y / N)");
	               	String answer = sc.next();
	               	if(answer.equals("Y") || answer.equals("y")) {
	               		Member editm = editlist.get(0);
	                  		
	               		editm.setAdmin("1");
	               		dao.updateAdm(editm);
	               	}else {
	               		System.out.println("관리자 권한 부여 작업을 취소합니다.");
	               		return;
	               	}
	               	
	            }else {
	               System.out.println("관리자 계정이 아닙니다.");
	               return;
	            }
			}
        }catch(SQLException e) {
			System.out.println(e.getMessage());
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	
	//ArrayList<Member> print
	public void printList(ArrayList<Member> list) {
		for(Member m : list) {
			System.out.println(m);
		}
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
		try(MemberDao<Member> dao = (MemberDao<Member>) this.dao){
			Member m = nowMember();
			if(m != null) {
	            HashMap<String, String> map = new HashMap<String, String>();
	            map.put("id", m.getId());
	                   
	            ArrayList<Member> list = dao.select(map);
	            printList(list);
			}
		}catch(SQLException e) {
			System.out.println(e.getMessage());
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	//내 정보 수정
	public void editInfo(Scanner sc) {
		try(MemberDao<Member> dao = (MemberDao<Member>) this.dao){
			Member m = nowMember();
			if(m != null) {
	            System.out.println("내 정보 수정");
	               
	            System.out.println("본인 확인을 위해 비밀번호를 입력해주세요");
	            System.out.print("pwd:");
	            String pwd = sc.next();
	 			if(pwd.equals(m.getPwd())) {
	   				System.out.print("new pwd:");
	   				m.setPwd(sc.next());
	   				System.out.print("new name:");
	   				m.setName(sc.next());
	   				
	   				dao.update(m);
	   				System.out.println("수정 완료");
	   				System.out.println(m);
	   			}else {
	       			System.out.println("비밀번호를 다시 확인해주세요");
	       			return;
	   			}
			}else {
				System.out.println("로그인 후에 이용할 수 있는 기능입니다.");
				return;
			}
		}catch(SQLException e) {
			System.out.println(e.getMessage());
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	//내 게시물 조회
	public void myArticle(Scanner sc) {
		try(MemberDao<Member> dao = (MemberDao<Member>) this.dao){
			Member m = nowMember();
			if(m != null) {
	            System.out.println("내 게시물 조회");
	            HashMap<String, String> amap = new HashMap<String, String>();
	            ArrayList<Article> alist = ((ArticleService)this.manager.getService("ArticleService")).searchByMemberNo(m.getNo());
	            ((ArticleService)this.manager.getService("ArticleService")).printAll(alist);
			}
		}catch(SQLException e) {
			System.out.println(e.getMessage());
		}
	}
	
	//meet 참가 내역 조회
	public void checkMeet(Scanner sc) {
		try(MemberDao<Member> dao = (MemberDao<Member>) this.dao) {
			Member m = nowMember();
			if(m != null) {
	            System.out.println("Meet 참가 내역 조회");
	            ((MeetService)this.manager.getService("MeetService")).menu(4, m.getNo());
			}   
		}catch(SQLException e) {
			System.out.println(e.getMessage());
		}
	}
	
	//좋아요 표시한 게시물 조회
	public void searchLike(Scanner sc) {
		try(MemberDao<Member> dao = (MemberDao<Member>) this.dao) {
			Member m = nowMember();
			if(m != null) {
	            System.out.println("좋아요 표시한 게시물 조회");
	            
	            
			}    
		}catch(SQLException e) {
			System.out.println(e.getMessage());
		}
	}
	
	/**
	 * 회원검색
	 *  - id로 검색
	 *  - 이름으로 검색
	 *  - 번호로 검색(관리자용)
	 *  - 전체 검색(관리자용)
	 */

	//회원 검색(by id)
	public void searchById(Scanner sc) {
		try(MemberDao<Member> dao = (MemberDao<Member>) this.dao){
			System.out.println("회원 id로 검색");
			System.out.print("id:");
			String id =sc.next();
			
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("id", id);
			
			ArrayList<Member> list = new ArrayList<>();
			list = ((MemberDao<Member>) dao).selectIdName(map);
			printList(list);
		}catch(SQLException e) {
			System.out.println(e.getMessage());
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	//회원 검색(by name)
	public void searchByName(Scanner sc) {
		try(MemberDao<Member> dao = (MemberDao<Member>) this.dao){
			System.out.println("회원 이름으로 검색");
			System.out.print("name:");
			String name =sc.next();
			
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("name", name);
			
			ArrayList<Member> list = new ArrayList<>();
			list = dao.selectIdName(map);
			printList(list);
		}catch(SQLException e) {
			System.out.println(e.getMessage());
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	//회원 검색(by no)
	public void searchByNum(Scanner sc) {
		try(MemberDao<Member> dao = (MemberDao<Member>) this.dao){
			Member m = nowMember();
			if(m.getId() != null) {
				HashMap<String, String> map = new HashMap<String, String>();
	            map.put("id", m.getId());
	                   
	            ArrayList<Member> list = dao.select(map);
	            
	            if(m.getAdmin().equals("1")) {
	             	System.out.println("회원 번호로 검색");
	        		System.out.print("No:");
	        		String no = sc.next();
	        		
	        		HashMap<String, String> nummap = new HashMap<String, String>();
	        		map.put("no", no);
	        		
	        		ArrayList<Member> numlist = dao.select(nummap);
	        		printList(numlist);
	            }else {
	               	System.out.println("관리자 계정이 아닙니다");
	               	return;
	            }
			}else {
				System.out.println("관리자 계정으로 로그인 후 이용 가능합니다");
				return;
			}
		}catch(SQLException e) {
			System.out.println(e.getMessage());
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	//전체 회원 검색
	public void searchAll() {
		try (MemberDao<Member> dao = (MemberDao<Member>) this.dao) {
			Member m = nowMember();
			if(m.getId() != null) {
	            if(m.getAdmin().equals("1")) {
	            	System.out.println("전체 회원 검색");
	    			
	    			ArrayList<Member> mlist = dao.select(null);
	    			printList(mlist);
	            }else {
	            	System.out.println("관리자 계정이 아닙니다");
	            	return;
	            }
			}else {
				System.out.println("관리자 계정으로 로그인 후 이용 가능합니다");
				return;
			}
		} catch(SQLException e) {
			System.out.println(e.getMessage());
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	
	//회원 탈퇴
	public void delMember(Scanner sc) {
		try(MemberDao<Member> dao = (MemberDao<Member>) this.dao){
			Member m = nowMember();
			if(m.getId() != null) {
				HashMap<String, String> map = new HashMap<String, String>();
		        map.put("id", m.getId());
		        
		        System.out.println("회원 탈퇴");
				
		        System.out.println("본인 확인을 위해 비밀번호를 입력해주세요");
		        System.out.print("pwd:");
		        String pwd = sc.next();
		        if(pwd.equals(m.getPwd())) {
		          	dao.delete(m.getNo());
		           	System.out.println("삭제 완료");
		           	return;
		        	}else {
		        		System.out.println("비밀번호를 다시 확인해주세요");
		        		return;
		        	}
				}else {
					System.out.println("로그인 후에 이용할 수 있는 기능입니다.");
					return;
				}
			}catch(SQLException e) {
				System.out.println(e.getMessage());
			}catch(Exception e) {
				System.out.println(e.getMessage());
			}
		}
		
}
