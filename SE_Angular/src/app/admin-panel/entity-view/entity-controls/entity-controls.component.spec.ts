import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { EntityControlsComponent } from './entity-controls.component';

describe('EntityControlsComponent', () => {
  let component: EntityControlsComponent;
  let fixture: ComponentFixture<EntityControlsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ EntityControlsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(EntityControlsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
