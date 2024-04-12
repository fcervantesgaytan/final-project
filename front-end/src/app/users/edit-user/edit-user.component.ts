import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import User from '../../models/user';
import { FormControl, FormGroup } from '@angular/forms';
import { UserService } from '../user.service';

@Component({
  selector: 'app-edit-user',
  templateUrl: './edit-user.component.html',
  styleUrl: './edit-user.component.css'
})
export class EditUserComponent implements OnInit {
  userId: number;

  updateUserForm = new FormGroup({
    email: new FormControl(''),
    name: new FormControl(''),
    lastName: new FormControl(''),
    bio: new FormControl(''),
    areaOfInterest: new FormControl('')
  });

  constructor (private route: ActivatedRoute, private router: Router, private userService: UserService) {
    this.userId = this.route.snapshot.params['id'];
  }

  ngOnInit(): void {
    this.userService.getUser(this.userId)
      .subscribe((user) => {
        this.updateUserForm.setValue({
          email: user.email,
          name: user.name,
          lastName: user.lastName,
          bio: user.bio,
          areaOfInterest: user.areaOfInterest
        });
      });
  }

  onSubmitUpdate() {
    this.userService.updateUser(this.userId, (this.updateUserForm.value as User))
      .subscribe((user) => {
        this.router.navigate(['..'], { relativeTo: this.route });
      });
  }
}
