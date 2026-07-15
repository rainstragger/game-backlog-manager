import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BacklogNavbar } from './backlog-navbar';

describe('BacklogNavbar', () => {
  let component: BacklogNavbar;
  let fixture: ComponentFixture<BacklogNavbar>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [BacklogNavbar],
    }).compileComponents();

    fixture = TestBed.createComponent(BacklogNavbar);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
