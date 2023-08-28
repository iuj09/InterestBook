- 계정 생성 및 권한 부여

conn /as sysdba

create user kosta identified by 1234;

grant connect, resource, dba to kosta;

commit;

-- 테이블 생성
-- 지역
CREATE TABLE LOCATION (
    NO                  NUMBER                  PRIMARY KEY,
    NAME                VARCHAR2(100)           UNIQUE
);

-- 관심사
CREATE TABLE FAVORITE(
    NO                  NUMBER                  PRIMARY KEY,
    NAME                VARCHAR2(100)           NOT NULL,
    HEART               NUMBER                  DEFAULT 0
);

-- 회원
CREATE TABLE MEMBER (
    NO                  NUMBER                  PRIMARY KEY,
    ID                  VARCHAR2(100)           UNIQUE,
    PWD                 VARCHAR2(100)           NOT NULL,
    NAME                VARCHAR2(100)           NOT NULL,
    EMAIL               VARCHAR2(100)           NOT NULL,
    JOIN_DATE           DATE                    DEFAULT SYSDATE,
    
    LOCATION_NO         NUMBER,
    FAVORITE_NO         NUMBER,
    
    CONSTRAINT FK_LOCATION_NO FOREIGN KEY(LOCATION_NO) REFERENCES LOCATION(NO),
    CONSTRAINT FK_FAVORITE_NO FOREIGN KEY(FAVORITE_NO) REFERENCES FAVORITE(NO)
);

-- 게시판
CREATE TABLE BOARD (
    NO                  NUMBER                 PRIMARY KEY,
    TITLE               VARCHAR2(100)          NOT NULL,
    CONTENT             VARCHAR2(100)          NOT NULL,
    HEART               NUMBER                 DEFAULT 0,
    W_DATE              DATE                   DEFAULT SYSDATE,
    E_DATE              DATE                   DEFAULT SYSDATE,
    MEMBER_NO           NUMBER                 NOT NULL,
    FAVORITE_NO         NUMBER                 NOT NULL,
    
    CONSTRAINT FK_MEMBER_NO FOREIGN KEY(MEMBER_NO) REFERENCES MEMBER(NO) ON DELETE SET NULL,
    FOREIGN KEY(FAVORITE_NO) REFERENCES FAVORITE(NO)
);

-- 댓글
CREATE TABLE REPLY (
    NO                  NUMBER                  PRIMARY KEY,
    CONTENT             VARCHAR2(100)           NOT NULL,
    W_DATE              DATE                    DEFAULT SYSDATE,
    E_DATE              DATE                    DEFAULT SYSDATE,
    HEART               NUMBER                  DEFAULT 0,
    BOARD_NO            NUMBER                  NOT NULL,
    MEMBER_NO           NUMBER                  NOT NULL,
    
    CONSTRAINT FK_BOARD_NO FOREIGN KEY(BOARD_NO) REFERENCES BOARD(NO),
    FOREIGN KEY(MEMBER_NO) REFERENCES MEMBER(NO) ON DELETE SET NULL
);

-- 미팅
CREATE TABLE MEET (
    NO                  NUMBER                  PRIMARY KEY,
    RECURIT             NUMBER                  DEFAULT 1,
    TITLE               VARCHAR2(100)           NOT NULL,
    CONTENT             VARCHAR2(100)           NOT NULL,
    W_DATE              DATE                    DEFAULT SYSDATE,
    E_DATE              DATE                    DEFAULT SYSDATE,
    DEADLINE            DATE                    NOT NULL,
    LOCATION_NO         NUMBER                  NOT NULL,
    MEMBER_NO           NUMBER                  NOT NULL,
    
    FOREIGN KEY(LOCATION_NO) REFERENCES LOCATION(NO),
    FOREIGN KEY(MEMBER_NO) REFERENCES MEMBER(NO)
);

CREATE TABLE BOARD_LIKE (
    MEMBER_NO           NUMBER                  NOT NULL,
    BOARD_NO            NUMBER                  NOT NULL,
    
    FOREIGN KEY(MEMBER_NO) REFERENCES MEMBER(NO),
    FOREIGN KEY(BOARD_NO) REFERENCES BOARD(NO)
);

CREATE TABLE REPLY_LIKE (
    MEMBER_NO           NUMBER                  NOT NULL,
    BOARD_NO            NUMBER                  NOT NULL,
    REPLY_NO            NUMBER                  NOT NULL,
        
    FOREIGN KEY(MEMBER_NO) REFERENCES MEMBER(NO),
    FOREIGN KEY(BOARD_NO) REFERENCES BOARD(NO),
    CONSTRAINT FK_REPLY_NO FOREIGN KEY(REPLY_NO) REFERENCES REPLY(NO)
);

-- 테이블별 NO 시퀀스
CREATE SEQUENCE BOARD_NO_SEQUENCE;

CREATE SEQUENCE MEMBER_NO_SEQUENCE;

CREATE SEQUENCE REPLY_NO_SEQUENCE;

CREATE SEQUENCE MMET_NO_SEQUENCE;

-- 임시 데이터
INSERT INTO LOCATION VALUES(1, '서울특별시');
INSERT INTO LOCATION VALUES(2, '경기도');
INSERT INTO LOCATION VALUES(3, '강원도');
INSERT INTO LOCATION VALUES(4, '충청도');
INSERT INTO LOCATION VALUES(5, '전라도');
INSERT INTO LOCATION VALUES(6, '경상도');

INSERT INTO FAVORITE(NO, NAME) VALUES(1, '운동');
INSERT INTO FAVORITE(NO, NAME) VALUES(2, '요리');
INSERT INTO FAVORITE(NO, NAME) VALUES(3, '공부');
INSERT INTO FAVORITE(NO, NAME) VALUES(4, '독서');
INSERT INTO FAVORITE(NO, NAME) VALUES(5, '음악');

INSERT INTO MEMBER VALUES(1, 'AAA', '12', '철수', 'aaa@gmail.com', SYSDATE, 1, 3);
INSERT INTO MEMBER VALUES(2, 'AAB', '121', '영자', 'aab@gmail.com', SYSDATE, 3, 2);
INSERT INTO MEMBER VALUES(3, 'AAC', '122', '옥자', 'aac@gmail.com', SYSDATE, 2, 5);
INSERT INTO MEMBER VALUES(4, 'BBA', '21', '마빡', 'bba@gmail.com', SYSDATE, 5, 4);
INSERT INTO MEMBER VALUES(5, 'BBB', '211', '맹구', 'bbb@gmail.com', SYSDATE, 4, 3);
INSERT INTO MEMBER VALUES(6, 'BBC', '212', '유리', 'bbc@gmail.com', SYSDATE, 2, 2);
INSERT INTO MEMBER VALUES(7, 'CCA', '31', '하마', 'cca@gmail.com', SYSDATE, 3, 1);
INSERT INTO MEMBER VALUES(8, 'CCB', '311', '탱고', 'ccb@gmail.com', SYSDATE, 5, 5);
INSERT INTO MEMBER VALUES(9, 'CCC', '312', '천수', 'ccc@gmail.com', SYSDATE, 6, 2);

INSERT INTO BOARD VALUES(1, 'hello', 'world', 0, sysdate, sysdate, 1, 5);
INSERT INTO BOARD VALUES(2, 'my', 'world', 0, sysdate, sysdate, 3, 2);
INSERT INTO BOARD VALUES(3, 'your', 'world', 0, sysdate, sysdate, 4, 1);
INSERT INTO BOARD VALUES(4, 'her', 'world', 0, sysdate, sysdate, 2, 4);
INSERT INTO BOARD VALUES(5, 'him', 'world', 0, sysdate, sysdate, 6, 3);
INSERT INTO BOARD VALUES(6, 'girls', 'world', 0, sysdate, sysdate, 7, 2);
INSERT INTO BOARD VALUES(7, 'boys', 'world', 0, sysdate, sysdate, 2, 4);
INSERT INTO BOARD VALUES(8, 'aunt', 'world', 0, sysdate, sysdate, 1, 1);
INSERT INTO BOARD VALUES(9, 'uncle', 'world', 0, sysdate, sysdate, 4, 1);

INSERT INTO REPLY VALUES(1, '야이씨', SYSDATE, SYSDATE, 0, 1, 3);
INSERT INTO REPLY VALUES(2, '저이씨', SYSDATE, SYSDATE, 0, 4, 2);
INSERT INTO REPLY VALUES(3, '상놈의씨', SYSDATE, SYSDATE, 0, 3, 1);
INSERT INTO REPLY VALUES(4, '개노무씨', SYSDATE, SYSDATE, 0, 5, 1);
INSERT INTO REPLY VALUES(5, '이자슥씨', SYSDATE, SYSDATE, 0, 7, 5);
INSERT INTO REPLY VALUES(6, '오함마씨', SYSDATE, SYSDATE, 0, 9, 6);
INSERT INTO REPLY VALUES(7, '장도리씨', SYSDATE, SYSDATE, 0, 4, 4);
INSERT INTO REPLY VALUES(8, '어린노무자슥', SYSDATE, SYSDATE, 5, 1, 8);
INSERT INTO REPLY VALUES(9, '이노무자슥', SYSDATE, SYSDATE, 0, 6, 1);

INSERT INTO MEET VALUES(1, 5, '데스윙팟4인', '4인급구', SYSDATE, SYSDATE, '2023/09/10', 3, 1);
INSERT INTO MEET VALUES(2, 10, '데스윙팟10인', '10인급구', SYSDATE, SYSDATE, '2023/09/11', 2, 2);
INSERT INTO MEET VALUES(3, 2, '데스윙팟2인', '2인급구', SYSDATE, SYSDATE, '2023/09/12', 1, 7);
INSERT INTO MEET VALUES(4, 8, '데스윙팟8인', '8인급구', SYSDATE, SYSDATE, '2023/09/13', 5, 4);
INSERT INTO MEET VALUES(5, 21, '데스윙팟21인', '21인급구', SYSDATE, SYSDATE, '2023/09/14', 4, 5);
INSERT INTO MEET VALUES(6, 9, '데스윙팟9인', '9인급구', SYSDATE, SYSDATE, '2023/09/10', 1, 5);
INSERT INTO MEET VALUES(7, 4, '데스윙팟4인', '4인급구', SYSDATE, SYSDATE, '2023/09/12', 5, 8);
INSERT INTO MEET VALUES(8, 6, '데스윙팟6인', '6인급구', SYSDATE, SYSDATE, '2023/09/13', 4, 4);
INSERT INTO MEET VALUES(9, 2, '데스윙팟2인', '2인급구', SYSDATE, SYSDATE, '2023/09/11', 6, 3);

COMMIT;