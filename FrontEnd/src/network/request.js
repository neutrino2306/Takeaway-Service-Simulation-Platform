import axios from 'axios';
import qs from 'qs';

// 创建axios实例
const http = axios.create({
    baseURL: 'http://localhost:8080/api', // 后端基础 URL
    timeout: 5000, // 请求超时时间
    transformRequest: [(data) => {
        return qs.stringify(data);
    }],
});

// 设置post请求头
http.defaults.headers.post['Content-Type'] = 'application/x-www-form-urlencoded;charset=UTF-8';

// 导出 http 实例
export default http;
