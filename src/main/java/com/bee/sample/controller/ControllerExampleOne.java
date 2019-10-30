package com.bee.sample.controller;


import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.format.datetime.DateFormatter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.MatrixVariable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.bee.sample.conf.ApplicationException;
import com.bee.sample.entity.Order;
import com.bee.sample.entity.OrderDetail;
import com.bee.sample.entity.OrderForm;
import com.bee.sample.entity.User;
import com.bee.sample.entity.WorkInfoForm;
import com.bee.sample.entity.WorkInfoFormGroup;
import com.bee.sample.entity.WorkOverTimeForm;

@Controller
@RequestMapping("/exampleone")
public class ControllerExampleOne {

	@RequestMapping("/m1")
	public String m1() {
		
		return "m1.html";
	}
	
	@RequestMapping("/m2")
	public @ResponseBody String m2() {
		return "i am a plain string(是)";
	}
	
	@RequestMapping(value="/m3",method=RequestMethod.GET,consumes="text/html"
			,produces="text/html",params="",headers="")
	@ResponseBody
	public String m3() {
		return "show requestmapping annotation params.";
	}
	
	@RequestMapping(value="m4/{id}")
	public @ResponseBody String m4(@PathVariable("id") String id) {
		
		return "从url路径中接受的参数:"+ id;
	}
	
	@RequestMapping("/m5/?")
	public @ResponseBody String m5() {
		return "通配符?_单个字符";
	}
	
	@RequestMapping("/m6/*")
	public @ResponseBody String m6() {
		return "通配符*_任意字符组合";
	}
	@RequestMapping("/m7/**/1")
	public @ResponseBody String m7() {
		return "通配符**_任意路径";
	}
	
	@RequestMapping(value="/m8", method=RequestMethod.POST)
	public @ResponseBody String m8() {
		return "i am a response for a post request";
	}
	
	@GetMapping("/m9")
	@ResponseBody
	public String m9() {
		return "i am a new annotation: @GetMapping";
	}
	
	@RequestMapping(value="/m10", consumes="text/html")
	@ResponseBody
	public String m10() {
		return "i use the property: consumes=text/html(Content-Type)";
	}
	
	@RequestMapping(value="/m11", produces="text/html")
	@ResponseBody
	public String m11() {
		return "i use the property: produces=text/html(Accept)";
	}
	
	@RequestMapping(value="/m12", params="action=save")
	@ResponseBody
	public String m12() {
		return "i use the property: params=save";
	}
	
	@RequestMapping(value="/m13", headers="action=update")
	@ResponseBody
	public String m13() {
		return "i use the property: headers=update";
	}
	
	@RequestMapping(value="/m14/{id}/{status}")
	@ResponseBody
	public String m14(@PathVariable("id") String id, @PathVariable("status") String status) {
		return "id="+id+";status="+status;
	}
	
	
	@RequestMapping("/m15/{param}")
	public @ResponseBody String m15(@MatrixVariable(pathVar="param",value="color")String[] yanse
			, @MatrixVariable(pathVar="param",value="size")String size){
		StringBuffer sb = new StringBuffer();
		for (String s : yanse) {
			sb.append(s);
			sb.append("/");
		}
		sb.append(size);
		return sb.toString();	
	}
	
	
	@RequestMapping("/m16")
	public String m16(Model model) {
		model.addAttribute("username", "model");
		return "m16.html";
	}
	
	@RequestMapping("/m17")
	public ModelAndView m17(ModelAndView mav) {
		mav.addObject("username", "modelandview");
		mav.setViewName("m17.html");
		return mav;
	}
	
	@RequestMapping("/m18")
	public @ResponseBody String m18(String id, String name){
		return "id="+id+";name="+name;
	}
	
	@RequestMapping("/m19")
	public @ResponseBody String m19(@RequestParam(name="id",required=true) String id, 
			@RequestParam(name="name",defaultValue="default")String name){
		return "id="+id+";name="+name;
	}
	
	@RequestMapping("/m20")
	public @ResponseBody String m20(User user){
		return user.getId() + user.getName();
	}
	@RequestMapping("/m21")
	public @ResponseBody String m21(OrderForm orderForm){
		Order order = orderForm.getOrder();
		List<OrderDetail> orderDetail = orderForm.getDetails();
		return order.getName() + 
				orderDetail.get(0).getName() +
				orderDetail.get(1).getName();
	}
	
	@RequestMapping("/m22")
	public @ResponseBody String m22(@RequestBody User user) {
		return user.getId() + user.getName();
	}
	@RequestMapping("/m23")
	public @ResponseBody String m23(MultipartFile file) {
		StringBuffer sb = new StringBuffer();
		
		if(!file.isEmpty()) {
			sb.append(file.getOriginalFilename());
			
		}
		return sb.toString();
	}
	@RequestMapping("/m24")
	public @ResponseBody String m24(MultipartFile[] files) {
		StringBuffer sb = new StringBuffer();		
		for(MultipartFile file: files) {
			sb.append(file.getOriginalFilename());			
		}
		return sb.toString();
	}
	/*//配合方法m25使用
	 @ModelAttribute
	public User findUserById(@PathVariable String id) {
		User user = new User();
		user.setId(id);
		user.setName("自动执行");
		return user;
	}*/
	
	@RequestMapping("/m25/{id}")
	public @ResponseBody String m25(Model model){
		Map<String, Object> map = model.asMap();
		User user = (User)map.get("user");
		return user.getId()+","+user.getName();
	}
	//配合方法m26使用
	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		System.out.println("calling");
		binder.addCustomFormatter(new DateFormatter("yyyy-MM-dd"));
	}
	@RequestMapping("/m26")
	public @ResponseBody String m26(Date date) {
		System.out.println(date);
		return date.toString();
	}
	
	@RequestMapping("/m27")
	public @ResponseBody String m27(@Validated WorkInfoForm workInfoForm, BindingResult bindingResult) {
		StringBuilder sb = new StringBuilder();
		if(bindingResult.hasErrors()) {
			List<ObjectError> errorList = bindingResult.getAllErrors();
			FieldError fieldError = (FieldError) errorList.get(0);
			sb.append(fieldError.getObjectName());
			sb.append(",");
			sb.append(fieldError.getField());
			sb.append(",");
			sb.append(fieldError.getDefaultMessage());
		}
		return sb.toString();
	}
	
	@RequestMapping("/m28")
	public @ResponseBody String m28(@Validated({WorkInfoFormGroup.Add.class}) WorkInfoFormGroup workInfoFormGroup, BindingResult bindingResult) {
		StringBuilder sb = new StringBuilder();
		if(bindingResult.hasErrors()) {
			List<ObjectError> errorList = bindingResult.getAllErrors();
			FieldError fieldError = (FieldError) errorList.get(0);
			sb.append(fieldError.getObjectName());
			sb.append(",");
			sb.append(fieldError.getField());
			sb.append(",");
			sb.append(fieldError.getDefaultMessage());
		}
		return sb.toString();
	}
	@RequestMapping("/m29")
	public @ResponseBody String m29(@Validated({WorkInfoFormGroup.Update.class}) WorkInfoFormGroup workInfoFormGroup, BindingResult bindingResult) {
		StringBuilder sb = new StringBuilder();
		if(bindingResult.hasErrors()) {
			List<ObjectError> errorList = bindingResult.getAllErrors();
			FieldError fieldError = (FieldError) errorList.get(0);
			sb.append(fieldError.getObjectName());
			sb.append(",");
			sb.append(fieldError.getField());
			sb.append(",");
			sb.append(fieldError.getDefaultMessage());
		}
		return sb.toString();
	}
	@RequestMapping("/m30")
	public @ResponseBody String m30(@Validated WorkOverTimeForm workOverTimeForm, BindingResult bindingResult) {
		StringBuilder sb = new StringBuilder();
		if(bindingResult.hasErrors()) {
			List<ObjectError> errorList = bindingResult.getAllErrors();
			FieldError fieldError = (FieldError) errorList.get(0);
			sb.append(fieldError.getObjectName());
			sb.append(",");
			sb.append(fieldError.getField());
			sb.append(",");
			sb.append(fieldError.getDefaultMessage());
		}
		return sb.toString();
	}
	//配合WebConfig中的addInterceptors演示
	@RequestMapping("/m31")
	public @ResponseBody String m31() {
		return "session handler interceptor";
	}
	//配合WebConfig中的addCorsMappings演示
	//先访问/m32,然后在浏览器中查看源代码，并将它保存为本地文件
	//然后用浏览器打开，测试/m33方法跨域请求
	@RequestMapping("/m32")
	public  String m32() {
		return "m32.html";
	}
	//配合WebConfig中的addCorsMappings演示
		@RequestMapping("/m33")
		public  @ResponseBody String m33() {
			return "showData({'age':123,'name':'1111'})";
		}
		
		//JacksonConf类来配合演示,观测返回的日期字符串的值
		@RequestMapping("/m34")
		public @ResponseBody Map m34() {
			Map map = new HashMap();
			map.put("time", new Date());
			return map;
		}
		
		//通用错误处理,演示非json请求,需要配合ErrorController使用，还需要一个error.html
		@RequestMapping("/m35")
		public @ResponseBody String m35(boolean flag) {
			if(flag) {
			throw new ApplicationException("this is a general error");
			}
			return "general error";
		}
		//通用错误处理,演示json请求,需要配合ErrorController使用
		@RequestMapping("/m36.json")
		public @ResponseBody String m36(boolean flag) {
			if(flag) {
			throw new ApplicationException("this is a general error");
			}
			return "general error";
		}
}
