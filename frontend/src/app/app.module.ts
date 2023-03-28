import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {ModelingToolsComponent} from './modeling-tools/modeling-tools.component';
import {HttpClientModule} from '@angular/common/http';
import {NgbModule} from '@ng-bootstrap/ng-bootstrap';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {FooterComponent} from './footer/footer.component';
import {ClickOutsideModule} from 'ng-click-outside';
import { HeaderComponent } from './header/header.component';
import {RecaptchaModule} from 'ng-recaptcha';
import {AngularResizeEventModule} from 'angular-resize-event';
import { CreateEditModelingToolsComponent } from './create-edit-modeling-tools/create-edit-modeling-tools.component';

@NgModule({
  declarations: [
    AppComponent,
    ModelingToolsComponent,
    FooterComponent,
    HeaderComponent,
    CreateEditModelingToolsComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    NgbModule,
    FormsModule,
    ClickOutsideModule,
    RecaptchaModule,
    AngularResizeEventModule,
    ReactiveFormsModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
