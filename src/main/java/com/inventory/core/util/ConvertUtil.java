package com.inventory.core.util;

import com.inventory.core.model.dto.UserSessionDTO;
import com.inventory.core.model.entity.UserSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ConvertUtil {
	@SuppressWarnings("unused")
	private Logger logger = LoggerFactory.getLogger(ConvertUtil.class);


	public static BigDecimal convertDoubleToDecimal(double value) {
		return new BigDecimal(value);
	}

	public static double convertDecimalToDouble(BigDecimal value) {
		return 0.0;
	}


	public static String getOrderNo(int length) {

		String charString = Long.toString(System.currentTimeMillis());
		String[] arrayString = charString.split("");
		StringBuilder randomCode = new StringBuilder();
		for (int i = 0; i < length; i++) {
			int randomIndex = (int) (Math.random() * charString.length());
			randomCode.append(arrayString[randomIndex]);
		}
		/*
		 * long uniqueId = (long) (Math.random() * 10000L); String orderNo =
		 * Long.toString(uniqueId);
		 */

		System.out.println("generated code >>>> " + randomCode);
		return randomCode.toString();
	}


	/*public static String CreateHash(String key, String password, String hash) throws ClientException {
		try {
			byte[] decodedKey = (key).getBytes();
			SecretKeySpec keySpec = new SecretKeySpec(decodedKey, hash);
			Mac mac = Mac.getInstance(hash);
			mac.init(keySpec);
			byte[] dataBytes = password.getBytes("UTF-8");
			byte[] signatureBytes = mac.doFinal(dataBytes);
			String encoded = new String(Base64.encodeBase64(signatureBytes), "UTF-8");
			System.out.println("Prepared Encoded Signature :" + encoded);
			return encoded;
		} catch (Exception e) {
			throw new ClientException("Service Down !!!");
		}
	}
*/
	public static List<UserSessionDTO> convertSessionList(List<UserSession> userSession) {
		List<UserSessionDTO> dtoList = new ArrayList<UserSessionDTO>();
		for (UserSession session : userSession) {
			dtoList.add(convertSession(session));
		}
		return dtoList;
	}

	public static UserSessionDTO convertSession(UserSession session) {

		UserSessionDTO dto = new UserSessionDTO();
		dto.setMobileNo(session.getUser().getUsername());
		dto.setId(session.getId());
		dto.setSessionId(session.getSessionId());
		dto.setUserId(session.getUser().getId());

		return dto;
	}
}