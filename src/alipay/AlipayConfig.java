package alipay;

public class AlipayConfig {
	// 商户appid
	public static String APPID = "2018021402198213";
	// 私钥 pkcs8格式的
	public static String RSA_PRIVATE_KEY = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCqWf0LDZKFRb8sFhKSAZM0Vi7nv2ijn2W0GW0N1W+BM9zP0bXaeGUfjet7XeHCDiqR2uDAZvIVfLMjjobuAJWnuFhjhR8yGAZDfi2ajU8DozreJnKEOnzWps8QEooSP8rjeiCxwZcyxMB2gfMn4jQTpshuxi2HfUuFj2DZUoO++osg/CVp3G53h7lSbdCtlG/Gya07pIv7oI010V/0tmVoVRqYM8eBkMcd4x6IYN5VOXSPon0+FGO2NWzX7W4iEjBibx3EbOYPL8VGa9s629tsRnsuPM86k66CcNzPWVOqxLDxVDsbqPpjajs0L1AUXFLcXeno6dHPfJ93jFA9MvmZAgMBAAECggEAUIONvK6ihMjtTSn1hvqll4PwUWo/S65nxFKMH5C0Te20PKkSeXqT2PEbzkgWDsDhT+SSe31sh8lTZcfUcCLomMfhRtBBaUy/kSDO7xBAuyhoB11GnMaEAHQAw8jvZyatfwxW7YzhKIajrJ9IzJ32HR+j9e9Gz5XhnvOgQ7bjO9yoOeuGwYrWzy0CIRdmAZa1qxPF40lUGRjM29aNkMSQR0OucU+SF0JWK/fsJ/HD1zqGG5d/nlSMTGnq5JxLooND4IYXASAvZr7mH39IOJ/Zfx+7DhnBLerqRpaq/uiBwKK2dwJJXFI3sC4YBbbCrLqZYi/aY/ikFkQ50lKtexMvnQKBgQD6mGy3rAp+/CfcDeFJ/lkDIWpAwA868NyWS4N0VCrG3D7SmO3DpkdF5dPxTxaPdkmG/G601FzaJ2SETc+ywKJJkPyHkNA0PAxcBESEReiP94cIQBWkAJyc9H3oZPOh12K3gOQIUq5mO2iOQDx5gTu/32tOmtd3ZF7YX4/zPRD7kwKBgQCuBoZqHveP16S3G9MBxW78ZWzFhsWcywU9XeZV0WfId6PzC0gzr5zeFlSERvxOrkm61B/+0BUfN0Y1BnUayk0DUzAGyC+BQwP3Bz9iVHLgWoe+QaUWhIl36LRaVN0MY7KUVs2acc5SLxU8jd9psO120tp8Q5u4hXpssqO5xi7powKBgGXlBI/KjJoFvc9kdW641UjIlYOulILK3WbfXZw3fQ1PENxhAozDV8e8I+nC1tGOr2cLzZgJoZey3NRPL1znpxVytrwIzh/vKrdfpNyD8IRZibZ5GFOPTB8l6uxL4CS2h5VF+3LG69L15RWyWOSu/7SNC/zySRpq3YXtlYK6GeWxAoGAZ7H1qAhZxXleacmHmxeVAYpCHRUobTxAHAf2tw43RCmeJLWFSV8MHv25ULSnpQ6PWmcJxaEh5N7f07rMTrFa4G9SP8hLHj/bfffwmdvuM6deGU0uXNOOwkeEKAtHSBmkEGYEz1Ge+jOZ+OIkzOqjJNmRXNhAUtweTeTIUGs6CvcCgYEAlZAWqh3NRDeYkhBDxW9q1zWKyTJRRfDJGXRHdY6k9DYWD5igf7GsZdSjS3j+1C6O8T3Wl6hZ9WEBWlDen7OuDTuW8V45txic/Iw+g7FyJbKpjbPhNgSaGW9FhV20ozd6UzHCrp8Go/QQTNUYDjsabgdzygyroWl3fy4maLEyIpc=";
	// 商户服务器地址
	public static String BASE_URL = "https://qbserver.cn/fireman";
	// public static String BASE_URL = "http://c19758058n.imwork.net/fireman";
	// 服务器异步通知页面路径 需http://或者https://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
	public static String notify_url = BASE_URL + "/alipay/notify";
	// 页面跳转同步通知页面路径 需http://或者https://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
	// 商户可以自定义同步跳转地址
	public static String return_url = BASE_URL + "/alipay/return";
	// 请求网关地址
	public static String URL = "https://openapi.alipay.com/gateway.do";
	// 编码
	public static String CHARSET = "UTF-8";
	// 返回格式
	public static String FORMAT = "json";
	// 支付宝公钥
	public static String ALIPAY_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAlEvlO3IAAvoHq15QTE5G/BL71Mc04dYusBzmAm/kHKxZXVNvz1tgkOkxBBqwgbuhM+dYfyzhQW7bQGjUXwvYsr1Cc/2mU2g6UmklcOzzjt1YRpYCv7xwPFiHQBUAsPBF/Mt7OeKCCCSG7yM+BFddJiu83r+RV2lUuhBkZqSN9VEBs91bsZhrcsl0TqCDMBwR1tDTAvfDhWvcey20T9dlIB1SRLVHLjAAsTU79zY0vT9m7l6jg2/HWvdSOisa1aVFFpKlCxcuE8yrcRpcHLoC3kMg4wAKIIRmCnfYVfH8srvRexrzglUmUjLCpfgq/4fsuwN4VkUkkRIw6TSB9FoQowIDAQAB";
	// 日志记录目录
	public static String log_path = "/log";
	// RSA2
	public static String SIGNTYPE = "RSA2";

}
