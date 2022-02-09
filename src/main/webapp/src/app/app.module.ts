import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {HeaderComponent} from './components/header/header.component';
import { ButtonComponent } from './components/button/button.component';
import { PaymentsComponent } from './components/payments/payments.component';
import { GeneratePaymentComponent } from './components/generate-payment/generate-payment.component';
import {FormsModule} from "@angular/forms";
import { PaymentItemComponent } from './components/payment-item/payment-item.component';
import {FontAwesomeModule} from "@fortawesome/angular-fontawesome";
import {HttpClientModule} from "@angular/common/http";


@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    ButtonComponent,
    PaymentsComponent,
    GeneratePaymentComponent,
    PaymentItemComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    FontAwesomeModule,
    HttpClientModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
