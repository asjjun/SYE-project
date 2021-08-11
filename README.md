# SYE-project
<p align="center">
<img width="500px" src="https://user-images.githubusercontent.com/29851990/116390494-0c7ce700-a859-11eb-8b62-70d5cb49a922.PNG">
<br><br><br>
<img src="https://img.shields.io/badge/license-mit-green">
<img src="https://img.shields.io/github/issues/hongjin4790/SYE-project">
<img src="https://img.shields.io/badge/tag-v1.0.0-blue">
<br>
<img src="https://img.shields.io/badge/Android Studio-3DDC84?style=flat-square&logo=Android&logoColor=white"/>
<img src="https://img.shields.io/badge/Java-007396?style=flat-square&logo=Java&logoColor=white"/>
<img src="https://img.shields.io/badge/MySQL-4479A1?style=flat-square&logo=MySQL&logoColor=white"/>
<br>
<img src="https://img.shields.io/badge/Eclipse-2C2255?style=flat-square&logo=Eclipse&logoColor=white"/>
<img src="https://img.shields.io/badge/Tomcat9.0-F8DC75?style=flat-square&logo=Apache-Tomcat&logoColor=black"/>
<img src="https://img.shields.io/badge/AWS-491F59?style=flat-square&logo=Amazon-AWS&logoColor=white"/>
<img src="https://img.shields.io/badge/BlockChain-121D33?style=flat-square&logo=Bitcoin-SV&logoColor=white"/>
<br>
</p>

<!-- TABLE OF CONTENTS -->
<details open="open">
  <summary>Table of Contents</summary>
  <ol>
    <li>
      <a href="#프로젝트-소개">프로젝트 소개</a>
      <ul>
        <li><a href="#환경-설정">환경 설정</a></li>
      </ul>
    </li>
    <li>
      <a href="#본문-내용">본문 내용</a>
    </li>
    <li><a href="#usage">Usage</a></li>
    <li><a href="#roadmap">Roadmap</a></li>
    <li><a href="#contributing">Contributing</a></li>
    <li><a href="#license">License</a></li>
    <li><a href="#contact">Contact</a></li>
    <li><a href="#acknowledgements">Acknowledgements</a></li>
     <li><a href="https://www.youtube.com/watch?v=eYo_AbOF7cA">유튜브 링크</a></li>
  </ol>
</details>


## 프로젝트 소개
 > SYE는 ShareYourEnvironment 환경문제를 공유하고, 직접 실천과 지원을하고, 공공거래장부를 통해 서로를 보안하는 어플입니다.
 
## 환경 설정
     Android Studio 4.0.1 
       - API level 30 
       - 에물레이터 : Nexus 5X 
       - JDK version: 15

     Eclipse (Java EE IDE)
       - Version: 2020-09 (4.17.0)
       - Tomcat : v9.0
       - 상단의 Project -> Properity -> Web Project Setting -> "/" 입력
       - JDK Path 설정
       - UserDAO.java에서 본인의 dbID와 dbPassword 입력
       - 3306포트 이용 / 원한다면 변경가능

     Mysql : 8.0.21
       - SYE_query.sql 실행

     환경설정
      - 안드로이드 폴더 경로에 non-ASCII 로 들어가 있는 문자 경로 금지.
      - 서버와 DB를 실행 후 안드로이드 실행해야 정상적인 실행
      - 서버는 UI.jsp에서 실행
      - 안드로이드의 MyService.java에 들어가 public static final String hostname에 본인의 ip주소 입력 (또는 에뮬레이터 실행일 경우 10.0.2.2도 가능)
      - (중요!!) 나가기 버튼은 서버에서 나가는 버튼입니다. 모든 시현동작 실행 전까지 누르지 말아주세요

     추가사항
      - (오픈소스) AWS를 통해 사진 업로드와 불러오는 과정의 코드 가져옴
      - (오류) 가끔 AWS의 고유문제로 사진이 안불러와질 때가 있음

## 본문 내용 


우리 주변에서는 다양한 환경문제가 일어나고 있다. 그 예로는 비 때문에 수문을 급하게 개방했고, 그로인해 댐안에 살고있던 민물고기들이 댐 밖으로 밀려나 바다의 염분에 적응하지 못해 집단 폐사한 사건과 갯벌에 타이어를 무단폐기했고 그로인해 타이어가 은신처인줄알고 들어온 민물의 게들이 물이 빠지고 난후 타이어에 갇혀 다량으로 죽은 사건들이 있습니다.

앞서 이야기한 환경문제와 비슷한 일들을 보면서 우리는 “왜 이런일을 빠르게 공유받지 못했을까” 더 나아가 “사람들은 이와 같은 사소한 환경문제에 대해 어떤 인식을 가질까” 라는 의문이 들었습니다.

해당 의문에 대해 찾아 조사 해보니 “우리가 날씨다” 라는 책에는 접할 수 있었고, 책에서는 사람들이 환경문제가 심각하다는 것은 알고는 있지만 상당수가 추상적이고 멀다고 생각하는 경향이 있다고 하였습니다.
하지만, 실제로 우리 생활에 밀접해 있는 사소한 환경문제는 정부나 기관 차원이 아닌 개개인이 나서서 해결할 수 있는 것이 많다는 것을 알게 되었고, 현대인들이 친숙한 sns형식을 통해 주변의 문제를 공유해 해결해나가고자 합니다.

SYE의 대표적인 구성은 총 3단계로 구분이 되는데 환경문제를 공유하고, 직접 실천과 지원을하고, 공공거래장부를 통해 서로를 보안합니다.



=====================================================

화면에 보이는 것과 같이 사소한 환경문제를 해결해 나가고 있는 사람들도 있고, 그런사람들과 함께하고 응원하는 사람들도 있습니다. 그래서 봉사를 계획 & 모집하고  그 사람들에게 지원 할 수 있는 기능을 구현하였습니다.
<br>

<h3> ↓↓↓뉴스피드↓↓↓</h3>

<img width="30%" height="30%" src="https://user-images.githubusercontent.com/29851704/116550245-7bc60a00-a931-11eb-945d-abaea16fd4cd.gif"/>
  

<h3> ↓↓↓충전 및 펀딩↓↓↓</h3>

 
<img width="30%" height="30%" src="https://user-images.githubusercontent.com/29851704/116551399-dd3aa880-a932-11eb-9e05-dd149f463e40.gif"/>

<h3> ↓↓↓투표↓↓↓</h3>

  
<img width="30%" height="30%" src="https://user-images.githubusercontent.com/29851704/116551383-d90e8b00-a932-11eb-9554-a716457a1348.gif"/>

  
<h3> ↓↓↓뉴스피드↓↓↓</h3>
  
<img width="30%" height="30%" src="https://user-images.githubusercontent.com/29851704/116806274-dd22ee80-ab66-11eb-9a66-891f230d4abb.gif"/>

  
<h3> ↓↓↓내부데이터를 통한 블록체인 성능확인↓↓↓</h3>
  
<img width="70%" height="70%" src="https://user-images.githubusercontent.com/29851704/116806272-da27fe00-ab66-11eb-8fcc-3bf768c539cf.gif"/>

  --------------------------------------------------------------------------------------------------------
저희 어플은 가상화폐를 사용하므로 보안에 위험이 있다고 생각되어 공공거래장부릍 통해 서로가 서로를 지킬 수 있는 기능을 구현하였습니다.
즉, 블록체인 기술을 사용하였는데 밑의 그림의 일련의 과정을 코드로구현하였습니다.
저희가 모방한 블록체인기술은 환경의 제한상 pow나 pos과 같은 합의알고리즘을 따로구현하지않았습니다.
  
<h3> ↓↓↓블록체인 일련의 과정↓↓↓</h3>
  

![움짤](https://user-images.githubusercontent.com/29851704/129036553-9614984d-8f4a-4f90-9514-2ae89ba73151.gif)

## 기대 효과 

첫 번째로는 환경문제에 다지식해진다는 것이 있습니다. 문제를 공유하면서 모르고 행했던 환경에 대한 실수들을 고쳐나갈수 있습니다. 
두 번째로는 심각성이 낮아진다는 것입니다. 점차적으로 사람들의 인식과 직접나서는 문화가 형성이 되면 그만큼 환경문제에 대한 심각성은 낮아질 수 있습니다. <br>
세 번째로는 접근성이 좋아진다는 것입니다. 정부나 기관에서 신경 쓰지못할만한 세세한 작업들을 국민이 직접 해결할 수 있습니다. 


