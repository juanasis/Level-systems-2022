import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MateriasPrimaComponent } from './materias-prima.component';

describe('MateriasPrimaComponent', () => {
  let component: MateriasPrimaComponent;
  let fixture: ComponentFixture<MateriasPrimaComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ MateriasPrimaComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(MateriasPrimaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
