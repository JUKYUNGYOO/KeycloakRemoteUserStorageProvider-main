package com.appsdeveloperblog.keycloak;

import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;
import org.keycloak.component.ComponentModel;
import org.keycloak.models.KeycloakSession;
import org.keycloak.storage.UserStorageProviderFactory;

public class RemoteUserStorageProviderFactory implements UserStorageProviderFactory<RemoteUserStorageProvider>{

	public static final String PROVIDER_NAME="my-remote-user-storage-provider";
	//사용자 스토리지 spi를 사용하여 외부 사용자 데이터 베이스 및 인증 정보 저장소에 연결할 수 있음.
	
	@Override
	public RemoteUserStorageProvider create(KeycloakSession session, ComponentModel model) {
		 
		return new RemoteUserStorageProvider(session, 
				model, 
				buildHttpClient("http://localhost:8099"));
	}

	@Override
	public String getId() {
		return PROVIDER_NAME;
	}
	
	private UsersApiService buildHttpClient(String uri) {
		
		ResteasyClient client = new ResteasyClientBuilder().build();
		ResteasyWebTarget target = client.target(uri);
		
		return target.proxyBuilder(UsersApiService.class)
				.classloader(UsersApiService.class.getClassLoader()).build();
		
	}

}
