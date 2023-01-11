# TagMusic
진행 : 2022년 12월 05일 ~ 2023년 01월 01일 (4주)

주제 : 음악 스트리밍 및 커뮤니티 사이트


# 목차
 0. TagMusic (요약)
 1. 사용툴
 2. 구현기능
 3. 구현기능 상세페이지 및 설명
 
 
 ## TECH STACK
이미지 | 기술 
---- | ----
![자바](https://user-images.githubusercontent.com/109578927/211863807-e1d79da7-ea72-4a46-9503-546304ee58c2.png) | Java - jdk 1.8
![부트](https://user-images.githubusercontent.com/109578927/211864068-64b1a518-c855-4e1f-8080-1ee3907930da.png) | Spring Boot
![제이쿼리](https://user-images.githubusercontent.com/109578927/211862012-176d2ae5-2ba5-4b45-bcb7-5d06244fc17e.png) | Jquery
![마이에스큐엘](https://user-images.githubusercontent.com/109578927/211861814-c8401fc8-f6f9-4979-8c9e-2c84e49aae63.png) | MySQL
![자바스크립트](https://user-images.githubusercontent.com/109578927/211860948-44af18c8-3946-4b6f-9a46-e5511b3cd753.png) | JavaScript
![마이바티스](https://user-images.githubusercontent.com/109578927/211860508-924ff44b-5b5e-4646-ab34-8e64100e40a7.png) | MyBatis
![톰캣](https://user-images.githubusercontent.com/109578927/211863346-38db93e9-cd36-4234-96de-974de5979c41.png) | Apatch Tomcat 9.0
![에이치티엠엘](https://user-images.githubusercontent.com/109578927/211864920-da664848-b0ff-4138-a6b6-2c3490b12218.png) | HTML
![씨에스에스](https://user-images.githubusercontent.com/109578927/211865356-ab343546-a3a2-4c85-899d-158fc0fadf7d.png) | CSS



## 구현기능
1. 회원기능 (로그인, 회원가입, 회원정보 수정, 회원탈퇴)
2. 플레이리스트
3. 가장 많이 들은 음악 리스트
4. 좋아요 누른 음악 리스트
5. 최신곡 리스트
6. 인기가요 리스트 (장르별)
7. 뮤직플레이 (현재 음악 재생 바bar)
8. 음원 리스트 (음원정보, 좋아요, 플레이리스트에 추가, 음원 다운로드)
9. 게시판 (공지사항, 자주 하는 질문)
10. 1:1 채팅 (문의)
11. 관리자기능 (휴면, 탈퇴, 채팅 문의 답변)
12. 스트리밍 및 다운로드할 수 있는 이용권, 이용권 선물
13. 쿠폰 (무료 이용)
14. 알림




## 구현기능 상세보기

### 1. 회원기능

![로그인](https://user-images.githubusercontent.com/109578927/211839042-556ed804-3fef-4e61-87b6-5ca5eb7c190d.png)
- 이메일 정규식
- 비밀번호 정규식
- 이메일 기억하기
- 자동로그인 (15일)

![마이페이지](https://user-images.githubusercontent.com/109578927/211868205-bf95c777-eca7-4fd3-ae09-6a9464932ed4.png)


### 2. 플레이리스트

![image](https://user-images.githubusercontent.com/109578927/211840189-b6a080d1-514c-4279-b337-6937a7242e14.png)
- 플레이리스트 생성



### 3. 가장 많이 들은 음악 리스트

![image](https://user-images.githubusercontent.com/109578927/211841937-7feb4262-b64f-4d08-b68d-5d4c1956858a.png)
- 가장 많이 들은 음악 리스트 출력
- '지우기' 버튼을 이용해 데이터 영구 삭제



### 4. 좋아요 누른 음악 리스트

![image](https://user-images.githubusercontent.com/109578927/211842730-eb6c9952-ddd2-47db-9904-0e62c16aae0a.png)

- '깨진 하트 모양' 아이콘을 클릭함으로써 좋아요 취소



### 5. 최신곡 리스트

![최신리스트](https://user-images.githubusercontent.com/109578927/211866732-0e5b8b54-97c0-469b-aa9e-e84650e1dfc3.png)

- 페이징 처리 (좌/우 아이콘을 클릭하여 페이지 이동)
- 최신곡 상세리스트 ('+' 아이콘을 클릭하여 상세페이지로 이동)



#### 6. 인기가요 리스트 (장르별)

![인기리스트](https://user-images.githubusercontent.com/109578927/211845150-d1300fe4-8b52-4b12-a257-340957a7b278.png)

- 장르 별로 좋아요를 많이 받은 순으로 출력



### 7. 뮤직플레이 (현재 음악 재생 바bar)


![재생바](https://user-images.githubusercontent.com/109578927/211856980-014f7c2f-4c46-42bf-abbc-baca9adbbd76.png)

- 이전 곡 / 다음 곡 재생 가능
- 현재 곡 재생 / 일시정지 가능
- 음소거 가능
- 한 곡 반복 재생 가능
- '닫기' 버튼 클릭 시 뮤직 플레이바 닫고 메인 페이지로 이동


### 8. 음원 리스트 (음원정보, 좋아요, 플레이리스트에 추가, 음원 다운로드)


![음악리스트 페이지](https://user-images.githubusercontent.com/109578927/211854791-99c95660-2630-4a1e-8fd8-4e6de562f6f4.png)

- 음원 이미지 / 음원명 / 장르 / 좋아요 / 플레이리스트에 추가 / 음원 다운로드 순으로 기능구현
- 앨범 이미지를 클릭 시, 음원 상페보기 페이지로 이동
- 좋아요 클릭 시, 빨간 하트 아이콘으로 변경. 한 번 더 클릭 시 좋아요 취소
- 장바구니 아이콘을 클릭 시, 생성된 플레이리스트 중에 선택하여 음원 추가 가능
- 플레이리스트 아이콘 클릭 시, 플레이리스트 생성 가능


### 9. 게시판 (공지사항, 자주 하는 질문)


![공지사항 페이지](https://user-images.githubusercontent.com/109578927/211853849-d2108c94-c9e8-4c93-8c07-b1b96c78eb18.png)

- 관리자만 작성할 수 있으며, 회원과 비회원은 열람만 할 수 있다.


### 10. 1:1 채팅 (문의)



### 11. 관리자기능 (휴면, 탈퇴, 채팅 문의 답변)


![관리자문의 페이지](https://user-images.githubusercontent.com/109578927/211852644-3f7a1ebe-653a-4736-b8f4-faef3e73d7d8.png)

- 관리자의 경우, 카카오톡과 유사한 UI로 1:1채팅 문의 레이아웃 구성



### 12. 스트리밍 및 다운로드할 수 있는 이용권, 이용권 선물


![이용권 페이지](https://user-images.githubusercontent.com/109578927/211851407-742f6fc0-914d-4124-94c7-62b099983c1e.png)

- '결제하기' 버튼 클릭 시, PG결제 API 동작
- 이용권이 없는 경우, 이용권 등록
- 이용권이 있는 경우, 이용권 연장



### 13. 쿠폰 (무료 이용)


![쿠폰](https://user-images.githubusercontent.com/109578927/211850895-27c56faf-8961-4fbd-a876-6d4d738e9a7e.png)

- 대문자 / 숫자 정규식 적용
- 소문자 입력 시, 대문자로 치환
- 영문 / 숫자 외 입력 치 공백으로 치



### 14. 알림


![알림](https://user-images.githubusercontent.com/109578927/211850303-7ea45f7b-77fc-4e9c-9468-684a6114c2a4.png)

- [ 공지사항 / 이용권 구매 / 이용권 선물 / 비밀번호 3개월 전 변경 ] 일 때 알림 메시지 전송


