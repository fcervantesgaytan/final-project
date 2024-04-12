import { HttpEvent, HttpHandler, HttpHeaders, HttpInterceptor, HttpRequest } from "@angular/common/http";
import { Observable } from "rxjs";
import { environment } from "../../environments/environment";
import { AuthService } from "../auth/auth.service";
import { Injectable } from "@angular/core";

@Injectable({
  providedIn: 'root'
})
export class APIInteceptor implements HttpInterceptor {
  constructor (private authService: AuthService) { }

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    let headers = new HttpHeaders();

    if (! req.url.startsWith("images")) {
      headers = headers.append('Content-Type', 'application/json');
    }
    if (this.authService.isAuthenticated()) {
      headers = headers.append('Authorization', `Basic ${this.authService.getBasicAuthToken()}`);
    }

    const apiReq = req.clone({
      url: `${environment.apiUrl}/${req.url}`,
      headers: headers,
    });

    return next.handle(apiReq);
  }
}
