import { Injectable } from '@angular/core';
import { environment } from '../../environments/environment';
import { HttpClient } from '@angular/common/http';
import Image from '../models/image';

@Injectable({
  providedIn: 'root'
})
export class ImageService {
  private BASE_URL = 'images';

  constructor(private httpClient: HttpClient) { }

  public getApiImageLink(imageUrl: string): string {
    return `${environment.backendUrl}/${imageUrl}`;
  }

  public uploadImage(file: File) {
    const formData = new FormData();
    formData.append('file', file, file.name);

    return this.httpClient.post<Image>(this.BASE_URL,  formData);
  }
}
