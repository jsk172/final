/*
 * package com.khit.library.service;
 * 
 * import com.google.gson.Gson; import com.khit.library.dto.NaverDTO; import
 * org.springframework.beans.factory.annotation.Value; import
 * org.springframework.http.*; import org.springframework.stereotype.Service;
 * import org.springframework.util.LinkedMultiValueMap; import
 * org.springframework.util.MultiValueMap; import
 * org.springframework.web.client.RestTemplate;
 * 
 * @Service public class NaverService {
 * 
 * @Value("${naver.client.id}") private String NAVER_CLIENT_ID;
 * 
 * @Value("${naver.client.secret}") private String NAVER_CLIENT_SECRET;
 * 
 * @Value("${naver.redirect.url}") private String NAVER_REDIRECT_URL;
 * 
 * private final static String NAVER_AUTH_URI = "https://nid.naver.com"; private
 * final static String NAVER_API_URI = "https://openapi.naver.com";
 * 
 * private final Gson gson = new Gson();
 * 
 * public String getNaverLogin() { return NAVER_AUTH_URI + "/oauth2.0/authorize"
 * + "?client_id=" + "fXz0cK7MT1J_YBilmMD2" + "&redirect_uri=" +
 * "http://localhost:8080/oauth2/login/naver" + "&response_type=code"; }
 * 
 * public NaverDTO getNaverInfo(String code) throws Exception { if (code ==
 * null) throw new Exception("Failed get authorization code");
 * 
 * String accessToken; String refreshToken;
 * 
 * try { HttpHeaders headers = new HttpHeaders();
 * headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
 * 
 * MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
 * params.add("grant_type", "authorization_code"); params.add("client_id",
 * "fXz0cK7MT1J_YBilmMD2"); params.add("client_secret", "DDDt2eiQPe");
 * params.add("code", code); params.add("redirect_uri",
 * "http://localhost:8080/oauth2/login/naver");
 * 
 * HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params,
 * headers);
 * 
 * ResponseEntity<String> response = new RestTemplate().exchange( NAVER_AUTH_URI
 * + "/oauth2.0/token", HttpMethod.POST, request, String.class );
 * 
 * if (response.getStatusCode() != HttpStatus.OK) { throw new
 * RuntimeException("Failed to get access token. HTTP error code: " +
 * response.getStatusCode()); }
 * 
 * NaverTokenResponse tokenResponse = gson.fromJson(response.getBody(),
 * NaverTokenResponse.class); accessToken = tokenResponse.getAccessToken();
 * refreshToken = tokenResponse.getRefreshToken(); } catch (Exception e) { throw
 * new Exception("Failed to call Naver API", e); }
 * 
 * return getUserInfoWithToken(accessToken); }
 * 
 * private NaverDTO getUserInfoWithToken(String accessToken) throws Exception {
 * HttpHeaders headers = new HttpHeaders(); headers.setBearerAuth(accessToken);
 * headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
 * 
 * HttpEntity<String> request = new HttpEntity<>(headers);
 * 
 * ResponseEntity<String> response = new RestTemplate().exchange( NAVER_API_URI
 * + "/v1/nid/me", HttpMethod.POST, request, String.class );
 * 
 * if (response.getStatusCode() != HttpStatus.OK) { throw new
 * RuntimeException("Failed to get user info. HTTP error code: " +
 * response.getStatusCode()); }
 * 
 * NaverUserInfoResponse userInfoResponse = gson.fromJson(response.getBody(),
 * NaverUserInfoResponse.class); NaverDTO naverDTO = new NaverDTO();
 * naverDTO.setId(userInfoResponse.getResponse().getId());
 * naverDTO.setEmail(userInfoResponse.getResponse().getEmail());
 * naverDTO.setName(userInfoResponse.getResponse().getName()); return naverDTO;
 * } }
 * 
 * class NaverTokenResponse { private String accessToken; private String
 * refreshToken;
 * 
 * public String getAccessToken() { return accessToken; }
 * 
 * public void setAccessToken(String accessToken) { this.accessToken =
 * accessToken; }
 * 
 * public String getRefreshToken() { return refreshToken; }
 * 
 * public void setRefreshToken(String refreshToken) { this.refreshToken =
 * refreshToken; } }
 * 
 * class NaverUserInfoResponse { private NaverUserInfoResponseData response;
 * 
 * public NaverUserInfoResponseData getResponse() { return response; }
 * 
 * public void setResponse(NaverUserInfoResponseData response) { this.response =
 * response; } }
 * 
 * class NaverUserInfoResponseData { private String id; private String email;
 * private String name;
 * 
 * public String getId() { return id; }
 * 
 * public void setId(String id) { this.id = id; }
 * 
 * public String getEmail() { return email; }
 * 
 * public void setEmail(String email) { this.email = email; }
 * 
 * public String getName() { return name; }
 * 
 * public void setName(String name) { this.name = name; } }
 * 
 */

