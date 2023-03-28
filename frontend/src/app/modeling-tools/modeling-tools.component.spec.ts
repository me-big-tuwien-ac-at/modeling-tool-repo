import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ModelingToolsComponent } from './modeling-tools.component';

describe('ModelingToolsComponent', () => {
  let component: ModelingToolsComponent;
  let fixture: ComponentFixture<ModelingToolsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ModelingToolsComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ModelingToolsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
