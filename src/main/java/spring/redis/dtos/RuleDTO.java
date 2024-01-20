package spring.redis.dtos;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RuleDTO {
	private String key;
	private Object value;
}
