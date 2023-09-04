package service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

import common.Manager;
import common.SERVICE;
import dao.RepliesDao;
import vo.Article;
import vo.Member;
import vo.Replies;

public class RepliesService extends SERVICE<Replies> {
	private RepliesDao<Replies> dao;

	public RepliesService(Scanner sc, RepliesDao<Replies> dao, Manager manager) {
		super(sc, dao, manager);
		this.dao = new RepliesDao<Replies>(manager);
	}

	// 댓글 추가
	public void addReplies(Scanner sc, Article a, Member m) {
		try {
			if (loginCheck()) {
				System.out.print("댓글 입력:");
				String con = sc.nextLine();
				dao.insert(new Replies(0, con, null, null, 0, a.getNum(), m.getNo()));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// 해당 게시글 댓글 보기
	public void printAll(Article a) {
		ArrayList<Replies> list = new ArrayList<Replies>(50);
		try {
			list = dao.selectByArtNo(a.getNum());
			System.out.println();
			for (int i = 1; i <= list.size(); i++) {
				System.out.println(list.get(i - 1).toString(i));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// 댓글 수정
	public void updateReplies(Scanner sc, Member m, Article a) {
		try {
			ArrayList<Replies> list = dao.selectByArtNo(a.getNum());
			if (loginCheck()) {
				if (hasComment(a)) {
					System.out.print("수정할 댓글번호:");
					int no = 0;
					no = sc.nextInt();
					if ((list.size() >= no) && (list.size() > 0)) {
						Replies r = dao.selectByNo(list.get(no - 1).getNo());
						if (r.getMember_no() == m.getNo()) {
							System.out.print("수정할 내용:");
							sc.nextLine();
							String content = sc.nextLine();
							r.setContent(content);
							dao.update(new Replies(r.getNo(), r.getContent(), r.getW_date(), r.getE_date(),
									r.getHeart(), r.getArticle_no(), r.getMember_no()));
						} else {
							System.out.println("본인 댓글만 수정가능합니다");
						}
					} else {
						System.out.println("댓글이 존재하지 않습니다");
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// 번호로 삭제
	public void delReplies(Scanner sc, Member m, Article a) {
		try {
			ArrayList<Replies> list = dao.selectByArtNo(a.getNum());
			if (loginCheck()) {
				if (hasComment(a)) {
					System.out.print("삭제할 댓글번호 입력:");
					int no = 0;
					no = sc.nextInt();
					if ((list.size() >= no) && (list.size() > 0)) {
						Replies r = dao.selectByNo(list.get(no - 1).getNo());
						if (r.getMember_no() == m.getNo()) {
							dao.delete(r);
						} else {
							System.out.println("본인 댓글만 삭제가능합니다");
						}
					} else {
						System.out.println("댓글이 존재하지 않습니다");
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// 댓글 좋아요 추가 및 삭제
	public void Like(Article a, Member m) {
		try {
			ArrayList<Replies> list = dao.selectByArtNo(a.getNum());
			if (loginCheck()) {
				if (hasComment(a)) {
					printAll(a);
					System.out.print("'좋아요' 누를 댓글번호:");
					int no = 0;
					no = sc.nextInt();
					if ((list.size() >= no) && (list.size() > 0)) {
						Replies r = dao.selectByNo(list.get(no - 1).getNo());
						if (!dao.isLike(m.getNo(), a.getNum(), r.getNo())) {
							int heart = r.getHeart() + 1;
							r.setHeart(heart);
							dao.updateHeart(new Replies(r.getNo(), r.getContent(), r.getW_date(), r.getE_date(),
									r.getHeart(), r.getArticle_no(), r.getMember_no()));
							dao.likeReply(m.getNo(), a.getNum(), r.getNo());
						} else if (dao.isLike(m.getNo(), a.getNum(), r.getNo())) {
							int heart = r.getHeart() - 1;
							r.setHeart(heart);
							if (heart < 0) {
								r.setHeart(heart + 1);
							}
							dao.updateHeart(new Replies(r.getNo(), r.getContent(), r.getW_date(), r.getE_date(),
									r.getHeart(), r.getArticle_no(), r.getMember_no()));
							dao.dislikeReply(m.getNo(), a.getNum(), r.getNo());
						}
					} else {
						System.out.println("댓글이 존재하지 않습니다");
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// 댓글 유무 확인
	public boolean hasComment(Article a) {
		ArrayList<Replies> list;
		try {
			list = dao.selectByArtNo(a.getNum());
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
		if (((MemberService) this.manager.getService("MemberService")).nowMember() == null) {
			System.out.println("로그인 후 이용해주십시오");
			return false;
		} else {
			return true;
		}
	}
}
