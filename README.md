# forRest

**캠핑 장비 중고거래, 보관 및 대여를 제공하는 통합 플랫폼**

요구사항 정의부터 ERD 설계, 구현까지 경험한 Spring Boot 기반 Java Web 프로젝트입니다.
<br>

부피가 큰 캠핑 용품의 보관 문제를 해결하기 위해 캠핑 장비를 보관할 수 있는 장소를 제공하고

보관한 장비를 대여 가능하게 하여 수익을 창출할 수 있습니다.

<br>

## 제작 기간 & 참여인원

* 2022.05.01 ~ 2022.06.30
* 팀프로젝트: 6명

<br>

## 사용 기술

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

## ERD 설계

<img src="https://user-images.githubusercontent.com/83762364/189522608-b4397e0b-9c58-4e04-9949-2692a4df8439.png" width="900" height="600"/>


<br>

## 담당 핵심 기능

이 서비스의 핵심 기능은 캠핑 장비를 보관함과 동시에 렌탈을 통해 수익을 창출하는 것입니다.

사용자가 장비를 보관할 때 렌탈을 가능하게 하면 렌탈 물품 리스트에 장비가 자동으로 등록됩니다.

서비스흐름 사진


#### 등록(DB)
#### 결제(rest controller)
#### 검색(product)

<br>

## 리팩토링

### 1. PaymentController

<br>

**1-1. REST API에 맞는 URL과 파라미터 적용**

<details>
<summary><b>기존 코드</b></summary>
<div markdown="1">

```java

@RestController
@RequestMapping("/payment")
public class PaymentController {

// ... 생략

// 결제
@RequestMapping("/json/verifyIamport")
	public IamportResponse<Payment> paymentByImpUid(@RequestParam("imp_uid") String imp_uid) throws IamportResponseException, IOException{	
	
		return client.paymentByImpUid(imp_uid);		
 }

// 환불
@RequestMapping("/json/cancelIamport")
	public IamportResponse<Payment> cancelPaymentByImpUid(@RequestParam("imp_uid") String imp_uid) throws IamportResponseException, IOException {

		return client.cancelPaymentByImpUid(new CancelData(imp_uid, true));
	}

```

</div>
</details>

* 기존의 URL 경로는 리소스와 HTTP Method로 설계하는 REST API의 규칙을 따르지 않아 가독성이 떨어졌습니다.
* 기존코드에는 결제와 환불 메소드에 검색필터에 주로 사용되는 쿼리스트링을 사용하여 @RequestParam을 통해 값을 받도록 설계했습니다.


<br>

<details>
<summary><b>개선된 코드</b></summary>
<div markdown="1">

```java

@RestController
@RequestMapping("/payment")
public class PaymentController {

// ... 생략

// 결제
@GetMapping("/{imp_uid}")
	public IamportResponse<Payment> paymentByImpUid(@PathVariable("imp_uid") String imp_uid) throws IamportResponseException, IOException{	
		
		return client.paymentByImpUid(imp_uid);		
}

// 환불
@PostMapping("/cancel")
	public IamportResponse<Payment> cancelPaymentByImpUid(@RequestBody String imp_uid) throws IamportResponseException, IOException {
  
		return client.cancelPaymentByImpUid(new CancelData(imp_uid, true));
	}

```

</div>
</details>

* iamport에서 정의한 URL규칙에 따라 리소스를 정의하고 Get과 Post 방식을 사용하여 결제와 환불을 구현했습니다.
* Query String 대신 Path Variable을 사용하여 리소스를 식별하도록 변경했습니다.
* 환불 메소드는 Post를 사용하기 때문에 Query String을 사용하지 않고 @RequestBody를 통해 http body에 있는 값을 받도록 변경했습니다.


<br>

**1-2. api key 노출문제 개선**

<details>
<summary><b>기존 코드</b></summary>
<div markdown="1">

```java
private IamportClient api;

public PaymentController(){
	this.api = new IamportClient(실제 api_key, 실제 api_secret);
}
```
</div>
</details>

* PaymentController는 생성시에 api_key와  api_secret을 파라미터로 갖는 IamportClient객체의 인스턴스를 생성하고 필드를 초기화 시킵니다.
* 기존의 코드에는 외부에 노출되면 안되는 api_key와  api_secret을 직접 입력했습니다.

<br>

<details>
<summary><b>개선된 코드</b></summary>
<div markdown="1">

<br>

**application.yml에 api_key와 api_secret 저장**

```java
iamportApi:
  api_key: 실제 api_key
  api_secret: 실제 api_secret
```

<br>

```java
private IamportClient client;

public PaymentController(@Value("${iamportApi.api_key}") String api_key, @Value("${iamportApi.api_secret}") String api_secret){
	this.client = new IamportClient(api_key, api_secret);
}
```

</div>
</details>

* api_key와  api_secret을 application.yml에 저장하고 @Value를 통해 값을 가져왔습니다.
* api_key, api_secret을 필드에 변수로 선언하고 @Value를 사용하면 PaymentController가 생성될때 두 값이 null이 됩니다. <br>
  따라서 PaymentController 생성자의 매개변수에서 @Value을 통해 두 값을 가져오도록 변경했습니다.
  


<br>

## 발표 영상 및 프로젝트 보고서

#### 발표 영상

[![imamge](https://user-images.githubusercontent.com/83762364/188839247-4079e5b1-3979-47b8-ae37-753f1cd64937.png)](https://youtu.be/24TOhOPyFDk?t=204) 

<br>

#### 프로젝트 보고서

[forRest 프로젝트 보고서.pdf](https://drive.google.com/file/d/1lcodOBAqL4omjVfrSmB6bWeIqKXgnOyb/view?usp=sharing)






