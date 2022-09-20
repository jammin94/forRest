# :evergreen_tree: forRest

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

#### `Back-end`

* java 8
* Spring Boot 2.6.8
* Gradle
* MyBatis
* MariaDB 10.5.9
* Spring Security

#### `Front-end`

* HTML
* CSS
* Javascript
* JQuery
* Thymeleaf
* ajax

<br>

## 3. ERD 설계

<img src="https://user-images.githubusercontent.com/83762364/189522608-b4397e0b-9c58-4e04-9949-2692a4df8439.png" width="900" height="600"/>

### Product, Transaction 테이블 설계

 * 초기 설계 단계에선 Product(물품), Storage(보관), Rental(대여) 세 개의 테이블이 존재했습니다.
 * 정규화 과정에서  Storage와 Rental 테이블에서 중복되는 칼럼이 많다는 것을 확인했습니다.
 * Storage와 Rental 테이블을 Transaction 테이블로 통합하고 tranCode 칼럼을 통해 보관에 관한 것인지 대여에 관한 것인지 구분하도록 변경했습니다.


<br>

## 4. 담당 핵심 기능

이 서비스의 핵심 기능은 캠핑 장비를 보관함과 동시에 렌탈을 통해 수익을 창출하는 것입니다.

사용자가 장비를 보관할 때 렌탈 가능에 체크하면 보관 기간 동안 다른 사용자에게 장비를 대여해 줄 수 있습니다.

이렇게 보관한 장비는 listStorage(내가 보관 중인 장비), listProduct(사용자들이 대여 가능한 물품)에 함께 등록됩니다.

### 전체 흐름

![image](https://user-images.githubusercontent.com/83762364/190890614-f1c76688-58f7-4b87-8eb5-72d9d45ba902.png)

### 4-1. Client

![image](https://user-images.githubusercontent.com/83762364/191182681-7de0d236-0a8f-48d6-8946-941909f25495.png)

* **결제 정보 검증을 위한 비동기 요청**
  * 검증을 위해 정의한 URL 형식으로 비동기 요청을 보냅니다.
  * 요청을 받은 RestController에서 imp_uid(거래 고유번호)를 검사하고 데이터를 응답해 줍니다.
  
* **물품 등록을 위한 POST 요청**
  * paid_amount와 amount의 금액이 같으면 물품 등록을 위해 폼데이터를 POST로 전송합니다.

<br>

### 4-2. Controller

![image](https://user-images.githubusercontent.com/83762364/191183566-8a4e5a91-3fa6-4be9-90f3-77b6a7d76e16.png)

* **결제 정보 검증**
  * 아임포트 라이브러리에서 제공하는 paymentByImpUid 메서드를 사용하여 결제 정보를 검증했습니다.
  * 검증에 사용되는 토큰을 발급하기 위해 필요한 API 키, API secret을 생성자의 매개변수로 지정했습니다.
  * API 키, API secret이 직접 노출되는 것을 피하기 위해 yml 파일에 저장하고 불러오도록 했습니다.

* **요청 처리**
  * 고유한 식별자를 위해 서버에서 UUID를 통해 ProdNo를 생성했습니다.
  * 등록한 이미지들을 별도의 이미지 테이블에 저장하고 Product, Transaction 테이블에 저장할 대표 이미지를 리턴하도록 했습니다.
  * setParam 메서드를 정의해 등록에 필요한 값들이 포함된 객체를 리턴하도록 했습니다.
  * 등록을 위해 ProductService의 addProduct, StorageService의 addStorage를 호출했습니다.

* **리다이렉트**
  * 중복 등록을 방지하기 위하여 결제 완료 후 상세조회 페이지로 리다이렉트 하도록 설계했습니다.
  * RedirectAttributes를 사용하여 pathVarible과 임의의 상태 코드를 지정했습니다.

<br>

### 4-3. Service

![image](https://user-images.githubusercontent.com/83762364/191060884-1b8e4801-3d14-45f3-83f1-a3bfee836c3c.png)

* **DAO 호출**
  * DB에 접근하기 위한 객체인 DAO를 호출합니다.

<br>

### 4-4. DAO

![image](https://user-images.githubusercontent.com/83762364/191068491-6ed5ba0d-37e1-4176-af49-d9d4331a20c9.png)

* **물품 정보 저장(MyBatis Mapper)**
  * @Mapper를 통해 인터페이스를 매퍼로 등록했습니다.
  * INSERT 문을 작성할 때 DATE_ADD() 함수로 보관 기간과 현재 날짜를 더하여 보관 종료일을 계산하고 이를 DB에 저장하도록 했습니다.

<br>

## 5. 리팩토링

### 5-1. PaymentController

#### `REST API에 맞는 URL과 파라미터 적용`

<details>
<summary><b>상세 보기</b></summary>
<div markdown="1">

<br>

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
* 기존 코드에는 결제와 환불 메서드에 검색 필터에 주로 사용되는 쿼리 스트링을 사용하여 @RequestParam을 통해 값을 받도록 설계했습니다.


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
@PostMapping("/{imp_uid}")
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

* REST API의 규칙을 토대로 Post 방식을 사용하여 결제와 환불을 구현했습니다.
* Query String 대신 Path Variable을 사용하여 리소스를 식별하도록 변경했습니다.
* 환불 메서드는 Post를 사용하기 때문에 Query String을 사용하지 않고 @RequestBody를 통해 http body에 있는 값을 받도록 변경했습니다.

</div>
</details>

<br>

#### `api key 노출문제 개선`

<details>
<summary><b>상세 보기</b></summary>
<div markdown="1">

<br>

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

* PaymentController는 생성 시에 api_key와  api_secret을 파라미터로 갖는 IamportClient 객체의 인스턴스를 생성하고 필드를 초기화 시킵니다.
* 기존의 코드에는 외부에 노출되면 안 되는 api_key와  api_secret을 직접 입력했습니다.

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
* api_key, api_secret을 필드에 변수로 선언하고 @Value를 사용하면 PaymentController가 생성될 때 두 값이 null이 됩니다. <br>
  따라서 PaymentController 생성자의 매개변수에서 @Value을 통해 두 값을 가져오도록 변경했습니다.
  
</div>
</details>
  
 <br>
 
### 5-2. StorageController
 
#### `새로고침 시 FormData 재전송으로 인한 오류 개선 (PRG 패턴 사용)`

<details>
<summary><b>상세 보기</b></summary>
<div markdown="1">

<br>
 
<details>
<summary><b>기존 코드</b></summary>
<div markdown="1">

```java
@PostMapping("addStorage")
public String addStoragePost(@ModelAttribute("product") Product product,
			     @ModelAttribute("storage") Storage storage,
			     @ModelAttribute("ownCoupon") OwnCoupon ownCoupon,
			     @RequestParam("uploadFile") List<MultipartFile> uploadFile,
			     @RequestParam("paymentNo") String paymentNo,
			     Model model) throws Exception {
			     
  // 물품등록을 위한 로직 수행

  model.addAttribute("storage", storage);
  
  // 물품등록 후 상세조회 페이지로 리턴
  return "forward:/storage/getStorage";
``` 
</div>
</details>

* 기존에는 물품을 등록한 뒤 상세조회를 요청하는 URL을 forward 방식으로 호출했습니다.
* 최초 요청 시에는 상세조회 페이지가 출력되었지만 새로고침 시 서버 에러가 발생했습니다.
* forward는 클라이언트에서 URL이 변경되지 않기 때문에 새로고침 시 상세조회 페이지가 아닌 물품 등록 URL이 요청되었습니다. <br>
  이때 FormData가 재전송 되면서 물품등록을 위한 로직을 수행 중 이미지 이름 중복 때문에 에러가 발생하는 것을 확인했습니다.
 
 <br>
 
<details>
<summary><b>개선된 코드</b></summary>
<div markdown="1">

```java
@PostMapping("{paymentNo}/add")
public String addStoragePost(@ModelAttribute("product") Product product,
			     @ModelAttribute("storage") Storage storage,
			     @ModelAttribute("ownCoupon") OwnCoupon ownCoupon,
			     @RequestParam("uploadFile") List<MultipartFile> uploadFile,
			     @PathVariable("paymentNo") String paymentNo,
			     RedirectAttributes redirectAttributes) throws Exception {
			     
  // 물품등록을 위한 로직 수행

  redirectAttributes.addAttribute("tranNo", storage.getTranNo());
  redirectAttributes.addAttribute("status", true);
		
  return "redirect:/storage/{tranNo}"; 
```
</div>
</details>

* redirect로 새로고침 시 상세조회 URL이 호출되도록 변경했습니다.
* redirectAttributes를 사용하여 pathVarible을 설정하고 쿼리 스트링으로 간단한 상태 코드를 추가했습니다.

</div>
</details>
 
<br>
 
#### `필드 주입에서 생성자 주입으로 변경`

<details>
<summary><b>상세 보기</b></summary>
<div markdown="1">

<br>

<details>
<summary><b>기존 코드</b></summary>
<div markdown="1">
 
 ```java
@Controller
@RequestMapping("/storage/*")
public class StorageController {
	
   @Autowired
   public StorageService storageService ;
	
   @Autowired
   public ProductService productService ;
	
   @Autowired
   public UserService userService;
	
   @Autowired
   public CouponService couponService;
	
   @Autowired
   public FileUtils fileUtils;
 ```
</div>
</details>
 
 * 기존에는 코드의 간결함 때문에 의존관계에 있는 클래스들을 필드 주입을 통해 사용했습니다.
 * 하지만 간단한 테스트 코드를 작성할 때도 의존관계에 있는 클래스를 사용하기 위해서 서버를 실행해야 해서 비효율적이었습니다.
 
 <br>

<details>
<summary><b>개선된 코드</b></summary>
<div markdown="1">
 
 ```java
@Controller
@RequestMapping("/storage/*")
@RequiredArgsConstructor
public class StorageController {
		
   private final StorageService storageService ;
	
   private final ProductService productService ;
	
   private final UserService userService;
	
   private final CouponService couponService;
	
   private final FileUtils fileUtils;
 ```
</div>
</details>

 * 위의 문제점을 해결하고 의존관계를 불변하게 설계할 수 있는 생성자 주입으로 변경했습니다.
 * 또한 롬복의 @RequiredArgsConstructor를 통해 새로운 필드를 추가할 때 다시 생성자를 만들어서 관리해야 하는 번거로움을 해결했습니다.
 
</div>
</details>
 
 

<br>

## 6. 발표 영상 및 프로젝트 보고서

### 6-1. 발표 영상

[![imamge](https://user-images.githubusercontent.com/83762364/188839247-4079e5b1-3979-47b8-ae37-753f1cd64937.png)](https://youtu.be/24TOhOPyFDk?t=204) 

<br>

### 6-2. 프로젝트 보고서

[forRest 프로젝트 보고서.pdf](https://drive.google.com/file/d/1lcodOBAqL4omjVfrSmB6bWeIqKXgnOyb/view?usp=sharing)






