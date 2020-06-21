import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {AttributeFieldsComponent} from './attribute-fields.component';

describe('AttributeFieldsComponent', () => {
  let component: AttributeFieldsComponent;
  let fixture: ComponentFixture<AttributeFieldsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [AttributeFieldsComponent]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AttributeFieldsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
