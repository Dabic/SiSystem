import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {AccountTypeItemComponent} from './account-type-item.component';

describe('AccountTypeItemComponent', () => {
  let component: AccountTypeItemComponent;
  let fixture: ComponentFixture<AccountTypeItemComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [AccountTypeItemComponent]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AccountTypeItemComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
