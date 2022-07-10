import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ProfileAccordianComponent } from './profile-accordian.component';

describe('ProfileAccordianComponent', () => {
  let component: ProfileAccordianComponent;
  let fixture: ComponentFixture<ProfileAccordianComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ProfileAccordianComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ProfileAccordianComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
