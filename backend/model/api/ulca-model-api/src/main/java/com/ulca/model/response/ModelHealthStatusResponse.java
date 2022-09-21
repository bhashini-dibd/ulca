package com.ulca.model.response;

import com.ulca.model.dao.ModelHealthStatus;
import lombok.*;

import java.util.List;

@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ModelHealthStatusResponse {
    private String message;
    private List<ModelHealthStatus> benchmark;
    private int count;



}
