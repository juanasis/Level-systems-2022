import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ProductoitemComponent } from './productoitem.component';

describe('ProductoitemComponent', () => {
  let component: ProductoitemComponent;
  let fixture: ComponentFixture<ProductoitemComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ProductoitemComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ProductoitemComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
