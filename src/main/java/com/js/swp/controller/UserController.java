
package com.js.swp.controller;

import java.util.Date;

import javax.inject.Inject;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.social.google.connect.GoogleConnectionFactory;
import org.springframework.social.oauth2.GrantType;
import org.springframework.social.oauth2.OAuth2Operations;
import org.springframework.social.oauth2.OAuth2Parameters;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.WebUtils;

import com.js.swp.auth.SNSLogin;
import com.js.swp.auth.SnsValue;
import com.js.swp.domain.User;
import com.js.swp.dto.LoginDTO;
import com.js.swp.interceptor.SessionKey;
import com.js.swp.service.UserService;

@Controller
public class UserControllerss	//WWWW 9번부터 시작
{
	@Inject
	private UserService service;
	
	@Inject
	private SnsValue naverSns;
	
	@Inject
	private SnsValue googleSns;
	
	@Inject
	private GoogleConnectionFactory googleConnectionFactory;
	
	@Inject
	private OAuth2Parameters googleOAuth2Parameters;
	
	@RequestMapping(value = "/auth/google/callback", 	//1011 로그인 추가
			method = { RequestMethod.GET, RequestMethod.POST})
	public String snsLoginCallback(Model model, @RequestParam String code) throws Exception {
		// 1. code를 이용해서 access_token 받기
		// 2. access_token을 이용해서 사용자 profile 정보 가져오기
		SNSLogin snsLogin = new SNSLogin(googleSns);
		String profile = snsLogin.getUserProfile(code); // 1,2번 동시
		System.out.println("Profile>>" + profile);
		model.addAttribute("result", profile);
		
		// 3. DB 해당 유저가 존재하는 체크 (googleid, naverid 컬럼 추가)
		
		// 4. 존재시 강제로그인, 미존재시 가입페이지로!!
		
		return "loginResult";
	}
	
	private static final Logger logger = LoggerFactory.getLogger(UploadController.class);
	
	@RequestMapping(value = "/login", method = RequestMethod.GET) //GET으로하면 url에 아디 비번 보임
	public void loginGET(Model model) throws Exception
	{
		logger.info("login GET...");
		
		SNSLogin snsLogin = new SNSLogin(naverSns);
		model.addAttribute("naver_url", snsLogin.getNaverAuthURL());
		
//		SNSLogin googleLogin = new SNSLogin(googleSns);
//		model.addAttribute("google_url", googleLogin.getNaverAuthURL());
		
		/* 구글code 발행을 위한 URL 생성 */
		OAuth2Operations oauthOperations = googleConnectionFactory.getOAuthOperations();
		String url = oauthOperations.buildAuthorizeUrl(GrantType.AUTHORIZATION_CODE, googleOAuth2Parameters);
		model.addAttribute("google_url", url);
	}
	
	@RequestMapping(value = "/loginPost", method = RequestMethod.POST)
	public void loginPost(LoginDTO dto, Model model, HttpSession session) throws Exception
	{
		try
		{
			logger.info("loginPost>>>>>>>>>>>>={}", dto);
			User user = service.login(dto);	//user를 받는다.
			
			if(user != null)	//user 안비어있으면 로그인 성공
			{	
				Date expire = new Date(System.currentTimeMillis() + SessionKey.EXPIRE * 1000); //기본단위가 m/s 그래서 1초면 * 1000
				service.keepLogin(user.getUid(), session.getId(), expire);
				model.addAttribute("user", user);	//모델에다 user박기전에 db에 먼저쓴다.
				//어트리뷰트에 담기 LoginInterceptor에서 get으로 가질 수 있음.
			}
			else
				model.addAttribute("loginResult", "아이디 또는 비밀번호가 일치하지 않습니다.");
		} 
		
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value = "/logout", method = RequestMethod.GET) //GET으로하면 url에 아디 비번 보임
	public String loginOut(HttpSession session,		//HttpSession 스프링이 나한테 세션 준다.
						HttpServletRequest request, HttpServletResponse response) throws Exception
	{	
		logger.info("logout GET...");
		session.removeAttribute(SessionKey.LOGIN);	//되어있는 로그인을 날려버린다.
		session.invalidate();	//세션의 로그인시간, 세션 맵 정보들 날려버림
		
		//getCookie 이름이 같은 것만 리턴해준다. 키밸류쌍 
		Cookie loginCookie = WebUtils.getCookie(request, SessionKey.LOGIN);	
		
		if(loginCookie != null)	 //로그인 쿠키가 있으면
		{
			loginCookie.setPath("/");
			loginCookie.setMaxAge(0);	//지금까지
			
			response.addCookie(loginCookie);		//스프링쪽에 쿠키를 담아놓음
			
			User user = (User)session.getAttribute(SessionKey.LOGIN);
			service.keepLogin(user.getUid(), session.getId(), new Date());
		}
		return "/login";
	}
	
	@ResponseBody
	@RequestMapping(value="/logoutAjax", method=RequestMethod.GET)
	public ResponseEntity<String> logoutAjax(HttpServletRequest request, HttpServletResponse response, 
			HttpSession session) {
		logger.info("Logout Ajax>> " + session.getAttribute("loginUser"));
		session.removeAttribute("loginUser");
		
		User user = (User)session.getAttribute(SessionKey.LOGIN);
		if (user != null) {
			session.removeAttribute(SessionKey.LOGIN);
			session.invalidate();
			
			Cookie loginCookie = WebUtils.getCookie(request, "loginCookie");
			if (loginCookie != null) {
				loginCookie.setPath("/");
				loginCookie.setMaxAge(0);
				response.addCookie(loginCookie);
			}
		}
		
		return new ResponseEntity<>("logouted", HttpStatus.OK);
	}
	
	@ResponseBody
	@RequestMapping(value = "/loginAjax", method = RequestMethod.POST)
	public ResponseEntity<User> loginAjax(@RequestBody LoginDTO dto, HttpSession session,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.info("loginPost...LoginDTO={}", dto); 
		
		try {
			User user = service.login(dto);
			if (user != null) { // login success
				user.setUpw(null);
				
				session.setAttribute("loginUser", user);
				
				Cookie loginCookie = new Cookie("loginCookie", session.getId());
				loginCookie.setPath("/");
				loginCookie.setMaxAge(7 * 24 * 60 * 60);
				
				response.addCookie(loginCookie);
				
				return new ResponseEntity<>(user, HttpStatus.OK);
				
			} else {
				return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
	}
	
}
