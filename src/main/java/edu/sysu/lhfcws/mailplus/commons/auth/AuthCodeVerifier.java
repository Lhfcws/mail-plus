package edu.sysu.lhfcws.mailplus.commons.auth;

import edu.sysu.lhfcws.mailplus.commons.base.Consts;

/**
 * @author lhfcws
 * @time 14-10-24.
 */
public class AuthCodeVerifier {

    public static boolean verify(AuthObject authObject) {
        String code = authObject.getAuthCode();
        authObject.setAuthCode(Consts.RESET_AUTH_CODE);

        String verifyCode = new AuthCodeGenerator().generateCode(authObject);

        boolean result = code.equals(verifyCode);
        authObject.setAuthCode(code);
        return result;
    }
}
