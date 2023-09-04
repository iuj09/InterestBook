package service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import common.CRUD;
import common.Manager;
import common.SERVICE;
import dao.FavoriteDao;
import vo.Favorite;
import vo.Member;

public class FavoriteService extends SERVICE<Favorite>{

	public FavoriteService(Scanner sc, CRUD<Favorite> dao, Manager manager) {
		super(sc, dao, manager);
	}
	
	public void printList(ArrayList<Favorite> list) {
		for(Favorite f:list) {
			System.out.println(f);
		}
	}
	
	public void addFav(Scanner sc) {
		try(FavoriteDao<Favorite> dao = (FavoriteDao<Favorite>) this.dao){
			Member m = ((MemberService)this.manager.getService("MemberService")).nowMember();
			if(m != null) {
                if(m.getAdmin().equals("1")) {
                	System.out.println("관심사 추가");
                   	System.out.print("No:");
                   	int no = sc.nextInt();
                   	System.out.print("name:");
                   	String name = sc.next();
                   	
                   	Favorite f = new Favorite(no, name);
                    	
                   	dao.insert(f);
                   	System.out.println("추가 완료");
                   	System.out.println(f);
                }
            }
		}catch(SQLException e) {
			System.out.println(e.getMessage());
		}
	}
	
	public void searchFavByNum(Scanner sc) {
		try(FavoriteDao<Favorite> dao = (FavoriteDao<Favorite>) this.dao){
			Member m = ((MemberService)this.manager.getService("MemberService")).nowMember();
			if(m != null) {
                if(m.getAdmin().equals("1")) {
                   	System.out.println("조회할 관심사 번호 입력");
                   	System.out.print("No:");
                   	String no = sc.next();
                   	HashMap<String, String> fmap = new HashMap<String, String>();
                    	
                   	fmap.put("no", no);
                   	
                   	ArrayList<Favorite> favlist = dao.select(fmap);
                   	
                   	printList(favlist);
                   }
            }
		}catch(SQLException e) {
			System.out.println(e.getMessage());
		}
	}
	
	public ArrayList<Favorite> searchAllFav() {
		try(FavoriteDao<Favorite> dao = (FavoriteDao<Favorite>) this.dao){
          	ArrayList<Favorite> favlist = dao.select(null);
                   	
          	return favlist;
		} catch(SQLException e) {
			System.out.println(e.getMessage());
		}
		return null;
	}
	
	public void editFav(Scanner sc) {
		try(FavoriteDao<Favorite> dao = (FavoriteDao<Favorite>) this.dao){
			Member m = ((MemberService)this.manager.getService("MemberService")).nowMember();
			if(m != null) {
                if(m.getAdmin().equals("1")) {
                  	System.out.println("관심사 수정");
                   	System.out.print("No:");
                   	String no = sc.next();
                   	
                   	HashMap<String, String> fmap = new HashMap<String, String>();
                   	
                   	fmap.put("no", no);
                   	
                   	Favorite f = dao.select(fmap).get(0);
                   	f.setName(sc.next());
                   	
                   	dao.update(f);
                   	System.out.println("수정 완료");
                   	System.out.println(f);
                   	
                }
            }
		}catch(SQLException e) {
			System.out.println(e.getMessage());
		}
	}
	
	public void delFav(Scanner sc) {
		try(FavoriteDao<Favorite> dao = (FavoriteDao<Favorite>) this.dao){
			Member m = ((MemberService)this.manager.getService("MemberService")).nowMember();
			if(m != null) {
                if(m.getAdmin().equals("1")) {
                   	System.out.println("관심사 삭제");
                   	System.out.print("No:");
                   	String no = sc.next();
                   	
                   	HashMap<String, String> fmap = new HashMap<String, String>();
                   	
                   	fmap.put("no", no);
                   	
                   	Favorite f = dao.select(fmap).get(0);
                   	dao.delete(f.getNo());
                   	System.out.println("삭제 완료");
                }
            }
		}catch(SQLException e) {
			System.out.println(e.getMessage());
		}
		
	}

}
