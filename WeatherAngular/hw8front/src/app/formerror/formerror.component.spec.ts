import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { FormerrorComponent } from './formerror.component';

describe('FormerrorComponent', () => {
  let component: FormerrorComponent;
  let fixture: ComponentFixture<FormerrorComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ FormerrorComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(FormerrorComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
