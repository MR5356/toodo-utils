# Toodo工具箱

## 通用工具

### 是否为手机号
```java
CommonUtil.isMobile("")
```

### 是否为URL
```java
CommonUtil.isURL("")
```

### 是否为Http URL
```java
CommonUtil.isURLHttp("")
```

### 是否为中文
```java
CommonUtil.isChinese("")
```

### 是否为IPV4
```java
CommonUtil.isIPV4("")
```

### 是否为IPV6
```java
CommonUtil.isIPV6("")
```

### 是否为邮箱
```java
CommonUtil.isEMail("")
```

### 是否为UUID
```java
CommonUtil.isUUID("")
```

### 是否为不含'-'的UUID
```java
CommonUtil.isUUIDSimple("")
```

### 是否为MAC地址
```java
CommonUtil.isMacAddress("")
```

## 转换工具

### 字节转换工具
```java
ConversionUtil.byteConversion()
```

## RSA工具

### 生成密钥对
```java
Map<String, Key> map = RSAUtil.init()
```

### 获取公钥
```java
RSAUtil.getPublicKey(map)
```

### 获取私钥
```java
RSAUtil.getPrivateKey(map)
```

### 公钥加密
```java
RSAUtil.encryptByPublicKey(str, publicKey)
```

### 公钥解密
```java
RSAUtil.decryptByPublicKey(enStr2, publicKey)
```

### 私钥加密
```java
RSAUtil.encryptByPrivateKey(str, privateKey)
```

### 私钥解密
```java
RSAUtil.decryptByPrivateKey(enStr1, privateKey)
```

### 私钥消息签名
```java
String sign = RSAUtil.sign(enStr2, privateKey)
```

### 公钥验证签名正确性
```java
RSAUtil.verify(enStr2, publicKey, sign)
```

## 网络请求工具

### GET请求体构造
```java
HttpRequest httpRequest = ToodoHttpUtil.httpGet(url)
```

### POST请求体构造
```java
HttpRequest httpRequest = ToodoHttpUtil.httpPost(url, data)
```

### 发送网络请求
```java
ToodoHttpUtil.httpExecute(httpRequest)
```

## 企业微信工具

### 初始化
```java
WeworkUtil weworkUtil = new WeworkUtil("corpID", "corpSecret", "agentID");
```

### 获取Access Token
```java
weworkUtil.getAccessToken()
```

### 获取AccessToken过期时间
```java
weworkUtil.getAccessTokenExpiresTime()
```

### 获取部门成员
```java
weworkUtil.getUserListByDepartmentID("1")
```

### 发送消息
```java
weworkUtil.sendMessage()
```

### 发送webhook机器人消息(static)
```java
WeworkUtil.sendMessage()
```