import {APP_INITIALIZER, ApplicationConfig, NgModule, provideZoneChangeDetection} from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { ProductsComponent } from './ui/products/products.component';
import { HttpClientModule, provideHttpClient } from '@angular/common/http';
import {KeycloakAngularModule, KeycloakService, provideKeycloak} from 'keycloak-angular';



function initializeKeycloak(keycloak: KeycloakService) {
  return () =>
    keycloak.init({
      config: {
        url: 'http://localhost:8080',
        realm: 'ecom-web-app',
        clientId: 'front-end-angular-client'
      },
      initOptions: {
        onLoad: 'check-sso',// si vous acceder à une ressource securisé c'est la ou il va nous rediriger vers la page login de keycloak
        silentCheckSsoRedirectUri:
          window.location.origin + '/assets/silent-check-sso.html'
      }
    });
}

@NgModule({
  declarations: [
    AppComponent,
    ProductsComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    KeycloakAngularModule

  ],
  providers: [
    provideHttpClient(),
    {provide : APP_INITIALIZER, useFactory : initializeKeycloak, multi :true, deps : [KeycloakService]}
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
