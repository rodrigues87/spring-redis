package spring.redis.services.imp;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import spring.redis.dtos.RuleDTO;
import spring.redis.dtos.RuleResponseDTO;
import spring.redis.services.SpringRedisService;
import spring.redis.services.RuleService;


import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
public class RuleServiceImpl implements RuleService {
	
	private final SpringRedisService springRedisService;
	
	public RuleServiceImpl(SpringRedisService springRedisService) {
		this.springRedisService = springRedisService;
	}
	
	@Override
	public void save(RuleDTO ruleDTO) {
		springRedisService.save(ruleDTO.getKey(), ruleDTO.getValue());
	}
	
	@Override
	public RuleResponseDTO findAll() {
		Set<String> keys = springRedisService.findAllKeys();
		if (Objects.isNull(keys) || keys.isEmpty()) {
			return RuleResponseDTO.builder().rules(Collections.emptyList()).build();
		}
		return RuleResponseDTO
				.builder()
				.rules(keys.stream().map(key ->
						RuleDTO
								.builder()
								.key(key)
								.value(springRedisService.findByKey(key, Object.class))
								.build()).collect(Collectors.toList()))
				.build();
	}
	
	@Override
	public Object findByKey(String key) {
		return springRedisService.findByKey(key, Object.class);
	}
	
	@Override
	public void delete(String key) {
		springRedisService.delete(key);
	}
	
	@Override
	public void delete(List<String> keys) {
		springRedisService.delete(keys);
	}
}
