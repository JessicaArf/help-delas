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

    private Long tokenId;

    private String token;
    private UserModel user;
    private LocalDateTime expireTime;
    private boolean isUsed;

    public RequestPasswordDTO(RequestPasswordModel requestPasswordModel) {
        this.tokenId = requestPasswordModel.getTokenId();
        this.token = requestPasswordModel.getToken();
        this.user = requestPasswordModel.getUser();
        this.expireTime = requestPasswordModel.getExpireTime();
        this.isUsed = requestPasswordModel.isUsed();
    }

    public static RequestPasswordModel convert(RequestPasswordDTO requestPasswordDTO) {
        RequestPasswordModel requestPasswordModel = new RequestPasswordModel();
        requestPasswordModel.setTokenId(requestPasswordDTO.getTokenId());
        requestPasswordModel.setUser(requestPasswordDTO.getUser());
        requestPasswordModel.setExpireTime(requestPasswordDTO.getExpireTime());
        requestPasswordModel.setUsed(requestPasswordDTO.isUsed());
        return requestPasswordModel;
    }
}
