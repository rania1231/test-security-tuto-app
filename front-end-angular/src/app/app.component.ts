import {Component, OnInit} from '@angular/core';
import {KeycloakService} from 'keycloak-angular';
import {KeycloakProfile} from 'keycloak-js';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  standalone: false,
  styleUrl: './app.component.css'
})
export class AppComponent implements OnInit{
  constructor(public keycloakService:KeycloakService) {
  }

  title = 'front-end';
  public profile! : KeycloakProfile;

  async handleLogin(){
    await this.keycloakService.login( {
        redirectUri:window.location.origin
      });
  }
  handleLogout(){
  this.keycloakService.logout(window.location.origin);
  }

  ngOnInit(): void {
    if( this.keycloakService.isLoggedIn()){
      this.keycloakService.loadUserProfile().then(
        profile=>{
          this.profile=profile
        }
      )
    }
  }

}

