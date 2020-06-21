import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {AccountTypeItemMarkComponent} from './account-type-item-mark.component';

describe('AccountTypeItemMarkComponent', () => {
  let component: AccountTypeItemMarkComponent;
  let fixture: ComponentFixture<AccountTypeItemMarkComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [AccountTypeItemMarkComponent]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AccountTypeItemMarkComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
