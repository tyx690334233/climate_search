import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ResTabsComponent } from './res-tabs.component';

describe('ResTabsComponent', () => {
  let component: ResTabsComponent;
  let fixture: ComponentFixture<ResTabsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ResTabsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ResTabsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
