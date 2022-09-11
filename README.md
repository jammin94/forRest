# forRest

**캠핑 장비 중고거래, 보관 및 대여를 제공하는 통합 플랫폼**

요구사항 정의부터 ERD 설계, 구현까지 경험한 Spring Boot 기반 Java Web 프로젝트입니다.
<br>

부피가 큰 캠핑 용품의 보관 문제를 해결하기 위해 캠핑 장비를 보관할 수 있는 장소를 제공하고

보관한 장비를 대여 가능하게 하여 수익을 창출할 수 있습니다.

<br>

## 1. 제작 기간 & 참여인원

* 2022.05.01 ~ 2022.06.30
* 팀프로젝트: 6명

<br>

## 2. 사용 기술

**Back-end**

* java 8
* Spring Boot 2.6.8
* Gradle
* MyBatis
* MariaDB
* Spring Security

**Front-end**

* HTML
* CSS
* Javascript
* JQuery
* Thymeleaf
* ajax

<br>

## 3. ERD 설계

<img src="https://user-images.githubusercontent.com/83762364/189522608-b4397e0b-9c58-4e04-9949-2692a4df8439.png" width="900" height="600"/>


<br>

## 4. 담당 핵심 기능

이 서비스의 핵심 기능은 캠핑 장비를 보관함과 동시에 렌탈을 통해 수익을 창출하는 것입니다.

사용자가 장비를 보관할 때 렌탈을 가능하게 하면 렌탈 물품 리스트에 장비가 자동으로 등록됩니다.

서비스흐름 사진


#### 등록(DB)
#### 결제(rest controller)
#### 검색(product)

<br>

## 5. 이슈 체크리스트

#### 비니지스로직 컨트롤러에서 서비스로 이동
#### 필드주입에서 생성자주입으로 변경
#### 하나의 메서드는 하나의 기능만 하도록 변경

<br>

## 6. 발표 영상 및 프로젝트 보고서

#### 발표 영상

[![imamge](https://user-images.githubusercontent.com/83762364/188839247-4079e5b1-3979-47b8-ae37-753f1cd64937.png)](https://youtu.be/24TOhOPyFDk?t=204) 

<br>

#### 프로젝트 보고서

[forRest 프로젝트 보고서.pdf](https://drive.google.com/file/d/1lcodOBAqL4omjVfrSmB6bWeIqKXgnOyb/view?usp=sharing)






