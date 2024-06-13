package avengers.nexus.github.controller;

import avengers.nexus.github.service.OauthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/oath")
public class OauthController {
    @Autowired
    OauthService oauthService;
    @GetMapping("/login")
    public String getAccessToken(@RequestParam String accessCode, HttpServletRequest httpServletRequest){
        String accessToken = oauthService.getAccessToken(accessCode);
        httpServletRequest.getSession().invalidate();
        HttpSession session = httpServletRequest.getSession();
        session.setAttribute("access_token",accessToken);
        return accessToken;
    }
}
