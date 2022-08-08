package com.gavin.mbg.config;

import io.undertow.server.DefaultByteBufferPool;
import io.undertow.websockets.jsr.WebSocketDeploymentInfo;
import org.springframework.boot.web.embedded.undertow.UndertowServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Configuration;

/**
 * Undertow 警告Buffer pool was not set on WebSocketDeploymentInfo 处理配置
 */
@Configuration
public class MyUndertowServletWebServerFactory implements WebServerFactoryCustomizer<UndertowServletWebServerFactory> {
  @Override
  public void customize(UndertowServletWebServerFactory factory) {
    factory.addDeploymentInfoCustomizers(connector -> {
      WebSocketDeploymentInfo webSocketDeploymentInfo = new WebSocketDeploymentInfo();
      webSocketDeploymentInfo.setBuffers(new DefaultByteBufferPool(false, 1024));
      connector.addServletContextAttribute("io.undertow.websockets.jsr.WebSocketDeploymentInfo", webSocketDeploymentInfo);
    });

  }
}
