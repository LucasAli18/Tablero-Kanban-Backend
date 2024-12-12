package com.tablero.app.dtos;

//Encargado de devolvernos el mensaje con el token que se genero y el tipo que tenga este
public class DtoAuthRespuesta {
	
	private String accessToken;
	private String tokenType = "Bearer ";
	
    public DtoAuthRespuesta(String accessToken) {
        this.accessToken = accessToken;
    }

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
	/**
	 * @return the tokenType
	 */
	public String getTokenType() {
		return tokenType;
	}
	/**
	 * @param tokenType the tokenType to set
	 */
	public void setTokenType(String tokenType) {
		this.tokenType = tokenType;
	}
	
	
}
