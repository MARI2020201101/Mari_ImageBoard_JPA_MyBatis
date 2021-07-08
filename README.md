# Mari_ImageBoard_JPA_MyBatis
***
## 기능
- 회원제 게시판
- 이미지 갤러리 게시판
***
## project ver 01
[ver .01](https://https://github.com/MARI2020201101/Mari_Community_Board)
[ver .02](https://github.com/MARI2020201101/Mari_Board_JPA_MyBatis)
***
## 프로젝트 목적
- 파일 업로드, 삭제, 조회를 직접 구현하기 
- Spring data JPA를 활용하기
- 피드백을 받고 지속적으로 개선하기
- 제작부터 배포까지 전부 구현하기
## 신경 쓴 점
- Ajax를 이용한 다중 파일 업로드하기
- 위험 파일 확장자 및 크기체크를 Front-End 와 Back-End 양쪽에서 검사하여 보안 강화
- 업로드 날짜별로 폴더를 생성하여 속도 저하 방지
- UUID를 이용하여 중복된 파일 이름 처리
- 파일경로로 직접 보여주는 대신, HTTP메서드를 이용하여 이미지 파일을 보여주기
- 썸네일 이미지를 만들어 리스트 화면에서의 속도 개선
- 실제 배포 시 template의 경로를 못 찾는 에러 수정
- aws의 EC2, RDS를 활용하여 배포 실습
## 피드백 후 개선한 점
- 파일 이름 체크 시 : 브라우저별로 들어오는 파일이 다르므로 Back-End에서 한번 더 걸러주기
- 개발환경에서는 SQLite를, 운영환경에서는 MariaDB를 사용하도록 설정하여 개발 용이성 개선
- 개발환경과 배포환경에서 각각 다른 properties 파일로 동작할 수 있도록 개선
