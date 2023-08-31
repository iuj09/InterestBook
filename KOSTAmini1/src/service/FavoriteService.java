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
import dao.FavoriteDao;
import dao.LocationDao;
import dao.MemberDao;
import vo.Favorite;
import vo.Location;
import vo.Member;

public class FavoriteService extends SERVICE<Favorite>{
	private MemberDao mdao;
	
	public FavoriteService(Scanner sc, CRUD<Favorite> dao) {
		super(sc, dao);
		mdao = new MemberDao();
	}
	
	public void printList(ArrayList<Favorite> list) {
		for(Favorite f:list) {
			System.out.println(f);
		}
	}
	
	public void addLoc(Scanner sc) {
		try(FavoriteDao<Favorite> dao = (FavoriteDao<Favorite>) this.dao){
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
		try(FavoriteDao<Favorite> dao = (FavoriteDao<Favorite>) this.dao){
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
                    	System.out.println("조회할 관심사 번호 입력");
                    	System.out.print("No:");
                    	String no = sc.next();
                    	HashMap<String, String> fmap = new HashMap<String, String>();
                    	
                    	fmap.put("no", no);
                    	
                    	ArrayList<Favorite> favlist = dao.select(fmap);
                    	
                    	printList(favlist);
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
		try(FavoriteDao<Favorite> dao = (FavoriteDao<Favorite>) this.dao){
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
                    	ArrayList<Favorite> favlist = dao.select(null);
                    	
                    	printList(favlist);
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
		try(FavoriteDao<Favorite> dao = (FavoriteDao<Favorite>) this.dao){
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
		try(FavoriteDao<Favorite> dao = (FavoriteDao<Favorite>) this.dao){
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
