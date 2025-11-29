package Producto.Producto.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/test")
public class TestController {

    @GetMapping("/auth")
    public Map<String, Object> testAuth() {
        Map<String, Object> result = new HashMap<>();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        
        if (auth != null) {
            result.put("authenticated", true);
            result.put("principal", auth.getName());
            result.put("authorities", auth.getAuthorities().toString());
        } else {
            result.put("authenticated", false);
        }
        
        return result;
    }
    
    @GetMapping("/public")
    public Map<String, String> testPublic() {
        return Map.of("message", "Este endpoint es público");
    }
    
    @GetMapping("/admin-test")
    public Map<String, Object> testAdminRole() {
        Map<String, Object> result = new HashMap<>();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        
        result.put("hasRoleAdmin", auth.getAuthorities().stream()
            .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN")));
        result.put("authorities", auth.getAuthorities().toString());
        
        return result;
    }
    
    @PostMapping("/post-test")
    public Map<String, Object> testPost(@RequestBody Map<String, String> body) {
        Map<String, Object> result = new HashMap<>();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        
        result.put("received", body);
        result.put("authenticated", auth.isAuthenticated());
        result.put("authorities", auth.getAuthorities().toString());
        result.put("hasAdminRole", auth.getAuthorities().stream()
            .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN")));
        result.put("hasVendedorRole", auth.getAuthorities().stream()
            .anyMatch(a -> a.getAuthority().equals("ROLE_VENDEDOR")));
        
        return result;
    }
    
    @PostMapping("/admin-post-test")
    public Map<String, Object> testAdminPost() {
        Map<String, Object> result = new HashMap<>();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        
        result.put("message", "POST Test successful");
        result.put("hasRoleAdmin", auth.getAuthorities().stream()
            .anyMatch(a -> a.getAuthority().equals("ADMIN")));
        result.put("authorities", auth.getAuthorities().toString());
        
        return result;
    }
}