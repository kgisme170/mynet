package pkg;

import javax.jws.WebService;

@WebService
interface IWebService {
    /**
     * 转换输入字符串
     * @param words
     * @return
     */
    String transWords(String words);
}