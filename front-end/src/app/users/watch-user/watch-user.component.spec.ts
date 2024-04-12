import { ComponentFixture, TestBed } from '@angular/core/testing';

import { WatchUserComponent } from './watch-user.component';

describe('WatchUserComponent', () => {
  let component: WatchUserComponent;
  let fixture: ComponentFixture<WatchUserComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [WatchUserComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(WatchUserComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
