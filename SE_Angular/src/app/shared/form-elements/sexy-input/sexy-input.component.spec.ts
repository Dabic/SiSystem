import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {SexyInputComponent} from './sexy-input.component';

describe('SexyInputComponent', () => {
  let component: SexyInputComponent;
  let fixture: ComponentFixture<SexyInputComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [SexyInputComponent]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SexyInputComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
