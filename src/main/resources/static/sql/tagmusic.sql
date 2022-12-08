-- 시퀀스 삭제
DROP SEQUENCE MUSIC_LIKE_SEQ;
DROP SEQUENCE MUSIC_COMMENT_SEQ;
DROP SEQUENCE PLAYLIST_SEQ;
DROP SEQUENCE MY_MUSIC_SEQ;
DROP SEQUENCE ACTIVE_LOG_SEQ;
DROP SEQUENCE MUSIC_SEQ;
DROP SEQUENCE CHAT_SEQ;
DROP SEQUENCE BOARD_SEQ;
DROP SEQUENCE USERS_SEQ;


-- 시퀀스 생성
CREATE SEQUENCE USERS_SEQ NOCACHE;
CREATE SEQUENCE BOARD_SEQ NOCACHE;
CREATE SEQUENCE CHAT_SEQ  NOCACHE;
CREATE SEQUENCE MUSIC_SEQ NOCACHE;
CREATE SEQUENCE ACTIVE_LOG_SEQ NOCACHE;
CREATE SEQUENCE MY_MUSIC_SEQ NOCACHE;
CREATE SEQUENCE PLAYLIST_SEQ NOCACHE;
CREATE SEQUENCE MUSIC_COMMENT_SEQ NOCACHE;
CREATE SEQUENCE MUSIC_LIKE_SEQ NOCACHE;

-- 테이블 삭제
DROP TABLE ACTIVE_LOG    CASCADE CONSTRAINTS;
DROP TABLE MY_MUSIC      CASCADE CONSTRAINTS;
DROP TABLE PLAYLIST      CASCADE CONSTRAINTS;
DROP TABLE MUSIC_COMMENT CASCADE CONSTRAINTS;
DROP TABLE MUSIC_LIKE    CASCADE CONSTRAINTS;
DROP TABLE MUSIC         CASCADE CONSTRAINTS;
DROP TABLE CHAT_ADMIN;
DROP TABLE BOARD;
DROP TABLE RETIRE_USERS;
DROP TABLE SLEEP_USERS;
DROP TABLE USERS;

-- 테이블 생성
CREATE TABLE USERS (
	USER_NO	           NUMBER	            NOT NULL,
	EMAIL	           VARCHAR2 (45 BYTE)   NOT NULL,
	ARTIST	           VARCHAR2 (45 BYTE)   NOT NULL,
	NAME	           VARCHAR2 (45 BYTE)   NOT NULL,
	PW	               VARCHAR2 (45 BYTE)   NOT NULL,
    PROFILE_IMAGE      VARCHAR2 (500 BYTE)  NOT NULL,
	MOBILE	           VARCHAR2 (11 BYTE)   NOT NULL,
    GENDER	           VARCHAR2 (2 BYTE),
	BIRTHYEAR	       VARCHAR2 (4 BYTE),
	BIRTHDAY	       VARCHAR2 (4 BYTE),
	POSTCODE	       VARCHAR2 (5 BYTE),
	ROAD_ADDRESS	   VARCHAR2 (100 BYTE),
	JIBUN_ADDRESS	   VARCHAR2 (100 BYTE),
	DETAIL_ADDRESS	   VARCHAR2 (100 BYTE),
	EXTRA_ADDRESS	   VARCHAR2 (100 BYTE),
    SNS_TYPE	       VARCHAR2 (10 BYTE),
    SESSION_ID         VARCHAR2 (32 BYTE),
	SESSION_LIMIT_DATE DATE,
	JOIN_DATE	       DATE,
	PW_MODIFY_DATE     DATE,
	INFO_MODIFY_DATE   DATE,
	AGREE_CODE	       NUMBER
);

CREATE TABLE SLEEP_USERS (
	USER_NO	          NUMBER	            NOT NULL,
	EMAIL	          VARCHAR2 (45 BYTE)	NOT NULL,
	ARTIST	          VARCHAR2 (45 BYTE)	NOT NULL,
	NAME	          VARCHAR2 (45 BYTE)	NOT NULL,
	PW	              VARCHAR2 (45 BYTE)	NOT NULL,
    MOBILE	          VARCHAR2 (11 BYTE)    NOT NULL,
    JOIN_DATE	      DATE	                NOT NULL,
    SLEEP_DATE	      DATE                  NOT NULL,
    PROFILE_IMAGE	  VARCHAR2 (500 BYTE)   NOT NULL,
	GENDER	          VARCHAR2 (2 BYTE),
	BIRTHYEAR	      VARCHAR2 (4 BYTE),
	BIRTHDAY	      VARCHAR2 (4 BYTE),
	POSTCODE	      VARCHAR2 (5 BYTE),
	ROAD_ADDRESS	  VARCHAR2 (100 BYTE),
	JIBUN_ADDRESS	  VARCHAR2 (100 BYTE),
	DETAIL_ADDRESS    VARCHAR2 (100 BYTE),
	EXTRA_ADDRESS	  VARCHAR2 (100 BYTE),
	AGREE_CODE	      NUMBER,
	SNS_TYPE	      VARCHAR2 (10 BYTE),
	PW_MODIFY_DATE	  DATE,
	INFO_MODIFY_DATE  DATE
);

CREATE TABLE RETIRE_USERS (
	RETIRE_NO	NUMBER	           NOT NULL,
	EMAIL	    VARCHAR2 (45 BYTE) NOT NULL,
	RETIRE_DATE	DATE               NOT NULL,
	ARTIST	    VARCHAR2 (45 BYTE) NOT NULL
);

CREATE TABLE BOARD (
	BOARD_NO          NUMBER                NOT NULL,
	BOARD_TITLE       VARCHAR2(100  BYTE),
	BOARD_CONTENT	  VARCHAR2(4000 BYTE),
	BOARD_CREATE_DATE DATE,
	BOARD_HIT         NUMBER,
	IP	              VARCHAR2(30 BYTE),
	GUBUN	          VARCHAR2(1 BYTE)
);

CREATE TABLE CHAT_ADMIN (
   CHAT_NO     NUMBER             NOT NULL,
   USER_NO     NUMBER             NOT NULL,
   CHAT_DATE   DATE,
   IP          VARCHAR2(30 BYTE),
   CONTENT     VARCHAR2(100 BYTE),
   STATE       NUMBER(1),
   DEPTH       NUMBER(2),
   GROUP_NO    NUMBER,
   GROUP_ORDER NUMBER
);

CREATE TABLE MUSIC (
    MUSIC_NO          NUMBER	           NOT NULL,
    EMAIL	          VARCHAR2 (45 BYTE)   NOT NULL,
	MUSIC_TITLE	      VARCHAR2 (100 BYTE),
	MUSIC_CONTENT	  VARCHAR2 (2000 BYTE),
	MUSIC_ALBUM	      VARCHAR2 (100 BYTE),
	MUSIC_GENRE	      VARCHAR2 (30 BYTE),
	MUSIC_UPLOAD_DATE DATE,
	MUSIC_MODIFY_DATE DATE,
	IP                VARCHAR2 (30 BYTE),
	IMG_ORIGIN	      VARCHAR2 (300 BYTE),
	IMG_FILESYSTEM	  VARCHAR2 (40 BYTE),
	MUSIC_ORIGIN	  VARCHAR2 (300 BYTE),
	MUSIC_FILESYSTEM  VARCHAR2 (40 BYTE),
	DOWNLOAD_CNT	  NUMBER,
	IMAGE_PATH	      VARCHAR2 (300 BYTE),
	MUSIC_PATH	      VARCHAR2 (300 BYTE)
);

CREATE TABLE ACTIVE_LOG (
    ACTIVE_NO	     NUMBER	            NOT NULL,
	EMAIL	         VARCHAR2 (45 BYTE)	NOT NULL,
	MUSIC_NO	     NUMBER	            NOT NULL,
	LAST_LISTEN_DATE DATE,
	LISTEN_COUNT     NUMBER
);

CREATE TABLE MY_MUSIC (
    MY_MUSIC_NO NUMBER NOT NULL,
	LIST_NO     NUMBER NOT NULL,
	MUSIC_NO    NUMBER NOT NULL
);

CREATE TABLE PLAYLIST (
    LIST_NO    NUMBER	          NOT NULL,
	EMAIL      VARCHAR2 (45 BYTE) NOT NULL,
	GROUP_NAME VARCHAR2 (40 BYTE)
);

CREATE TABLE MUSIC_COMMENT (
    COMMENT_NO	    NUMBER	              NOT NULL,
    MUSIC_NO	    NUMBER	              NOT NULL,
	EMAIL	        VARCHAR2 (45 BYTE)    NOT NULL,
	COMMENT_CONTENT VARCHAR2 (1000 BYTE),
	CREATE_DATE	    DATE
);

CREATE TABLE MUSIC_LIKE (
    LIKE_NO	 NUMBER	            NOT NULL,
	EMAIL	 VARCHAR2 (45 BYTE)	NOT NULL,
	MUSIC_NO NUMBER	            NOT NULL
);


-- 관계키 형성
-- 기본키
ALTER TABLE USERS
    ADD CONSTRAINT PK_USERS PRIMARY KEY (USER_NO);
ALTER TABLE SLEEP_USERS
    ADD CONSTRAINT PK_SLEEP_USERS PRIMARY KEY (USER_NO);
ALTER TABLE RETIRE_USERS
    ADD CONSTRAINT PK_RETIRE_USERS PRIMARY KEY (RETIRE_NO);
ALTER TABLE BOARD
    ADD CONSTRAINT PK_BOARD PRIMARY KEY (BOARD_NO);
ALTER TABLE CHAT_ADMIN
    ADD CONSTRAINT PK_CHAT_ADMIN PRIMARY KEY (CHAT_NO);
ALTER TABLE MUSIC
    ADD CONSTRAINT PK_MUSIC PRIMARY KEY (MUSIC_NO);
ALTER TABLE ACTIVE_LOG
    ADD CONSTRAINT PK_ACTIVE_LOG PRIMARY KEY (ACTIVE_NO);
ALTER TABLE PLAYLIST
    ADD CONSTRAINT PK_PLAYLIST PRIMARY KEY (LIST_NO);
ALTER TABLE MY_MUSIC
    ADD CONSTRAINT PK_MY_MUSIC PRIMARY KEY (MY_MUSIC_NO);
ALTER TABLE MUSIC_COMMENT
    ADD CONSTRAINT PK_MUSIC_COMMENTE PRIMARY KEY (COMMENT_NO);
ALTER TABLE MUSIC_LIKE
    ADD CONSTRAINT PK_MUSIC_LIKE PRIMARY KEY (LIKE_NO);

-- 고유키 : USERS
ALTER TABLE USERS
    ADD CONSTRAINT UK_USERS UNIQUE (EMAIL);

-- 외래키 : CHAT_ADMIN 테이블
ALTER TABLE CHAT_ADMIN
    ADD CONSTRAINT FK_CHAT_ADMIN_USERS
        FOREIGN KEY (USER_NO) REFERENCES USERS (USER_NO)
            ON DELETE CASCADE;
-- 외래키 : ACTIVE_LOG 테이블
ALTER TABLE ACTIVE_LOG
    ADD CONSTRAINT FK_ACTIVE_LOG_USERS
        FOREIGN KEY (EMAIL) REFERENCES USERS (EMAIL)
            ON DELETE CASCADE;
ALTER TABLE ACTIVE_LOG
    ADD CONSTRAINT FK_ACTIVE_LOG_MUSIC
        FOREIGN KEY (MUSIC_NO) REFERENCES MUSIC (MUSIC_NO)
            ON DELETE CASCADE;
-- 외래키 : PLAYLIST 테이블
ALTER TABLE PLAYLIST
    ADD CONSTRAINT FK_PLAYLIST_USERS
        FOREIGN KEY(EMAIL) REFERENCES USERS (EMAIL)
            ON DELETE CASCADE;
-- 외래키 : MY_MUSIC 테이블
ALTER TABLE MY_MUSIC
    ADD CONSTRAINT FK_MY_MUSIC_MUSIC
        FOREIGN KEY(MUSIC_NO) REFERENCES MUSIC(MUSIC_NO)
            ON DELETE CASCADE;
-- 외래키 : MUSIC_COMMENT
ALTER TABLE MUSIC_COMMENT
    ADD CONSTRAINT FK_MUSIC_COMMENT_USERS
        FOREIGN KEY(EMAIL) REFERENCES USERS(EMAIL)
            ON DELETE CASCADE;
ALTER TABLE MUSIC_COMMENT
    ADD CONSTRAINT FK_MUSIC_COMMENT_MUSIC
        FOREIGN KEY(MUSIC_NO) REFERENCES MUSIC(MUSIC_NO)
            ON DELETE CASCADE;
-- 외래키 : MUSIC_LIKE
ALTER TABLE MUSIC_LIKE
    ADD CONSTRAINT FK_MUSIC_LIKE_USERS
        FOREIGN KEY(EMAIL) REFERENCES USERS(EMAIL)
            ON DELETE CASCADE;
ALTER TABLE MUSIC_LIKE
    ADD CONSTRAINT FK_MUSIC_LIKE_MUSIC
        FOREIGN KEY(MUSIC_NO) REFERENCES MUSIC(MUSIC_NO)
            ON DELETE CASCADE;
        
-- 기본 데이터 베이스
INSERT INTO USERS
    (USER_NO, EMAIL, ARTIST, NAME, PW, MOBILE, PROFILE_IMAGE, JOIN_DATE)
VALUES
    (USERS_SEQ.NEXTVAL, 'admin@web.com', '관리자', '관리자', '1111', '01011111111', '123', SYSDATE);
COMMIT;