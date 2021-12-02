/**
 * 
 */
package com.praful.kecloak.payload.response;

/**
 * @author jack
 *
 */
public class VerifyPasswordResponse {

	private boolean result;

	public VerifyPasswordResponse(boolean result) {
		this.result = result;
	}

	/**
	 * @return the result
	 */
	public boolean isResult() {
		return result;
	}

	/**
	 * @param result the result to set
	 */
	public void setResult(boolean result) {
		this.result = result;
	}

}
