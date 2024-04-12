import { Component, Input } from '@angular/core';
import { environment } from '../../../environments/environment';

@Component({
  selector: 'app-base-form',
  templateUrl: './base-form.component.html',
  styleUrl: './base-form.component.css'
})
export class BaseFormComponent {
  backEndUrl = environment.backendUrl;

  @Input()
  header: string;

  @Input()
  redirectText: string;

  @Input()
  redirectLink: string;

  @Input()
  redirectLinkText: string;

}
