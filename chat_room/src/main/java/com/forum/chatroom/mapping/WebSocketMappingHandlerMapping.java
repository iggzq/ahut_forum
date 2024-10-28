package com.forum.chatroom.mapping;

import com.forum.chatroom.config.WebSocketMapping;
import org.springframework.beans.BeansException;
import org.springframework.core.Ordered;
import org.springframework.web.reactive.handler.SimpleUrlHandlerMapping;
import org.springframework.web.reactive.socket.WebSocketHandler;

import java.util.HashMap;
import java.util.Map;

/**
 * @author lituizi
 */
public class WebSocketMappingHandlerMapping extends SimpleUrlHandlerMapping {

	private Map<String, WebSocketHandler> handlerMap = new HashMap<>();

	@Override
	public void initApplicationContext() throws BeansException {
		Map<String, Object> beansWithAnnotation = obtainApplicationContext()
			.getBeansWithAnnotation(WebSocketMapping.class);

		for (Object bean : beansWithAnnotation.values()) {
			if (!(bean instanceof WebSocketHandler)) {
				throw new IllegalStateException(
						String.format("@WebSocketMapping must be used on a WebSocketHandler,error in [%s]",
								bean.getClass().getName()));
			}
			WebSocketMapping mapping = bean.getClass().getAnnotation(WebSocketMapping.class);
			String url = mapping.value();
			handlerMap.put(url, (WebSocketHandler) bean);
		}
		super.setOrder(Ordered.HIGHEST_PRECEDENCE);
		super.setUrlMap(handlerMap);
		super.initApplicationContext();
	}

}
