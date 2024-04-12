import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';
import { AuthService } from './auth.service';

export const canActivateAuthenticatedGuard: CanActivateFn = (route, state) => {
  if (! inject(AuthService).isAuthenticated()) {
    inject(Router).navigate(['/login']);
  }

  return true;
};
