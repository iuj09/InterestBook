package service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

import common.Info;
import common.Manager;
import common.SERVICE;
import dao.RepliesDao;
import vo.Article;
import vo.Member;
import vo.Replies;

public class RepliesService extends SERVICE<Replies> {
	private RepliesDao<Replies> dao;
	private Member m;
	private Article a;

	public RepliesService(Scanner sc, RepliesDao<Replies> dao, Manager manager) {
		super(sc, dao, manager);
		this.dao = new RepliesDao<Replies>(manager);
		m = new Member();
		a = new Article();
	}

	// 댓글 추가
	public void addReplies(Scanner sc) {
		// 로그인 확인
		try {
			// 실제 사용시 !삭제하기
			if (!loginCheck()) {
				System.out.print("댓글 입력:");
				String con = sc.next();
				dao.insert(new Replies(0, con, null, null, 0, 1, 1));
				// 게시판 번호, 멤버 번호 참조값으로 가져옴(수정필요)
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// 댓글 보기
	public void printAll() {
		ArrayList<Replies> list;
		try {
			list = dao.selectAll();
			if (hasComment()) {
				System.out.println();
				for (Replies r : list) {
					System.out.println(r);
				}
				System.out.println();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// 댓글 수정
	public void updateReplies(Scanner sc) {
		// 로그인 확인
		try {
			// 실제 사용시 !삭제하기
			if (!loginCheck()) {
				if (hasComment()) {
					printAll();
					System.out.print("수정할 댓글번호:");
					int no = 0;
					no = sc.nextInt();
					Replies r = dao.selectByNo(no);
					// 로그인한 사용자와 작성자가 동일한지 체크
					if (r.getMember_no() == m.getNo()) {
						System.out.print("수정할 내용:");
						sc.nextLine();
						String content = sc.nextLine();
						r.setContent(content);
						dao.update(new Replies(r.getNo(), r.getContent(), r.getW_date(), r.getE_date(), r.getHeart(),
								r.getArticle_no(), r.getMember_no()));
					} else {
						System.out.println("본인 댓글만 수정가능합니다");
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// 번호로 삭제
	public void delReplies(Scanner sc) {
		// 로그인 확인
		try {
			// 실제 사용시 !삭제하기
			if (!loginCheck()) {
				if (hasComment()) {
					printAll();
					System.out.print("삭제할 댓글번호 입력:");
					int no = 0;
					no = sc.nextInt();
					Replies r = dao.selectByNo(no);
					// 로그인한 사용자와 작성자가 동일한지 체크
					if (r.getMember_no() == m.getNo()) {
						dao.delete(r);
					} else {
						System.out.println("본인 댓글만 삭제가능합니다");
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// 댓글 좋아요 추가 및 삭제
	// 좋아요 입력시 Replies_Like에 Members_no. Articles_no. Replies_no 저장
	// dao.islike()로 값이 있는지 검색해 boolean 값 반환
	// true일 경우 이미 좋아요를 눌렀음 -> 좋아요 -1(DELETE)
	// false 일경우 좋아요를 누르지 않음 -> 좋아요 +1(INSERT)
	// Members_no. Articles_no. Replies_no 참조
	public void Like() {
		try {
			if (!loginCheck()) {
				if (hasComment()) {
					printAll();
					System.out.print("'좋아요' 누를 댓글번호:");
					int no = 0;
					no = sc.nextInt();
					Replies r = dao.selectByNo(no);
					// 좋아요 한번 추가
					if (!dao.isLike(1, 1, r.getNo())) {
						int heart = r.getHeart() + 1;
						r.setHeart(heart);
						dao.updateHeart(new Replies(r.getNo(), r.getContent(), r.getW_date(), r.getE_date(),
								r.getHeart(), r.getArticle_no(), r.getMember_no()));
						dao.likeReply(1, 1, r.getNo());
						// 이미 좋아요 눌렀을 경우 취소
					} else if (dao.isLike(1, 1, r.getNo())) {
						int heart = r.getHeart() - 1;
						r.setHeart(heart);
						if (heart < 0) {
							r.setHeart(heart + 1);
						}
						dao.updateHeart(new Replies(r.getNo(), r.getContent(), r.getW_date(), r.getE_date(),
								r.getHeart(), r.getArticle_no(), r.getMember_no()));
						dao.dislikeReply(1, 1, r.getNo());
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// 댓글 유무 확인
	public boolean hasComment() {
		ArrayList<Replies> list;
		try {
			list = dao.selectAll();
			if (list.size() == 0) {
				System.out.println("작성된 댓글이 없습니다");
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return true;
	}

	// 로그인 상태 확인 (true면 로그인 됨 / false면 로그인 안됨)
	public boolean loginCheck() {
		if (!Info.log()) {
			System.out.println("로그인 후 이용해주십시오");
			return false;
		} else {
			return true;
		}
	}
}