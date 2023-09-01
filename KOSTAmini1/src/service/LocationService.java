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
import common.MemberLog;
import common.SERVICE;
import dao.LocationDao;
import dao.MemberDao;
import vo.Location;
import vo.Member;

public class LocationService extends SERVICE<Location>{
	public LocationService(Scanner sc, CRUD<Location> dao) {
		super(sc, dao);
	}

	public void printList(ArrayList<Location> list) {
		for(Location l:list) {
			System.out.println(l);
		}
	}
	
	public void addLoc(Scanner sc) {
		try(LocationDao<Location> dao = (LocationDao<Location>) this.dao){
			Member m = ((MemberService<Member>)service).nowMember();
			if(m != null) {
                if(m.getAdmin().equals("1")) {
                   	System.out.println("지역 추가");
                   	System.out.print("No:");
                   	int no = sc.nextInt();
                   	System.out.print("name:");
                   	String name = sc.next();
                   	
                   	Location l = new Location(no, name);
                   	
                   	dao.insert(l);
                   	System.out.println("추가 완료");
                   	System.out.println(l);
                   }
			}
		}catch(SQLException e) {
			System.out.println(e.getMessage());
		}
	}
	
	public void searchLocByNum(Scanner sc) {
		try(LocationDao<Location> dao = (LocationDao<Location>) this.dao){
			Member m = ((MemberService<Member>)service).nowMember();
			if(m != null) {
               if(m.getAdmin().equals("1")) {
            	   System.out.println("조회할 지역 번호 입력");
                   System.out.print("No:");
                   String no = sc.next();
                   HashMap<String, String> lmap = new HashMap<String, String>();
                  	
                   lmap.put("no", no);
                   	
                   ArrayList<Location> loclist = dao.select(lmap);
                   	
                   printList(loclist);
                }
            }
		}catch(SQLException e) {
			System.out.println(e.getMessage());
		}
	}
	
	public void searchAllLoc() {
		try(LocationDao<Location> dao = (LocationDao<Location>) this.dao){
           	ArrayList<Location> loclist = dao.select(null);
          	printList(loclist);
		} catch(SQLException e) {
			System.out.println(e.getMessage());
		}
	}
	
	public void editLoc(Scanner sc) {
		try(LocationDao<Location> dao = (LocationDao<Location>) this.dao){
			Member m = ((MemberService<Member>)service).nowMember();
			if(m != null) {
                if(m.getAdmin().equals("1")) {
                   	System.out.println("지역 수정");
                   	System.out.print("No:");
                   	String no = sc.next();
                   	
                  	HashMap<String, String> lmap = new HashMap<String, String>();
                   	
                   	lmap.put("no", no);
                   	
                  	Location l = dao.select(lmap).get(0);
                   	l.setName(sc.next());
                   	
                   	dao.update(l);
                   	System.out.println("수정 완료");
                   	System.out.println(l);
                   	
                }
            }
		}catch(SQLException e) {
			System.out.println(e.getMessage());
		}
	}
	
	public void delLoc(Scanner sc) {
		try(LocationDao<Location> dao = (LocationDao<Location>) this.dao){
			Member m = ((MemberService<Member>)service).nowMember();
			if(m != null) {
                if(m.getAdmin().equals("1")) {
                   	System.out.println("지역 삭제");
                   	System.out.print("No:");
                   	String no = sc.next();
                   	
                   	HashMap<String, String> lmap = new HashMap<String, String>();
                   	
                   	lmap.put("no", no);
                   	
                   	Location l = dao.select(lmap).get(0);
                   	dao.delete(l.getNo());
                   	System.out.println("삭제 완료");
                }
            }
		}catch(SQLException e) {
			System.out.println(e.getMessage());
		}
	}
}
