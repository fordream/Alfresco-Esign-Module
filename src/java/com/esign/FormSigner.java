package java.com.esign;
import java.security.PublicKey;

public interface FormSigner {
	public String signForm(String originalData);

	public boolean verifyForm(String signature, String originalData,
			PublicKey pub);
}
