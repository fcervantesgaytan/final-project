import { Component } from '@angular/core';
import { environment } from '../../environments/environment';
import User from '../models/user';
import { AuthService } from '../auth/auth.service';
import { Subscription } from 'rxjs';
import { Router } from '@angular/router';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrl: './header.component.css'
})
export class HeaderComponent {
  backendUrl = environment.backendUrl;
  user: User | null;
  sessionUpdateSubscription: Subscription;

  constructor(private authService: AuthService, private router: Router) {
    this.user = this.authService.getLoggedInUser();
    this.sessionUpdateSubscription = this.authService.sessionUpdate.subscribe((user) => {
      this.user = user;

      if (user === null) {
        this.router.navigate(['/login']);
      }
    });
  }

  public logout() {
    this.authService.logout();
  }

  get isLoggedin() {
    return this.user;
  }
}
