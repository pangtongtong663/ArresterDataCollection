# ArresterDataCollection
 ## Background
 本项目由Web后端组、Web前端组和电子组合作完成，旨在为电子组的设备数据提供后台处理。本人负责该项目的后端开发。
 ## Structure
 ## Usage
 下面是本项目主要实现的功能和相关技术介绍。
 ### 管理员权限
 管理员权限主要是由注解实现，注解实现如下。
 ```java
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LoginRequired {
    boolean needAdmin() default false;
}
```
对于那些需要管理员权限的方法，只需要在方法前指定needAdmin()为true。对于那些只需要用户登录的方法，只需要needAdmin默认即可。

需要管理员权限的方法：
```java
@LoginRequired(needAdmin = true)
@RequestMapping(value = "/modify", method = RequestMethod.POST)
public ResponseDto<Object> modify(@Valid @RequestBody UserLogInDto userLogInDto) {
    return StandardResponse.ok(userService.modify(userLogInDto));
 }
 ```
 不需要管理员权限的方法：
 ```java
 @LoginRequired
 @RequestMapping(value = "/test/username_cn", method = RequestMethod.GET)
 public String getUserNameCn() {
     return AuthInterceptor.getCurrentUser().getUserNameCn();
 }
 ```
 ### 用户注册
 只有管理员才可以添加用户，添加用户时需要提供用户的英文名、中文名和密码，以及用户是否设置为管理员。
 
 该项目利用mybatis-plus+mysql，实现将用户加入数据库中。实现逻辑主要如下：
 ```java
 @Override
    public void signUp(UserSignUpDto signUpDto) {
        DuplicateInfoException e = new DuplicateInfoException();
        QueryWrapper<User> qw = new QueryWrapper<>();
        qw.eq("username_en", signUpDto.getUserNameEn());

        if (userMapper.selectList(qw).size() != 0) {
            e.addDuplicateInfoField("username_en");
        }

        if (e.getDuplicateInfos().size() == 0) {

            User user = User.builder().userNameEn(signUpDto.getUserNameEn())
                            .userNameCn(signUpDto.getUserNameCn())
                                    .password(SecurityTool.encrypt(signUpDto.getUserNameEn(), signUpDto.getPassword()))
                                            .permission(signUpDto.getPermission())
                                                    .build();

            userMapper.insert(user);

        } else {
            throw e;
        }

    }
```
在用户注册的时候，会对用户的密码进行加密，加密算法主要如下:
```java
    public static String encrypt (String password, String salt) {
        String passwordAndSalt = password + salt;
        return Hex.encodeHexString(md5.digest(passwordAndSalt.getBytes(StandardCharsets.UTF_8)));
    }
```
### 用户登录
用户登录时只需要输入用户的英文名和密码。后台会返回一个token和登录用户的信息。在拦截器Interceptor中验证token。

验证token逻辑如下：
```java
 @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {

        if (!(handler instanceof HandlerMethod)) return true;

        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();

        if (method.isAnnotationPresent(LoginRequired.class)) {
            LoginRequired loginRequired = method.getAnnotation(LoginRequired.class);
            String token = request.getHeader("Authorization");
            boolean res = jwt.checkToken(token);
            if (res) {
                User user = userMapper.selectById(jwt.getUserIdFromToken(token));
                currentUserBasicInfo.set(new UserBasicInfo(user.getId()));
                if (loginRequired.needAdmin()) {
                    return authAdmin(user);
                } else {
                    return true;
                }
            } else {
                return false;
            }
        }
        return true;
    }
```
用户登录的实现逻辑如下：
```java
@RequestMapping(value = "/log_in", method = RequestMethod.POST)
    public ResponseDto<Object> logIn(@Valid @RequestBody UserLogInDto userLogInDto) {
        User user = userService.logIn(userLogInDto);

        if (user != null) {
            Map<String,String> map = new HashMap<>();
            map.put("token", jwt.generateJwtToken(user.getId()));
            map.put("登陆状态", "成功登录");
            return StandardResponse.ok(map);
        } else {
            return StandardResponse.fail("用户不存在");
        }

    }
```
为登录用户设置token的逻辑如下：
```java
public String generateJwtToken(int user_id) {

        Map<String, Object> map = new HashMap<>();
        map.put("alg", "HS256");
        map.put("typ", "JWT");

        Map<String,Object> claims = new HashMap<String,Object>();
        claims.put("id", user_id);

        return Jwts.builder()
                .setHeader(map)
                .setClaims(claims)
                .setId(UUID.randomUUID().toString())

                .setIssuedAt(new Date())                                          //签发时间
                .setExpiration(new Date(System.currentTimeMillis() + access_token_expiration * 1000)) //过期时间
                .setSubject("user")                                               //面向用户

                .signWith(SignatureAlgorithm.HS256, SECRET)                       //签名
                .compact();                                                       //压缩生成token

    }
```
### 根据时间来获取数据
下面以A相电流数据为例:

用户登录以后，只需要输入机器设备号和天数即可查询到对应的A相数据，在前端会以趋势图的形式呈现。

Dto逻辑：
```java
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeviceDataByTimeDto {
    @NotBlank(message = "Empty deviceSn")
    private String deviceSn;

    @NotBlank(message = "Empty duration")
    private Integer days;
}
```
处理逻辑：
```java
@Override
    public List<DeviceMessageA> showDataAByTime(String deviceSn, Integer days){
        List<DeviceMessageA> allMsgs = showDataA(deviceSn);
        List<DeviceMessageA> ret = new ArrayList<>();
        LocalDateTime now = LocalDateTime.now();
        for (DeviceMessageA deviceMessageA: allMsgs) {
            Duration duration = Duration.between(deviceMessageA.getCreateTime(), now);
            if (duration.toDays() <= days) {
                ret.add(deviceMessageA);
            }
        }
        return ret;
    }
```
### mybatis-plus自动填充设置
```java
@Component
public class MyMetaObjectHandler implements MetaObjectHandler{
    @Override
    public void insertFill(MetaObject metaObject) {
        this.strictInsertFill(metaObject, "createTime", LocalDateTime.class, LocalDateTime.now());
    }

    @Override
    public void updateFill(MetaObject metaObject) {
    }
}
```

### 各项配置
在这里由于springboot版本问题，导致添加拦截器的配置和跨域配置的顺序不同，所以会出现跨域失败的问题。因此，本项目采用过滤器来配置跨域功能。

跨域配置：
```java
@Configuration
public class CorsConfig {
    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration config = new CorsConfiguration();
        config.addAllowedOrigin("*");
        config.setAllowCredentials(true);
        config.addAllowedMethod("*");
        config.addAllowedHeader("*");
        UrlBasedCorsConfigurationSource configSource = new UrlBasedCorsConfigurationSource();
        configSource.registerCorsConfiguration("/**", config);
        return new CorsFilter(configSource);
    }
}
```
拦截器配置:
```java
@Override
public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(new AuthInterceptor(jwt, userMapper)).addPathPatterns("/**");
 }
```
## 数据库ER图
![emage1](https://github.com/pangtongtong663/picture/blob/main/picture1.png)
