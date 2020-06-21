import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { EntityTabsComponent } from './entity-tabs.component';

describe('EntityTabsComponent', () => {
  let component: EntityTabsComponent;
  let fixture: ComponentFixture<EntityTabsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ EntityTabsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(EntityTabsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
