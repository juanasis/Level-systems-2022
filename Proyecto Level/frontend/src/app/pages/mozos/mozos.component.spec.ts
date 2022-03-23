import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MozosComponent } from './mozos.component';

describe('MozosComponent', () => {
  let component: MozosComponent;
  let fixture: ComponentFixture<MozosComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ MozosComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(MozosComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
