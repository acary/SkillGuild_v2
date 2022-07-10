import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CreateContentModalComponent } from './create-content-modal.component';

describe('CreateContentModalComponent', () => {
  let component: CreateContentModalComponent;
  let fixture: ComponentFixture<CreateContentModalComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CreateContentModalComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CreateContentModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
