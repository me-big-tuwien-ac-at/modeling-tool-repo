import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {ModelingToolsComponent} from './modeling-tools/modeling-tools.component';
import {
  CreateEditModelingToolsComponent,
  CreateEditModelingToolsEnum
} from './create-edit-modeling-tools/create-edit-modeling-tools.component';

const routes: Routes = [
  {path: '', component: ModelingToolsComponent},
  {path: 'modeling_tool', children: [
      {path: 'create', component: CreateEditModelingToolsComponent, data: {mode: CreateEditModelingToolsEnum.create}},
      {path: 'edit', component: CreateEditModelingToolsComponent, data: {mode: CreateEditModelingToolsEnum.edit}},
      {path: 'edit/:id', component: CreateEditModelingToolsComponent, data: {mode: CreateEditModelingToolsEnum.editWithId}}
    ]}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
