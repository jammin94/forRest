package com.mvc.forrest.controller.storage;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.mvc.forrest.common.utils.RandomNumberGenerator;
import com.mvc.forrest.service.domain.Page;
import com.mvc.forrest.service.domain.Product;
import com.mvc.forrest.service.domain.Search;
import com.mvc.forrest.service.domain.Storage;
import com.mvc.forrest.service.domain.User;
import com.mvc.forrest.service.product.ProductService;
import com.mvc.forrest.service.storage.StorageService;
import com.mvc.forrest.service.user.UserService;





@Controller
@RequestMapping("/storage/*")
public class StorageController {
	
	@Autowired
	public StorageService storageService ;
	
	@Autowired
	public ProductService productService ;
	
	@Autowired
	public UserService userService;
	
	
	public RandomNumberGenerator rng;
	
	public StorageController() {
		System.out.println(this.getClass());
	}

	@Value("5")
	int pageUnit;
	
	@Value("10")
	int pageSize;
		
	
	//보관 메인화면 단순 네비게이션
	@GetMapping("storageMain")
	public String storageMain() throws Exception  {
		
		return "storage/storageMain";
	}
	
	//보관물품등록을 위한 페이지로 네비게이션
	@GetMapping("addStorage")
	public String addStorageGet(Model model, HttpSession session) throws Exception {
		
//		String userId = ((User) session.getAttribute("user")).getUserId();
//		model.addAttribute("user", userService.getUser(userId));
		
		return "storage/addStorage";
	}
	
	//결제정보를 리턴받고 물품과 보관테이블에 동시에 추가
//	@PostMapping("addStorage")
//	public String addStoragePost(@RequestParam("imp_uid") String imp_uid,   //아임포트에서 리턴해주는 번호
//												@RequestParam("merchant_uid") int merchant_uid, // 우리시스템의 tranNo
//												@ModelAttribute("product") Product product,
//												@ModelAttribute("storage") Storage storage,
//												HttpSession session, Model model) throws Exception {
//
//		product.setUserId(((User) session.getAttribute("user")).getUserId());
//		productService.addProduct(product);
//		
//		storage.setTranNo(merchant_uid);
//		storage.setPaymentNo(imp_uid);
//		
//		storageService.addStorage(storage);
//		
//		model.addAttribute("storage", storage);
//		
//		return "storage/getStorage";
//	}
	
	//결제구현전 테스트
	@PostMapping("addStorage")
	public String addStoragePost(@ModelAttribute("product") Product product,
												@ModelAttribute("storage") Storage storage,
												HttpSession session, Model model) throws Exception {
		
		
		
		//디버깅
		System.out.println("product: "+product);
		
		//세션에있는 유저아이디
        String userId = ((User) session.getAttribute("user")).getUserId();
        
		//랜덤으로 생성한 prodNo(db에 들어가기전 prodNo가 필요)
//        int prodNo = rng.makeRandomProductNumber();
//        System.out.println("랜덤prodNo: "+ prodNo);
           int prodNo = 14747;
        
        //랜덤으로 생성한 tranNo (TEST)
//        int tranNo = rng.makeRandomTransactionNumber();
//        System.out.println("랜덤tranNo: "+ tranNo);
           int tranNo = 147474;
        
		product.setUserId(userId);
		product.setProdNo(prodNo);
		productService.addProduct(product);
		
		//디버깅
		System.out.println("storage: "+storage);
		storage.setUserId(userId);
		storage.setProdNo(prodNo);
		storage.setTranNo(tranNo);
		storage.setPaymentNo("우하하 팡파레~");
		storageService.addStorage(storage);
		
		model.addAttribute("storage", storage);
		
		return "forward:/storage/getStorage?tranNo=" + tranNo ;
	}
	
	@RequestMapping("listStorage")
	public String listStorage(@ModelAttribute("search") Search search, HttpSession httpSession, Model model) throws Exception {
		
		if (search.getCurrentPage() == 0) {
			search.setCurrentPage(1);
		}
		
		search.setPageSize(pageSize);
				
		String userId = ((User)httpSession.getAttribute("user")).getUserId();
		
		Map<String, Object> map = new HashMap<>();
		map.put("search", search);
		map.put("userId", userId);
		
		Map<String, Object> mapStorage = storageService.getStorageList(map);
		
		Page resultPage = new Page(search.getCurrentPage(), ((Integer)mapStorage.get("totalCount")).intValue(), pageUnit, pageSize );
		
		//System.out.println("디버그 "+mapStorage.get("list"));
		
		model.addAttribute("list", mapStorage.get("list"));
		model.addAttribute("resultPage", resultPage);
		model.addAttribute("search", search);
		
		return "storage/listStorage";
	}
	
	@RequestMapping("listStorageForAdmin")
	public String listStorageForAdmin(@ModelAttribute("search") Search search, Model model) throws Exception {
		
		if (search.getCurrentPage() == 0) {
			search.setCurrentPage(1);
		}
		
		//전체 보관물품을 볼때 SearchProductCondition을 null로 만들기위한코드
		if(search.getSearchProductCondition() == "") {
			search.setSearchProductCondition(null);
		}
		
		//디버깅
		System.out.println("serarch:" + search);
		
		search.setPageSize(pageSize);
		
		Map<String, Object> map = storageService.getStorageListForAdmin(search);
		Page resultPage = new Page(search.getCurrentPage(), ((Integer)map.get("totalCount")).intValue(), pageUnit, pageSize );
		
	
		
		model.addAttribute("list", map.get("list"));
		model.addAttribute("resultPage", resultPage);
		model.addAttribute("search", search);
		
		return "storage/listStorageForAdmin";
	}
		
	@GetMapping("extendStorage")
	public String extendStorageGet(@RequestParam("tranNo") int tranNo, Model model) throws Exception {
		
		model.addAttribute("storage", storageService.getStorage(tranNo));
		
		return "storage/extendStorage";
	}
	
	//보관물품의 기간을 연장
	@PostMapping("extendStorage")
	public String extendStoragePost(@ModelAttribute("storage") Storage storage,
													@RequestParam("imp_uid") String imp_uid, //아임포트에서 리턴해주는 번호
													@RequestParam("merchant_uid") int merchant_uid, // 우리시스템의 tranNo
													Model model) throws Exception {
		
		//기존에 보관한 물품에 변경되는 정보를 업데이트
		storage.setPaymentNo(imp_uid);
		storageService.updateStorage(storage);
		
		//업데이트된 보관물품정보를 테이블에 새로 추가
		Storage storageExtended = storageService.getStorage(storage.getTranNo());
		storageExtended.setTranNo(merchant_uid);
		
		storageService.addStorage(storageExtended);
		
		//기존에 보관한물품기록을 삭제
		storageService.deleteStorage(storage.getTranNo());
		
		
		return "storage/getStorage";
	}
	
	@RequestMapping("getStorage")
	public String getStorage(@RequestParam("tranNo") int tranNo, Model model) throws Exception {
		
		model.addAttribute("storage", storageService.getStorage(tranNo));
	
		return "storage/getStorage";
	}
	
	//tranNo를 생성하기위한 메서드
//	public int createTranNoForStorage() throws Exception{
//	
//	Integer tranNo =	storageService.getMaxTranNoForStorage() + 1;
//	
//	if(tranNo != null) {
//		return tranNo + 1;
//	} else {
//		return 1;
//	}
//
//	}

}