import java.util.Scanner;

import common.Info;
import dao.MemberDao;
import service.MemberService;
import vo.Member;

public class Main {
	public static void main(String[] args) {
//<<<<<<< HEAD
		// TODO Auto-generated method stub
		Scanner sc = new Scanner(System.in);
		MemberService mservice = new MemberService(sc, new MemberDao<Member>());
//		Info.login(sc, new MemberDao<Member>());
//		Info.logout();
//		mservice.join(sc);
//		mservice.editAdm(sc)
//		mservice.myInfo(sc);
//		mservice.editInfo(sc);
//		mservice.searchByName(sc); //select id, name, locNo, favNo
//		mservice.searchById(sc); //select id, name, locNo, favNo
//		mservice.searchByNum(sc);
//		mservice.searchAll();
//		mservice.delMember(sc);
		
//=======
//		Menu menu = new Menu();
//		menu.menu();
//>>>>>>> refs/remotes/origin/iuj
	}

}
