package com.ulca.model.response;

import java.util.List;
import com.ulca.model.request.ModelLeaderboardRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ModelLeaderboardResponse {

	String message;
	List<ModelLeaderboardResponseDto> data;
	int count;
	
}
