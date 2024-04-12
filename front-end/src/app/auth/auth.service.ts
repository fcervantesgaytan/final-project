import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import User from '../models/user';
import { Subject, firstValueFrom } from 'rxjs';
import { ImageService } from '../image/image.service';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private BASE_URL = 'auth';
  private USER_KEY = 'user';
  private USER_AUTH_TOKEN_KEY = 'Authorization';

  private user: User | null = null;
  private basicAuthToken: string | null = null;

  public sessionUpdate = new Subject<User|null>();

  public getBasicAuthToken(): string | null {
    return this.basicAuthToken;
  }

  public getLoggedInUser(): User | null {
    return this.user;
  }

  constructor(private httpClient: HttpClient, private imageService: ImageService) {
    this.loadAuthentication();
  }

  protected loadAuthentication(): void {
    let userJson = localStorage.getItem(this.USER_KEY);
    let userAuthToken = localStorage.getItem(this.USER_AUTH_TOKEN_KEY);

    if (userJson && userAuthToken) {
      this.user = JSON.parse(userJson);
      this.basicAuthToken = userAuthToken;
    }
  }

  protected saveToLocalStorage(): void {
    localStorage.setItem(this.USER_KEY, JSON.stringify(this.user));
    localStorage.setItem(this.USER_AUTH_TOKEN_KEY, this.basicAuthToken!);
  }

  protected createAuthentication(user: User, password: string): void {
    user.profilePictureUrl = this.imageService.getApiImageLink(user.profilePictureUrl!);

    this.user = user;
    this.basicAuthToken = btoa(`${this.user.email}:${password}`);
  }

  public isAuthenticated(): boolean {
    return this.user !== null && this.basicAuthToken !== null;
  }

  public async register(user: User): Promise<User> {
    const newUser: User = await firstValueFrom(this.httpClient.post<User>(`${this.BASE_URL}/register`, user));

    this.createAuthentication(newUser, user.password!);
    this.saveToLocalStorage();
    this.loadAuthentication();

    this.sessionUpdate.next(this.user);
    return user;
  }

  public async login(user: User): Promise<User> {
    const loggedInUser = await firstValueFrom(this.httpClient.post<User>(`${this.BASE_URL}/login`,  user));

    this.createAuthentication(loggedInUser, user.password!);
    this.saveToLocalStorage();
    this.loadAuthentication();

    this.sessionUpdate.next(this.user);
    return user;
  }

  public async updateUser(user: User, newPassword: string | null): Promise<User> {
    const password = newPassword ? newPassword : atob(this.basicAuthToken!).split(":")[1];

    this.createAuthentication(user, password);
    this.saveToLocalStorage();
    this.loadAuthentication();

    this.sessionUpdate.next(this.user);
    return user;
  }

  public async logout(): Promise<void> {
    this.user = null;
    localStorage.removeItem(this.USER_AUTH_TOKEN_KEY);
    localStorage.removeItem(this.USER_KEY);

    this.sessionUpdate.next(null);
  }
}
