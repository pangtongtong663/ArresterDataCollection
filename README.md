# ArresterDataCollection
 ## Background
 本项目由Web后端组、Web前端组和电子组合作完成，旨在为电子组的设备数据提供后台处理。本人负责该项目的后端开发。
 ## Structure
 ![image2](https://github.com/pangtongtong663/picture/blob/main/picture2.png)
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
### 设备创建
设备创建时，需要提供设备的deviceId，moaName，和设备拥有者的英文名。

在这里只是简单地用mybatis-plus提供的方法插入设备，同时加入查重验证即可。
```java
@Override
    public void deviceCreate(DeviceCreateDto deviceCreateDto) {
        DuplicateInfoException e = new DuplicateInfoException();
        QueryWrapper<Device> qw = new QueryWrapper<>();
        qw.eq("device_id", deviceCreateDto.getDeviceId());

        if (deviceMapper.selectList(qw).size() != 0) {
            e.addDuplicateInfoField("device_id");
        }

        if (e.getDuplicateInfos().size() == 0) {

            Device device = Device.builder().deviceId(deviceCreateDto.getDeviceId())
                    .moaName(deviceCreateDto.getMoaName())
                    .usernameEn(deviceCreateDto.getUsernameEn())
                    .build();

            deviceMapper.insert(device);
            if (deviceCreateDto.getUsernameEn().length() > 0 || !deviceCreateDto.getUsernameEn().equals("undefined")) {
                DeviceAssociation deviceAssociation = DeviceAssociation.builder().usernameEn(deviceCreateDto.getUsernameEn())
                        .deviceId(deviceCreateDto.getDeviceId())
                        .build();
                deviceAssociationMapper.insert(deviceAssociation);
            }

        } else {
            throw e;
        }
    }
```
### 设备绑定
为了方便查找，本项目数据库中设置device_association表，来记录设备和用户的记录关系。因此，在绑定设备的时候，首先要修改该表。如果设备已存在，对其更新即可，如果设备不存在，则在device_association表中插入数据。
```java
if (deviceAssociationMapper.selectList(qw).size() == 0) {
            DeviceAssociation deviceAssociation = DeviceAssociation.builder().usernameEn(deviceBindDto.getUsernameEn())
                    .usernameCn(deviceBindDto.getUsernameCn())
                    .deviceId(deviceBindDto.getDeviceId())
                    .build();
            deviceAssociationMapper.insert(deviceAssociation);

        } else {
            DeviceAssociation deviceAssociation = deviceAssociationMapper.selectList(qw).get(0);
            deviceAssociation.setUsernameEn(deviceBindDto.getUsernameEn());
            deviceAssociation.setUsernameCn(deviceBindDto.getUsernameCn());
            deviceAssociationMapper.updateById(deviceAssociation);
   }
``` 
根据项目需求，为了方便管理员操作，在此没有选择对设备拥有者的信息进行错误判断，而是选择了直接将device的设备拥有者进行修改。
```java
QueryWrapper<Device> qw2 = new QueryWrapper<>();
qw2.eq("device_id", deviceBindDto.getDeviceId());
Device device = deviceMapper.selectList(qw2).get(0);
device.setUsernameEn(deviceBindDto.getUsernameEn());
device.setUsernameCn(deviceBindDto.getUsernameCn());
deviceMapper.updateById(device);
```

### 数据收集
由于要处理的数据过多，前端也需要相应的数据绘制趋势图，因此将数据分为A、B，C三相，以及把绘制趋势图所需的阻性电流，全电流等分离开，额外建表。

数据创建过程有些“屎山”，在这里只体现数据存储的逻辑。
```java
deviceMessageAMapper.insert(deviceMessageA);
deviceMessageBMapper.insert(deviceMessageB);
deviceMessageCMapper.insert(deviceMessageC);
iaMapper.insert(ia);
irMapper.insert(ir);
phMapper.insert(ph);
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

### 自己实现的异常和返回类型
为了更加清晰地反应错误类型，在本项目中自己实现了一些异常，以及建立了response相关的Dto。

信息重复异常
```java
public class DuplicateInfoException extends RuntimeException{
    private ArrayList<String> duplicateInfos = new ArrayList<>();

    public DuplicateInfoException() {
        super();
    }

    public void addDuplicateInfoField(String info) {
        this.duplicateInfos.add(info);
    }

    public ArrayList<String> getDuplicateInfos() {
        return this.duplicateInfos;
    }
}
```
StandardResponse
```java
public class StandardResponse {

    public static ResponseDto<Object> ok() {
        return new ResponseDto<>("200", "success");
    }

    public static ResponseDto<Object> ok(Object data) {
        return new ResponseDto<>("200", "success", data);
    }

    public static ResponseDto<Object> fail() {
        return new ResponseDto<>("400", "failed");
    }
    public static ResponseDto<Object> fail(Object data) {
        return new ResponseDto<>("400", "failed", data);
    }


    public static ResponseDto<Object> duplicateInformation(ArrayList<String> duplicateInfos) {
        return new ResponseDto<>("403", "duplicate information", duplicateInfos);
    }

}
```
### 各项配置
不同的springboot版本，会导致添加拦截器的配置和跨域配置的顺序不同，如果采用实现WebMvcConfigurer可能会出现跨域失败的问题。因此，本项目采用过滤器来配置跨域功能。

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
### 其他
在这里只介绍了本项目主要的实现逻辑和源码展示，其他功能和具体细节可以在项目源码中查看。
## 数据库ER图
![emage1](https://github.com/pangtongtong663/picture/blob/main/picture1.png)
## docker部署
由于团队的云服务器下载mysql版本老是出错，而且自己也没有root权限，所以本项目采用docker部署。


首先拉取镜像，并检查是否拉取成功。
```
docker pull mysql:latest
sudo docker images
```
接着配置和建立容器
```
sudo docker run -p 3310:3306 --name mysql -e MYSQL_ROOT_PASSWORD=xxxxx -d mysql:latest
```
通过docker连接mysql，判断mysql是否创建正确。
```
docker container ls
sudo docker exec -it mysql bash
mysql -uroot -pxxxx
```
最后通过navicat进行远程连接即可。
