package avengers.nexus.github.controller;

import avengers.nexus.github.service.GithubService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/oauth")
public class GithubController {
    @Autowired
    private GithubService githubService;
    @GetMapping("/login")
    public String getAccessToken(@RequestParam String accessCode, HttpServletRequest httpServletRequest){
        String accessToken = githubService.getAccessToken(accessCode);
        httpServletRequest.getSession().invalidate();
        HttpSession session = httpServletRequest.getSession();
        session.setAttribute("access_token",accessToken);
        return accessToken;
    }
}
