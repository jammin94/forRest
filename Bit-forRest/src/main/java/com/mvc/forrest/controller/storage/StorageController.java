package com.mvc.forrest.controller.storage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.mvc.forrest.common.utils.FileNameUtils;
import com.mvc.forrest.common.utils.FileUtils;
import com.mvc.forrest.config.auth.LoginUser;
import com.mvc.forrest.service.coupon.CouponService;
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
	
	@Autowired
	public CouponService couponService;
	
	@Autowired
	public FileUtils fileUtils;
	
	
	
	public StorageController() {
		System.out.println(this.getClass());
	}

	@Value("5")
	int pageUnit;
	
	@Value("10")
	int pageSize;
		
	
	//보관 메인화면 단순 네비게이션
	//비회원도 접근가능
	@GetMapping("storageMain")
	public String storageMain() throws Exception  {
		
		return "storage/storageMain";
	}
	
	//보관물품등록을 위한 페이지로 네비게이션
	//회원, 어드민 가능
	@GetMapping("addStorage")
	public String addStorageGet(Model model) throws Exception {
		
		//암호화된 유저아이디를 받아옴
		LoginUser loginUser= (LoginUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String userId= loginUser.getUser().getUserId();
		System.out.println("userId: "+userId);
		
		//회원의 보유쿠폰리스트를 받아옴
		Map<String,Object> map =couponService.getOwnCouponList(userId);
		
		model.addAttribute("list", map.get("list"));
		
		//디버깅
		System.out.println("쿠폰list:" + map.get("list"));
		
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
	//회원, 어드민 가능
	@PostMapping("addStorage")
	public String addStoragePost(@ModelAttribute("product") Product product,
												@ModelAttribute("storage") Storage storage,
												@RequestParam("uploadFile") List<MultipartFile> uploadFile,
												HttpSession session, Model model) throws Exception {
		
		
		
		System.out.println("product: "+product);
		System.out.println("uploadFile1: " + uploadFile.get(0).getOriginalFilename());
		//System.out.println("uploadFile2: " + uploadFile.get(1).getOriginalFilename());
		
		//암호화된 유저아이디를 받아옴
		LoginUser loginUser= (LoginUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String userId= loginUser.getUser().getUserId();

        
		//랜덤으로 생성한 prodNo(db에 들어가기전 prodNo가 필요)
          String prodNo = FileNameUtils.getRandomString();
        
        //랜덤으로 생성한 tranNo
           String tranNo = FileNameUtils.getRandomString();
        
		product.setUserId(userId);
		product.setProdNo(prodNo);
		productService.addProduct(product);
		
		//////////이미지업로드
	
		fileUtils.uploadFiles(uploadFile, prodNo, "product");
	
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
	
	//회원, 어드민 가능
	@RequestMapping("listStorage")
	public String listStorage(@ModelAttribute("search") Search search, HttpSession httpSession, Model model) throws Exception {
		
		if (search.getCurrentPage() == 0) {
			search.setCurrentPage(1);
		}
		
	
		search.setPageSize(pageSize);
		
		//암호화된 userId를 받아옴
		LoginUser loginUser= (LoginUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String userId= loginUser.getUser().getUserId();

		
		Map<String, Object> map = new HashMap<>();
		map.put("search", search);
		map.put("userId", userId);
		
		Map<String, Object> mapStorage = storageService.getStorageList(map);
		
		Page resultPage = new Page(search.getCurrentPage(), ((Integer)mapStorage.get("totalCount")).intValue(), pageUnit, pageSize );
		
		System.out.println("디버그 "+mapStorage.get("list"));
		
		model.addAttribute("list", mapStorage.get("list"));
		model.addAttribute("resultPage", resultPage);
		model.addAttribute("search", search);
		
		return "storage/listStorage";
	}
	
	
	//admin만 접근가능
	@RequestMapping("listStorageForAdmin")
	public String listStorageForAdmin(@ModelAttribute("search") Search search, Model model) throws Exception {
		
		if (search.getCurrentPage() == 0) {
			search.setCurrentPage(1);
		}
		
		//더좋은 방법이 있을듯
		//전체 보관물품을 볼때 SearchProductCondition을 null로 만들기위한코드
		if(search.getSearchProductCondition() == "") {
			search.setSearchProductCondition(null);
		}
		
		if(search.getSearchKeyword() == "") {
			search.setSearchKeyword(null);
		}
		
		if(search.getSearchCondition() == "") {
			search.setSearchCondition(null);
		}
		
		//디버깅
		System.out.println("serarch in StorageController:" + search);
		
		search.setPageSize(pageSize);
		
		Map<String, Object> map = storageService.getStorageListForAdmin(search);
		Page resultPage = new Page(search.getCurrentPage(), ((Integer)map.get("totalCount")).intValue(), pageUnit, pageSize );
		
	
		
		model.addAttribute("list", map.get("list"));
		model.addAttribute("resultPage", resultPage);
		model.addAttribute("search", search);
		
		return "storage/listStorageForAdmin";
	}
	
	//회원, 어드민 가능
	@GetMapping("extendStorage")
	public String extendStorageGet(@RequestParam("tranNo") String tranNo, Model model) throws Exception {
		
		model.addAttribute("storage", storageService.getStorage(tranNo));
		
		return "storage/extendStorage";
	}
	
	//보관물품의 기간을 연장
	//회원, 어드민 가능
//	@PostMapping("extendStorage")
//	public String extendStoragePost(@ModelAttribute("storage") Storage storage,
//													@RequestParam("imp_uid") String imp_uid, //아임포트에서 리턴해주는 번호
//													@RequestParam("merchant_uid") int merchant_uid, // 우리시스템의 tranNo
//													Model model) throws Exception {
//		
//		//기존에 보관한 물품에 변경되는 정보를 업데이트
//		storage.setPaymentNo(imp_uid);
//		storageService.updateStorage(storage);
//		
//		//업데이트된 보관물품정보를 테이블에 새로 추가
//		Storage storageExtended = storageService.getStorage(storage.getTranNo());
//		storageExtended.setTranNo(merchant_uid);
//		
//		storageService.addStorage(storageExtended);
//		
//		//기존에 보관한물품기록을 삭제
//		storageService.deleteStorage(storage.getTranNo());
//		
//		
//		return "storage/getStorage";
//	}
	
	//회원, 어드민 가능
	@RequestMapping("getStorage")
	public String getStorage(@RequestParam("tranNo") String tranNo, Model model) throws Exception {
		
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