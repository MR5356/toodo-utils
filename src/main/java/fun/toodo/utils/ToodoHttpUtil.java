package fun.toodo.utils;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import fun.toodo.utils.exception.ToodoException;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ToodoHttpUtil {
    public ToodoHttpUtil() {}

    public static HttpRequest httpGet(String url) {
        return HttpRequest.get(url);
    }

    public static HttpRequest httpPost(String url, Object data) {
        return HttpRequest.post(url)
                .body(data.toString());
    }

    @SneakyThrows
    public static String httpExecute(HttpRequest httpRequest) {
        HttpResponse response;
        for (int i = 0; i < 5; i++) {
            try {
                response = httpRequest.execute();
                if (response.getStatus() != 200 && response.getStatus() != 204) {
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
