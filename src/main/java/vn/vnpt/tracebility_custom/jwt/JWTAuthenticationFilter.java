package vn.vnpt.tracebility_custom.jwt;

import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import vn.vnpt.tracebility_custom.exception.GeneralException;
import vn.vnpt.tracebility_custom.security.CustomUserDetailService;
import vn.vnpt.tracebility_custom.security.UserPrincipal;
import vn.vnpt.tracebility_custom.service.impl.CheckService;
import vn.vnpt.tracebility_custom.util.Constant;


import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class JWTAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private CustomUserDetailService userDetailService;

    @Autowired
    private JWTTokenProvider jwtTokenProvider;


    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                                    FilterChain filterChain) throws ServletException, IOException {
        try {

            boolean isMultipart = ServletFileUpload.isMultipartContent(httpServletRequest);
            if (isMultipart) {
                try {
                    System.out.println("isMultipart - upload file");
                    if (!checkUploadKey(httpServletRequest))
                        throw new GeneralException(Constant.RESPONSE.CODE.C006, Constant.RESPONSE.MESSAGE.C006);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            String jwt = getJWT(httpServletRequest);
            if (StringUtils.hasText(jwt) && jwtTokenProvider.validateToken(jwt)) {
                Long userId = jwtTokenProvider.getUserIdFromJWT(jwt);
                UserPrincipal userPrincipal = (UserPrincipal) userDetailService.loadUserById(userId);
                if (userPrincipal != null) {
                    UsernamePasswordAuthenticationToken authenticationToken = new
                            UsernamePasswordAuthenticationToken(
                            userPrincipal,
                            null,
                            userPrincipal.getAuthorities()
                    );
                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }
            }
        } catch (
                Exception e) {
            log.error("Fail to set authentication " + e);
        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }

    public String getJWT(HttpServletRequest httpServletRequest) {

        String bearerToken = httpServletRequest.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    private boolean checkUploadKey(HttpServletRequest httpRequest) throws IOException {

        String key = httpRequest.getHeader("UploadKey");

        if (key == null) return false;
        else {
            httpRequest.getSession().setAttribute("keyUpload", key);
            return true;
        }
    }

}
