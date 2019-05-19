package com.recetas.spring.boot.backend.apirest.auth;

public class JwtConfig {

	public static final String LLAVE_SECRETA = "alguna.clave.secreta.12345678";
	
	public static final String RSA_PRIVADA = "-----BEGIN RSA PRIVATE KEY-----\r\n" + 
			"MIIEowIBAAKCAQEAu8oBXDw8QeQ2cG0a/cik5xQwyL2R6O8sdtA1iqKq6E97Oa0v\r\n" + 
			"B89pmx+a+VTNi8nWKWy6d1oMTkeiWjwGEoLGprfgNz1OdnPZw2klDTN7MkPUvvGo\r\n" + 
			"p/eM5pAydgZX2qkhZ3iHfS9egZPz48mC3JtAHohcIfRUvEx2pR7EIjJftO0pKTSB\r\n" + 
			"H2JNTOJy0NWbTg15Uignafjkf1P+/SaoG6BV3RELwwkKag2oB82+0goEwlGWUg12\r\n" + 
			"s6V8jtHUlxF8BnXcIn0zWpQyjeMzfnHReaVm1/vzDc2ENWLXCwrTII6m/jy+3DxS\r\n" + 
			"asnHGxu+UFniANJbYvukb7ahDJ8RiSSx148B0QIDAQABAoIBAGrQ5qEuaiAYzZZ5\r\n" + 
			"mSbOIG6In48vtXVHxoT2vd3T1V87Bp6yEdRheHKSMbatNBXVgwohHJeTEvjb6k9l\r\n" + 
			"YRbaaGCgqtsA9kaOc0mh3z95DxRyXOsXzpVsTCtBzlIwHXz1Q1j1yCu+7ATMwafK\r\n" + 
			"EbOmY3x6l92Ae0p4O+yEaZACE0p3uO/TqE97FB/DpTR0atgfvojJX/LO6h/k28/z\r\n" + 
			"2yYwuVjSoVGYC8IyyYD7SGFP1liawAmMcoR/xqp9wz923OwPfHKiw9hTesYxd58K\r\n" + 
			"7aTWzloxUMZEw3MAAdYGHrGB3zj/roxqLk/JQByR1lzywBfF/jNe9GqqKncpVh8+\r\n" + 
			"4Ub2iBkCgYEA95x1yaLWslIBZq9UQQtAFvJe9KBbfjfOLqtCE9fGHauh4Ov3AmKL\r\n" + 
			"qko7dCAThDCBlEO31+PCfZB4hc3Fd4BxD8Wfg7OeZtmjz1cPOPOXErEL4yRZvG9J\r\n" + 
			"m3ZbTAIEz8c2i9aaPubZ2F6b86V5+MDkx/nhY+t/1jokF9zKh6FTmCMCgYEAwia0\r\n" + 
			"zKTORqvE5gC5KUeM6MqdVrBLxKUB9pQ7xdjHLDg4JTiawBzxnVo1q3Oer23ch5UG\r\n" + 
			"2gsGq7DseescYh5m3DKttS4Ee/aHg1r/A9H94VLZhFflTJNhWPyImVH8Cgp2e613\r\n" + 
			"JoY74sZR3s+Q43pCKuv41kucDM7bFN0k/pd5g3sCgYAaWV33bQqnwy4+/R1cJpRV\r\n" + 
			"XMlaAs55qfqY4MSRU/7THXaGdtW8PTxbjLay7s7k3DyNyH8BzEzgC8Wnh3Qh+VsO\r\n" + 
			"sJr+6thKXZKe98a46sMCzDBVpYKfGMCeeIPSnMxiR/K4Asg7NVmtOrQhw1Qtcmlm\r\n" + 
			"FmMzvNXHuDwrBeXPdQ1uPQKBgGTDK+BtxDS/XxAz3eVDoikLRv8/vgMP1GYB5se2\r\n" + 
			"yx/zCYifyQjdvLP6ppIWDoxjiym+uXCeelbPGtvnnrJ5rs6naqn2eRNRDmGo72Mv\r\n" + 
			"MAEqO4PJDlNgxgwl/Er+Vr1rUrS7gXQ4BIqcfM8TkNHOmNqn31xwn0uya9d3MoUD\r\n" + 
			"XmhTAoGBAN/VYnQdB+AlrkzYfl1PBrSRf8I9rifqLEv2nrcwONJM4lhzCzorO2XZ\r\n" + 
			"P0YcAMVcQ49OrM1YON6qCiGRe7+baQNOWiDsn/FDUyPyz2QYmpV8nPs4zMMYq8HX\r\n" + 
			"u3eMy5muPdSeIKW5vraJdtiG+9ZcZEzecqEl5XND6PGnar4IGISn\r\n" + 
			"-----END RSA PRIVATE KEY-----";
	
	public static final String RSA_PUBLICA = "-----BEGIN PUBLIC KEY-----\r\n" + 
			"MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAu8oBXDw8QeQ2cG0a/cik\r\n" + 
			"5xQwyL2R6O8sdtA1iqKq6E97Oa0vB89pmx+a+VTNi8nWKWy6d1oMTkeiWjwGEoLG\r\n" + 
			"prfgNz1OdnPZw2klDTN7MkPUvvGop/eM5pAydgZX2qkhZ3iHfS9egZPz48mC3JtA\r\n" + 
			"HohcIfRUvEx2pR7EIjJftO0pKTSBH2JNTOJy0NWbTg15Uignafjkf1P+/SaoG6BV\r\n" + 
			"3RELwwkKag2oB82+0goEwlGWUg12s6V8jtHUlxF8BnXcIn0zWpQyjeMzfnHReaVm\r\n" + 
			"1/vzDc2ENWLXCwrTII6m/jy+3DxSasnHGxu+UFniANJbYvukb7ahDJ8RiSSx148B\r\n" + 
			"0QIDAQAB\r\n" + 
			"-----END PUBLIC KEY-----";
}
