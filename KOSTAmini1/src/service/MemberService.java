package service;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;
import java.util.Scanner;

import common.CRUD;
import common.Info;
import common.SERVICE;
import dao.MemberDao;
import vo.Member;

public class MemberService extends SERVICE<Member> {
	
	public MemberService(Scanner sc, CRUD<Member> dao) {
		super(sc, dao);
	}
	
	//회원가입
	public void join(Scanner sc){
		System.out.println("회원 가입");
		try(MemberDao<Member> dao = (MemberDao<Member>) this.dao){
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
			System.out.println("1.aaa 2.bbb 3.ccc");
			System.out.print("location:");
			int loc = sc.nextInt();
			System.out.println("[관심사 선택]");
			System.out.println("1.aaa 2.bbb 3.ccc");
			System.out.print("favorite:");
			int fav = sc.nextInt();
			
			m = new Member(10, id, pwd, name, email, null, loc, fav);
			
			dao.insert(m);
		} catch(SQLException e) {
			System.out.println(e.getMessage());
		}
		
	}
	
	//ArrayList print
	public void printList(ArrayList<Member> list) {
		for(Member m : list) {
			System.out.println(m);
		}
	}
	
	public void printStList(ArrayList<String> list) {
		for(String s : list) {
			System.out.println(s);
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
		try(MemberDao<Member> dao = this.dao){
			HashMap<String, String> map = new HashMap<String, String>();
			String id = "aaa";
			map.put("id", id);
			ArrayList<Member> list = dao.select(map);
			System.out.println(list);
		}catch(SQLException e) {
			System.out.println(e.getMessage());
		}
	}
	
	
	//내 정보 수정
	public void editInfo(Scanner sc) {
		try(MemberDao<Member> dao = this.dao) {
			//////////////로그인 시
//			if(Info.log()) {
//				Properties prop;
//				prop = new Properties();
//                prop.load(new FileReader("C:\\Users\\KOSTA\\dogi\\prop.properties"));
//                for (Object key : prop.keySet()) {
//                    String k = (String) key;
                HashMap<String, String> map = new HashMap<String, String>();
				String k = "aaa";
    			map.put("id", k);
                Member m = dao.select(map).get(0);
                
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
//                }
//			}else {
//				System.out.println("로그인 후에 이용할 수 있는 기능입니다.");
//				return;
//			}
		}catch(SQLException e) {
			System.out.println(e.getMessage());
//		} catch (FileNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}
	}
	
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
			System.out.println("회원 번호로 검색");
			System.out.print("no:");
			String no = sc.next();
			map.put("no", no);
			ArrayList<Member> list = dao.select(map);
			printList(list);
		} catch(SQLException e) {
			System.out.println(e.getMessage());
		}
	}
	
	
	//전체 검색
	public void searchAll() {
		try (MemberDao<Member> dao = this.dao) {
			System.out.println("전체 회원 검색");
			ArrayList<Member> list = dao.select(null);
			printList(list);
		} catch(SQLException e) {
			System.out.println(e.getMessage());
		}
	}
	
	//회원 검색(by id)
	public void searchById(Scanner sc) {
		try(MemberDao<Member> dao = this.dao){
			System.out.println("회원 id로 검색");
			System.out.print("id:");
			String id =sc.next();
			
			System.out.println(dao.selectById(id));
		}catch(SQLException e) {
			System.out.println(e.getMessage());
		}
	}
	
	//회원 검색(by name)
	public void searchByName(Scanner sc) {
		try(MemberDao<Member> dao = this.dao){
			System.out.println("회원 이름으로 검색");
			System.out.print("name:");
			String name =sc.next();
			
			ArrayList<String> list = new ArrayList<>();
			list = dao.selectByName(name);
			printStList(list);
		}catch(SQLException e) {
			System.out.println(e.getMessage());
		}	
	}
	
	
	//회원 탈퇴
	public void delMember(Scanner sc) {
		try(MemberDao<Member> dao = this.dao){
			/////////////////로그인 시
//			if(Info.log()) {
//				Properties prop;
//				prop = new Properties();
//                prop.load(new FileReader("C:\\Users\\KOSTA\\dogi\\prop.properties"));
//                for (Object key : prop.keySet()) {
                    String k = "aaa";
                    HashMap<String, String> map = new HashMap<String, String>();
                    map.put("id", k);
                    Member m = dao.select(map).get(0);
			
                    System.out.println("회원 탈퇴");
			
                    System.out.println("본인 확인을 위해 비밀번호를 입력해주세요");
                    System.out.print("pwd:");
                    String pwd = sc.next();
                    if(pwd.equals(m.getPwd())) {
                    	dao.delete(m.getNo());
                    	System.out.println("삭제 완료");
                    }else {
                    	System.out.println("비밀번호를 다시 확인해주세요");
                    	return;
                    }
//                }
//			}else {
//				System.out.println("로그인 후에 이용할 수 있는 기능입니다.");
//				return;
//			}
		} catch(SQLException e) {
			System.out.println(e.getMessage());
//		} catch (FileNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}
	}	
}
