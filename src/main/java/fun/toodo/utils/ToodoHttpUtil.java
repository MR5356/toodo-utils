package fun.toodo.utils;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import fun.toodo.utils.exception.ToodoException;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ToodoHttpUtil {
    public ToodoHttpUtil() {}

    /**
     * 构建get请求
     * @param url 请求地址
     * @return 返回请求对象
     */
    public static HttpRequest httpGet(String url) {
        return HttpRequest.get(url);
    }

    /**
     * 构建post请求
     * @param url 请求地址
     * @return 返回请求对象
     */
    public static HttpRequest httpPost(String url, Object data) {
        return HttpRequest.post(url)
                .body(data.toString());
    }

    /**
     * 发送http请求
     * @param httpRequest 请求对象
     * @return 返回请求结果
     */
    @SneakyThrows
    public static String httpExecute(HttpRequest httpRequest) {
        HttpResponse response;
        for (int i = 0; i < 5; i++) {
            try {
                response = httpRequest.execute();
                if (!response.isOk()) {
                    throw new ToodoException("错误的返回码：" + response.getStatus());
                } else {
                    return response.body();
                }
            } catch (ToodoException e) {
                throw new ToodoException(e.getMessage());
            } catch (Exception e) {
                if (i == 4) {
                    throw new ToodoException(e.getMessage());
                }
                log.info("token获取失败，正在进行第{}次重试", i + 1);
            }
        }
        return null;
    }
}
