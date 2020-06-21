import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CreateReadModalComponent } from './create-read-modal.component';

describe('CreateReadModalComponent', () => {
  let component: CreateReadModalComponent;
  let fixture: ComponentFixture<CreateReadModalComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CreateReadModalComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CreateReadModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
