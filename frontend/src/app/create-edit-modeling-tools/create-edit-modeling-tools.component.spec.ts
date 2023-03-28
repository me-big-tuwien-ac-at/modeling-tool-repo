import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CreateEditModelingToolsComponent } from './create-edit-modeling-tools.component';

describe('CreateEditModelingToolsComponent', () => {
  let component: CreateEditModelingToolsComponent;
  let fixture: ComponentFixture<CreateEditModelingToolsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CreateEditModelingToolsComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CreateEditModelingToolsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
