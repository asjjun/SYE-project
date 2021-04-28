DROP table if EXISTS reply;
drop table IF EXISTS rootfundingtbl;
drop table if EXISTS boardtbl;
drop table if EXISTS votetbl;
drop table IF EXISTS reportvolunteer;
drop table IF EXISTS volunteerplan;
drop table if EXISTS usertbl;

create table usertbl
(
usercode VARCHAR(11)  not null,
userID    char(15) NOT NULL PRIMARY KEY,
userPW  varchar(200) not null,
name      char(10) not null,
usergender char(2) not null,
birthYear varchar(8) not null, 
addr   varchar(40) not null,
mobile char(11) not null,
usermoney int ,
userfavorite varchar(100)
            -- 가입일
);

drop table if EXISTS user_seq;
create table user_seq
(
   `id` int  auto_increment not null primary key
    
);
DROP TRIGGER IF EXISTS user_insert_seq;
DELIMITER $$
CREATE TRIGGER user_insert_seq
BEFORE INSERT ON usertbl
FOR EACH ROW
BEGIN
  INSERT INTO user_seq VALUES (NULL);
  SET NEW.usercode = CONCAT('USER', LPAD(LAST_INSERT_ID(), 4, '0'));
END$$
DELIMITER ;

insert into usertbl values (null,'hongjin4790','96cae35ce8a9b0244178bf28e4966c2ce1b8385723a96a6b838858cdd6ca0a1e', '김홍진','남성','1998','서울','01086464790',0,null);
insert into usertbl values ( null,'hyungseok','96cae35ce8a9b0244178bf28e4966c2ce1b8385723a96a6b838858cdd6ca0a1e', '차형석','남성','1994','서울','01083254123',2000,null);
insert into usertbl values ( null,'lovelyjina','96cae35ce8a9b0244178bf28e4966c2ce1b8385723a96a6b838858cdd6ca0a1e', '강지나','여성','1988','대구','01086521234',30000,null);
insert into usertbl values ( null,'hongdroid','96cae35ce8a9b0244178bf28e4966c2ce1b8385723a96a6b838858cdd6ca0a1e', '홍예지','여성','1978','충남','01098584121',500,null);
insert into usertbl values ( null,'sejun4624','96cae35ce8a9b0244178bf28e4966c2ce1b8385723a96a6b838858cdd6ca0a1e', '안세준','남성','1990','인천','01066874213',10000,null);
insert into usertbl values ( null,'hyuni00','96cae35ce8a9b0244178bf28e4966c2ce1b8385723a96a6b838858cdd6ca0a1e', '이현이','여성','1989','부산','01066874213',10000,null);
insert into usertbl values ( null,'jiyoung2','96cae35ce8a9b0244178bf28e4966c2ce1b8385723a96a6b838858cdd6ca0a1e', '박지영','여성','1999','부산','01022357843',10000,null);




create table boardtbl
(   num   int auto_increment  not null primary key, -- 순번(pk) 순번 1부터 자동생성
   userID char(15) not null,
    boardtitle VARCHAR(50)  not null COMMENT'게시글 제목',
    boardcontent VARCHAR(5000) not null COMMENT'게시글 내용',
    board_imgurl varchar(500)   comment '이미지url',
   FOREIGN KEY (userID) references usertbl(userID)
    )DEFAULT CHARSET=utf8mb4;
    
    
insert into boardtbl values (null,'hongjin4790','연어떼에게 펼쳐진 충격적인 현실','이글은 우리나라 하천으로 회귀하는 연어들의 산란을 도와주기 위해 공사를 한 일이 오히려 독이된 케이스다','20201121_012436147.jpg');
insert into boardtbl values (null,'hyungseok','죽음을 기다리는 무덤','갈수록 시기때에 많은 생명의 불씨가 꺼집니다. 여름에는 가뭄으로 인해, 겨울에는 결빙에 의하여 수위가 낮은 물에 갇혀 죽는 생물이 많다. 우리나라 어도가 잘못만들어져서 수문을 열어 놓으면 제기능을 못하고 말라버린다. 원래라면 물이 계속 흘러야 되는데 설계미스로 물이흐르지않는다.','20201121_014741576.jpg');
insert into boardtbl values (null,'lovelyjina','멸종위기종 맹꽁이의 참혹한 현장','멸종위기종 맹꽁이가 길가에서 겨우 턱 하나 넘지 못하고 말라죽고 밟혀죽고 있습니다. 물론 운좋게 턱이 낮거나 벽을타서 올라갈수 있는 개체들도 있지만 하수구에 빠져서 영영 못나오고 썩은물에서 죽어가는걸 보니 마음이 좋지않습니다.','20201121_015612085.jpg');
insert into boardtbl values (null,'hongdroid','농수로에 갇힌 생물들','수로를 보면 개구리만 죽어있는게 아닙니다. 개구리부터 시작해서 두더지, 쥐, 뱀 아주 다양한 생물들이 빠져 죽습니다. 개구리들은 가끔씩 벽을 타고 올라가서 살아남는 일도 있지만 두더지, 쥐는 빠지면 무조건 죽습니다. 뱀같은 경우에도 저 수로에 빠져서 계속 물타고 뱅글뱅글 돌다가 결국에는 죽습니다. 어쩔때 가보면 초등학생들이 수로 안에 있는 뱀을 
죽일려고 돌을 수로로 던지는데 아주 충격적이였습니다 작은 생명이라도 소중하게 생각하는 지성인이됩시다.','20201121_020430939.jpg');
insert into boardtbl values(null,'sejun4624','민물고기 지옥이된 바닷가','댐의 수문을 비가와서 갑자기열다보니 민물고기들이 바다로 떠밀려와서 바닷물에 적응하지못해 다 죽어가고있다.','20201121_012113658.jpg');
insert into boardtbl values(null,'hyuni00','공사장인근의 물고기 무덤','극심한 가뭄에 의해 공사장인근 웅덩이에 모여살던 물고기들이 빠져나가지 못하고 갇혀 죽음을 기다리고 있는 상황이다.','20201121_014251951.jpg');
insert into boardtbl values (null,'hyungseok','연어떼에게 펼쳐진 충격적인 현실','이글은 우리나라 하천으로 회귀하는 연어들의 산란을 도와주기 위해 공사를 한 일이 오히려 독이된 케이스다','20201121_012436147.jpg');
insert into boardtbl values (null,'lovelyjina','수백마리 게들의 죽음','갯벌에 타이어가 박혀있다. 갯벌에 물이 차오를때 이 타이어는 게들에게 좋은 은신처가 되어 게들이 많이 몰렸다. 하지만 물이 없어지고 난후 이 타이어는 게들에게 감옥이 되어 빠져나가지도 못해 그대로 다 죽었다. 갯벌에 타이어같은건 함부로 버리지 말았으면 좋겠다.','20201121_014725380.jpg');
insert into boardtbl values (null,'hongjin4790','죽음을 기다리는 무덤','갈수록 시기때에 많은 생명의 불씨가 꺼집니다. 여름에는 가뭄으로 인해, 겨울에는 결빙에 의하여 수위가 낮은 물에 갇혀 죽는 생물이 많다. 우리나라 어도가 잘못만들어져서 수문을 열어 놓으면 제기능을 못하고 말라버린다. 원래라면 물이 계속 흘러야 되는데 설계미스로 물이흐르지않는다.','20201121_014741576.jpg');


CREATE TABLE reply
(
   num int auto_increment not null primary key,
     primarykey int not null,
    userid char(15) not null,
    replycontent varchar(2000) not null,
    WRI_DTT datetime ,
   FOREIGN KEY (userID) references usertbl(userID),
     FOREIGN KEY (primarykey) references boardtbl(num)
)DEFAULT CHARSET=utf8mb4;

insert into reply values(null,9,"hyungseok","새로운 사실 알아가네요~",now()),
                  (null,8,"hongjin4790","좋아요~~",now()), (null,8,"hyuni00","좋은글 감사합니다~~",now()), (null,8,"lovelyjina","새로운사실이네요!!",now()),
                  (null,7,"sejun4624","궁금하네요~",now()),(null,7,"hongdroid","이런 사실들을 알아보면서 상황이 심각하다는것을 느끼게해줬어요",now()),
                   (null,6,"hongjin4790","추천!!!",now());



create table volunteerplan
 (
   num int auto_increment primary key,
    userid char (15) not null,
   vol_date char(20) not null,
    vol_time char(20) not null,
    place char(25) not null,
    vol_goal varchar(50) not null,
    activity_content varchar(300) not null,
    vol_prepare char(20) not null,
   etc char (15) ,
   FOREIGN KEY (userID) references usertbl(userID)

)DEFAULT CHARSET=utf8mb4;
insert into volunteerplan values (null,'hyungseok','2020-11-21','12:00~18:00','경기도 용인시 기흥구 동백동 한숲공원','열대어 구피 구하기','애완용으로 키우기위해 구입한 열대어들을 누군가 공원에 방생시켜 생명력이 강한 열대어들이 살아간다. 문제는 열대어들이 번식력이 강하여 가을정도에 공원을 뒤덮을 정도로 많아지는데 그 상태로 겨울이 되면 구피들은 우리나라의 온도를 적응하지 못해 몇만마리가 되는 구피들이 다 몰살당한다. 이들을 잡아서 구하려한다','여벌옷,장갑,뜰채,바구니','없음');
insert into volunteerplan values (null,'hyuni00','2020-11-23','13:00~16:00','어스쉐어(서초구 내곡동)','자연의 경이로움을 전하고 환경의식을 높이기','흙의 소중함과 흙 되살리기 이해, 흙 생태감수성 놀이, 환경봉사활동(흙살리기 작업)','도시락,물,면장갑,마스크,편한복장','자원봉사 확인서 발급');
insert into volunteerplan values (null,'sejun4624','2020 12-27','13:00~16:00','경기도 평택시 진위천','고립된 물고기 구출','공사로 인해 주위 하천의 물이 갑자기 빠져 물고기들이 땅에서 메말라 죽어가고있습니다. 작은 생명이지만 약간의 노력만 있다면 많은 어종의 물고기를 구출하고 보존할 수 있습니다.','고무장화, 장갑, 편한 옷, 마스크','없음');




create table reportvolunteer
(
   
num int auto_increment primary key,
    userid char (15) not null,
   vol_date char(20) not null,
    vol_place char(30) not null,
    activity_content varchar(300) not null,
    vol_prepare_progress char(40) not null,
   image char(200) not null,
    plan_Key int ,
    progress int  not null,
    
   FOREIGN KEY (userID) references usertbl(userID),
   Foreign key (plan_Key) references volunteerplan(num)

)DEFAULT CHARSET=utf8mb4;

insert into reportvolunteer values (null,'hyungseok','2020-11-21','경기도 용인시 기흥구 동백동 한숲공원','겨울이 오기전에 열대어구하기','대략 두바가지 정도의 구피들을 구해냈습니다.','20201121_022615971.jpg',1,1);
insert into reportvolunteer values(null,'hyuni00','2020-11-24','어스쉐어(서초구 내곡동)','흙의 소중함과 흙 되살리기','흙의소중함을 알게되었다.','20201121_030119275.jpg','2',1);



create table votetbl
(
   num int AUTO_INCREMENT not null PRIMARY Key,
    primarykey int not null,
    userid CHAR(15) not null,
   status CHAR(10) not null,
    FOREIGN KEY (userID) references usertbl(userID),
    foreign key (primarykey) references reportvolunteer(num)
)DEFAULT CHARSET=utf8mb4;


create table rootfundingtbl
(
   num int AUTO_INCREMENT primary key,
    primarykey int not null,
    userid char(15) not null,
    fundingmoney int not null,
   foreign key (userid) references usertbl (userid),
    foreign key (primarykey) references volunteerplan (num)
    
);
