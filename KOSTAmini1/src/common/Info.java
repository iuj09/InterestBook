package common;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Scanner;

import dao.MemberDao;
import vo.Member;

public class Info {
    /*
     * 로그인 상태 확인
     */
    public static boolean log() {
        boolean check = false;
        try {
            File file = new File("C:\\Users\\KOSTA\\dogi\\prop.properties");
            if (!file.isFile()) {
                file.createNewFile();
            }

            Properties prop = null;

            if (file.isFile()) {
                // 로그인 상태 체크
                prop = new Properties();
                prop.load(new FileReader("C:\\Users\\KOSTA\\dogi\\prop.properties"));
                for (Object key : prop.keySet()) {
                    String k = (String) key;
                    // 로그인 상태
                    if (!prop.getProperty(k).equals("?")) {
                        check = true;
                        return check;
                    }
                }
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        // 로그아웃 상태
        return check;
    }

    /**
     * 로그인
     * 
     * @param sc
     * @param dao
     * @return
     */
    public static boolean login(Scanner sc, MemberDao<Member> dao) {
        File file = new File("C:\\Users\\KOSTA\\dogi\\prop.properties");
        String id = null;
        String pwd = null;

        try (MemberDao<Member> memberDao = (MemberDao<Member>)dao;) {
            if (!file.isFile()) {
                file.createNewFile();
            }

            Properties prop = new Properties();

            if (file.isFile()) {
                // 로그인 되어 있는 상태
                if (log()) {
                    return true;
                }

                // 로그인 되어 있지 않은 상태에서 로그인
                while (true) {
                    System.out.print("아이디 입력: ");
                    id = sc.next();

                    System.out.print("비밀번호 입력: ");
                    pwd = sc.next();

                    // 아이디 비밀번호 체크
                    boolean idCheck = false;
                    boolean pwdCheck = false;
                    
                        ArrayList<Member> list = memberDao.select(null);
                        for (Member member : list) {
                            if (member.getId().equals(id)) {
                                idCheck = true;
                            }
                            if (member.getPwd().equals(pwd)) {
                                pwdCheck = true;
                            }
                        }

                        if (idCheck && pwdCheck) {
                            break;
                        } else {
                            System.out.println("아이디 혹은 비밀번호가 틀렸습니다.");
                        }
                }

                prop.put("id", id);
                prop.put("pwd", pwd);
                prop.store(new FileWriter("C:\\Users\\KOSTA\\dogi\\prop.properties"), "로그인 정보");
            }
        } catch(SQLException e) {
            System.out.println(e.getMessage());
        } catch(IOException e) {
            System.out.println(e.getMessage());
        }

        return false;
    }

    /**
     * 로그아웃
     * @throws SQLException
     * @throws IOException
     */
    public static void logout() {
        try {
            Properties prop = new Properties();

            prop.put("id", "?");
            prop.put("pwd", "?");
            prop.store(new FileWriter("C:\\Users\\KOSTA\\dogi\\prop.properties"), "로그인 정보");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
