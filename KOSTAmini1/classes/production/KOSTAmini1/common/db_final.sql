ALTER TABLE MEETS ADD ENTER NUMBER DEFAULT '1';

ALTER TABLE MEETS DROP COLUMN ENTER;

CREATE TABLE MEMBER_MEETS_JOIN (
    MEMBERS_NO              NUMBER,
    MEETS_NO                NUMBER,

    FOREIGN KEY(MEMBERS_NO) REFERENCES MEMBERS(NO),
    FOREIGN KEY(MEETS_NO) REFERENCES MEETS(NO)
);


-- 지역
CREATE TABLE LOCATIONS (
    -- 번호
    NO                  NUMBER                  PRIMARY KEY,
    -- 지역 이름
    NAME                VARCHAR2(100)           UNIQUE
);

-- 관심사
CREATE TABLE FAVORITES (
    -- 번호
    NO                  NUMBER                  PRIMARY KEY,
    -- 이름
    NAME                VARCHAR2(100)           NOT NULL,
    -- 관심이 등록되어 있는 회원 수
    HEART               NUMBER                  DEFAULT 0
);

-- 회원
CREATE TABLE MEMBERS (
    -- 번호
    NO                  NUMBER                  PRIMARY KEY,
    -- 아이디
    ID                  VARCHAR2(100)           UNIQUE,
    -- 비밀번호
    PWD                 VARCHAR2(100)           NOT NULL,
    -- 이름
    NAME                VARCHAR2(100)           NOT NULL,
    -- 이메일
    EMAIL               VARCHAR2(100)           NOT NULL,
    -- 가입 날짜
    JOIN_DATE           DATE                    DEFAULT SYSDATE,
    -- 슈퍼 계정 설정 여부(0: 일반계정 / 1: 슈퍼계정)
    ADMIN               CHAR(1)                 DEFAULT '0',                    
    
    -- FK
    -- 지역 번호
    LOCATIONS_NO         NUMBER                 NOT NULL,
    -- 관심사 번호
    FAVORITES_NO         NUMBER                 NOT NULL,
    
    -- 제약조건
    CONSTRAINT FK_LOCATIONS_NO FOREIGN KEY(LOCATIONS_NO) REFERENCES LOCATIONS(NO) ON DELETE SET NULL,
    CONSTRAINT FK_FAVORITES_NO FOREIGN KEY(FAVORITES_NO) REFERENCES FAVORITES(NO) ON DELETE SET NULL
);

-- 게시판
CREATE TABLE ARTICLES (
    -- 번호
    NO                  NUMBER                 PRIMARY KEY,
    -- 제목
    TITLE               VARCHAR2(100)          NOT NULL,
    -- 내용
    CONTENT             VARCHAR2(100)          NOT NULL,
    -- 좋아요 수
    HEART               NUMBER                 DEFAULT 0,
    -- 첫 게시 날짜
    W_DATE              DATE                   DEFAULT SYSDATE,
    -- 최종 수정 날짜
    E_DATE              DATE                   DEFAULT SYSDATE,

    -- FK
    -- 회원 번호
    MEMBERS_NO          NUMBER                 NOT NULL,
    -- 관심사 번호
    FAVORITES_NO        NUMBER                 NOT NULL,

    -- 제약조건  
    CONSTRAINT FK_MEMBERS_NO FOREIGN KEY(MEMBERS_NO) REFERENCES MEMBERS(NO) ON DELETE CASCADE,
    FOREIGN KEY(FAVORITES_NO) REFERENCES FAVORITES(NO) ON DELETE SET NULL
);

-- 댓글
CREATE TABLE REPLIES (
    -- 이름
    NO                  NUMBER                  PRIMARY KEY,
    -- 내용
    CONTENT             VARCHAR2(100)           NOT NULL,
    -- 첫 게시 날짜
    W_DATE              DATE                    DEFAULT SYSDATE,
    -- 최종 수정 날짜
    E_DATE              DATE                    DEFAULT SYSDATE,
    -- 좋아요 수
    HEART               NUMBER                  DEFAULT 0,

    -- FK
    -- 게시판 번호
    ARTICLES_NO         NUMBER                  NOT NULL,
    -- 회원 번호
    MEMBERS_NO          NUMBER                  NOT NULL,
    
    -- 제약조건
    CONSTRAINT FK_ARTICLES_NO FOREIGN KEY(ARTICLES_NO) REFERENCES ARTICLES(NO) ON DELETE CASCADE,
    FOREIGN KEY(MEMBERS_NO) REFERENCES MEMBERS(NO) ON DELETE CASCADE
);

-- 모집글
CREATE TABLE MEETS (
    -- 번호
    NO                  NUMBER                  PRIMARY KEY,
    -- 모집인원 수
    RECURIT             NUMBER                  DEFAULT 1,
    -- 참가인원 수
    ENTER               NUMBER                  DEFAULT 1,
    -- 제목
    TITLE               VARCHAR2(100)           NOT NULL,
    -- 내용
    CONTENT             VARCHAR2(100)           NOT NULL,
    -- 첫 게시 날짜
    W_DATE              DATE                    DEFAULT SYSDATE,
    -- 마지막 수정 날짜
    E_DATE              DATE                    DEFAULT SYSDATE,
    -- 마감 날짜
    DEADLINE            DATE                    NOT NULL,

    -- FK
    -- 지역 번호
    LOCATIONS_NO         NUMBER                 NOT NULL,
    -- 회원 번호
    MEMBERS_NO           NUMBER                 NOT NULL,
    
    -- 제약조건
    FOREIGN KEY(LOCATIONS_NO) REFERENCES LOCATIONS(NO) ON DELETE SET NULL,
    FOREIGN KEY(MEMBERS_NO) REFERENCES MEMBERS(NO) ON DELETE CASCADE
);

-- 모집글 댓글
CREATE TABLE MEETS_REPLIES (
    -- 번호
    NO                  NUMBER                  PRIMARY KEY,
    -- 내용
    CONTENT             VARCHAR2(100)           NOT NULL,
    -- 첫 게시 날짜
    W_DATE              DATE                    DEFAULT SYSDATE,
    -- 마지막 수정 날짜
    E_DATE              DATE                    DEFAULT SYSDATE,
    -- 대댓글 번호(이 번호는 MEETS_REPLIES의 NO)
    IN_REPLY            NUMBER                  DEFAULT 0,

    -- FK
    -- 모집글 번호
    MEETS_NO         NUMBER,
    -- 회원 번호
    MEMBERS_NO       NUMBER,
    
    -- 제약조건
    CONSTRAINT FK_MEETS_NO FOREIGN KEY(MEETS_NO) REFERENCES MEETS(NO) ON DELETE CASCADE,
    FOREIGN KEY(MEMBERS_NO) REFERENCES MEMBERS(NO) ON DELETE CASCADE
);

-- 게시판 좋아요 테이블
CREATE TABLE ARTICLES_LIKE (
    -- 회원 번호
    MEMBERS_NO           NUMBER                  NOT NULL,
    -- 게시판 번호
    ARTICLES_NO          NUMBER                  NOT NULL,
    
    -- FK
    FOREIGN KEY(MEMBERS_NO) REFERENCES MEMBERS(NO) ON DELETE CASCADE,
    FOREIGN KEY(ARTICLES_NO) REFERENCES ARTICLES(NO) ON DELETE CASCADE
);

-- 댓글 좋아요 테이블
CREATE TABLE REPLIES_LIKE (
    -- 회원 번호
    MEMBERS_NO           NUMBER                  NOT NULL,
    -- 게시판 번호
    ARTICLES_NO          NUMBER                  NOT NULL,
    -- 댓글 번호
    REPLIES_NO           NUMBER                  NOT NULL,
    
    -- FK
    FOREIGN KEY(MEMBERS_NO) REFERENCES MEMBERS(NO) ON DELETE CASCADE,
    FOREIGN KEY(ARTICLES_NO) REFERENCES ARTICLES(NO) ON DELETE CASCADE,
    CONSTRAINT FK_REPLIES_NO FOREIGN KEY(REPLIES_NO) REFERENCES REPLIES(NO) ON DELETE CASCADE
);

-- 모집 인원 관련 테이블
CREATE TABLE MEETS_RECURIT_JOIN (
    -- 모집글 번호
    MEETS_NO                NUMBER,
    -- 회원 번호
    MEMBERS_NO              NUMBER,
    -- 모집글에 참가한 인원을 체크하는 컬럼(참가를 했다 취소한 사람도 체크 
    -- 0: 불참 / 1: 참가
    EXC                     NUMBER(1)               CHECK(EXC IN(0, 1))
);

-- 테이블별 NO 시퀀스
CREATE SEQUENCE ARTICLES_NO_SEQUENCE;

CREATE SEQUENCE MEMBERS_NO_SEQUENCE;

CREATE SEQUENCE REPLIES_NO_SEQUENCE;

CREATE SEQUENCE MEETS_NO_SEQUENCE;

CREATE SEQUENCE MEETS_REPLIES_NO_SEQUENCE;

-- 임시 데이터
INSERT INTO LOCATIONS VALUES(1, '서울특별시');
INSERT INTO LOCATIONS VALUES(2, '경기도');
INSERT INTO LOCATIONS VALUES(3, '강원도');
INSERT INTO LOCATIONS VALUES(4, '충청도');
INSERT INTO LOCATIONS VALUES(5, '전라도');
INSERT INTO LOCATIONS VALUES(6, '경상도');

INSERT INTO FAVORITES(NO, NAME) VALUES(1, '운동');
INSERT INTO FAVORITES(NO, NAME) VALUES(2, '요리');
INSERT INTO FAVORITES(NO, NAME) VALUES(3, '공부');
INSERT INTO FAVORITES(NO, NAME) VALUES(4, '독서');
INSERT INTO FAVORITES(NO, NAME) VALUES(5, '음악');

-- 슈퍼계정
INSERT INTO MEMBERS VALUES(MEMBERS_NO_SEQUENCE.NEXTVAL, 'admin', '12', '관리자', 'aaa@gmail.com', SYSDATE, 1, 1, 3);
-- 일반계정
INSERT INTO MEMBERS VALUES(MEMBERS_NO_SEQUENCE.NEXTVAL, 'cook', '121', '요리회원1', 'aab@gmail.com', SYSDATE, 0, 3, 2);
INSERT INTO MEMBERS VALUES(MEMBERS_NO_SEQUENCE.NEXTVAL, 'music', '122', '음악회원1', 'aac@gmail.com', SYSDATE, 0, 2, 5);
INSERT INTO MEMBERS VALUES(MEMBERS_NO_SEQUENCE.NEXTVAL, 'book', '21', '독서회원1', 'bba@gmail.com', SYSDATE, 0, 5, 4);
INSERT INTO MEMBERS VALUES(MEMBERS_NO_SEQUENCE.NEXTVAL, 'study', '211', '공부회원1', 'bbb@gmail.com', SYSDATE, 0, 4, 1);
INSERT INTO MEMBERS VALUES(MEMBERS_NO_SEQUENCE.NEXTVAL, 'read', '212', '독서회원2', 'bbc@gmail.com', SYSDATE, 0, 2, 4);
INSERT INTO MEMBERS VALUES(MEMBERS_NO_SEQUENCE.NEXTVAL, 'exercise', '31', '운동회원1', 'cca@gmail.com', SYSDATE, 0, 3, 1);
INSERT INTO MEMBERS VALUES(MEMBERS_NO_SEQUENCE.NEXTVAL, 'playlist', '311', '음악회원2', 'ccb@gmail.com', SYSDATE, 0, 5, 5);
INSERT INTO MEMBERS VALUES(MEMBERS_NO_SEQUENCE.NEXTVAL, 'food', '312', '요리회원2', 'ccc@gmail.com', SYSDATE, 0, 6, 2);
INSERT INTO MEMBERS VALUES(MEMBERS_NO_SEQUENCE.NEXTVAL, 'health', '012', '운동회원2', 'ddd@gmail.com', SYSDATE, 0, 3, 1);
INSERT INTO MEMBERS VALUES(MEMBERS_NO_SEQUENCE.NEXTVAL, 'CDD', '000', '요리회원3', 'dda@gmail.com', SYSDATE, 0, 6, 2);
INSERT INTO MEMBERS VALUES(MEMBERS_NO_SEQUENCE.NEXTVAL, 'DDD', '011', '음악회원3', 'dcb@gmail.com', SYSDATE, 0, 2, 5);
INSERT INTO MEMBERS VALUES(MEMBERS_NO_SEQUENCE.NEXTVAL, 'CCC', '010', '독서회원3', 'def@gmail.com', SYSDATE, 0, 2, 4);



INSERT INTO ARTICLES VALUES(ARTICLES_NO_SEQUENCE.NEXTVAL, 'TID', 'database - oracle', 0, sysdate, sysdate, 5, 3);
INSERT INTO ARTICLES VALUES(ARTICLES_NO_SEQUENCE.NEXTVAL, '비오는 날 듣기 좋은 playlist', '잔잔한 노래 list', 0, sysdate, sysdate, 3, 5);
INSERT INTO ARTICLES VALUES(ARTICLES_NO_SEQUENCE.NEXTVAL, '고전문학 추천', '데미안', 0, sysdate, sysdate, 4, 4);
INSERT INTO ARTICLES VALUES(ARTICLES_NO_SEQUENCE.NEXTVAL, '닭볶음탕 레시피', '닭, 감자, 당근, 양념장 ...', 0, sysdate, sysdate, 2, 2);
INSERT INTO ARTICLES VALUES(ARTICLES_NO_SEQUENCE.NEXTVAL, '집에서 쉽게 해 먹을 수 있는 메뉴', '추천해주실 수 있나요?', 0, sysdate, sysdate, 9, 2);
INSERT INTO ARTICLES VALUES(ARTICLES_NO_SEQUENCE.NEXTVAL, '오운완!', '밖에 나가기 귀찮아서 홈트했습니다', 0, sysdate, sysdate, 7, 1);
INSERT INTO ARTICLES VALUES(ARTICLES_NO_SEQUENCE.NEXTVAL, '저메추 받아요', '추천 부탁드립니다!', 0, sysdate, sysdate, 2, 2);
INSERT INTO ARTICLES VALUES(ARTICLES_NO_SEQUENCE.NEXTVAL, 'java class', 'class 클래스명{}', 0, sysdate, sysdate, 5, 3);
INSERT INTO ARTICLES VALUES(ARTICLES_NO_SEQUENCE.NEXTVAL, '포브스 선정 꼭 읽어야하는 책', 'list{0, 1, 2, 3 ...}', 0, sysdate, sysdate, 4, 4);

INSERT INTO REPLIES VALUES(REPLIES_NO_SEQUENCE.NEXTVAL, '플리가 너무 좋아요', SYSDATE, SYSDATE, 0, 2, 8);
INSERT INTO REPLIES VALUES(REPLIES_NO_SEQUENCE.NEXTVAL, '감사합니다', SYSDATE, SYSDATE, 0, 2, 2);
INSERT INTO REPLIES VALUES(REPLIES_NO_SEQUENCE.NEXTVAL, '한 번 읽어봐야겠어요', SYSDATE, SYSDATE, 0, 3, 6);
INSERT INTO REPLIES VALUES(REPLIES_NO_SEQUENCE.NEXTVAL, '제목보고 알려주시는 줄 알았는데..', SYSDATE, SYSDATE, 0, 5, 2);
INSERT INTO REPLIES VALUES(REPLIES_NO_SEQUENCE.NEXTVAL, '고기!', SYSDATE, SYSDATE, 0, 7, 9);
INSERT INTO REPLIES VALUES(REPLIES_NO_SEQUENCE.NEXTVAL, '공유 감사합니다', SYSDATE, SYSDATE, 0, 9, 6);
INSERT INTO REPLIES VALUES(REPLIES_NO_SEQUENCE.NEXTVAL, '도움이 많이 됐어요', SYSDATE, SYSDATE, 0, 4, 2);
INSERT INTO REPLIES VALUES(REPLIES_NO_SEQUENCE.NEXTVAL, 'ㅋㅋㅋㅋ', SYSDATE, SYSDATE, 0, 5, 9);
INSERT INTO REPLIES VALUES(REPLIES_NO_SEQUENCE.NEXTVAL, 'good', SYSDATE, SYSDATE, 0, 6, 10);

INSERT INTO MEETS VALUES(MEETS_NO_SEQUENCE.NEXTVAL, 5, 1, '주말 풋살5인', '주말에 공 차실 분 5분 구합니다', SYSDATE, SYSDATE, SYSDATE + 3, 3, 7);
INSERT INTO MEETS VALUES(MEETS_NO_SEQUENCE.NEXTVAL, 10, 1, '내일 저녁 집에서 파티해요(10인)', '같이 저녁 드실 분', SYSDATE, SYSDATE, SYSDATE + 1, 6, 9);
INSERT INTO MEETS VALUES(MEETS_NO_SEQUENCE.NEXTVAL, 4, 1, '독서모임(4인)', '매주 독서모임 하실 분 모집합니다', SYSDATE, SYSDATE, SYSDATE + 5, 2, 6);
INSERT INTO MEETS VALUES(MEETS_NO_SEQUENCE.NEXTVAL, 2, 1, '콘서트 같이 가실 분 한 분 구해요', '티켓이 남아서 같이 가실 분 구합니다', SYSDATE, SYSDATE, SYSDATE + 1, 2, 12);


INSERT INTO MEETS_REPLIES VALUES(MEETS_REPLIES_NO_SEQUENCE.NEXTVAL, '참가합니다', SYSDATE, SYSDATE, 0, 1, 10);
INSERT INTO MEETS_REPLIES VALUES(MEETS_REPLIES_NO_SEQUENCE.NEXTVAL, '내일은 시간이 안돼서ㅜ', SYSDATE, SYSDATE, 0, 2, 11);
INSERT INTO MEETS_REPLIES VALUES(MEETS_REPLIES_NO_SEQUENCE.NEXTVAL, '저요!', SYSDATE, SYSDATE, 0, 3, 13);

INSERT INTO MEETS_RECURIT_JOIN VALUES(1, 10, 1);
INSERT INTO MEETS_RECURIT_JOIN VALUES(3, 13, 1);

COMMIT;