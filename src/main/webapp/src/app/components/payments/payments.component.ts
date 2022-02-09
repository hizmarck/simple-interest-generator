import {Component, OnDestroy, OnInit} from '@angular/core';
import {IPayment, IPaymentRequest} from "../../IPayment";
import {PaymentService} from "../../services/payment.service";
import {Subscription} from "rxjs";

@Component({
  selector: 'app-payments',
  templateUrl: './payments.component.html',
  styleUrls: ['./payments.component.css']
})
export class PaymentsComponent implements OnInit, OnDestroy {

  payments: IPayment[] = [];
  subscription: Subscription;

  constructor(private service: PaymentService) {
    this.subscription = service.getPayments().subscribe((payments) => this.payments = payments);
  }

  ngOnInit(): void {}

  ngOnDestroy() {
    this.subscription?.unsubscribe()
  }

  generatePayments(request: IPaymentRequest) {
    this.service.generatePayments(request).subscribe((newPayments) => this.payments.push(...newPayments));
  }

}
