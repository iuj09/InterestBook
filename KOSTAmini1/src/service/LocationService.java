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
import dao.LocationDao;
import dao.MemberDao;
import vo.Location;
import vo.Member;

public class LocationService extends SERVICE<Location>{
	private MemberDao mdao;
	
	public LocationService(Scanner sc, CRUD<Location> dao) {
		super(sc, dao);
		mdao = new MemberDao();
	}

	public void printList(ArrayList<Location> list) {
		for(Location l:list) {
			System.out.println(l);
		}
	}
	
	public void addLoc(Scanner sc) {
		try(LocationDao<Location> dao = (LocationDao<Location>) this.dao){
			if(Info.log()) {
				Properties prop;
				prop = new Properties();
                prop.load(new FileReader("C:\\Users\\KOSTA\\dogi\\prop.properties"));
                for (Object key : prop.keySet()) {
                    String k = (String) key;
                    HashMap<String, String> map = new HashMap<String, String>();
                    map.put("id", prop.getProperty(k));
                    
                    ArrayList<Member> list = mdao.select(map);
                    Member m = list.get(0);
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
			}
		}catch(SQLException e) {
			System.out.println(e.getMessage());
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void searchLocByNo(Scanner sc) {
		try(LocationDao<Location> dao = (LocationDao<Location>) this.dao){
			if(Info.log()) {
				Properties prop;
				prop = new Properties();
                prop.load(new FileReader("C:\\Users\\KOSTA\\dogi\\prop.properties"));
                for (Object key : prop.keySet()) {
                    String k = (String) key;
                    HashMap<String, String> map = new HashMap<String, String>();
                    map.put("id", prop.getProperty(k));
                    
                    ArrayList<Member> list = mdao.select(map);
                    Member m = list.get(0);
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
			}
		}catch(SQLException e) {
			System.out.println(e.getMessage());
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void searchAllLoc() {
		try(LocationDao<Location> dao = (LocationDao<Location>) this.dao){
			if(Info.log()) {
				Properties prop;
				prop = new Properties();
                prop.load(new FileReader("C:\\Users\\KOSTA\\dogi\\prop.properties"));
                for (Object key : prop.keySet()) {
                    String k = (String) key;
                    HashMap<String, String> map = new HashMap<String, String>();
                    map.put("id", prop.getProperty(k));
                    
                    ArrayList<Member> list = mdao.select(map);
                    Member m = list.get(0);
                    if(m.getAdmin().equals("1")) {
                    	ArrayList<Location> loclist = dao.select(null);
                    	
                    	printList(loclist);
                    } else {
                    	System.out.println("관리자 계정이 아닙니다");
                    }
                }
			} else {
				System.out.println("관리자 계정으로 로그인 후 이용할 수 있습니다");
			}
		} catch(SQLException e) {
			System.out.println(e.getMessage());
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void editLoc(Scanner sc) {
		try(LocationDao<Location> dao = (LocationDao<Location>) this.dao){
			if(Info.log()) {
				Properties prop;
				prop = new Properties();
                prop.load(new FileReader("C:\\Users\\KOSTA\\dogi\\prop.properties"));
                for (Object key : prop.keySet()) {
                    String k = (String) key;
                    HashMap<String, String> map = new HashMap<String, String>();
                    map.put("id", prop.getProperty(k));
                    
                    ArrayList<Member> list = mdao.select(map);
                    Member m = list.get(0);
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
			}
		}catch(SQLException e) {
			System.out.println(e.getMessage());
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void delLoc(Scanner sc) {
		try(LocationDao<Location> dao = (LocationDao<Location>) this.dao){
			if(Info.log()) {
				Properties prop;
				prop = new Properties();
                prop.load(new FileReader("C:\\Users\\KOSTA\\dogi\\prop.properties"));
                for (Object key : prop.keySet()) {
                    String k = (String) key;
                    HashMap<String, String> map = new HashMap<String, String>();
                    map.put("id", prop.getProperty(k));
                    
                    ArrayList<Member> list = mdao.select(map);
                    Member m = list.get(0);
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
			}
		}catch(SQLException e) {
			System.out.println(e.getMessage());
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
