package test.jiami;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Enumeration;

public class CAUtil {
	public static KeyStore getKeyStore(String keyStorePath, String password) {
		// 实例化密钥库
		KeyStore ks = null;
		FileInputStream is = null;
		try {
			ks = KeyStore.getInstance(KeyStore.getDefaultType());
			// 获得密钥库文件流
			is = new FileInputStream(keyStorePath);
			// 加载密钥库
			ks.load(is, password.toCharArray());

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != is) {
				try {
					is.close();
				} catch (Exception e) {
				}
			}
		}
		return ks;
	}

	// 通过PFX文件获得私钥
	public static PrivateKey GetPvkformPfx(String strPfx, String strPassword) {
		PrivateKey prikey = null;
		try {
			char[] nPassword = null;
			if ((strPassword == null) || strPassword.trim().equals("")) {
				nPassword = null;
			} else {
				nPassword = strPassword.toCharArray();
			}
			KeyStore ks = getKsformPfx(strPfx, strPassword);
			String keyAlias = getAlsformPfx(strPfx, strPassword);
			prikey = (PrivateKey) ks.getKey(keyAlias, nPassword);
		} catch (UnrecoverableKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (KeyStoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return prikey;
	}

	// 通过PFX文件获得KEYSTORE
	public static KeyStore getKsformPfx(String strPfx, String strPassword) {
		FileInputStream fis = null;
		try {
			KeyStore ks = KeyStore.getInstance("PKCS12");
			fis = new FileInputStream(strPfx);
			// If the keystore password is empty(""), then we have to set
			// to null, otherwise it won't work!!!
			char[] nPassword = null;
			if ((strPassword == null) || strPassword.trim().equals("")) {
				nPassword = null;
			} else {
				nPassword = strPassword.toCharArray();
			}
			ks.load(fis, nPassword);

			return ks;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != fis) {
				try {
					fis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}

	// 通过PFX文件获得别名
	public static String getAlsformPfx(String strPfx, String strPassword) {
		String keyAlias = null;
		try {
			KeyStore ks = getKsformPfx(strPfx, strPassword);
			Enumeration enumas = ks.aliases();
			keyAlias = null;
			// we are readin just one certificate.
			if (enumas.hasMoreElements()) {
				keyAlias = (String) enumas.nextElement();
			}
		} catch (KeyStoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return keyAlias;
	}

	// 通过PFX文件获得公钥
	public static PublicKey getPukformPfx(String strPfx, String strPassword) {
		PublicKey pubkey = null;
		try {
			KeyStore ks = getKsformPfx(strPfx, strPassword);
			String keyAlias = getAlsformPfx(strPfx, strPassword);
			Certificate cert = ks.getCertificate(keyAlias);
			pubkey = cert.getPublicKey();
		} catch (KeyStoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return pubkey;
	}

	// 通过PFX文件获得Certificate
	public static Certificate getCfeformPfx(String strPfx, String strPassword) {
		Certificate cert = null;
		try {
			KeyStore ks = getKsformPfx(strPfx, strPassword);
			String keyAlias = getAlsformPfx(strPfx, strPassword);
			cert = ks.getCertificate(keyAlias);
		} catch (KeyStoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return cert;
	}
	// 获取证书对象
	public static X509Certificate getX509Certificate(String certPath) {
		X509Certificate x509Certificate = null;
		InputStream ism = null;
		try {
			CertificateFactory cf = CertificateFactory.getInstance("X.509");
			ism = new FileInputStream(certPath);
			x509Certificate = (X509Certificate) cf.generateCertificate(ism);

		} catch (CertificateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (null != ism) {
				try {
					ism.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return x509Certificate;
	}
}
