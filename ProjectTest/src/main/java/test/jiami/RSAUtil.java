package test.jiami;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.cert.Certificate;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import sun.security.rsa.RSAPrivateKeyImpl;

import com.sun.org.apache.xerces.internal.impl.xs.SchemaGrammar;

public class RSAUtil {
	private static String ALGORITHM = "RSA";
	/** 指定私钥存放文件 */
	private static String PRIVATE_KEY_FILE = "PrivateKey";
	
	public static void main(String[] args) throws Exception {
		Signature sg=Signature.getInstance("RSA");
	    PrivateKey privateKey=new RSAPrivateKeyImpl();
	    
	    ObjectInputStream obj=new ObjectInputStream(new FileInputStream(PRIVATE_KEY_FILE));
		byte[] 
	}
}
