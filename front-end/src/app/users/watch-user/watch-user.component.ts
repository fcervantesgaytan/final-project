import { Component, OnInit } from '@angular/core';
import User from '../../models/user';
import { ActivatedRoute } from '@angular/router';
import { UserService } from '../user.service';

@Component({
  selector: 'app-watch-user',
  templateUrl: './watch-user.component.html',
  styleUrl: './watch-user.component.css'
})
export class WatchUserComponent implements OnInit {
  user: User | null = null;

  constructor (private route: ActivatedRoute, private userService: UserService) {}

  ngOnInit(): void {
    this.userService.getUser(this.route.snapshot.params['id'])
      .subscribe((user) => {
        this.user = user;
      });
  }
}
