package com.elastech.helpdelas.dtos;

import com.elastech.helpdelas.model.RequestPasswordModel;
import com.elastech.helpdelas.model.UserModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class RequestPasswordDTO {

    private Long requestId; // Renomeado de tokenId para requestId
    private String token;
    private UserModel user;
    private LocalDateTime expireTime;
    private boolean isUsed;

    public RequestPasswordDTO(RequestPasswordModel requestPasswordModel) {
        this.requestId = requestPasswordModel.getRequestId(); // Certifique-se de que o campo está mapeado corretamente
        this.token = requestPasswordModel.getToken();
        this.user = requestPasswordModel.getUser();
        this.expireTime = requestPasswordModel.getExpireTime();
        this.isUsed = requestPasswordModel.isUsed();
    }

    // Método de conversão para converter de DTO para modelo
    public static RequestPasswordModel convert(RequestPasswordDTO requestPasswordDTO) {
        RequestPasswordModel requestPasswordModel = new RequestPasswordModel();
        requestPasswordModel.setRequestId(requestPasswordDTO.getRequestId());
        requestPasswordModel.setToken(requestPasswordDTO.getToken());
        requestPasswordModel.setUser(requestPasswordDTO.getUser());
        requestPasswordModel.setExpireTime(requestPasswordDTO.getExpireTime());
        requestPasswordModel.setUsed(requestPasswordDTO.isUsed());
        return requestPasswordModel;
    }
}