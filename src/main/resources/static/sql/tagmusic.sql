-- 시퀀스 삭제
DROP SEQUENCE MUSIC_LIKE_SEQ;
DROP SEQUENCE MUSIC_COMMENT_SEQ;
DROP SEQUENCE PLAYLIST_SEQ;
DROP SEQUENCE MY_MUSIC_SEQ;
DROP SEQUENCE ACTIVE_LOG_SEQ;
DROP SEQUENCE MUSIC_SEQ;
DROP SEQUENCE CHAT_SEQ;
DROP SEQUENCE BOARD_SEQ;
DROP SEQUENCE PAYMENT_SEQ;
DROP SEQUENCE PAYMENT_LOG_SEQ;
DROP SEQUENCE PASS_SEQ;
DROP SEQUENCE USERS_SEQ;

DROP SEQUENCE ATTACH_SEQ;
DROP SEQUENCE UPLOAD_SEQ;


-- 시퀀스 생성
CREATE SEQUENCE USERS_SEQ NOCACHE;
CREATE SEQUENCE PAYMENT_LOG_SEQ NOCACHE;
CREATE SEQUENCE PAYMENT_SEQ NOCACHE;
CREATE SEQUENCE PASS_SEQ NOCACHE;
CREATE SEQUENCE BOARD_SEQ NOCACHE;
CREATE SEQUENCE CHAT_SEQ NOCACHE;
CREATE SEQUENCE MUSIC_SEQ NOCACHE;
CREATE SEQUENCE ACTIVE_LOG_SEQ NOCACHE;
CREATE SEQUENCE MY_MUSIC_SEQ NOCACHE;
CREATE SEQUENCE PLAYLIST_SEQ NOCACHE;
CREATE SEQUENCE MUSIC_COMMENT_SEQ NOCACHE;
CREATE SEQUENCE MUSIC_LIKE_SEQ NOCACHE;
CREATE SEQUENCE ATTACH_SEQ NOCACHE;
CREATE SEQUENCE UPLOAD_SEQ NOCACHE;

-- 테이블 삭제
DROP TABLE ACTIVE_LOG    CASCADE CONSTRAINTS;
DROP TABLE MY_MUSIC      CASCADE CONSTRAINTS;
DROP TABLE PLAYLIST      CASCADE CONSTRAINTS;
DROP TABLE MUSIC_COMMENT CASCADE CONSTRAINTS;
DROP TABLE MUSIC_LIKE    CASCADE CONSTRAINTS;
DROP TABLE MUSIC         CASCADE CONSTRAINTS;
DROP TABLE CHAT_ADMIN;
DROP TABLE UPLOAD CASCADE CONSTRAINTS;
DROP TABLE ATTACH CASCADE CONSTRAINTS;
DROP TABLE BOARD  CASCADE CONSTRAINTS;
DROP TABLE PAYMENT;
DROP TABLE PAYMENT_LOG;
DROP TABLE PASS;
DROP TABLE RETIRE_USERS;
DROP TABLE SLEEP_USERS;
DROP TABLE USERS;

DROP TABLE SUMMERNOTE_IMAGE;

-- 테이블 생성
CREATE TABLE USERS (
   USER_NO              NUMBER               NOT NULL,
   EMAIL              VARCHAR2 (100 BYTE)   NOT NULL,
   ARTIST              VARCHAR2 (45 BYTE)   NOT NULL,
   NAME              VARCHAR2 (45 BYTE)   NOT NULL,
   PW                  VARCHAR2 (64 BYTE)   NOT NULL,
    PROFILE_IMAGE      VARCHAR2 (500 BYTE),
   MOBILE              VARCHAR2 (11 BYTE)   NOT NULL,
    GENDER              VARCHAR2 (2 BYTE),
   BIRTHYEAR          VARCHAR2 (4 BYTE),
   BIRTHDAY          VARCHAR2 (4 BYTE),
   POSTCODE          VARCHAR2 (5 BYTE),
   ROAD_ADDRESS      VARCHAR2 (100 BYTE),
   JIBUN_ADDRESS      VARCHAR2 (100 BYTE),
   DETAIL_ADDRESS      VARCHAR2 (100 BYTE),
   EXTRA_ADDRESS      VARCHAR2 (100 BYTE),
    SNS_TYPE          VARCHAR2 (10 BYTE),
    SESSION_ID         VARCHAR2 (32 BYTE),
   SESSION_LIMIT_DATE DATE,
   JOIN_DATE          DATE,
   PW_MODIFY_DATE     DATE,
   INFO_MODIFY_DATE   DATE,
   AGREE_CODE          NUMBER
);

CREATE TABLE SLEEP_USERS (
   USER_NO             NUMBER               NOT NULL,
   EMAIL             VARCHAR2 (100 BYTE)   NOT NULL,
   ARTIST             VARCHAR2 (45 BYTE)   NOT NULL,
   NAME             VARCHAR2 (45 BYTE)   NOT NULL,
   PW                 VARCHAR2 (64 BYTE)   NOT NULL,
    MOBILE             VARCHAR2 (11 BYTE)    NOT NULL,
    JOIN_DATE         DATE                   NOT NULL,
    SLEEP_DATE         DATE                  NOT NULL,
    PROFILE_IMAGE     VARCHAR2 (500 BYTE) ,
   GENDER             VARCHAR2 (2 BYTE),
   BIRTHYEAR         VARCHAR2 (4 BYTE),
   BIRTHDAY         VARCHAR2 (4 BYTE),
   POSTCODE         VARCHAR2 (5 BYTE),
   ROAD_ADDRESS     VARCHAR2 (100 BYTE),
   JIBUN_ADDRESS     VARCHAR2 (100 BYTE),
   DETAIL_ADDRESS    VARCHAR2 (100 BYTE),
   EXTRA_ADDRESS     VARCHAR2 (100 BYTE),
   AGREE_CODE         NUMBER,
   SNS_TYPE         VARCHAR2 (10 BYTE),
   PW_MODIFY_DATE     DATE,
   INFO_MODIFY_DATE  DATE
);

CREATE TABLE RETIRE_USERS (
   USER_NO    NUMBER              NOT NULL,
   EMAIL       VARCHAR2 (100 BYTE) NOT NULL,
   RETIRE_DATE   DATE               NOT NULL,
   ARTIST       VARCHAR2 (45 BYTE) NOT NULL
);

CREATE TABLE PASS(
    PASS_NO     NUMBER      NOT NULL,
    PASS_NAME   VARCHAR2(100 BYTE)      NOT NULL,
    PASS_PRICE  NUMBER      NOT NULL
);
CREATE TABLE PAYMENT(
    PAY_NO NUMBER NOT NULL,
    EMAIL VARCHAR2(100 BYTE),
    PASS_NO NUMBER,
    EXPIRATION_DATE DATE NOT NULL
);
CREATE TABLE PAYMENT_LOG(
    PAY_LOG_NO      VARCHAR2(500 BYTE)      NOT NULL,
    PAY_LOG_EMAIL       VARCHAR2(100 BYTE),
    PAY_LOG_NAME     VARCHAR2(100 BYTE),
    PAY_LOG_DATE    DATE        NOT NULL,
    PAY_LOG_PG      VARCHAR2(100 BYTE),
    PAY_LOG_PRICE NUMBER NOT NULL

);

CREATE TABLE BOARD (
   BOARD_NO          NUMBER                NOT NULL,
   BOARD_TITLE       VARCHAR2(100  BYTE),
   BOARD_CONTENT     VARCHAR2(4000 BYTE),
   BOARD_CREATE_DATE DATE,
   BOARD_HIT         NUMBER,
   IP                 VARCHAR2(30 BYTE),
   GUBUN             VARCHAR2(1 BYTE)
);
CREATE TABLE UPLOAD
(
    UPLOAD_NO NUMBER NOT NULL,  -- PK
    TITLE VARCHAR2(100 BYTE),   -- 제목
    CONTENT VARCHAR2(100 BYTE), -- 내용
    CREATE_DATE TIMESTAMP,      -- 작성일
    MODIFY_DATE TIMESTAMP       -- 수정일
);
CREATE TABLE ATTACH
(
    ATTACH_NO NUMBER NOT NULL,     -- PK
    PATH VARCHAR2(300 BYTE),       -- 파일의 경로
    ORIGIN VARCHAR2(300 BYTE),     -- 파일의 원래 이름
    FILESYSTEM VARCHAR2(42 BYTE),  -- 파일의 저장된 이름
    DOWNLOAD_CNT NUMBER,           -- 다운로드 횟수
    HAS_THUMBNAIL NUMBER,          -- 썸네일이 있으면 1, 없으면 0
    UPLOAD_NO NUMBER               -- 게시글번호, FK
);
CREATE TABLE SUMMERNOTE_IMAGE
(
    BOARD_NO NUMBER,
    FILESYSTEM VARCHAR2(40 BYTE)
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
    MUSIC_NO          NUMBER              NOT NULL,
    EMAIL             VARCHAR2 (45 BYTE)   NOT NULL,
   MUSIC_TITLE         VARCHAR2 (100 BYTE),
   MUSIC_CONTENT     VARCHAR2 (2000 BYTE),
   MUSIC_ALBUM         VARCHAR2 (100 BYTE),
   MUSIC_GENRE         VARCHAR2 (30 BYTE),
   MUSIC_UPLOAD_DATE DATE,
   MUSIC_MODIFY_DATE DATE,
   IP                VARCHAR2 (30 BYTE),
   IMG_ORIGIN         VARCHAR2 (300 BYTE),
   IMG_FILESYSTEM     VARCHAR2 (40 BYTE),
   MUSIC_ORIGIN     VARCHAR2 (300 BYTE),
   MUSIC_FILESYSTEM  VARCHAR2 (40 BYTE),
   DOWNLOAD_CNT     NUMBER,
   IMG_PATH         VARCHAR2 (300 BYTE),
   MUSIC_PATH         VARCHAR2 (300 BYTE),
    HAS_THUMBNAIL   NUMBER
    -- 썸네일이 있으면 1 없으면 0
);

CREATE TABLE ACTIVE_LOG (
    ACTIVE_NO        NUMBER             NOT NULL,
   EMAIL            VARCHAR2 (100 BYTE) NOT NULL,
   MUSIC_NO        NUMBER               NOT NULL,
   LAST_LISTEN_DATE DATE,
   LISTEN_COUNT     NUMBER
);

CREATE TABLE MY_MUSIC (
    MY_MUSIC_NO NUMBER NOT NULL,
   LIST_NO     NUMBER NOT NULL,
   MUSIC_NO    NUMBER NOT NULL
);

CREATE TABLE PLAYLIST (
    LIST_NO    NUMBER             NOT NULL,
   EMAIL      VARCHAR2 (100 BYTE) NOT NULL,
   GROUP_NAME VARCHAR2 (40 BYTE)
);

CREATE TABLE MUSIC_COMMENT (
    COMMENT_NO       NUMBER                 NOT NULL,
    MUSIC_NO       NUMBER                 NOT NULL,
   EMAIL           VARCHAR2 (100 BYTE)    NOT NULL,
   COMMENT_CONTENT VARCHAR2 (1000 BYTE),
   CREATE_DATE       DATE
);

CREATE TABLE MUSIC_LIKE (
    LIKE_NO    NUMBER               NOT NULL,
   EMAIL    VARCHAR2 (100 BYTE)   NOT NULL,
   MUSIC_NO NUMBER               NOT NULL
);


-- 관계키 형성
-- 기본키
ALTER TABLE USERS
    ADD CONSTRAINT PK_USERS PRIMARY KEY (USER_NO);
ALTER TABLE SLEEP_USERS
    ADD CONSTRAINT PK_SLEEP_USERS PRIMARY KEY (USER_NO);
ALTER TABLE RETIRE_USERS
    ADD CONSTRAINT PK_RETIRE_USERS PRIMARY KEY (USER_NO);
ALTER TABLE PASS
    ADD CONSTRAINT PK_PASS PRIMARY KEY (PASS_NO);
ALTER TABLE PAYMENT_LOG
    ADD CONSTRAINT PK_PAYMENT_LOG PRIMARY KEY (PAY_LOG_NO);
ALTER TABLE PAYMENT
    ADD CONSTRAINT PK_PAYMENT PRIMARY KEY (PAY_NO);
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
ALTER TABLE ATTACH
    ADD CONSTRAINT PK_ATTACH
        PRIMARY KEY(ATTACH_NO);
ALTER TABLE UPLOAD
    ADD CONSTRAINT PK_UPLOAD
        PRIMARY KEY(UPLOAD_NO);



-- 고유키 : USERS
ALTER TABLE USERS
    ADD CONSTRAINT UK_USERS UNIQUE (EMAIL);

-- 외래키 : SURMMERNOTE_IMAGE 테이블 
ALTER TABLE SUMMERNOTE_IMAGE
    ADD CONSTRAINT FK_SUMMERNOTE_BOARD
        FOREIGN KEY(BOARD_NO) REFERENCES BOARD(BOARD_NO)
            ON DELETE CASCADE;
-- 외래키 : UPLOAD 테이블
ALTER TABLE ATTACH
    ADD CONSTRAINT FK_ATTACH_UPLOAD
        FOREIGN KEY(UPLOAD_NO) REFERENCES UPLOAD(UPLOAD_NO)
            ON DELETE CASCADE;
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
ALTER TABLE PAYMENT
    ADD CONSTRAINT FK_PAYMENT_USERS
        FOREIGN KEY (EMAIL) REFERENCES USERS (EMAIL)
            ON DELETE CASCADE;            
ALTER TABLE PAYMENT
    ADD CONSTRAINT FK_PAYMENT_PASS
        FOREIGN KEY(PASS_NO)REFERENCES PASS(PASS_NO)
            ON DELETE CASCADE;
            
-- 디폴트
ALTER TABLE USERS
    MODIFY PROFILE_IMAGE
        DEFAULT '/images/basic_profileImage.png';


-- 기본 데이터 베이스(관리자 : userNo=1로 고정)
INSERT INTO USERS
    (USER_NO, EMAIL, ARTIST, NAME, PW, MOBILE, JOIN_DATE)
VALUES
    (USERS_SEQ.NEXTVAL, 'admin@web.com', '관리자', '관리자', ' FFE1ABD1A 8215353C233D6E0 9613E95EEC4253832A761AF28FF37AC5A15 C', '01011111111', SYSDATE);

-- 기본 데이터 베이스(유저)
INSERT INTO USERS
    (USER_NO, EMAIL, ARTIST, NAME, PW, MOBILE, JOIN_DATE)
VALUES
    (USERS_SEQ.NEXTVAL, 'user1@web.com', '회원1', '회원본명1', ' FFE1ABD1A 8215353C233D6E0 9613E95EEC4253832A761AF28FF37AC5A15 C', '01011111111', SYSDATE);
    


-- 기초데이터 영역 ----------------------------------------

-- 기본 데이터 베이스
INSERT INTO USERS (USER_NO, EMAIL, ARTIST, NAME, PW, MOBILE, JOIN_DATE) VALUES (USERS_SEQ.NEXTVAL, 'practice@web.com', '연습1', '연습1', ' FFE1ABD1A 8215353C233D6E0 9613E95EEC4253832A761AF28FF37AC5A15 C', '01011111111', SYSDATE);

-- 음악 게시물 기초 데이터
INSERT INTO MUSIC (MUSIC_NO, EMAIL, MUSIC_TITLE, MUSIC_CONTENT, MUSIC_ALBUM, MUSIC_GENRE, MUSIC_UPLOAD_DATE, MUSIC_MODIFY_DATE, IP, IMG_ORIGIN, IMG_FILESYSTEM, MUSIC_ORIGIN, MUSIC_FILESYSTEM, DOWNLOAD_CNT, IMG_PATH, MUSIC_PATH, HAS_THUMBNAIL )
VALUES 
(MUSIC_SEQ.NEXTVAL,'practice@web.com', '관리제목1', '내용1', '앨범1' , '락', SYSDATE, SYSDATE, '00000001', '이미지1', 'animal1.jpg', '음악1', 'DB음악1', 0, 'finalServer\\img', 'finalServer\\music', 1);
INSERT INTO MUSIC (MUSIC_NO, EMAIL, MUSIC_TITLE, MUSIC_CONTENT, MUSIC_ALBUM, MUSIC_GENRE, MUSIC_UPLOAD_DATE, MUSIC_MODIFY_DATE, IP, IMG_ORIGIN, IMG_FILESYSTEM, MUSIC_ORIGIN, MUSIC_FILESYSTEM, DOWNLOAD_CNT, IMG_PATH, MUSIC_PATH, HAS_THUMBNAIL )
VALUES 
(MUSIC_SEQ.NEXTVAL,'practice@web.com', '관리제목2', '내용1', '앨범2' , '팝송', SYSDATE, SYSDATE, '00000001', '이미지1', 'animal1.jpg', '음악1', 'DB음악1', 0, 'finalServer\\img', 'finalServer\\music', 1);
INSERT INTO MUSIC (MUSIC_NO, EMAIL, MUSIC_TITLE, MUSIC_CONTENT, MUSIC_ALBUM, MUSIC_GENRE, MUSIC_UPLOAD_DATE, MUSIC_MODIFY_DATE, IP, IMG_ORIGIN, IMG_FILESYSTEM, MUSIC_ORIGIN, MUSIC_FILESYSTEM, DOWNLOAD_CNT, IMG_PATH, MUSIC_PATH, HAS_THUMBNAIL )
VALUES 
(MUSIC_SEQ.NEXTVAL,'admin@web.com', '관리제목3', '내용1', '앨범3' , '발라드', SYSDATE, SYSDATE, '00000001', '이미지1', 'animal1.jpg', '음악1', 'DB음악1', 0, 'finalServer\\img', 'finalServer\\music', 1);
INSERT INTO MUSIC (MUSIC_NO, EMAIL, MUSIC_TITLE, MUSIC_CONTENT, MUSIC_ALBUM, MUSIC_GENRE, MUSIC_UPLOAD_DATE, MUSIC_MODIFY_DATE, IP, IMG_ORIGIN, IMG_FILESYSTEM, MUSIC_ORIGIN, MUSIC_FILESYSTEM, DOWNLOAD_CNT, IMG_PATH, MUSIC_PATH, HAS_THUMBNAIL )
VALUES 
(MUSIC_SEQ.NEXTVAL,'admin@web.com', '관리제목4', '내용1', '앨범4' , '클래식', SYSDATE, SYSDATE, '00000001', '이미지1', 'animal1.jpg', '음악1', 'DB음악1', 0, 'finalServer\\img', 'finalServer\\music', 1);
INSERT INTO MUSIC (MUSIC_NO, EMAIL, MUSIC_TITLE, MUSIC_CONTENT, MUSIC_ALBUM, MUSIC_GENRE, MUSIC_UPLOAD_DATE, MUSIC_MODIFY_DATE, IP, IMG_ORIGIN, IMG_FILESYSTEM, MUSIC_ORIGIN, MUSIC_FILESYSTEM, DOWNLOAD_CNT, IMG_PATH, MUSIC_PATH, HAS_THUMBNAIL )
VALUES 
(MUSIC_SEQ.NEXTVAL,'practice@web.com', '관리제목5', '내용1', '앨범5' , '락', SYSDATE, SYSDATE, '00000001', '이미지1', 'animal1.jpg', '음악1', 'DB음악1', 0, 'finalServer\\img', 'finalServer\\music', 1);
INSERT INTO MUSIC (MUSIC_NO, EMAIL, MUSIC_TITLE, MUSIC_CONTENT, MUSIC_ALBUM, MUSIC_GENRE, MUSIC_UPLOAD_DATE, MUSIC_MODIFY_DATE, IP, IMG_ORIGIN, IMG_FILESYSTEM, MUSIC_ORIGIN, MUSIC_FILESYSTEM, DOWNLOAD_CNT, IMG_PATH, MUSIC_PATH, HAS_THUMBNAIL )
VALUES 
(MUSIC_SEQ.NEXTVAL,'admin@web.com', '관리제목6', '내용1', '앨범1' , '팝송', SYSDATE, SYSDATE, '00000001', '이미지1', 'animal1.jpg', '음악1', 'DB음악1', 0, 'finalServer\\img', 'finalServer\\music', 1);
INSERT INTO MUSIC (MUSIC_NO, EMAIL, MUSIC_TITLE, MUSIC_CONTENT, MUSIC_ALBUM, MUSIC_GENRE, MUSIC_UPLOAD_DATE, MUSIC_MODIFY_DATE, IP, IMG_ORIGIN, IMG_FILESYSTEM, MUSIC_ORIGIN, MUSIC_FILESYSTEM, DOWNLOAD_CNT, IMG_PATH, MUSIC_PATH, HAS_THUMBNAIL )
VALUES 
(MUSIC_SEQ.NEXTVAL,'admin@web.com', '관리제목7', '내용1', '앨범2' , '발라드', SYSDATE, SYSDATE, '00000001', '이미지1', 'animal1.jpg', '음악1', 'DB음악1', 0, 'finalServer\\img', 'finalServer\\music', 1);
INSERT INTO MUSIC (MUSIC_NO, EMAIL, MUSIC_TITLE, MUSIC_CONTENT, MUSIC_ALBUM, MUSIC_GENRE, MUSIC_UPLOAD_DATE, MUSIC_MODIFY_DATE, IP, IMG_ORIGIN, IMG_FILESYSTEM, MUSIC_ORIGIN, MUSIC_FILESYSTEM, DOWNLOAD_CNT, IMG_PATH, MUSIC_PATH, HAS_THUMBNAIL )
VALUES 
(MUSIC_SEQ.NEXTVAL,'admin@web.com', '관리제목8', '내용1', '앨범3' , '팝송', SYSDATE, SYSDATE, '00000001', '이미지1', 'animal1.jpg', '음악1', 'DB음악1', 0, 'finalServer\\img', 'finalServer\\music', 1);
INSERT INTO MUSIC (MUSIC_NO, EMAIL, MUSIC_TITLE, MUSIC_CONTENT, MUSIC_ALBUM, MUSIC_GENRE, MUSIC_UPLOAD_DATE, MUSIC_MODIFY_DATE, IP, IMG_ORIGIN, IMG_FILESYSTEM, MUSIC_ORIGIN, MUSIC_FILESYSTEM, DOWNLOAD_CNT, IMG_PATH, MUSIC_PATH, HAS_THUMBNAIL )
VALUES 
(MUSIC_SEQ.NEXTVAL,'admin@web.com', '관리제목9', '내용1', '앨범4' , '클래식', SYSDATE, SYSDATE, '00000001', '이미지1', 'animal1.jpg', '음악1', 'DB음악1', 0, 'finalServer\\img', 'finalServer\\music', 1);
INSERT INTO MUSIC (MUSIC_NO, EMAIL, MUSIC_TITLE, MUSIC_CONTENT, MUSIC_ALBUM, MUSIC_GENRE, MUSIC_UPLOAD_DATE, MUSIC_MODIFY_DATE, IP, IMG_ORIGIN, IMG_FILESYSTEM, MUSIC_ORIGIN, MUSIC_FILESYSTEM, DOWNLOAD_CNT, IMG_PATH, MUSIC_PATH, HAS_THUMBNAIL )
VALUES 
(MUSIC_SEQ.NEXTVAL,'admin@web.com', '관리제목10', '내용1', '앨범5' , '팝송', SYSDATE, SYSDATE, '00000001', '이미지1', 'animal1.jpg', '음악1', 'DB음악1', 0, 'finalServer\\img', 'finalServer\\music', 1);
INSERT INTO MUSIC (MUSIC_NO, EMAIL, MUSIC_TITLE, MUSIC_CONTENT, MUSIC_ALBUM, MUSIC_GENRE, MUSIC_UPLOAD_DATE, MUSIC_MODIFY_DATE, IP, IMG_ORIGIN, IMG_FILESYSTEM, MUSIC_ORIGIN, MUSIC_FILESYSTEM, DOWNLOAD_CNT, IMG_PATH, MUSIC_PATH, HAS_THUMBNAIL )
VALUES 
(MUSIC_SEQ.NEXTVAL,'practice@web.com', '관리제목11', '내용1', '앨범1' , '트로트', SYSDATE, SYSDATE, '00000001', '이미지1', 'animal1.jpg', '음악1', 'DB음악1', 0, 'finalServer\\img', 'finalServer\\music', 1);
INSERT INTO MUSIC (MUSIC_NO, EMAIL, MUSIC_TITLE, MUSIC_CONTENT, MUSIC_ALBUM, MUSIC_GENRE, MUSIC_UPLOAD_DATE, MUSIC_MODIFY_DATE, IP, IMG_ORIGIN, IMG_FILESYSTEM, MUSIC_ORIGIN, MUSIC_FILESYSTEM, DOWNLOAD_CNT, IMG_PATH, MUSIC_PATH, HAS_THUMBNAIL )
VALUES 
(MUSIC_SEQ.NEXTVAL,'admin@web.com', '관리제목12', '내용1', '앨범2' , '클래식', SYSDATE, SYSDATE, '00000001', '이미지1', 'animal1.jpg', '음악1', 'DB음악1', 0, 'finalServer\\img', 'finalServer\\music', 1);
INSERT INTO MUSIC (MUSIC_NO, EMAIL, MUSIC_TITLE, MUSIC_CONTENT, MUSIC_ALBUM, MUSIC_GENRE, MUSIC_UPLOAD_DATE, MUSIC_MODIFY_DATE, IP, IMG_ORIGIN, IMG_FILESYSTEM, MUSIC_ORIGIN, MUSIC_FILESYSTEM, DOWNLOAD_CNT, IMG_PATH, MUSIC_PATH, HAS_THUMBNAIL )
VALUES 
(MUSIC_SEQ.NEXTVAL,'practice@web.com', '관리제목13', '내용1', '앨범3' , '트로트', SYSDATE, SYSDATE, '00000001', '이미지1', 'animal1.jpg', '음악1', 'DB음악1', 0, 'finalServer\\img', 'finalServer\\music', 1);
INSERT INTO MUSIC (MUSIC_NO, EMAIL, MUSIC_TITLE, MUSIC_CONTENT, MUSIC_ALBUM, MUSIC_GENRE, MUSIC_UPLOAD_DATE, MUSIC_MODIFY_DATE, IP, IMG_ORIGIN, IMG_FILESYSTEM, MUSIC_ORIGIN, MUSIC_FILESYSTEM, DOWNLOAD_CNT, IMG_PATH, MUSIC_PATH, HAS_THUMBNAIL )
VALUES 
(MUSIC_SEQ.NEXTVAL,'admin@web.com', '관리제목14', '내용1', '앨범4' , '발라드', SYSDATE, SYSDATE, '00000001', '이미지1', 'animal1.jpg', '음악1', 'DB음악1', 0, 'finalServer\\img', 'finalServer\\music', 1);
INSERT INTO MUSIC (MUSIC_NO, EMAIL, MUSIC_TITLE, MUSIC_CONTENT, MUSIC_ALBUM, MUSIC_GENRE, MUSIC_UPLOAD_DATE, MUSIC_MODIFY_DATE, IP, IMG_ORIGIN, IMG_FILESYSTEM, MUSIC_ORIGIN, MUSIC_FILESYSTEM, DOWNLOAD_CNT, IMG_PATH, MUSIC_PATH, HAS_THUMBNAIL )
VALUES 
(MUSIC_SEQ.NEXTVAL,'admin@web.com', '관리제목15', '내용1', '앨범5' , '락', SYSDATE, SYSDATE, '00000001', '이미지1', '', '음악1', 'DB음악1', 0, '', 'finalServer\\music', 0);
INSERT INTO MUSIC (MUSIC_NO, EMAIL, MUSIC_TITLE, MUSIC_CONTENT, MUSIC_ALBUM, MUSIC_GENRE, MUSIC_UPLOAD_DATE, MUSIC_MODIFY_DATE, IP, IMG_ORIGIN, IMG_FILESYSTEM, MUSIC_ORIGIN, MUSIC_FILESYSTEM, DOWNLOAD_CNT, IMG_PATH, MUSIC_PATH, HAS_THUMBNAIL )
VALUES 
(MUSIC_SEQ.NEXTVAL,'admin@web.com', '관리제목16', '내용1', '앨범1' , '트로트', SYSDATE, SYSDATE, '00000001', '이미지1', 'animal1.jpg', '음악1', 'DB음악1', 0, 'finalServer\\img', 'finalServer\\music', 1);
INSERT INTO MUSIC (MUSIC_NO, EMAIL, MUSIC_TITLE, MUSIC_CONTENT, MUSIC_ALBUM, MUSIC_GENRE, MUSIC_UPLOAD_DATE, MUSIC_MODIFY_DATE, IP, IMG_ORIGIN, IMG_FILESYSTEM, MUSIC_ORIGIN, MUSIC_FILESYSTEM, DOWNLOAD_CNT, IMG_PATH, MUSIC_PATH, HAS_THUMBNAIL )
VALUES 
(MUSIC_SEQ.NEXTVAL,'admin@web.com', '관리제목17', '내용1', '앨범2' , '발라드', SYSDATE, SYSDATE, '00000001', '이미지1', '', '음악1', 'DB음악1', 0, '', 'finalServer\\music', 0);
INSERT INTO MUSIC (MUSIC_NO, EMAIL, MUSIC_TITLE, MUSIC_CONTENT, MUSIC_ALBUM, MUSIC_GENRE, MUSIC_UPLOAD_DATE, MUSIC_MODIFY_DATE, IP, IMG_ORIGIN, IMG_FILESYSTEM, MUSIC_ORIGIN, MUSIC_FILESYSTEM, DOWNLOAD_CNT, IMG_PATH, MUSIC_PATH, HAS_THUMBNAIL )
VALUES 
(MUSIC_SEQ.NEXTVAL,'admin@web.com', '관리제목18', '내용1', '앨범3' , '트로트', SYSDATE, SYSDATE, '00000001', '이미지1', 'animal1.jpg', '음악1', 'DB음악1', 0, 'finalServer\\img', 'finalServer\\music', 1);
INSERT INTO MUSIC (MUSIC_NO, EMAIL, MUSIC_TITLE, MUSIC_CONTENT, MUSIC_ALBUM, MUSIC_GENRE, MUSIC_UPLOAD_DATE, MUSIC_MODIFY_DATE, IP, IMG_ORIGIN, IMG_FILESYSTEM, MUSIC_ORIGIN, MUSIC_FILESYSTEM, DOWNLOAD_CNT, IMG_PATH, MUSIC_PATH, HAS_THUMBNAIL )
VALUES 
(MUSIC_SEQ.NEXTVAL,'admin@web.com', '관리제목19', '내용1', '앨범4' , '발라드', SYSDATE, SYSDATE, '00000001', '이미지1', '', '음악1', 'DB음악1', 0, '', 'finalServer\\music', 0);
INSERT INTO MUSIC (MUSIC_NO, EMAIL, MUSIC_TITLE, MUSIC_CONTENT, MUSIC_ALBUM, MUSIC_GENRE, MUSIC_UPLOAD_DATE, MUSIC_MODIFY_DATE, IP, IMG_ORIGIN, IMG_FILESYSTEM, MUSIC_ORIGIN, MUSIC_FILESYSTEM, DOWNLOAD_CNT, IMG_PATH, MUSIC_PATH, HAS_THUMBNAIL )
VALUES 
(MUSIC_SEQ.NEXTVAL,'admin@web.com', '관리제목20', '내용1', '앨범5' , '클래식', SYSDATE, SYSDATE, '00000001', '이미지1', 'animal1.jpg', '음악1', 'DB음악1', 0, 'finalServer\\img', 'finalServer\\music', 1);
INSERT INTO MUSIC (MUSIC_NO, EMAIL, MUSIC_TITLE, MUSIC_CONTENT, MUSIC_ALBUM, MUSIC_GENRE, MUSIC_UPLOAD_DATE, MUSIC_MODIFY_DATE, IP, IMG_ORIGIN, IMG_FILESYSTEM, MUSIC_ORIGIN, MUSIC_FILESYSTEM, DOWNLOAD_CNT, IMG_PATH, MUSIC_PATH, HAS_THUMBNAIL )
VALUES 
(MUSIC_SEQ.NEXTVAL,'admin@web.com', '관리제목21', '내용1', '앨범5' , '클래식', SYSDATE, SYSDATE, '00000001', '이미지1', 'animal1.jpg', '음악1', 'DB음악1', 0, 'finalServer\\img', 'finalServer\\music', 1);
INSERT INTO MUSIC (MUSIC_NO, EMAIL, MUSIC_TITLE, MUSIC_CONTENT, MUSIC_ALBUM, MUSIC_GENRE, MUSIC_UPLOAD_DATE, MUSIC_MODIFY_DATE, IP, IMG_ORIGIN, IMG_FILESYSTEM, MUSIC_ORIGIN, MUSIC_FILESYSTEM, DOWNLOAD_CNT, IMG_PATH, MUSIC_PATH, HAS_THUMBNAIL )
VALUES 
(MUSIC_SEQ.NEXTVAL,'admin@web.com', '관리제목22', '내용1', '앨범5' , '클래식', SYSDATE, SYSDATE, '00000001', '이미지1', 'animal1.jpg', '음악1', 'DB음악1', 0, 'finalServer\\img', 'finalServer\\music', 1);




-- 로그 기초데이터 : USER 테이블의 EMAIL과  MUSIC 테이블의 MUSIC_NO를 복수의 FK로 받는다
INSERT INTO ACTIVE_LOG (ACTIVE_NO, EMAIL, MUSIC_NO, LAST_LISTEN_DATE, LISTEN_COUNT ) VALUES (ACTIVE_LOG_SEQ.NEXTVAL, 'practice@web.com', '1', SYSDATE, 0);
INSERT INTO ACTIVE_LOG (ACTIVE_NO, EMAIL, MUSIC_NO, LAST_LISTEN_DATE, LISTEN_COUNT ) VALUES (ACTIVE_LOG_SEQ.NEXTVAL, 'practice@web.com', '2', SYSDATE, 0);
INSERT INTO ACTIVE_LOG (ACTIVE_NO, EMAIL, MUSIC_NO, LAST_LISTEN_DATE, LISTEN_COUNT ) VALUES (ACTIVE_LOG_SEQ.NEXTVAL, 'admin@web.com', '3', SYSDATE, 0);
INSERT INTO ACTIVE_LOG (ACTIVE_NO, EMAIL, MUSIC_NO, LAST_LISTEN_DATE, LISTEN_COUNT ) VALUES (ACTIVE_LOG_SEQ.NEXTVAL, 'admin@web.com', '4', SYSDATE, 0);
INSERT INTO ACTIVE_LOG (ACTIVE_NO, EMAIL, MUSIC_NO, LAST_LISTEN_DATE, LISTEN_COUNT ) VALUES (ACTIVE_LOG_SEQ.NEXTVAL, 'practice@web.com', '5', SYSDATE, 0);
INSERT INTO ACTIVE_LOG (ACTIVE_NO, EMAIL, MUSIC_NO, LAST_LISTEN_DATE, LISTEN_COUNT ) VALUES (ACTIVE_LOG_SEQ.NEXTVAL, 'admin@web.com', '6', SYSDATE, 0);
INSERT INTO ACTIVE_LOG (ACTIVE_NO, EMAIL, MUSIC_NO, LAST_LISTEN_DATE, LISTEN_COUNT ) VALUES (ACTIVE_LOG_SEQ.NEXTVAL, 'admin@web.com', '7', SYSDATE, 0);
INSERT INTO ACTIVE_LOG (ACTIVE_NO, EMAIL, MUSIC_NO, LAST_LISTEN_DATE, LISTEN_COUNT ) VALUES (ACTIVE_LOG_SEQ.NEXTVAL, 'admin@web.com', '8', SYSDATE, 0);
INSERT INTO ACTIVE_LOG (ACTIVE_NO, EMAIL, MUSIC_NO, LAST_LISTEN_DATE, LISTEN_COUNT ) VALUES (ACTIVE_LOG_SEQ.NEXTVAL, 'admin@web.com', '9', SYSDATE, 0);
INSERT INTO ACTIVE_LOG (ACTIVE_NO, EMAIL, MUSIC_NO, LAST_LISTEN_DATE, LISTEN_COUNT ) VALUES (ACTIVE_LOG_SEQ.NEXTVAL, 'admin@web.com', '10', SYSDATE, 0);
INSERT INTO ACTIVE_LOG (ACTIVE_NO, EMAIL, MUSIC_NO, LAST_LISTEN_DATE, LISTEN_COUNT ) VALUES (ACTIVE_LOG_SEQ.NEXTVAL, 'practice@web.com', '11', SYSDATE, 0);
INSERT INTO ACTIVE_LOG (ACTIVE_NO, EMAIL, MUSIC_NO, LAST_LISTEN_DATE, LISTEN_COUNT ) VALUES (ACTIVE_LOG_SEQ.NEXTVAL, 'admin@web.com', '12', SYSDATE, 0);
INSERT INTO ACTIVE_LOG (ACTIVE_NO, EMAIL, MUSIC_NO, LAST_LISTEN_DATE, LISTEN_COUNT ) VALUES (ACTIVE_LOG_SEQ.NEXTVAL, 'practice@web.com', '13', SYSDATE, 0);
INSERT INTO ACTIVE_LOG (ACTIVE_NO, EMAIL, MUSIC_NO, LAST_LISTEN_DATE, LISTEN_COUNT ) VALUES (ACTIVE_LOG_SEQ.NEXTVAL, 'admin@web.com', '14', SYSDATE, 0);
INSERT INTO ACTIVE_LOG (ACTIVE_NO, EMAIL, MUSIC_NO, LAST_LISTEN_DATE, LISTEN_COUNT ) VALUES (ACTIVE_LOG_SEQ.NEXTVAL, 'admin@web.com', '15', SYSDATE, 0);
INSERT INTO ACTIVE_LOG (ACTIVE_NO, EMAIL, MUSIC_NO, LAST_LISTEN_DATE, LISTEN_COUNT ) VALUES (ACTIVE_LOG_SEQ.NEXTVAL, 'admin@web.com', '16', SYSDATE, 0);
INSERT INTO ACTIVE_LOG (ACTIVE_NO, EMAIL, MUSIC_NO, LAST_LISTEN_DATE, LISTEN_COUNT ) VALUES (ACTIVE_LOG_SEQ.NEXTVAL, 'admin@web.com', '17', SYSDATE, 0);
INSERT INTO ACTIVE_LOG (ACTIVE_NO, EMAIL, MUSIC_NO, LAST_LISTEN_DATE, LISTEN_COUNT ) VALUES (ACTIVE_LOG_SEQ.NEXTVAL, 'admin@web.com', '18', SYSDATE, 0);
INSERT INTO ACTIVE_LOG (ACTIVE_NO, EMAIL, MUSIC_NO, LAST_LISTEN_DATE, LISTEN_COUNT ) VALUES (ACTIVE_LOG_SEQ.NEXTVAL, 'admin@web.com', '19', SYSDATE, 0);
INSERT INTO ACTIVE_LOG (ACTIVE_NO, EMAIL, MUSIC_NO, LAST_LISTEN_DATE, LISTEN_COUNT ) VALUES (ACTIVE_LOG_SEQ.NEXTVAL, 'admin@web.com', '20', SYSDATE, 0);
INSERT INTO ACTIVE_LOG (ACTIVE_NO, EMAIL, MUSIC_NO, LAST_LISTEN_DATE, LISTEN_COUNT ) VALUES (ACTIVE_LOG_SEQ.NEXTVAL, 'admin@web.com', '21', SYSDATE, 0);
INSERT INTO ACTIVE_LOG (ACTIVE_NO, EMAIL, MUSIC_NO, LAST_LISTEN_DATE, LISTEN_COUNT ) VALUES (ACTIVE_LOG_SEQ.NEXTVAL, 'admin@web.com', '22', SYSDATE, 0);

-- 좋아요 수 기초데이터
INSERT INTO MUSIC_LIKE (LIKE_NO, EMAIL, MUSIC_NO) VALUES (MUSIC_LIKE_SEQ.NEXTVAL, 'admin@web.com', 1);
INSERT INTO MUSIC_LIKE (LIKE_NO, EMAIL, MUSIC_NO) VALUES (MUSIC_LIKE_SEQ.NEXTVAL, 'admin@web.com', 2);
INSERT INTO MUSIC_LIKE (LIKE_NO, EMAIL, MUSIC_NO) VALUES (MUSIC_LIKE_SEQ.NEXTVAL, 'practice@web.com', 11);
INSERT INTO MUSIC_LIKE (LIKE_NO, EMAIL, MUSIC_NO) VALUES (MUSIC_LIKE_SEQ.NEXTVAL, 'admin@web.com', 4);
INSERT INTO MUSIC_LIKE (LIKE_NO, EMAIL, MUSIC_NO) VALUES (MUSIC_LIKE_SEQ.NEXTVAL, 'practice@web.com', 15);
INSERT INTO MUSIC_LIKE (LIKE_NO, EMAIL, MUSIC_NO) VALUES (MUSIC_LIKE_SEQ.NEXTVAL, 'admin@web.com', 9);
INSERT INTO MUSIC_LIKE (LIKE_NO, EMAIL, MUSIC_NO) VALUES (MUSIC_LIKE_SEQ.NEXTVAL, 'practice@web.com', 15);
INSERT INTO MUSIC_LIKE (LIKE_NO, EMAIL, MUSIC_NO) VALUES (MUSIC_LIKE_SEQ.NEXTVAL, 'admin@web.com', 8);
INSERT INTO MUSIC_LIKE (LIKE_NO, EMAIL, MUSIC_NO) VALUES (MUSIC_LIKE_SEQ.NEXTVAL, 'practice@web.com', 11);
INSERT INTO MUSIC_LIKE (LIKE_NO, EMAIL, MUSIC_NO) VALUES (MUSIC_LIKE_SEQ.NEXTVAL, 'practice@web.com', 7);
INSERT INTO MUSIC_LIKE (LIKE_NO, EMAIL, MUSIC_NO) VALUES (MUSIC_LIKE_SEQ.NEXTVAL, 'admin@web.com', 19);
INSERT INTO MUSIC_LIKE (LIKE_NO, EMAIL, MUSIC_NO) VALUES (MUSIC_LIKE_SEQ.NEXTVAL, 'admin@web.com', 19);
INSERT INTO MUSIC_LIKE (LIKE_NO, EMAIL, MUSIC_NO) VALUES (MUSIC_LIKE_SEQ.NEXTVAL, 'admin@web.com', 19);
INSERT INTO MUSIC_LIKE (LIKE_NO, EMAIL, MUSIC_NO) VALUES (MUSIC_LIKE_SEQ.NEXTVAL, 'admin@web.com', 3);

-- 댓글수 기초데이터
INSERT INTO MUSIC_COMMENT (COMMENT_NO, MUSIC_NO, EMAIL, COMMENT_CONTENT, CREATE_DATE) VALUES (MUSIC_LIKE_SEQ.NEXTVAL, 3, 'admin@web.com', '댓글1', SYSDATE);
INSERT INTO MUSIC_COMMENT (COMMENT_NO, MUSIC_NO, EMAIL, COMMENT_CONTENT, CREATE_DATE) VALUES (MUSIC_LIKE_SEQ.NEXTVAL, 3, 'practice@web.com', '댓글1', SYSDATE);
INSERT INTO MUSIC_COMMENT (COMMENT_NO, MUSIC_NO, EMAIL, COMMENT_CONTENT, CREATE_DATE) VALUES (MUSIC_LIKE_SEQ.NEXTVAL, 3, 'practice@web.com', '댓글1', SYSDATE);
INSERT INTO MUSIC_COMMENT (COMMENT_NO, MUSIC_NO, EMAIL, COMMENT_CONTENT, CREATE_DATE) VALUES (MUSIC_LIKE_SEQ.NEXTVAL, 4, 'admin@web.com', '댓글1', SYSDATE);
INSERT INTO MUSIC_COMMENT (COMMENT_NO, MUSIC_NO, EMAIL, COMMENT_CONTENT, CREATE_DATE) VALUES (MUSIC_LIKE_SEQ.NEXTVAL, 5, 'admin@web.com', '댓글1', SYSDATE);
INSERT INTO MUSIC_COMMENT (COMMENT_NO, MUSIC_NO, EMAIL, COMMENT_CONTENT, CREATE_DATE) VALUES (MUSIC_LIKE_SEQ.NEXTVAL, 7, 'admin@web.com', '댓글1', SYSDATE);
INSERT INTO MUSIC_COMMENT (COMMENT_NO, MUSIC_NO, EMAIL, COMMENT_CONTENT, CREATE_DATE) VALUES (MUSIC_LIKE_SEQ.NEXTVAL, 8, 'admin@web.com', '댓글1', SYSDATE);
INSERT INTO MUSIC_COMMENT (COMMENT_NO, MUSIC_NO, EMAIL, COMMENT_CONTENT, CREATE_DATE) VALUES (MUSIC_LIKE_SEQ.NEXTVAL, 13, 'admin@web.com', '댓글1', SYSDATE);
INSERT INTO MUSIC_COMMENT (COMMENT_NO, MUSIC_NO, EMAIL, COMMENT_CONTENT, CREATE_DATE) VALUES (MUSIC_LIKE_SEQ.NEXTVAL, 13, 'admin@web.com', '댓글1', SYSDATE);
INSERT INTO MUSIC_COMMENT (COMMENT_NO, MUSIC_NO, EMAIL, COMMENT_CONTENT, CREATE_DATE) VALUES (MUSIC_LIKE_SEQ.NEXTVAL, 2, 'admin@web.com', '댓글1', SYSDATE);
INSERT INTO MUSIC_COMMENT (COMMENT_NO, MUSIC_NO, EMAIL, COMMENT_CONTENT, CREATE_DATE) VALUES (MUSIC_LIKE_SEQ.NEXTVAL, 2, 'admin@web.com', '댓글1', SYSDATE);
INSERT INTO MUSIC_COMMENT (COMMENT_NO, MUSIC_NO, EMAIL, COMMENT_CONTENT, CREATE_DATE) VALUES (MUSIC_LIKE_SEQ.NEXTVAL, 1, 'admin@web.com', '댓글1', SYSDATE);
INSERT INTO MUSIC_COMMENT (COMMENT_NO, MUSIC_NO, EMAIL, COMMENT_CONTENT, CREATE_DATE) VALUES (MUSIC_LIKE_SEQ.NEXTVAL, 2, 'admin@web.com', '댓글1', SYSDATE);
INSERT INTO MUSIC_COMMENT (COMMENT_NO, MUSIC_NO, EMAIL, COMMENT_CONTENT, CREATE_DATE) VALUES (MUSIC_LIKE_SEQ.NEXTVAL, 7, 'admin@web.com', '댓글1', SYSDATE);
   INSERT INTO MUSIC_COMMENT (COMMENT_NO, MUSIC_NO, EMAIL, COMMENT_CONTENT, CREATE_DATE) VALUES (MUSIC_LIKE_SEQ.NEXTVAL, 8, 'admin@web.com', '댓글1', SYSDATE);

INSERT INTO PASS
    (PASS_NO, PASS_NAME, PASS_PRICE)
VALUES 
    (PASS_SEQ.NEXTVAL, '30일이용권', 1);
INSERT INTO PASS
    (PASS_NO, PASS_NAME, PASS_PRICE)
VALUES 
    (PASS_SEQ.NEXTVAL, '정액이용권', 2);

COMMIT;